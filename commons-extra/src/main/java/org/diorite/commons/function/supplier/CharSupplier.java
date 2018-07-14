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

package org.diorite.commons.function.supplier;

import org.diorite.commons.lazy.CharLazyValue;

/**
 * Represents a supplier of {@code char}-valued results.  This is the
 * {@code char}-producing primitive specialization of {@link Supplier}.
 * <br>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * @see Supplier
 */
@FunctionalInterface
public interface CharSupplier extends Supplier<Character> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    char getAsChar();

    @Override
    default Character get() {
        return this.getAsChar();
    }

    /**
     * @return this as lazy value
     */
    default CharLazyValue lazyChar() {
        return CharLazyValue.lazy(this);
    }
}
