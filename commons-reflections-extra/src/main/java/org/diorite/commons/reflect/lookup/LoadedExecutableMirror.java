package org.diorite.commons.reflect.lookup;

import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;

class LoadedExecutableMirror extends LoadedMemberMirror<Executable> implements MethodMirror {
    LoadedExecutableMirror(Executable element) {
        super(element);
    }

    @Override
    public boolean isConstructor() {
        return this.element instanceof Constructor;
    }

    @Override
    public String getReturnType() {
        if (this.isConstructor()) {
            return MirrorUtils.getName(this.element.getDeclaringClass());
        }
        return MirrorUtils.getName(((Method) this.element).getReturnType());
    }

    @Override
    public TypeMirror getGenericReturnType() {
        if (this.isConstructor()) {
            return TypeMirror.of(this.element.getAnnotatedReturnType().getType());
        }
        return TypeMirror.of((((Method) this.element).getGenericReturnType()));
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedReturnType() {
        return AnnotatedTypeMirror.of(this.element.getAnnotatedReturnType());
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedReceiverType() {
        return AnnotatedTypeMirror.of(this.element.getAnnotatedReceiverType());
    }

    @Override
    public List<? extends String> getExceptionTypes() {
        return MirrorUtils.typeNames(this.element.getExceptionTypes());
    }

    @Override
    public List<? extends TypeMirror> getGenericExceptionTypes() {
        return MirrorUtils.mirrorTypes(this.element.getGenericExceptionTypes());
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedExceptionTypes() {
        return MirrorUtils.mirrorTypes(this.element.getAnnotatedExceptionTypes());
    }

    @Override
    public List<? extends String> getParameterTypes() {
        return MirrorUtils.typeNames(this.element.getParameterTypes());
    }

    @Override
    public List<? extends ParameterMirror> getParameters() {
        return MirrorUtils.mirror(this.element.getParameters());
    }

    @Override
    public List<? extends TypeVariableMirror> getTypeParameters() {
        return MirrorUtils.mirror(this.element.getTypeParameters());
    }

    @Override
    public String getDescriptor() {
        if (this.isConstructor()) {
            return Type.getConstructorDescriptor(((Constructor) this.element));
        }
        return Type.getMethodDescriptor(((Method) this.element));
    }
}
