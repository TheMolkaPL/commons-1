package org.diorite.commons.reflect.lookup;

final class ExtraModifiers {
    static final int BRIDGE     = 0x00000040;
    static final int VARARGS    = 0x00000080;
    static final int SYNTHETIC  = 0x00001000;
    static final int ANNOTATION = 0x00002000;
    static final int ENUM       = 0x00004000;
    static final int MANDATED   = 0x00008000;

    private ExtraModifiers() {}
}
