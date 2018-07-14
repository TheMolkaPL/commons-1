package org.diorite.commons.reflect;

import org.diorite.commons.ExceptionUtils;
import sun.misc.Unsafe;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

final class AccessibilityUtil {
    private AccessibilityUtil() {}

    // not needed in java 8
    private static final Unsafe unsafe;
    private static final Field  constructorModifiers;
    private static final Field  methodModifiers;
    private static final Field  fieldModifiers;
    private static final long   constructorModifiersOffset;
    private static final long   methodModifiersOffset;
    private static final long   fieldModifiersOffset;
    @Nullable
    private static final Method setAccessible;

    private static final MethodHandle lookupCreator = ReflectionUtils.constructorLookupIn(Lookup.class)
                                                                     .withParameters(Class.class, int.class)
                                                                     .findExact().getHandle();

    static {
        try {
            Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
            unsafeConstructor.setAccessible(true);
            unsafe = unsafeConstructor.newInstance();
            constructorModifiers = Constructor.class.getDeclaredField("modifiers");
            constructorModifiersOffset = unsafe.objectFieldOffset(constructorModifiers);
            methodModifiers = Method.class.getDeclaredField("modifiers");
            methodModifiersOffset = unsafe.objectFieldOffset(methodModifiers);
            fieldModifiers = Field.class.getDeclaredField("modifiers");
            fieldModifiersOffset = unsafe.objectFieldOffset(fieldModifiers);
            if (System.getProperty("java.specification.version").startsWith("1.")) {
                setAccessible = null;
            }
            else {
                setAccessible = AccessibleObject.class.getDeclaredMethod("setAccessible0", boolean.class);
                setForceAccessible(setAccessible);
            }
        }
        catch (Exception e) {
            throw new InternalError(e);
        }
    }

    private static boolean setForceAccessible(AccessibleObject accessibleObject) {
        try {
            if (accessibleObject instanceof Constructor) {
                Constructor<?> object = (Constructor<?>) accessibleObject;
                unsafe.getAndSetInt(object, constructorModifiersOffset, addPublicModifier(object.getModifiers()));
                return true;
            }
            if (accessibleObject instanceof Method) {
                Method object = (Method) accessibleObject;
                unsafe.getAndSetInt(object, methodModifiersOffset, addPublicModifier(object.getModifiers()));
                return true;
            }
            if (accessibleObject instanceof Field) {
                Field object = (Field) accessibleObject;
                unsafe.getAndSetInt(object, fieldModifiersOffset, addPublicModifier(object.getModifiers()));
                return true;
            }
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int addPublicModifier(int mod) {
        mod &= ~ (Modifier.PRIVATE);
        mod &= ~ (Modifier.PROTECTED);
        mod |= (Modifier.PUBLIC);
        return mod;
    }

    static <T extends AccessibleObject> T getAccess(T o) {
        if (! o.isAccessible()) {
            try {
                if (setAccessible != null) {
                    setAccessible.invoke(o, true);
                }
                else {
                    o.setAccessible(true);
                }
            }
            catch (Exception e) {
                throw new InternalError("Can't get access to: " + o, e);
            }
        }
        return o;
    }

    static Field getAccess(Field field, boolean makeNonFinal) {
        getAccess((AccessibleObject) field);
        if (makeNonFinal && Modifier.isFinal(field.getModifiers())) {
            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                getAccess(modifiersField);
                modifiersField.setInt(field, field.getModifiers() & ~ Modifier.FINAL);
            }
            catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                throw new IllegalArgumentException("Can't make field non-final");
            }
        }
        return field;
    }

    static Lookup createLookup(Class<?> clazz, int mode) {
        try {
            return (Lookup) lookupCreator.invokeExact(clazz, mode);
        }
        catch (Throwable throwable) {
            throw ExceptionUtils.sneakyThrow(throwable);
        }
    }

    static Lookup createPrivateLookup(Class<?> clazz) {
        try {
            return (Lookup) lookupCreator.invokeExact(clazz, Lookup.PRIVATE);
        }
        catch (Throwable throwable) {
            throw ExceptionUtils.sneakyThrow(throwable);
        }
    }

    static Lookup createTrustedLookup(Class<?> clazz) {
        try {
            return (Lookup) lookupCreator.invokeExact(clazz, - 1);
        }
        catch (Throwable throwable) {
            throw ExceptionUtils.sneakyThrow(throwable);
        }
    }
}
