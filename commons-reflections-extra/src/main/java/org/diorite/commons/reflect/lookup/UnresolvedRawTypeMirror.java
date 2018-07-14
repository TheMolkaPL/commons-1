package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Type;

public interface UnresolvedRawTypeMirror extends TypeMirror {
    default String getRawType() {
        return this.toString();
    }

    @Override
    default Type resolve() throws ResolveException {
        throw new ResolveException("Can't resolve: " + this.getRawType());
    }

    @Override
    default Type getIfResolved() {
        return null;
    }

    @Override
    default boolean isResolved() {
        return false;
    }

    @Override
    default boolean tryToResolve() {
        return false;
    }
}
