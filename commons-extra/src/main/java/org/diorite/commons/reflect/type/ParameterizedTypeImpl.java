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

import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

class ParameterizedTypeImpl implements ParameterizedType {
    private final Type[] actualTypeArguments;
    private final Class  rawType;
    @Nullable
    private final Type   ownerType;

    ParameterizedTypeImpl(Class rawType, Type[] actualTypeArguments, @Nullable Type ownerType) {
        this.actualTypeArguments = actualTypeArguments;
        this.rawType = rawType;
        if ((ownerType != null) || (rawType.getDeclaringClass() == null)) {
            this.ownerType = ownerType;
        }
        else {
            Class declaringClass = rawType.getDeclaringClass();
            if (Modifier.isStatic(rawType.getModifiers())) {
                this.ownerType = declaringClass;
            }
            else {
                TypeVariable[] typeParameters = declaringClass.getTypeParameters();
                if (typeParameters.length == 0) {
                    this.ownerType = declaringClass;
                }
                else {
                    this.ownerType = new ParameterizedTypeImpl(declaringClass, typeParameters, null);
                }
            }
        }
    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.actualTypeArguments;
    }

    @Override
    public Class getRawType() {
        return this.rawType;
    }

    @Nullable
    @Override
    public Type getOwnerType() {
        return this.ownerType;
    }

    /*
     * From the JavaDoc for java.lang.reflect.ParameterizedType
     * "Instances of classes that implement this interface must
     * implement an equals() method that equates any two instances
     * that share the same generic type declaration and have equal
     * type parameters."
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ParameterizedType) {
            // Check that information is equivalent
            ParameterizedType that = (ParameterizedType) o;

            if (this == that) {
                return true;
            }

            Type thatOwner = that.getOwnerType();
            Type thatRawType = that.getRawType();

            return
                    Objects.equals(this.ownerType, thatOwner) &&
                            Objects.equals(this.rawType, thatRawType) &&
                            Arrays.equals(this.actualTypeArguments, // avoid clone
                                    that.getActualTypeArguments());
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return
                Arrays.hashCode(this.actualTypeArguments) ^
                        Objects.hashCode(this.ownerType) ^
                        Objects.hashCode(this.rawType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        if (this.ownerType != null) {
            sb.append(this.ownerType.getTypeName());
            sb.append("$");

            if (this.ownerType instanceof ParameterizedTypeImpl) {
                // Find simple name of nested type by removing the
                // shared prefix with owner.
                sb.append(this.rawType.getName().replace(((ParameterizedTypeImpl) this.ownerType).rawType.getName() + "$", ""));
            }
            else {
                sb.append(this.rawType.getSimpleName());
            }
        }
        else {
            sb.append(this.rawType.getName());
        }

        StringJoiner sj = new StringJoiner(", ", "<", ">");
        sj.setEmptyValue("");
        for (Type t: this.actualTypeArguments) {
            sj.add(t.getTypeName());
        }
        sb.append(sj.toString());

        return sb.toString();
    }
}
