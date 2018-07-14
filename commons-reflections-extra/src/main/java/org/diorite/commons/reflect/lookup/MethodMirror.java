package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Represent mirrored field data of class that might not be loaded or even exist in runtime. <br>
 * This can represent constructors too.
 */
public interface MethodMirror extends MemberMirror {

    /**
     * @return true if this is a constructor method.
     */
    boolean isConstructor();

    /**
     * @return return type of this method
     */
    String getReturnType();

    /**
     * Get return type as type mirror, this is not guaranteed to be ClassMirror if this is raw type - it might be UnresolvedRawTypeMirror
     *
     * @return generic return type of this method.
     */
    TypeMirror getGenericReturnType();

    /**
     * @return return type as annotated type.
     */
    AnnotatedTypeMirror getAnnotatedReturnType();

    /**
     * @return return "this" argument as annotated type
     */
    AnnotatedTypeMirror getAnnotatedReceiverType();

    /**
     * @return list of exception types.
     */
    List<? extends String> getExceptionTypes();

    /**
     * Get set of exception generic types. Types are not guaranteed to be ClassMirror if they are raw type - it might be
     * UnresolvedRawTypeMirror
     *
     * @return set of exception generic types.
     */
    List<? extends TypeMirror> getGenericExceptionTypes();

    /**
     * @return set of exception annotated types.
     */
    List<? extends AnnotatedTypeMirror> getAnnotatedExceptionTypes();

    /**
     * @return list of parameters.
     */
    List<? extends String> getParameterTypes();

    /**
     * @return list of parameters.
     */
    List<? extends ParameterMirror> getParameters();

    /**
     * @return list of type variables.
     */
    List<? extends TypeVariableMirror> getTypeParameters();

    default boolean isFinal() {
        return this.checkModifier(Modifier.FINAL);
    }

    default boolean isStatic() {
        return this.checkModifier(Modifier.STATIC);
    }

    default boolean isAbstract() {
        return this.checkModifier(Modifier.ABSTRACT);
    }

    default boolean isSynchronized() {
        return this.checkModifier(Modifier.SYNCHRONIZED);
    }

    default boolean isNative() {
        return this.checkModifier(Modifier.NATIVE);
    }

    default boolean isBridge() {
        return this.checkModifier(ExtraModifiers.BRIDGE);
    }

    default boolean isVarargs() {
        return this.checkModifier(ExtraModifiers.VARARGS);
    }

    @Override
    Executable resolve() throws ResolveException;

    @Override
    Executable getIfResolved();

    static MethodMirror of(Executable executable) {
        return MirrorUtils.cached(executable);
    }
}
