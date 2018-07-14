package org.diorite.commons.reflect.lookup;

import java.lang.reflect.WildcardType;
import java.util.List;

class LoadedWildcardTypeMirror extends LoadedGenericType<WildcardType> implements WildcardTypeMirror {
    LoadedWildcardTypeMirror(WildcardType element) {
        super(element);
    }

    @Override
    public List<? extends TypeMirror> getUpperBounds() {
        return MirrorUtils.mirrorTypes(this.element.getUpperBounds());
    }

    @Override
    public List<? extends TypeMirror> getLowerBounds() {
        return MirrorUtils.mirrorTypes(this.element.getLowerBounds());
    }
}
