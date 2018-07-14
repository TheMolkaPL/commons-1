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

package org.diorite.commons.math.range;

import org.diorite.commons.math.MathUtils;
import org.diorite.commons.math.random.RandomUtils;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * Class defining range in shorts, may be used to validate numbers.
 */
public final class ShortRange implements IntegerNumericRange<Short> {
    /**
     * Range from {@link Short#MIN_VALUE} to {@link Short#MAX_VALUE}
     */
    public static final ShortRange FULL = of(Short.MIN_VALUE, Short.MAX_VALUE);

    private final short min;
    private final short max;

    private ShortRange(short min, short max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @return min value in range.
     */
    public short getMin() {
        return this.min;
    }

    /**
     * @return max value in range.
     */
    public short getMax() {
        return this.max;
    }

    /**
     * @return random value in range.
     */
    public short getRandom() {
        return this.getRandom(ThreadLocalRandom.current());
    }

    /**
     * Returns random value in range.
     *
     * @param random random instance to use.
     *
     * @return random value in range.
     */
    public short getRandom(Random random) {
        return ((this.max - this.min) == 0) ? this.max : (short) RandomUtils.getRandomInt(random, this.min, this.max);
    }

    /**
     * @return size of range. (max - min)
     */
    public int size() {
        return ((int) this.max - (int) this.min);
    }

    @Override
    public Short getMinAsNumber() {
        return this.min;
    }

    @Override
    public Short getMaxAsNumber() {
        return this.max;
    }

    @Override
    public boolean isInRange(long number) {
        return (number >= this.min) && (number <= this.max);
    }

    @Override
    public boolean isInRange(double number) {
        return (number >= this.min) && (number <= this.max);
    }

    public short getInRange(short number, short def) {
        if ((number > this.max) || (number < this.min)) {
            return def;
        }
        return number;
    }

    public short getInRange(short number) {
        if (number > this.max) {
            return this.max;
        }
        if (number < this.min) {
            return this.min;
        }
        return number;
    }

    @Override
    public Short getInRangeAsNumber(Short number, Short def) {
        return this.getInRange(number, def);
    }

    @Override
    public Short getInRangeAsNumber(Short number) {
        return this.getInRange(number);
    }

    @Override
    public Stream<Short> streamNumbersBetweenAsNumbers(Short step) {
        return this.streamNumbersBetweenAsInts(step.intValue()).mapToObj(x -> (short) x);
    }

    @Override
    public Short getRandomNumber(Random random) {
        return this.getRandom(random);
    }

    @Override
    public int hashCode() {
        int result = (int) this.min;
        result = (31 * result) + (int) this.max;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof ShortRange)) {
            return false;
        }

        ShortRange other = (ShortRange) o;

        return (this.max == other.max) && (this.min == other.min);

    }

    @Override
    public String toString() {
        return this.min + "," + this.max;
    }

    /**
     * Create range with only given value in range.
     *
     * @param num min and max of range.
     *
     * @return range with only one value in range.
     */
    public static ShortRange fixed(int num) {
        return of(num, num);
    }

    /**
     * Create range with only given value in range.
     *
     * @param num min and max of range.
     *
     * @return range with only one value in range.
     */
    public static ShortRange fixed(short num) {
        return of(num, num);
    }

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public static ShortRange of(short min, short max) {
        return new ShortRange(min, max);
    }

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public static ShortRange of(int min, int max) {
        if (! FULL.isInRange(min)) {
            throw new IllegalArgumentException("Min (" + min + ") not in range: " + FULL);
        }
        if (! FULL.isInRange(max)) {
            throw new IllegalArgumentException("Max (" + max + ") not in range: " + FULL);
        }
        return new ShortRange((short) min, (short) max);
    }

    /**
     * Parses given string to range, string is valid range when contains 2 numbers (second greater than first) and split char: <br>
     * " - ", " : ", " ; ", ", ", " ", ",", ";", ":", "-"
     *
     * @param string string to parse.
     *
     * @return parsed range or null.
     */
    @Nullable
    public static ShortRange valueOf(String string) {
        return RangeParserHelper.parse(string, ShortRange::of, MathUtils::asShort, (max, min) -> max > min);
    }
}
