package neurons.hidden;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.util.ArrayList;
import java.util.List;

public class Box {

    private double[] we;
    private double[] wy;
    private double[] oczekiwane;
    private Boolean poprawna;



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
        setPoprawna();
    }

    public double[] getOczekiwane() {
        return oczekiwane;
    }

    public void setOczekiwane(double[] oczekiwane) {
        this.oczekiwane = oczekiwane;
    }

    private void setPoprawna()
    {
        double maxwy = 0;
        double maxocz = 0;
        int iwy = 0;
        int iocz = 0;

        for (int i = 0; i < wy.length; i++) {

            if(wy[i]>maxwy)
            {
                maxwy = wy[i];
                iwy = i;
            }

            if(oczekiwane[i]>maxocz)
            {
                maxocz = oczekiwane[i];
                iocz = i;
            }
        }
        if(iwy == iocz)
        {
            poprawna = true;
        }
        else
            poprawna = false;
    }

    public Boolean isRight() {
        return poprawna;
    }
}
