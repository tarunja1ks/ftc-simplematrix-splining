package ftc.simplematrix.splining;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

import org.ejml.equation.Symbol;
import org.ejml.simple.SimpleMatrix;
import  ftc.simplematrix.splining.Vector2;
public class QSplinePath {
    public static SimpleMatrix TCoeff; // The coeffecients for all of the polynomials
    public static SimpleMatrix IF;// Initial and Final
    public  SimpleMatrix Coeff;
    public double t0, tf;
    public double q0,qf;
    public QSplinePath(double t0, double q0, double v0, double a0, double tf, double qf, double vf, double af){// initial time, initial poisiton, initial velocity, initial accel, final time, final position, final velcity, final accel
        double[][]tcoeff= new double[6][6];
        for(int j = 0; j<6; j++){
            tcoeff[0][j]=Q(t0)[j];
        }
        for(int j = 0; j<6; j++){
            tcoeff[1][j]=Qprime(t0)[j];
        }
        for(int j = 0; j<6; j++){
            tcoeff[2][j]=Qdoubleprime(t0)[j];
        }
        for(int j = 0; j<6; j++){
            tcoeff[3][j]=Q(tf)[j];
        }
        for(int j = 0; j<6; j++){
            tcoeff[4][j]=Qprime(tf)[j];
        }
        for(int j = 0; j<6; j++){
            tcoeff[5][j]=Qdoubleprime(tf)[j];
        }
        TCoeff=new SimpleMatrix(tcoeff);
        IF=new SimpleMatrix(new double[]{q0,v0,a0,qf,vf,af});
        this.t0=t0;
        this.tf=tf;
        this.q0=q0;
        this.qf=qf;
    }

    /**
     * Returns the x and y value for some value of t (plugs in a value into our function)
     * @param t The t-value to plug in
     * @return An x and y value.
     */

    public double[] getTimes(){
        return (new double[]{t0,tf});
    }
    public Vector2 getPoint(double t) {
        double x =1 ;
        double y = 1;
        return new Vector2(x, y);
    }

    /**
     * Generates an array that has some points, equally spaced, along the function.
     * @param points The length of the array.
     * @return An array of points.
     */
    public Vector2[] generateArr(int points) {
        // assuming that the spline goes from t=0 to t=1, this should be the step
        // idk if this is right though
        Vector2[] retVal = new Vector2[points];
        double step = 1 / (double) points;
        for (int i = 0; i < points; i++) {
            retVal[i] = this.getPoint(step * i);
        }
        return retVal;
    }

    public static double[] Q(double t){
        return new double[]{1,t,t*t,t*t*t,t*t*t*t,t*t*t*t*t};// math.pow is for nerds
    }
    public static double[] Qprime(double t){
        return new double[]{0,1,2*t,3*t*t,4*t*t*t,5*t*t*t*t};
    }

    public static double[] Qdoubleprime(double t){
        return new double[]{0,0,2,6*t,12*t*t,20*t*t*t};
    }

    public SimpleMatrix makeSpline(){
        Coeff=(TCoeff.invert()).mult(IF);
        return Coeff;
    }

    public SimpleMatrix getcoeff(){
        return Coeff;
    }
}

