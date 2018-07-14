package org.diorite.commons.reflect;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Type;

final class MethodInvokerReflectedGetter<T> implements ReflectedGetter<T> {
    private final MethodInvoker<T> methodInvoker;

    MethodInvokerReflectedGetter(MethodInvoker<T> methodInvoker) {this.methodInvoker = methodInvoker;}

    @Nullable
    @Override
    public T get(@Nullable Object src) {
        return this.methodInvoker.invoke(src);
    }

    @Override
    public MethodHandle getGetter() {
        return this.methodInvoker.getHandle();
    }

    @Override
    public MethodHandle getGetter(Lookup lookup) {
        return this.methodInvoker.getHandle(lookup);
    }

    @Override
    public ReflectedGetter<T> ensureAccessible() {
        this.methodInvoker.ensureAccessible();
        return this;
    }

    @Override
    public boolean existsIn(Object object) {
        return this.methodInvoker.existsIn(object);
    }

    @Override
    public Class<T> getType() {
        return this.methodInvoker.getReturnType();
    }

    @Override
    public Type getGenericType() {
        return this.methodInvoker.getGenericReturnType();
    }

    @Override
    public String getName() {
        return this.methodInvoker.getName();
    }

    @Override
    public int getModifiers() {
        return this.methodInvoker.getModifiers();
    }
}
