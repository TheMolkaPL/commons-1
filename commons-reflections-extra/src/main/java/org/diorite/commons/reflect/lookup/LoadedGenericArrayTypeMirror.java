package org.diorite.commons.reflect.lookup;

import java.lang.reflect.GenericArrayType;

class LoadedGenericArrayTypeMirror extends LoadedGenericType<GenericArrayType> implements GenericArrayTypeMirror {
    LoadedGenericArrayTypeMirror(GenericArrayType element) {
        super(element);
    }

    @Override
    public TypeMirror getGenericComponentType() {
        return TypeMirror.of(this.element.getGenericComponentType());
    }
}
