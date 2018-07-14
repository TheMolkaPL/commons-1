package org.diorite.commons.math.range;

import org.diorite.commons.math.random.RandomUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Class defining range in some integer number, may be used to validate numbers.
 */
public interface IntegerNumericRange<N extends Number> extends NumericRange<N> {
    /**
     * @return min value in range.
     */
    default long getMinAsLong() {
        return this.getMinAsNumber().longValue();
    }

    /**
     * @return max value in range.
     */
    default long getMaxAsLong() {
        return this.getMaxAsNumber().longValue();
    }

    /**
     * Return given number if it is in range, or default value.
     * {@code number > max -> def}
     * {@code number < min -> def}
     * {@code else -> number}
     *
     * @param number number to validate.
     * @param def default value.
     *
     * @return given number or default value.
     */
    default long getInRangeAsLong(long number, long def) {
        long max = this.getMaxAsLong();
        long min = this.getMinAsLong();
        if ((number > max) || (number < min)) {
            return def;
        }
        return number;
    }

    /**
     * Return given number if it is in range, or default value.
     * {@code number > max -> def}
     * {@code number < min -> def}
     * {@code else -> number}
     *
     * @param number number to validate.
     * @param def default value.
     *
     * @return given number or default value.
     */
    default long getInRangeAsLong(int number, long def) {
        return this.getInRangeAsLong(((long) number), def);
    }

    /**
     * Return given number if it is in range, or default value.
     * {@code number > max -> def}
     * {@code number < min -> def}
     * {@code else -> number}
     *
     * @param number number to validate.
     * @param def default value.
     *
     * @return given number or default value.
     */
    default long getInRangeAsLong(double number, long def) {
        long max = this.getMaxAsLong();
        long min = this.getMinAsLong();
        if ((number > max) || (number < min)) {
            return def;
        }
        return (long) number;
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code number > max -> max}
     * {@code number < min -> min}
     * {@code else -> number}
     *
     * @param number number to validate.
     *
     * @return closest number in range.
     */
    default long getInRangeAsLong(long number) {
        long max = this.getMaxAsLong();
        long min = this.getMinAsLong();
        if (number > max) {
            return max;
        }
        if (number < min) {
            return min;
        }
        return number;
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code number > max -> max}
     * {@code number < min -> min}
     * {@code else -> number}
     *
     * @param number number to validate.
     *
     * @return closest number in range.
     */
    default long getInRangeAsLong(int number) {
        return this.getInRangeAsLong((long) number);
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code number > max -> max}
     * {@code number < min -> min}
     * {@code else -> number}
     *
     * @param number number to validate.
     *
     * @return closest number in range.
     */
    default long getInRangeAsLong(double number) {
        long max = this.getMaxAsLong();
        long min = this.getMinAsLong();
        if (number > max) {
            return max;
        }
        if (number < min) {
            return min;
        }
        return (long) number;
    }

    /**
     * @return size of range. (max - min)
     */
    default long sizeAsLong() {
        return this.getMaxAsLong() - this.getMinAsLong();
    }

    /**
     * Returns amount of steps of given size between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return count of numbers between range min/max using given step.
     */
    default long sizeBetween(long step) {
        long min = this.getMinAsLong();
        long max = this.getMaxAsLong();
        return (((max - min) / step) + 1);
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return numbers between range min/max using given step.
     */
    default long[] numbersBetweenAsLongs(long step) {
        return this.streamNumbersBetweenAsLongs(step).toArray();
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return lazy stream of numbers between range min/max using given step.
     */
    default LongStream streamNumbersBetweenAsLongs(long step) {
        long min = this.getMinAsLong();
        long max = this.getMaxAsLong();
        return LongStream.iterate(min, x -> x <= max, prev -> prev + step);
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return numbers between range min/max using given step.
     */
    default long[] numbersBetweenAsLongs(double step) {
        return this.streamNumbersBetweenAsLongs(step).toArray();
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return lazy stream of numbers between range min/max using given step.
     */
    default LongStream streamNumbersBetweenAsLongs(double step) {
        long min = this.getMinAsLong();
        long max = this.getMaxAsLong();
        return DoubleStream.iterate(min, x -> x <= max, prev -> prev + step).mapToLong(x -> ((long) x));
    }


    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return numbers between range min/max using given step.
     */
    default int[] numbersBetweenAsInts(int step) {
        return this.streamNumbersBetweenAsInts(step).toArray();
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return lazy stream of numbers between range min/max using given step.
     */
    default IntStream streamNumbersBetweenAsInts(int step) {
        return this.streamNumbersBetweenAsLongs(step).mapToInt(x -> (int) x);
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return numbers between range min/max using given step.
     */
    default int[] numbersBetweenAsInts(double step) {
        return this.streamNumbersBetweenAsInts(step).toArray();
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return lazy stream of numbers between range min/max using given step.
     */
    default IntStream streamNumbersBetweenAsInts(double step) {
        return this.streamNumbersBetweenAsLongs(step).mapToInt(x -> (int) x);
    }

    /**
     * Returns random value in range.
     *
     * @return random value in range.
     */
    default long getRandomLong() {
        return this.getRandomLong(ThreadLocalRandom.current());
    }

    /**
     * Returns random value in range.
     *
     * @param random random instance to use.
     *
     * @return random value in range.
     */
    default long getRandomLong(Random random) {
        long min = this.getMinAsLong();
        long max = this.getMaxAsLong();
        return ((max - min) == 0) ? max : (byte) RandomUtils.getRandomLong(random, min, max);
    }
}
