package org.diorite.commons.reflect;

import org.diorite.commons.function.predicate.IntPredicate;
import org.diorite.commons.function.predicate.Predicate;
import org.diorite.commons.reflect.type.TypeMatcher;
import org.diorite.commons.reflect.type.TypeToken;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MethodLookup<T, R> extends MemberLookup<T, R, MethodLookup<T, R>> {
    // big enough so array almost never needs to be resized
    private static final int PARAMETERS_ARRAY_DEFAULT_SIZE = 10;

    static final int BRIDGE  = 0x00000040;
    static final int VARARGS = 0x00000080;

    private   boolean              constructor;
    protected int                  parametersCount    = - 1;
    protected int                  parametersCountMin = - 1;
    protected int                  parametersCountMax = - 1;
    protected TypeMatcher<?>[]     parameters         = new TypeMatcher[PARAMETERS_ARRAY_DEFAULT_SIZE];
    protected Type[]               argumentTypes      = new Type[PARAMETERS_ARRAY_DEFAULT_SIZE]; // special case for simple lookups,
    // additional accuracy for .findBest
    protected int                  nextParameterIndex = 0;
    protected List<TypeMatcher<?>> parametersAny      = new ArrayList<>();
    protected IntPredicate         isSynchronized     = modifier -> true;
    protected IntPredicate         isStrictFp         = modifier -> true;
    protected IntPredicate         isBridge           = modifier -> true;
    protected IntPredicate         isVarargs          = modifier -> true;

    MethodLookup(Class<T> inClass) {
        super(inClass);
    }

    @SuppressWarnings("unchecked")
    MethodLookup<T, T> constructors() {
        this.constructor = true;
        this.nameMatcher = Predicate.isEqual("<init>");
        this.resultType = TypeMatcher.is(this.inClass);
        this.memberTypeMatcher = TypeMatcher.assignable(Constructor.class);
        return (MethodLookup<T, T>) this;
    }

    @SuppressWarnings("unchecked")
    MethodLookup<T, Object> methods() {
        this.nameMatcher = name -> true;
        this.resultType = TypeMatcher.is(this.inClass);
        this.memberTypeMatcher = TypeMatcher.assignable(Method.class);
        return (MethodLookup<T, Object>) this;
    }

    @Override
    public MethodLookup<T, R> name(Predicate<String> namePredicate) {
        if (this.constructor) {
            throw new IllegalStateException("Can't change lookup name of constructor");
        }
        return super.name(namePredicate);
    }

    public <RR> MethodLookup<T, RR> returnTypeExact(TypeToken<RR> returnType) {
        return this.returnType(TypeMatcher.is(returnType));
    }

    public <RR> MethodLookup<T, RR> returnType(Class<RR> returnType) {
        return this.returnType(TypeMatcher.isClass(returnType));
    }

    public <RR> MethodLookup<T, RR> returnTypeAssignableTo(Class<RR> returnType) {
        return this.returnType(TypeMatcher.assignable(TypeToken.get(returnType)));
    }

    public <RR> MethodLookup<T, RR> returnTypeAssignableTo(TypeToken<RR> returnType) {
        return this.returnType(TypeMatcher.assignable(returnType));
    }

    @SuppressWarnings("unchecked")
    public <RR> MethodLookup<T, RR> returnType(TypeMatcher<RR> typeMatcher) {
        this.resultType = typeMatcher;
        return (MethodLookup<T, RR>) this;
    }

    public MethodLookup<T, R> parametersCount(int parametersCount) {
        this.parametersCount = parametersCount;
        this.parametersCountMin = parametersCount;
        this.parametersCountMax = parametersCount;
        return this.lookup();
    }

    public MethodLookup<T, R> parametersCountMin(int parametersCount) {
        this.parametersCountMin = parametersCount;
        return this.lookup();
    }

    public MethodLookup<T, R> parametersCountMax(int parametersCount) {
        this.parametersCountMax = parametersCount;
        return this.lookup();
    }

    public final MethodLookup<T, R> withParametersAccepting(Object... arguments) {
        for (Object argument: arguments) {
            TypeMatcher<?> matcher;
            Class<?> argumentType;
            if (argument == null) {
                argumentType = null;
                matcher = type -> ! TypeToken.get(type).getRawType().isPrimitive();
            }
            else {
                argumentType = argument.getClass();
                matcher = type -> ReflectionUtils.getWrapperClass(TypeToken.get(type).getRawType()).isAssignableFrom(argumentType);
            }
            this.withParameter0(matcher, argumentType);
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withParametersAccepting(Type... types) {
        for (Type type: types) {
            this.withParameter0(TypeMatcher.accepting(type), type);
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withParameter(TypeMatcher<?> parameterType) {
        return this.withParameterAt(this.nextParameterIndex++, parameterType);
    }

    private MethodLookup<T, R> withParameter0(TypeMatcher<?> parameterType, @Nullable Type type0) {
        return this.withParameterAt0(this.nextParameterIndex++, parameterType, type0);
    }

    public final MethodLookup<T, R> withParameters(TypeMatcher<?>... parameterTypes) {
        for (TypeMatcher<?> parameterType: parameterTypes) {
            this.withParameter0(parameterType, null);
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withParameters(Class<?>... classes) {
        for (Class<?> clazz: classes) {
            this.withParameter0(TypeMatcher.isClass(clazz), clazz);
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withAssignableParameters(Type... types) {
        for (Type type: types) {
            this.withParameter0(TypeMatcher.assignable(type), type);
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withExactParameters(Type... types) {
        for (Type type: types) {
            this.withParameter0(TypeMatcher.is(type), type);
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withParameterAt(int index, Class<?> clazz) {
        return this.withParameterAt0(index, TypeMatcher.isClass(clazz), clazz);
    }

    public final MethodLookup<T, R> withAssignableParameterAt(int index, Type type) {
        return this.withParameterAt0(index, TypeMatcher.assignable(type), type);
    }

    public final MethodLookup<T, R> withParameterExactAt(int index, Type type) {
        return this.withParameterAt0(index, TypeMatcher.is(type), type);
    }

    public MethodLookup<T, R> withParameterAt(int index, TypeMatcher<?> parameterType) {
        return this.withParameterAt0(index, parameterType, null);
    }

    private MethodLookup<T, R> withParameterAt0(int index, TypeMatcher<?> parameterType, @Nullable Type type0) {
        Objects.requireNonNull(parameterType);
        if (this.parameters.length <= index) {
            this.parameters = Arrays.copyOf(this.parameters, index + 1);
            this.argumentTypes = Arrays.copyOf(this.argumentTypes, index + 1);
        }
        if (index >= this.parametersCountMin) {
            this.parametersCountMin = index + 1;
        }
        this.parameters[index] = parameterType;
        this.argumentTypes[index] = type0;
        return this.lookup();
    }

    public final MethodLookup<T, R> withParametersAnywhere(Class<?>... classes) {
        for (Class<?> clazz: classes) {
            this.withParameterAnywhere(TypeMatcher.isClass(clazz));
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withExactParametersAnywhere(Type... types) {
        for (Type type: types) {
            this.withParameterAnywhere(TypeMatcher.is(type));
        }
        return this.lookup();
    }

    public final MethodLookup<T, R> withAssignableParameterAnywhere(Type... types) {
        for (Type type: types) {
            this.withParameterAnywhere(TypeMatcher.assignable(type));
        }
        return this.lookup();
    }

    public MethodLookup<T, R> withParametersAnywhere(TypeMatcher<?>... parameterTypes) {
        this.parametersAny.addAll(Arrays.asList(parameterTypes));
        return this.lookup();
    }

    public MethodLookup<T, R> withParameterAnywhere(TypeMatcher<?> parameterType) {
        this.parametersAny.add(parameterType);
        return this.lookup();
    }

    private int refreshMinParametersCount() {
        int min = 0;
        for (TypeMatcher<?> parameter: this.parameters) {
            if (parameter != null) {
                min++;
            }
        }
        min += this.parametersAny.size();
        if (min > this.parametersCountMin) {
            this.parametersCountMin = min;
        }
        return min;
    }

    public MethodLookup<T, R> isSynchronized() {
        this.isSynchronized = Modifier::isSynchronized;
        return this.lookup();
    }

    public MethodLookup<T, R> notSynchronized() {
        this.isSynchronized = IntPredicate.negated(Modifier::isSynchronized);
        return this.lookup();
    }

    public MethodLookup<T, R> isStrictFp() {
        this.isStrictFp = Modifier::isStrict;
        return this.lookup();
    }

    public MethodLookup<T, R> notStrictFp() {
        this.isStrictFp = IntPredicate.negated(Modifier::isStrict);
        return this.lookup();
    }

    public MethodLookup<T, R> isVarargs() {
        this.isVarargs = MethodLookup::isVarargs;
        return this.lookup();
    }

    public MethodLookup<T, R> notVarargs() {
        this.isVarargs = IntPredicate.negated(MethodLookup::isVarargs);
        return this.lookup();
    }

    public MethodLookup<T, R> isBridge() {
        this.isBridge = MethodLookup::isBridge;
        return this.lookup();
    }

    public MethodLookup<T, R> notBridge() {
        this.isBridge = IntPredicate.negated(MethodLookup::isBridge);
        return this.lookup();
    }

    public Optional<ReflectedMethod<R>> tryFindExact() {
        return this.find0(true, true);
    }

    public Optional<ReflectedMethod<R>> tryFindBest() {
        return this.find0(false, true);
    }

    public Optional<ReflectedMethod<R>> tryFindAny() {
        return this.find0(false, false);
    }

    public ReflectedMethod<R> findExact() {
        return this.find0(true, true).orElseThrow(() -> new IllegalStateException("Can't find method matching this lookup"));
    }

    public ReflectedMethod<R> findBest() {
        return this.find0(false, true).orElseThrow(() -> new IllegalStateException("Can't find method matching this lookup"));
    }

    public ReflectedMethod<R> findAny() {
        return this.find0(false, false).orElseThrow(() -> new IllegalStateException("Can't find method matching this lookup"));
    }

    private Optional<ReflectedMethod<R>> find0(boolean exact, boolean best) {
        List<? extends ReflectedMethod<R>> all = this.findAll0();
        if (all.isEmpty()) {
            return Optional.empty();
        }
        if (exact && (all.size() > 1)) {
            throw new IllegalStateException("Matched more than one method: " + all);
        }
        if (! best) {
            ReflectedMethod<R> method = all.get(0);
            if (this.ensureAccessible) {
                method.ensureAccessible();
            }
            return Optional.of(method);
        }
        return Optional.of(RuntimeExecutableDispatcherUtils.findBest(all, this.argumentTypes));
    }

    public List<? extends ReflectedMethod<R>> findAll() {
        List<? extends ReflectedMethod<R>> methods = this.findAll0();
        if (this.ensureAccessible) {
            methods.forEach(ReflectedMethod::ensureAccessible);
        }
        return methods;
    }

    public List<? extends ReflectedMethod<R>> findAll0() {
        this.validate();
        List<ReflectedMethod<R>> results = new ArrayList<>();

        return results;
    }

    private void validate() {
        int realMin = this.refreshMinParametersCount();
        int countMax = this.parametersCountMax;
        int countMin = this.parametersCountMin;
        int count = this.parametersCount;
        if ((countMax != - 1) && (countMax < countMin)) {
            throw new IllegalStateException("parametersCountMax (" + countMax + ") < parametersCountMin (" + countMin + ")");
        }
        if ((count != - 1) && (realMin > count)) {
            throw new IllegalStateException(
                    "Can't expect (" + count + ") less parameters than amount of provided (" + realMin + ") parameters!");
        }

        Class<T> inClass = this.inClass;
        boolean constructor = this.constructor;
        if (constructor && ! this.resultType.test(inClass)) {
            throw new IllegalStateException("Can't find constructor with different return type than lookup class (" + inClass + ")");
        }
    }

    private static boolean isBridge(int modifiers) {
        return (modifiers & BRIDGE) != 0;
    }

    private static boolean isVarargs(int modifiers) {
        return (modifiers & VARARGS) != 0;
    }
}
