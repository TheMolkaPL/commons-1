package org.diorite.commons.reflect;

import org.diorite.commons.array.ArrayUtils;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;

public final class ReflectionUtils {

    private ReflectionUtils() {}

    @SuppressWarnings("unchecked")
    public static <T, R> FieldLookup<T, R> uncheckedFieldLookupIn(Class inClass) {
        return (FieldLookup<T, R>) fieldLookupIn(inClass);
    }

    public static <T> FieldLookup<T, Object> fieldLookupIn(Class<T> inClass) {
        return new FieldLookup<>(inClass).fields();
    }

    @SuppressWarnings("unchecked")
    public static <T, R> MethodLookup<T, R> uncheckedMethodLookupIn(Class inClass) {
        return (MethodLookup<T, R>) methodLookupIn(inClass);
    }

    public static <T> MethodLookup<T, Object> methodLookupIn(Class<T> inClass) {
        return new MethodLookup<>(inClass).methods();
    }

    public static <T> MethodLookup<T, T> constructorLookupIn(Class<T> inClass) {
        return methodLookupIn(inClass).constructors();
    }

    public static boolean isArray(Type type) {
        return TypeUtils.isArray(type);
    }

    /**
     * Try to get URL of class, method might fail if class does not provide any information about its location (dynamically injected
     * classes etc)
     *
     * @param clazz class to find.
     *
     * @return URL of class
     */
    @Nullable
    public static URL findClassLocation(Class<?> clazz) {
        Class<?> enclosingClass = clazz;
        while (enclosingClass.getEnclosingClass() != null) {
            enclosingClass = enclosingClass.getEnclosingClass();
        }
        return enclosingClass.getResource(enclosingClass.getSimpleName() + ".class");
    }

    /**
     * Method will try find simple method setter and getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find element.
     * @param <T> type of field.
     *
     * @return Element for field value.
     */
    static <T> ReflectedProperty<T> getReflectedProperty(Field field) {
        return PropertyUtils.getReflectedProperty(field);
    }

    /**
     * Method will try find simple method setter and getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param fieldName name of field to find element.
     * @param clazz clazz with this field.
     * @param <T> type of field.
     *
     * @return Element for field value.
     */
    static <T> ReflectedProperty<T> getReflectedProperty(String fieldName, Class<?> clazz) {
        return PropertyUtils.getReflectedProperty(fieldName, clazz);
    }

    /**
     * Method will try find simple method setter for selected field,
     * or directly use field if method can't be found.
     *
     * @param fieldName name of field to find setter.
     * @param clazz clazz with this field.
     * @param <T> type of field.
     *
     * @return Setter for field value.
     */
    static <T> ReflectedSetter<T> getReflectSetter(String fieldName, Class<?> clazz) {
        return PropertyUtils.getReflectSetter(fieldName, clazz);
    }

    /**
     * Method will try find simple method setter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find setter.
     * @param <T> type of field.
     *
     * @return Setter for field value.
     */
    static <T> ReflectedSetter<T> getReflectSetter(Field field) {
        return PropertyUtils.getReflectSetter(field);
    }

    /**
     * Method will try find simple method getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param fieldName name of field to find getter.
     * @param clazz clazz with this field.
     * @param <T> type of field.
     *
     * @return Getter for field value.
     */
    static <T> ReflectedGetter<T> getReflectGetter(String fieldName, Class<?> clazz) {
        return PropertyUtils.getReflectGetter(fieldName, clazz);
    }

    /**
     * Method will try find simple method getter for selected field,
     * or directly use field if method can't be found.
     *
     * @param field field to find getter.
     * @param <T> type of field.
     *
     * @return Getter for field value.
     */
    static <T> ReflectedGetter<T> getReflectGetter(Field field) {
        return PropertyUtils.getReflectGetter(field);
    }

    /**
     * Returns raw type of given type, for class returns class itself, for {@link TypeVariable} tries to find best raw type - where class
     * is better than interface, and highest position in class hierarchy.
     *
     * @param type generic type.
     *
     * @return raw class type.
     */
    public static Class<?> getRawType(Type type) {
        return TypeUtils.getRawType(type);
    }

    /**
     * Checks if given objectType can be assigned to variable of variableType type.
     *
     * @param objectType type of object that you want to assign.
     * @param variableType type of variable where object will be assigned.
     * @param autoboxing whether to use implicit autoboxing/unboxing between primitives and wrappers
     *
     * @return {@code true} if assignment possible
     */
    public static boolean isAssignable(@Nullable Class<?> objectType, final Class<?> variableType, final boolean autoboxing) {
        return TypeUtils.isAssignable(objectType, variableType, autoboxing);
    }

    /**
     * Checks if given objectType can be assigned to variable of variableType type.
     *
     * @param objectType type of object that you want to assign.
     * @param variableType type of variable where object will be assigned.
     *
     * @return true if types are compatible.
     */
    public static boolean isAssignable(@Nullable Type objectType, Type variableType) {
        return TypeUtils.isAssignable(objectType, variableType);
    }

    /**
     * Checks if given object type is assignable to given type variable.
     *
     * @param typeVariable generic type variable.
     * @param objectType type of object to assign.
     *
     * @return true if types are compatible.
     */
    public static boolean isAssignable(TypeVariable<?> typeVariable, Type objectType) {
        return TypeUtils.isAssignable(typeVariable, objectType);
    }

    /**
     * Returns {@link TypeVariable} from given {@link GenericDeclaration} with given name.
     *
     * @param of generic declaration, like a class, method or constructor.
     * @param name name of type variable to get. (Like K and V in {@literal Map<K, V>})
     * @param <X> type of generic declaration.
     *
     * @return {@link TypeVariable} from given {@link GenericDeclaration} with given name or null.
     *
     * @throws IllegalStateException if declaration contains more than one type variable of that name.
     */
    @Nullable
    public static <X extends GenericDeclaration> TypeVariable<X> typeVariableOf(X of, String name) throws IllegalStateException {
        return TypeUtils.typeVariableOf(of, name);
    }

    /**
     * Returns {@link TypeVariable} from given {@link GenericDeclaration} on given position, might throw {@link IndexOutOfBoundsException}
     *
     * @param of generic declaration, like a class, method or constructor.
     * @param index index of type variable to get.
     * @param <X> type of generic declaration.
     *
     * @return {@link TypeVariable} from given {@link GenericDeclaration}  on given position.
     */
    public static <X extends GenericDeclaration> TypeVariable<X> typeVariableOf(X of, int index) {
        return TypeUtils.typeVariableOf(of, index);
    }

    /**
     * If given class is non-primitive type {@link Class#isPrimitive()} then it will return
     * primitive class for it. Like: Boolean.class {@literal ->} boolean.class
     * If given class is primitive, then it will return given class.
     * If given class can't be primitive (like {@link String}) then it will return given class.
     *
     * @param clazz class to get primitive of it.
     *
     * @return primitive class if possible.
     */
    @SuppressWarnings("ObjectEquality")
    public static Class<?> getPrimitive(Class<?> clazz) {
        return ClassUtils.getPrimitive(clazz);
    }

    /**
     * If given class is primitive type {@link Class#isPrimitive()} then it will return
     * wrapper class for it. Like: boolean.class {@literal ->} Boolean.class
     * If given class isn't primitive, then it will return given class.
     *
     * @param clazz class to get wrapper of it.
     *
     * @return non-primitive class.
     */
    @SuppressWarnings("ObjectEquality")
    public static Class<?> getWrapperClass(Class<?> clazz) {
        return ClassUtils.getWrapperClass(clazz);
    }

    /**
     * Get array class for given class.
     *
     * @param clazz class to get array type of it.
     *
     * @return array version of class.
     */
    @SuppressWarnings("ObjectEquality")
    public static Class<?> getArrayClass(Class<?> clazz) {
        return ArrayUtils.getEmptyArray(clazz).getClass();
    }

    /**
     * Return class for given name or throw exception.
     *
     * @param canonicalName name of class to find.
     *
     * @return class for given name.
     *
     * @throws IllegalArgumentException if there is no class with given name.
     */
    public static Class<?> getCanonicalClass(String canonicalName) throws IllegalArgumentException {
        return getCanonicalClass(canonicalName, ArrayUtils.getEmptyObjectArray(ClassLoader.class));
    }

    /**
     * Return class for given name or null.
     *
     * @param canonicalName name of class to find.
     *
     * @return class for given name or null.
     */
    @Nullable
    public static Class<?> tryGetCanonicalClass(String canonicalName) {
        return tryGetCanonicalClass(canonicalName, ArrayUtils.getEmptyObjectArray(ClassLoader.class));
    }

    /**
     * Return class for given name or throw exception.
     *
     * @param canonicalName name of class to find.
     * @param loaders additional class loaders to lookup.
     *
     * @return class for given name.
     *
     * @throws IllegalArgumentException if there is no class with given name.
     */
    public static Class<?> getCanonicalClass(String canonicalName, ClassLoader... loaders) throws IllegalArgumentException {
        return ClassUtils.getCanonicalClass(canonicalName, loaders);
    }

    /**
     * Return class for given name or null.
     *
     * @param canonicalName name of class to find.
     * @param loaders additional class loaders to lookup.
     *
     * @return class for given name or null.
     */
    @Nullable
    public static Class<?> tryGetCanonicalClass(String canonicalName, ClassLoader... loaders) {
        return ClassUtils.tryGetCanonicalClass(canonicalName, loaders);
    }

    /**
     * Set given accessible object as accessible if needed.
     *
     * @param o accessible to get access.
     * @param <T> type of accessible object, used to keep type of returned value.
     *
     * @return this same object as given.
     */
    public static <T extends AccessibleObject> T getAccess(T o) {
        return AccessibilityUtil.getAccess(o);
    }

    /**
     * Set field as accessible.
     *
     * @param field field to get access.
     *
     * @return this same field.
     */
    public static Field getAccess(Field field) {
        return AccessibilityUtil.getAccess(field, false);
    }

    /**
     * Set field as accessible, and remove final flag if set.
     *
     * @param field field to get access.
     *
     * @return this same field.
     */
    public static Field getAccessNonFinal(Field field) {
        return AccessibilityUtil.getAccess(field, true);
    }

    /**
     * Search for nested class with givan name.
     *
     * @param clazz a class to start with.
     * @param name name of class.
     *
     * @return nested class form given class.
     */
    public static Class<?> getNestedClass(Class<?> clazz, @Nullable String name) {
        return ClassUtils.getNestedClass(clazz, name);
    }

    /**
     * Creates method handle lookup object with given class and mode.
     *
     * @param clazz lookup class.
     * @param mode lookup mode.
     *
     * @return instance of MethodHandles.Lookup
     */
    public static Lookup createLookup(Class<?> clazz, int mode) {
        return AccessibilityUtil.createLookup(clazz, mode);
    }

    /**
     * Creates method handle lookup object with given class and private lookup mode.
     *
     * @param clazz lookup class.
     *
     * @return instance of MethodHandles.Lookup
     *
     * @see Lookup#PRIVATE
     */
    public static Lookup createPrivateLookup(Class<?> clazz) {
        return AccessibilityUtil.createPrivateLookup(clazz);
    }

    /**
     * Creates method handle lookup object with given class and trusted lookup mode.
     *
     * @param clazz lookup class.
     *
     * @return instance of MethodHandles.Lookup
     */
    public static Lookup createTrustedLookup(Class<?> clazz) {
        return AccessibilityUtil.createTrustedLookup(clazz);
    }

}
