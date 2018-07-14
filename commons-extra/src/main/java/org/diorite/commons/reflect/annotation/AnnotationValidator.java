package org.diorite.commons.reflect.annotation;

import org.diorite.commons.array.ArrayUtils;
import org.diorite.commons.reflect.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

final class AnnotationValidator<T extends Annotation> {
    private static final Map<Class, AnnotationValidator> validators = new ConcurrentHashMap<>();

    private final Class<T>                                 annotationType;
    private final Map<String, AnnotationPropertyValidator> properties;

    private AnnotationValidator(Class<T> annotationType,
                                Collection<AnnotationPropertyValidator> properties) {
        this.annotationType = annotationType;
        this.properties = properties.stream().collect(Collectors.toMap(AnnotationPropertyValidator::getName, Function.identity()));
    }

    LinkedHashMap<String, Object> validate(Map<String, ?> values) {
        LinkedHashMap<String, Object> validated = new LinkedHashMap<>(values);

        Set<String> propertiesLeft = new HashSet<>(values.keySet());
        Collection<AnnotationPropertyValidator> properties = new HashSet<>(this.properties.values());
        for (AnnotationPropertyValidator property: properties) {
            if (! propertiesLeft.remove(property.name)) {
                if (property.isNeeded()) {
                    throw new IllegalArgumentException("Missing value for: " + this.annotationType + "." + property.name);
                }
                validated.put(property.name, property.defaultValue);
                continue;
            }
            Object value = values.get(property.name);
            property.validate(property.name, value);
            if (value.getClass().isArray()) {
                validated.put(property.name, ArrayUtils.deepClone(value));
            }
        }

        if (! propertiesLeft.isEmpty()) {
            throw new IllegalArgumentException("Unresolved properties found: " + propertiesLeft);
        }
        return validated;
    }


    @SuppressWarnings("unchecked")
    static <T extends Annotation> AnnotationValidator<T> getOrCreate(Class<T> annotationClass) {
        if (! annotationClass.isAnnotation()) {
            throw new IllegalArgumentException("Expected annotation interface but got: " + annotationClass + " instead.");
        }
        return validators.computeIfAbsent(annotationClass, AnnotationValidator::create);
    }

    private static <T extends Annotation> AnnotationValidator<T> create(Class<T> annotationClass) {
        List<AnnotationPropertyValidator> properties = new ArrayList<>();
        for (Method declaredMethod: annotationClass.getDeclaredMethods()) {
            Object defaultValue = declaredMethod.getDefaultValue();
            Type type = declaredMethod.getGenericReturnType();
            String name = declaredMethod.getName();
            properties.add(new AnnotationPropertyValidator(name, type, defaultValue));
        }
        return new AnnotationValidator<>(annotationClass, properties);
    }

    static class AnnotationPropertyValidator {
        final String name;
        final Type   type;
        final Object defaultValue;

        AnnotationPropertyValidator(String name, Type type, Object defaultValue) {
            this.name = name;
            this.type = type;
            if ((defaultValue != null) && defaultValue.getClass().isArray()) {
                this.defaultValue = ArrayUtils.deepClone(defaultValue);
            }
            else {
                this.defaultValue = defaultValue;
            }
        }

        String getName() {
            return this.name;
        }

        boolean isNeeded() {
            return this.defaultValue == null;
        }

        void validate(String name, Object value) {
            if (! name.equals(this.name)) {
                throw new AssertionError();
            }
            if (value == null) {
                throw new IllegalArgumentException("Value cannot be null for property: " + name);
            }
            if (! ReflectionUtils.isAssignable(value.getClass(), this.type)) {
                throw new IllegalArgumentException(
                        "Value type is not assignable to annotation property value: " + value.getClass() + " is not assignable to " + this.type);
            }
        }
    }
}
