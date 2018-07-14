package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.GenericArrayType;

public interface AnnotatedArrayTypeMirror extends AnnotatedTypeMirror {
    /**
     * Returns the potentially annotated generic component type of this array type.
     *
     * @return the potentially annotated generic component type of this array type
     *
     * @see GenericArrayType#getGenericComponentType()
     */
    AnnotatedTypeMirror getAnnotatedGenericComponentType();

    /**
     * Returns the potentially annotated type that this type is a member of, if
     * this type represents a nested type. For example, if this type is
     * {@code @TA O<T>.I<S>}, return a representation of {@code @TA O<T>}.
     *
     * <p>Returns {@code null} for an {@code AnnotatedType} that is an instance
     * of {@code AnnotatedArrayType}.
     *
     * @return {@code null}
     */
    @Nullable
    @Override
    AnnotatedTypeMirror getAnnotatedOwnerType();

    @Override
    AnnotatedArrayType resolve() throws ResolveException;

    @Override
    AnnotatedArrayType getIfResolved();

    static AnnotatedArrayTypeMirror of(AnnotatedArrayType type) {
        return MirrorUtils.cached(type);
    }
}
