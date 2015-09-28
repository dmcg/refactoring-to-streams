package org.spaconference.rts.exercises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;
import org.spaconference.rts.runner.ExampleRunner.Way;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
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
