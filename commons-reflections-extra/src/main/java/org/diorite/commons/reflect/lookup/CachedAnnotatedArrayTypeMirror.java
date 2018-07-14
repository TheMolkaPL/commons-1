package org.diorite.commons.reflect.lookup;

import java.lang.reflect.AnnotatedArrayType;

class CachedAnnotatedArrayTypeMirror extends CachedAnnotatedTypeMirror<AnnotatedArrayTypeMirror> implements AnnotatedArrayTypeMirror {
    CachedAnnotatedArrayTypeMirror(AnnotatedArrayTypeMirror element) {
        super(element);
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedGenericComponentType() {
        return this.cached("getAnnotatedGenericComponentType", this.element::getAnnotatedGenericComponentType);
    }

    @Override
    public AnnotatedArrayType resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public AnnotatedArrayType getIfResolved() {
        return this.element.getIfResolved();
    }
}
