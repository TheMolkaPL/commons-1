package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

final class MirrorUtils {
    private MirrorUtils() {}

    static String getName(Class type) {
        return type.getCanonicalName();
    }

    @Nullable
    static String getNameNullable(@Nullable Class type) {
        return (type == null) ? null : getName(type);
    }

    static List<? extends String> typeNames(Class... types) {
        List<String> results = new ArrayList<>(types.length);
        for (AnnotatedElement type: types) {
            results.add(types.getClass().getCanonicalName());
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    static List<? extends AnnotationMirror> mirror(Annotation... annotations) {
        List<AnnotationMirror> results = new ArrayList<>(annotations.length);
        for (Annotation annotation: annotations) {
            results.add(AnnotationMirror.of(annotation));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    static <T extends AnnotatedElementMirror> List<? extends T> mirror(AnnotatedElement... elements) {
        List<T> results = new ArrayList<>(elements.length);
        for (AnnotatedElement type: elements) {
            results.add((T) AnnotatedElementMirror.of(type));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    static <T extends TypeMirror> List<? extends T> mirrorTypes(Type... types) {
        List<T> results = new ArrayList<>(types.length);
        for (Type type: types) {
            results.add((T) TypeMirror.of(type));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    static <T extends AnnotatedTypeMirror> List<? extends T> mirrorTypes(AnnotatedType... types) {
        List<T> results = new ArrayList<>(types.length);
        for (AnnotatedType type: types) {
            results.add((T) AnnotatedTypeMirror.of(type));
        }
        return results;
    }

    static final Map<Class, Function> creators   = new LinkedHashMap<>(Map.ofEntries(
            converter(Class.class, LoadedClassMirror::new),
            converter(Field.class, LoadedFieldMirror::new),
            converter(Method.class, LoadedExecutableMirror::new),
            converter(Constructor.class, LoadedExecutableMirror::new),
            converter(Annotation.class, LoadedAnnotationMirror::new),
            converter(AnnotatedArrayType.class, LoadedAnnotatedArrayTypeMirror::new),
            converter(AnnotatedParameterizedType.class, LoadedAnnotatedParameterizedTypeMirror::new),
            converter(AnnotatedWildcardType.class, LoadedAnnotatedWildcardTypeMirror::new),
            converter(GenericArrayType.class, LoadedGenericArrayTypeMirror::new),
            converter(ParameterizedType.class, LoadedParameterizedTypeMirror::new),
            converter(WildcardType.class, LoadedWildcardTypeMirror::new),
            converter(TypeVariable.class, LoadedTypeVariableMirror::new)
    ));
    static final Map<Class, Function> cachedFunc = new LinkedHashMap<>(Map.ofEntries(
            converter(LoadedClassMirror.class, CachedClassMirror::new),
            converter(LoadedFieldMirror.class, CachedFieldMirror::new),
            converter(LoadedExecutableMirror.class, CachedMethodMirror::new),
            converter(LoadedAnnotationMirror.class, CachedAnnotationMirror::new),
            converter(LoadedAnnotatedArrayTypeMirror.class, CachedAnnotatedArrayTypeMirror::new),
            converter(LoadedAnnotatedParameterizedTypeMirror.class, CachedAnnotatedParameterizedTypeMirror::new),
            converter(LoadedAnnotatedWildcardTypeMirror.class, CachedAnnotatedWildcardTypeMirror::new),
            converter(LoadedGenericArrayTypeMirror.class, CachedGenericArrayTypeMirror::new),
            converter(LoadedParameterizedTypeMirror.class, CachedParameterizedTypeMirror::new),
            converter(LoadedWildcardTypeMirror.class, CachedWildcardTypeMirror::new),
            converter(LoadedTypeVariableMirror.class, CachedTypeVariableMirror::new),

            caches(ClassMirror.class, CachedClassMirror::new),
            caches(FieldMirror.class, CachedFieldMirror::new),
            caches(MethodMirror.class, CachedMethodMirror::new),
            caches(AnnotationMirror.class, CachedAnnotationMirror::new),
            caches(AnnotatedArrayTypeMirror.class, CachedAnnotatedArrayTypeMirror::new),
            caches(AnnotatedParameterizedTypeMirror.class, CachedAnnotatedParameterizedTypeMirror::new),
            caches(AnnotatedWildcardTypeMirror.class, CachedAnnotatedWildcardTypeMirror::new),
            caches(GenericArrayTypeMirror.class, CachedGenericArrayTypeMirror::new),
            caches(ParameterizedTypeMirror.class, CachedParameterizedTypeMirror::new),
            caches(WildcardTypeMirror.class, CachedWildcardTypeMirror::new),
            caches(ClassMirror.class, CachedClassMirror::new),
            caches(TypeVariableMirror.class, CachedTypeVariableMirror::new)
    ));

    private static <T, R> Entry<Class<T>, Function<T, R>> converter(Class<T> type, Function<T, R> func) {
        return Map.entry(type, func);
    }

    private static <T, R extends T> Entry<Class<T>, Function<T, R>> caches(Class<T> type, Function<T, R> func) {
        return Map.entry(type, func);
    }

    private static Function findCreatorFor(Object object) {
        synchronized (creators) {
            Class<?> type = object.getClass();
            Function function = creators.get(type);
            if (function == null) {
                for (Entry<Class, Function> entry: creators.entrySet()) {
                    if (type.isAssignableFrom(entry.getKey())) {
                        function = entry.getValue();
                        break;
                    }
                }
                if (function == null) {
                    throw new IllegalStateException("Unexpected type: " + object);
                }
                creators.put(type, function);
            }
            return function;
        }
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    static <T, R> R cached(@Nullable T type) {
        if (type == null) {
            return null;
        }
        if (type instanceof CachedMirror) {
            return (R) type;
        }
        Object result = type;
        Class<?> typeClass = type.getClass();
        if (! (type instanceof Resolvable)) {
            result = findCreatorFor(type).apply(result);
            typeClass = type.getClass();
        }
        Function function = cachedFunc.get(typeClass);
        if (function == null) {
            throw new IllegalStateException("Unexpected type: " + type);
        }
        result = function.apply(result);
        return (R) result;
    }
}
