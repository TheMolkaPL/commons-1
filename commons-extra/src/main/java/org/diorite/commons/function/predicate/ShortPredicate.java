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

import java.util.Objects;

/**
 * Represents an operation that accepts a single {@code short}-valued argument and
 * returns boolean as result. This is the primitive type specialization of
 * {@link java.util.function.Predicate} for {@code short}.
 *
 * @see java.util.function.Predicate
 */
@FunctionalInterface
public interface ShortPredicate extends Predicate<Short> {
    /**
     * Performs this test on the given argument.
     *
     * @param value the input argument
     *
     * @return true if given argument passed test.
     */
    boolean test(short value);

    @Override
    default boolean test(Short value) {
        return this.test(value.shortValue());
    }

    @Override
    default ShortPredicate and(java.util.function.Predicate<? super Short> other) {
        Objects.requireNonNull(other);
        if (other instanceof ShortPredicate) {
            return this.and(((ShortPredicate) other));
        }
        return (t) -> this.test(t) && other.test(t);
    }

    default ShortPredicate and(ShortPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) && other.test(t);
    }

    @Override
    default ShortPredicate negate() {
        return (t) -> ! this.test(t);
    }

    @Override
    default ShortPredicate or(java.util.function.Predicate<? super Short> other) {
        Objects.requireNonNull(other);
        if (other instanceof ShortPredicate) {
            return this.or(((ShortPredicate) other));
        }
        return (t) -> this.test(t) || other.test(t);
    }

    default ShortPredicate or(ShortPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) || other.test(t);
    }

    static ShortPredicate negated(ShortPredicate predicate) {
        return predicate.negate();
    }

    /**
     * Returns a predicate that tests if two arguments are equal.
     *
     * @param value value with which to compare for equality;
     *
     * @return a predicate that tests if two arguments are equal
     */
    static ShortPredicate isEqual(short value) {
        return v -> v == value;
    }
}