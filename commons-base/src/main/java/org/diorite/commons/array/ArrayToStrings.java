package org.diorite.commons.array;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

@SuppressWarnings("unchecked")
final class ArrayToStrings {

    private ArrayToStrings() {}

    private static final Map<Class, Function> arrayFunctions = Map.ofEntries(
            cloneEntry(boolean[].class, Arrays::toString), cloneEntry(byte[].class, Arrays::toString),
            cloneEntry(short[].class, Arrays::toString), cloneEntry(char[].class, Arrays::toString),
            cloneEntry(int[].class, Arrays::toString), cloneEntry(long[].class, Arrays::toString),
            cloneEntry(float[].class, Arrays::toString), cloneEntry(double[].class, Arrays::toString)
    );

    private static <T> Entry<Class<T>, Function<T, String>> cloneEntry(Class<T> arrayClass, Function<T, String> toStringFunc) {
        return Map.entry(arrayClass, toStringFunc);
    }

    static <T> String toString(T array) {
        if (! array.getClass().isArray()) {
            throw new IllegalArgumentException("Expected array but got: " + array.getClass());
        }
        if (array instanceof Object[]) {
            return Arrays.toString((Object[]) array);
        }
        return (String) arrayFunctions.get(array.getClass()).apply(array);
    }

    static <T> String deepToString(T array) {
        Class<?> arrayClass = array.getClass();
        if (! arrayClass.isArray()) {
            throw new IllegalArgumentException("Expected array but got: " + arrayClass);
        }
        if (array instanceof Object[]) {
            return Arrays.deepToString(((Object[]) array));
        }
        return toString(array);
    }
}
