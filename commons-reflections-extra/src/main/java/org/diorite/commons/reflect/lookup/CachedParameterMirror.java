package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.Parameter;

class CachedParameterMirror extends CachedAnnotatedElementMirror<ParameterMirror> implements ParameterMirror {
    CachedParameterMirror(ParameterMirror element) {
        super(element);
    }

    @Override
    public boolean isNamePresent() {
        return this.element.isNamePresent();
    }

    @Override
    public String getName() {
        return this.element.getName();
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
    public AnnotatedTypeMirror getAnnotatedType() {
        return this.cached("getAnnotatedType", this.element::getAnnotatedType);
    }

    @Override
    public boolean isVarargs() {
        return this.element.isVarargs();
    }

    @Override
    public boolean isImplicit() {
        return this.element.isImplicit();
    }

    @Override
    public boolean isSynthetic() {
        return this.element.isSynthetic();
    }

    @Override
    public Parameter resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public int getModifiers() {
        return this.element.getModifiers();
    }

    @Override
    public boolean checkModifier(int modifier) {
        return this.element.checkModifier(modifier);
    }

    @Override
    public boolean isFinal() {
        return this.element.isFinal();
    }

    @Nullable
    @Override
    public Parameter getIfResolved() {
        return this.element.getIfResolved();
    }
}
