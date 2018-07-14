package org.diorite.commons.reflect;

import org.diorite.commons.function.predicate.IntPredicate;
import org.diorite.commons.function.predicate.Predicate;
import org.diorite.commons.reflect.type.TypeMatcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
abstract class MemberLookup<T, R, B extends MemberLookup<T, R, B>> {
    protected final Class<T>                                    inClass;
    protected       boolean                                     inSupertypes;
    protected       boolean                                     ensureAccessible;
    protected       Predicate<String>                           nameMatcher        = name -> true;
    protected       int                                         index              = - 1;
    protected       TypeMatcher<?>                              resultType         = type -> true;
    protected       TypeMatcher<? extends AccessibleObject>     memberTypeMatcher  = TypeMatcher.assignable(AccessibleObject.class);
    protected       IntPredicate                                isStatic           = modifier -> true;
    protected       IntPredicate                                isFinal            = modifier -> true;
    protected       IntPredicate                                isNative           = modifier -> true;
    protected       Predicate<? extends AccessibleObject>       isAccessible       = object -> true;

    MemberLookup(Class<T> inClass) {
        this.inClass = inClass;
    }

    FieldLookup<T, Object> fields() {
        this.nameMatcher = name -> true;
        this.resultType = TypeMatcher.is(this.inClass);
        this.memberTypeMatcher = TypeMatcher.assignable(Field.class);
        return (FieldLookup<T, Object>) this;
    }

    protected B lookup() {
        return (B) this;
    }

    public B includeSupertypes() {
        this.inSupertypes = true;
        return this.lookup();
    }

    public B excludeSupertypes() {
        this.inSupertypes = false;
        return this.lookup();
    }

    public final B name(String name) {
        return this.name(Predicate.isEqual(name));
    }

    public final B nameMatches(String name) {
        return this.name(Predicate.fromJava(Pattern.compile(name).asPredicate()));
    }

    public final B name(Pattern name) {
        return this.name(Predicate.fromJava(name.asPredicate()));
    }

    public final B name(java.util.function.Predicate<String> namePredicate) {
        return this.name(Predicate.fromJava(namePredicate));
    }

    public B name(Predicate<String> namePredicate) {
        this.nameMatcher = namePredicate;
        return this.lookup();
    }

    public B index(int index) {
        this.index = index;
        return this.lookup();
    }

    public B isStatic() {
        this.isStatic = Modifier::isStatic;
        return this.lookup();
    }

    public B notStatic() {
        this.isStatic = IntPredicate.negated(Modifier::isStatic);
        return this.lookup();
    }

    public B isFinal() {
        this.isFinal = Modifier::isFinal;
        return this.lookup();
    }

    public B notFinal() {
        this.isFinal = IntPredicate.negated(Modifier::isFinal);
        return this.lookup();
    }

    public B isNative() {
        this.isNative = Modifier::isNative;
        return this.lookup();
    }

    public B notNative() {
        this.isNative = IntPredicate.negated(Modifier::isNative);
        return this.lookup();
    }

    public B accessible() {
        this.isAccessible = AccessibleObject::isAccessible;
        return this.lookup();
    }

    public B ensureAccessible() {
        this.ensureAccessible = true;
        return this.lookup();
    }

    public B withAnnotations(Class<? extends Annotation>... annotationTypes) {
        for (Class<? extends Annotation> annotationType: annotationTypes) {
            this.withAnnotation(a -> a.annotationType() == annotationType);
        }
        return this.lookup();
    }

    public B withAnnotations(Annotation... annotations) {
        for (Annotation annotation: annotations) {
            this.withAnnotation(annotation::equals);
        }
        return this.lookup();
    }

    public B withAnnotation(java.util.function.Predicate<? extends Annotation> predicate) {
        return this.withAnnotation(Predicate.fromJava(predicate));
    }

}
