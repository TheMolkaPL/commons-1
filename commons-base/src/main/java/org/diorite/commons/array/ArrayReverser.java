package org.diorite.commons.array;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Map.Entry;

final class ArrayReverser {

    private ArrayReverser() {}

    private static final Map<Class, Reverser> arrayFunctions = Map.ofEntries(
            cloneEntry(boolean[].class, ArrayReverser::reverse), cloneEntry(byte[].class, ArrayReverser::reverse),
            cloneEntry(short[].class, ArrayReverser::reverse), cloneEntry(char[].class, ArrayReverser::reverse),
            cloneEntry(int[].class, ArrayReverser::reverse), cloneEntry(long[].class, ArrayReverser::reverse),
            cloneEntry(float[].class, ArrayReverser::reverse), cloneEntry(double[].class, ArrayReverser::reverse)
    );

    private static <T> Entry<Class<T>, Reverser<T>> cloneEntry(Class<T> arrayClass, Reverser<T> reverser) {
        return Map.entry(arrayClass, reverser);
    }

    @FunctionalInterface
    interface Reverser<T> {
        void reverse(T array, int fromInclusive, int toExclusive);
    }

    @Nullable
    static <T> T reverse(@Nullable T array) {
        if (array == null) {
            return null;
        }
        return reverse(array, 0, Array.getLength(array));
    }

    @SuppressWarnings("unchecked")
    @Nullable
    static <T> T reverse(@Nullable T array, int fromInclusive, int toExclusive) {
        if (array == null) {
            return null;
        }
        if (array instanceof Object[]) {
            reverse(((Object[]) array), fromInclusive, toExclusive);
            return array;
        }
        arrayFunctions.get(array.getClass()).reverse(array, fromInclusive, toExclusive);
        return array;
    }

    static void reverse(Object[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            Object tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(boolean[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            boolean tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(byte[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            byte tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(char[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            char tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(short[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            short tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(int[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            int tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(long[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            long tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(float[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            float tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }

    static void reverse(double[] array, int fromInclusive, int toExclusive) {
        int from = Math.max(0, fromInclusive);
        int to = Math.min(array.length - 1, toExclusive);
        while (to > from) {
            double tmp = array[to];
            array[to--] = array[from];
            array[from++] = tmp;
        }
    }
}
