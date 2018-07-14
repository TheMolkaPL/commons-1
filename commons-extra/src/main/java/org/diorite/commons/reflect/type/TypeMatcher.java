package org.diorite.commons.reflect.type;

import org.diorite.commons.function.predicate.Predicate;
import org.diorite.commons.reflect.ReflectionUtils;

import java.lang.reflect.Type;

@FunctionalInterface
public interface TypeMatcher<T> extends Predicate<Type> {
    default boolean test(TypeToken<?> typeToken) {
        return this.test(typeToken.getType());
    }

    @SuppressWarnings("unchecked")
    static <T> TypeMatcher<T> isClass(Class<T> clazz) {
        return type -> ReflectionUtils.getRawType(type) == clazz;
    }

    static <T> TypeMatcher<T> is(Type otherType) {
        return type -> TypeToken.get(type).equals(TypeToken.get(otherType));
    }

    static <T> TypeMatcher<? extends T> assignable(Type otherType) {
        return type -> ReflectionUtils.isAssignable(type, otherType);
    }

    static <T> TypeMatcher<? extends T> accepting(Type otherType) {
        return type -> ReflectionUtils.isAssignable(otherType, type);
    }

    static <T> TypeMatcher<T> is(TypeToken<T> otherType) {
        return type -> TypeToken.get(type).equals(otherType);
    }

    static <T> TypeMatcher<? extends T> assignable(TypeToken<T> otherType) {
        return type -> ReflectionUtils.isAssignable(type, otherType.getType());
    }

    static <T> TypeMatcher<? extends T> accepting(TypeToken<T> otherType) {
        return type -> ReflectionUtils.isAssignable(otherType.getType(), type);
    }
}
