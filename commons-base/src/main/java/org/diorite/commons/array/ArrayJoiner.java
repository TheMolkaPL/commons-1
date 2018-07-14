package org.diorite.commons.array;

import java.lang.reflect.Array;

final class ArrayJoiner {

    private ArrayJoiner() {}

    @SuppressWarnings("unchecked")
    static <T> T join0(boolean reversed, T... arrays) {
        Class<?> componentType = arrays.getClass().getComponentType().getComponentType();
        if (componentType == null) {
            throw new IllegalStateException("Argument is not an array of arrays!");
        }
        int arraysLength = arrays.length;
        if (arraysLength == 0) {
            return (T) ArrayUtils.getEmptyArrayByArrayClass(componentType);
        }
        if (arraysLength == 1) {
            return arrays[0];
        }
        if (arraysLength == 2) {
            return appendPrepend0(arrays[0], arrays[1], ! reversed);
        }
        T notNull = null;
        int finalSize = 0;
        int nullArrays = 0;
        int[] lengths = new int[arraysLength];
        for (int i = 0; i < arraysLength; i++) {
            T array = arrays[i];
            int length = lengths[i] = Array.getLength(array);
            if ((array == null) || (length == 0)) {
                nullArrays++;
            }
            else {
                notNull = array;
                finalSize += length;
            }
        }
        if (nullArrays == arraysLength) {
            return (T) ArrayUtils.getEmptyArrayByArrayClass(componentType);
        }
        if (nullArrays == (arraysLength - 1)) {
            if (notNull == null) {
                return (T) ArrayUtils.getEmptyArrayByArrayClass(componentType);
            }
            return notNull;
        }
        T finalArray = (T) ArrayUtils.newArray(componentType, finalSize);
        int destPos = 0;

        if (reversed) {
            for (int i = (arraysLength - 1); i >= 0; i--) {
                destPos = arraycopy(arrays, i, lengths, finalArray, destPos);
            }
        }
        else {
            for (int i = 0; i < arraysLength; i++) {
                destPos = arraycopy(arrays, i, lengths, finalArray, destPos);
            }
        }
        return finalArray;
    }

    @SuppressWarnings("unchecked")
    static <T> T appendPrepend0(T arrayA, T arrayB, boolean append) {
        int lengthB = Array.getLength(arrayB);
        if (lengthB == 0) {
            return arrayA;
        }
        int lengthA = Array.getLength(arrayA);
        if (lengthA == 0) {
            return arrayB;
        }
        T array = (T) ArrayUtils.newArrayByArrayClass(arrayA.getClass(), lengthA + lengthB);
        copy(array, arrayA, arrayB, append);
        return array;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    private static <T> int arraycopy(T[] arrays, int i, int[] lengths, T finalArray, int destPos) {
        T array = arrays[i];
        int length = lengths[i];
        if ((array == null) || (length == 0)) {
            return destPos;
        }
        System.arraycopy(array, 0, finalArray, destPos, length);
        return destPos + length;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    private static <T> void copy(T array, T arrayA, T arrayB, boolean append) {
        if (append) {
            int lengthA = Array.getLength(arrayA);
            System.arraycopy(arrayA, 0, array, 0, lengthA);
            System.arraycopy(arrayB, 0, array, lengthA, Array.getLength(arrayB));
        }
        else {
            int lengthB = Array.getLength(arrayB);
            System.arraycopy(arrayB, 0, array, 0, lengthB);
            System.arraycopy(arrayA, 0, array, lengthB, Array.getLength(arrayA));
        }
    }
}
