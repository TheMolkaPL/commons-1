package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

class CachedParameterizedTypeMirror extends CachedMirror<ParameterizedTypeMirror> implements ParameterizedTypeMirror {
    CachedParameterizedTypeMirror(ParameterizedTypeMirror element) {
        super(element);
    }

    @Override
    public List<? extends TypeMirror> getActualTypeArguments() {
        return this.cached("getActualTypeArguments", this.element::getActualTypeArguments);
    }

    @Override
    public TypeMirror getRawType() {
        return this.cached("getRawType", this.element::getRawType);
    }

    @Override
    @Nullable
    public TypeMirror getOwnerType() {
        return this.cached("getOwnerType", this.element::getOwnerType);
    }

    @Override
    public ParameterizedType resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Nullable
    @Override
    public ParameterizedType getIfResolved() {
        return this.element.getIfResolved();
    }
}
