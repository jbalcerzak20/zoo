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

    public void setNewError()
    {
        error = oczekiwana - wy;
    }

    public void setNewError(Layer layer)
    {
        if(layer.getNextLayer()!=null) {
            double suma = 0;
            Layer next = layer.getNextLayer();
            double w[][] = layer.getWeights();
            int index = layer.getNeurons().indexOf(this);

            for (int i = 0; i < next.getNeurons().size(); i++) {
                suma+=w[index][i]*next.getNeurons().get(i).getError();
            }

            error = wy * (1 - wy) * suma;
        }
        else {
            error = wy * (1 - wy) * (oczekiwana - wy);
        }
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
