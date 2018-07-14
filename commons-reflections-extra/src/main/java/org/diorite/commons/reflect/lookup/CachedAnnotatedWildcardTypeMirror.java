package org.diorite.commons.reflect.lookup;

import java.lang.reflect.AnnotatedWildcardType;
import java.util.List;

class CachedAnnotatedWildcardTypeMirror extends CachedAnnotatedTypeMirror<AnnotatedWildcardTypeMirror> implements AnnotatedWildcardTypeMirror {
    CachedAnnotatedWildcardTypeMirror(AnnotatedWildcardTypeMirror element) {
        super(element);
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedLowerBounds() {
        return this.cached("getAnnotatedLowerBounds", this.element::getAnnotatedLowerBounds);
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedUpperBounds() {
        return this.cached("getAnnotatedUpperBounds", this.element::getAnnotatedUpperBounds);
    }

    @Override
    public AnnotatedWildcardType resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public AnnotatedWildcardType getIfResolved() {
        return this.element.getIfResolved();
    }
}
