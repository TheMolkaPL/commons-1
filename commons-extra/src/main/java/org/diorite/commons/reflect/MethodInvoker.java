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

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * Class used to access/invoke previously prepared methods,
 * methods used by this class must be accessible.
 */
class MethodInvoker<T> implements ReflectedMethod<T> {
    protected final Method       method;
    @Nullable
    private         MethodHandle handle;

    /**
     * Construct new invoker for given method, it don't check its accessible status.
     *
     * @param method method to wrap.
     */
    public MethodInvoker(Method method) {
        this.method = method;
    }

    /**
     * Invoke method and create new object.
     *
     * @param target target object, use null for static fields.
     * @param arguments arguments for method.
     *
     * @return method invoke result.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public T invoke(@Nullable Object target, Object... arguments) {
        try {
            return (T) this.method.invoke(target, arguments);
        }
        catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException("Cannot invoke method " + this.method, e);
        }
        catch (InvocationTargetException e) {
            throw ExceptionUtils.sneakyThrow(e.getCause());
        }
        catch (Throwable e) {
            throw ExceptionUtils.sneakyThrow(e);
        }
    }

    @Override
    public boolean isConstructor() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Method getExecutable() {
        return this.method;
    }

    @Nullable
    @Override
    public T invokeWith(Object... args) throws IllegalArgumentException {
        if (this.isStatic()) {
            return this.invoke(null, args);
        }
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing object instance!");
        }
        Object inst = args[0];
        if (args.length == 1) {
            return this.invoke(inst);
        }
        Object[] newArgs = new Object[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        return this.invoke(inst, newArgs);
    }

    @Override
    public boolean existsIn(Object target) {
        return ReflectionUtils.isAssignable(target.getClass(), this.method.getDeclaringClass());
    }

    @Override
    public Class<T> getType() {
        return this.getReturnType();
    }

    @Override
    public Type getGenericType() {
        return this.getGenericReturnType();
    }

    @Override
    public String getName() {
        return this.method.getName();
    }

    @Override
    public int getModifiers() {
        return this.method.getModifiers();
    }

    @Override
    public MethodInvoker<T> ensureAccessible() {
        ReflectionUtils.getAccess(this.method);
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
            return this.handle = lookup.unreflect(this.method);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    @Override
    public String toString() {
        return this.method.toString();
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.method.getDeclaringClass();
    }

    @Override
    public TypeVariable<Method>[] getTypeParameters() {
        return this.method.getTypeParameters();
    }

    /**
     * Returns a {@code Class} object that represents the formal return type
     * of the method represented by this {@code Method} object.
     *
     * @return the return type for the method this object represents
     */
    @SuppressWarnings("unchecked")
    public Class<T> getReturnType() {
        return (Class<T>) this.method.getReturnType();
    }

    /**
     * Returns a {@code Type} object that represents the formal return
     * type of the method represented by this {@code Method} object.
     *
     * <p>If the return type is a parameterized type,
     * the {@code Type} object returned must accurately reflect
     * the actual type parameters used in the source code.
     *
     * <p>If the return type is a type variable or a parameterized type, it
     * is created. Otherwise, it is resolved.
     *
     * @return a {@code Type} object that represents the formal return type of the underlying  method
     *
     * @throws GenericSignatureFormatError if the generic method signature does not conform to the format specified in <cite>The
     *         Java&trade; Virtual Machine Specification</cite>
     * @throws TypeNotPresentException if the underlying method's return type refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if the underlying method's return typed refers to a parameterized type that cannot be
     *         instantiated for any reason
     */
    public Type getGenericReturnType() {
        return this.method.getGenericReturnType();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return this.method.getParameterTypes();
    }

    @Override
    public int getParameterCount() {
        return this.method.getParameterCount();
    }

    @Override
    public Type[] getGenericParameterTypes() {
        return this.method.getGenericParameterTypes();
    }

    @Override
    public Class<?>[] getExceptionTypes() {
        return this.method.getExceptionTypes();
    }

    @Override
    public Type[] getGenericExceptionTypes() {
        return this.method.getGenericExceptionTypes();
    }

    @Override
    public String toGenericString() {
        return this.method.toGenericString();
    }

    @Override
    public boolean isBridge() {
        return this.method.isBridge();
    }

    @Override
    public boolean isVarArgs() {
        return this.method.isVarArgs();
    }

    @Override
    public boolean isSynthetic() {
        return this.method.isSynthetic();
    }

    @Override
    public boolean isDefault() {
        return this.method.isDefault();
    }

    @Override
    public Object getDefaultValue() {
        return this.method.getDefaultValue();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<? extends A> annotationClass) {
        return this.method.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.method.getDeclaredAnnotations();
    }

    @Override
    public Annotation[][] getParameterAnnotations() {
        return this.method.getParameterAnnotations();
    }

    @Override
    public AnnotatedType getAnnotatedReturnType() {
        return this.method.getAnnotatedReturnType();
    }

    @Override
    public Parameter[] getParameters() {
        return this.method.getParameters();
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<? extends A> annotationClass) {
        return this.method.getAnnotationsByType(annotationClass);
    }

    @Override
    public AnnotatedType getAnnotatedReceiverType() {
        return this.method.getAnnotatedReceiverType();
    }

    @Override
    public AnnotatedType[] getAnnotatedParameterTypes() {
        return this.method.getAnnotatedParameterTypes();
    }

    @Override
    public AnnotatedType[] getAnnotatedExceptionTypes() {
        return this.method.getAnnotatedExceptionTypes();
    }

    @Override
    public boolean isAccessible() {
        return this.method.isAccessible();
    }

    @Override
    public boolean canAccess(Object obj) {
        return this.method.canAccess(obj);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.method.isAnnotationPresent(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.method.getAnnotations();
    }

    @Override
    public <A extends Annotation> A getDeclaredAnnotation(Class<? extends A> annotationClass) {
        return this.method.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<? extends A> annotationClass) {
        return this.method.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public ReflectedSetter<T> asSetter() {
        if (this.getParameterCount() != 1) {
            throw new IllegalStateException("Expected 1 parameter but found: " + Arrays.toString(this.getParameterTypes()));
        }
        return new MethodInvokerReflectedSetter<>(this);
    }

    @Override
    public ReflectedGetter<T> asGetter() {
        if (this.getParameterCount() != 0) {
            throw new IllegalStateException("Expected 0 parameters but found: " + Arrays.toString(this.getParameterTypes()));
        }
        return new MethodInvokerReflectedGetter<>(this);
    }

}
