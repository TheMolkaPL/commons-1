package org.diorite.commons.reflect;

import org.diorite.commons.function.predicate.Predicate;

import java.lang.annotation.Annotation;
import java.util.Collection;

public class LookupAnnotationsHelper {
     final Collection<Predicate<? extends Annotation>>      withAnnotations;
          final Collection<Predicate<? extends Annotation>> withoutAnnotations;

    public LookupAnnotationsHelper(
            Collection<Predicate<? extends Annotation>> withAnnotations,
            Collection<Predicate<? extends Annotation>> withoutAnnotations) {
        this.withAnnotations = withAnnotations;
        this.withoutAnnotations = withoutAnnotations;
    }
}
