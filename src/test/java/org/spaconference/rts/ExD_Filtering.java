package org.spaconference.rts;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Theories.class)
public class ExD_Filtering {

    @DataPoint
    public static Function<List<Integer>, List<Integer>> FOR_LOOP = xs -> {
        List<Integer> result = new ArrayList<>();
        for (int x : xs) {
            if (x % 2 == 0)
                result.add(x);
        }
        return result;
    };

    @DataPoint
    public static Function<List<Integer>, List<Integer>> STREAM = null;


    @Theory
    public void test(Function<List<Integer>, List<Integer>> f) {
        assertThat(f.apply(asList(1, 2, 3, 4, 5, 6, 7, 8)), equalTo(asList(2, 4, 6, 8)));
    }

}
