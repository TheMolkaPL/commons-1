package org.diorite.commons.reflect.lookup;

interface HasModifiers {
    /**
     * @return modifiers of this member
     */
    int getModifiers();

    /**
     * Check if this member have given modifier
     *
     * @param modifier modifier to check.
     *
     * @return if this member have given modifier
     */
    default boolean checkModifier(int modifier) {
        return (this.getModifiers() & modifier) != 0;
    }
}
