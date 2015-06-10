package org.spaconference.rts.solutions;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExG_Nullity {

    public interface Lookup {
        int lookupInt(Map<String,String> properties, String propertyName, int defaultValue);
    }

    @Way
    public static int withNulls(Map<String,String> properties, String propertyName, int defaultValue) {
        String stringValue = properties.get(propertyName);
        if (stringValue != null) {
            Integer value = stringToNullableInteger(stringValue);
            if (value != null) {
                return value;
            }
        }
        return defaultValue;
    };

    private static Integer stringToNullableInteger(String value) {
        try {
            return new Integer(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Way
    public static int withOptional(Map<String,String> properties, String propertyName, int defaultValue) {
        return Optional.ofNullable(properties.get(propertyName))
                .flatMap(ExG_Nullity::stringToOptionalInteger)
                .orElse(defaultValue);
    }

    private static Optional<Integer> stringToOptionalInteger(String v) {
        try {
            return Optional.of(new Integer(v));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Test
    public void test(Lookup f) {
        Map<String,String> properties = ImmutableMap.of(
                "x", "10",
                "y", "20",
                "z", "cakes");

        assertThat("x", f.lookupInt(properties, "x", 99), equalTo(10));
        assertThat("y", f.lookupInt(properties, "y", 99), equalTo(20));
        assertThat("z - non-integer value", f.lookupInt(properties, "z", 99), equalTo(99));
        assertThat("a - nonexistent property", f.lookupInt(properties, "a", 99), equalTo(99));
    }
}
