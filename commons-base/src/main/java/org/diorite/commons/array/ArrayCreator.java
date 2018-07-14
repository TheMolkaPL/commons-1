package org.diorite.commons.array;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class ArrayCreator {
    private static final Map<Class<?>, Object> arrayMaps = new ConcurrentHashMap<>(20);

    private ArrayCreator() {}

    @SuppressWarnings("unchecked")
    static <T> T[] getEmptyObjectArray(Class<T> clazz) throws IllegalArgumentException {
        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException("Can't create array of primitive type: " + clazz);
        }
        Object o = arrayMaps.get(clazz);
        if (o != null) {
            return (T[]) o;
        }
        T[] array = (T[]) Array.newInstance(clazz, 0);
        arrayMaps.put(clazz, array);
        return array;
    }

    @SuppressWarnings("unchecked")
    static <T> T[] getCachedEmptyArray(T[] array) {
        if (array.length != 0) {
            return array;
        }
        Class<?> componentType = array.getClass().getComponentType();
        Object o = arrayMaps.get(componentType);
        if (o != null) {
            return (T[]) o;
        }
        arrayMaps.put(componentType, array);
        return array;
    }

    static Object getEmptyArray(Class<?> clazz) {
        Object o = arrayMaps.get(clazz);
        if (o != null) {
            return o;
        }
        Object array = Array.newInstance(clazz, 0);
        arrayMaps.put(clazz, array);
        return array;
    }

    @SuppressWarnings("unchecked")
    static <T> T getEmptyArrayByArrayClass(Class<T> clazz) throws IllegalArgumentException {
        if (! clazz.isArray()) {
            throw new IllegalArgumentException("Class must be array type: " + clazz);
        }
        Object o = arrayMaps.get(clazz);
        if (o != null) {
            return (T) o;
        }
        T array = (T) Array.newInstance(clazz.getComponentType(), 0);
        arrayMaps.put(clazz, array);
        return array;
    }

    @SuppressWarnings("unchecked")
    static <T> T[] newObjectArray(Class<? extends T> clazz, int size) throws IllegalArgumentException {
        if (size == 0) {
            return getEmptyObjectArray(clazz);
        }
        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException("Can't create array of primitive type: " + clazz);
        }
        return (T[]) Array.newInstance(clazz, size);
    }

    static Object newArray(Class<?> clazz, int size) {
        if (size == 0) {
            return getEmptyArray(clazz);
        }
        return Array.newInstance(clazz, size);
    }

    @SuppressWarnings("unchecked")
    static <T> T newArrayByArrayClass(Class<? extends T> clazz, int size) throws IllegalArgumentException {
        if (size == 0) {
            return getEmptyArrayByArrayClass(clazz);
        }
        if (! clazz.isArray()) {
            throw new IllegalArgumentException("Class must be array type: " + clazz);
        }
        return (T) Array.newInstance(clazz.getComponentType(), size);
    }

}
