package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.TypeVariable;
import java.util.List;

class CachedTypeVariableMirror extends CachedAnnotatedElementMirror<TypeVariableMirror> implements TypeVariableMirror {
    CachedTypeVariableMirror(TypeVariableMirror element) {
        super(element);
    }

    @Override
    public List<? extends TypeMirror> getBounds() {
        return this.cached("getBounds", this.element::getBounds);
    }

    @Override
    public String getName() {
        return this.cached("getName", this.element::getName);
    }

    @Override
    public List<? extends AnnotatedTypeMirror> getAnnotatedBounds() {
        return this.cached("getAnnotatedBounds", this.element::getAnnotatedBounds);
    }

    @Override
    public TypeVariable<?> resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Nullable
    @Override
    public TypeVariable<?> getIfResolved() {
        return this.element.getIfResolved();
    }
}
