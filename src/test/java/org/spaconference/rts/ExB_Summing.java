package org.spaconference.rts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.function.LongUnaryOperator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class ExB_Summing {

    @Way
    public static long oldWay(long limit) {
        long result = 0;
        for (long i = 0; i < limit; i++) {
            result += i;
        }
        return result;
    }

    @Way
    public static long newWay(long limit) {
        return 0;
    }

    @Test
    public void is_0_for_limit_0() {
        assertThat(f.applyAsLong(0), equalTo(0L));
    }

    @Test
    public void is_3_for_limit_3() {
        assertThat(f.applyAsLong(3), equalTo(3L));
    }

    @Test
    public void is_6_for_limit_4() {
        assertThat(f.applyAsLong(4), equalTo(6L));
    }

    @Test
    public void is_big_for_MAX_VALUE() {
        assertThat(f.applyAsLong(Integer.MAX_VALUE), equalTo(2305843005992468481L));
    }

    @Parameterized.Parameters public static Collection<?> tests() {
        return Ways.waysFrom(ExB_Summing.class, LongUnaryOperator.class);
    }

    @Parameterized.Parameter public LongUnaryOperator f;

}
