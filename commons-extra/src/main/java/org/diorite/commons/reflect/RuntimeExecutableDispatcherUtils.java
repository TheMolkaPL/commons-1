package org.diorite.commons.reflect;

import javax.annotation.Nullable;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class RuntimeExecutableDispatcherUtils {
    private RuntimeExecutableDispatcherUtils() { }

    @SuppressWarnings("unchecked")
    static <T> ReflectedMethod<T> findMatchingConstructor(Class<T> type,
                                                          Object... values) throws NoSuchMethodException, IllegalStateException {
        List<? extends ReflectedMethod<T>> methods = (List) Stream.of(type.getDeclaredConstructors())
                                                                  .map(ReflectedMethod::fromConstructor)
                                                                  .collect(Collectors.toList());
        return findMatchingExecutable(methods, values);
    }

    static <T> ReflectedMethod<T> findMatchingMethod(Class<?> type, String methodName,
                                                     Object... values) throws NoSuchMethodException, IllegalStateException {
        List<ReflectedMethod<T>> methodList = new ArrayList<>(20);
        Class<?> currentType = type;
        while ((currentType != null) && ! currentType.equals(Object.class)) {
            for (Method method: currentType.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && (method.getParameterCount() == values.length)) {
                    methodList.add(ReflectedMethod.fromMethod(method));
                }
            }
            currentType = currentType.getSuperclass();
        }
        return findMatchingExecutable(methodList, values);
    }

    static <T> ReflectedMethod<T> findMatchingExecutable(Collection<? extends ReflectedMethod<T>> executables,
                                                         Object... values) throws NoSuchMethodException, IllegalStateException {
        if (values.length == 0) {
            for (ReflectedMethod<T> executable: executables) {
                if (executable.getParameterCount() == 0) {
                    return executable;
                }
            }
            throw new NoSuchMethodException("Can't find no-args executable.");
        }
        Class<?>[] paramTypes = new Class<?>[values.length];
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            paramTypes[i] = (value == null) ? null : value.getClass();
        }
        return findBest(executables, paramTypes);
    }

    static <T> ReflectedMethod<T> wrap(Executable executable, boolean setAccessible) {
        ReflectedMethod<T> reflectedMethod = ReflectedMethod.fromExecutable(executable);
        return setAccessible ? reflectedMethod.ensureAccessible() : reflectedMethod;
    }

    static <T> ReflectedMethod<T> findBest(Collection<? extends ReflectedMethod<T>> methods, Type[] paramTypes) {
        // try to find exact matching constructor, and add any just compatible to collection.
        int exactMatches = 0;
        ReflectedMethod<T> exact = null;
        ReflectedMethod<T> bestMatch = null;
        for (ReflectedMethod<T> executable: methods) {
            CompatibleExecutableResults compatibleConstructor = isCompatibleExecutable(executable, paramTypes);
            if (compatibleConstructor == CompatibleExecutableResults.EXACT) {
                if (exactMatches >= 1) {
                    throw new IllegalStateException("Ambiguous executables found " + Arrays.toString(paramTypes));
                }
                exact = executable;
                exactMatches += 1;
            }
            if (compatibleConstructor != CompatibleExecutableResults.INVALID) {
                bestMatch = getMoreSpecialized(bestMatch, executable);
            }
        }
        if (bestMatch == null) {
            throw new IllegalStateException("Can't find matching executable for: " + Arrays.toString(paramTypes));
        }
        if (exact != null) {
            if (! bestMatch.equals(exact)) {
                throw new IllegalStateException(
                        "Ambiguous executables found " + Arrays.toString(paramTypes) + "\n    A: " + bestMatch + "\n    B: " + exact);
            }
            return exact;
        }
        return bestMatch;
    }

    @Nullable
    static <T> ReflectedMethod<T> getMoreSpecialized(@Nullable ReflectedMethod<T> a, @Nullable ReflectedMethod<T> b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        Class<?>[] aTypes = a.getParameterTypes();
        Class<?>[] bTypes = b.getParameterTypes();
        int result = 0;
        for (int i = 0; i < aTypes.length; i++) {
            Class<?> aType = aTypes[i];
            Class<?> bType = bTypes[i];
            if (aType.equals(bType)) {
                continue;
            }
            // if aType is less specialized than bType
            if ((aType.isPrimitive() && ! bType.isPrimitive()) ||
                        ReflectionUtils.getWrapperClass(aType).isAssignableFrom(ReflectionUtils.getWrapperClass(bType))) {
                // one of prev types was less specialized, javac fails to find such constructor, we should too
                if (result < 0) {
                    throw new IllegalStateException(
                            "Ambiguous constructors found for: " + Arrays.toString(aTypes) + " and " + Arrays.toString(bTypes));
                }
                result += 1;
            }
            else {
                if (result > 0) {
                    throw new IllegalStateException(
                            "Ambiguous constructors found for: " + Arrays.toString(aTypes) + " and " + Arrays.toString(bTypes));
                }
                result -= 1;
            }
        }
        if (result == 0) {
            throw new IllegalStateException(
                    "Ambiguous constructors found for: " + Arrays.toString(aTypes) + " and " + Arrays.toString(bTypes));
        }
        if (result < 0) {
            return a;
        }
        return b;
    }

    private static CompatibleExecutableResults isCompatibleExecutable(ReflectedMethod method, Type[] providedTypes) {
        Class<?>[] constructorParameterTypes = method.getParameterTypes();
        boolean compatible = true;
        CompatibleExecutableResults current = CompatibleExecutableResults.EXACT;
        for (int i = 0; i < constructorParameterTypes.length; i++) {
            Class<?> providedType = (providedTypes[i] == null) ? null : ReflectionUtils.getRawType(providedTypes[i]);
            Class<?> parameterType = constructorParameterTypes[i];

            // null can't be used as primitive
            if ((providedType == null) && parameterType.isPrimitive()) {
                return CompatibleExecutableResults.INVALID;
            }

            // handle primitives correctly by using wrapped type as boolean.class.isAssignableFrom(Boolean.class) => false
            if ((providedType != null) && ! ReflectionUtils.getWrapperClass(parameterType).isAssignableFrom(providedType)) {
                return CompatibleExecutableResults.INVALID;
            }

            if ((providedType == null) || parameterType.equals(providedType)) {
                continue; // sill exact match
            }
            current = CompatibleExecutableResults.COMPATIBLE;
        }
        return current;
    }

    enum CompatibleExecutableResults {
        EXACT,
        COMPATIBLE,
        INVALID
    }
}
