package org.diorite.commons.reflect.lookup;

import java.lang.reflect.AnnotatedParameterizedType;
import java.util.List;

class CachedAnnotatedParameterizedTypeMirror extends CachedAnnotatedTypeMirror<AnnotatedParameterizedTypeMirror> implements AnnotatedParameterizedTypeMirror {
    CachedAnnotatedParameterizedTypeMirror(AnnotatedParameterizedTypeMirror element) {
        super(element);
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedActualTypeArguments() {
        return this.cached("getAnnotatedActualTypeArguments", this.element::getAnnotatedActualTypeArguments);
    }

    @Override
    public AnnotatedParameterizedType resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public AnnotatedParameterizedType getIfResolved() {
        return this.element.getIfResolved();
    }
}
