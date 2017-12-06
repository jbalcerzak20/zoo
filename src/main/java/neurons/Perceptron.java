package neurons;

import animals.Zwierze;
import javafx.collections.ObservableList;

import java.util.Arrays;

public class Perceptron extends Macierz {

    private double[] weights;    //wagi
    private double[] weightsOlder;   //wagi dla momentum
    private double lt; //wspolczynnik uczenia
    private double ltm;
    private int lpd;
    private int maxT; //maksymalna liczba iteracji
    private double eps; //
    private ObservableList<Zwierze> zwierzes;

    public Perceptron() {
        lpd = 0;
        maxT = 0;
    }

    public Perceptron(int n) {
        this();
        weights = new double[n];
        weightsOlder = new double[n];


        for (int i = 0; i < weightsOlder.length; i++) {
            weightsOlder[i] = 0;
        }
    }

    public Perceptron(int n, double lt) {
        this(n);
        this.lt = lt;
    }

    public Perceptron(int n, double lt, double ltm) {
        this(n, lt);
        this.ltm = ltm;
    }

    public double sums(double[] inputs) {
        float suma = 0;

        if (inputs.length != weights.length)
            return 0;


        for (int i = 0; i < inputs.length; i++) {
            suma += (inputs[i] * weights[i]);
        }

        return suma;
    }


    public void teach() {
        int pt = 0;
        double[][] res;
        while (true) {
            res = activation(multiplyWeightsInputs()); //odpowiedz siec

            if(isEqual(res))
                break;

            if(pt>1000)
                break;

            double[][] errors = errors(res);
            updateWeigths(errors,lt,ltm);
            pt++;
        }
        for (int i = 0; i < res.length; i++) {
            System.out.println(Arrays.toString(res[i]));
        }
        System.out.println("Numer iteracji: "+pt);
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getLt() {
        return lt;
    }

    public void setLt(double lt) {
        this.lt = lt;
    }

    public double getLtm() {
        return ltm;
    }

    public void setLtm(double ltm) {
        this.ltm = ltm;
    }

    public int getMaxT() {
        return maxT;
    }

    public void setMaxT(int maxT) {
        this.maxT = maxT;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public ObservableList<Zwierze> getZwierzes() {
        return zwierzes;
    }

    public void setZwierzes(ObservableList<Zwierze> zwierzes) {
        this.zwierzes = zwierzes;
        super.setInputs(zwierzes);
    }
}
