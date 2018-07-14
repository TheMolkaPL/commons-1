package org.diorite.commons.reflect;

import javax.annotation.Nullable;
import java.util.Map;

final class ClassUtils {
    private ClassUtils() {}

    //@formatter:off
    private static final Map<Class<?>, Class<?>> primitives = Map.of(
        Boolean.class,   boolean.class,     Byte.class,    byte.class,        Short.class, short.class,
        Character.class, char.class,        Integer.class, int.class,         Long.class,  long.class,
        Float.class,     float.class,       Double.class,  double.class,      Void.class,  void.class);
    private static final Map<Class<?>, Class<?>> wrappers   = Map.of(
        boolean.class, Boolean.class,       byte.class,   Byte.class,         short.class, Short.class,
        char.class,    Character.class,     int.class,    Integer.class,      long.class,  Long.class,
        float.class,   Float.class,         double.class, Double.class,       void.class,  Void.class);
    //@formatter:on

    static Class<?> getCanonicalClass(String canonicalName, ClassLoader... loaders) throws IllegalArgumentException {
        try {
            return Class.forName(canonicalName);
        }
        catch (ClassNotFoundException e) {
            try {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                if (contextClassLoader == null) {
                    throw new IllegalArgumentException("Cannot find " + canonicalName, e);
                }
                return contextClassLoader.loadClass(canonicalName);
            }
            catch (ClassNotFoundException e2) {
                e2.addSuppressed(e);
                for (ClassLoader loader: loaders) {
                    try {
                        return loader.loadClass(canonicalName);
                    }
                    catch (ClassNotFoundException suppressed) {
                        e2.addSuppressed(suppressed);
                    }
                }
                throw new IllegalArgumentException("Cannot find " + canonicalName, e2);
            }
        }
    }

    @Nullable
    static Class<?> tryGetCanonicalClass(String canonicalName, ClassLoader... loaders) {
        try {
            return Class.forName(canonicalName);
        }
        catch (ClassNotFoundException e) {
            try {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                if (contextClassLoader == null) {
                    return null;
                }
                return contextClassLoader.loadClass(canonicalName);
            }
            catch (ClassNotFoundException e2) {
                for (ClassLoader loader: loaders) {
                    try {
                        return loader.loadClass(canonicalName);
                    }
                    catch (ClassNotFoundException ignored) {
                    }
                }
                return null;
            }
        }
    }

    static Class<?> getNestedClass(Class<?> clazz, @Nullable String name) {
        Class<?> base = clazz;
        do {
            for (Class<?> nc: clazz.getDeclaredClasses()) {
                if ((name == null) || nc.getSimpleName().equals(name)) {
                    return nc;
                }
            }
            clazz = clazz.getSuperclass();
        }
        while (clazz != null);
        throw new IllegalStateException("Unable to find nested class: " + name + " in " + base);
    }

    @SuppressWarnings("ObjectEquality")
    static Class<?> getPrimitive(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return clazz;
        }
        return primitives.getOrDefault(clazz, clazz);
    }

    @SuppressWarnings("ObjectEquality")
    static Class<?> getWrapperClass(Class<?> clazz) {
        if (! clazz.isPrimitive()) {
            return clazz;
        }
        return wrappers.getOrDefault(clazz, clazz);
    }
}
