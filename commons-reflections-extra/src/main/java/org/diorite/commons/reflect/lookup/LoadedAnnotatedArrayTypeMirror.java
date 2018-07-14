package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedType;

class LoadedAnnotatedArrayTypeMirror extends LoadedAnnotatedElementMirror<AnnotatedArrayType> implements AnnotatedArrayTypeMirror {
    LoadedAnnotatedArrayTypeMirror(AnnotatedArrayType element) {
        super(element);
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedGenericComponentType() {
        return AnnotatedTypeMirror.of(this.element.getAnnotatedGenericComponentType());
    }

    @Override
    @Nullable
    public AnnotatedTypeMirror getAnnotatedOwnerType() {
        AnnotatedType type = this.element.getAnnotatedOwnerType();
        return (type == null) ? null : AnnotatedTypeMirror.of(type);
    }

    @Override
    public TypeMirror getType() {
        return TypeMirror.of(this.element.getType());
    }
}
