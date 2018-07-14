package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedType;

abstract class CachedAnnotatedTypeMirror<T extends AnnotatedTypeMirror> extends CachedAnnotatedElementMirror<T> implements AnnotatedTypeMirror {
    CachedAnnotatedTypeMirror(T element) {
        super(element);
    }

    @Override
    @Nullable
    public AnnotatedTypeMirror getAnnotatedOwnerType() {
        return this.cached("getAnnotatedOwnerType", this.element::getAnnotatedOwnerType);
    }

    @Override
    public TypeMirror getType() {
        return this.cached("getType", this.element::getType);
    }

    @Override
    public AnnotatedType resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public AnnotatedType getIfResolved() {
        return this.element.getIfResolved();
    }
}
