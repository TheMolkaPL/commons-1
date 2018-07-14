package org.diorite.commons.reflect;

import org.diorite.commons.function.predicate.Predicate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

public final class Annotations {
    protected Collection<Predicate<? extends Annotation>> withAnnotations    = new ArrayList<>();
    protected Collection<Predicate<? extends Annotation>> withoutAnnotations = new ArrayList<>();

    Annotations() {}

    public static Annotations lookup() {
        return new Annotations();
    }

    public <A extends Annotation> Annotations withAnnotation(Class<A> annotationType, java.util.function.Predicate<A> predicate) {
        return this.withAnnotation(a -> (a.annotationType() == annotationType) && predicate.test((A) a));
    }

    public Annotations withAnnotation(java.util.function.Predicate<? extends Annotation> predicate) {
        return this.withAnnotation(Predicate.fromJava(predicate));
    }

    private Annotations withAnnotation(Predicate<? extends Annotation> predicate) {
        this.withAnnotations.add(predicate);
        return this;
    }

    public Annotations withoutAnnotations(Class<? extends Annotation>... annotationTypes) {
        for (Class<? extends Annotation> annotationType: annotationTypes) {
            this.withoutAnnotations(a -> a.annotationType() == annotationType);
        }
        return this;
    }

    public Annotations withoutAnnotations(Annotation... annotations) {
        for (Annotation annotation: annotations) {
            this.withoutAnnotations(annotation::equals);
        }
        return this;
    }

    public <A extends Annotation> Annotations withoutAnnotations(Class<A> annotationType, java.util.function.Predicate<A> predicate) {
        return this.withoutAnnotations(a -> (a.annotationType() == annotationType) && predicate.test((A) a));
    }

    public Annotations withoutAnnotations(java.util.function.Predicate<? extends Annotation> predicate) {
        return this.withoutAnnotations(Predicate.fromJava(predicate));
    }

    private Annotations withoutAnnotations(Predicate<? extends Annotation> predicate) {
        this.withAnnotations.add(predicate);
        return this;
    }

    LookupAnnotationsHelper build() {
        return new LookupAnnotationsHelper(withAnnotations, withoutAnnotations);
    }
}
