package org.spaconference.rts.solutions;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.*;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


public class ExampleRunner extends BlockJUnit4ClassRunner {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface Way {
    }

    public ExampleRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
        getTestClass().getAnnotatedMethods(annotation).stream().forEach(e -> e.validatePublicVoid(isStatic, errors));
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> testMethods = super.computeTestMethods();
        if (testMethods.isEmpty())
            return testMethods;

        Map<Method, Object[]> testParameters = annotatedMethodsAsFunctions(getTestClass().getJavaClass(), testMethods.get(0).getMethod().getParameters()[0].getType(), Way.class);
        return testMethods.stream().flatMap(m -> testMethodWithParamsFor(m, testParameters)).collect(toList());
    }

    private Stream<TestMethodWithParams> testMethodWithParamsFor(FrameworkMethod testMethod, Map<Method, Object[]> testParameters) {
        return testParameters.entrySet().stream().map(e -> new TestMethodWithParams(testMethod, e.getValue(), e.getKey().getName()));
    }

    private Map<Method, Object[]> annotatedMethodsAsFunctions(Class<?> donorClass, Class<?> interfaceClass, Class<? extends Annotation> annotationClass) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        return Arrays.stream(donorClass.getDeclaredMethods())
                .filter(hasAnnotation(annotationClass))
                .collect(toMap(identity(), method -> new Object[]{functionFor(method, interfaceClass, lookup)}));
    }

    private Predicate<Method> hasAnnotation(Class<? extends Annotation> annotationClass) {
        return method -> method.getDeclaredAnnotation(annotationClass) != null;
    }

    private <T> T functionFor(Method method, Class<? extends T> interfaceClass, MethodHandles.Lookup lookup) {
        try {
            return MethodHandleProxies.asInterfaceInstance(interfaceClass, lookup.unreflect(method));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <K,V> Entry<K,V> mapEntry(K key, V value) {
        return new SimpleImmutableEntry<>(key, value);
    }

    private class TestMethodWithParams extends FrameworkMethod {
        private final Object[] params;
        private final String paramsName;

        public TestMethodWithParams(FrameworkMethod testMethod, Object[] params, String paramsName) {
            super(testMethod.getMethod());
            this.params = params;
            this.paramsName = paramsName;
        }

        @Override
        public Object invokeExplosively(Object target, Object... ignored) throws Throwable {
            return super.invokeExplosively(target, this.params);
        }

        @Override
        public String getName() {
            return super.getName() + " " + paramsName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            TestMethodWithParams that = (TestMethodWithParams) o;
            return paramsName.equals(that.paramsName);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + paramsName.hashCode();
            return result;
        }
    }
}
