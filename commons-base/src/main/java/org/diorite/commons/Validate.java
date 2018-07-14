package org.diorite.commons;

import java.util.function.Supplier;

/**
 * Few simple validation methods with support for lazy evaluated message supplier
 */
public final class Validate {
    private Validate() {}

    public static void isTrue(boolean condition, String errorMessage) {
        if (condition) {
            throw new IllegalStateException(errorMessage);
        }
    }

    public static void isTrue(boolean condition, Supplier<String> errorMessage) {
        if (condition) {
            throw new IllegalStateException(errorMessage.get());
        }
    }
}
