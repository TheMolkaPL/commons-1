package org.diorite.commons.reflect.lookup;

import java.lang.annotation.Annotation;
import java.util.List;

class CachedAnnotationMirror extends CachedMirror<AnnotationMirror> implements AnnotationMirror {
    CachedAnnotationMirror(AnnotationMirror element) {
        super(element);
    }

    @Override
    public String annotationType() {
        return this.cached("annotationType", this.element::annotationType);
    }

    @Override
    public List<? extends String> getProperties() {
        return this.cached("getProperties", this.element::getProperties);
    }

    @Override
    public Class<?> getTypeOf(String property) {
        return this.cached("getTypeOf", property, this.element::getTypeOf);
    }

    @Override
    public String getTypeNameOf(String property) {
        return this.cached("getTypeNameOf", property, this.element::getTypeNameOf);
    }

    @Override
    public Object getValue(String property) {
        return this.cached("getValue", property, this.element::getValue);
    }

    @Override
    public <T> T getValue(String property, Class<T> asType) {
        return this.cached("getValue", property, asType, this.element::getValue);
    }

    @Override
    public Annotation resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public Annotation getIfResolved() {
        return this.element.getIfResolved();
    }
}
