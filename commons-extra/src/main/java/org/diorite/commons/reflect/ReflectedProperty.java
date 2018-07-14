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

import org.diorite.commons.reflect.type.TypeToken;

import java.lang.reflect.Field;

/**
 * Class that connect {@link ReflectedGetter} and {@link ReflectedSetter},
 * allowing to get and set values using one object.
 *
 * @param <T> type of value.
 */
public interface ReflectedProperty<T> extends ReflectedGetter<T>, ReflectedSetter<T> {
    @Override
    @SuppressWarnings("unchecked")
    default T invokeWith(Object... args) {
        if (this.isStatic()) {
            if (args.length == 0) {
                return this.get(null);
            }
            if (args.length == 1) {
                this.set(null, (T) args[0]);
                return null;
            }
            throw new IllegalArgumentException("Expected none or single parameter.");
        }
        if (args.length == 1) {
            return this.get(args[0]);
        }
        if (args.length == 2) {
            this.set(args[0], (T) args[1]);
            return null;
        }
        throw new IllegalArgumentException("Expected object instance and none or one parameter.");

    }

    @Override
    ReflectedProperty<T> ensureAccessible();

    static <T> ReflectedProperty<T> fromField(Field field) {
        return new FieldAccessor<>(field);
    }

    static <T> ReflectedProperty<T> fromField(Field field, TypeToken<T> type) {
        if (! type.getType().equals(field.getGenericType())) {
            throw new IllegalArgumentException("Invalid type, requested: " + type + " but found: " + field.getGenericType());
        }
        return fromField(field);
    }

    static <T> ReflectedProperty<T> fromField(Field field, Class<T> type) {
        if (! type.equals(field.getType())) {
            throw new IllegalArgumentException("Invalid type, requested: " + type + " but found: " + field.getType());
        }
        return fromField(field);
    }
}
