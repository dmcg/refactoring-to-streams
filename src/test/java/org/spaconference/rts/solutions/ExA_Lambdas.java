package org.spaconference.rts.solutions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExA_Lambdas {

    @Way
    public static Function<String, Integer> anonymousClass() {
        return new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
    }

    @Way
    public static Function<String, Integer> lambda() {
        return s -> Integer.parseInt(s);
    }

    @Way
    public static Function<String, Integer> methodReference() {
        return Integer::parseInt;
    }

    @Test
    public void call_a_function(Supplier<Function<String, Integer>> f) {
        assertThat(f.get().apply("42"), equalTo(42));
    }

}
