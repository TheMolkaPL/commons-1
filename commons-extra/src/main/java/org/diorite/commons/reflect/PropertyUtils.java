package org.diorite.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

final class PropertyUtils {

    private PropertyUtils() {}

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
        return getReflectedProperty(field.getName(), field.getDeclaringClass());
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
        ReflectedGetter<T> getter = getReflectGetter(fieldName, clazz);
        if (getter instanceof ReflectedProperty) {
            ReflectedProperty<T> property = (ReflectedProperty<T>) getter;
            return new ReflectedPropertyImpl<>(property, property);
        }
        else {
            return new ReflectedPropertyImpl<>(getter, getReflectSetter(fieldName, clazz));
        }
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
        String fieldNameCpy = fieldName;
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method m = null;

        try {
            m = clazz.getMethod("set" + fieldName);
        }
        catch (NoSuchMethodException ignored2) {
            for (Method cm: clazz.getMethods()) {
                if ((cm.getName().equalsIgnoreCase("set" + fieldName)) && (cm.getReturnType() == void.class) && (cm
                                                                                                                         .getParameterCount() == 1)) {
                    m = cm;
                    break;
                }
            }
        }

        if (m != null) {
            return ReflectedMethod.<T>fromMethod(m).asSetter();
        }
        else {
            return ReflectionUtils.<Object, T>uncheckedFieldLookupIn(clazz).includeSupertypes().name(fieldNameCpy).findExact();
        }
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
        return getReflectSetter(field.getName(), field.getDeclaringClass());
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
        String fieldNameCpy = fieldName;
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method m = null;
        try {
            m = clazz.getMethod("get" + fieldName);
        }
        catch (NoSuchMethodException ignored1) {
            try {
                m = clazz.getMethod("is" + fieldName);
            }
            catch (NoSuchMethodException ignored2) {
                for (Method cm: clazz.getMethods()) {
                    if ((cm.getName().equalsIgnoreCase("get" + fieldName) || cm.getName().equalsIgnoreCase("is" + fieldName)) &&
                                (cm.getReturnType() != Void.class) && (cm.getReturnType() != void.class) && (cm.getParameterCount() == 0)) {
                        m = cm;
                        break;
                    }
                }
            }
        }

        if (m != null) {
            return ReflectedMethod.<T>fromMethod(m).asGetter();
        }
        else {
            return ReflectionUtils.<Object, T>uncheckedFieldLookupIn(clazz).includeSupertypes().name(fieldNameCpy).findExact();
        }
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
        return getReflectGetter(field.getName(), field.getDeclaringClass());
    }
}
