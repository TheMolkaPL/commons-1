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

import org.diorite.commons.object.Resettable;
import org.diorite.commons.object.ToStringHelper;

/**
 * Base class for abstract lazy values.
 */
abstract class AbstractLazyValue implements Resettable {
    /**
     * lock object for easier sync
     */
    protected final Object lock = new Object();

    /**
     * Determine if value was already initialized.
     */
    protected volatile boolean isCached;

    /**
     * Returns true if value was already initialized.
     *
     * @return true if value was already initialized.
     */
    public boolean isCached() {
        return this.isCached;
    }

    @Override
    public void reset() {
        if (! this.isCached) {
            return;
        }
        synchronized (this.lock) {
            this.isCached = false;
        }
    }

    @Override
    public String toString() {
        return ToStringHelper.addMember(ToStringHelper.start(this), "isCached", this.isCached).toString();
    }
}
