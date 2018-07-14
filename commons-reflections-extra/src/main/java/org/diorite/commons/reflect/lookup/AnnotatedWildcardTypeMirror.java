package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.WildcardType;
import java.util.List;

public interface AnnotatedWildcardTypeMirror extends AnnotatedTypeMirror {
    /**
     * Returns the potentially annotated lower bounds of this wildcard type.
     * If no lower bound is explicitly declared, the lower bound is the
     * type of null. In this case, a zero length array is returned.
     *
     * @return the potentially annotated lower bounds of this wildcard type or
     *         an empty array if no lower bound is explicitly declared.
     *
     * @see WildcardType#getLowerBounds()
     */
    List<? extends AnnotatedTypeMirror> getAnnotatedLowerBounds();

    /**
     * Returns the potentially annotated upper bounds of this wildcard type.
     * If no upper bound is explicitly declared, the upper bound is
     * unannotated {@code Object}
     *
     * @return the potentially annotated upper bounds of this wildcard type
     *
     * @see WildcardType#getUpperBounds()
     */
    List<? extends AnnotatedTypeMirror> getAnnotatedUpperBounds();

    /**
     * Returns the potentially annotated type that this type is a member of, if
     * this type represents a nested type. For example, if this type is
     * {@code @TA O<T>.I<S>}, return a representation of {@code @TA O<T>}.
     *
     * <p>Returns {@code null} for an {@code AnnotatedType} that is an instance
     * of {@code AnnotatedWildcardType}.
     *
     * @return {@code null}
     */
    @Override
    @Nullable
    AnnotatedTypeMirror getAnnotatedOwnerType();

    @Override
    AnnotatedWildcardType resolve() throws ResolveException;

    @Override
    AnnotatedWildcardType getIfResolved();

    static AnnotatedWildcardTypeMirror of(AnnotatedWildcardType type) {
        return MirrorUtils.cached(type);
    }
}
