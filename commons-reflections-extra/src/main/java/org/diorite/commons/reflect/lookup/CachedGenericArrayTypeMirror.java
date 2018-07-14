package org.diorite.commons.reflect.lookup;

import java.lang.reflect.GenericArrayType;

class CachedGenericArrayTypeMirror extends CachedMirror<GenericArrayTypeMirror> implements GenericArrayTypeMirror {
    CachedGenericArrayTypeMirror(GenericArrayTypeMirror element) {
        super(element);
    }

    @Override
    public TypeMirror getGenericComponentType() {
        return this.cached("getGenericComponentType", this.element::getGenericComponentType);
    }

    @Override
    public GenericArrayType resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public GenericArrayType getIfResolved() {
        return this.element.getIfResolved();
    }
}
