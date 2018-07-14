package org.diorite.commons.reflect;

import org.diorite.commons.function.predicate.IntPredicate;
import org.diorite.commons.reflect.type.TypeMatcher;
import org.diorite.commons.reflect.type.TypeToken;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class FieldLookup<T, R> extends MemberLookup<T, R, FieldLookup<T, R>> {
    static final int SYNTHETIC = 0x00001000;
    static final int ENUM      = 0x00004000;

    protected IntPredicate isVolatile  = modifier -> true;
    protected IntPredicate isTransient = modifier -> true;
    protected IntPredicate isSynthetic = modifier -> true;
    protected IntPredicate isEnum      = modifier -> true;

    FieldLookup(Class<T> inClass) {
        super(inClass);
    }

    public <RR> FieldLookup<T, RR> typeExact(TypeToken<RR> returnType) {
        return this.type(TypeMatcher.is(returnType));
    }

    public <RR> FieldLookup<T, RR> type(Class<RR> returnType) {
        return this.type(TypeMatcher.isClass(returnType));
    }

    public <RR> FieldLookup<T, RR> typeAssignableTo(Class<RR> returnType) {
        return this.type(TypeMatcher.assignable(TypeToken.get(returnType)));
    }

    public <RR> FieldLookup<T, RR> typeAssignableTo(TypeToken<RR> returnType) {
        return this.type(TypeMatcher.assignable(returnType));
    }

    @SuppressWarnings("unchecked")
    public <RR> FieldLookup<T, RR> type(TypeMatcher<RR> typeMatcher) {
        this.resultType = typeMatcher;
        return (FieldLookup<T, RR>) this;
    }

    public FieldLookup<T, R> isVolatile() {
        this.isVolatile = Modifier::isVolatile;
        return this.lookup();
    }

    public FieldLookup<T, R> notVolatile() {
        this.isVolatile = IntPredicate.negated(Modifier::isVolatile);
        return this.lookup();
    }

    public FieldLookup<T, R> isTransient() {
        this.isTransient = Modifier::isTransient;
        return this.lookup();
    }

    public FieldLookup<T, R> notTransient() {
        this.isTransient = IntPredicate.negated(Modifier::isTransient);
        return this.lookup();
    }

    public FieldLookup<T, R> isEnum() {
        this.isEnum = FieldLookup::isEnum;
        return this.lookup();
    }

    public FieldLookup<T, R> notEnum() {
        this.isEnum = IntPredicate.negated(FieldLookup::isEnum);
        return this.lookup();
    }

    public FieldLookup<T, R> isSynthetic() {
        this.isSynthetic = FieldLookup::isTransient;
        return this.lookup();
    }

    public FieldLookup<T, R> notSynthetic() {
        this.isTransient = IntPredicate.negated(FieldLookup::isTransient);
        return this.lookup();
    }

    public Optional<ReflectedProperty<R>> tryFindExact() {
        return this.find0(true);
    }

    public Optional<ReflectedProperty<R>> tryFindAny() {
        return this.find0(false);
    }

    public ReflectedProperty<R> findExact() {
        return this.find0(true).orElseThrow(() -> new IllegalStateException("Can't find method matching this lookup"));
    }

    public ReflectedProperty<R> findAny() {
        return this.find0(false).orElseThrow(() -> new IllegalStateException("Can't find method matching this lookup"));
    }

    private Optional<ReflectedProperty<R>> find0(boolean exact) {
        List<? extends ReflectedProperty<R>> all = this.findAll0();
        if (all.isEmpty()) {
            return Optional.empty();
        }
        if (exact && (all.size() > 1)) {
            throw new IllegalStateException("Matched more than one method: " + all);
        }
        ReflectedProperty<R> property = all.get(0);
        if (this.ensureAccessible) {
            property.ensureAccessible();
        }
        return Optional.of(property);
    }

    public List<? extends ReflectedProperty<R>> findAll() {
        List<? extends ReflectedProperty<R>> methods = this.findAll0();
        if (this.ensureAccessible) {
            methods.forEach(ReflectedProperty::ensureAccessible);
        }
        return methods;
    }

    public List<? extends ReflectedProperty<R>> findAll0() {
        List<ReflectedProperty<R>> results = new ArrayList<>();

        return results;
    }

    private static boolean isTransient(int modifiers) {
        return (modifiers & SYNTHETIC) != 0;
    }

    private static boolean isEnum(int modifiers) {
        return (modifiers & ENUM) != 0;
    }
}
