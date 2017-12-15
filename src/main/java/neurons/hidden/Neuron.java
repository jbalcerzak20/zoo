package neurons.hidden;

import java.math.BigDecimal;
import java.util.Arrays;

public class Neuron {

    private double we;
    private double wy;
    private double oczekiwana;
    private double error;

    public Neuron() {
        we = 0.0;
        wy = 0.0;
        oczekiwana = 0.0;
    }

    public Neuron(double we) {
        this();
        this.we = we;
    }

    public Neuron(double we, double oczekiwana) {
        this(we);
        this.oczekiwana = oczekiwana;
    }

    public double getWe() {
        return we;
    }

    public void setWe(double we) {
        this.we = we;
        activation(this.we);
    }

    public void activation(double y) {
        wy = (1 / (1 + Math.exp(-1 * y)));
    }

    public double getWy() {
        return wy;
    }

    public void setWy(double wy) {
        this.wy = wy;
    }

    public void obliczError(Layer layer, double[][] weights, int n) {
//        error = (Math.pow((oczekiwana - wy),2))/2;
        if (layer == null) {
            error = wy * (1 - wy) * (oczekiwana - wy);
        } else {
            if (layer.getTyp() == 2) {
                double suma = 0;
                double[] err = layer.getErrors();

                for (int i = 0; i < weights[n].length; i++) {
                    suma += (weights[n][i] * err[i]);
                }
                error = wy * (1 - wy) * suma;
            }
            if(layer.getTyp()==1)
            {
                double suma = 0;
                double[] err = layer.getErrors();

                for (int i = 0; i < weights[n].length; i++) {
                    suma+=(weights[n][i]*err[i]);
                }
                error = wy*(1-wy)*suma;
            }
        }
    }

    public void setNewError()
    {
//        error = (Math.pow((wy-oczekiwana),2)/2);
        error = oczekiwana - wy;
    }

    public double getOczekiwana() {
        return oczekiwana;
    }

    public void setOczekiwana(double oczekiwana) {
        this.oczekiwana = oczekiwana;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    private double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
