package org.spaconference.rts;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Thingy.class)
public class ExA_RunningATest {

    @Way
    public static String oldWay() {
        return "That took longer than expected";
    }

    @Way
    public static String newWay() {
        return "something";
    }

    @Test
    public void returns_a_result(Supplier<String> f) {
        assertThat(f.get(), equalTo("That took longer than expected"));
    }

}
