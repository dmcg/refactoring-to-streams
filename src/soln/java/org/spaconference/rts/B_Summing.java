package org.spaconference.rts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class B_Summing {

    @Parameterized.Parameters public static Collection<LongUnaryOperator> tests() {
        return asList(B_Summing::forLoop, B_Summing::stream);
    }

    @Parameterized.Parameter public LongUnaryOperator sumUpTo;

    public static long forLoop(long limit) {
        long result = 0;
        for (long i = 0; i < limit; i++) {
            result += i;
        }
        return result;
    }

    public static long stream(long limit) {
        return LongStream.range(0, limit).sum();
    }

    @Test
    public void is_0_for_limit_0() {
        assertThat(sumUpTo.applyAsLong(0), equalTo(0L));
    }

    @Test
    public void is_3_for_limit_3() {
        assertThat(sumUpTo.applyAsLong(3), equalTo(3L));
    }

    @Test
    public void is_6_for_limit_4() {
        assertThat(sumUpTo.applyAsLong(4), equalTo(6L));
    }

    @Test
    public void is_big_for_MAX_VALUE() {
        assertThat(sumUpTo.applyAsLong(Integer.MAX_VALUE), equalTo(2305843005992468481L));
    }

}
