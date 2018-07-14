package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedWildcardType;
import java.util.List;

class LoadedAnnotatedWildcardTypeMirror extends LoadedAnnotatedElementMirror<AnnotatedWildcardType> implements AnnotatedWildcardTypeMirror {
    LoadedAnnotatedWildcardTypeMirror(AnnotatedWildcardType element) {
        super(element);
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedLowerBounds() {
        return MirrorUtils.mirrorTypes(this.element.getAnnotatedLowerBounds());
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedUpperBounds() {
        return MirrorUtils.mirrorTypes(this.element.getAnnotatedUpperBounds());
    }

    @Nullable
    @Override
    public AnnotatedTypeMirror getAnnotatedOwnerType() {
        AnnotatedType type = this.element.getAnnotatedOwnerType();
        return (type == null) ? null : AnnotatedTypeMirror.of(type);
    }

    @Override
    public TypeMirror getType() {
        return TypeMirror.of(this.element.getType());
    }

    static AnnotatedWildcardTypeMirror of(AnnotatedWildcardType type) {
        return MirrorUtils.cached(new LoadedAnnotatedWildcardTypeMirror(type));
    }
}
