package org.diorite.commons.reflect.lookup;

import java.lang.reflect.WildcardType;
import java.util.List;

class CachedWildcardTypeMirror extends CachedMirror<WildcardTypeMirror> implements WildcardTypeMirror {
    CachedWildcardTypeMirror(WildcardTypeMirror element) {
        super(element);
    }

    @Override
    public List<? extends TypeMirror> getUpperBounds() {
        return this.cached("getUpperBounds", this.element::getUpperBounds);
    }

    @Override
    public List<? extends TypeMirror> getLowerBounds() {
        return this.cached("getLowerBounds", this.element::getLowerBounds);
    }

    @Override
    public WildcardType resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public WildcardType getIfResolved() {
        return this.element.getIfResolved();
    }
}
