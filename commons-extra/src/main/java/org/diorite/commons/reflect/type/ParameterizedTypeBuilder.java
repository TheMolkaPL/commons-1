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
import org.diorite.commons.reflect.ReflectionUtils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public final class ParameterizedTypeBuilder<T> extends TypeBuilder {
    final Class<T>                            type;
    final Map<TypeVariable<Class<T>>, Type>   parameters               = new LinkedHashMap<>();
    final Map<String, TypeVariable<Class<T>>> parametersByName         = new LinkedHashMap<>();
    final Set<String>                         duplicatedParameterNames = new HashSet<>();
    final Deque<TypeVariable<Class<T>>>       toFill                   = new LinkedList<>();

    @SuppressWarnings("unchecked")
    ParameterizedTypeBuilder(ParameterizedType type) {
        this.type = (Class<T>) type.getRawType();
        Type[] actualTypeArguments = type.getActualTypeArguments();
        TypeVariable<Class<T>>[] typeParameters = this.type.getTypeParameters();
        if (actualTypeArguments.length != typeParameters.length) {
            throw new RuntimeException(
                    "Unexpected length differ between type parameters (" + typeParameters.length + ") and actual type arguments (" +
                            actualTypeArguments.length + ")!");
        }
        for (int i = 0, typeParametersLength = typeParameters.length; i < typeParametersLength; i++) {
            TypeVariable<Class<T>> typeVariable = typeParameters[i];
            this.toFill.addLast(typeVariable);
            this.parameters.put(typeVariable, actualTypeArguments[i]);
            if (this.parametersByName.put(typeVariable.getName(), typeVariable) != null) {
                this.duplicatedParameterNames.add(typeVariable.getName());
            }
        }
    }

    ParameterizedTypeBuilder(Class<T> type) {
        this.type = type;
        for (TypeVariable<Class<T>> typeVariable: type.getTypeParameters()) {
            this.toFill.addLast(typeVariable);
            this.parameters.put(typeVariable, WildcardTypeImpl.wildcard());
            if (this.parametersByName.put(typeVariable.getName(), typeVariable) != null) {
                this.duplicatedParameterNames.add(typeVariable.getName());
            }
        }
    }

    /**
     * Adds {@link TypeVariable} as generic type to this builder from given {@link GenericDeclaration} with given name.
     *
     * @param of source of type variable (generic declaration, like a class, method or constructor) to assign.
     * @param typeName name of type variable to get from "of" generic declaration. (Like K and V in {@literal Map<K, V>}) of build class.
     *
     * @throws IllegalStateException if "of" generic declaration type source contains more than one parameter with given type name.
     * @throws IllegalArgumentException if given generic declaration does not contains type with given name.
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     * @see ReflectionUtils#typeVariableOf(GenericDeclaration, String)
     */
    public ParameterizedTypeBuilder<T> withTypeVariableOf(GenericDeclaration of, String typeName) throws IllegalArgumentException {
        TypeVariable<GenericDeclaration> typeVariable = TypeBuilder.typeVariableOf(of, typeName);
        if (typeVariable == null) {
            throw new IllegalArgumentException("Missing parameter with name `" + typeName + "` inside `" + of + "`");
        }
        return this.with(TypeBuilder.typeVariableOf(of, typeName));
    }

    /**
     * Set given type variable to given type, if type does not belong to type being build by this builder {@link IllegalArgumentException}
     * will be thrown.
     *
     * @param name name of type variable (Like K and V in {@literal Map<K, V>}) of build class.
     * @param of source of type variable (generic declaration, like a class, method or constructor) to assign.
     * @param typeName name of type variable to get from "of" generic declaration. (Like K and V in {@literal Map<K, V>}) of build class.
     *
     * @return this same builder instance.
     *
     * @throws IllegalArgumentException if given generic type name does not belong to type being build by this builder.
     * @throws IllegalStateException if "of" generic declaration type source contains more than one parameter with given type name.
     * @throws IllegalStateException if builder contains more than one parameter with given name.
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withTypeVariableOf(String name, GenericDeclaration of, String typeName) {
        return this.with(name, this.findTypeVariableOrThrow(of, typeName));
    }


    /**
     * @param index index of type variable in type being build to fill with given type.
     * @param of source of type variable (generic declaration, like a class, method or constructor) to assign.
     * @param typeName name of type variable to get from "of" generic declaration. (Like K and V in {@literal Map<K, V>}) of build class.
     *
     * @return this same builder instance.
     *
     * @throws IllegalStateException if "of" generic declaration type source contains more than one parameter with given type name.
     * @throws IndexOutOfBoundsException if index of parameter to fill will be out of bounds in
     *         {@link GenericDeclaration#getTypeParameters()}
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withTypeVariableOf(int index, GenericDeclaration of, String typeName) {
        return this.with(index, this.findTypeVariableOrThrow(of, typeName));
    }


    /**
     * Adds {@link TypeVariable} as generic type to this builder from given {@link GenericDeclaration} on given index.
     *
     * @param of source of type variable (generic declaration, like a class, method or constructor) to assign.
     * @param typeIndex index of type variable to get.
     *
     * @throws IndexOutOfBoundsException if type index parameter will be out of bounds in {@link GenericDeclaration#getTypeParameters()}
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     * @see ReflectionUtils#typeVariableOf(GenericDeclaration, String)
     */
    public ParameterizedTypeBuilder<T> withTypeVariableOf(GenericDeclaration of, int typeIndex) {
        return this.with(TypeBuilder.typeVariableOf(of, typeIndex));
    }

    /**
     * Set given type variable to type variable from given generic declaration.
     *
     * @param name name of type variable (Like K and V in {@literal Map<K, V>}) of build class.
     * @param of source of type variable (generic declaration, like a class, method or constructor) to assign.
     * @param typeIndex index of type variable to get from "of" generic declaration.
     *
     * @return this same builder instance.
     *
     * @throws IllegalArgumentException if given generic type name does not belong to type being build by this builder.
     * @throws IllegalStateException if builder contains more than one parameter with given name.
     * @throws IndexOutOfBoundsException if type index parameter will be out of bounds in {@link GenericDeclaration#getTypeParameters()}
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withTypeVariableOf(String name, GenericDeclaration of, int typeIndex) {
        return this.with(name, TypeBuilder.typeVariableOf(of, typeIndex));
    }

    /**
     * Set type variable on given index to type variable from given generic declaration.
     *
     * @param index index of type variable in type being build to fill with given type.
     * @param of source of type variable (generic declaration, like a class, method or constructor) to assign.
     * @param typeIndex index of type variable to get from "of" generic declaration.
     *
     * @return this same builder instance.
     *
     * @throws IndexOutOfBoundsException if type index parameter will be out of bounds in {@link GenericDeclaration#getTypeParameters()}
     * @throws IndexOutOfBoundsException if index of parameter to fill will be out of bounds in
     *         {@link GenericDeclaration#getTypeParameters()}
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withTypeVariableOf(int index, GenericDeclaration of, int typeIndex) {
        return this.with(index, TypeBuilder.typeVariableOf(of, typeIndex));
    }

    /**
     * Set next available (not yet filled) type variable to a given type defined as wildcard {@literal ? extends type}.
     *
     * @param type type to use as extends bound of wildcard {@literal ? extends type} to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withExtends(Type type) {
        return this.with(TypeBuilder.wildcardExtends(type));
    }

    /**
     * Set given type variable to a given type defined as wildcard {@literal ? extends type}.
     *
     * @param name name of type variable (Like K and V in {@literal Map<K, V>}) of build class.
     * @param type type to use as extends bound of wildcard {@literal ? extends type} to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws IllegalArgumentException if given generic type name does not belong to type being build by this builder.
     * @throws IllegalStateException if builder contains more than one parameter with given name.
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withExtends(String name, Type type) {
        return this.with(name, TypeBuilder.wildcardExtends(type));
    }

    /**
     * Sets type parameter on given index to given type.
     *
     * @param index index of type variable in type being build to fill with given type.
     * @param type type to use as extends bound of wildcard {@literal ? extends type} to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws IndexOutOfBoundsException if type index parameter will be out of bounds in {@link GenericDeclaration#getTypeParameters()}
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withExtends(int index, Type type) {
        return this.with(index, TypeBuilder.wildcardExtends(type));
    }


    /**
     * Set next available (not yet filled) type variable to a given type defined as wildcard {@literal ? super type}.
     *
     * @param type type to use as extends bound of wildcard {@literal ? super type} to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withSuper(Type type) {
        return this.with(TypeBuilder.wildcardSuper(type));
    }

    /**
     * Set given type variable to a given type defined as wildcard {@literal ? super type}.
     *
     * @param name name of type variable (Like K and V in {@literal Map<K, V>}) of build class.
     * @param type type to use as extends bound of wildcard {@literal ? super type} to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws IllegalArgumentException if given generic type name does not belong to type being build by this builder.
     * @throws IllegalStateException if builder contains more than one parameter with given name.
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withSuper(String name, Type type) {
        return this.with(name, TypeBuilder.wildcardSuper(type));
    }

    /**
     * Sets type parameter on given index to given type.
     *
     * @param index index of type variable in type being build to fill with given type.
     * @param type type to use as extends bound of wildcard {@literal ? super type} to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws IndexOutOfBoundsException if type index parameter will be out of bounds in {@link GenericDeclaration#getTypeParameters()}
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> withSuper(int index, Type type) {
        return this.with(index, TypeBuilder.wildcardSuper(type));
    }

    /**
     * Sets type parameter on given index to given type.
     *
     * @param index index of type variable in type being build to fill with given type.
     * @param type type to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws IndexOutOfBoundsException if index of parameter to fill will be out of bounds in
     *         {@link GenericDeclaration#getTypeParameters()}
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> with(int index, Type type) {
        return this.with(this.type.getTypeParameters()[index], type);
    }

    /**
     * Fill next not yet assigned types with given types, all types that were set using other methods, like by name or index will be NOT
     * affected, example: <br>
     * <blockquote><pre>{@code
     * class MyClass<A, B, C, D> {}
     * Type build = TypeBuilder.parameterized(MyClass.class).with(0, String.class).withSuper("C", Number.class).with(Set.class, List.class);
     * }</pre></blockquote>
     * Will produce {@code MyClass<String, Set, ? super Number, List>} <br>
     *
     * @param types types to use.
     *
     * @return this same builder instance.
     *
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     * @throws IllegalArgumentException If given types array is longer than rest of types to fill.
     */
    public ParameterizedTypeBuilder<T> with(Type... types) {
        if (this.toFill.size() < types.length) {
            throw new IllegalArgumentException("There is less types to fill than provided! " + this.toFill.size() + " < " + types.length);
        }
        for (Type type: types) {
            this.with(this.toFill.getFirst(), type);
        }
        return this;
    }

    /**
     * Set type variable of given name to given type.
     *
     * @param name name of type variable (Like K and V in {@literal Map<K, V>}) of build class.
     * @param type type to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws IllegalStateException if builder contains more than one parameter with given name.
     * @throws IllegalArgumentException if type does not belong to type being build by this builder.
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> with(String name, Type type) {
        if (this.duplicatedParameterNames.contains(name)) {
            throw new IllegalStateException(
                    "Duplicated parameter names, more than ore parameter use " + name + " as its name (probably " +
                            "code is obfuscated).");
        }
        TypeVariable<Class<T>> typeVariable = this.parametersByName.get(name);
        if (typeVariable == null) {
            throw new IllegalArgumentException("Missing parameter with name `" + name + "`! " + this.parametersByName.keySet());
        }
        return this.with(typeVariable, type);
    }

    /**
     * Set given type variable to given type.
     *
     * @param typeVariable type variable of build class.
     * @param type type to use as given generic/variable type.
     *
     * @return this same builder instance.
     *
     * @throws IllegalArgumentException if type does not belong to type being build by this builder.
     * @throws ClassCastException If given type isn't assignable to given variable type
     *         {@link ReflectionUtils#isAssignable(TypeVariable, Type)} is used
     *         for this check.
     */
    public ParameterizedTypeBuilder<T> with(TypeVariable<Class<T>> typeVariable, Type type) {
        if (! this.parameters.containsKey(typeVariable)) {
            throw new IllegalArgumentException(
                    "Missing parameter `" + typeVariable + "` inside `" + this.type + "`! " + this.parametersByName.keySet());
        }
        if (! ReflectionUtils.isAssignable(typeVariable, type)) {
            throw new ClassCastException("Can't cast `" + type.getTypeName() + "` to `" + this.typeVariableToString(typeVariable) + "`");
        }
        this.parameters.put(typeVariable, type);
        this.toFill.remove(typeVariable);
        return this;
    }

    /**
     * Builds this type and wraps it into the array.
     *
     * @return generic array type instance.
     */
    public GenericArrayType asArray() {
        return TypeBuilder.array(this.build());
    }

    /**
     * Builds this type and wraps it into the array with given dimensions.
     *
     * @return generic array type instance.
     */
    public GenericArrayType asArray(int dimensions) {
        return TypeBuilder.array(this.build(), dimensions);
    }

    private String typeVariableToString(TypeVariable typeVariable) {
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length == 0) {
            return typeVariable.getName();
        }
        StringBuilder str = new StringBuilder(40);
        str.append('(');
        str.append(typeVariable.getName());
        str.append(" extends ");
        for (int i = 0; i < bounds.length; i++) {
            Type bound = bounds[i];
            if (bound instanceof TypeVariable) {
                str.append(this.typeVariableToString(typeVariable));
            }
            else {
                str.append(bound.getTypeName());
            }
            if ((i + 1) < bounds.length) {
                str.append(" & ");
            }
        }
        str.append(')');
        return str.toString();
    }

    private TypeVariable<GenericDeclaration> findTypeVariableOrThrow(GenericDeclaration of, String typeName) {
        TypeVariable<GenericDeclaration> typeVariable = TypeBuilder.typeVariableOf(of, typeName);
        if (typeVariable == null) {
            throw new IllegalArgumentException("Missing parameter with name `" + typeName + "` inside `" + of + "`");
        }
        return typeVariable;
    }

    @Override
    public ParameterizedType build() {
        return new ParameterizedTypeImpl(this.type, this.parameters.values().toArray(ArrayUtils.EMPTY_TYPES), null);
    }
}
