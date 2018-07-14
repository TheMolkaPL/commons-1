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

import org.diorite.commons.function.supplier.Supplier;
import org.diorite.commons.object.Resettable;
import org.diorite.commons.object.ToStringHelper;

import javax.annotation.Nullable;

/**
 * Class to represent lazy init object values, lazy value is initialized on first {@link #get()} invoke by {@link #calculateLazyValue()} method. <br>
 * Class also implements {@link Resettable} so cached value can be reset and new value will be created on next {@link #get()} method invoke.
 *
 * @param <T> type of lazy init object.
 */
public abstract class LazyValueAbstract<T> extends AbstractLazyValue implements Supplier<T> {
    /**
     * Used to store cached value.
     */
    @Nullable
    protected T cached;

    /**
     * Construct new LazyValueAbstract object.
     */
    protected LazyValueAbstract() {
    }

    /**
     * Method that will return cached value or initialize new if value isn't cached yet.
     *
     * @return value of this lazy value.
     */
    @Override
    @Nullable
    public T get() {
        if (this.isCached) {
            return this.cached;
        }
        else {
            synchronized (this.lock) {
                if (this.isCached) {
                    return this.cached;
                }
                T result = this.cached = this.calculateLazyValue();
                this.isCached = true;
                return result;
            }
        }
    }

    /**
     * Method that should return new value to cache. <br>
     * Invoked in {@link #get()} method when value isn't cached/initialized yet.
     *
     * @return new value to cache.
     */
    @Nullable
    protected abstract T calculateLazyValue();

    @Override
    public String toString() {
        StringBuilder builder = ToStringHelper.addMember(ToStringHelper.start(this), "cached", this.cached);
        return ToStringHelper.addSuper(builder, super.getClass(), super.toString()).toString();
    }
}
