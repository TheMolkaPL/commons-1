package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.Executable;
import java.util.List;

class CachedMethodMirror extends CachedMemberMirror<MethodMirror> implements MethodMirror {
    CachedMethodMirror(MethodMirror element) {
        super(element);
    }

    @Override
    public boolean isConstructor() {
        return this.element.isConstructor();
    }

    @Override
    public String getReturnType() {
        return this.cached("getReturnType", this.element::getReturnType);
    }

    @Override
    public TypeMirror getGenericReturnType() {
        return this.cached("getGenericReturnType", this.element::getGenericReturnType);
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedReturnType() {
        return this.cached("getAnnotatedReturnType", this.element::getAnnotatedReturnType);
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedReceiverType() {
        return this.cached("getAnnotatedReceiverType", this.element::getAnnotatedReceiverType);
    }

    @Override
    public List<? extends String> getExceptionTypes() {
        return this.cached("getExceptionTypes", this.element::getExceptionTypes);
    }

    @Override
    public List<? extends TypeMirror> getGenericExceptionTypes() {
        return this.cached("getGenericExceptionTypes", this.element::getGenericExceptionTypes);
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedExceptionTypes() {
        return this.cached("getAnnotatedExceptionTypes", this.element::getAnnotatedExceptionTypes);
    }

    @Override
    public List<? extends String> getParameterTypes() {
        return this.cached("getParameterTypes", this.element::getParameterTypes);
    }

    @Override
    public List<? extends ParameterMirror> getParameters() {
        return this.cached("getParameters", this.element::getParameters);
    }

    @Override
    public List<? extends TypeVariableMirror> getTypeParameters() {
        return this.cached("getTypeParameters", this.element::getTypeParameters);
    }

    @Override
    public boolean isFinal() {
        return this.element.isFinal();
    }

    @Override
    public boolean isStatic() {
        return this.element.isStatic();
    }

    @Override
    public boolean isAbstract() {
        return this.element.isAbstract();
    }

    @Override
    public boolean isSynchronized() {
        return this.element.isSynchronized();
    }

    @Override
    public boolean isNative() {
        return this.element.isNative();
    }

    @Override
    public boolean isBridge() {
        return this.element.isBridge();
    }

    @Override
    public boolean isVarargs() {
        return this.element.isVarargs();
    }

    @Override
    public Executable resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Nullable
    @Override
    public Executable getIfResolved() {
        return this.element.getIfResolved();
    }
}
