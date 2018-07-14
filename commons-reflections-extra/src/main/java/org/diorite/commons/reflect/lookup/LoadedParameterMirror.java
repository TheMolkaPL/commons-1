package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Parameter;

class LoadedParameterMirror extends LoadedAnnotatedElementMirror<Parameter> implements ParameterMirror {
    LoadedParameterMirror(Parameter element) {
        super(element);
    }

    @Override
    public boolean isNamePresent() {
        return this.element.isNamePresent();
    }

    @Override
    public String getType() {
        return MirrorUtils.getName(this.element.getType());
    }

    @Override
    public TypeMirror getGenericType() {
        return TypeMirror.of(this.element.getParameterizedType());
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedType() {
        return AnnotatedTypeMirror.of(this.element.getAnnotatedType());
    }

    @Override
    public boolean isVarargs() {
        return this.element.isVarArgs();
    }

    @Override
    public String getName() {
        return this.element.getName();
    }

    @Override
    public int getModifiers() {
        return this.element.getModifiers();
    }
}
