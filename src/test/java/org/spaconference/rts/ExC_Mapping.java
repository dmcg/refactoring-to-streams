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
public class ExC_Mapping {

    @Way
    public static List<Integer> oldWay(List<String> strings) {
        List<Integer> result = new ArrayList<>();
        for (String string : strings) {
            result.add(Integer.parseInt(string));
        }
        return result;
    }

    @Way
    public static List<Integer> newWay(List<String> strings) {
        return Collections.emptyList();
    }


    @Test
    public void test() {
        assertThat(f.apply(asList("2", "3", "5", "7")), equalTo(asList(2, 3, 5, 7)));
    }


    @Parameterized.Parameters public static Collection<?> tests() {
        return waysFrom(ExC_Mapping.class, Function.class);
    }

    @Parameterized.Parameter public Function<List<String>, List<Integer>> f;

}
