package org.spaconference.rts.solutions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;
import org.spaconference.rts.runner.ExampleRunner.Way;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(ExampleRunner.class)
public class ExJ_FlatMapping {

    @Way
    public static List<Integer> oldWay(int max) {
        List<Integer> ints = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            for (int j = 1; j <= i; j++) {
                ints.add(j);
            }
        }
        return ints;
    }

    @Way
    public static List<Integer> newWay(int max) {
        return IntStream.range(1, max + 1).flatMap(i -> IntStream.range(1, i + 1))
                .boxed().collect(toList());
    }

    @Test
    public void counting_to_one(IntFunction<List<Integer>> f) {
        assertThat(f.apply(1), equalTo(asList(
                1)));
    }

    @Test
    public void counting_to_two(IntFunction<List<Integer>> f) {
        assertThat(f.apply(2), equalTo(asList(
                1,
                1, 2)));
    }

    @Test
    public void counting_to_four(IntFunction<List<Integer>> f) {
        assertThat(f.apply(4), equalTo(asList(
                1,
                1, 2,
                1, 2, 3,
                1, 2, 3, 4)));
    }

    @Test
    public void counting_to_ten(IntFunction<List<Integer>> f) {
        assertThat(f.apply(10), equalTo(asList(
                1,
                1, 2,
                1, 2, 3,
                1, 2, 3, 4,
                1, 2, 3, 4, 5,
                1, 2, 3, 4, 5, 6,
                1, 2, 3, 4, 5, 6, 7,
                1, 2, 3, 4, 5, 6, 7, 8,
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    }

    @Test
    public void counting_to_zero_is_not_counting(IntFunction<List<Integer>> f) {
        assertThat(f.apply(0), equalTo(emptyList()));
    }

    @Test
    public void counting_to_negative_number_is_not_counting(IntFunction<List<Integer>> f) {
        assertThat(f.apply(-1), equalTo(emptyList()));
    }
}
