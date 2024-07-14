package ftc.simplematrix.splining.PurePursuit;


import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.NonNull;

import ftc.simplematrix.splining.Qsplines.QSplinePath;
import ftc.simplematrix.splining.Robot.Drivetrain;
import ftc.simplematrix.splining.math.Pose2d;
import ftc.simplematrix.splining.math.Vector2;

abstract class PathFollower {
    QSplinePath path;
    Drivetrain drivetrain;

    public void setDrivetrain(@NonNull Drivetrain drivetrain) {
        // this.drivetrain = drivetrain;
    }

    /**
     * Sets the path to follow
     *
     * @param path The path to follow.
     * @param points How many points to generate along the path.
     */
    public void setPath(@NonNull QSplinePath path, int points) {
        this.path = path;
    }

    /**
     * Follow a path that was set by {@link #setPath(Vector2[])} earlier.
     *
     * @param getCurrentPos A method which retrieves the current position using odometry.
     */
    abstract void follow(@NonNull Supplier<Pose2d> getCurrentPos);
}