package org.diorite.commons.object;

import org.diorite.commons.array.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Simple utility class to make creation of nice toString simpler without creating any objects.
 * <pre>{@code
 * }
 * </pre>
 */
public class ToStringHelper {
    /**
     * For objects it calls toString on it, and for arrays it calls {@link Arrays#deepToString(Object[])}
     *
     * @param object object to change to string.
     *
     * @return string representation of given object.
     */
    public static String deepToString(@Nullable Object object) {
        if (object == null) {
            return "null";
        }
        if (object.getClass().isArray()) {
            return ArrayUtils.deepToString(object);
        }
        return object.toString();
    }

    public static StringBuilder start(Object object) {
        StringBuilder stringBuilder = new StringBuilder(100);
        Class<?> clazz = object.getClass();
        stringBuilder.append(clazz.getName().substring(clazz.getPackageName().length())).append('{');
        return stringBuilder;
    }

    public static StringBuilder addMember(StringBuilder builder, String name, boolean value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, byte value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, char value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, short value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, int value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, long value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, float value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, double value) {
        return addMember0(builder, name, false, String.valueOf(value));
    }

    public static StringBuilder addMember(StringBuilder builder, String name, @Nullable Object value) {
        return addMember0(builder, name, value instanceof CharSequence, deepToString(value));
    }

    private static StringBuilder addMember0(StringBuilder builder, String name, boolean wasString, String stringValue) {
        int insertPos = builder.length() - 1;
        if (builder.charAt(insertPos) == '}') {
            insertPos -= 1;
        }
        builder.append(name);
        if (wasString) {
            builder.insert(insertPos, "='").append(stringValue).append("'}");
        }
        else {
            builder.insert(insertPos, '=').append(stringValue).append('}');
        }
        return builder;
    }

    public static StringBuilder addSuper(StringBuilder builder, Class<?> type, String superObj) {
        int insertPos = builder.length() - 1;
        if (builder.charAt(insertPos) == '}') {
            insertPos -= 1;
        }
        String typeNameToStr = type.getName().substring(type.getPackageName().length());
        if (superObj.startsWith(typeNameToStr)) {
            superObj = superObj.substring(typeNameToStr.length());
        }
        return builder.insert(insertPos, "super=").append(superObj);
    }
}
