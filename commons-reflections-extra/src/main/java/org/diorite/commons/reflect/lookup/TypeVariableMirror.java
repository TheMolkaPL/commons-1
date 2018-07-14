package org.diorite.commons.reflect.lookup;

import java.lang.reflect.TypeVariable;
import java.util.List;

public interface TypeVariableMirror extends TypeMirror, AnnotatedElementMirror {

    /**
     * Returns an array of {@code Type} objects representing the
     * upper bound(s) of this type variable.  If no upper bound is
     * explicitly declared, the upper bound is {@code Object}.
     *
     * <p>For each upper bound B: <ul> <li>if B is a parameterized
     * type or a type variable, it is created, (see {@link
     * java.lang.reflect.ParameterizedType ParameterizedType} for the
     * details of the creation process for parameterized types).
     * <li>Otherwise, B is resolved.  </ul>
     *
     * @return an array of {@code Type}s representing the upper
     *         bound(s) of this type variable
     */
    List<? extends TypeMirror> getBounds();

    /**
     * Returns the name of this type variable, as it occurs in the source code.
     *
     * @return the name of this type variable, as it appears in the source code
     */
    String getName();

    /**
     * Returns an array of AnnotatedType objects that represent the use of
     * types to denote the upper bounds of the type parameter represented by
     * this TypeVariable. The order of the objects in the array corresponds to
     * the order of the bounds in the declaration of the type parameter. Note that
     * if no upper bound is explicitly declared, the upper bound is unannotated
     * {@code Object}.
     *
     * @return an array of objects representing the upper bound(s) of the type variable
     */
    List<? extends AnnotatedTypeMirror> getAnnotatedBounds();

    @Override
    TypeVariable<?> resolve() throws ResolveException;

    @Override
    TypeVariable<?> getIfResolved();

    static TypeVariableMirror of(TypeVariable type) {
        return MirrorUtils.cached(type);
    }
}
