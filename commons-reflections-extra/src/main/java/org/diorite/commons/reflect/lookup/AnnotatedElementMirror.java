package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.util.List;

/**
 * Represents some annotated element - this might be a class, method, field, constructor or method/constructor parameter, return type,
 * generic type etc... <br>
 * Note that in some cases inherited annotations might be missing - like if super class was not found in provided class paths. <br>
 * For this same reason repeatable annotations might not be compacted to single annotation if type of super annotation is not present on
 * defined scan classpath. <br>
 * This class tries to behave almost ths same as {@link AnnotatedElement}
 */
public interface AnnotatedElementMirror extends AnnotatedElement, Resolvable {
    /**
     * @return list of available annotations
     */
    List<? extends String> getDeclaredAnnotationsTypes();

    /**
     * Returns true if an annotation for the specified type
     * is <em>present</em> on this element, else false.  This method
     * is designed primarily for convenient access to marker annotations.
     *
     * <p>The truth value returned by this method is equivalent to:
     * {@code getAnnotation(annotationClass) != null}
     *
     * <p>The body of the default method is specified to be the code
     * above.
     *
     * @param annotationClass the Class name corresponding to the
     *         annotation type
     *
     * @return true if an annotation for the specified annotation
     *         type is present on this element, else false
     *
     * @throws NullPointerException if the given annotation class is null
     */
    default boolean isAnnotationPresent(String annotationClass) {
        return this.getMirroredAnnotation(annotationClass) != null;
    }

    /**
     * Returns this element's annotation for the specified type if
     * such an annotation is <em>present</em>, else null.
     *
     * @param annotationClass the Class name corresponding to the
     *         annotation type
     *
     * @return this element's annotation for the specified annotation type if
     *         present on this element, else null
     *
     * @throws NullPointerException if the given annotation class is null
     * @since 1.5
     */
    @Nullable
    AnnotationMirror getMirroredAnnotation(String annotationClass);

    /**
     * Returns annotations that are <em>present</em> on this element.
     *
     * If there are no annotations <em>present</em> on this element, the return
     * value is an array of length 0.
     *
     * @return annotations present on this element
     */
    List<? extends AnnotationMirror> getMirroredAnnotations();

    /**
     * Returns annotations that are <em>associated</em> with this element.
     *
     * If there are no annotations <em>associated</em> with this element, the return
     * value is an array of length 0.
     *
     * The difference between this method and {@link #getAnnotation(Class)}
     * is that this method detects if its argument is a <em>repeatable
     * annotation type</em> (JLS 9.6), and if so, attempts to find one or
     * more annotations of that type by "looking through" a container
     * annotation.
     *
     * @param annotationClass the Class name corresponding to the
     *         annotation type
     *
     * @return all this element's annotations for the specified annotation type if
     *         associated with this element, else an array of length zero
     *
     * @throws NullPointerException if the given annotation class is null
     * @implSpec The default implementation first calls {@link
     *         #getDeclaredAnnotationsByType(Class)} passing {@code
     *         annotationClass} as the argument. If the returned array has
     *         length greater than zero, the array is returned. If the returned
     *         array is zero-length and this {@code AnnotatedElement} is a
     *         class and the argument type is an inheritable annotation type,
     *         and the superclass of this {@code AnnotatedElement} is non-null,
     *         then the returned result is the result of calling {@link
     *         #getAnnotationsByType(Class)} on the superclass with {@code
     *         annotationClass} as the argument. Otherwise, a zero-length
     *         array is returned.
     * @since 1.8
     */
    List<? extends AnnotationMirror> getMirroredAnnotationsByType(String annotationClass);

    /**
     * Returns this element's annotation for the specified type if
     * such an annotation is <em>directly present</em>, else null.
     *
     * This method ignores inherited annotations. (Returns null if no
     * annotations are directly present on this element.)
     *
     * @param annotationClass the Class name corresponding to the
     *         annotation type
     *
     * @return this element's annotation for the specified annotation type if
     *         directly present on this element, else null
     *
     * @throws NullPointerException if the given annotation class is null
     * @implSpec The default implementation first performs a null check
     *         and then loops over the results of {@link
     *         #getDeclaredAnnotations} returning the first annotation whose
     *         annotation type matches the argument type.
     * @since 1.8
     */
    @Nullable
    AnnotationMirror getMirroredDeclaredAnnotation(String annotationClass);

    /**
     * Returns this element's annotation(s) for the specified type if
     * such annotations are either <em>directly present</em> or
     * <em>indirectly present</em>. This method ignores inherited
     * annotations.
     *
     * If there are no specified annotations directly or indirectly
     * present on this element, the return value is an array of length
     * 0.
     *
     * The difference between this method and {@link
     * #getDeclaredAnnotation(Class)} is that this method detects if its
     * argument is a <em>repeatable annotation type</em> (JLS 9.6), and if so,
     * attempts to find one or more annotations of that type by "looking
     * through" a container annotation if one is present.
     *
     * @param annotationClass the Class name corresponding to the
     *         annotation type
     *
     * @return all this element's annotations for the specified annotation type if
     *         directly or indirectly present on this element, else an array of length zero
     *
     * @throws NullPointerException if the given annotation class is null
     * @implSpec The default implementation may call {@link
     *         #getDeclaredAnnotation(Class)} one or more times to find a
     *         directly present annotation and, if the annotation type is
     *         repeatable, to find a container annotation. If annotations of
     *         the annotation type {@code annotationClass} are found to be both
     *         directly and indirectly present, then {@link
     *         #getDeclaredAnnotations()} will get called to determine the
     *         order of the elements in the returned array.
     *
     *         <p>Alternatively, the default implementation may call {@link
     *         #getDeclaredAnnotations()} a single time and the returned array
     *         examined for both directly and indirectly present
     *         annotations. The results of calling {@link
     *         #getDeclaredAnnotations()} are assumed to be consistent with the
     *         results of calling {@link #getDeclaredAnnotation(Class)}.
     * @since 1.8
     */
    List<? extends AnnotationMirror> getMirroredDeclaredAnnotationsByType(String annotationClass);

    /**
     * Returns annotations that are <em>directly present</em> on this element.
     * This method ignores inherited annotations.
     *
     * If there are no annotations <em>directly present</em> on this element,
     * the return value is an array of length 0.
     *
     * @return annotations directly present on this element
     */
    List<? extends AnnotationMirror> getMirroredDeclaredAnnotations();

    /**
     * Returns annotations that are <em>present</em> on this element.
     *
     * If there are no annotations <em>present</em> on this element, the return
     * value is an array of length 0.
     *
     * @return annotations present on this element
     */
    default List<? extends Annotation> getAnnotationsAsList() {
        return List.of(this.getAnnotations());
    }

    /**
     * Returns annotations that are <em>associated</em> with this element.
     *
     * If there are no annotations <em>associated</em> with this element, the return
     * value is an array of length 0.
     *
     * The difference between this method and {@link #getAnnotation(Class)}
     * is that this method detects if its argument is a <em>repeatable
     * annotation type</em> (JLS 9.6), and if so, attempts to find one or
     * more annotations of that type by "looking through" a container
     * annotation.
     *
     * @param <T> the type of the annotation to query for and return if present
     * @param annotationClass the Class object corresponding to the
     *         annotation type
     *
     * @return all this element's annotations for the specified annotation type if
     *         associated with this element, else an array of length zero
     *
     * @throws NullPointerException if the given annotation class is null
     * @implSpec The default implementation first calls {@link
     *         #getDeclaredAnnotationsByType(Class)} passing {@code
     *         annotationClass} as the argument. If the returned array has
     *         length greater than zero, the array is returned. If the returned
     *         array is zero-length and this {@code AnnotatedElement} is a
     *         class and the argument type is an inheritable annotation type,
     *         and the superclass of this {@code AnnotatedElement} is non-null,
     *         then the returned result is the result of calling {@link
     *         #getAnnotationsByType(Class)} on the superclass with {@code
     *         annotationClass} as the argument. Otherwise, a zero-length
     *         array is returned.
     */
    default <T extends Annotation> List<? extends T> getAnnotationsByTypeAsList(Class<? extends T> annotationClass) {
        return List.of(this.getAnnotationsByType(annotationClass));
    }

    /**
     * Returns this element's annotation(s) for the specified type if
     * such annotations are either <em>directly present</em> or
     * <em>indirectly present</em>. This method ignores inherited
     * annotations.
     *
     * If there are no specified annotations directly or indirectly
     * present on this element, the return value is an array of length
     * 0.
     *
     * The difference between this method and {@link
     * #getDeclaredAnnotation(Class)} is that this method detects if its
     * argument is a <em>repeatable annotation type</em> (JLS 9.6), and if so,
     * attempts to find one or more annotations of that type by "looking
     * through" a container annotation if one is present.
     *
     * @param <T> the type of the annotation to query for and return
     *         if directly or indirectly present
     * @param annotationClass the Class object corresponding to the
     *         annotation type
     *
     * @return all this element's annotations for the specified annotation type if
     *         directly or indirectly present on this element, else an array of length zero
     *
     * @throws NullPointerException if the given annotation class is null
     * @implSpec The default implementation may call {@link
     *         #getDeclaredAnnotation(Class)} one or more times to find a
     *         directly present annotation and, if the annotation type is
     *         repeatable, to find a container annotation. If annotations of
     *         the annotation type {@code annotationClass} are found to be both
     *         directly and indirectly present, then {@link
     *         #getDeclaredAnnotations()} will get called to determine the
     *         order of the elements in the returned array.
     *
     *         <p>Alternatively, the default implementation may call {@link
     *         #getDeclaredAnnotations()} a single time and the returned array
     *         examined for both directly and indirectly present
     *         annotations. The results of calling {@link
     *         #getDeclaredAnnotations()} are assumed to be consistent with the
     *         results of calling {@link #getDeclaredAnnotation(Class)}.
     */
    default <T extends Annotation> List<? extends T> getDeclaredAnnotationsByTypeAsList(Class<? extends T> annotationClass) {
        return List.of(this.getDeclaredAnnotationsByType(annotationClass));
    }

    /**
     * Returns annotations that are <em>directly present</em> on this element.
     * This method ignores inherited annotations.
     *
     * If there are no annotations <em>directly present</em> on this element,
     * the return value is an array of length 0.
     *
     * @return annotations directly present on this element
     */
    default List<? extends Annotation> getDeclaredAnnotationsAsList() {
        return List.of(this.getDeclaredAnnotations());
    }

    @Override
    AnnotatedElement resolve() throws ResolveException;

    @Override
    AnnotatedElement getIfResolved();

    static AnnotatedElementMirror of(AnnotatedElement element) {
        if (element instanceof Class) {
            return ClassMirror.of((Class<?>) element);
        }
        if (element instanceof Executable) {
            return MethodMirror.of((Executable) element);
        }
        if (element instanceof Member) {
            return MemberMirror.of((Member) element);
        }
        if (element instanceof Executable) {
            return MethodMirror.of((Executable) element);
        }
        return null; // TODO
    }
}
