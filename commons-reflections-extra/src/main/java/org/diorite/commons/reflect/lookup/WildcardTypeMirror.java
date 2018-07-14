package org.diorite.commons.reflect.lookup;

import java.lang.reflect.WildcardType;
import java.util.List;

public interface WildcardTypeMirror extends TypeMirror {
    /**
     * Returns an array of {@code Type} objects representing the  upper
     * bound(s) of this type variable.  If no upper bound is
     * explicitly declared, the upper bound is {@code Object}.
     *
     * <p>For each upper bound B :
     * <ul>
     * <li>if B is a parameterized type or a type variable, it is created,
     * (see {@link java.lang.reflect.ParameterizedType ParameterizedType}
     * for the details of the creation process for parameterized types).
     * <li>Otherwise, B is resolved.
     * </ul>
     *
     * @return an array of Types representing the upper bound(s) of this
     *         type variable
     */
    List<? extends TypeMirror> getUpperBounds();

    /**
     * Returns an array of {@code Type} objects representing the
     * lower bound(s) of this type variable.  If no lower bound is
     * explicitly declared, the lower bound is the type of {@code null}.
     * In this case, a zero length array is returned.
     *
     * <p>For each lower bound B :
     * <ul>
     * <li>if B is a parameterized type or a type variable, it is created,
     * (see {@link java.lang.reflect.ParameterizedType ParameterizedType}
     * for the details of the creation process for parameterized types).
     * <li>Otherwise, B is resolved.
     * </ul>
     *
     * @return an array of Types representing the lower bound(s) of this
     *         type variable
     */
    List<? extends TypeMirror> getLowerBounds();
    // one or many? Up to language spec; currently only one, but this API
    // allows for generalization.

    @Override
    WildcardType resolve() throws ResolveException;

    @Override
    WildcardType getIfResolved();

    static WildcardTypeMirror of(WildcardType type) {
        return MirrorUtils.cached(new LoadedWildcardTypeMirror(type));
    }
}
