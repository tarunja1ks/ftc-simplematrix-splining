package ftc.simplematrix.splining;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import javax.swing.JPanel;

import org.ejml.equation.Symbol;

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
    public GraphPanel(List<QSplinePath> spline_x, List<QSplinePath> spline_y) {
        this.spline_x=spline_x;
        this.spline_y=spline_y;
        this.backgroundImage = new ImageIcon("src/main/java/ftc/simplematrix/splining/custom-centerstage-field-diagrams-works-with-meepmeep-v0-gytjsw5tfpob1.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        System.out.println(this.backgroundImage);
        System.out.println( this.backgroundImage.getWidth(this));
        // Set background color
        // g2d.setColor(Color.WHITE);
        // g2d.fillRect(0, 0, getWidth(), getHeight());

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

        // Draw the polynomial q(t) = c0 + c1*t + c2*t^2 + c3*t^3 + c4*t^4 + c5*t^5
        
        for(int i=0;i<spline_y.size();i++){
            graphSpline(g2d, spline_x.get(i), spline_y.get(i), xAxis, yAxis, width, height);
            plotPoint(g2d, spline_x.get(i).q0, spline_y.get(i).q0);
            plotPoint(g2d, spline_x.get(i).qf, spline_y.get(i).qf);
        }


        // g2d.setColor(Color.BLACK);
        // prevX = 0; prevY = 0;
        // firstPoint = true;
        // for (double t = 0; t <= 20000; t += 0.1) {
        //     double xpos = t;
        //     updatecoeffecientsplines(spline_y);
        //     double ypos = c0 + c1 * t + c2 * Math.pow(t, 2) + c3 * Math.pow(t, 3) + c4 * Math.pow(t, 4) + c5 * Math.pow(t, 5);
        //     double scaledX = yAxis + xpos * (width / 144.0);
        //     double scaledY = xAxis - ypos * (height / 144.0);

        //     if (firstPoint) {
        //         firstPoint = false;
        //     } else {
        //         g2d.drawLine((int) prevX, (int) prevY, (int) scaledX, (int) scaledY);
        //     }

        //     prevX = scaledX;
        //     prevY = scaledY;
        // }
        // g2d.setColor(Color.GREEN);
        // prevX = 0; prevY = 0;
        // firstPoint = true;
        // for (double t = 0; t <= 20000; t += 0.1) {
        //     double xpos = t;
        //     updatecoeffecientsplines(spline_x);
        //     double ypos = c0 + c1 * t + c2 * Math.pow(t, 2) + c3 * Math.pow(t, 3) + c4 * Math.pow(t, 4) + c5 * Math.pow(t, 5);
        //     double scaledX = yAxis + xpos * (width / 144.0);
        //     double scaledY = xAxis - ypos * (height / 144.0);

        //     if (firstPoint) {
        //         firstPoint = false;
        //     } else {
        //         g2d.drawLine((int) prevX, (int) prevY, (int) scaledX, (int) scaledY);
        //     }

        //     prevX = scaledX;
        //     prevY = scaledY;
        // }

       
    }


    public void plotPoint(Graphics2D g2d, double x, double y) {
        int xAxis = getHeight() / 2;
        int yAxis = getWidth() / 2;

        double scaledX = x * (getWidth() / 144.0) + yAxis;
        double scaledY = xAxis - y * (getHeight() / 144.0); // Adjust scaling factor here

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) scaledX - 5, (int) scaledY - 5, 10, 10); // Draw a small circle at the point
    }

    public void updatecoeffecientsplines(QSplinePath awesomepantssplines){ //updating c1 to 5 to the spline assigned so that way i dont call it each time and its not annoying and poopy
        this.c0=awesomepantssplines.getcoeff().get(0);
        this.c1=awesomepantssplines.getcoeff().get(1);
        this.c2=awesomepantssplines.getcoeff().get(2);
        this.c3=awesomepantssplines.getcoeff().get(3);
        this.c4=awesomepantssplines.getcoeff().get(4);
        this.c5=awesomepantssplines.getcoeff().get(5);
    }

    public void graphSpline(Graphics2D g2d, QSplinePath spline_x, QSplinePath spline_y, double xAxis, double yAxis, double width, double height){
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
}
