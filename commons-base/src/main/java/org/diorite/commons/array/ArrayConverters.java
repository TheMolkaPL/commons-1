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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@SuppressWarnings("Duplicates")
final class ArrayConverters {
    private ArrayConverters() {}

    private static final Map<ArrayConverterPair, MethodHandle> methods = new HashMap<>();

    static {
        Lookup lookup = MethodHandles.lookup();
        Predicate<String> pattern = Pattern.compile("to[A-Za-z]+Array").asPredicate();
        for (Method method : ArrayConverters.class.getDeclaredMethods()) {
            if (! pattern.test(method.getName()) || ! method.getReturnType().isArray() ||
                        (method.getParameterCount() != 1) || ! method.getParameterTypes()[0].isArray()) {
                continue;
            }
            Class<?> to = method.getReturnType();
            Class<?> from = method.getParameterTypes()[0];

            MethodHandle methodHandle = null;
            try {
                methodHandle = lookup.unreflect(method).asType(MethodType.methodType(Object.class, Object.class));
            }
            catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
            methods.put(new ArrayConverterPair<>(from, to), methodHandle);
        }
    }

    @SuppressWarnings("unchecked")
    static <F, T> T convert(Class<T> to, F array) {
        MethodHandle methodHandle = methods.get(new ArrayConverterPair<>(array.getClass(), to));
        if (methodHandle == null) {
            throw new IllegalStateException("Unsupported conversion: " + array.getClass() + " -> " + to);
        }
        try {
            return (T) methodHandle.invokeExact(array);
        }
        catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    static final class ArrayConverterPair<F, T> {
        private final Class<F> from;
        private final Class<T> to;

        ArrayConverterPair(Class<F> from, Class<T> to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (this.getClass() != o.getClass())) {
                return false;
            }
            ArrayConverterPair<?, ?> that = (ArrayConverterPair<?, ?>) o;
            return Objects.equals(this.from, that.from) &&
                           Objects.equals(this.to, that.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.from, this.to);
        }
    }

    private static boolean[] toBooleanArray(byte[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(short[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(char[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(int[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(long[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(float[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(double[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static byte[] toByteArray(boolean[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (byte) 1 : (byte) 0;
        }
        return result;
    }

    private static byte[] toByteArray(short[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static byte[] toByteArray(char[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static byte[] toByteArray(int[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static byte[] toByteArray(long[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static byte[] toByteArray(float[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static byte[] toByteArray(double[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static short[] toShortArray(boolean[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (short) 1 : (short) 0;
        }
        return result;
    }

    private static short[] toShortArray(byte[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static short[] toShortArray(char[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static short[] toShortArray(int[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static short[] toShortArray(long[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static short[] toShortArray(float[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static short[] toShortArray(double[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static char[] toCharArray(boolean[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (char) 1 : (char) 0;
        }
        return result;
    }

    private static char[] toCharArray(short[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static char[] toCharArray(byte[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static char[] toCharArray(int[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static char[] toCharArray(long[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static char[] toCharArray(float[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static char[] toCharArray(double[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static int[] toIntArray(boolean[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static int[] toIntArray(short[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static int[] toIntArray(char[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static int[] toIntArray(byte[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static int[] toIntArray(long[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static int[] toIntArray(float[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static int[] toIntArray(double[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static long[] toLongArray(boolean[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static long[] toLongArray(short[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static long[] toLongArray(char[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static long[] toLongArray(int[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static long[] toLongArray(byte[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static long[] toLongArray(float[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static long[] toLongArray(double[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static float[] toFloatArray(boolean[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static float[] toFloatArray(short[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static float[] toFloatArray(char[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static float[] toFloatArray(int[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static float[] toFloatArray(long[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static float[] toFloatArray(byte[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static float[] toFloatArray(double[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static double[] toDoubleArray(boolean[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static double[] toDoubleArray(short[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static double[] toDoubleArray(char[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static double[] toDoubleArray(int[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static double[] toDoubleArray(long[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static double[] toDoubleArray(float[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static double[] toDoubleArray(byte[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    /*
     * Object versions
     */

    private static boolean[] toBooleanArray(Boolean[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static boolean[] toBooleanArray(Number[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue() > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(Byte[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(Short[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(Character[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(Integer[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(Long[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(Float[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static boolean[] toBooleanArray(Double[] array) {
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static byte[] toByteArray(Byte[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static byte[] toByteArray(Number[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static byte[] toByteArray(Boolean[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (byte) 1 : (byte) 0;
        }
        return result;
    }

    private static byte[] toByteArray(Short[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static byte[] toByteArray(Character[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = ((byte) array[i].charValue());
        }
        return result;
    }

    private static byte[] toByteArray(Integer[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static byte[] toByteArray(Long[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static byte[] toByteArray(Float[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static byte[] toByteArray(Double[] array) {
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static short[] toShortArray(Short[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static short[] toShortArray(Number[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static short[] toShortArray(Boolean[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (short) 1 : (short) 0;
        }
        return result;
    }

    private static short[] toShortArray(Byte[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static short[] toShortArray(Character[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = ((short) array[i].charValue());
        }
        return result;
    }

    private static short[] toShortArray(Integer[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static short[] toShortArray(Long[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static short[] toShortArray(Float[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static short[] toShortArray(Double[] array) {
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static char[] toCharArray(Character[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static char[] toCharArray(Number[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].intValue();
        }
        return result;
    }

    private static char[] toCharArray(Boolean[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (char) 1 : (char) 0;
        }
        return result;
    }

    private static char[] toCharArray(Short[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].shortValue();
        }
        return result;
    }

    private static char[] toCharArray(Byte[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].byteValue();
        }
        return result;
    }

    private static char[] toCharArray(Integer[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].intValue();
        }
        return result;
    }

    private static char[] toCharArray(Long[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].longValue();
        }
        return result;
    }

    private static char[] toCharArray(Float[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].floatValue();
        }
        return result;
    }

    private static char[] toCharArray(Double[] array) {
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].doubleValue();
        }
        return result;
    }

    private static int[] toIntArray(Integer[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static int[] toIntArray(Number[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static int[] toIntArray(Boolean[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static int[] toIntArray(Short[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static int[] toIntArray(Character[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            //noinspection UnnecessaryUnboxing
            result[i] = ((int) array[i].charValue());
        }
        return result;
    }

    private static int[] toIntArray(Byte[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static int[] toIntArray(Long[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static int[] toIntArray(Float[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static int[] toIntArray(Double[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static long[] toLongArray(Long[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static long[] toLongArray(Number[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static long[] toLongArray(Boolean[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static long[] toLongArray(Short[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static long[] toLongArray(Character[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            //noinspection UnnecessaryUnboxing
            result[i] = ((long) array[i].charValue());
        }
        return result;
    }

    private static long[] toLongArray(Integer[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static long[] toLongArray(Byte[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static long[] toLongArray(Float[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static long[] toLongArray(Double[] array) {
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static float[] toFloatArray(Float[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static float[] toFloatArray(Number[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static float[] toFloatArray(Boolean[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static float[] toFloatArray(Short[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static float[] toFloatArray(Character[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            //noinspection UnnecessaryUnboxing
            result[i] = ((float) array[i].charValue());
        }
        return result;
    }

    private static float[] toFloatArray(Integer[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static float[] toFloatArray(Long[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static float[] toFloatArray(Byte[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static float[] toFloatArray(Double[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static double[] toDoubleArray(Double[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static double[] toDoubleArray(Number[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static double[] toDoubleArray(Boolean[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static double[] toDoubleArray(Short[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static double[] toDoubleArray(Character[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            //noinspection UnnecessaryUnboxing
            result[i] = ((double) array[i].charValue());
        }
        return result;
    }

    private static double[] toDoubleArray(Integer[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static double[] toDoubleArray(Long[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static double[] toDoubleArray(Float[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static double[] toDoubleArray(Byte[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /*
     * Boxing versions
     */

    private static Boolean[] toBoxedBooleanArray(byte[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(short[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(char[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(int[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(long[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(float[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(double[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(boolean[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (byte) 1 : (byte) 0;
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(short[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(char[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(int[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(long[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(float[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(double[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i];
        }
        return result;
    }

    private static Short[] toBoxedShortArray(boolean[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (short) 1 : (short) 0;
        }
        return result;
    }

    private static Short[] toBoxedShortArray(byte[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static Short[] toBoxedShortArray(char[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static Short[] toBoxedShortArray(int[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static Short[] toBoxedShortArray(long[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static Short[] toBoxedShortArray(float[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static Short[] toBoxedShortArray(double[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i];
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(boolean[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (char) 1 : (char) 0;
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(short[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(byte[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(int[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(long[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(float[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(double[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i];
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(boolean[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(short[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(char[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(byte[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(long[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(float[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(double[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (int) array[i];
        }
        return result;
    }

    private static Long[] toBoxedLongArray(boolean[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1L : 0L;
        }
        return result;
    }

    private static Long[] toBoxedLongArray(short[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static Long[] toBoxedLongArray(char[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static Long[] toBoxedLongArray(int[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static Long[] toBoxedLongArray(byte[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static Long[] toBoxedLongArray(float[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static Long[] toBoxedLongArray(double[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (long) array[i];
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(boolean[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1F : 0F;
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(short[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(char[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(int[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(long[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(byte[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(double[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) array[i];
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(boolean[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1D : 0D;
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(short[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(char[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(int[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(long[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(float[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(byte[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (double) array[i];
        }
        return result;
    }

    /*
     * Boxed to other boxed
     */

    private static Boolean[] toBoxedBooleanArray(Byte[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(Short[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(Character[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(Integer[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(Long[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(Float[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Boolean[] toBoxedBooleanArray(Double[] array) {
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] > 0;
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(Boolean[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[1] ? (byte) 1 : (byte) 0;
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(Short[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(Character[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) array[i].charValue();
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(Integer[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(Long[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(Float[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static Byte[] toBoxedByteArray(Double[] array) {
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    private static Short[] toBoxedShortArray(Boolean[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (short) 1 : (short) 0;
        }
        return result;
    }

    private static Short[] toBoxedShortArray(Byte[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static Short[] toBoxedShortArray(Character[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (short) array[i].charValue();
        }
        return result;
    }

    private static Short[] toBoxedShortArray(Integer[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static Short[] toBoxedShortArray(Long[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static Short[] toBoxedShortArray(Float[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static Short[] toBoxedShortArray(Double[] array) {
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(Boolean[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? (char) 1 : (char) 0;
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(Short[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].shortValue();
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(Byte[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].byteValue();
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(Integer[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].intValue();
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(Long[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].longValue();
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(Float[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].floatValue();
        }
        return result;
    }

    private static Character[] toBoxedCharacterArray(Double[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (char) array[i].doubleValue();
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(Boolean[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1 : 0;
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(Short[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(Character[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(Byte[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(Long[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(Float[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static Integer[] toBoxedIntegerArray(Double[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    private static Long[] toBoxedLongArray(Boolean[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1L : 0L;
        }
        return result;
    }

    private static Long[] toBoxedLongArray(Short[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static Long[] toBoxedLongArray(Character[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }

    private static Long[] toBoxedLongArray(Integer[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static Long[] toBoxedLongArray(Byte[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static Long[] toBoxedLongArray(Float[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static Long[] toBoxedLongArray(Double[] array) {
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(Boolean[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1F : 0F;
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(Short[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(Character[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Float.valueOf(array[i]);
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(Integer[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(Long[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(Byte[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static Float[] toBoxedFloatArray(Double[] array) {
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(Boolean[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? 1D : 0D;
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(Short[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(Character[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(Integer[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(Long[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(Float[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    private static Double[] toBoxedDoubleArray(Byte[] array) {
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }
}
