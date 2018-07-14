/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.reflect.type;

import org.diorite.commons.reflect.ReflectionUtils;

import java.lang.reflect.Type;

/**
 * Represent generic type as type token, this can be subclassed so java will save generic type of super class, so type can be found at
 * runtime. <br>
 * Token can be also created via existing type and factory method.<br>
 * For example, to create a type token for {@code List<String>}, you need to create an empty anonymous inner class: <br>
 * {@code TypeToken<List<String>> list = new TypeToken<List<String>>() {};} <br>
 *
 * To create token with wildcard ({@code List<? extends String>}) {@link TypeBuilder} or {@link TypeParser} must be used instead, and
 * then type can be wrapped into token.
 */
public class TypeToken<T> {
    final Class<? super T> rawType;
    final Type             type;
    final int              hashCode;

    /**
     * Constructs a new type literal. Derives represented class from type
     * parameter.
     *
     * <p>Clients create an empty anonymous subclass. Doing so embeds the type
     * parameter in the anonymous class's type hierarchy so we can reconstitute it
     * at runtime despite erasure.
     */
    @SuppressWarnings("unchecked")
    protected TypeToken() {
        this.type = this.getClass().getGenericSuperclass();
        this.rawType = (Class<? super T>) ReflectionUtils.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    @SuppressWarnings("unchecked")
    TypeToken(Type type) {
        this.type = type;
        this.rawType = (Class<? super T>) ReflectionUtils.getRawType(type);
        this.hashCode = this.type.hashCode();
    }

    /**
     * Returns raw type class of this type token.
     *
     * @return raw type class of this type token.
     */
    public final Class<? super T> getRawType() {
        return this.rawType;
    }

    /**
     * Returns underlying {@link Type}.
     *
     * @return underlying {@link Type}.
     */
    public final Type getType() {
        return this.type;
    }

    /**
     * Checks if object od given type token can be assigned to variable of this type.
     *
     * @param token type token of object that you want to assign.
     *
     * @return true if types are compatible.
     *
     * @see ReflectionUtils#isAssignable(Type, Type)
     */
    public final boolean isAssignableFrom(TypeToken<?> token) {
        return this.isAssignableFrom(token.type);
    }

    /**
     * Checks if object od given type can be assigned to variable of this type.
     *
     * @param type type of object that you want to assign.
     *
     * @return true if types are compatible.
     *
     * @see ReflectionUtils#isAssignable(Type, Type)
     */
    public final boolean isAssignableFrom(Type type) {
        return ReflectionUtils.isAssignable(type, this.type);
    }

    @Override
    public final int hashCode() {
        return this.hashCode;
    }

    @Override
    public final boolean equals(Object o) {
        return (o instanceof TypeToken<?>) && this.type.equals(((TypeToken<?>) o).type);
    }

    @Override
    public final String toString() {
        return this.type.toString();
    }

    /**
     * Returns type literal for the given {@code Type} instance.
     *
     * @param type type instance.
     *
     * @return type literal for the given {@code Type} instance.
     */
    public static TypeToken<?> get(Type type) {
        return new TypeToken<>(type);
    }

    /**
     * Returns type literal for the given {@code Class} instance.
     *
     * @param type class instance.
     *
     * @return type literal for the given {@code Class} instance.
     */
    public static <T> TypeToken<T> get(Class<T> type) {
        return new TypeToken<>(type);
    }
}
