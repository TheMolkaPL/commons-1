package org.diorite.commons.reflect.lookup;

import java.lang.reflect.Modifier;

interface CanBeFinal extends HasModifiers {
    default boolean isFinal() {
        return this.checkModifier(Modifier.FINAL);
    }
}
