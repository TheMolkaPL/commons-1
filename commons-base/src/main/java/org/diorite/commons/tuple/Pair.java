package org.diorite.commons.tuple;

import javax.annotation.Nullable;
import java.util.Map.Entry;

public class Pair<L, R> implements Entry<L, R> {
    @Nullable
    L left;
    @Nullable
    R right;

    Pair(@Nullable L left, @Nullable R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(@Nullable L left, @Nullable R right) {
        return new Pair<>(left, right);
    }

    @Nullable
    public L getLeft() {
        return this.left;
    }

    @Nullable
    public R getRight() {
        return this.right;
    }

    @Nullable
    @Override
    public L getKey() {
        return this.getLeft();
    }

    @Nullable
    @Override
    public R getValue() {
        return this.getRight();
    }

    @Nullable
    @Override
    public R setValue(@Nullable R value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "{" + this.left + '=' + this.right + '}';
    }

}
