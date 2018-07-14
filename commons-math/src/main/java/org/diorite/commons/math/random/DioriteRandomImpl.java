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

package org.diorite.commons.math.random;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

class DioriteRandomImpl extends Random implements DioriteRandom {
    private static final long serialVersionUID = 0;

    protected DioriteRandomImpl() {
    }

    protected DioriteRandomImpl(long seed) {
        super(seed);
    }

    @Override
    @Nullable
    public <T> T getRandom(T[] array) {
        return RandomUtils.getRandom(this, array);
    }

    @Override
    @Nullable
    public <T> T getRandom(List<? extends T> coll) {
        return RandomUtils.getRandom(this, coll);
    }

    @Override
    public <T, E extends Collection<? super T>> E getRandom(Collection<? extends T> coll, E target, int amount) {
        return RandomUtils.getRandom(this, coll, target, amount);
    }

    @Override
    public <T, E extends Collection<? super T>> E getRandom(Collection<? extends T> coll, E target, int amount, boolean noRepeat) {
        return RandomUtils.getRandom(this, coll, target, amount, noRepeat);
    }

    @Override
    @Nullable
    public <T> T getRandom(Collection<? extends T> coll) {
        return RandomUtils.getRandom(this, coll);
    }

    @Override
    public long getRandomLongSafe(long a, long b) {
        return RandomUtils.getRandomLongSafe(this, a, b);
    }

    @Override
    public int getRandomIntSafe(int a, int b) {
        return RandomUtils.getRandomIntSafe(this, a, b);
    }

    @Override
    public double getRandomDoubleSafe(double a, double b) {
        return RandomUtils.getRandomDoubleSafe(this, a, b);
    }

    @Override
    public float getRandomFloatSafe(float a, float b) {
        return RandomUtils.getRandomFloatSafe(this, a, b);
    }

    @Override
    public long getRandomLong(long min, long max) throws IllegalArgumentException {
        return RandomUtils.getRandomLong(this, min, max);
    }

    @Override
    public int getRandomInt(int min, int max) throws IllegalArgumentException {
        return RandomUtils.getRandomInt(this, min, max);
    }

    @Override
    public double getRandomDouble(double min, double max) throws IllegalArgumentException {
        return RandomUtils.getRandomDouble(this, min, max);
    }

    @Override
    public float getRandomFloat(float min, float max) throws IllegalArgumentException {
        return RandomUtils.getRandomFloat(this, min, max);
    }

    @Override
    public boolean getChance(double chance) {
        return RandomUtils.getChance(this, chance);
    }

    @Override
    @Nullable
    public <T extends IWeightedRandomChoice> T getWeightedRandom(Iterable<? extends T> choices) {
        return RandomUtils.getWeightedRandom(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandomReversed(Map<? extends Number, ? extends T> choices) {
        return RandomUtils.getWeightedRandomReversed(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandomReversedDouble(Map<Double, ? extends T> choices) {
        return RandomUtils.getWeightedRandomReversedDouble(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandomReversedInt(Map<Integer, ? extends T> choices) {
        return RandomUtils.getWeightedRandomReversedInt(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandomReversed(Double2ObjectMap<? extends T> choices) {
        return RandomUtils.getWeightedRandomReversed(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandomReversed(Int2ObjectMap<? extends T> choices) {
        return RandomUtils.getWeightedRandomReversed(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandom(Map<? extends T, ? extends Number> choices) {
        return RandomUtils.getWeightedRandom(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandomDouble(Map<? extends T, Double> choices) {
        return RandomUtils.getWeightedRandomDouble(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandomInt(Map<? extends T, Integer> choices) {
        return RandomUtils.getWeightedRandomInt(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandom(Object2DoubleMap<? extends T> choices) {
        return RandomUtils.getWeightedRandom(this, choices);
    }

    @Override
    @Nullable
    public <T> T getWeightedRandom(Object2IntMap<? extends T> choices) {
        return RandomUtils.getWeightedRandom(this, choices);
    }

    @Override
    public Random asRandom() {
        return this;
    }
}
