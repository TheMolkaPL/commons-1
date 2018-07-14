package org.diorite.commons.reflect.lookup;

import org.diorite.commons.object.Named;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

public interface MemberMirror extends HasModifiers, HasAccessFlags, AnnotatedElementMirror, Named {
    /**
     * @return name of this member.
     */
    @Override
    @Nonnull
    String getName();

    /**
     * @return signature of member
     */
    String getDescriptor();

    /**
     * @return class declaring that member.
     */
    ClassMirror getDeclaringClass();

    @Override
    AnnotatedElement resolve() throws ResolveException;

    @Override
    AnnotatedElement getIfResolved();

    static MemberMirror of(Member member) {
        return MirrorUtils.cached(member);
    }
}
