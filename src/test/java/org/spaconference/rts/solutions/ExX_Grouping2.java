package org.spaconference.rts.solutions;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExX_Grouping2 {

    @Way
    public static Map<Integer, List<Integer>> oldWay(int max) {
        SortedMap<Integer, List<Integer>> multiples = new TreeMap<>();

        for (int i = 2; i < max; i++) {
            int divisor = smallestDivisor(i);

            if (multiples.containsKey(divisor)) {
                multiples.get(divisor).add(i);
            } else {
                List<Integer> group = new ArrayList<>();
                group.add(i);
                multiples.put(divisor, group);
            }
        }

        return multiples;
    }

    @Way
    public static Map<Integer, List<Integer>> newWay(int max) {
        return IntStream.range(2, max).boxed().collect(groupingBy(ExX_Grouping2::smallestDivisor));
    }

    @Test
    public void test_to_10(IntFunction<Map<Integer, List<Integer>>> multiples) {
        assertThat(multiples.apply(10), equalTo(ImmutableMap.of(
                2, asList(2, 4, 6, 8),
                3, asList(3, 9),
                5, asList(5),
                7, asList(7))));
    }

    @Test
    public void test_to_30(IntFunction<Map<Integer, List<Integer>>> f) {
        assertThat(f.apply(30), equalTo(ImmutableMap.builder()
                .put(2, asList(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28))
                .put(3, asList(3, 9, 15, 21, 27))
                .put(5, asList(5, 25))
                .put(7, asList(7))
                .put(11, asList(11))
                .put(13, asList(13))
                .put(17, asList(17))
                .put(19, asList(19))
                .put(23, asList(23))
                .put(29, asList(29))
                .build()));
    }

    private static List<Integer> rangeAsList(final int from, final int to) {
        return IntStream.range(from, to).boxed().collect(toList());
    }

    static int smallestDivisor(int n) {
        assert n >= 2;

        int sqrt_n = (int) floor(sqrt(n));
        int k = 2;
        while (k <= sqrt_n) {
            if (n % k == 0) {
                return k;
            } else {
                k = k + 1;
            }
        }
        return n;
    }
}
