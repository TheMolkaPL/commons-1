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

package org.diorite.commons.function.function;

import org.diorite.commons.ExceptionUtils;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A supplier function that can throw an exception. <br>
 * It will catch any exception and sneaky throw it back, or use factory method to handle it.
 *
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface ExceptionalSupplier<R> extends Supplier<R>, Callable<R> {
    /**
     * Applies this function to the given argument.
     *
     * @return the function result
     *
     * @throws Throwable if operator will throw it.
     */
    @Nullable
    R getOrThrow() throws Throwable;

    /**
     * Applies this function to the given argument.
     *
     * @return the function result
     */
    @Nullable
    @Override
    default R get() {
        try {
            return this.getOrThrow();
        }
        catch (Throwable e) {
            throw ExceptionUtils.sneakyThrow(e);
        }
    }

    @Override
    @Nullable
    default R call() throws Exception {
        return this.get();
    }

    /**
     * Helper method for easier casting.
     *
     * @param function function that might throw error.
     * @param <R> the type of the result of the function
     *
     * @return exceptional function instance.
     */
    static <R> ExceptionalSupplier<R> of(ExceptionalSupplier<R> function) {
        return function;
    }

    /**
     * Crates exceptional function with throwable handler.
     *
     * @param function function that might throw error.
     * @param consumer handler of error.
     * @param <R> the type of the result of the function
     *
     * @return created function.
     */
    static <R> ExceptionalSupplier<R> withHandler(ExceptionalSupplier<? extends R> function,
                                                  Function<? super Throwable, ? extends R> consumer) {
        return () ->
        {
            try {
                return function.getOrThrow();
            }
            catch (Throwable throwable) {
                return consumer.apply(throwable);
            }
        };
    }
}
