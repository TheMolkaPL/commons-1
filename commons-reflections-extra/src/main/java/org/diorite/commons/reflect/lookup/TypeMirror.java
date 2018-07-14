package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Type;

public interface TypeMirror extends Resolvable {
    @Override
    Type resolve() throws ResolveException;

    @Override
    Type getIfResolved();

    static TypeMirror of(Type type) {
        return MirrorUtils.cached(type);
    }
}
