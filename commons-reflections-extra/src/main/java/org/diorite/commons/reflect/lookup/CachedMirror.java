package org.diorite.commons.reflect.lookup;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

abstract class CachedMirror<T extends Resolvable> implements Resolvable {
    private static final Object NULL = new Object();

    protected final T element;

    CachedMirror(T element) {
        this.element = element;
    }

    @Override
    public boolean isResolved() {
        return this.element.isResolved();
    }

    @Override
    public boolean tryToResolve() {
        return this.element.tryToResolve();
    }

    private final Map<Object, Object> cached = new ConcurrentHashMap<>();

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    protected <R> R cached(String name, Supplier<R> supplier) {
        return (R) normalizeReturn(this.cached.computeIfAbsent(name, x -> normalize(supplier.get())));
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    protected <A1, R> R cached(String name, A1 arg1, Function<? super A1, R> supplier) {
        CacheKey cacheKey = new CacheKey(name, arg1);
        return (R) normalizeReturn(this.cached.computeIfAbsent(cacheKey, x -> normalize(supplier.apply(arg1))));
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    protected <A1, A2, R> R cached(String name, A1 arg1, A2 arg2, BiFunction<? super A1, ? super A2, R> supplier) {
        CacheKey cacheKey = new CacheKey(name, arg1, arg2);
        return (R) normalizeReturn(this.cached.computeIfAbsent(cacheKey, x -> normalize(supplier.apply(arg1, arg2))));
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    protected <A1, A2, A3, R> R cached(String name, A1 arg1, A2 arg2, A3 arg3,
                                       TriFunction<? super A1, ? super A2, ? super A3, R> supplier) {
        CacheKey cacheKey = new CacheKey(name, arg1, arg2, arg3);
        return (R) normalizeReturn(this.cached.computeIfAbsent(cacheKey, x -> normalize(supplier.apply(arg1, arg2, arg3))));
    }

    @FunctionalInterface
    interface TriFunction<A1, A2, A3, R> {
        @Nullable
        R apply(@Nullable A1 arg1, @Nullable A2 arg2, @Nullable A3 arg3);
    }

    static class CacheKey {
        private final String   func;
        @Nullable
        private       Object[] args = null;

        CacheKey(String func) {this.func = func;}

        CacheKey(String func, Object... args) {
            this.func = func;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (this.getClass() != o.getClass())) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(this.func, cacheKey.func) &&
                           Arrays.deepEquals(this.args, cacheKey.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(this.func);
            result = (31 * result) + Arrays.deepHashCode(this.args);
            return result;
        }
    }

    @Nullable
    private static Object normalize(@Nullable Object o) {
        if (o == null) {
            return NULL;
        }
        if (o instanceof List) {
            return List.copyOf((List) o);
        }
        return o;
    }

    @Nullable
    private static Object normalizeReturn(@Nullable Object o) {
        if (o == NULL) {
            return null;
        }
        if (o instanceof Object[]) {
            return ((Object[]) o).clone();
        }
        return o;
    }

    @Override
    public String toString() {
        return this.element.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return this.element.equals(o);
    }

    @Override
    public int hashCode() {
        return this.element.hashCode();
    }
}
