package org.spaconference.rts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.Ways.waysFrom;


@RunWith(Parameterized.class)
public class ExD_Filtering {

    @Way
    public static List<Integer> oldWay(List<Integer> xs) {
        List<Integer> result = new ArrayList<>();
        for (int x : xs) {
            if (x % 2 == 0)
                result.add(x);
        }
        return result;
    };

    @Way
    public static List<Integer> newWay(List<Integer> xs) {
        return Collections.emptyList();
    };


    @Test
    public void test() {
        assertThat(f.apply(asList(1, 2, 3, 4, 5, 6, 7, 8)), equalTo(asList(2, 4, 6, 8)));
    }

    @Parameterized.Parameters public static Collection<?> tests() {
        return waysFrom(ExD_Filtering.class, Function.class);
    }

    @Parameterized.Parameter public Function<List<Integer>, List<Integer>> f;

}
