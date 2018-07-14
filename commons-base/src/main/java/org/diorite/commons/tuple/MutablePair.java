package org.diorite.commons.tuple;

import javax.annotation.Nullable;

public final class MutablePair<L, R> extends Pair<L, R> {
    MutablePair(@Nullable L left, @Nullable R right) {
        super(left, right);
    }

    public static <L, R> MutablePair<L, R> of(@Nullable L left, @Nullable R right) {
        return new MutablePair<>(left, right);
    }

    public void setLeft(@Nullable L left) {
        this.left = left;
    }

    public void setRight(@Nullable R right) {
        this.right = right;
    }

    @Nullable
    @Override
    public R setValue(@Nullable R value) {
        final R result = this.right;
        this.right = value;
        return result;
    }

}
