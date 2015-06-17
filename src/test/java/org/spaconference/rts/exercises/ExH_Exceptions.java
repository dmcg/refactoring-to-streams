package org.spaconference.rts.exercises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;
import org.spaconference.rts.runner.ExampleRunner.Way;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(ExampleRunner.class)
public class ExH_Exceptions {
    @Way
    public static List<URL> old_way(List<String> strings) throws MalformedURLException {
        List<URL> uris = new ArrayList<>();
        for (String string : strings) {
            uris.add(new URL(string));
        }
        return uris;
    }

    @Way
    public static List<URL> exception_tunneling(List<String> strings) throws MalformedURLException {
        return new ArrayList<>();
    }

    @Test
    public void parse_good_uris(PartialFunction<List<String>,List<URI>, MalformedURLException> f) throws MalformedURLException {
        List<String> good_uris = asList(
                "http://example.com/example_a",
                "http://example.com/example_b",
                "http://example.com/example_c",
                "http://example.com/example_d");

        assertThat(f.apply(good_uris), equalTo(asList(
                new URL("http://example.com/example_a"),
                new URL("http://example.com/example_b"),
                new URL("http://example.com/example_c"),
                new URL("http://example.com/example_d")
        )));
    }

    @Test
    public void parse_bad_uris(PartialFunction<List<String>,List<URI>, MalformedURLException> f) throws MalformedURLException {
        List<String> good_uris = asList(
                "http://example.com/example_a",
                "http://example.com/example_b",
                "http://example.com/example_c",
                "http://example.com/example_d");

        f.apply(good_uris);
    }

    @FunctionalInterface
    public interface PartialFunction<A, R, T extends Throwable> {
        R apply(A value) throws T;
    }
}
