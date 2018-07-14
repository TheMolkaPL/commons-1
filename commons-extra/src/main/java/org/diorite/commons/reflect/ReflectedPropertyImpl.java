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

import org.diorite.commons.lazy.LazyValue;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Class that connect {@link ReflectedGetter} and {@link ReflectedSetter},
 * allowing to get and set values using one object.
 *
 * @param <T> type of value.
 */
class ReflectedPropertyImpl<T> implements ReflectedProperty<T> {
    @Nullable
    private final ReflectedGetter<T> getter;
    @Nullable
    private final ReflectedSetter<T> setter;

    /**
     * Construct new reflect element using given getter and setter instance.
     *
     * @param getter getter for value.
     * @param setter setter for value.
     */
    public ReflectedPropertyImpl(@Nullable ReflectedGetter<T> getter, @Nullable ReflectedSetter<T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public T get(@Nullable Object src) {
        if (this.getter == null) {
            throw new IllegalStateException("Getter not provided.");
        }
        return this.getter.get(src);
    }

    private final LazyValue<String> name = LazyValue.lazy(this::initName);

    @Override
    public String getName() {
        return Objects.requireNonNull(this.name.get());
    }

    private String initName() {
        if (this.getter instanceof FieldAccessor) {
            return this.getter.getName();
        }
        if (this.setter instanceof FieldAccessor) {
            return this.setter.getName();
        }
        String baseName;
        if (this.getter != null) {
            baseName = this.getter.getName();
            if (baseName.startsWith("get")) {
                char first = baseName.charAt(3);
                baseName.substring(4);
                return Character.toLowerCase(first) + baseName;
            }
            if (baseName.startsWith("is")) {
                char first = baseName.charAt(2);
                baseName.substring(3);
                return Character.toLowerCase(first) + baseName;
            }
            if (baseName.startsWith("has")) {
                char first = baseName.charAt(3);
                baseName.substring(4);
                return Character.toLowerCase(first) + baseName;
            }
            return baseName;
        }
        if (this.setter != null) {
            baseName = this.setter.getName();
            if (baseName.startsWith("set")) {
                char first = baseName.charAt(3);
                baseName.substring(4);
                return Character.toLowerCase(first) + baseName;
            }
            return baseName;
        }
        throw new IllegalStateException("Both setter and getter is null.");
    }

    @Override
    public void set(@Nullable Object src, @Nullable T obj) {
        if (this.setter == null) {
            throw new IllegalStateException("Setter not provided.");
        }
        this.setter.set(src, obj);
    }

    @Override
    public MethodHandle getSetter() {
        if (this.setter == null) {
            throw new IllegalStateException("Setter not provided.");
        }
        return this.setter.getSetter();
    }

    @Override
    public MethodHandle getSetter(Lookup lookup) {
        if (this.setter == null) {
            throw new IllegalStateException("Setter not provided.");
        }
        return this.setter.getSetter(lookup);
    }

    @Override
    public MethodHandle getGetter() {
        if (this.getter == null) {
            throw new IllegalStateException("Getter not provided.");
        }
        return this.getter.getGetter();
    }

    @Override
    public MethodHandle getGetter(Lookup lookup) {
        if (this.getter == null) {
            throw new IllegalStateException("Getter not provided.");
        }
        return this.getter.getGetter(lookup);
    }

    @Override
    public boolean existsIn(Object object) {
        if (this.getter != null) {
            return this.getter.existsIn(object);
        }
        if (this.setter != null) {
            return this.setter.existsIn(object);
        }
        throw new IllegalStateException("Missing getter/setter instance");
    }

    @Override
    public Class<T> getType() {
        if (this.getter != null) {
            return this.getter.getType();
        }
        if (this.setter != null) {
            return this.setter.getType();
        }
        throw new IllegalStateException("Missing getter/setter instance");
    }

    @Override
    public Type getGenericType() {
        if (this.getter != null) {
            return this.getter.getGenericType();
        }
        if (this.setter != null) {
            return this.setter.getGenericType();
        }
        throw new IllegalStateException("Missing getter/setter instance");
    }

    @Override
    public int getModifiers() {
        if (this.getter != null) {
            return this.getter.getModifiers();
        }
        if (this.setter != null) {
            return this.setter.getModifiers();
        }
        throw new IllegalStateException("Missing getter/setter instance");
    }

    @Override
    public ReflectedPropertyImpl<T> ensureAccessible() {
        if (this.getter != null) {
            this.getter.ensureAccessible();
        }
        if (this.setter != null) {
            this.setter.ensureAccessible();
        }
        return this;
    }
}
