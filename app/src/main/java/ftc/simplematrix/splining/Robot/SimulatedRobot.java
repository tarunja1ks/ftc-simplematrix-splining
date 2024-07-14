package ftc.simplematrix.splining.Robot;

import ftc.simplematrix.splining.math.Pose2d;
import ftc.simplematrix.splining.math.Vector2;

public class SimulatedRobot implements Drivetrain {
    public Pose2d position;

    public SimulatedRobot(Pose2d startingPosition) {
        this.position = startingPosition;
    }

    @Override
    public void move(Vector2 velocity, double rotation, double speed) {
        velocity.mult(speed);
        rotation *= speed;

        this.position.x += velocity.x;
        this.position.y += velocity.y;
        this.position.heading += rotation;
    }
    
}
