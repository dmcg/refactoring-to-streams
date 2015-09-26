package org.spaconference.rts.solutions;

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
public class ExE_Filtering {

    @Way
    public static List<Integer> oldWay(List<Integer> xs) {
        List<Integer> result = new ArrayList<>();
        for (int x : xs) {
            if (x % 2 == 0)
                result.add(x);
        }
        return result;
    }

    @Way
    public static List<Integer> step1_stream(List<Integer> xs) {
        List<Integer> result = new ArrayList<>();
        xs.stream().forEach(x -> {
            if (x % 2 == 0) result.add(x);
        });
        return result;
    }

    @Way
    public static List<Integer> step2_filter(List<Integer> xs) {
        List<Integer> result = new ArrayList<>();
        xs.stream().filter(x -> x % 2 == 0).forEach(result::add);
        return result;
    }

    @Way
    public static List<Integer> step3_collect(List<Integer> xs) {
        return xs.stream().filter(x -> x % 2 == 0).collect(toList());
    }

    @Test
    public void test(Function<List<Integer>, List<Integer>> f) {
        assertThat(f.apply(asList(1, 2, 3, 4, 5, 6, 7, 8)), equalTo(asList(2, 4, 6, 8)));
    }

}
