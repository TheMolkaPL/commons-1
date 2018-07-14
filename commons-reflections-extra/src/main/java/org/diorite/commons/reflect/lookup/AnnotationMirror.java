package org.diorite.commons.reflect.lookup;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Represents annotation data, might represent normal loaded annotation value or annotation read from bytecode with unloaded types
 */
public interface AnnotationMirror extends Resolvable {
    /**
     * @return type name of this annotation.
     */
    String annotationType();

    /**
     * @return set of all properties names.
     */
    List<? extends String> getProperties();

    /**
     * Returns type of property with given name, if property is also of annotation type raw {@link Annotation} class will be returned.
     *
     * @param property name of property.
     *
     * @return type of property with given name.
     */
    Class<?> getTypeOf(String property);

    /**
     * Returns name of type of given property, if property is also of annotation type - name of that annotation class will be returned
     *
     * @param property name of property.
     *
     * @return name of type of given property.
     */
    String getTypeNameOf(String property);

    /**
     * Returns value of property with given name, if property is also of annotation type - another instance of {@link AnnotationMirror}
     * will be returned.
     *
     * @param property name of property.
     *
     * @return value of property with given name.
     */
    Object getValue(String property);

    /**
     * Returns value of property with given name as given type, if type does not match {@link ClassCastException} will be thrown. <br>
     * If property is of annotation type and: <br>
     * <ul>
     * <li>{@code AnnotationMirror.class} type is provided then valid instance of {@link AnnotationMirror} will be returned</li>
     * <li>{@code Annotation.class} type is provided then method will try to load and return valid annotation instance, but might throw
     * {@link ClassNotFoundException} exception.</li>
     * <li>Loaded annotation type class is provided then new instance of that annotation will be returned</li>
     * </ul>
     *
     * @param property name of property.
     *
     * @return value of property with given name.
     */
    <T> T getValue(String property, Class<T> asType);

    /**
     * Get instance of loaded annotation mirror for given annotation.
     *
     * @param annotation annotation to warp.
     *
     * @return instance of loaded annotation mirror for given annotation.
     */
    static AnnotationMirror of(Annotation annotation) {
        return MirrorUtils.cached(annotation);
    }

    @Override
    Annotation resolve() throws ResolveException;

    @Override
    Annotation getIfResolved();
}
