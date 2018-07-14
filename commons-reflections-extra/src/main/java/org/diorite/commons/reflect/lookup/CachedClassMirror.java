package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.util.List;

class CachedClassMirror extends CachedAnnotatedElementMirror<ClassMirror> implements ClassMirror {
    CachedClassMirror(ClassMirror element) {
        super(element);
    }

    @Override
    public String getSimpleName() {
        return this.cached("getSimpleName", this.element::getSimpleName);
    }

    @Override
    public String getCanonicalName() {
        return this.cached("getCanonicalName", this.element::getCanonicalName);
    }

    @Override
    public String getPackageName() {
        return this.cached("getPackageName", this.element::getPackageName);
    }

    @Override
    @Nullable
    public String getSuperclass() {
        return this.cached("getSuperclass", this.element::getSuperclass);
    }

    @Override
    public List<? extends String> getInterfaces() {
        return this.cached("getInterfaces", this.element::getInterfaces);
    }

    @Override
    public List<? extends MethodMirror> getDeclaredExecutables() {
        return this.cached("getDeclaredExecutables", this.element::getDeclaredExecutables);
    }

    @Override
    public List<? extends MemberMirror> getDeclaredMembers() {
        return this.cached("getDeclaredMembers", this.element::getDeclaredMembers);
    }

    @Override
    public List<? extends MethodMirror> getDeclaredConstructors() {
        return this.cached("getDeclaredConstructors", this.element::getDeclaredConstructors);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredConstructor(String... argumentTypes) {
        return this.cached("getDeclaredConstructor", argumentTypes, this.element::getDeclaredConstructor);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredConstructor(Class<?>... argumentTypes) {
        return this.cached("getDeclaredConstructor", argumentTypes, this.element::getDeclaredConstructor);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredConstructorBySignature(String signature) {
        return this.cached("getDeclaredConstructorBySignature", signature, this.element::getDeclaredConstructorBySignature);
    }

    @Override
    public List<? extends MethodMirror> getDeclaredMethods() {
        return this.cached("getDeclaredMethods", this.element::getDeclaredMethods);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredMethodExact(String returnType, String name, String... argumentTypes) {
        return this.cached("getDeclaredMethodExact", returnType, name, argumentTypes, this.element::getDeclaredMethodExact);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredMethodExact(Class<?> returnType, String name, Class<?>... argumentTypes) {
        return this.cached("getDeclaredMethodExact", returnType, name, argumentTypes, this.element::getDeclaredMethodExact);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredMethodBySignature(String name, String descriptor) {
        return this.cached("getDeclaredMethodBySignature", name, descriptor, this.element::getDeclaredMethodBySignature);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredMethod(String name, String... argumentTypes) {
        return this.cached("getDeclaredMethod", name, argumentTypes, this.element::getDeclaredMethod);
    }

    @Override
    @Nullable
    public MethodMirror getDeclaredMethod(String name, Class<?>... argumentTypes) {
        return this.cached("getDeclaredMethod", name, argumentTypes, this.element::getDeclaredMethod);
    }

    @Override
    public List<? extends FieldMirror> getDeclaredFields() {
        return this.cached("getDeclaredFields", this.element::getDeclaredFields);
    }

    @Override
    @Nullable
    public FieldMirror getDeclaredFieldExact(String returnType, String name) {
        return this.cached("getDeclaredFieldExact", returnType, name, this.element::getDeclaredFieldExact);
    }

    @Override
    @Nullable
    public FieldMirror getDeclaredFieldExact(Class<?> returnType, String name) {
        return this.cached("getDeclaredFieldExact", returnType, name, this.element::getDeclaredFieldExact);
    }

    @Override
    @Nullable
    public FieldMirror getDeclaredField(String name) {
        return this.cached("getDeclaredField", name, this.element::getDeclaredField);
    }

    @Override
    @Nullable
    public FieldMirror getDeclaredFieldBySignature(String name, String descriptor) {
        return this.cached("getDeclaredFieldBySignature", name, descriptor, this.element::getDeclaredFieldBySignature);
    }

    @Override
    public List<? extends FieldMirror> getEnumFields() {
        return this.cached("getEnumFields", this.element::getEnumFields);
    }

    @Override
    @Nullable
    public String getEnclosingClass() {
        return this.cached("getEnclosingClass", this.element::getEnclosingClass);
    }

    @Override
    @Nullable
    public String getDeclaringClass() {
        return this.cached("getDeclaringClass", this.element::getDeclaringClass);
    }

    @Override
    public List<? extends String> getDeclaredClasses() {
        return this.cached("getDeclaredClasses", this.element::getDeclaredClasses);
    }

    @Override
    @Nullable
    public TypeMirror getGenericSuperclass() {
        return this.cached("getGenericSuperclass", this.element::getGenericSuperclass);
    }

    @Override
    public List<? extends TypeMirror> getGenericInterfaces() {
        return this.cached("getGenericInterfaces", this.element::getGenericInterfaces);
    }

    @Override
    @Nullable
    public MethodMirror getEnclosingMethod() {
        return this.cached("getEnclosingMethod", this.element::getEnclosingMethod);
    }

    @Override
    public List<? extends TypeVariableMirror> getTypeParameters() {
        return this.cached("getTypeParameters", this.element::getTypeParameters);
    }

    @Override
    public boolean isInterface() {
        return this.element.isInterface();
    }

    @Override
    public boolean isAbstract() {
        return this.element.isAbstract();
    }

    @Override
    public boolean isEnum() {
        return this.element.isEnum();
    }

    @Override
    public boolean isSynthetic() {
        return this.element.isSynthetic();
    }

    @Override
    public boolean isAnnotation() {
        return this.element.isAnnotation();
    }

    @Override
    public boolean isArray() {
        return this.element.isArray();
    }

    @Override
    public boolean isPrimitive() {
        return this.element.isPrimitive();
    }

    @Override
    public boolean isAnonymousClass() {
        return this.element.isAnonymousClass();
    }

    @Override
    public boolean isLocalClass() {
        return this.element.isLocalClass();
    }

    @Override
    public boolean isMemberClass() {
        return this.element.isMemberClass();
    }

    @Override
    public int getModifiers() {
        return this.element.getModifiers();
    }

    @Override
    public boolean checkModifier(int modifier) {
        return this.element.checkModifier(modifier);
    }

    @Override
    public boolean isPublic() {
        return this.element.isPublic();
    }

    @Override
    public boolean isPrivate() {
        return this.element.isPrivate();
    }

    @Override
    public boolean isProtected() {
        return this.element.isProtected();
    }

    @Override
    public boolean isDefaultAccess() {
        return this.element.isDefaultAccess();
    }

    @Override
    public boolean isFinal() {
        return this.element.isFinal();
    }

    @Override
    public Class<?> resolve() throws ResolveException {
        return this.element.resolve();
    }

    @Override
    public Class<?> getIfResolved() {
        return this.element.getIfResolved();
    }
}
