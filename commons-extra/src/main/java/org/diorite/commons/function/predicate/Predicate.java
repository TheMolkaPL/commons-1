/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.commons.function.predicate;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Represents an operation that accepts a single input argument and returns boolean as a result.
 *
 * This is a functional interface
 * whose functional method is {@link #test(Object)}.
 *
 * @param <T> the type of the input to test.
 */
@FunctionalInterface
public interface Predicate<T> extends java.util.function.Predicate<T> {

    @Override
    default Predicate<T> and(java.util.function.Predicate<? super T> other) {
        Objects.requireNonNull(other);
        if (other instanceof Predicate) {
            //noinspection unchecked
            return this.and(((Predicate<? super T>) other));
        }
        return (t) -> this.test(t) && other.test(t);
    }

    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) && other.test(t);
    }

    @Override
    default Predicate<T> negate() {
        return (t) -> ! this.test(t);
    }

    @Override
    default Predicate<T> or(java.util.function.Predicate<? super T> other) {
        Objects.requireNonNull(other);
        if (other instanceof Predicate) {
            //noinspection unchecked
            return this.or(((Predicate<? super T>) other));
        }
        return (t) -> this.test(t) || other.test(t);
    }

    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) || other.test(t);
    }

    static <T> Predicate<T> fromJava(java.util.function.Predicate<T> predicate) {
        return predicate::test;
    }

    static <T> Predicate<T> negated(Predicate<T> predicate) {
        return predicate.negate();
    }

    /**
     * Returns a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}.
     *
     * @param <T> the type of arguments to the predicate
     * @param targetRef the object reference with which to compare for equality,
     *         which may be {@code null}
     *
     * @return a predicate that tests if two arguments are equal according
     *         to {@link Objects#equals(Object, Object)}
     */
    static <T> Predicate<T> isEqual(@Nullable Object targetRef) {
        return (targetRef == null) ? Objects::isNull : targetRef::equals;
    }
}
