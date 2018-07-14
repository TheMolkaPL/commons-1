package org.diorite.commons.reflect.lookup;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedElement;

abstract class CachedMemberMirror<T extends MemberMirror> extends CachedAnnotatedElementMirror<T> implements MemberMirror {
    CachedMemberMirror(T element) {
        super(element);
    }

    @Override
    @Nonnull
    public String getName() {
        return this.element.getName();
    }

    @Override
    public String getDescriptor() {
        return this.cached("getDescriptor", this.element::getDescriptor);
    }

    @Override
    public ClassMirror getDeclaringClass() {
        return this.cached("getDeclaringClass", this.element::getDeclaringClass);
    }

    @Override
    public int getModifiers() {
        return this.element.getModifiers();
    }

    @Override
    public boolean checkModifier(int modifier) {
        return this.element.checkModifier(modifier);
    }

    @Override
    public boolean isPublic() {
        return this.element.isPublic();
    }

    @Override
    public boolean isPrivate() {
        return this.element.isPrivate();
    }

    @Override
    public boolean isProtected() {
        return this.element.isProtected();
    }

    @Override
    public boolean isDefaultAccess() {
        return this.element.isDefaultAccess();
    }

    @Override
    public AnnotatedElement resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Nullable
    @Override
    public AnnotatedElement getIfResolved() {
        return this.element.getIfResolved();
    }
}
