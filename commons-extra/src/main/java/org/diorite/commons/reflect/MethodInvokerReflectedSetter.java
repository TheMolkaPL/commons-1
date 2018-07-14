package org.diorite.commons.reflect;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Type;

final class MethodInvokerReflectedSetter<T> implements ReflectedSetter<T> {
    private final MethodInvoker<T> methodInvoker;

    public MethodInvokerReflectedSetter(MethodInvoker<T> methodInvoker) {this.methodInvoker = methodInvoker;}

    @Override
    public void set(@Nullable Object src, @Nullable T obj) {
        this.methodInvoker.invoke(src, obj);
    }

    @Override
    public MethodHandle getSetter() {
        return this.methodInvoker.getHandle();
    }

    @Override
    public MethodHandle getSetter(Lookup lookup) {
        return this.methodInvoker.getHandle(lookup);
    }

    @Override
    public ReflectedSetter<T> ensureAccessible() {
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
