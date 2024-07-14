package ftc.simplematrix.splining.Robot;

import ftc.simplematrix.splining.math.Vector2;

public interface Drivetrain {
    /**
     * Drive at a certain speed.
     * @param velocity The x and y.
     * @param rotation The rotation
     * @param speed    The robot's speed, a value between 0 and 1 (this is a multiplier, will which
     *                 basically sets the max speed).
     */
    public void move(Vector2 velocity, double rotation, double speed);

    /**
     * Drive at max speed (1).
     * @param velocity The x and y.
     * @param rotation The rotation.
     */
    default void move(Vector2 velocity, double rotation) {
        move(velocity, rotation, 1);
    }
}
