package org.diorite.commons.reflect.enums;

import javax.annotation.Nullable;

/**
 * Enum utilities
 */
public final class EnumUtils {
    private EnumUtils() {}

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name name of enum field (ignore-case)
     * @param id ordinal id.
     * @param enumClass class of enum.
     * @param <T> type of enum.
     *
     * @return enum element or null.
     */
    @Nullable
    public static <T extends Enum<T>> T getEnumValueSafe(@Nullable String name, int id, Class<T> enumClass) {
        return getEnumValueSafe(name, id, enumClass, null);
    }

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name name of enum field (ignore-case)
     * @param id ordinal id.
     * @param def default value, can't be null.
     * @param <T> type of enum.
     *
     * @return enum element or def.
     */
    @Nullable
    public static <T extends Enum<T>> T getEnumValueSafe(@Nullable String name, int id, T def) {
        return getEnumValueSafe(name, id, def.getDeclaringClass(), def);
    }

    @Nullable
    private static <T extends Enum<T>> T getEnumValueSafe(@Nullable String name, int id, Class<T> enumClass, @Nullable T def) {
        T[] elements = enumClass.getEnumConstants();
        for (T element: elements) {
            if (element.name().equalsIgnoreCase(name) || (element.ordinal() == id)) {
                return element;
            }
        }
        return def;
    }
}
