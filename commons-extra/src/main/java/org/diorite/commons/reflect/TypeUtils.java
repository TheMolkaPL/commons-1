package org.diorite.commons.reflect;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Objects;

final class TypeUtils {
    private TypeUtils() {}

    public static boolean isArray(Type type) {
        return getRawType(type).isArray();
    }

    static Class<?> getRawType(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            assert rawType instanceof Class;
            return (Class<?>) rawType;
        }
        else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        else if (type instanceof TypeVariable) {
            Type[] bounds = ((TypeVariable) type).getBounds();
            if (bounds.length == 0) {
                return Object.class;
            }
            Class<?> rawTop = null;
            for (Type bound: bounds) {
                Class<?> raw = getRawType(bound);
                if (raw == Object.class) {
                    continue;
                }
                if (rawTop == null) {
                    rawTop = raw;
                    continue;
                }
                if (rawTop.isAssignableFrom(raw)) {
                    rawTop = raw;
                    continue;
                }
                if (! raw.isInterface()) {
                    rawTop = raw;
                }
            }
            return Objects.requireNonNullElse(rawTop, Object.class);
        }
        else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }
        String className = type.getClass().getName();
        throw new IllegalArgumentException(
                "Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
    }

    static boolean isAssignable(@Nullable Type objectType, Type variableType) {
        return ApacheTypeUtils.isAssignable(objectType, variableType);
    }

    static boolean isAssignable(@Nullable Class<?> objectType, final Class<?> variableType, final boolean autoboxing) {
        if (objectType == null) {
            return ! variableType.isPrimitive();
        }
        if (objectType == variableType) {
            return true;
        }

        if (autoboxing) {
            if (objectType.isPrimitive() && ! variableType.isPrimitive()) {
                objectType = ReflectionUtils.getWrapperClass(objectType);
            }
            if (variableType.isPrimitive() && ! objectType.isPrimitive()) {
                objectType = ReflectionUtils.getPrimitive(objectType);
                if (! objectType.isPrimitive()) {
                    return false;
                }
            }
        }

        if (objectType == variableType) {
            return true;
        }

        if (objectType.isPrimitive()) {
            if (! variableType.isPrimitive()) {
                return false;
            }
            if (Integer.TYPE.equals(objectType)) {
                return Long.TYPE.equals(variableType) || Float.TYPE.equals(variableType) || Double.TYPE.equals(variableType);
            }
            if (Long.TYPE.equals(objectType)) {
                return Float.TYPE.equals(variableType) || Double.TYPE.equals(variableType);
            }
            if (Boolean.TYPE.equals(objectType)) {
                return false;
            }
            if (Double.TYPE.equals(objectType)) {
                return false;
            }
            if (Float.TYPE.equals(objectType)) {
                return Double.TYPE.equals(variableType);
            }
            if (Character.TYPE.equals(objectType)) {
                return Integer.TYPE.equals(variableType)
                               || Long.TYPE.equals(variableType)
                               || Float.TYPE.equals(variableType)
                               || Double.TYPE.equals(variableType);
            }
            if (Short.TYPE.equals(objectType)) {
                return Integer.TYPE.equals(variableType)
                               || Long.TYPE.equals(variableType)
                               || Float.TYPE.equals(variableType)
                               || Double.TYPE.equals(variableType);
            }
            if (Byte.TYPE.equals(objectType)) {
                return Short.TYPE.equals(variableType)
                               || Integer.TYPE.equals(variableType)
                               || Long.TYPE.equals(variableType)
                               || Float.TYPE.equals(variableType)
                               || Double.TYPE.equals(variableType);
            }
            return false;
        }
        return variableType.isAssignableFrom(objectType);
    }

    static boolean isAssignable(TypeVariable<?> typeVariable, Type objectType) {
        if (objectType instanceof WildcardType) {
            WildcardType objectWildcardType = (WildcardType) objectType;
            for (Type type: objectWildcardType.getUpperBounds()) {
                if (! isAssignable(typeVariable, type)) {
                    return false;
                }
            }
            for (Type type: objectWildcardType.getLowerBounds()) {
                if (! isAssignable(typeVariable, type)) {
                    return false;
                }
            }
            return true;
        }
        for (Type bounds: typeVariable.getBounds()) {
            if (bounds instanceof TypeVariable) {
                if (! isAssignable(((TypeVariable) bounds), objectType)) {
                    return false;
                }
                continue;
            }
            if (bounds instanceof WildcardType) {
                WildcardType wildcardTypeBounds = (WildcardType) bounds;
                for (Type bound: wildcardTypeBounds.getUpperBounds()) {
                    if (! isAssignable(objectType, bound)) {
                        return false;
                    }
                }
                for (Type bound: wildcardTypeBounds.getLowerBounds()) {
                    if (! isAssignable(objectType, bound)) {
                        return false;
                    }
                }
                continue;
            }
            if (! isAssignable(objectType, bounds)) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    static <X extends GenericDeclaration> TypeVariable<X> typeVariableOf(X of, String name) throws IllegalStateException {
        TypeVariable<?> result = null;
        for (TypeVariable<?> typeVariable: of.getTypeParameters()) {
            if (typeVariable.getName().equals(name)) {
                if (result != null) {
                    throw new IllegalStateException(
                            "Duplicated parameter names, more than ore parameter use " + name + " as its name (probably code is " +
                                    "obfuscated).");
                }
                result = typeVariable;
            }
        }
        return (TypeVariable<X>) result;
    }

    @SuppressWarnings("unchecked")
    static <X extends GenericDeclaration> TypeVariable<X> typeVariableOf(X of, int index) {
        return (TypeVariable<X>) of.getTypeParameters()[index];
    }

}
