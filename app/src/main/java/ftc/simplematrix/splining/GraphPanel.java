package ftc.simplematrix.splining;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class GraphPanel extends JPanel {
    private double c0, c1, c2, c3, c4, c5;
    private double x1,x2,y1,y2;

    public GraphPanel(double c0, double c1, double c2, double c3, double c4, double c5, double x1, double x2, double y1, double y2) {
        this.c0 = c0;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

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
        for (int i = -width / 2; i <= width / 2; i += 50) {
            int x = yAxis + i;
            g2d.drawLine(x, xAxis - 5, x, xAxis + 5);
            if (i != 0) {
                g2d.drawString(String.valueOf(i / 100.0), x - 10, xAxis + 20);
            }
        }

        for (int i = -height / 2; i <= height / 2; i += 50) {
            int y = xAxis - i;
            g2d.drawLine(yAxis - 5, y, yAxis + 5, y);
            if (i != 0) {
                g2d.drawString(String.valueOf(i / 100.0), yAxis + 10, y + 5);
            }
        }

        // Draw the polynomial q(t) = c0 + c1*t + c2*t^2 + c3*t^3 + c4*t^4 + c5*t^5
        g2d.setColor(Color.RED);
        double prevX = 0, prevY = 0;
        boolean firstPoint = true;

        for (double x = -yAxis; x < yAxis; x += 0.1) { // Increase the step for smoothness
            double t = x / 100.0;
            double y = c0 + c1 * t + c2 * Math.pow(t, 2) + c3 * Math.pow(t, 3) + c4 * Math.pow(t, 4) + c5 * Math.pow(t, 5);
            double scaledX = x + yAxis;
            double scaledY = xAxis - y * 100; // Adjust scaling factor here

            if (firstPoint) {
                firstPoint = false;
            } else {
                g2d.drawLine((int) prevX, (int) prevY, (int) scaledX, (int) scaledY);
            }

            prevX = scaledX;
            prevY = scaledY;
        }
        plotPoint(g2d, x1, y1);
        plotPoint(g2d, x2, y2);
    }


    public void plotPoint(Graphics2D g2d, double x, double y) {
        int xAxis = getHeight() / 2;
        int yAxis = getWidth() / 2;

        double scaledX = x * 100 + yAxis;
        double scaledY = xAxis - y * 100; // Adjust scaling factor here

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) scaledX - 5, (int) scaledY - 5, 10, 10); // Draw a small circle at the point
    }
}