package org.spaconference.rts.exercises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
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

    @Test
    public void test(Function<List<Integer>, List<Integer>> f) {
        assertThat(f.apply(asList(1, 2, 3, 4, 5, 6, 7, 8)), equalTo(asList(2, 4, 6, 8)));
    }

}
