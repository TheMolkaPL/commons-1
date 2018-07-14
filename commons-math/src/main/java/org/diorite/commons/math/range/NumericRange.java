package org.diorite.commons.math.range;

import org.diorite.commons.math.random.RandomUtils;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Class defining range in numbers, may be used to validate numbers.
 */
public interface NumericRange<N extends Number> {
    /**
     * @return min value in range.
     */
    N getMinAsNumber();

    /**
     * @return max value in range.
     */
    N getMaxAsNumber();

    /**
     * @return min value in range.
     */
    default double getMinAsDouble() {
        return this.getMinAsNumber().doubleValue();
    }

    /**
     * @return max value in range.
     */
    default double getMaxAsDouble() {
        return this.getMaxAsNumber().doubleValue();
    }

    /**
     * Check if given number is in range.
     *
     * @param number number to check.
     *
     * @return true if it is in range
     */
    boolean isInRange(long number);

    /**
     * Check if given number is in range.
     *
     * @param number number to check.
     *
     * @return true if it is in range
     */
    default boolean isInRange(int number) {
        return this.isInRange(((long) number));
    }

    /**
     * Check if given number is in range.
     *
     * @param number number to check.
     *
     * @return true if it is in range
     */
    default boolean isInRange(short number) {
        return this.isInRange(((long) number));
    }

    /**
     * Check if given number is in range.
     *
     * @param number number to check.
     *
     * @return true if it is in range
     */
    default boolean isInRange(byte number) {
        return this.isInRange(((long) number));
    }

    /**
     * Check if given number is in range.
     *
     * @param number number to check.
     *
     * @return true if it is in range
     */
    default boolean isInRange(float number) {
        return this.isInRange(((double) number));
    }

    /**
     * Check if given number is in range.
     *
     * @param number number to check.
     *
     * @return true if it is in range
     */
    boolean isInRange(double number);

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
    N getInRangeAsNumber(N number, N def);

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
    N getInRangeAsNumber(N number);

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
    default double getInRangeAsDouble(long number, double def) {
        double max = this.getMaxAsDouble();
        double min = this.getMinAsDouble();
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
    default double getInRangeAsDouble(int number, double def) {
        return this.getInRangeAsDouble(((long) number), def);
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
    default double getInRangeAsDouble(double number, double def) {
        double max = this.getMaxAsDouble();
        double min = this.getMinAsDouble();
        if ((number > max) || (number < min)) {
            return def;
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
    default double getInRangeAsDouble(long number) {
        double max = this.getMaxAsDouble();
        double min = this.getMinAsDouble();
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
    default double getInRangeAsDouble(int number) {
        return this.getInRangeAsDouble((long) number);
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
    default double getInRangeAsDouble(double number) {
        double max = this.getMaxAsDouble();
        double min = this.getMinAsDouble();
        if (number > max) {
            return max;
        }
        if (number < min) {
            return min;
        }
        return number;
    }

    /**
     * @return size of range. (max - min)
     */
    default double sizeAsDouble() {
        return this.getMaxAsDouble() - this.getMinAsDouble();
    }

    /**
     * Returns amount of steps of given size between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return count of numbers between range min/max using given step.
     */
    default long sizeBetween(double step) {
        double min = this.getMinAsDouble();
        double max = this.getMaxAsDouble();
        return (long) (((max - min) / step) + 1);
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return numbers between range min/max using given step.
     */
    default double[] numbersBetweenAsDoubles(double step) {
        return this.streamNumbersBetweenAsDoubles(step).toArray();
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return numbers between range min/max using given step.
     */
    @SuppressWarnings("unchecked")
    default N[] numbersBetweenAsNumbers(N step) {
        return this.streamNumbersBetweenAsNumbers(step).toArray(i -> (N[]) Array.newInstance(step.getClass(), i));
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return lazy stream of numbers between range min/max using given step.
     */
    default DoubleStream streamNumbersBetweenAsDoubles(double step) {
        double min = this.getMinAsDouble();
        double max = this.getMaxAsDouble();
        return DoubleStream.iterate(min, x -> x <= max, prev -> prev + step);
    }

    /**
     * Return steps of given range between min and max, like for range between 1 and 10 and step of 1 there is 10 numbers
     * between: [1,2,3,4,5,6,7,8,9,10], or for range between 1 and 1.65 and step of 0.3 there are 2 numbers: [1.3, 1.6]
     *
     * @return lazy stream of numbers between range min/max using given step.
     */
    Stream<N> streamNumbersBetweenAsNumbers(N step);

    /**
     * Returns random value in range.
     *
     * @return random value in range.
     */
    default double getRandomDouble() {
        return this.getRandomDouble(ThreadLocalRandom.current());
    }

    /**
     * Returns random value in range.
     *
     * @param random random instance to use.
     *
     * @return random value in range.
     */
    default double getRandomDouble(Random random) {
        double min = this.getMinAsDouble();
        double max = this.getMaxAsDouble();
        return ((max - min) == 0) ? max : (byte) RandomUtils.getRandomDouble(random, min, max);
    }

    /**
     * Returns random value in range.
     *
     * @return random value in range.
     */
    default N getRandomNumber() {
        return this.getRandomNumber(ThreadLocalRandom.current());
    }

    /**
     * Returns random value in range.
     *
     * @param random random instance to use.
     *
     * @return random value in range.
     */
    N getRandomNumber(Random random);

    /**
     * Parses given string to range, string is valid range when contains 2 numbers (second greater than first) and split char: <br>
     * " - ", " : ", " ; ", ", ", " ", ",", ";", ":", "-"
     *
     * @param string string to parse.
     *
     * @return parsed range or null.
     */
    static DoubleRange valueOf(String string) {
        return DoubleRange.valueOf(string);
    }
}
