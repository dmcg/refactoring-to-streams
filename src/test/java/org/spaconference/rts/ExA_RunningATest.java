package org.spaconference.rts;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Theories.class)
public class ExA_RunningATest {

    @DataPoint
    public static Supplier<String> SUPPLIER_AS_CLASS = new Supplier<String>() {
        public String get() {
            return "That took longer than expected";
        }
    };

    @DataPoint
    public static Supplier<String> SUPPLIER_AS_LAMBDA = null;


    @Theory
    public void returns_a_result(Supplier<String> f) {
        assertThat(f.get(), equalTo("That took longer than expected"));
    }

}
