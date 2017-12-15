package neurons.hidden;

import java.util.ArrayList;
import java.util.List;

public class Box {

    private double[] we;
    private double[] wy;
    private double[] oczekiwane;



    public Box()
    {
        we = new double[1];
        wy = new double[1];
        oczekiwane = new double[1];
    }

    public double[] getWe() {
        return we;
    }

    public void setWe(double[] we) {
        this.we = we;
    }

    public double[] getWy() {
        return wy;
    }

    public void setWy(double[] wy) {
        this.wy = wy;
    }

    public double[] getOczekiwane() {
        return oczekiwane;
    }

    public void setOczekiwane(double[] oczekiwane) {
        this.oczekiwane = oczekiwane;
    }


}
