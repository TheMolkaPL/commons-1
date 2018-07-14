package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"RawTypeCanBeGeneric", "unchecked"})
abstract class LoadedAnnotatedElementMirror<E extends AnnotatedElement> implements AnnotatedElementMirror {
    protected final E                     element;
    private final   Map<String, Class<?>> annotationsTypes = new LinkedHashMap<>();

    LoadedAnnotatedElementMirror(E element) {
        this.element = element;

        for (Annotation annotation: this.getAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            this.annotationsTypes.put(MirrorUtils.getName(annotationType), annotationType);
        }
    }

    @Override
    public List<? extends String> getDeclaredAnnotationsTypes() {
        return new ArrayList<>(this.annotationsTypes.keySet());
    }

    @Override
    @Nullable
    public AnnotationMirror getMirroredAnnotation(String annotationClass) {
        Class annotationType = this.annotationsTypes.get(annotationClass);
        if (annotationType == null) {
            return null;
        }
        Annotation annotation = this.getAnnotation(annotationType);
        if (annotation == null) {
            return null;
        }
        return AnnotationMirror.of(annotation);
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredAnnotations() {
        return MirrorUtils.mirror(this.getAnnotations());
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredAnnotationsByType(String annotationClass) {
        Class annotationType = this.annotationsTypes.get(annotationClass);
        if (annotationType == null) {
            return List.of();
        }
        return MirrorUtils.mirror(this.getAnnotationsByType(annotationType));
    }

    @Override
    @Nullable
    public AnnotationMirror getMirroredDeclaredAnnotation(String annotationClass) {
        Class annotationType = this.annotationsTypes.get(annotationClass);
        if (annotationType == null) {
            return null;
        }
        Annotation declaredAnnotation = this.getDeclaredAnnotation(annotationType);
        if (declaredAnnotation == null) {
            return null;
        }
        return AnnotationMirror.of(declaredAnnotation);
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredDeclaredAnnotationsByType(String annotationClass) {
        Class annotationType = this.annotationsTypes.get(annotationClass);
        if (annotationType == null) {
            return List.of();
        }
        return MirrorUtils.mirror(this.getDeclaredAnnotationsByType(annotationType));
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredDeclaredAnnotations() {
        return MirrorUtils.mirror(this.getDeclaredAnnotations());
    }

    @Override
    @Nullable
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return this.element.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.element.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.element.getDeclaredAnnotations();
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public E resolve() {
        return this.element;
    }

    @Override
    public E getIfResolved() {
        return this.element;
    }
}
