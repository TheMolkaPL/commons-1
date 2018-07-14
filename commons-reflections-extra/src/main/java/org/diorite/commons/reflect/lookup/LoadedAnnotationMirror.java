package org.diorite.commons.reflect.lookup;

import org.diorite.commons.reflect.ReflectionUtils;
import org.diorite.commons.reflect.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.List;

class LoadedAnnotationMirror implements AnnotationMirror {
    private final Annotation annotation;

    LoadedAnnotationMirror(Annotation annotation) {this.annotation = annotation;}

    @Override
    public String annotationType() {
        return MirrorUtils.getName(this.annotation.annotationType());
    }

    @Override
    public List<? extends String> getProperties() {
        return List.copyOf(AnnotationUtils.getValues(this.annotation).keySet());
    }

    @Override
    public Class<?> getTypeOf(String property) {
        Object value = this.getValue(property);
        if (value instanceof Annotation) {
            return ((Annotation) value).annotationType();
        }
        return ReflectionUtils.getPrimitive(value.getClass());
    }

    @Override
    public String getTypeNameOf(String property) {
        return MirrorUtils.getName(this.getTypeOf(property));
    }

    @Override
    public Object getValue(String property) {
        Object value = AnnotationUtils.getValues(this.annotation).get(property);
        if (value == null) {
            throw new IllegalStateException("Missing property: " + property);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String property, Class<T> asType) {
        Object value = this.getValue(property);
        if ((value.getClass() == ReflectionUtils.getWrapperClass(asType))
                    || ((value instanceof Annotation) && (asType == Annotation.class))) {
            return (T) value;
        }
        if ((asType == AnnotationMirror.class) && (value instanceof Annotation)) {
            return (T) AnnotationMirror.of((Annotation) value);
        }
        throw new IllegalStateException("Property " + property + " is not compatible with given type: " + asType);
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public Annotation resolve() {
        return this.annotation;
    }

    @Override
    public Annotation getIfResolved() {
        return this.annotation;
    }
}

