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
 * Represents an operation that accepts a single {@code double}-valued argument and
 * returns boolean as result. This is the primitive type specialization of
 * {@link Predicate} for {@code double}.
 *
 * @see Predicate
 */
@FunctionalInterface
public interface DoublePredicate extends Predicate<Double> {
    /**
     * Performs this test on the given argument.
     *
     * @param value the input argument
     *
     * @return true if given argument passed test.
     */
    boolean test(double value);

    @Override
    default boolean test(Double value) {
        return this.test(value.doubleValue());
    }

    @Override
    default DoublePredicate and(java.util.function.Predicate<? super Double> other) {
        Objects.requireNonNull(other);
        if (other instanceof DoublePredicate) {
            return this.and(((DoublePredicate) other));
        }
        return (t) -> this.test(t) && other.test(t);
    }

    default DoublePredicate and(DoublePredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) && other.test(t);
    }

    default DoublePredicate and(java.util.function.DoublePredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) && other.test(t);
    }

    @Override
    default DoublePredicate negate() {
        return (t) -> ! this.test(t);
    }

    @Override
    default DoublePredicate or(java.util.function.Predicate<? super Double> other) {
        Objects.requireNonNull(other);
        if (other instanceof DoublePredicate) {
            return this.or(((DoublePredicate) other));
        }
        return (t) -> this.test(t) || other.test(t);
    }

    default DoublePredicate or(DoublePredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) || other.test(t);
    }

    default DoublePredicate or(java.util.function.DoublePredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) || other.test(t);
    }

    static DoublePredicate fromJava(java.util.function.DoublePredicate predicate) {
        return predicate::test;
    }

    static DoublePredicate negated(DoublePredicate predicate) {
        return predicate.negate();
    }

    /**
     * Returns a predicate that tests if two arguments are equal.
     *
     * @param value value with which to compare for equality;
     *
     * @return a predicate that tests if two arguments are equal
     */
    static CharPredicate isEqual(char value) {
        return v -> v == value;
    }
}