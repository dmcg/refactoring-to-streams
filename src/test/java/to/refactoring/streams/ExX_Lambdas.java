package to.refactoring.streams;

import org.junit.Test;
import org.junit.runner.RunWith;
import to.refactoring.runner.ExampleRunner;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static to.refactoring.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExX_Lambdas {

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
        return new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
    }

    @Way
    public static Function<String, Integer> methodReference() {
        return new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
    }

    @Test
    public void call_a_function(Supplier<Function<String, Integer>> f) {
        assertThat(f.get().apply("42"), equalTo(42));
    }

}
