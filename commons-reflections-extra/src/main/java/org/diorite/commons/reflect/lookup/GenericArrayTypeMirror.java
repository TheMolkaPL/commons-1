package org.diorite.commons.reflect.lookup;

import java.lang.reflect.GenericArrayType;

public interface GenericArrayTypeMirror extends TypeMirror {
    /**
     * Returns a {@code Type} object representing the component type
     * of this array. This method creates the component type of the
     * array.  See the declaration of {@link
     * java.lang.reflect.ParameterizedType ParameterizedType} for the
     * semantics of the creation process for parameterized types and
     * see {@link java.lang.reflect.TypeVariable TypeVariable} for the
     * creation process for type variables.
     *
     * @return a {@code Type} object representing the component type
     *         of this array
     */
    TypeMirror getGenericComponentType();

    @Override
    GenericArrayType resolve() throws ResolveException;

    @Override
    GenericArrayType getIfResolved();

    static GenericArrayTypeMirror of(GenericArrayType type) {
        return MirrorUtils.cached(type);
    }
}
