package org.diorite.commons.reflect.lookup;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.List;

class LoadedAnnotatedParameterizedTypeMirror extends LoadedAnnotatedElementMirror<AnnotatedParameterizedType> implements AnnotatedParameterizedTypeMirror {
    LoadedAnnotatedParameterizedTypeMirror(AnnotatedParameterizedType element) {
        super(element);
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedActualTypeArguments() {
        return MirrorUtils.mirrorTypes(this.element.getAnnotatedActualTypeArguments());
    }

    @Override
    public AnnotatedTypeMirror getAnnotatedOwnerType() {
        AnnotatedType type = this.element.getAnnotatedOwnerType();
        return (type == null) ? null : AnnotatedTypeMirror.of(type);
    }

    @Override
    public TypeMirror getType() {
        return TypeMirror.of(this.element.getType());
    }
}
