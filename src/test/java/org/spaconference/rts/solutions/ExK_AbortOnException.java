package org.spaconference.rts.solutions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;
import org.spaconference.rts.runner.ExampleRunner.Way;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(ExampleRunner.class)
public class ExK_AbortOnException {

    @Way
    public static List<URL> oldWay(List<String> strings) throws MalformedURLException {
        List<URL> uris = new ArrayList<>();
        for (String string : strings) {
            uris.add(new URL(string));
        }
        return uris;
    }

    @Way
    public static List<URL> step_1_stream(List<String> strings) throws MalformedURLException {
        List<URL> uris = new ArrayList<>();
        for (String string : (Iterable<String>) strings.stream()::iterator) {
            uris.add(new URL(string));
        }
        return uris;
    }

    @Way
    public static List<URL> step_2_for_each(List<String> strings) throws MalformedURLException {
        List<URL> uris = new ArrayList<>();
        try {
            strings.stream().forEach(string -> {
                try {
                    uris.add(new URL(string));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            });
            return uris;
        } catch (RuntimeException e) {
            throw (MalformedURLException) e.getCause();
        }
    }

    @Way
    public static List<URL> step_3_map(List<String> strings) throws MalformedURLException {
        try {
            return strings.stream().map(string -> {
                try {
                    return new URL(string);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }).collect(toList());
        } catch (RuntimeException e) {
            throw (MalformedURLException) e.getCause();
        }
    }

    @Way
    public static List<URL> step_4_private_exception_type(List<String> strings) throws MalformedURLException {
        class Abort extends RuntimeException {
            public Abort(MalformedURLException e) {
                super(e);
            }
        }
        try {
            return strings.stream().map(string -> {
                try {
                    return new URL(string);
                } catch (MalformedURLException e) {
                    throw new Abort(e);
                }
            }).collect(toList());
        } catch (Abort e) {
            throw (MalformedURLException) e.getCause();
        }
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

    @Test(expected = MalformedURLException.class)
    public void bad_urls_abort(UrlParser f) throws MalformedURLException {
        List<String> bad_uris = asList(
                "http://example.com/good",
                "example.com/bad",
                "http://example.com/good2");

        f.apply(bad_uris);
    }


    @FunctionalInterface
    public interface UrlParser {
        List<URL> apply(List<String> value) throws MalformedURLException;
    }
}
