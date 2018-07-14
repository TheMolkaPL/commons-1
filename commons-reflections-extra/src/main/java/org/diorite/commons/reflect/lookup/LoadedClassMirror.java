package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class LoadedClassMirror extends LoadedAnnotatedElementMirror<Class<?>> implements ClassMirror {
    private final Map<Member, MemberMirror> members;
    private final List<FieldMirror>         fields;
    private final List<MethodMirror>        methods;
    private final List<MethodMirror>        executables;
    private final List<MethodMirror>        constructors;

    LoadedClassMirror(Class<?> element) {
        super(element);

        Map<Member, MemberMirror> members = new LinkedHashMap<>();
        List<FieldMirror> fields = new ArrayList<>();
        List<MethodMirror> methods = new ArrayList<>();
        List<MethodMirror> executables = new ArrayList<>();
        List<MethodMirror> constructors = new ArrayList<>();
        for (Constructor<?> member: this.element.getDeclaredConstructors()) {
            MethodMirror mirror = MethodMirror.of(member);
            members.put(member, mirror);
            executables.add(mirror);
            constructors.add(mirror);
        }
        for (Method member: this.element.getDeclaredMethods()) {
            MethodMirror mirror = MethodMirror.of(member);
            members.put(member, mirror);
            methods.add(mirror);
            executables.add(mirror);
        }
        for (Field member: this.element.getDeclaredFields()) {
            FieldMirror mirror = FieldMirror.of(member);
            members.put(member, mirror);
            fields.add(mirror);
        }

        this.members = new LinkedHashMap<>(members);
        this.fields = List.copyOf(fields);
        this.methods = List.copyOf(methods);
        this.executables = List.copyOf(executables);
        this.constructors = List.copyOf(constructors);
    }

    @Override
    public String getSimpleName() {
        return this.element.getSimpleName();
    }

    @Override
    public String getCanonicalName() {
        return this.element.getCanonicalName();
    }

    @Override
    public String getPackageName() {
        return this.element.getPackageName();
    }

    @Nullable
    @Override
    public String getSuperclass() {
        return MirrorUtils.getNameNullable(this.element.getSuperclass());
    }

    @Override
    public List<? extends String> getInterfaces() {
        return MirrorUtils.mirrorTypes(this.element.getInterfaces());
    }

    @Override
    public List<? extends MethodMirror> getDeclaredExecutables() {
        return this.executables;
    }

    @Override
    public List<? extends MemberMirror> getDeclaredMembers() {
        return List.copyOf(this.members.values());
    }

    @Override
    public List<? extends MethodMirror> getDeclaredConstructors() {
        return this.constructors;
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredConstructor(String... argumentTypes) {
        List<String> types = Arrays.asList(argumentTypes);
        for (MethodMirror constructor: this.constructors) {
            if (constructor.getParameterTypes().equals(types)) {
                return constructor;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredConstructor(Class<?>... argumentTypes) {
        try {
            return (MethodMirror) this.members.get(this.element.getDeclaredConstructor(argumentTypes));
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredConstructorBySignature(String descriptor) {
        for (MethodMirror constructor: this.constructors) {
            if (constructor.getDescriptor().equals(descriptor)) {
                return constructor;
            }
        }
        return null;
    }

    @Override
    public List<? extends MethodMirror> getDeclaredMethods() {
        return this.methods;
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredMethodExact(String returnType, String name, String... argumentTypes) {
        List<String> types = Arrays.asList(argumentTypes);
        for (MethodMirror method: this.methods) {
            if (method.getName().equals(name) && method.getReturnType().equals(returnType) && method.getParameterTypes().equals(types)) {
                return method;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredMethodExact(Class<?> returnType, String name, Class<?>... argumentTypes) {
        for (Method method: this.element.getDeclaredMethods()) {
            if (method.getName().equals(name) && (method.getReturnType() == returnType)
                        && Arrays.equals(method.getParameterTypes(), argumentTypes)) {
                return (MethodMirror) this.members.get(method);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredMethodBySignature(String name, String descriptor) {
        for (MethodMirror method: this.methods) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredMethod(String name, String... argumentTypes) {
        List<String> types = Arrays.asList(argumentTypes);
        for (MethodMirror method: this.methods) {
            if (method.getName().equals(name) && method.getParameterTypes().equals(types)) {
                return method;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public MethodMirror getDeclaredMethod(String name, Class<?>... argumentTypes) {
        try {
            return (MethodMirror) this.members.get(this.element.getDeclaredMethod(name, argumentTypes));
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public List<? extends FieldMirror> getDeclaredFields() {
        return this.fields;
    }

    @Nullable
    @Override
    public FieldMirror getDeclaredFieldExact(String returnType, String name) {
        for (FieldMirror field: this.fields) {
            if (field.getName().equals(name) && field.getType().equals(returnType)) {
                return field;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public FieldMirror getDeclaredFieldExact(Class<?> returnType, String name) {
        for (Field field: this.element.getDeclaredFields()) {
            if (field.getName().equals(name) && (field.getType() == returnType)) {
                return (FieldMirror) this.members.get(field);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public FieldMirror getDeclaredField(String name) {
        try {
            return (FieldMirror) this.members.get(this.element.getDeclaredField(name));
        }
        catch (NoSuchFieldException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public FieldMirror getDeclaredFieldBySignature(String name, String descriptor) {
        for (FieldMirror field: this.fields) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }
        return null;
    }

    @Override
    public List<? extends FieldMirror> getEnumFields() {
        List<FieldMirror> enumFields = new ArrayList<>();
        for (FieldMirror field: this.fields) {
            if (field.isEnumConstant()) {
                enumFields.add(field);
            }
        }
        return enumFields;
    }

    @Nullable
    @Override
    public String getEnclosingClass() {
        return MirrorUtils.getNameNullable(this.element.getEnclosingClass());
    }

    @Nullable
    @Override
    public String getDeclaringClass() {
        return MirrorUtils.getNameNullable(this.element.getDeclaringClass());
    }

    @Override
    public List<? extends String> getDeclaredClasses() {
        return MirrorUtils.mirrorTypes(this.element.getDeclaredClasses());
    }

    @Nullable
    @Override
    public TypeMirror getGenericSuperclass() {
        return TypeMirror.of(this.element.getGenericSuperclass());
    }

    @Override
    public List<? extends TypeMirror> getGenericInterfaces() {
        return MirrorUtils.mirrorTypes(this.element.getGenericInterfaces());
    }

    @Nullable
    @Override
    public MethodMirror getEnclosingMethod() {
        return MethodMirror.of(this.element.getEnclosingMethod());
    }

    @Override
    public List<? extends TypeVariableMirror> getTypeParameters() {
        return MirrorUtils.mirror(this.element.getTypeParameters());
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
}
