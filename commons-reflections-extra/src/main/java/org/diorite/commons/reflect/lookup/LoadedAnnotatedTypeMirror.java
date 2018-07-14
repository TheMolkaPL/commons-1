package org.diorite.commons.reflect.lookup;

import java.lang.reflect.AnnotatedType;

abstract class LoadedAnnotatedTypeMirror extends LoadedAnnotatedElementMirror<AnnotatedType> implements AnnotatedTypeMirror {
    LoadedAnnotatedTypeMirror(AnnotatedType element) {
        super(element);
    }

    @Override
    public TypeMirror getType() {
        return TypeMirror.of(this.element.getType());
    }
}
