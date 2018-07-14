package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

class LoadedParameterizedTypeMirror extends LoadedGenericType<ParameterizedType> implements ParameterizedTypeMirror {
    LoadedParameterizedTypeMirror(ParameterizedType element) {
        super(element);
    }

    @Override
    public List<? extends TypeMirror> getActualTypeArguments() {
        return MirrorUtils.mirrorTypes(this.element.getActualTypeArguments());
    }

    @Override
    public TypeMirror getRawType() {
        return TypeMirror.of(this.element.getRawType());
    }

    @Nullable
    @Override
    public TypeMirror getOwnerType() {
        Type type = this.element.getOwnerType();
        return (type == null) ? null : TypeMirror.of(type);
    }
}
