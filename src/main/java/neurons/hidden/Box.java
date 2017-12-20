package neurons.hidden;

import animals.Rodzaj;
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
    private Rodzaj rodzajOczekiwany;
    private Rodzaj rodzajWynikowy;
    private int id;



    public Box()
    {
        we = new double[1];
        wy = new double[1];
        oczekiwane = new double[1];
    }

    public Box(int i)
    {
        this();
        this.id = i;
    }

    public int getId()
    {
        return id;
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

        rodzajOczekiwany = Rodzaj.getKlase(iocz+1);
        rodzajWynikowy = Rodzaj.getKlase(iwy+1);
    }

    public Boolean isRight() {
        return poprawna;
    }

    public Rodzaj getRodzajOczekiwany() {
        return rodzajOczekiwany;
    }

    public Rodzaj getRodzajWynikowy() {
        return rodzajWynikowy;
    }
}
