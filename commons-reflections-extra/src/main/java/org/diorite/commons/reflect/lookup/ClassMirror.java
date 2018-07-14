package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Represents class mirror
 */
public interface ClassMirror extends HasModifiers, HasAccessFlags, CanBeFinal, AnnotatedElementMirror, TypeMirror {
    String getSimpleName();

    String getCanonicalName();

    String getPackageName();

    @Nullable
    String getSuperclass();

    List<? extends String> getInterfaces();

    List<? extends MethodMirror> getDeclaredExecutables();

    List<? extends MemberMirror> getDeclaredMembers();

    List<? extends MethodMirror> getDeclaredConstructors();

    @Nullable
    MethodMirror getDeclaredConstructor(String... argumentTypes);

    @Nullable
    MethodMirror getDeclaredConstructor(Class<?>... argumentTypes);

    @Nullable
    MethodMirror getDeclaredConstructorBySignature(String signature);

    List<? extends MethodMirror> getDeclaredMethods();

    @Nullable
    MethodMirror getDeclaredMethodExact(String returnType, String name, String... argumentTypes);

    @Nullable
    MethodMirror getDeclaredMethodExact(Class<?> returnType, String name, Class<?>... argumentTypes);

    @Nullable
    MethodMirror getDeclaredMethodBySignature(String name, String descriptor);

    @Nullable
    MethodMirror getDeclaredMethod(String name, String... argumentTypes);

    @Nullable
    MethodMirror getDeclaredMethod(String name, Class<?>... argumentTypes);

    List<? extends FieldMirror> getDeclaredFields();

    @Nullable
    FieldMirror getDeclaredFieldExact(String returnType, String name);

    @Nullable
    FieldMirror getDeclaredFieldExact(Class<?> returnType, String name);

    @Nullable
    FieldMirror getDeclaredField(String name);

    @Nullable
    FieldMirror getDeclaredFieldBySignature(String name, String descriptor);

    List<? extends FieldMirror> getEnumFields();

    @Nullable
    String getEnclosingClass();

    @Nullable
    String getDeclaringClass();

    List<? extends String> getDeclaredClasses();

    @Nullable
    TypeMirror getGenericSuperclass();

    List<? extends TypeMirror> getGenericInterfaces();

    @Nullable
    MethodMirror getEnclosingMethod();

    /**
     * @return list of type variables.
     */
    List<? extends TypeVariableMirror> getTypeParameters();

    default boolean isInterface() {
        return Modifier.isInterface(this.getModifiers());
    }

    default boolean isAbstract() {
        return Modifier.isAbstract(this.getModifiers());
    }

    default boolean isEnum() {
        return this.checkModifier(ExtraModifiers.ENUM);
    }

    default boolean isSynthetic() {
        return this.checkModifier(ExtraModifiers.SYNTHETIC);
    }

    default boolean isAnnotation() {
        return this.checkModifier(ExtraModifiers.ANNOTATION);
    }

    boolean isArray();

    boolean isPrimitive();

    boolean isAnonymousClass();

    boolean isLocalClass();

    boolean isMemberClass();

    @Override
    Class<?> resolve() throws ResolveException;

    @Override
    Class<?> getIfResolved();

    static ClassMirror of(Class<?> type) {
        return MirrorUtils.cached(type);
    }
}
