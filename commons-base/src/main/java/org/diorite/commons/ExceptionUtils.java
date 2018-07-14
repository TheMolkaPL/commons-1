package org.diorite.commons;

public final class ExceptionUtils {
    private ExceptionUtils() {}

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> RuntimeException sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }
}
