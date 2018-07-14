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

package org.diorite.commons.array;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Utility class for empty arrays and joining multiple arrays into one.
 */
@SuppressWarnings("Duplicates")
public final class ArrayUtils {
    /**
     * Empty array of boolean type.
     */
    public static final boolean[] EMPTY_BOOLEANS = getEmptyArrayByArrayClass(boolean[].class);
    /**
     * Empty array of char type.
     */
    public static final char[]    EMPTY_CHARS    = getEmptyArrayByArrayClass(char[].class);
    /**
     * Empty array of byte type.
     */
    public static final byte[]    EMPTY_BYTES    = getEmptyArrayByArrayClass(byte[].class);
    /**
     * Empty array of short type.
     */
    public static final short[]   EMPTY_SHORTS   = getEmptyArrayByArrayClass(short[].class);
    /**
     * Empty array of int type.
     */
    public static final int[]     EMPTY_INTS     = getEmptyArrayByArrayClass(int[].class);
    /**
     * Empty array of long type.
     */
    public static final long[]    EMPTY_LONGS    = getEmptyArrayByArrayClass(long[].class);
    /**
     * Empty array of float type.
     */
    public static final float[]   EMPTY_FLOATS   = getEmptyArrayByArrayClass(float[].class);
    /**
     * Empty array of double type.
     */
    public static final double[]  EMPTY_DOUBLES  = getEmptyArrayByArrayClass(double[].class);
    /**
     * Empty array of Object type.
     */
    public static final Object[]  EMPTY_OBJECT   = getEmptyArrayByArrayClass(Object[].class);
    /**
     * Empty array of String type.
     */
    public static final String[]  EMPTY_STRINGS  = getEmptyArrayByArrayClass(String[].class);
    /**
     * Empty array of Class type.
     */
    public static final Class[]   EMPTY_CLASSES  = getEmptyArrayByArrayClass(Class[].class);
    /**
     * Empty array of Type type.
     */
    public static final Type[]    EMPTY_TYPES    = getEmptyArrayByArrayClass(Type[].class);

    private ArrayUtils() {
    }

    /**
     * Adds prefix to each array element.
     *
     * @param prefix prefix to be added.
     * @param array array to edit.
     *
     * @return this same array as given after editing elements.
     */
    public static String[] addPrefix(String prefix, String[] array) {
        return addPrefixAndSuffix(prefix, "", array);
    }

    /**
     * Adds suffix to each array element.
     *
     * @param suffix suffix to be added.
     * @param array array to edit.
     *
     * @return this same array as given after editing elements.
     */
    public static String[] addSuffix(String suffix, String[] array) {
        return addPrefixAndSuffix("", suffix, array);
    }

    /**
     * Adds prefix and suffix to each array element.
     *
     * @param prefix prefix to be added.
     * @param suffix suffix to be added.
     * @param array array to edit.
     *
     * @return this same array as given after editing elements.
     */
    public static String[] addPrefixAndSuffix(String prefix, String suffix, String[] array) {
        for (int i = 0, arrayLength = array.length; i < arrayLength; i++) {
            String s = array[i];
            array[i] = prefix + s + suffix;
        }
        return array;
    }

    /**
     * Returns empty array of given type, must be an object type, for primitives use {@link #getEmptyArray(Class)} or
     * {@link #getEmptyArrayByArrayClass(Class)}
     * <br> All arrays are cached, method will always return this same array for this same class.
     *
     * @param clazz type of array, must be class of object.
     * @param <T> type of array.
     *
     * @return empty array of given type.
     *
     * @throws IllegalArgumentException if given class is primitive type. {@link Class#isPrimitive()}
     */
    public static <T> T[] getEmptyObjectArray(Class<? extends T> clazz) throws IllegalArgumentException {
        return ArrayCreator.getEmptyObjectArray(clazz);
    }

    public static <T> T[] getCachedEmptyArray(T[] array) {
        return ArrayCreator.getCachedEmptyArray(array);
    }

    /**
     * Returns array of given type as object. (to support primitive types) <br>
     * All arrays are cached, method will always return this same array for this same class.
     *
     * @param clazz type of array.
     *
     * @return empty array of given type.
     */
    public static Object getEmptyArray(Class<?> clazz) {
        return ArrayCreator.getEmptyArray(clazz);
    }

    /**
     * Returns array of given type, given class must be type of array, like int[].class <br>
     * All arrays are cached, method will always return this same array for this same class.
     *
     * @param clazz type of array, must be class of array type.
     * @param <T> type of array.
     *
     * @return empty array of given type.
     *
     * @throws IllegalArgumentException if given class isn't array. {@link Class#isArray()}
     */
    public static <T> T getEmptyArrayByArrayClass(Class<? extends T> clazz) throws IllegalArgumentException {
        return ArrayCreator.getEmptyArrayByArrayClass(clazz);
    }

    /**
     * Returns array of given type, must be an object type, for primitives use {@link #getEmptyArray(Class)} or
     * {@link #getEmptyArrayByArrayClass(Class)} <br>
     * If given size is equals to 0, result of {@link #getEmptyArray(Class)} will be returned.
     *
     * @param clazz type of array, must be class of object.
     * @param size size of array.
     * @param <T> type of array.
     *
     * @return array of given type and size.
     *
     * @throws IllegalArgumentException if given class is primitive type. {@link Class#isPrimitive()}
     */
    public static <T> T[] newObjectArray(Class<? extends T> clazz, int size) throws IllegalArgumentException {
        return ArrayCreator.newObjectArray(clazz, size);
    }

    /**
     * Returns array of given type as object. (to support primitive types) <br>
     * If given size is equals to 0, result of {@link #getEmptyArray(Class)} will be returned.
     *
     * @param clazz type of array.
     * @param size size of array.
     *
     * @return array of given type and size.
     */
    public static Object newArray(Class<?> clazz, int size) {
        return ArrayCreator.newArray(clazz, size);
    }

    /**
     * Returns new array of given type, given class must be type of array, like int[].class <br>
     * If given size is equals to 0, result of {@link #getEmptyArrayByArrayClass(Class)} will be returned.
     *
     * @param clazz type of array, must be class of array type.
     * @param size size of array.
     * @param <T> type of array.
     *
     * @return array of given type and size.
     *
     * @throws IllegalArgumentException if given class isn't array. {@link Class#isArray()}
     */
    public static <T> T newArrayByArrayClass(Class<? extends T> clazz, int size) throws IllegalArgumentException {
        return ArrayCreator.newArrayByArrayClass(clazz, size);
    }

    /**
     * Returns true if given array is empty. <br>
     * For null input this methods return true.
     *
     * @param array array to check.
     *
     * @return true if given array is empty.
     */
    public static boolean isEmpty(@Nullable Object array) {
        if (array == null) {
            return true;
        }
        if (! array.getClass().isArray()) {
            throw new IllegalStateException("Expected array but got: " + array.getClass());
        }
        return Array.getLength(array) == 0;
    }

    /**
     * Reverse given array.
     *
     * @param array array to reverse
     *
     * @return same array instance as given after being reversed
     */
    @Nullable
    public static <T> T reverse(@Nullable T array) {
        return ArrayReverser.reverse(array);
    }

    /**
     * Reverse part of given array.
     *
     * @param array array to operate on
     * @param fromInclusive starting inclusive index of area/range to reverse.
     * @param toExclusive ending exclusive index of area/range to reverse.
     *
     * @return same array instance as given after being reversed
     */
    @Nullable
    public static <T> T reverse(@Nullable T array, int fromInclusive, int toExclusive) {
        return ArrayReverser.reverse(array, fromInclusive, toExclusive);
    }

    /**
     * Alternative to {@link Arrays#equals(Object[], Object[])} but for any type of array including primitive ones.
     *
     * @param arrayA first array
     * @param arrayB second array
     * @param <T> type of arrays
     *
     * @return true if arrays are equal.
     */
    public static <T> boolean equals(T arrayA, T arrayB) {
        return ArrayEqualityChecker.equals(arrayA, arrayB);
    }

    /**
     * Alternative to {@link Arrays#deepEquals(Object[], Object[])} but for any type of array including primitive ones.
     *
     * @param arrayA first array
     * @param arrayB second array
     * @param <T> type of arrays
     *
     * @return true if arrays are equal.
     */
    public static <T> boolean deepEquals(T arrayA, T arrayB) {
        return ArrayEqualityChecker.deepEquals(arrayA, arrayB);
    }

    /**
     * Alternative to {@link Arrays#hashCode(Object[])} but for any type of array including primitive ones.
     *
     * @param array array to count hashcode for.
     *
     * @return array hashcode.
     */
    public static int hashCode(Object array) {
        return ArrayHashCodes.hashCode(array);
    }

    /**
     * Alternative to {@link Arrays#deepHashCode(Object[])} but for any type of array including primitive ones.
     *
     * @param array array to count hashcode for.
     *
     * @return array hashcode.
     */
    public static int deepHashCode(Object array) {
        return ArrayHashCodes.deepHashCode(array);
    }

    /**
     * Alternative to {@link Arrays#toString(Object[])} but for any type of array including primitive ones.
     *
     * @param array array to get string representation of it.
     *
     * @return array to string representation.
     */
    public static String toString(Object array) {
        return ArrayToStrings.toString(array);
    }

    /**
     * Alternative to {@link Arrays#deepToString(Object[])} but for any type of array including primitive ones.
     *
     * @param array array to get string representation of it.
     *
     * @return array to string representation.
     */
    public static String deepToString(Object array) {
        return ArrayToStrings.deepToString(array);
    }

    /**
     * Clone array, supports primitive arrays.
     *
     * @param array array to clone, can be of primitive type.
     * @param <T> type of array.
     *
     * @return cloned array.
     */
    public static <T> T clone(T array) {
        return ArrayCloner.cloneArray(array);
    }

    /**
     * Clone array.
     *
     * @param array array to clone.
     * @param <T> type of array.
     *
     * @return cloned array.
     */
    public static <T> T[] clone(T[] array) {
        return ArrayCloner.cloneArray(array);
    }

    /**
     * Deep clone array, supports primitive arrays.
     *
     * @param array array to clone, can be of primitive type.
     * @param <T> type of array.
     *
     * @return cloned array.
     */
    public static <T> T deepClone(T array) {
        return ArrayCloner.cloneArrayDeep(array);
    }

    /**
     * Deep clone array.
     *
     * @param array array to clone.
     * @param <T> type of array.
     *
     * @return cloned array.
     */
    public static <T> T[] deepClone(T[] array) {
        return ArrayCloner.cloneArrayDeep(array);
    }

    /**
     * Convert one array type to another - only for primitives and boxed primitives. Examples: <br>
     * int[] to Integer[] <br>
     * float[] to int[] <br>
     * Boolean[] to double[] <br>
     * Character[] to Boolean[]
     *
     * @param to type of output array, must be primitive or primitive boxed array type.
     * @param array source array, must be primitive or primitive boxed array type.
     * @param <F> type of source array.
     * @param <T> type of output array.
     *
     * @return converted array.
     */
    public static <F, T> T convert(Class<? extends T> to, F array) {
        return ArrayConverters.convert(to, array);
    }


    /**
     * Joins arrays together, if only one array contains elements it will be returned without coping anything. <br>
     * If given array or arrays is empty, new empty array will be returned. <br>
     * <pre>{@code
     * joinReversed(new int[]{1,2}, new int[]{3,4}, new int[]{5,6}) == {1,2,3,4,5,6}
     * }
     * </pre>
     *
     * @param arrays arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    @SafeVarargs
    private static <T> T join(T... arrays) {
        return ArrayJoiner.join0(false, arrays);
    }

    /**
     * Joins arrays together but elements from first arrays will be at the end of result array, if only one array contains elements it
     * will be returned without coping anything. <br>
     * If given array or arrays is empty, empty array will be returned. <br>
     * <pre>{@code
     * joinReversed(new int[]{1,2}, new int[]{3,4}, new int[]{5,6}) == {5,6,3,4,1,2}
     * }
     * </pre>
     *
     * @param arrays arrays to join.
     *
     * @return new joined array, or one of given ones if other arrays were empty.
     */
    @SafeVarargs
    private static <T> T joinReversed(T... arrays) {
        return ArrayJoiner.join0(true, arrays);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything. <br>
     * NOTE: this method use reflections!
     *
     * @param arrayA first array.
     * @param arrayB second array.
     * @param <T> type of array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    @SafeVarargs
    private static <T> T[] append(T[] arrayA, T... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything. <br>
     * NOTE: this method use reflections!
     *
     * @param arrayA first array.
     * @param arrayB second array.
     * @param <T> type of array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    @SafeVarargs
    private static <T> T[] prepend(T[] arrayA, T... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static boolean[] append(boolean[] arrayA, boolean... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static boolean[] prepend(boolean[] arrayA, boolean... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static char[] append(char[] arrayA, char... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static char[] prepend(char[] arrayA, char... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static byte[] append(byte[] arrayA, byte... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static byte[] prepend(byte[] arrayA, byte... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static short[] append(short[] arrayA, short... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static short[] prepend(short[] arrayA, short... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static int[] append(int[] arrayA, int... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static int[] prepend(int[] arrayA, int... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static long[] append(long[] arrayA, long... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static long[] prepend(long[] arrayA, long... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static float[] append(float[] arrayA, float... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static float[] prepend(float[] arrayA, float... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static double[] append(double[] arrayA, double... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, true);
    }

    /**
     * Joins 2 arrays together, if any array is empty then other array will be returned without coping anything.
     *
     * @param arrayA first array.
     * @param arrayB second array.
     *
     * @return new joined array, or one of given ones if any of arrays was empty.
     */
    private static double[] prepend(double[] arrayA, double... arrayB) {
        return ArrayJoiner.appendPrepend0(arrayA, arrayB, false);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static byte sum(byte[] array) {
        return ArraySum.sum(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static int sumToInt(byte[] array) {
        return ArraySum.sumToInt(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sumToLong(byte[] array) {
        return ArraySum.sumToLong(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static char sum(char[] array) {
        return ArraySum.sum(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static int sumToInt(char[] array) {
        return ArraySum.sumToInt(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sumToLong(char[] array) {
        return ArraySum.sumToLong(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static int sum(int[] array) {
        return ArraySum.sum(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sumToLong(int[] array) {
        return ArraySum.sumToLong(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static long sum(long[] array) {
        return ArraySum.sum(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static float sum(float[] array) {
        return ArraySum.sum(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static double sumToDouble(float[] array) {
        return ArraySum.sumToDouble(array);
    }

    /**
     * Sum all numbers from array.
     *
     * @param array array to sum.
     *
     * @return sum of all elements in given array.
     */
    public static double sum(double[] array) {
        return ArraySum.sum(array);
    }

}
