package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedType;

public interface AnnotatedTypeMirror extends AnnotatedElementMirror {
    /**
     * Returns the potentially annotated type that this type is a member of, if
     * this type represents a nested type. For example, if this type is
     * {@code @TA O<T>.I<S>}, return a representation of {@code @TA O<T>}.
     *
     * <p>Returns {@code null} if this {@code AnnotatedType} represents a
     * top-level type, or a local or anonymous class, or a primitive type, or
     * void.
     *
     * <p>Returns {@code null} if this {@code AnnotatedType} is an instance of
     * {@code AnnotatedArrayType}, {@code AnnotatedTypeVariable}, or
     * {@code AnnotatedWildcardType}.
     *
     * @return an {@code AnnotatedType} object representing the potentially
     *         annotated type that this type is a member of, or {@code null}
     *
     * @throws TypeNotPresentException if the owner type
     *         refers to a non-existent type declaration
     * @implSpec This default implementation returns {@code null} and performs no other
     *         action.
     */
    @Nullable
    default AnnotatedTypeMirror getAnnotatedOwnerType() {
        return null;
    }

    /**
     * Returns the underlying type that this annotated type represents.
     *
     * @return the type this annotated type represents
     */
    TypeMirror getType();

    @Override
    AnnotatedType resolve() throws ResolveException;

    @Override
    AnnotatedType getIfResolved();

    static AnnotatedTypeMirror of(AnnotatedType type) {
        return MirrorUtils.cached(type);
    }
}
