package org.spaconference.rts;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class Thingy extends BlockJUnit4ClassRunner {
    public Thingy(Class<?> klass) throws InitializationError {
        super(klass);
    }

    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation,
                                                  boolean isStatic, List<Throwable> errors) {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
        methods.stream().forEach(e -> e.validatePublicVoid(isStatic, errors));
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> testMethods = super.computeTestMethods();
        List<FrameworkMethod> result = new ArrayList<>();
        for (FrameworkMethod testMethod : testMethods) {
            for (Object[] args : testParameters(testMethod.getMethod().getParameters()[0].getType())) {
                result.add(new TestMethodWithParams(testMethod, args, "thing"));
            }
        }
        return result;
    }

    private Object[][] testParameters(Class<?> wayClass) {
        List<?> ways = Ways.waysFrom(getTestClass().getJavaClass(), wayClass);
        Object[][] result = new Object[ways.size()][1];
        int i = 0;
        for (Object way : ways) {
            result[i++][0] = way;
        }
        return result;
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
    }
}
