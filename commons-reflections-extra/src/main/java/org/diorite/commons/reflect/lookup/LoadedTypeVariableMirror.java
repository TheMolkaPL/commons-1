package org.diorite.commons.reflect.lookup;

import java.lang.reflect.TypeVariable;
import java.util.List;

class LoadedTypeVariableMirror extends LoadedAnnotatedElementMirror<TypeVariable<?>> implements TypeVariableMirror {
    LoadedTypeVariableMirror(TypeVariable<?> element) {
        super(element);
    }

    @Override
    public List<? extends TypeMirror> getBounds() {
        return MirrorUtils.mirrorTypes(this.element.getBounds());
    }

    @Override
    public String getName() {
        return this.element.getName();
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedBounds() {
        return MirrorUtils.mirrorTypes(this.element.getAnnotatedBounds());
    }
}
