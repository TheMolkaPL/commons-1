package org.diorite.commons.math.geometry;

final class RectangleLookupShape implements LookupShape {
    @Override
    public Result isIn(double cx, double cy, double cz, double size, double px, double py, double pz) {
        double dx, dy, dz;
        if (((dx = Math.abs(px - cx) - size) > 0) || ((dy = Math.abs(py - cy) - size) > 0) || ((dz = Math.abs(pz - cz) - size) > 0)) {
            return Result.OUT;
        }
        if ((dx == 0) || (dy == 0) || (dz == 0)) {
            return Result.ON;
        }
        return Result.IN;
    }

    @Override
    public Result isIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        double dx, dy, dz;
        if (((dx = Math.abs(px - cx) - sxz) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sxz) > 0)) {
            return Result.OUT;
        }
        if ((dx == 0) || (dy == 0) || (dz == 0)) {
            return Result.ON;
        }
        return Result.IN;
    }

    @Override
    public Result isIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        double dx, dy, dz;
        if (((dx = Math.abs(px - cx) - sx) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sz) > 0)) {
            return Result.OUT;
        }
        if ((dx == 0) || (dy == 0) || (dz == 0)) {
            return Result.ON;
        }
        return Result.IN;
    }

    @Override
    public Result isIn(long cx, long cy, long cz, long size, long px, long py, long pz) {
        long dx, dy, dz;
        if (((dx = Math.abs(px - cx) - size) > 0) || ((dy = Math.abs(py - cy) - size) > 0) || ((dz = Math.abs(pz - cz) - size) > 0)) {
            return Result.OUT;
        }
        if ((dx == 0) || (dy == 0) || (dz == 0)) {
            return Result.ON;
        }
        return Result.IN;
    }

    @Override
    public Result isIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        long dx, dy, dz;
        if (((dx = Math.abs(px - cx) - sxz) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sxz) > 0)) {
            return Result.OUT;
        }
        if ((dx == 0) || (dy == 0) || (dz == 0)) {
            return Result.ON;
        }
        return Result.IN;
    }

    @Override
    public Result isIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        long dx, dy, dz;
        if (((dx = Math.abs(px - cx) - sx) > 0) || ((dy = Math.abs(py - cy) - sy) > 0) || ((dz = Math.abs(pz - cz) - sz) > 0)) {
            return Result.OUT;
        }
        if ((dx == 0) || (dy == 0) || (dz == 0)) {
            return Result.ON;
        }
        return Result.IN;
    }

    @Override
    public boolean isNotOutside(double cx, double cy, double cz, double size, double px, double py, double pz) {
        return ((Math.abs(px - cx) - size) <= 0) && ((Math.abs(py - cy) - size) <= 0) && ((Math.abs(pz - cz) - size) <= 0);
    }

    @Override
    public boolean isNotOutside(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        return ((Math.abs(px - cx) - sxz) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sxz) <= 0);
    }

    @Override
    public boolean isNotOutside(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        return ((Math.abs(px - cx) - sx) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sz) <= 0);
    }

    @Override
    public boolean isNotOutside(long cx, long cy, long cz, long size, long px, long py, long pz) {
        return ((Math.abs(px - cx) - size) <= 0) && ((Math.abs(py - cy) - size) <= 0) && ((Math.abs(pz - cz) - size) <= 0);
    }

    @Override
    public boolean isNotOutside(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        return ((Math.abs(px - cx) - sxz) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sxz) <= 0);
    }

    @Override
    public boolean isNotOutside(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        return ((Math.abs(px - cx) - sx) <= 0) && ((Math.abs(py - cy) - sy) <= 0) && ((Math.abs(pz - cz) - sz) <= 0);
    }

    @Override
    public boolean isExactlyIn(double cx, double cy, double cz, double size, double px, double py, double pz) {
        return ((Math.abs(px - cx) - size) < 0) && ((Math.abs(py - cy) - size) < 0) && ((Math.abs(pz - cz) - size) < 0);
    }

    @Override
    public boolean isExactlyIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        return ((Math.abs(px - cx) - sxz) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sxz) < 0);
    }

    @Override
    public boolean isExactlyIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        return ((Math.abs(px - cx) - sx) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sz) < 0);
    }

    @Override
    public boolean isExactlyIn(long cx, long cy, long cz, long size, long px, long py, long pz) {
        return ((Math.abs(px - cx) - size) < 0) && ((Math.abs(py - cy) - size) < 0) && ((Math.abs(pz - cz) - size) < 0);
    }

    @Override
    public boolean isExactlyIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        return ((Math.abs(px - cx) - sxz) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sxz) < 0);
    }

    @Override
    public boolean isExactlyIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        return ((Math.abs(px - cx) - sx) < 0) && ((Math.abs(py - cy) - sy) < 0) && ((Math.abs(pz - cz) - sz) < 0);
    }

    @Override
    public boolean isExactlyOn(double cx, double cy, double cz, double size, double px, double py, double pz) {
        return ((Math.abs(px - cx) - size) == 0) && ((Math.abs(py - cy) - size) == 0) && ((Math.abs(pz - cz) - size) == 0);
    }

    @Override
    public boolean isExactlyOn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz) {
        return ((Math.abs(px - cx) - sxz) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sxz) == 0);
    }

    @Override
    public boolean isExactlyOn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz) {
        return ((Math.abs(px - cx) - sx) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sz) == 0);
    }

    @Override
    public boolean isExactlyOn(long cx, long cy, long cz, long size, long px, long py, long pz) {
        return ((Math.abs(px - cx) - size) == 0) && ((Math.abs(py - cy) - size) == 0) && ((Math.abs(pz - cz) - size) == 0);
    }

    @Override
    public boolean isExactlyOn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz) {
        return ((Math.abs(px - cx) - sxz) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sxz) == 0);
    }

    @Override
    public boolean isExactlyOn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz) {
        return ((Math.abs(px - cx) - sx) == 0) && ((Math.abs(py - cy) - sy) == 0) && ((Math.abs(pz - cz) - sz) == 0);
    }
}
