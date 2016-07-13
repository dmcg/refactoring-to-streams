package to.refactoring.streams.solutions;

import org.junit.Test;
import org.junit.runner.RunWith;
import to.refactoring.runner.ExampleRunner;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static to.refactoring.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExA_ForEach {

    @Way
    public static void old_java_for_loop(List<String> things, PrintWriter writer) {
        for (int i = 0; i < things.size(); i++) {
            writer.println(things.get(i));
        }
    }

    @Way
    public static void java_5_for_loop(List<String> things, PrintWriter writer) {
        for (String thing : things) {
            writer.println(thing);
        }
    }

    @Way
    public static void foreach_with_lambda(List<String> things, PrintWriter writer) {
        things.forEach((x) -> writer.println(x));
    }

    @Way
    public static void foreach_with_method_reference(List<String> things, PrintWriter writer) {
        things.forEach(writer::println);
    }

    @Way
    public static void foreach_with_anonymous_inner_class(List<String> things, PrintWriter writer) {
        Consumer<String> println = new Consumer<String>() {
            public void accept(String s) {
                writer.println(s);
            }
        };
        things.forEach(println);
    }

    @Test
    public void test(Thing f) {
        Writer writer = new StringWriter();
        f.call(asList("one", "two", "three"), new PrintWriter(writer));
        assertThat(writer.toString(), equalTo("one\ntwo\nthree\n"));
    }

    public interface Thing {
        void call(Iterable<String> things, PrintWriter writer);
    }
}
