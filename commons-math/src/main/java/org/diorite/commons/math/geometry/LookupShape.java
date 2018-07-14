package org.diorite.commons.math.geometry;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * Lookup shapes, used to check if given point is inside some area defined by it center and size that are defining some shape, like
 * rectangle or ellipsoid.
 */
public interface LookupShape {
    /**
     * For rectangle lookups
     */
    LookupShape RECTANGLE = new RectangleLookupShape();
    /**
     * For cylinder lookups
     */
    LookupShape CYLINDER  = new CylinderLookupShape();
    /**
     * For ellipsoid lookups
     */
    LookupShape ELLIPSOID = new EllipsoidLookupShape();

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, Vector3d size, Vector3d point) {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, double sx, double sy, double sz, Vector3d point) {
        return this.isIn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, Vector2d size, Vector3d point) {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, double sxz, double sy, Vector3d point) {
        return this.isIn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, double size, Vector3d point) {
        return this.isIn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(double cx, double cy, double cz, Vector3d size, Vector3d point) {
        return this.isIn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(double cx, double cy, double cz, Vector2d size, Vector3d point) {
        return this.isIn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(double cx, double cy, double cz, double size, Vector3d point) {
        return this.isIn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, Vector3d size, double px, double py, double pz) {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, double sx, double sy, double sz, double px, double py, double pz) {
        return this.isIn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, Vector2d size, double px, double py, double pz) {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, double sxz, double sy, double px, double py, double pz) {
        return this.isIn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3d center, double size, double px, double py, double pz) {
        return this.isIn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(double cx, double cy, double cz, Vector3d size, double px, double py, double pz) {
        return this.isIn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(double cx, double cy, double cz, Vector2d size, double px, double py, double pz) {
        return this.isIn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    Result isIn(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    Result isIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    Result isIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    Result isIn(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    Result isIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    Result isIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Returns enum element indicating that element is inside, outside or on border of area.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return enum element indicating that element is inside, outside or on border of area.
     */
    default Result isIn(Vector3f center, Vector3f size, Vector3f point) {
        return this.isIn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, Vector3d size, Vector3d point) {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, double sx, double sy, double sz, Vector3d point) {
        return this.isNotOutside(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, Vector2d size, Vector3d point) {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, double sxz, double sy, Vector3d point) {
        return this.isNotOutside(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, double size, Vector3d point) {
        return this.isNotOutside(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(double cx, double cy, double cz, Vector3d size, Vector3d point) {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(double cx, double cy, double cz, Vector2d size, Vector3d point) {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(double cx, double cy, double cz, double size, Vector3d point) {
        return this.isNotOutside(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, Vector3d size, double px, double py, double pz) {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, double sx, double sy, double sz, double px, double py, double pz) {
        return this.isNotOutside(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, Vector2d size, double px, double py, double pz) {
        return this.isNotOutside(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, double sxz, double sy, double px, double py, double pz) {
        return this.isNotOutside(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(Vector3d center, double size, double px, double py, double pz) {
        return this.isNotOutside(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(double cx, double cy, double cz, Vector3d size, double px, double py, double pz) {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    default boolean isNotOutside(double cx, double cy, double cz, Vector2d size, double px, double py, double pz) {
        return this.isNotOutside(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    boolean isNotOutside(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    boolean isNotOutside(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    boolean isNotOutside(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    boolean isNotOutside(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    boolean isNotOutside(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns true if point is in area or on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is in area or on area border.
     */
    boolean isNotOutside(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, Vector3d size, Vector3d point) {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, double sx, double sy, double sz, Vector3d point) {
        return this.isExactlyIn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, Vector2d size, Vector3d point) {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, double sxz, double sy, Vector3d point) {
        return this.isExactlyIn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, double size, Vector3d point) {
        return this.isExactlyIn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(double cx, double cy, double cz, Vector3d size, Vector3d point) {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(double cx, double cy, double cz, Vector2d size, Vector3d point) {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(double cx, double cy, double cz, double size, Vector3d point) {
        return this.isExactlyIn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, Vector3d size, double px, double py, double pz) {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, double sx, double sy, double sz, double px, double py, double pz) {
        return this.isExactlyIn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, Vector2d size, double px, double py, double pz) {
        return this.isExactlyIn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, double sxz, double sy, double px, double py, double pz) {
        return this.isExactlyIn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(Vector3d center, double size, double px, double py, double pz) {
        return this.isExactlyIn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(double cx, double cy, double cz, Vector3d size, double px, double py, double pz) {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    default boolean isExactlyIn(double cx, double cy, double cz, Vector2d size, double px, double py, double pz) {
        return this.isExactlyIn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    boolean isExactlyIn(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    boolean isExactlyIn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    boolean isExactlyIn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    boolean isExactlyIn(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    boolean isExactlyIn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns true if point is exactly in area. (it can't be on border)
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly in area. (it can't be on border)
     */
    boolean isExactlyIn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, Vector3d size, Vector3d point) {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, size.z, point.z, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, double sx, double sy, double sz, Vector3d point) {
        return this.isExactlyOn(center.x, center.y, center.z, sx, sy, sz, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, Vector2d size, Vector3d point) {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, double sxz, double sy, Vector3d point) {
        return this.isExactlyOn(center.x, center.y, center.z, sxz, sy, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, double size, Vector3d point) {
        return this.isExactlyOn(center.x, center.y, center.z, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(double cx, double cy, double cz, Vector3d size, Vector3d point) {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, size.z, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(double cx, double cy, double cz, Vector2d size, Vector3d point) {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param point point to be checked if it is inside of given area.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(double cx, double cy, double cz, double size, Vector3d point) {
        return this.isExactlyOn(cx, cy, cz, size, point.x, point.y, point.z);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, Vector3d size, double px, double py, double pz) {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, double sx, double sy, double sz, double px, double py, double pz) {
        return this.isExactlyOn(center.x, center.y, center.z, sx, sy, sz, px, py, pz);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, Vector2d size, double px, double py, double pz) {
        return this.isExactlyOn(center.x, center.y, center.z, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, double sxz, double sy, double px, double py, double pz) {
        return this.isExactlyOn(center.x, center.y, center.z, sxz, sy, px, py, pz);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param center center point of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(Vector3d center, double size, double px, double py, double pz) {
        return this.isExactlyOn(center.x, center.y, center.z, size, px, py, pz);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(double cx, double cy, double cz, Vector3d size, double px, double py, double pz) {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, size.z, px, py, pz);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    default boolean isExactlyOn(double cx, double cy, double cz, Vector2d size, double px, double py, double pz) {
        return this.isExactlyOn(cx, cy, cz, size.x, size.y, px, py, pz);
    }

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    boolean isExactlyOn(double cx, double cy, double cz, double size, double px, double py, double pz);

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    boolean isExactlyOn(double cx, double cy, double cz, double sxz, double sy, double px, double py, double pz);

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    boolean isExactlyOn(double cx, double cy, double cz, double sx, double sy, double sz, double px, double py, double pz);

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param size size of area in all 3 axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    boolean isExactlyOn(long cx, long cy, long cz, long size, long px, long py, long pz);

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sxz size of area in xz axis.
     * @param sy size of area in y axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    boolean isExactlyOn(long cx, long cy, long cz, long sxz, long sy, long px, long py, long pz);

    /**
     * Returns true if point is exactly on area border.
     *
     * @param cx x center coordinates of area.
     * @param cy y center coordinates of area.
     * @param cz z center coordinates of area.
     * @param sx size of area in x axis.
     * @param sy size of area in y axis.
     * @param sz size of area in z axis.
     * @param px x coordinates of point to check.
     * @param py y coordinates of point to check.
     * @param pz z coordinates of point to check.
     *
     * @return true if point is exactly on area border.
     */
    boolean isExactlyOn(long cx, long cy, long cz, long sx, long sy, long sz, long px, long py, long pz);

    /**
     * Possible result of checks.
     */
    enum Result {
        /**
         * Point is inside of area.
         */
        IN,
        /**
         * Point is on border of area.
         */
        ON,
        /**
         * Point is outside of area.
         */
        OUT,
        ;

        static Result getResult(double d) {
            if (d > 0) {
                return Result.OUT;
            }
            if (d == 0) {
                return Result.ON;
            }
            return Result.IN;
        }

        static Result getResult(long i) {
            if (i > 0) {
                return Result.OUT;
            }
            if (i == 0) {
                return Result.ON;
            }
            return Result.IN;
        }
    }
}
