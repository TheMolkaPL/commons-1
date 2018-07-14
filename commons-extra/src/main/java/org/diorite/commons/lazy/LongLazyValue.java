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

package org.diorite.commons.lazy;

import java.util.Collection;
import java.util.function.LongSupplier;

/**
 * Class to represent lazy init long values that use {@link LongSupplier} passed in constructor to initialize value in {@link #calculateLazyValue()}
 * method. <br>
 * Class is extending {@link LongLazyValueAbstract}
 *
 * @see LongLazyValueAbstract
 */
public class LongLazyValue extends LongLazyValueAbstract {
    /**
     * supplier used by {@link #calculateLazyValue()} method.
     */
    protected final LongSupplier supplier;

    /**
     * Construct new LongLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     */
    LongLazyValue(LongSupplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Construct new LongLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     */
    public LongLazyValue(Collection<? super LongLazyValue> collection, LongSupplier supplier) {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected long calculateLazyValue() {
        return this.supplier.getAsLong();
    }

    /**
     * Construct LazyValue with given supplier for value. If supplier is already a lazy value method will just cast it back to lazy value.
     *
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     *
     * @return LazyValue instance.
     */
    public static LongLazyValue lazy(LongSupplier supplier) {
        if (supplier instanceof LongLazyValue) {
            return (LongLazyValue) supplier;
        }
        return new LongLazyValue(supplier);
    }
}
