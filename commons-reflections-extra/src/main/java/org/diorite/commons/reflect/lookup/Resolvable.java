package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;

interface Resolvable {
    /**
     * @return true if this type is mirror of loaded type or it is resolved to some java loaded type.
     */
    boolean isResolved();

    /**
     * Tries to resolve this type to java type, CAUTION: this might cause loading of some classes - including classes that are not
     * related to mirrored type too.
     *
     * @return if resolve was successful
     */
    default boolean tryToResolve() {
        try {
            this.resolve();
            return true;
        }
        catch (ResolveException e) {
            return false;
        }
    }

    /**
     * Tries to resolve instance, throw ResolveException on failure, that exception will most likely contain another exception as cause.
     *
     * @return resolved instance.
     */
    Object resolve() throws ResolveException;

    /**
     * @return resolved instance - without trying to resolve.
     */
    @Nullable
    Object getIfResolved();
}
