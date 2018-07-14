package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represent mirrored field data of class that might not be loaded or even exist in runtime.
 */
public interface FieldMirror extends CanBeFinal, MemberMirror, Resolvable {
    /**
     * @return type of this field
     */
    String getType();

    /**
     * @return generic type of this field.
     */
    TypeMirror getGenericType();

    default boolean isStatic() {
        return this.checkModifier(Modifier.STATIC);
    }

    default boolean isVolatile() {
        return this.checkModifier(Modifier.VOLATILE);
    }

    default boolean isTransient() {
        return this.checkModifier(Modifier.TRANSIENT);
    }

    default boolean isSynthetic() {
        return this.checkModifier(ExtraModifiers.SYNTHETIC);
    }

    default boolean isEnumConstant() {
        return this.checkModifier(ExtraModifiers.ENUM);
    }

    @Override
    Field resolve() throws ResolveException;

    @Override
    Field getIfResolved();

    static FieldMirror of(Field field) {
        return MirrorUtils.cached(field);
    }
}
