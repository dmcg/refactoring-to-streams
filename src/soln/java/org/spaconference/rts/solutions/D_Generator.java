package org.spaconference.rts.solutions;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Theories.class)
public class D_Generator {

    @DataPoint
    public static IntFunction<List<Integer>> FOR_LOOP = count -> {
        List<Integer> result = new ArrayList<>();
        int i1 = 1;
        int i2 = 2;

        for (int i = 0; i < count; i++) {
            int t = i1;
            i1 = i2;
            i2 = t + i2;
            result.add(t);
        }
        return result;
    };

    @DataPoint
    public static IntFunction<List<Integer>> STREAM = count ->
            IntStream.generate(fibonacciSupplier(1, 2)).limit(count).boxed().collect(toList());



    @Theory
    public void test(IntFunction<List<Integer>> f) {
        assertThat(f.apply(10), equalTo(asList(1, 2, 3, 5, 8, 13, 21, 34, 55, 89)));
    }

    public static IntSupplier fibonacciSupplier(int initial1, int initial2) {
        return new IntSupplier() {
            int i1 = initial1;
            int i2 = initial2;

            @Override
            public int getAsInt() {
                int result = i1;
                i1 = i2;
                i2 = result + i2;
                return result;
            }
        };
    }
}
