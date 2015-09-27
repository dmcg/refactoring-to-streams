package org.spaconference.rts.solutions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;
import org.spaconference.rts.runner.ExampleRunner.Way;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(ExampleRunner.class)
public class ExL_IgnoreExceptions {

    @Way
    public static List<URL> oldWay(List<String> strings) {
        List<URL> uris = new ArrayList<>();
        for (String string : strings) {
            try {
                uris.add(new URL(string));
            } catch (MalformedURLException ignored) {
            }
        }
        return uris;
    }

    @Way
    public static List<URL> step_1_forEach(List<String> strings) {
        List<URL> uris = new ArrayList<>();
        strings.stream().forEach(string -> {
            try {
                uris.add(new URL(string));
            } catch (MalformedURLException ignored) {
            }
        });
        return uris;
    }

    @Way
    public static List<URL> step_2_flatmap(List<String> strings) {
        return strings.stream().flatMap(string -> {
            try {
                return Stream.of(new URL(string));
            } catch (MalformedURLException ignored) {
                return Stream.empty();
            }
        }).collect(toList());
    }

    @Way
    public static List<URL> step_3_guarded(List<String> strings) {
        return strings.stream().flatMap(guarded(URL::new)).collect(toList());
    }

    @Way
    public static List<URL> step_3a_Optional_and_filter(List<String> strings) {
        return strings.stream().map(string -> {
            try {
                return Optional.of(new URL(string));
            } catch (MalformedURLException e) {
                return Optional.<URL>empty();
            }
        }).filter(Optional::isPresent).map(Optional::get).collect(toList());
    }

    private static interface FailingFunction<T, R> {
        public R apply(T t) throws Exception;
    }

    private static <T, R> Function<T, Stream<R>> guarded(FailingFunction<T, R> f) {
        return t -> {
            try {
                return Stream.of(f.apply(t));
            } catch (RuntimeException x) {
                throw x;
            } catch (Exception x) {
                return Stream.empty();
            }
        };
    }

    @Test
    public void good_urls_pass(UrlParser f) throws MalformedURLException {
        List<String> good_uris = asList(
                "http://example.com/example_a",
                "http://example.com/example_b",
                "http://example.com/example_c");

        assertThat(f.apply(good_uris), equalTo(asList(
                new URL("http://example.com/example_a"),
                new URL("http://example.com/example_b"),
                new URL("http://example.com/example_c")
        )));
    }

    @Test
    public void bad_urls_are_skipped(UrlParser f) throws MalformedURLException {
        List<String> mixed_uris = asList(
                "http://example.com/good",
                "example.com/bad",
                "http://example.com/good2");

        assertThat(f.apply(mixed_uris), equalTo(asList(
                new URL("http://example.com/good"),
                new URL("http://example.com/good2")
        )));
    }


    @FunctionalInterface
    public interface UrlParser {
        List<URL> apply(List<String> value) throws MalformedURLException;
    }
}
