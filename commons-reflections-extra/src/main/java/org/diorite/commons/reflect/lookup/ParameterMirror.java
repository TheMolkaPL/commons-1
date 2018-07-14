package org.diorite.commons.reflect.lookup;

import org.diorite.commons.object.Named;

import java.lang.reflect.Parameter;

/**
 * Represents parameter of method/constructor
 */
public interface ParameterMirror extends HasModifiers, CanBeFinal, AnnotatedElementMirror, Named {
    boolean isNamePresent();

    String getType();

    TypeMirror getGenericType();

    AnnotatedTypeMirror getAnnotatedType();

    boolean isVarargs();

    /**
     * Returns {@code true} if this parameter is implicitly declared
     * in source code; returns {@code false} otherwise.
     *
     * @return true if and only if this parameter is implicitly
     *         declared as defined by <cite>The Java&trade; Language
     *         Specification</cite>.
     */
    default boolean isImplicit() {
        return this.checkModifier(ExtraModifiers.MANDATED);
    }

    /**
     * Returns {@code true} if this parameter is neither implicitly
     * nor explicitly declared in source code; returns {@code false}
     * otherwise.
     *
     * @return true if and only if this parameter is a synthetic
     *         construct as defined by
     *         <cite>The Java&trade; Language Specification</cite>.
     */
    default boolean isSynthetic() {
        return this.checkModifier(ExtraModifiers.SYNTHETIC);
    }

    @Override
    Parameter resolve() throws ResolveException;

    @Override
    Parameter getIfResolved();

    static ParameterMirror of(Parameter parameter) {
        return MirrorUtils.cached(parameter);
    }
}
