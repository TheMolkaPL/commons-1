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
 * Represents an operation that accepts a single {@code int}-valued argument and
 * returns boolean as result. This is the primitive type specialization of
 * {@link Predicate} for {@code int}.
 *
 * @see Predicate
 */
@FunctionalInterface
public interface IntPredicate extends Predicate<Integer> {
    /**
     * Performs this test on the given argument.
     *
     * @param value the input argument
     *
     * @return true if given argument passed test.
     */
    boolean test(int value);

    @Override
    default boolean test(Integer value) {
        return this.test(value.intValue());
    }

    @Override
    default IntPredicate and(java.util.function.Predicate<? super Integer> other) {
        Objects.requireNonNull(other);
        if (other instanceof IntPredicate) {
            return this.and(((IntPredicate) other));
        }
        return (t) -> this.test(t) && other.test(t);
    }

    default IntPredicate and(IntPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) && other.test(t);
    }

    default IntPredicate and(java.util.function.IntPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) && other.test(t);
    }

    @Override
    default IntPredicate negate() {
        return (t) -> ! this.test(t);
    }

    @Override
    default IntPredicate or(java.util.function.Predicate<? super Integer> other) {
        Objects.requireNonNull(other);
        if (other instanceof IntPredicate) {
            return this.or(((IntPredicate) other));
        }
        return (t) -> this.test(t) || other.test(t);
    }

    default IntPredicate or(IntPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) || other.test(t);
    }

    default IntPredicate or(java.util.function.IntPredicate other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) || other.test(t);
    }

    static IntPredicate fromJava(java.util.function.IntPredicate predicate) {
        return predicate::test;
    }

    static IntPredicate negated(IntPredicate predicate) {
        return predicate.negate();
    }

    /**
     * Returns a predicate that tests if two arguments are equal.
     *
     * @param value value with which to compare for equality;
     *
     * @return a predicate that tests if two arguments are equal
     */
    static IntPredicate isEqual(int value) {
        return v -> v == value;
    }
}