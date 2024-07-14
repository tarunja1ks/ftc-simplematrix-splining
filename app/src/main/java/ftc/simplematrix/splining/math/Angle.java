package ftc.simplematrix.splining.math;

import static ftc.simplematrix.splining.math.Algebra.sign;

public class Angle {
    /**
     * Change an angle to become between -180 and 180 degrees.
     * @param angle An angle, in radians
     * @return The optimized angle
     */
    public static double optimize(double angle) {
        if (angle > 2*Math.PI || angle < -2*Math.PI) {
            angle = -1 * sign(angle) * (360 - Math.abs(angle));
        }

        return angle;
    }

    /**
     * Gets the difference between two angles.
     * @param angle1 The first angle
     * @param angle2 The second angle
     * @return The difference between the two angles, in radians
     */
    public double getdelta(double angle1, double angle2){ // function for difference between 2 angles
        double[] changes = {-2.0*Math.PI, 0.0, 2.0*Math.PI}; // add/subtract 2pi radians
        double min_delta = Math.abs(angle2 - angle1);
        for (int i=0; i < 3; i++) {
            min_delta = Math.min(min_delta, Math.abs((angle2 + changes[i]) - angle1));
        }
        return min_delta;
    }
}
