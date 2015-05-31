package org.spaconference.rts;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.function.LongFunction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Theories.class)
public class ExB_Summing {

    @DataPoint
    public static Long2Long FOR_LOOP = limit -> {
        long result = 0;
        for (long i = 0; i < limit; i++) {
            result += i;
        }
        return result;
    };

    @DataPoint
    public static Long2Long STREAM = limit -> { return 0L; };


    @Theory
    public void is_0_for_limit_0(Long2Long sum) {
        assertThat(sum.apply(0), equalTo(0L));
    }

    @Theory
    public void is_3_for_limit_3(Long2Long sum) {
        assertThat(sum.apply(3), equalTo(3L));
    }

    @Theory
    public void is_6_for_limit_4(Long2Long sum) {
        assertThat(sum.apply(4), equalTo(6L));
    }

    @Theory
    public void is_big_for_MAX_VALUE(Long2Long sum) {
        assertThat(sum.apply(Integer.MAX_VALUE), equalTo(2305843005992468481L));
    }

    static interface Long2Long extends LongFunction<Long> {}

}
