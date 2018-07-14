package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

class CachedFieldMirror extends CachedMemberMirror<FieldMirror> implements FieldMirror {
    CachedFieldMirror(FieldMirror element) {
        super(element);
    }

    @Override
    public String getType() {
        return this.cached("getType", this.element::getType);
    }

    @Override
    public TypeMirror getGenericType() {
        return this.cached("getGenericType", this.element::getGenericType);
    }

    @Override
    public boolean isStatic() {
        return this.element.isStatic();
    }

    @Override
    public boolean isVolatile() {
        return this.element.isVolatile();
    }

    @Override
    public boolean isTransient() {
        return this.element.isTransient();
    }

    @Override
    public boolean isSynthetic() {
        return this.element.isSynthetic();
    }

    @Override
    public boolean isEnumConstant() {
        return this.element.isEnumConstant();
    }

    @Override
    public Field resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Nullable
    @Override
    public Field getIfResolved() {
        return this.element.getIfResolved();
    }
}
