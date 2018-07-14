package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

abstract class CachedAnnotatedElementMirror<T extends AnnotatedElementMirror> extends CachedMirror<T> implements AnnotatedElementMirror {
    CachedAnnotatedElementMirror(T element) {
        super(element);
    }

    @Override
    public List<? extends String> getDeclaredAnnotationsTypes() {
        return this.cached("getDeclaredAnnotationsTypes", this.element::getDeclaredAnnotationsTypes);
    }

    @Override
    public boolean isAnnotationPresent(String annotationClass) {
        return this.cached("isAnnotationPresent", annotationClass, this.element::isAnnotationPresent);
    }

    @Override
    @Nullable
    public AnnotationMirror getMirroredAnnotation(String annotationClass) {
        return this.cached("getMirroredAnnotation", annotationClass, this.element::getMirroredAnnotation);
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredAnnotations() {
        return this.cached("getMirroredAnnotations", this.element::getMirroredAnnotations);
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredAnnotationsByType(String annotationClass) {
        return this.cached("getMirroredAnnotationsByType", annotationClass, this.element::getMirroredAnnotationsByType);
    }

    @Override
    @Nullable
    public AnnotationMirror getMirroredDeclaredAnnotation(String annotationClass) {
        return this.cached("getMirroredDeclaredAnnotation", annotationClass, this.element::getMirroredDeclaredAnnotation);
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredDeclaredAnnotationsByType(String annotationClass) {
        return this.cached("getMirroredDeclaredAnnotationsByType", annotationClass, this.element::getMirroredDeclaredAnnotationsByType);
    }

    @Override
    public List<? extends AnnotationMirror> getMirroredDeclaredAnnotations() {
        return this.cached("getMirroredDeclaredAnnotations", this.element::getMirroredDeclaredAnnotations);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.cached("isAnnotationPresent", annotationClass, this.element::isAnnotationPresent);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return this.cached("getAnnotation", annotationClass, this.element::getAnnotation);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.cached("getAnnotations", this.element::getAnnotations);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationClass) {
        return this.cached("getAnnotationsByType", annotationClass, this.element::getAnnotationsByType);
    }

    @Override
    public <A extends Annotation> A getDeclaredAnnotation(Class<A> annotationClass) {
        return this.cached("getDeclaredAnnotation", annotationClass, this.element::getDeclaredAnnotation);
    }

    @Override
    public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> annotationClass) {
        return this.cached("getDeclaredAnnotationsByType", annotationClass, this.element::getDeclaredAnnotationsByType);
    }

    @Override
    public List<? extends Annotation> getAnnotationsAsList() {
        return this.cached("getAnnotationsAsList", this.element::getAnnotationsAsList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Annotation> List<? extends A> getAnnotationsByTypeAsList(Class<? extends A> annotationClass) {
        return this.cached("getAnnotationsByTypeAsList", annotationClass,
                arg -> (List) this.element.getAnnotationsByTypeAsList(arg));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Annotation> List<? extends A> getDeclaredAnnotationsByTypeAsList(Class<? extends A> annotationClass) {
        return this.cached("getDeclaredAnnotationsByTypeAsList", annotationClass,
                arg -> (List) this.element.getDeclaredAnnotationsByTypeAsList(arg));
    }

    @Override
    public List<? extends Annotation> getDeclaredAnnotationsAsList() {
        return this.cached("getDeclaredAnnotationsAsList", this.element::getDeclaredAnnotationsAsList);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.element.getDeclaredAnnotations();
    }

    @Override
    public AnnotatedElement resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public AnnotatedElement getIfResolved() {
        return this.element.getIfResolved();
    }
}
