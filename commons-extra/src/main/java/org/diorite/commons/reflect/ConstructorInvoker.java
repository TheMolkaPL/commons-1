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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Class used to invoke previously prepared constructors,
 * constructors used by this class must be accessible.
 */
class ConstructorInvoker<T> implements ReflectedMethod<T> {
    private final Constructor<T> constructor;
    @Nullable
    private       MethodHandle   handle;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param constructor constructor to wrap.
     */
    @SuppressWarnings("unchecked")
    public ConstructorInvoker(Constructor<?> constructor) {
        this.constructor = (Constructor<T>) constructor;
    }

    /**
     * Invoke constructor and create new object.
     *
     * @param arguments arguments for constructor.
     *
     * @return new object.
     */
    @Override
    @Nonnull
    public T invokeWith(Object... arguments) {
        try {
            return this.constructor.newInstance(arguments);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException("Cannot invoke constructor " + this.constructor, e);
        }
        catch (InvocationTargetException e) {
            throw ExceptionUtils.sneakyThrow(e.getCause());
        }
        catch (Exception e) {
            throw ExceptionUtils.sneakyThrow(e);
        }
    }

    @Override
    public boolean existsIn(Object object) {
        return object.getClass() == this.constructor.getDeclaringClass();
    }

    @Override
    public String getName() {
        return this.constructor.getName();
    }

    @Override
    public boolean isConstructor() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Constructor<T> getExecutable() {
        return this.constructor;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public int getModifiers() {
        return this.constructor.getModifiers();
    }

    @Override
    public ConstructorInvoker<T> ensureAccessible() {
        ReflectionUtils.getAccess(this.constructor);
        return this;
    }

    @Override
    public MethodHandle getHandle() {
        if (this.handle != null) {
            return this.handle;
        }
        return this.getHandle(MethodHandles.lookup());
    }

    @Override
    public MethodHandle getHandle(Lookup lookup) {
        if (this.handle != null) {
            return this.handle;
        }
        try {
            return this.handle = lookup.unreflectConstructor(this.constructor);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public Class<T> getType() {
        return this.constructor.getDeclaringClass();
    }

    @Override
    public Type getGenericType() {
        return this.getType();
    }

    @Override
    public Class<T> getDeclaringClass() {
        return this.constructor.getDeclaringClass();
    }

    @Override
    public TypeVariable<Constructor<T>>[] getTypeParameters() {
        return this.constructor.getTypeParameters();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return this.constructor.getParameterTypes();
    }

    @Override
    public int getParameterCount() {
        return this.constructor.getParameterCount();
    }

    @Override
    public Type[] getGenericParameterTypes() {
        return this.constructor.getGenericParameterTypes();
    }

    @Override
    public Class<?>[] getExceptionTypes() {
        return this.constructor.getExceptionTypes();
    }

    @Override
    public Type[] getGenericExceptionTypes() {
        return this.constructor.getGenericExceptionTypes();
    }

    @Override
    public String toGenericString() {
        return this.constructor.toGenericString();
    }

    @Override
    public boolean isBridge() {
        return false;
    }

    @Override
    public boolean isVarArgs() {
        return this.constructor.isVarArgs();
    }

    @Override
    public boolean isSynthetic() {
        return this.constructor.isSynthetic();
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<? extends A> annotationClass) {
        return this.constructor.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.constructor.getDeclaredAnnotations();
    }

    @Override
    public Annotation[][] getParameterAnnotations() {
        return this.constructor.getParameterAnnotations();
    }

    @Override
    public AnnotatedType getAnnotatedReturnType() {
        return this.constructor.getAnnotatedReturnType();
    }

    @Override
    public Parameter[] getParameters() {
        return this.constructor.getParameters();
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<? extends A> annotationClass) {
        return this.constructor.getAnnotationsByType(annotationClass);
    }

    @Override
    public AnnotatedType getAnnotatedReceiverType() {
        return this.constructor.getAnnotatedReceiverType();
    }

    @Override
    public AnnotatedType[] getAnnotatedParameterTypes() {
        return this.constructor.getAnnotatedParameterTypes();
    }

    @Override
    public AnnotatedType[] getAnnotatedExceptionTypes() {
        return this.constructor.getAnnotatedExceptionTypes();
    }

    @Override
    public boolean isAccessible() {
        return this.constructor.isAccessible();
    }

    @Override
    public boolean canAccess(Object obj) {
        return this.constructor.canAccess(obj);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.constructor.isAnnotationPresent(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.constructor.getAnnotations();
    }

    @Override
    public <A extends Annotation> A getDeclaredAnnotation(Class<? extends A> annotationClass) {
        return this.constructor.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<? extends A> annotationClass) {
        return this.constructor.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public ReflectedSetter<T> asSetter() {
        throw new IllegalStateException("Can't create setter from constructor");
    }

    @Override
    public ReflectedGetter<T> asGetter() {
        throw new IllegalStateException("Can't create getter from constructor");
    }

    @Override
    public String toString() {
        return this.constructor.toString();
    }
}
