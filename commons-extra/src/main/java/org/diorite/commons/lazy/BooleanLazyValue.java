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
import java.util.function.BooleanSupplier;

/**
 * Class to represent lazy init boolean values that use {@link BooleanSupplier} passed in constructor to initialize value in
 * {@link #calculateLazyValue()} method. <br>
 * Class is extending {@link BooleanLazyValueAbstract}
 *
 * @see BooleanLazyValueAbstract
 */
public class BooleanLazyValue extends BooleanLazyValueAbstract {
    /**
     * supplier used by {@link #calculateLazyValue()} method.
     */
    protected final BooleanSupplier supplier;

    /**
     * Construct new BooleanLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     */
    BooleanLazyValue(BooleanSupplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Construct new BooleanLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     */
    public BooleanLazyValue(Collection<? super BooleanLazyValue> collection, BooleanSupplier supplier) {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected boolean calculateLazyValue() {
        return this.supplier.getAsBoolean();
    }

    /**
     * Construct LazyValue with given supplier for value. If supplier is already a lazy value method will just cast it back to lazy value.
     *
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     *
     * @return LazyValue instance.
     */
    public static BooleanLazyValue lazy(BooleanSupplier supplier) {
        if (supplier instanceof BooleanLazyValue) {
            return (BooleanLazyValue) supplier;
        }
        return new BooleanLazyValue(supplier);
    }
}
