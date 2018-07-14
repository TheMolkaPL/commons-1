package org.diorite.commons.reflect.lookup;

import org.objectweb.asm.Type;

import java.lang.reflect.Field;

@SuppressWarnings({"unchecked", "RawTypeCanBeGeneric"})
class LoadedFieldMirror extends LoadedMemberMirror<Field> implements FieldMirror {

    LoadedFieldMirror(Field field) {
        super(field);
    }

    @Override
    public String getType() {
        return MirrorUtils.getName(this.element.getType());
    }

    @Override
    public TypeMirror getGenericType() {
        return TypeMirror.of(this.element.getGenericType());
    }

    @Override
    public String getDescriptor() {
        return Type.getDescriptor(this.element.getType());
    }
}
