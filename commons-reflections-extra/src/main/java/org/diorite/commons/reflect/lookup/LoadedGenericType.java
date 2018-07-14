package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Type;

abstract class LoadedGenericType<T extends Type> implements TypeMirror {
    protected final T element;

    LoadedGenericType(T element) {
        this.element = element;
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public T resolve() {
        return this.element;
    }

    @Override
    public T getIfResolved() {
        return this.element;
    }
}
