/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.commons.reflect.type;

import org.diorite.commons.array.ArrayUtils;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

class WildcardTypeImpl implements WildcardType {
    private static final Type[]           OBJECT   = {Object.class};
    private static final WildcardTypeImpl WILDCARD = new WildcardTypeImpl(OBJECT, ArrayUtils
                                                                                          .getEmptyObjectArray(Type.class));

    private final Type[] upperBounds;
    private final Type[] lowerBounds;

    WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
        this.upperBounds = upperBounds;
        this.lowerBounds = lowerBounds;
    }

    @Override
    public Type[] getUpperBounds() {
        if (this.upperBounds.length == 0) {
            return this.upperBounds;
        }
        return this.upperBounds.clone();
    }

    @Override
    public Type[] getLowerBounds() {
        if (this.lowerBounds.length == 0) {
            return this.lowerBounds;
        }
        return this.lowerBounds.clone();
    }

    static WildcardTypeImpl wildcard() {
        return WILDCARD;
    }

    static WildcardTypeImpl withExtends(Type type) {
        if (type == Object.class) {
            return wildcard();
        }
        return new WildcardTypeImpl(new Type[]{type}, ArrayUtils.getEmptyObjectArray(Type.class));
    }

    static WildcardTypeImpl withSuper(Type type) {
        return new WildcardTypeImpl(OBJECT, new Type[]{type});
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof WildcardType) {
            WildcardType that = (WildcardType) o;
            return Arrays.equals(this.getLowerBounds(), that.getLowerBounds()) && Arrays
                                                                                          .equals(this.getUpperBounds(),
                                                                                                  that.getUpperBounds());
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        Type[] lowerBounds = this.getLowerBounds();
        Type[] upperBounds = this.getUpperBounds();

        return Arrays.hashCode(lowerBounds) ^ Arrays.hashCode(upperBounds);
    }

    @Override
    public String toString() {
        Type[] bounds = this.getLowerBounds();
        StringBuilder builder = new StringBuilder(100);
        if (bounds.length > 0) {
            builder.append("? super ");
        }
        else {
            Type[] upperBounds = this.getUpperBounds();
            if ((upperBounds.length <= 0) || upperBounds[0].equals(Object.class)) {
                return "?";
            }

            bounds = upperBounds;
            builder.append("? extends ");
        }

        assert bounds.length > 0;

        boolean notLast = true;

        for (Type type: bounds) {
            if (! notLast) {
                builder.append(" & ");
            }

            notLast = false;
            builder.append(type.getTypeName());
        }

        return builder.toString();
    }
}
