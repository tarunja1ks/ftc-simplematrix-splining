package ftc.simplematrix.splining;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import javax.swing.JPanel;

import org.ejml.equation.Symbol;

import ftc.simplematrix.splining.Qsplines.QSplinePath;
import ftc.simplematrix.splining.Robot.SimulatedRobot;
import ftc.simplematrix.splining.math.Pose2d;
import ftc.simplematrix.splining.math.Vector2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
public class GraphPanel extends JPanel {
    private double c0, c1, c2, c3, c4, c5;
    private double x1,x2,y1,y2;
    private Image backgroundImage;
    public List<QSplinePath> spline_x;
    public List<QSplinePath> spline_y;

    public SimulatedRobot simulatedRobot;
    public int robotSize = 50;

    public GraphPanel(List<QSplinePath> spline_x, List<QSplinePath> spline_y) { // adding in the list of spline segments for x and y movement
        this.spline_x=spline_x;
        this.spline_y=spline_y;
        this.backgroundImage = new ImageIcon("src/main/java/ftc/simplematrix/splining/custom-centerstage-field-diagrams-works-with-meepmeep-v0-gytjsw5tfpob1.png").getImage();
        
        double startingX = spline_x.get(0).q0;
        double startingY = spline_y.get(0).q0;
        simulatedRobot = new SimulatedRobot(new Pose2d(
            startingX,
            startingY,
            Math.atan2(startingY, startingX)
        ));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Set background 
        g2d.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        System.out.println(this.backgroundImage);
        System.out.println( this.backgroundImage.getWidth(this));
        

        // Draw the axes
        g2d.setColor(Color.BLACK);
        int width = getWidth();
        int height = getHeight();
        int xAxis = height / 2;
        int yAxis = width / 2;

        g2d.drawLine(0, xAxis, width, xAxis); // X-Axis
        g2d.drawLine(yAxis, 0, yAxis, height); // Y-Axis

        // Draw axis labels
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = -72; i <= 72; i += 12) {
            int x = (int) (yAxis + i * (width / 144.0));
            g2d.drawLine(x, xAxis - 5, x, xAxis + 5);
            if (i != 0) {
                g2d.drawString(String.valueOf(i), x - 10, xAxis + 20);
            }
        }

        for (int i = -72; i <= 72; i += 12) {
            int y = (int) (xAxis - i * (height / 144.0));
            g2d.drawLine(yAxis - 5, y, yAxis + 5, y);
            if (i != 0) {
                g2d.drawString(String.valueOf(i), yAxis + 10, y + 5);
            }
        }

        // Looping through each spline segment and graphing it
        
        for(int i=0;i<spline_y.size();i++){
            graphSpline(g2d, spline_x.get(i), spline_y.get(i), xAxis, yAxis, width, height);
            plotPoint(g2d, spline_x.get(i).q0, spline_y.get(i).q0);
            plotPoint(g2d, spline_x.get(i).qf, spline_y.get(i).qf);
        }


        // Draw the robot as a square
        g2d.setColor(Color.BLUE);
        double scaledRobotX = yAxis + simulatedRobot.position.x * (width / 144.0);
        double scaledRobotY = xAxis - simulatedRobot.position.y * (height / 144.0);
        g2d.fillRect((int) scaledRobotX - robotSize / 2, (int) scaledRobotY - robotSize / 2, robotSize, robotSize);
        
        
       
    }


    public void plotPoint(Graphics2D g2d, double x, double y) { //plotting a point to mark waypoints/start/end and etc
        int xAxis = getHeight() / 2;
        int yAxis = getWidth() / 2;

        double scaledX = x * (getWidth() / 144.0) + yAxis;
        double scaledY = xAxis - y * (getHeight() / 144.0); // Adjust scaling factor here

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) scaledX - 5, (int) scaledY - 5, 10, 10); // Draw a small circle at the point
    }

    public void updatecoeffecientsplines(QSplinePath splines){ //updating c1 to 5 to the spline assigned so that way i dont call it each time and its not annoying and poopy
        this.c0=splines.getcoeff().get(0);
        this.c1=splines.getcoeff().get(1);
        this.c2=splines.getcoeff().get(2);
        this.c3=splines.getcoeff().get(3);
        this.c4=splines.getcoeff().get(4);
        this.c5=splines.getcoeff().get(5);
    }

    public void graphSpline(Graphics2D g2d, QSplinePath spline_x, QSplinePath spline_y, double xAxis, double yAxis, double width, double height){ // graphing the x/y blended spline
        g2d.setColor(Color.RED);
        double prevX = 0, prevY = 0;
        boolean firstPoint = true;
        for (double t = spline_x.getTimes()[0]; t <= spline_x.getTimes()[1]; t += 0.1) {
            updatecoeffecientsplines(spline_x);
            double xpos = c0 + c1 * t + c2 * Math.pow(t, 2) + c3 * Math.pow(t, 3) + c4 * Math.pow(t, 4) + c5 * Math.pow(t, 5);
            updatecoeffecientsplines(spline_y);
            double ypos = c0 + c1 * t + c2 * Math.pow(t, 2) + c3 * Math.pow(t, 3) + c4 * Math.pow(t, 4) + c5 * Math.pow(t, 5);
            double scaledX = yAxis + xpos * (width / 144.0);
            double scaledY = xAxis - ypos * (height / 144.0);

            if (firstPoint) {
                firstPoint = false;
            } else {
                g2d.drawLine((int) prevX, (int) prevY, (int) scaledX, (int) scaledY);
            }

                prevX = scaledX;
                prevY = scaledY;
        }
    }

    public void updateRobotPosition(double t) {
        for (int i = 0; i < spline_x.size(); i++) {
            if (t >= spline_x.get(i).getTimes()[0] && t <= spline_x.get(i).getTimes()[1]) {
                updatecoeffecientsplines(spline_x.get(i));
                double robotX = c0 + c1 * t + c2 * Math.pow(t, 2) + c3 * Math.pow(t, 3) + c4 * Math.pow(t, 4) + c5 * Math.pow(t, 5);
                updatecoeffecientsplines(spline_y.get(i));
                double robotY = c0 + c1 * t + c2 * Math.pow(t, 2) + c3 * Math.pow(t, 3) + c4 * Math.pow(t, 4) + c5 * Math.pow(t, 5);
                this.simulatedRobot.move(
                    new Vector2(robotX-simulatedRobot.position.x, robotY-simulatedRobot.position.y), 
                    Math.atan2(robotY, robotX)-simulatedRobot.position.heading
                );
                
                break;
            }
        }
    }
}
