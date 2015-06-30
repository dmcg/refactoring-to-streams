package org.spaconference.rts.exercises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.function.ToIntFunction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExH_Summing {

    @Way
    public static int oldWay(int[] ints) {
        int result = 0;
        for (int i : ints) {
            result += i;
        }
        return result;
    }

    @Test
    public void sums_array_elements(ToIntFunction<int[]> f) {
        assertThat(f.applyAsInt(new int[]{1, 2, 3, 4, 5}), equalTo(15));
    }

    @Test
    public void is_0_for_empty_array(ToIntFunction<int[]> f) {
        assertThat(f.applyAsInt(new int[0]), equalTo(0));
    }
}
