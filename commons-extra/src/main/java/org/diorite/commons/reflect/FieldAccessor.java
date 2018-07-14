/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.commons.reflect;

import org.diorite.commons.ExceptionUtils;
import org.diorite.commons.function.function.ExceptionalBiConsumer;
import org.diorite.commons.function.function.ExceptionalFunction;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Class used to access previously prepared fields,
 * fields used by this class must be accessible.
 *
 * @param <T> type of field.
 */
class FieldAccessor<T> implements ReflectedProperty<T> {
    protected final Field field;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param field field to wrap.
     */
    public FieldAccessor(Field field) {
        this.field = field;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public T get(@Nullable Object target) {
        try {
            ExceptionalFunction handle = this.getterHandleInternal;
            if (handle != null) {
                return (T) handle.applyOrThrow(target);
            }
            return (T) this.field.get(target);
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Cannot access reflection.", e);
        }
        catch (Throwable e) {
            throw ExceptionUtils.sneakyThrow(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getType() {
        return (Class<T>) this.field.getType();
    }

    @Override
    public Type getGenericType() {
        return this.field.getGenericType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(@Nullable Object target, @Nullable Object value) {
        try {
            ExceptionalBiConsumer handle = this.setterHandleInternal;
            if (handle != null) {
                handle.acceptOrThrow(target, value);
            }
            else {
                this.field.set(target, value);
            }
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Cannot access reflection.", e);
        }
        catch (Throwable e) {
            throw ExceptionUtils.sneakyThrow(e);
        }
    }

    @Nullable
    @Override
    public T invokeWith(Object... args) throws IllegalArgumentException {
        if (this.isStatic()) {
            if (args.length == 0) {
                return this.get(null);
            }
            if (args.length == 1) {
                this.set(null, args[0]);
                return null;
            }
            throw new IllegalArgumentException("Expected none or single parameter.");
        }
        if (args.length == 1) {
            return this.get(args[0]);
        }
        if (args.length == 2) {
            this.set(args[0], args[1]);
            return null;
        }
        throw new IllegalArgumentException("Expected object instance and none or one parameter.");
    }

    @Override
    public String getName() {
        return this.field.getName();
    }

    @Override
    public int getModifiers() {
        return this.field.getModifiers();
    }

    @Override
    public FieldAccessor<T> ensureAccessible() {
        ReflectionUtils.getAccess(this.field);
        return this;
    }

    @Override
    public boolean existsIn(Object target) {
        return ReflectionUtils.isAssignable(target.getClass(), this.field.getDeclaringClass());
    }

    @Nullable
    private MethodHandle          getterHandle;
    @Nullable
    private MethodHandle          setterHandle;
    @Nullable
    private ExceptionalFunction   getterHandleInternal;
    @Nullable
    private ExceptionalBiConsumer setterHandleInternal;

    @Override
    public MethodHandle getGetter() {
        if (this.getterHandle != null) {
            return this.getterHandle;
        }
        return this.getGetter(MethodHandles.lookup());
    }

    @Override
    public MethodHandle getGetter(Lookup lookup) {
        if (this.getterHandle != null) {
            return this.getterHandle;
        }
        try {
            MethodHandle handle = lookup.unreflectGetter(this.field);
            if (this.isStatic()) {
                MethodHandle internal = handle.asType(MethodType.methodType(Object.class));
                //noinspection Convert2MethodRef
                this.getterHandleInternal = obj -> internal.invokeExact();
            }
            else {
                MethodHandle internal = handle.asType(MethodType.methodType(Object.class, Object.class));
                //noinspection Convert2MethodRef
                this.getterHandleInternal = obj -> internal.invokeExact(obj);
            }
            return this.getterHandle = handle;
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public MethodHandle getSetter() {
        if (this.setterHandle != null) {
            return this.setterHandle;
        }
        return this.getSetter(MethodHandles.lookup());
    }

    @Override
    public MethodHandle getSetter(Lookup lookup) {
        if (this.setterHandle != null) {
            return this.setterHandle;
        }
        try {
            MethodHandle handle = lookup.unreflectSetter(this.field);
            if (this.isStatic()) {
                MethodHandle internal = handle.asType(MethodType.methodType(void.class, Object.class));
                this.setterHandleInternal = (obj, v) -> internal.invokeExact(v);
            }
            else {
                MethodHandle internal = handle.asType(MethodType.methodType(void.class, Object.class, Object.class));
                this.setterHandleInternal = (obj, v) -> internal.invokeExact(obj, v);
            }
            return this.setterHandle = handle;
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public String toString() {
        return this.field.toString();
    }
}
