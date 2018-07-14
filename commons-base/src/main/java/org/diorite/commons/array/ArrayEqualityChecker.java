package org.diorite.commons.array;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;

final class ArrayEqualityChecker {

    private ArrayEqualityChecker() {}

    private static final Map<Class, BiPredicate> arrayFunctions = Map.ofEntries(
            cloneEntry(boolean[].class, Arrays::equals), cloneEntry(byte[].class, Arrays::equals),
            cloneEntry(short[].class, Arrays::equals), cloneEntry(char[].class, Arrays::equals),
            cloneEntry(int[].class, Arrays::equals), cloneEntry(long[].class, Arrays::equals),
            cloneEntry(float[].class, Arrays::equals), cloneEntry(double[].class, Arrays::equals)
    );

    private static <T> Entry<Class<T>, BiPredicate<T, T>> cloneEntry(Class<T> arrayClass, BiPredicate<T, T> equals) {
        return Map.entry(arrayClass, equals);
    }

    @SuppressWarnings("unchecked")
    static <T> boolean equals(@Nullable T arrayA, @Nullable T arrayB) {
        if (arrayA == arrayB) {
            return true;
        }
        if ((arrayA == null) || (arrayB == null)) {
            return false;
        }
        if (! arrayA.getClass().isArray()) {
            throw new IllegalArgumentException("Expected array (A) but got: " + arrayA.getClass());
        }
        if (! arrayB.getClass().isArray()) {
            throw new IllegalArgumentException("Expected array (B) but got: " + arrayB.getClass());
        }
        if (arrayA instanceof Object[]) {
            if (arrayB instanceof Object[]) {
                return Arrays.equals((Object[]) arrayA, (Object[]) arrayB);
            }
            return false;
        }
        if (arrayA.getClass() != arrayB.getClass()) { // different primitive array types
            return false;
        }
        return arrayFunctions.get(arrayA.getClass()).test(arrayA, arrayB);
    }

    static <T> boolean deepEquals(@Nullable T arrayA, @Nullable T arrayB) {
        if (arrayA == arrayB) {
            return true;
        }
        if ((arrayA == null) || (arrayB == null)) {
            return false;
        }
        Class<?> arrayAClass = arrayA.getClass();
        if (! arrayAClass.isArray()) {
            throw new IllegalArgumentException("Expected array (A) but got: " + arrayAClass);
        }
        Class<?> arrayBClass = arrayB.getClass();
        if (! arrayBClass.isArray()) {
            throw new IllegalArgumentException("Expected array (B) but got: " + arrayBClass);
        }
        if (arrayA instanceof Object[]) {
            if (arrayB instanceof Object[]) {
                return Arrays.deepEquals((Object[]) arrayA, (Object[]) arrayB);
            }
            return false;
        }
        return equals(arrayA, arrayB);
    }
}
