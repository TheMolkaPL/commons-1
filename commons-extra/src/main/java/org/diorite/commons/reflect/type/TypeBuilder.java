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

import org.diorite.commons.object.Builder;
import org.diorite.commons.reflect.ReflectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Advanced builder for {@link Type}
 */

public abstract class TypeBuilder implements Builder<Type> {
    TypeBuilder() {}

    /**
     * Returns {@link TypeVariable} from given {@link GenericDeclaration} with given name.
     *
     * @param of generic declaration, like a class, method or constructor.
     * @param name name of type variable to get. (Like K and V in {@literal Map<K, V>})
     * @param <X> type of generic declaration.
     *
     * @return {@link TypeVariable} from given {@link GenericDeclaration} with given name or null.
     *
     * @throws IllegalStateException if declaration contains more than one type variable of that name.
     * @see ReflectionUtils#typeVariableOf(GenericDeclaration, String)
     */
    @Nullable
    public static <X extends GenericDeclaration> TypeVariable<X> typeVariableOf(X of, String name) throws IllegalStateException {
        return ReflectionUtils.typeVariableOf(of, name);
    }

    /**
     * Returns {@link TypeVariable} from given {@link GenericDeclaration} on given position, might throw {@link IndexOutOfBoundsException}
     *
     * @param of generic declaration, like a class, method or constructor.
     * @param index index of type variable to get.
     * @param <X> type of generic declaration.
     *
     * @return {@link TypeVariable} from given {@link GenericDeclaration}  on given position.
     *
     * @see ReflectionUtils#typeVariableOf(GenericDeclaration, int)
     */
    public static <X extends GenericDeclaration> TypeVariable<X> typeVariableOf(X of, int index) {
        return ReflectionUtils.typeVariableOf(of, index);
    }

    /**
     * Returns wildcard type. ({@literal <?>})
     *
     * @return wildcard type.
     */
    public static WildcardType wildcard() {
        return WildcardTypeImpl.wildcard();
    }

    /**
     * Creates wildcard type as {@literal <? super type>}<br>
     * Examples: <br>
     * {@literal String -> <? super String>} <br>
     * {@literal Map<?, ? extends Number> -> <? super Map<?, ? extends Number>>} <br>
     *
     * @param type type to use as super argument.
     *
     * @return created wildcard type.
     */
    public static WildcardType wildcardSuper(Type type) {
        return WildcardTypeImpl.withSuper(type);
    }

    /**
     * Creates wildcard type as {@literal <? extends type>}<br>
     * Examples: <br>
     * {@literal String -> <? extends String>} <br>
     * {@literal Map<?, ? extends Number> -> <? extends Map<?, ? extends Number>>} <br>
     *
     * @param type type to use as extends argument.
     *
     * @return created wildcard type.
     */
    public static WildcardType wildcardExtends(Type type) {
        return WildcardTypeImpl.withExtends(type);
    }

    /**
     * Returns parametrized type where all parameters are wildcards.<br>
     * Examples: <br>
     * {@literal Map -> Map<?, ?>} <br>
     * {@literal List -> List<?>} <br>
     *
     * @param type raw type.
     *
     * @return parametrized wildcard type.
     */
    public static ParameterizedType asWildcard(Class<?> type) {
        return parameterized(type).build();
    }

    /**
     * Returns parametrized type where all parameters are wildcards. <br>
     * Examples: <br>
     * {@literal Map<String, ?> -> Map<?, ?>} <br>
     * {@literal Map<String, Number> -> Map<?, ?>} <br>
     *
     * @param type parametrized type.
     *
     * @return parametrized wildcard type.
     */
    public static ParameterizedType asWildcard(ParameterizedType type) {
        boolean isWildcard = true;
        Type[] actualTypeArguments = type.getActualTypeArguments();
        if (actualTypeArguments.length == 0) {
            return type;
        }
        for (Type t: actualTypeArguments) {
            if (! t.equals(wildcard())) {
                isWildcard = false;
                break;
            }
        }
        if (isWildcard) {
            return type;
        }
        ParameterizedTypeBuilder<Object> builder = parameterized(type);
        int params = builder.toFill.size();
        for (int i = 0; i < params; i++) {
            builder.with(wildcard());
        }
        return builder.build();
    }

    /**
     * Creates {@link ParameterizedTypeBuilder} from {@link ParameterizedType} so parameters can be changed.
     *
     * @param type type to use in builder.
     * @param <T> raw type.
     *
     * @return instance of parameterized builder.
     */
    public static <T> ParameterizedTypeBuilder<T> parameterized(ParameterizedType type) {
        return new ParameterizedTypeBuilder<>(type);
    }

    /**
     * Creates {@link ParameterizedTypeBuilder} from given class, class can not be an array and must have some type parameters.
     *
     * @param clazz type to use in builder.
     * @param <T> type of class.
     *
     * @return instance of parameterized builder.
     *
     * @throws IllegalArgumentException if given class is an array.
     * @throws IllegalArgumentException if given class does not have any generic types/type parameters.
     */
    public static <T> ParameterizedTypeBuilder<T> parameterized(Class<T> clazz) {
        if (clazz.isArray()) {
            throw new IllegalArgumentException("Can't create parametrized type from array class");
        }
        if (clazz.getTypeParameters().length == 0) {
            throw new IllegalArgumentException(clazz + " is not parameterized.");
        }
        return new ParameterizedTypeBuilder<>(clazz);
    }

    /**
     * Simple method to create ParameterizedType from given raw type class and given types, examples: <br>
     * {@code assertEquals(parameterized(Map.class, Map.of(entry("V", Number.class), entry("K", String.class))), new
     * TypeToken<Map<String, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(Map.class, Map.of(entry("V", Number.class))), new TypeToken<Map<?, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(Map.class, Map.of(entry("K", wildcard()), entry("V", Number.class))), new TypeToken<Map<?,
     * Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(Map.class, Map.of(entry("K", wildcardExtends(String.class)), entry("V", Number.class))), new
     * TypeToken<Map<? extends String, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(Map.class, Map.of(), new TypeToken<Map<?, ?>>(){}.getType())} <br>
     *
     * @param clazz raw type of parameterized type.
     * @param types map of types to use as parameters.
     * @param <T> type of raw type.
     *
     * @return created ParameterizedType.
     *
     * @throws IllegalStateException if builder contains more than one parameter with one of names from given map.
     * @throws IllegalArgumentException if any type from map does not belong to type being build by this builder.
     * @throws ClassCastException If any type from map isn't assignable to its variable type.
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is
     *         used for this check.
     * @see ParameterizedTypeBuilder#with(String, Type)
     */
    public static <T> ParameterizedType parameterized(Class<T> clazz, Map<? extends String, ? extends Type> types) {
        ParameterizedTypeBuilder<T> typeBuilder = parameterized(clazz);
        for (Entry<? extends String, ? extends Type> entry: types.entrySet()) {
            typeBuilder.with(entry.getKey(), entry.getValue());
        }
        return typeBuilder.build();
    }

    /**
     * Simple method to create ParameterizedType from given raw type class and given types, examples: <br>
     * {@code assertEquals(parameterized(Map.class, String.class, Number.class), new TypeToken<Map<String, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(Map.class, wildcard(), Number.class), new TypeToken<Map<?, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(Map.class, wildcardExtends(String.class), Number.class), new TypeToken<Map<? extends String,
     * Number>>(){}.getType())} <br>
     *
     * @param clazz raw type of parameterized type.
     * @param types types to use as parameters.
     * @param <T> type of raw type.
     *
     * @return created ParameterizedType.
     *
     * @throws ClassCastException If one of types isn't assignable to variable type.
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     * @throws IllegalArgumentException If given types array is longer than parameter types to fill.
     * @see ParameterizedTypeBuilder#with(Type...)
     */
    public static <T> ParameterizedType parameterized(Class<T> clazz, Type... types) {
        return parameterized(clazz).with(types).build();
    }

    /**
     * Simple method to create ParameterizedType from given raw type class and given types, examples: <br>
     * {@code assertEquals(parameterized(new TypeToken<Map<String, ?>>(){}.getType(), Map.of(entry("V", Number.class), entry("K", String
     * .class))), new
     * TypeToken<Map<String, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(new TypeToken<Map<String, ?>>(){}.getType(), Map.of(entry("V", Number.class))), new
     * TypeToken<Map<String, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(new TypeToken<Map<String, ?>>(){}.getType(), Map.of(entry("K", wildcard()), entry("V", Number
     * .class))), new TypeToken<Map<?, Number>>(){}.getType())} <br>
     *
     * @param type current parameterized type.
     * @param types map of types to use as parameters.
     * @param <T> type of raw type.
     *
     * @return created ParameterizedType.
     *
     * @throws IllegalStateException if builder contains more than one parameter with one of names from given map.
     * @throws IllegalArgumentException if any type from map does not belong to type being build by this builder.
     * @throws ClassCastException If any type from map isn't assignable to its variable type.
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is
     *         used for this check.
     * @see ParameterizedTypeBuilder#with(String, Type)
     */
    public static <T> ParameterizedType parameterized(ParameterizedType type, Map<? extends String, ? extends Type> types) {
        ParameterizedTypeBuilder<T> typeBuilder = parameterized(type);
        for (Entry<? extends String, ? extends Type> entry: types.entrySet()) {
            typeBuilder.with(entry.getKey(), entry.getValue());
        }
        return typeBuilder.build();
    }

    /**
     * Simple method to create ParameterizedType from given raw type class and given types, examples: <br>
     * {@code assertEquals(parameterized(new TypeToken<Map<String, ?>>(){}.getType(), String.class, Number.class), new
     * TypeToken<Map<String, Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(new TypeToken<Map<?, Number>>(){}.getType(), wildcard(), Number.class), new TypeToken<Map<?,
     * Number>>(){}.getType())} <br>
     * {@code assertEquals(parameterized(new TypeToken<Map<String, ?>>(){}.getType(), wildcard(), Number.class), new TypeToken<Map<?,
     * Number>>(){}.getType())} <br>
     *
     * @param type current parameterized type.
     * @param types types to use as parameters.
     * @param <T> type of raw type.
     *
     * @return created ParameterizedType.
     *
     * @throws ClassCastException If one of types isn't assignable to variable type.
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     * @throws IllegalArgumentException If given types array is longer than parameter types to fill.
     * @see ParameterizedTypeBuilder#with(Type...)
     */
    public static <T> ParameterizedType parameterized(ParameterizedType type, Type... types) {
        return parameterized(type).with(types).build();
    }

    /**
     * Returns class of array with component type of given class. <br>
     * Examples: <br>
     * {@literal String[] for String} <br>
     * {@literal String[][] for String[]}
     *
     * @param type component type of array.
     *
     * @return class of array with component type of given class.
     */
    public static Class<?> array(Class<?> type) {
        return array(type, 1);
    }

    /**
     * Creates and returns array class with given amount of dimension for given component type. <br>
     * Examples: <br>
     * {@literal String[] for String and 1 dimension} <br>
     * {@literal String[][][] for String and 3 dimension} <br>
     * {@literal String[][][] for String[] and 2 dimension}
     *
     * @param type component type of array.
     * @param dimensionsToAdd dimensions of array type to add.
     *
     * @return class of array with component type of given class.
     */
    public static Class<?> array(Class<?> type, int dimensionsToAdd) {
        if (dimensionsToAdd <= 0) {
            throw new IllegalStateException("Number of array dimensions must be positive and non-zero");
        }
        return Array.newInstance(type, new int[dimensionsToAdd]).getClass();
    }

    /**
     * Returns GenericArrayType of array with component type of given other GenericArrayType. <br>
     * Examples: <br>
     * {@literal List<?>[][] for List<?>[]} <br>
     *
     * @param type component type of array.
     *
     * @return GenericArrayType with given amount of dimensions (nested GenericArrayTypes).
     */
    public static GenericArrayType array(GenericArrayType type) {
        return array0(type, 1);
    }

    /**
     * Returns {@link GenericArrayType} of array with component type of given other GenericArrayType. <br>
     * Examples: <br>
     * {@literal List<?>[][] for List<?>[] and 1} <br>
     * {@literal List<?>[][][] for List<?>[] and 2} <br>
     *
     * @param type component type of array.
     * @param dimensionsToAdd dimensions of array type to add.
     *
     * @return GenericArrayType with given amount of dimensions (nested GenericArrayTypes).
     */
    public static GenericArrayType array(GenericArrayType type, int dimensionsToAdd) {
        return array0(type, dimensionsToAdd);
    }

    /**
     * Returns GenericArrayType of array with component type of given other {@link ParameterizedType}. <br>
     * Examples: <br>
     * {@literal List<?>[] for List<?>} <br>
     *
     * @param type component type of array.
     *
     * @return GenericArrayType with given amount of dimensions (nested GenericArrayTypes).
     */
    public static GenericArrayType array(ParameterizedType type) {
        return array0(type, 1);
    }

    /**
     * Returns {@link GenericArrayType} of array with component type of given other {@link ParameterizedType}. <br>
     * Examples: <br>
     * {@literal List<?>[] for List<?> and 1} <br>
     * {@literal List<?>[][] for List<?> and 2} <br>
     *
     * @param type component type of array.
     * @param dimensionsToAdd dimensions of array type to add.
     *
     * @return GenericArrayType with given amount of dimensions (nested GenericArrayTypes).
     */
    public static GenericArrayType array(ParameterizedType type, int dimensionsToAdd) {
        return array0(type, dimensionsToAdd);
    }

    static GenericArrayType array0(Type type, int dimensionsToAdd) {
        if (dimensionsToAdd <= 0) {
            throw new IllegalStateException("Number of array dimensions must be positive and non-zero");
        }
        GenericArrayTypeImpl arrayType = new GenericArrayTypeImpl(type);
        for (int i = 1; i < dimensionsToAdd; i++) {
            arrayType = new GenericArrayTypeImpl(arrayType);
        }
        return arrayType;
    }
}
