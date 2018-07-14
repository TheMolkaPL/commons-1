package org.diorite.commons.array;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
final class ArrayCloner {

    private ArrayCloner() {}

    private static final Map<Class, UnaryOperator> arrayFunctions = Map.ofEntries(
            cloneEntry(boolean[].class, boolean[]::clone), cloneEntry(byte[].class, byte[]::clone),
            cloneEntry(short[].class, short[]::clone), cloneEntry(char[].class, char[]::clone),
            cloneEntry(int[].class, int[]::clone), cloneEntry(long[].class, long[]::clone),
            cloneEntry(float[].class, float[]::clone), cloneEntry(double[].class, double[]::clone)
    );

    private static <T> Entry<Class<T>, UnaryOperator<T>> cloneEntry(Class<T> arrayClass, UnaryOperator<T> cloner) {
        return Map.entry(arrayClass, cloner);
    }

    static <T> T cloneArray(T array) {
        if (! array.getClass().isArray()) {
            throw new IllegalArgumentException("Expected array but got: " + array.getClass());
        }
        if (array instanceof Object[]) {
            return (T) ((Object[]) array).clone();
        }
        return (T) arrayFunctions.get(array.getClass()).apply(array);
    }

    static <T> T cloneArrayDeep(T array) {
        Class<?> arrayClass = array.getClass();
        if (! arrayClass.isArray()) {
            throw new IllegalArgumentException("Expected array but got: " + arrayClass);
        }
        if (arrayClass.getComponentType().isArray()) {
            Object[] clonedArray = ((Object[]) array).clone();
            for (int i = 0, objectArrayLength = clonedArray.length; i < objectArrayLength; i++) {
                clonedArray[i] = cloneArrayDeep(clonedArray[i]);
            }
            return (T) clonedArray;
        }
        return cloneArray(array);
    }
}
