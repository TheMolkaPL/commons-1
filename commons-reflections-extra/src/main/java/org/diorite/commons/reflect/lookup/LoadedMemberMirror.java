package org.diorite.commons.reflect.lookup;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

abstract class LoadedMemberMirror<E extends AnnotatedElement & Member> extends LoadedAnnotatedElementMirror<E> implements MemberMirror {
    LoadedMemberMirror(E element) {
        super(element);
    }

    @Nonnull
    @Override
    public String getName() {
        return this.element.getName();
    }

    @Override
    public ClassMirror getDeclaringClass() {
        return ClassMirror.of(this.element.getDeclaringClass());
    }

    @Override
    public int getModifiers() {
        return this.element.getModifiers();
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public E resolve() {
        return this.element;
    }

    @Override
    public E getIfResolved() {
        return this.element;
    }
}
