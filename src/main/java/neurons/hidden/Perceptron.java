package neurons.hidden;

import animals.Zwierze;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perceptron {

    private List<Layer> layers;
    private List<Double> summaryErrors;
    private ObservableList<Zwierze> zwierzes;
    private List<Box> boxs;
    private int iloscDanychUczacych;
    private int maxIteration;
    private double epsilon;

    public Perceptron() {
        layers = new ArrayList<>();
        summaryErrors = new ArrayList<>();
        boxs = new ArrayList<>();

        Layer we = new Layer(0);
        Layer wy = new Layer(2);

        layers.add(we);
        layers.add(wy);

        layers.get(0).setLt(0.2);
        layers.get(1).setLt(0.2);

        layers.get(0).setNextLayer(wy);
        layers.get(1).setPrevLayer(we);

    }

    public double normalize(double d) {
        return ((d) / (8));
    }

    private void ustawWarstwy() {
        Layer l1 = new Layer(0);
        Layer l2 = new Layer(1);
        Layer l3 = new Layer(2);

        l1.getNeurons().add(new Neuron(0.05));
        l1.getNeurons().add(new Neuron(0.1));

        l2.getNeurons().add(new Neuron());
        l2.getNeurons().add(new Neuron());

        l3.getNeurons().add(new Neuron(0, 0.01));
        l3.getNeurons().add(new Neuron(0, 0.99));

        layers.add(l1);
        layers.add(l2);
        layers.add(l3);

        for (int i = 0; i < layers.size() - 1; i++) {
//            layers.get(i).randomWeights(layers.get(i+1).getNeurons().size());
            layers.get(i).setLt(0.5);
        }

        double[][] w1 = new double[2][2];
        w1[0][0] = 0.15;
        w1[0][1] = 0.20;
        w1[1][0] = 0.25;
        w1[1][1] = 0.30;
        layers.get(0).setWeights(w1);

        double[][] w2 = new double[2][2];
        w2[0][0] = 0.40;
        w2[0][1] = 0.45;
        w2[1][0] = 0.50;
        w2[1][1] = 0.55;
        layers.get(1).setWeights(w2);

        layers.get(0).setBias(0.35);
        layers.get(1).setBias(0.60);

        layers.get(0).setNextLayer(l2);

        layers.get(1).setPrevLayer(l1);
        layers.get(1).setNextLayer(l3);

        layers.get(2).setPrevLayer(l2);

    }

    public void setLayers(double[] input, double[] target) {


        if (layers.get(0).getNeurons().isEmpty()) {
            for (int i = 0; i < input.length; i++) {
                layers.get(0).getNeurons().add(new Neuron(input[i]));
            }

            for (int i = 0; i < target.length; i++) {
                layers.get(1).getNeurons().add(new Neuron(0, target[i]));
            }

            layers.get(0).randomWeights(7);
        } else {
            for (int i = 0; i < input.length; i++) {
                layers.get(0).getNeurons().get(i).setWe(input[i]);
            }

            for (int i = 0; i < target.length; i++) {
                layers.get(1).getNeurons().get(i).setOczekiwana(target[i]);
            }
        }
    }

    public void setInputVector(int i, double[] inputs, double[] target) {

        Zwierze item = zwierzes.get(i);
        inputs[0] = boolToDouble(item.isSiersc());
        inputs[1] = boolToDouble(item.isPiora());
        inputs[2] = boolToDouble(item.isJaja());
        inputs[3] = boolToDouble(item.isMleko());
        inputs[4] = boolToDouble(item.isLatajacy());
        inputs[5] = boolToDouble(item.isWodny());
        inputs[6] = boolToDouble(item.isDrapieznik());
        inputs[7] = boolToDouble(item.isUzebiony());
        inputs[8] = boolToDouble(item.isKregoslup());
        inputs[9] = boolToDouble(item.isOddycha());
        inputs[10] = boolToDouble(item.isJadowity());
        inputs[11] = boolToDouble(item.isPletwy());
        inputs[12] = boolToDouble(item.isOgon());
        inputs[13] = boolToDouble(item.isDomowy());
        inputs[14] = boolToDouble(item.isRozmiarKota());
        inputs[15] = normalize(item.getNogi());

        target[item.getTyp() - 1] = 1;
    }


    private double boolToDouble(Boolean value) {
        if (value)
            return 1.0;
        else
            return 0.0;
    }

    private void setArray(double[] array, double d) {
        for (int i = 0; i < array.length; i++) {
            array[i] = d;
        }
    }

    public void teach() {

        initBoxs();

        double[] wejscie = new double[16];
        double[] target = new double[7];

        int t = 0;

        while (t < maxIteration) {

            double suma = 0;
            for (int x = 0; x < iloscDanychUczacych; x++) {

                setArray(target, 0);

                setInputVector(x, wejscie, target);
                setLayers(wejscie, target);

                for (int i = 0; i < layers.size(); i++) {
                    layers.get(i).setNextNeuronInner();
                    layers.get(i).setErrors();
                }
                suma += layers.get(1).getLayerError();
                boxs.get(x).setWy(layers.get(1).getOutputs(layers.get(1)));

                for (int i = 0; i < layers.size(); i++) {
                    layers.get(i).updateWeights();
                }

            }
            summaryErrors.add(suma / 2);

            if (epsilonStop())
                break;
//            System.out.println(summaryErrors.get(summaryErrors.size()-1));

            t++;
        }
    }

    private Boolean epsilonStop() {


        if (summaryErrors.size() > 1) {
            double p1 = summaryErrors.get(summaryErrors.size() - 1) / 1000;
            double p2 = summaryErrors.get(summaryErrors.size() - 2) / 1000;

            if (Math.abs(p1 - p2) <= epsilon)
                return true;
        }

        return false;
    }


    private void initBoxs() {
        double[] wejscie = new double[16];
        double[] target = new double[7];


        for (int i = 0; i < iloscDanychUczacych; i++) {
            Box b = new Box();

            setArray(target, 0);
            setInputVector(i, wejscie, target);
            b.setOczekiwane(target.clone());
            b.setWe(wejscie.clone());
            boxs.add(b);
        }
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public ObservableList<Zwierze> getZwierzes() {
        return zwierzes;
    }

    public void setZwierzes(ObservableList<Zwierze> zwierzes) {
        this.zwierzes = zwierzes;
    }

    public int getIloscDanychUczacych() {
        return iloscDanychUczacych;
    }

    public void setIloscDanychUczacych(int iloscDanychUczacych) {
        this.iloscDanychUczacych = iloscDanychUczacych;
    }

    public int getMaxIteration() {
        return maxIteration;
    }

    public void setMaxIteration(int maxIteration) {
        this.maxIteration = maxIteration;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
