package org.diorite.commons.array;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.ToIntFunction;

@SuppressWarnings("unchecked")
final class ArrayHashCodes {

    private ArrayHashCodes() {}

    private static final Map<Class, ToIntFunction> arrayFunctions = Map.ofEntries(
            cloneEntry(boolean[].class, Arrays::hashCode), cloneEntry(byte[].class, Arrays::hashCode),
            cloneEntry(short[].class, Arrays::hashCode), cloneEntry(char[].class, Arrays::hashCode),
            cloneEntry(int[].class, Arrays::hashCode), cloneEntry(long[].class, Arrays::hashCode),
            cloneEntry(float[].class, Arrays::hashCode), cloneEntry(double[].class, Arrays::hashCode)
    );

    private static <T> Entry<Class<T>, ToIntFunction<T>> cloneEntry(Class<T> arrayClass, ToIntFunction<T> hashCodeFunc) {
        return Map.entry(arrayClass, hashCodeFunc);
    }

    static <T> int hashCode(T array) {
        if (! array.getClass().isArray()) {
            throw new IllegalArgumentException("Expected array but got: " + array.getClass());
        }
        if (array instanceof Object[]) {
            return Arrays.hashCode((Object[]) array);
        }
        return arrayFunctions.get(array.getClass()).applyAsInt(array);
    }

    static <T> int deepHashCode(T array) {
        Class<?> arrayClass = array.getClass();
        if (! arrayClass.isArray()) {
            throw new IllegalArgumentException("Expected array but got: " + arrayClass);
        }
        if (array instanceof Object[]) {
            return Arrays.deepHashCode(((Object[]) array));
        }
        return hashCode(array);
    }
}
