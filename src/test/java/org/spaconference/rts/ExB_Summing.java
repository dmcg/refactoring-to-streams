package org.spaconference.rts;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.function.IntFunction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Theories.class)
public class ExB_Summing {

    @DataPoint
    public static IntFunction<Integer> FOR_LOOP = limit -> {
        int result = 0;
        for (int i = 0; i < limit; i++) {
            result += i;
        }
        return result;
    };

    @DataPoint
    public static IntFunction<Integer> STREAM = null;


    @Theory
    public void is_0_for_limit_0(IntFunction<Integer> sum) {
        assertThat(sum.apply(0), equalTo(0));
    }

    @Theory
    public void is_3_for_limit_3(IntFunction<Integer> sum) {
        assertThat(sum.apply(3), equalTo(3));
    }

    @Theory
    public void is_6_for_limit_4(IntFunction<Integer> sum) {
        assertThat(sum.apply(4), equalTo(6));
    }
}
