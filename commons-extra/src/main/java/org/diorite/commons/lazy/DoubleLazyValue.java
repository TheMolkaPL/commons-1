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
import java.util.function.DoubleSupplier;

/**
 * Class to represent lazy init double values that use {@link DoubleSupplier} passed in constructor to initialize value in
 * {@link #calculateLazyValue()} method. <br>
 * Class is extending {@link DoubleLazyValueAbstract}
 *
 * @see DoubleLazyValueAbstract
 */
public class DoubleLazyValue extends DoubleLazyValueAbstract {
    /**
     * supplier used by {@link #calculateLazyValue()} method.
     */
    protected final DoubleSupplier supplier;

    /**
     * Construct new DoubleLazyValue with given supplier for value.
     *
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     */
    DoubleLazyValue(DoubleSupplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Construct new DoubleLazyValue with given supplier for value.
     *
     * @param collection created instance will be added to this list.
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     */
    public DoubleLazyValue(Collection<? super DoubleLazyValue> collection, DoubleSupplier supplier) {
        this.supplier = supplier;
        collection.add(this);
    }

    @Override
    protected double calculateLazyValue() {
        return this.supplier.getAsDouble();
    }

    /**
     * Construct LazyValue with given supplier for value. If supplier is already a lazy value method will just cast it back to lazy value.
     *
     * @param supplier supplier used to initialize value in {@link #calculateLazyValue()} method.
     *
     * @return LazyValue instance.
     */
    public static DoubleLazyValue lazy(DoubleSupplier supplier) {
        if (supplier instanceof DoubleLazyValue) {
            return (DoubleLazyValue) supplier;
        }
        return new DoubleLazyValue(supplier);
    }
}
