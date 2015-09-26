package org.spaconference.rts.solutions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExG_Finding {

    @Way
    public static String oldWay(List<String> strings, Predicate<? super String> criteria) {
        for (String string : strings) {
            if (criteria.test(string))
                return string;
        }
        return null;
    }

    @Way
    public static String step1_stream(List<String> strings, Predicate<? super String> criteria) {
        for (String string : (Iterable<String>) strings.stream()::iterator) {
            if (criteria.test(string))
                return string;
        }
        return null;
    }

    @Way
    public static String step2_filter(List<String> strings, Predicate<? super String> criteria) {
        for (String string : (Iterable<String>) strings.stream().filter(criteria)::iterator) {
            return string;
        }
        return null;
    }

    @Way
    public static String step3_find(List<String> strings, Predicate<? super String> criteria) {
        Optional<String> result = strings.stream().filter(criteria).findFirst();
        return result.orElse(null);
    }

    @Way
    public static String step4_inline(List<String> strings, Predicate<? super String> criteria) {
        return strings.stream().filter(criteria).findFirst().orElse(null);
    }

    @Test
    public void finds_item_when_first_in_list(BiFunction<List<String>, Predicate<? super String>, String> f) {
        assertThat(f.apply(asList("one", "two", "three"), s -> s.startsWith("o")), equalTo("one"));
    }

    @Test
    public void finds_first_item_when_many_in_list(BiFunction<List<String>, Predicate<? super String>, String> f) {
        assertThat(f.apply(asList("one", "two", "three"), s -> s.startsWith("t")), equalTo("two"));
    }

    @Test
    public void returns_null_if_nothing_found(BiFunction<List<String>, Predicate<? super String>, String> f) {
        assertThat(f.apply(asList("one", "two", "three"), s -> s.startsWith("f")), nullValue());
    }

    @Test
    public void returns_null_on_empty_list(BiFunction<List<String>, Predicate<? super String>, String> f) {
        assertThat(f.apply(asList(), s -> s.startsWith("f")), nullValue());
    }

    @Test
    public void doesnt_evaluate_predicate_after_match_found(BiFunction<List<String>, Predicate<? super String>, String> f) {
        assertThat(f.apply(asList("one", null), s -> s.startsWith("o")), equalTo("one"));
    }

}
