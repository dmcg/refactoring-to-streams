package org.spaconference.rts.solutions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExI_SummingRange {

    @Way
    public static long oldWay(long limit) {
        long result = 0;
        for (long i = 0; i < limit; i++) {
            result += i;
        }
        return result;
    }

    @Way
    public static long step1_forEach(long limit) {
        final long[] result = {0};
        LongStream.range(0, limit).forEach(i -> result[0] += i);
        return result[0];
    }

    @Way
    public static long step2_sum(long limit) {
        return LongStream.range(0, limit).sum();
    }

    @Way
    public static long step3_parallel(long limit) {
        return LongStream.range(0, limit).parallel().sum();
    }


    @Test
    public void is_0_for_limit_0(LongUnaryOperator f) {
        assertThat(f.applyAsLong(0), equalTo(0L));
    }

    @Test
    public void is_6_for_limit_4(LongUnaryOperator f) {
        assertThat(f.applyAsLong(4), equalTo(6L));
    }

    @Test
    public void is_big_for_MAX_VALUE(LongUnaryOperator f) {
        assertThat(f.applyAsLong(Integer.MAX_VALUE), equalTo(2305843005992468481L));
    }

}
