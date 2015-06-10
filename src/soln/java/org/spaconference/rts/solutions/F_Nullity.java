package org.spaconference.rts.solutions;

import com.google.common.collect.ImmutableMap;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Theories.class)
public class F_Nullity {
    public interface Lookup {
        int lookupInt(Map<String,String> properties, String propertyName, int defaultValue);
    }

    @DataPoint
    public static Lookup WITH_NULLS = (properties, propertyName, defaultValue) -> {
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

    @DataPoint
    public static Lookup STREAMS = (properties, propertyName, defaultValue) ->
            Optional.ofNullable(properties.get(propertyName))
                    .flatMap(F_Nullity::stringToOptionalInteger)
                    .orElse(defaultValue);

    private static Optional<Integer> stringToOptionalInteger(String v) {
        try {
            return Optional.of(new Integer(v));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Theory
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
