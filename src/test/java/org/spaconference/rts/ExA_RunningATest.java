package org.spaconference.rts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class ExA_RunningATest {


    @Parameterized.Parameters public static Collection<?> tests() throws IllegalAccessException {
        return Ways.waysFrom(ExA_RunningATest.class, Supplier.class);
    }

    @Parameterized.Parameter public Supplier<String> f;

    @Way
    public static String oldWay() {
        return "That took longer than expected";
    }

    @Way
    public static String newWay() {
        return "something";
    }

    @Test
    public void returns_a_result() {
        assertThat(f.get(), equalTo("That took longer than expected"));
    }

}
