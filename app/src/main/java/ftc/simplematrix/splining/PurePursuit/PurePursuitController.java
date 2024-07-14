package ftc.simplematrix.splining.PurePursuit;


import java.util.function.Supplier;

import javax.annotation.Nonnull;

import ftc.simplematrix.splining.Qsplines.QSplinePath;

/**
 * A class which is used to follow paths using the Pure Pursuit algorithm.
 */
public class PurePursuitController extends PathFollower {
    public double goalThreshold;
    public double lookAheadDist;
    public Vector2[] pathArr;

    public Obstacle[]Field= new Obstacle[1000];
    public int obstacle_count=0;

    /**
     * Instantiates a pure pursuit controller.
     * @param lookAheadDist The look ahead distance.
     * @param goalThreshold The threshold before reaching.
     */
    public PurePursuitController(double lookAheadDist, double goalThreshold) {
        this.lookAheadDist = lookAheadDist;
        this.goalThreshold = goalThreshold;
    }

    public void add_Field(Obstacle obstacle){
        Field[obstacle_count]=obstacle;
        obstacle_count+=1;
    }


    @Override
    public void setPath(@NonNull QSplinePath path, int points) {
        super.setPath(path, points);
        this.pathArr = this.path.generateArr(points);
    }

    /**
     * Follow a path that was set by {@link #setPath(Vector2[])} earlier and drive using the
     * drivetrain set by {@link #setDrivetrain(Drivetrain)}.
     * @param getCurrentPos A method which retrieves the current position using odometry.
     */
    public void follow(@NonNull Supplier<Pose2d> getCurrentPos) {
        Vector2 lastGoalPoint = this.pathArr[0];
        int lastPointIndex = 0;

        Pose2d currentPos = getCurrentPos.get();

        while (Math.abs(distToPoint(currentPos.getVector(), this.pathArr[this.pathArr.length-1])) > this.goalThreshold) {
            currentPos = getCurrentPos.get();

            // Calculate the goal point
            Vector2 goalPoint = null;
            for (int i = lastPointIndex; i < this.pathArr.length - 1; i++) {
                goalPoint = circleLineIntersection(this.pathArr[i], this.pathArr[i + 1], currentPos.getVector(), this.lookAheadDist);
                goalPoint=adapt_lookahead(0.5,20,currentPos,i);
                if (goalPoint != null) {
                    lastPointIndex = i;
                    break;
                }
            }
            if (goalPoint == null) {
                goalPoint = lastGoalPoint;
            }
            lastGoalPoint = goalPoint;
//            double linearErr = distToPoint(goalPoint, currentPos.getVector());
            this.driveToPoint(currentPos, goalPoint);
        }
        currentPos = getCurrentPos.get();
        this.driveToPoint(currentPos, this.pathArr[this.pathArr.length-1]);
    }



    private void driveToPoint(@NonNull Pose2d currentPos, @NonNull Vector2 goalPoint) {
        double goalAngle = Math.atan2(goalPoint.y, goalPoint.x);
        if (goalAngle < 0) { goalAngle += 2*Math.PI; }
        double angularErr = Angle.optimize(goalAngle - currentPos.heading);
        drivetrain.move(
                new Vector2(
                        goalPoint.x - currentPos.x,
                        goalPoint.y - currentPos.y
                ),
                angularErr
        );
    }

    private boolean CrashDetect(Pose2d currentPos, Vector2 goalPoint ){
        // first check if out of bounds
        for(int i=0;i<obstacle_count;i++){ // cycling for crash with all of the obstacles
            if(Field[i].crash_detect(currentPos.x,currentPos.y,goalPoint.x,goalPoint.y, currentPos.heading)){
                return true;
            }
        }
        return false;

        // add obstacle object and use the line intersection test on all four sides of the rectangle objects bounds

    }

    public Vector2 adapt_lookahead(double min, double max, Pose2d currentPos, int i){
        // binary search for the lookahead distance and if the lookahead doesnt work(use crash_detect) please decrease the lookahead
        double step_size=100;
        double interval=(max-min)/step_size;
        double mid=0;
        Vector2 goalPoint= new Vector2(0,0);
        while (min <= max) {
            mid=(min+max)/2;
            goalPoint = circleLineIntersection(this.pathArr[i], this.pathArr[i + 1], currentPos.getVector(), mid);
            if(CrashDetect(currentPos,goalPoint)){
                max=mid-interval;
            }
            else{
                min=mid+interval;
            }
        }
        return goalPoint;

    }


}
