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
import org.diorite.commons.tuple.Pair;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

/**
 * A consumer function that can throw an exception. <br>
 * It will catch any exception and sneaky throw it back, or use factory method to handle it.
 *
 * @param <T> the type of the input to the function
 * @param <U> the type of the second input to the function
 */
@FunctionalInterface
public interface ExceptionalBiConsumer<T, U> extends BiConsumer<T, U> {
    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @param u the function argument
     *
     * @throws Throwable if operator will throw it.
     */
    void acceptOrThrow(@Nullable T t, @Nullable U u) throws Throwable;

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @param u the function argument
     */
    @Override
    default void accept(@Nullable T t, @Nullable U u) {
        try {
            this.acceptOrThrow(t, u);
        }
        catch (Throwable e) {
            throw ExceptionUtils.sneakyThrow(e);
        }
    }

    /**
     * Helper method for easier casting.
     *
     * @param function function that might throw error.
     * @param <T> the type of the input to the function
     * @param <U> the type of the input to the function
     *
     * @return exceptional function instance.
     */
    static <T, U> ExceptionalBiConsumer<T, U> of(ExceptionalBiConsumer<T, U> function) {
        return function;
    }

    /**
     * Crates exceptional function with throwable handler.
     *
     * @param function function that might throw error.
     * @param consumer handler of error.
     * @param <T> the type of the input to the function
     * @param <U> the type of the input to the function
     *
     * @return created function.
     */
    static <T, U> ExceptionalBiConsumer<T, U> withHandler(ExceptionalBiConsumer<? super T, ? super U> function,
                                                          BiConsumer<? super Pair<T, U>, ? super Throwable> consumer) {
        return (t, u) ->
        {
            try {
                function.acceptOrThrow(t, u);
            }
            catch (Throwable throwable) {
                consumer.accept(Pair.of(t, u), throwable);
            }
        };
    }
}
