package org.diorite.commons.math.geometry;

import static org.diorite.commons.math.MathUtils.square;

final class EllipsoidLookupShape implements LookupShape {
    @Override
    public Result isIn(double cx, double cy, double cz, double size, double px, double py, double pz) {
        return Result.getResult((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size));
    }

    @Override
    public Result isIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        return Result.getResult((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1);
    }

    @Override
    public Result isIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        return Result.getResult(((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1);
    }

    @Override
    public Result isIn(long cx, long cy, long cz, long size, long px, long py, long pz) {
        return Result.getResult((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size));
    }

    @Override
    public Result isIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        return Result.getResult((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1);
    }

    @Override
    public Result isIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        return Result.getResult(((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1);
    }

    @Override
    public boolean isNotOutside(double cx, double cy, double cz, double size, double px, double py, double pz) {
        return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) <= 0;
    }

    @Override
    public boolean isNotOutside(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) <= 0;
    }

    @Override
    public boolean isNotOutside(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) <= 0;
    }

    @Override
    public boolean isNotOutside(long cx, long cy, long cz, long size, long px, long py, long pz) {
        return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) <= 0;
    }

    @Override
    public boolean isNotOutside(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) <= 0;
    }

    @Override
    public boolean isNotOutside(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) <= 0;
    }

    @Override
    public boolean isExactlyIn(double cx, double cy, double cz, double size, double px, double py, double pz) {
        return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) < 0;
    }

    @Override
    public boolean isExactlyIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) < 0;
    }

    @Override
    public boolean isExactlyIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) < 0;
    }

    @Override
    public boolean isExactlyIn(long cx, long cy, long cz, long size, long px, long py, long pz) {
        return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) < 0;
    }

    @Override
    public boolean isExactlyIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) < 0;
    }

    @Override
    public boolean isExactlyIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) < 0;
    }

    @Override
    public boolean isExactlyOn(double cx, double cy, double cz, double size, double px, double py, double pz) {
        return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) == 0;
    }

    @Override
    public boolean isExactlyOn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) == 0;
    }

    @Override
    public boolean isExactlyOn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) == 0;
    }

    @Override
    public boolean isExactlyOn(long cx, long cy, long cz, long size, long px, long py, long pz) {
        return ((square(px - cx) + square(py - cy) + square(pz - cz)) - square(size)) == 0;
    }

    @Override
    public boolean isExactlyOn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        return ((((square(px - cx) + square(pz - cz)) / square(sxz)) + (square(py - cy) / square(sy))) - 1) == 0;
    }

    @Override
    public boolean isExactlyOn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        return (((square(px - cx) / square(sx)) + (square(py - cy) / square(sy)) + (square(pz - cz) / square(sz))) - 1) == 0;
    }
}
