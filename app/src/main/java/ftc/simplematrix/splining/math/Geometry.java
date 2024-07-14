package ftc.simplematrix.splining.math;

import javax.annotation.Nullable;

import static ftc.simplematrix.splining.math.Algebra.sign;

import org.checkerframework.checker.nullness.qual.NonNull;

import ftc.simplematrix.splining.Vector2;

public class Geometry {
    /**
     * Gets the cartesian distance between two points.
     * @param point1 The first point.
     * @param point2 The second point.
     * @return The distance between the two points.
     */
    public static double distToPoint(@NonNull Vector2 point1, @NonNull Vector2 point2) {
        return Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
    }

    /**
     * Get an intersection between a line segment and circle. If there are two, the point closer
     * to the second point will be chosen. If there are none, null will be returned.
     * @param point1 First point on line
     * @param point2 Second point on line
     * @param center Center of circle
     * @param radius Radius of circle
     * @return A point, can be null.
     */
    @Nullable
    // @Contract("_, _, _, _ -> new")
    public static Vector2 circleLineIntersection(@NonNull Vector2 point1, @NonNull Vector2 point2, Vector2 center, double radius) {
        // Math comes from https://mathworld.wolfram.com/Circle-LineIntersection.html, except we
        // shift the center of the circle to the origin since our circle won't be centered at the
        // origin. We also add an extra check that the circle intersects with the line segmnet, not
        // the entire line.
        Vector2 p1shifted = Vector2.minus(point1, center); // shift to origin
        Vector2 p2shifted = Vector2.minus(point2, center); // shift to origin

        double minX = Math.min(point1.x, point2.x);
        double maxX = Math.max(point1.x, point2.x);
        double minY = Math.min(point1.y, point2.y);
        double maxY = Math.max(point1.y, point2.y);

        double dx = point2.x - point1.x;
        double dy = point2.y - point1.y;
        double dr = Math.sqrt(dx * dx + dy * dy);
        double det = p1shifted.x * p2shifted.y - p2shifted.x * p1shifted.y;
        double discriminant = Math.pow(radius, 2) * Math.pow(dr, 2) - Math.pow(det, 2);

        if (discriminant < 0) {
            return null;
        }

        double temp = sign(dy) * dx * Math.sqrt(discriminant);
        double x1 = (det * dy + temp) / Math.pow(dr, 2);
        double x2 = (det * dy - temp) / Math.pow(dr, 2);

        temp = Math.abs(dy) * Math.sqrt(discriminant);
        double y1 = (-det * dx + temp) / Math.pow(dr, 2);
        double y2 = (-det * dx - temp) / Math.pow(dr, 2);

        Vector2 sol1 = new Vector2(x1, y1);
        Vector2 sol2 = new Vector2(x2, y2);

        // Set the solution to null if it is outside of the line segment
        if ((minX > sol1.x || sol1.x > maxX) || (minY > sol1.y || sol1.y > maxY)) {
            sol1 = null;
        }
        if ((minX > sol2.x || sol2.x > maxX) || (minY > sol2.y || sol2.y > maxY)) {
            sol2 = null;
        }

        // Return the valid solution, or if there are two, the one closer to point 2
        if (sol1 != null && sol2 != null) {
            if (distToPoint(sol1, point2) < distToPoint(sol2, point2)) {
                return sol1;
            } else {
                return sol2;
            }
        } else if (sol1 != null) {
            return sol1;
        } else if (sol2 != null) {
            return sol2;
        } else {
            return null;
        }
    }

    public void line_intersections(){

    }
}
