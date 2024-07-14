package ftc.simplematrix.splining;

import ftc.simplematrix.splining.math.Pose2d;

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
