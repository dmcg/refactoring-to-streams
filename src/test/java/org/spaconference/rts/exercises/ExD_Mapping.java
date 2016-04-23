package org.spaconference.rts.exercises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExD_Mapping {

    @Way
    public static List<Integer> oldWay(List<String> strings) {
        List<Integer> result = new ArrayList<>();
        for (String string : strings) {
            result.add(Integer.parseInt(string));
        }
        return result;
    }

    @Way
    public static List<Integer> forEach(List<String> strings) {
        List<Integer> result = new ArrayList<>();
        strings.stream().forEach(string -> result.add( Integer.parseInt(string) ));
        return result;
    }
    @Way
    public static List<Integer> map(List<String> strings) {
        List<Integer> result = new ArrayList<>();
        strings.stream().map(Integer::parseInt).forEach(result::add);
        return result;
    }
    @Way
    public static List<Integer> map2(List<String> strings) {
        return strings.stream().map(Integer::parseInt).collect(toList());
    }

    @Test
    public void test(Function<List<String>, List<Integer>> f) {
        assertThat(f.apply(asList("2", "3", "5", "7")), equalTo(asList(2, 3, 5, 7)));
    }
}
