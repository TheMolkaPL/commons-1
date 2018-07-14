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
 * Represents an operation that accepts a single {@code float}-valued argument and
 * returns boolean as result. This is the primitive type specialization of
 * {@link java.util.function.Predicate} for {@code float}.
 *
 * @see java.util.function.Predicate
 */
@FunctionalInterface
public interface FloatPredicate extends Predicate<Float> {
    /**
     * Performs this test on the given argument.
     *
     * @param value the input argument
     *
     * @return true if given argument passed test.
     */
    boolean test(float value);

    @Override
    default boolean test(Float value) {
        return this.test(value.floatValue());
    }

    @Override
    default FloatPredicate and(java.util.function.Predicate<? super Float> other) {
        Objects.requireNonNull(other);
        if (other instanceof FloatPredicate) {
            return this.and(((FloatPredicate) other));
        }
        return (t) -> this.test(t) && other.test(t);
    }

    default FloatPredicate and(FloatPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) && other.test(t);
    }

    @Override
    default FloatPredicate negate() {
        return (t) -> ! this.test(t);
    }

    @Override
    default FloatPredicate or(java.util.function.Predicate<? super Float> other) {
        Objects.requireNonNull(other);
        if (other instanceof FloatPredicate) {
            return this.or(((FloatPredicate) other));
        }
        return (t) -> this.test(t) || other.test(t);
    }

    default FloatPredicate or(FloatPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) || other.test(t);
    }

    static FloatPredicate negated(FloatPredicate predicate) {
        return predicate.negate();
    }

    /**
     * Returns a predicate that tests if two arguments are equal.
     *
     * @param value value with which to compare for equality;
     *
     * @return a predicate that tests if two arguments are equal
     */
    static FloatPredicate isEqual(float value) {
        return v -> v == value;
    }
}