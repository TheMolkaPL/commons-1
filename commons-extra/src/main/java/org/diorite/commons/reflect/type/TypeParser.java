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

import org.diorite.commons.parser.ParserContext;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Parser for generic type signatures of fields. see {@link #parse(String)} for more information.
 *
 * @see #parse(String)
 */
public final class TypeParser {
    private final Collection<ClassLoader>         classLoaders = new LinkedBlockingDeque<>();
    @Nullable
    private final ConcurrentHashMap<String, Type> cache;
    private final Collection<String>              imports      = new LinkedBlockingDeque<>();
    @Nullable
    private final TypeParser                      parent;

    TypeParser(Collection<? extends ClassLoader> classLoaders, boolean cached, Collection<? extends String> imports,
               @Nullable TypeParser parent) {
        this.parent = parent;
        this.classLoaders.addAll(classLoaders);
        this.cache = cached ? new ConcurrentHashMap<>() : null;
        // imports duplicated from parent should be removed to improve performance
        Collection<String> importsFiltered = new ArrayList<>(imports);
        importsFiltered.removeAll(this.getImports());
        this.imports.addAll(importsFiltered);
    }

    public static TypeParserBuilder builder() {
        return new TypeParserBuilder();
    }

    /**
     * Returns builder of type parser that will be use this parser as parent.
     *
     * @return builder of type parser.
     */
    public TypeParserBuilder createChild() {
        return new TypeParserBuilder().withParent(this);
    }

    /**
     * Tries to parse given string to {@link Type} instance (it might be a simple class), note that this parser will try to
     * resolve all members. <br>
     * Example valid names: <br>
     * {@literal int} an int <br>
     * {@literal [I} <br>
     * {@literal [Ljava.lang.String;} <br>
     * {@literal java.lang.String} <br>
     * {@literal java.util.List<java.lang.String[]>} <br>
     * {@literal java.util.List<?>} <br>
     * {@literal java.util.Map<? extends java.lang.String, ? super java.lang.Number>} <br>
     * {@literal java.util.Map<? extends java.lang.String, ?>[]} <br>
     * {@literal java.util.Map<? extends java.lang.String, java.lang.Number[][]>[]} <br>
     * {@literal java.util.List} <br>
     * {@literal java.util.List<java.lang.String[]>[]} <br>
     * {@literal java.util.List<? super java.io.Serializable>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest<X>.Inner<java.lang.String>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest$InnerS<java.lang.String>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest$Inner} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest$InnerS<? super java.util.List<java.lang.String>[]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest$InnerS<? extends java.util.List<? super java.lang.String>[]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest$InnerS<? super java.util.List<?>[]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest$InnerS<? super java.util.List[][]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest$InnerS<? super java.util.List[][]>[]} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest<X>.Inner<? super java.util.List<java.lang.String>[]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest<X>.Inner<? extends java.util.List<? super java.lang.String>[]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest<X>.Inner<? super java.util.List<?>[]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest<X>.Inner<? super java.util.List[][]>} <br>
     * {@literal org.diorite.commons.reflections.type.TypeParserTest<X>.Inner<? super java.util.List[][]>[]} <br>
     *
     * Also type parser might use some imports, then it will try to find given classes in some packages, by default most of parsers imports
     * java.lang and java.util packages, so given examples are valid too: <br>
     * {@literal [LString;} <br>
     * {@literal List<String[]>} <br>
     *
     * Also note that both imports of this parser and parent parser will be used. see
     *
     * @param type type generic name representation.
     *
     * @return parsed type instance.
     *
     * @throws TypeParserException if some type can not be resolved or parser didn't manage to parse whole string.
     */
    public Type parse(String type) throws TypeParserException {
        type = type.trim();
        try {
            if (this.cache != null) {
                Type cached = this.cache.get(type);
                if (cached != null) {
                    return cached;
                }
            }
            Type parseType = this.parseType(type);
            if (this.cache != null) {
                Type existing = this.cache.putIfAbsent(type, parseType);
                if (existing != null) {
                    return existing;
                }
            }
            return parseType;
        }
        catch (Exception e) {
            throw new TypeParserException("Can't parse `" + type + "`", e);
        }
    }

    /**
     * Force clears the cache
     */
    public void clearCache() {
        if (this.cache != null) {
            this.cache.clear();
        }
    }

    /**
     * Returns imports used by this parser. (note that this will return both imports of this parser and all imports of parents) <br>
     * Imports are ordered from deepest parent to imports of this parser.
     *
     * @return imports used by this parser.
     */
    public List<? extends String> getImports() {
        ArrayList<String> result = new ArrayList<>();

        // first we need to create structure of parents
        LinkedList<TypeParser> structure = new LinkedList<>();
        TypeParser current = this;
        do {
            structure.add(current);
        }
        while ((current = current.parent) != null);

        // and copy all imports starting from deepest one
        while ((current = structure.pollLast()) != null) {
            result.addAll(current.imports);
        }
        result.trimToSize();
        return result;
    }

    Type parseType(String type) throws ClassNotFoundException {
        {
            Class fastCheck = basicTypes.get(type);
            if (fastCheck != null) {
                return fastCheck;
            }
        }
        ParserContext parserContext = new ParserContext(type);
        // first find name of class
        Class<?> parseClass = this.parseClass(parserContext);
        if (! parserContext.hasNext()) {
            return parseClass;
        }
        // then check if it is generic type or outer class
        ParameterizedTypeImpl generic = this.parseGeneric(parseClass, parseClass.getDeclaringClass(), parserContext);
        Type result = generic;
        if (parserContext.hasNext()) {
            char innerTest = parserContext.next();
            if ((innerTest == '$') || (innerTest == '.')) {
                ParameterizedTypeImpl outer = generic;
                // there might be multiple levels of classes
                do {
                    // that was an outer class, now we need to parse inner one
                    Class<?> innerClass = this.parseInnerClass(outer.getRawType(), parserContext);
                    // Handle case `A<X, Y>$B` java does not allow for such case as it would be represented as simple class
                    if (! parserContext.hasNext()) {
                        if (this.cache != null) {
                            this.cache.putIfAbsent(type, innerClass);
                        }
                        return innerClass;
                    }
                    // and check generics for it too
                    outer = this.parseGeneric(innerClass, outer, parserContext);
                    innerTest = parserContext.next();
                }
                while ((innerTest == '$') || (innerTest == '.'));
                result = outer;
            }
            parserContext.previous();
        }
        if (! parserContext.hasNext()) {
            return result;
        }
        // it might be an array
        String restOfType = parserContext.getText().substring(parserContext.getIndex() + 1);
        int arrays = 0;
        while (restOfType.endsWith("[]")) {
            restOfType = restOfType.substring(0, restOfType.length() - 2);
            result = new GenericArrayTypeImpl(result);
        }
        if (! restOfType.trim().isEmpty()) {
            throw new TypeParserException("Expected end of data, but found: `" + restOfType + "`");
        }
        return result;
    }

    Class<?> parseInnerClass(Class<?> outerClass, ParserContext context) throws ClassNotFoundException {
        StringBuilder classNameBuilder = new StringBuilder(20);
        while (context.hasNext()) {
            char next = context.next();
            if (next == '<') {
                context.previous();
                break;
            }
            classNameBuilder.append(next);
        }
        String className = classNameBuilder.toString();
        for (Class<?> innerClass: outerClass.getDeclaredClasses()) {
            if (innerClass.getSimpleName().equals(className)) {
                return innerClass;
            }
        }
        int indexOf = className.indexOf('$');
        if (indexOf == - 1) {
            indexOf = className.indexOf('.');
        }
        if (indexOf == - 1) {
            Class<?> found;
            try {
                return this.findClass(outerClass.getName() + "$" + className);
            }
            catch (ClassNotFoundException e1) {
                try {
                    return this.findClass(outerClass.getName() + "." + className);
                }
                catch (ClassNotFoundException e2) {
                    ClassNotFoundException exception = new ClassNotFoundException(
                            "Can't find `" + className + "` inner class of: " + outerClass.getName());
                    exception.addSuppressed(e1);
                    exception.addSuppressed(e2);
                    throw exception;
                }
            }
        }
        String innerClassName = className.substring(indexOf + 1);
        return this.parseInnerClass(outerClass, context);
    }

    Class<?> parseClass(ParserContext context) throws ClassNotFoundException {
        StringBuilder className = new StringBuilder(100);
        while (context.hasNext()) {
            char next = context.next();
            if (next == '<') {
                context.previous();
                return this.findClass(className.toString());
            }
            className.append(next);
        }
        return this.findClass(className.toString());
    }

    static final Map<String, Class> basicTypes = new HashMap<>();

    static {
        basicTypes.put("boolean", boolean.class);
        basicTypes.put("byte", byte.class);
        basicTypes.put("short", short.class);
        basicTypes.put("char", char.class);
        basicTypes.put("int", int.class);
        basicTypes.put("long", long.class);
        basicTypes.put("double", double.class);
        basicTypes.put("float", float.class);
        basicTypes.put("string", String.class);
        basicTypes.put("String", String.class);
        basicTypes.put("Boolean", Boolean.class);
        basicTypes.put("Byte", Byte.class);
        basicTypes.put("Short", Short.class);
        basicTypes.put("Character", Character.class);
        basicTypes.put("Integer", Integer.class);
        basicTypes.put("Long", Long.class);
        basicTypes.put("Double", Double.class);
        basicTypes.put("Float", Float.class);
        basicTypes.put("Z", boolean.class);
        basicTypes.put("B", byte.class);
        basicTypes.put("S", short.class);
        basicTypes.put("C", char.class);
        basicTypes.put("I", int.class);
        basicTypes.put("J", long.class);
        basicTypes.put("D", double.class);
        basicTypes.put("F", float.class);
    }

    private Class<?> getClass(String className) throws ClassNotFoundException {
        List<Exception> exceptions = new ArrayList<>(3);
        if (this.cache != null) {
            Type cached = this.cache.get(className);
            if (cached instanceof Class) {
                return (Class<?>) cached;
            }
        }

        Class result = basicTypes.get(className);
        if (result != null) {
            return result;
        }
        for (ClassLoader classLoader: this.classLoaders) {
            try {
                result = Class.forName(className, false, classLoader);
            }
            catch (ClassNotFoundException e) {
                exceptions.add(e);
            }
        }
        for (String anImport: this.imports) {
            for (ClassLoader classLoader: this.classLoaders) {
                try {
                    if (anImport.endsWith("." + className)) {
                        result = Class.forName(anImport, false, classLoader);
                    }
                    else {
                        result = Class.forName(anImport + "." + className, false, classLoader);
                    }
                }
                catch (ClassNotFoundException e) {
                    exceptions.add(e);
                }
            }
        }

        if (result != null) {
            if (this.cache != null) {
                Type cached = this.cache.putIfAbsent(className, result);
                if ((cached instanceof Class)) {
                    return (Class<?>) cached;
                }
            }
            return (Class<?>) result;
        }
        ClassNotFoundException classNotFoundException = new ClassNotFoundException(
                "Can't find class `" + className + "` (imports: " + this.imports + ")");
        for (Exception exception: exceptions) {
            classNotFoundException.addSuppressed(exception);
        }
        throw classNotFoundException;
    }

    Class<?> findClass(String className) throws ClassNotFoundException {
        className = className.trim();
        List<Exception> exceptions = new ArrayList<>();
        if (this.parent != null) {
            try {
                return this.parent.findClass(className);
            }
            catch (ClassNotFoundException e) {
                exceptions.add(e);
            }
        }
        try {
            try {
                return this.getClass(className);
            }
            catch (ClassNotFoundException e1) {
                exceptions.add(e1);
                int arrays = 0;
                String classNameOriginal = className;
                if (className.startsWith("[")) {
                    int index = 0;
                    while (className.charAt(index++) == '[') {
                        arrays += 1;
                    }
                    className = className.substring(arrays);
                    if (className.endsWith(";")) {
                        // to remove leading L too
                        if (className.startsWith("L")) {
                            className = className.substring(1, className.length() - 1);
                        }
                        else {
                            className = className.substring(0, className.length() - 1);
                        }
                    }
                }
                while (className.endsWith("[]")) {
                    arrays += 1;
                    className = className.substring(0, className.length() - 2);
                }
                Class<?> foundClass = this.getClass(className);
                if (arrays != 0) {
                    foundClass = Array.newInstance(foundClass, new int[arrays]).getClass();
                }
                if (this.cache != null) {
                    this.cache.putIfAbsent(foundClass.getName(), foundClass);
                    this.cache.putIfAbsent(classNameOriginal, foundClass);
                }
                return foundClass;
            }
        }
        catch (ClassNotFoundException topException) {
            for (Exception exception: exceptions) {
                topException.addSuppressed(exception);
            }
            throw topException;
        }
    }

    ParameterizedTypeImpl parseGeneric(Class<?> rawType, @Nullable Type outer, ParserContext context) {
        if (context.next() != '<') {
            throw new IllegalStateException();
        }
        char next;
        List<String> genericParts = new ArrayList<>();
        int startIndex = context.getIndex();
        String text = context.getText();
        boolean finished = false;
        int level = 1;
        while (context.hasNext()) {
            next = context.next();
            if (next == ',') {
                genericParts.add(text.substring(startIndex + 1, context.getIndex()).trim());
                context.skipWhitespaces();
                startIndex = context.getIndex();
                continue;
            }
            if (next == '<') {
                level += 1;
                continue;
            }
            if (next == '>') {
                level -= 1;
                if (level != 0) {
                    continue;
                }
                genericParts.add(text.substring(startIndex + 1, context.getIndex()).trim());
                finished = true;
                break;
            }
        }
        if (! finished) {
            throw new TypeParserException("Can't parse generic types, expected to find `>` before stream end. (level: " + level + ")");
        }
        char innerTest = context.next();
        if ((innerTest == '$') || (innerTest == '.')) {
            context.previous();
            TypeVariable<? extends Class<?>>[] typeParameters = rawType.getTypeParameters();
            if (genericParts.size() != typeParameters.length) {
                throw new TypeParserException("Class type parameters are different than expected, expected: " + Arrays.toString
                                                                                                                               (typeParameters) + ", found: " + genericParts);
            }
            return new ParameterizedTypeImpl(rawType, typeParameters, outer);
        }
        context.previous();
        Type[] genericBounds = new Type[genericParts.size()];
        for (int i = 0; i < genericParts.size(); i++) {
            String genericPart = genericParts.get(i);
            genericBounds[i] = this.readSingleGeneric(rawType, outer, genericPart);
        }
        return new ParameterizedTypeImpl(rawType, genericBounds, outer);
    }

    Type readSingleGeneric(Class<?> rawType, @Nullable Type outer, String genericPart) {
        if (genericPart.equals("?")) {
            return WildcardTypeImpl.wildcard();
        }
        boolean extendsWildcard = false;
        boolean superWildcard = false;
        if (genericPart.startsWith("? extends ")) {
            extendsWildcard = true;
            genericPart = genericPart.substring("? extends ".length()).trim();
        }
        else if (genericPart.startsWith("? super ")) {
            superWildcard = true;
            genericPart = genericPart.substring("? super ".length()).trim();
        }

        Type result = this.parse(genericPart);
        if (superWildcard) {
            return WildcardTypeImpl.withSuper(result);
        }
        if (extendsWildcard) {
            return WildcardTypeImpl.withExtends(result);
        }
        return result;
    }

}
