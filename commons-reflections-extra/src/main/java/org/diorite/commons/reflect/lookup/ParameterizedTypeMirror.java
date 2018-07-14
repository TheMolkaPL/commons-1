package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface ParameterizedTypeMirror extends TypeMirror {
    /**
     * Returns an array of {@code Type} objects representing the actual type
     * arguments to this type.
     *
     * <p>Note that in some cases, the returned array be empty. This can occur
     * if this type represents a non-parameterized type nested within
     * a parameterized type.
     *
     * @return an array of {@code Type} objects representing the actual type
     *         arguments to this type
     */
    List<? extends TypeMirror> getActualTypeArguments();

    /**
     * Returns the {@code Type} object representing the class or interface
     * that declared this type.
     *
     * @return the {@code Type} object representing the class or interface
     *         that declared this type
     */
    TypeMirror getRawType();

    /**
     * Returns a {@code Type} object representing the type that this type
     * is a member of.  For example, if this type is {@code O<T>.I<S>},
     * return a representation of {@code O<T>}.
     *
     * <p>If this type is a top-level type, {@code null} is returned.
     *
     * @return a {@code Type} object representing the type that
     *         this type is a member of. If this type is a top-level type,
     *         {@code null} is returned
     */
    @Nullable
    TypeMirror getOwnerType();

    @Override
    ParameterizedType resolve() throws ResolveException;

    @Override
    ParameterizedType getIfResolved();

    static ParameterizedTypeMirror of(ParameterizedType type) {
        return MirrorUtils.cached(type);
    }
}
