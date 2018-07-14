package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Modifier;

interface HasAccessFlags extends HasModifiers {
    default boolean isPublic() {
        return this.checkModifier(Modifier.PUBLIC);
    }

    default boolean isPrivate() {
        return this.checkModifier(Modifier.PRIVATE);
    }

    default boolean isProtected() {
        return this.checkModifier(Modifier.PROTECTED);
    }

    default boolean isDefaultAccess() {
        return ! (this.isPublic() || this.isPrivate() || this.isProtected());
    }
}
