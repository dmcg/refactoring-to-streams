package org.spaconference.rts;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Ways {
    public static <T> Collection<? extends T> waysFrom(Class<?> donorClass, Class<? extends T> wayClass) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        List<T> result = new ArrayList<>();
        for (Method method : donorClass.getDeclaredMethods()) {
            Way annotation = method.getDeclaredAnnotation(Way.class);
            if (annotation != null) {
                try {
                    MethodHandle methodHandle = lookup.unreflect(method);
                    result.add(MethodHandleProxies.asInterfaceInstance(wayClass, methodHandle));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
