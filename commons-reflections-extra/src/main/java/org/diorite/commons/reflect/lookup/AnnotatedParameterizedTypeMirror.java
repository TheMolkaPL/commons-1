package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface AnnotatedParameterizedTypeMirror extends AnnotatedTypeMirror {

    /**
     * Returns the potentially annotated actual type arguments of this parameterized type.
     *
     * @return the potentially annotated actual type arguments of this parameterized type
     *
     * @see ParameterizedType#getActualTypeArguments()
     */
    List<? extends AnnotatedTypeMirror> getAnnotatedActualTypeArguments();

    /**
     * Returns the potentially annotated type that this type is a member of, if
     * this type represents a nested type. For example, if this type is
     * {@code @TA O<T>.I<S>}, return a representation of {@code @TA O<T>}.
     *
     * <p>Returns {@code null} if this {@code AnnotatedType} represents a
     * top-level type, or a local or anonymous class, or a primitive type, or
     * void.
     *
     * @return an {@code AnnotatedType} object representing the potentially
     *         annotated type that this type is a member of, or {@code null}
     */
    @Override
    @Nullable
    AnnotatedTypeMirror getAnnotatedOwnerType();

    @Override
    AnnotatedParameterizedType resolve() throws ResolveException;

    @Override
    AnnotatedParameterizedType getIfResolved();

    static AnnotatedParameterizedTypeMirror of(AnnotatedParameterizedType type) {
        return MirrorUtils.cached(type);
    }
}
