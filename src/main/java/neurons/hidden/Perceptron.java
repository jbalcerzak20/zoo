package neurons.hidden;

import animals.Zwierze;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perceptron {

    private List<Layer> layers;
    private List<Double> summaryErrors;
    private List<Double> summaryErrorsVerify;
    private ObservableList<Zwierze> zwierzes;
    private List<Box> daneUczace;
    private List<Box> daneTestowe;
    private List<Box> daneWeryfikacyjne;
    private int iloscDanychUczacych;
    private int iloscDanychTestowych;
    private int iloscDanychWeryfikacyjnych;
    private int maxIteration;
    private double epsilon;
    private double lt;
    private double ltm;

    public Perceptron() {
        layers = new ArrayList<>();
        summaryErrors = new ArrayList<>();
        summaryErrorsVerify = new ArrayList<>();
        daneUczace = new ArrayList<>();
        daneTestowe = new ArrayList<>();
        daneWeryfikacyjne = new ArrayList<>();
    }

    public void setInitLayers(int n) {
        if (n == 2) {
            Layer we = new Layer(0);
            Layer wy = new Layer(2);

            layers.add(we);
            layers.add(wy);

            layers.get(0).setNextLayer(wy);
            layers.get(1).setPrevLayer(we);
        }
        if (n > 2) {
            Layer we = new Layer(0);
            layers.add(we);

            for (int i = 0; i < n - 2; i++) {
                layers.add(new Layer(1));
            }

            Layer wy = new Layer(2);
            layers.add(wy);

            Layer next = null;
            Layer prev = null;

            for (int i = 0; i < layers.size(); i++) {

                if (i < layers.size() - 1)
                    next = layers.get(i + 1);

                if (i > 0)
                    prev = layers.get(i - 1);

                layers.get(i).setNextLayer(next);
                layers.get(i).setPrevLayer(prev);
                next = null;
                prev = null;
            }

        }
    }

    public void setInitNeurons(int[] neuronCouns) {
        for (int i = 0; i < neuronCouns.length; i++) {
            Layer l = layers.get(i);
            int n = neuronCouns[i];
            for (int j = 0; j < n; j++) {
                l.getNeurons().add(new Neuron());
            }
        }

        for (int i = 0; i < layers.size() - 1; i++) {
            layers.get(i).randomWeights(layers.get(i + 1).getNeurons().size());
        }
    }

    public double normalize(double d) {
        return ((d) / (8));
    }

    public void setLayers(double[] input, double[] target) {

        Layer first = layers.get(0);
        Layer last = layers.get(layers.size() - 1);

        for (int i = 0; i < input.length; i++) {
            first.getNeurons().get(i).setWe(input[i]);
        }

        for (int i = 0; i < target.length; i++) {
            last.getNeurons().get(i).setOczekiwana(target[i]);
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

    public void podzielDane(int uczace, int testowe, int weryfikacyjne) {
        double[] wejscie = new double[16];
        double[] target = new double[7];


        if ((uczace + testowe + weryfikacyjne) > zwierzes.size()) {
            new Exception("Suma wszystkich wartosci nie moze byc wieksza niz calkowity zbiór danych");
            return;
        }


        setIloscDanychUczacych(uczace);
        setIloscDanychTestowych(testowe);
        setIloscDanychWeryfikacyjnych(weryfikacyjne);

        for (int i = 0; i < (iloscDanychUczacych + iloscDanychTestowych + iloscDanychWeryfikacyjnych); i++) {

            if (i < iloscDanychUczacych) {
                Box b = new Box();

                setArray(target, 0);
                setInputVector(i, wejscie, target);
                b.setOczekiwane(target.clone());
                b.setWe(wejscie.clone());
                daneUczace.add(b);
            }

            if ((i >= iloscDanychUczacych) && (i < (iloscDanychUczacych + iloscDanychTestowych))) {
                Box b = new Box();

                setArray(target, 0);
                setInputVector(i, wejscie, target);
                b.setOczekiwane(target.clone());
                b.setWe(wejscie.clone());
                daneTestowe.add(b);
            }

            if (i >= (iloscDanychUczacych + iloscDanychTestowych)) {
                Box b = new Box();

                setArray(target, 0);
                setInputVector(i, wejscie, target);
                b.setOczekiwane(target.clone());
                b.setWe(wejscie.clone());
                daneWeryfikacyjne.add(b);
            }
        }
    }

    private void initWsp() {
        layers.forEach(item -> {
            item.setLtm(ltm);
            item.setLt(lt);
        });
    }

    public void teach() {

        initWsp();
        int layerCount = layers.size();

        int t = 0;
        while (t < maxIteration) {
            double suma = 0;
            for (int x = 0; x < iloscDanychUczacych; x++) {
                setLayers(daneUczace.get(x).getWe(), daneUczace.get(x).getOczekiwane());

                for (int i = 0; i < layers.size(); i++) {
                    layers.get(i).setNextNeuronInner();
                }

                for (int i = layers.size() - 1; i >= 0; i--) {
                    layers.get(i).setErrors();
                    suma += layers.get(i).getLayerError();
                }
                daneUczace.get(x).setWy(layers.get(layerCount - 1).getOutputs(layers.get(layerCount - 1)));

                for (int i = 0; i < layers.size(); i++) {
                    layers.get(i).updateWeights();
                }
            }
            summaryErrors.add(suma / 2);

//            if (verificateStop())
//                break;
//
//            if (epsilonStop())
//                break;
            System.out.println(summaryErrors.get(summaryErrors.size() - 1));
            t++;
        }

        System.out.println(t);
    }

    public void tests() {


        double suma = 0;
        for (int x = 0; x < iloscDanychTestowych; x++) {

            setLayers(daneTestowe.get(x).getWe(), daneTestowe.get(x).getWy());

            for (int i = 0; i < layers.size(); i++) {
                layers.get(i).setNextNeuronInner();
            }
            for (int i = layers.size() - 1; i >= 0; i--) {
                layers.get(i).setErrors();
                suma += layers.get(i).getLayerError();
            }
            daneTestowe.get(x).setWy(layers.get(layers.size() - 1).getOutputs(layers.get(layers.size() - 1)));
        }

        daneTestowe.forEach(item->{
            System.out.println("ocz= "+Arrays.toString(item.getOczekiwane()));
            System.out.println(Arrays.toString(item.getWy()));
            System.out.println("");

        });
    }

    public Boolean verificateStop() {
        double suma = 0;
        for (int x = 0; x < iloscDanychWeryfikacyjnych; x++) {

            setLayers(daneWeryfikacyjne.get(x).getWe(), daneWeryfikacyjne.get(x).getWy());

            for (int i = 0; i < layers.size(); i++) {
                layers.get(i).setNextNeuronInner();
                layers.get(i).setErrors();
            }
            suma += layers.get(1).getLayerError();
            daneWeryfikacyjne.get(x).setWy(layers.get(1).getOutputs(layers.get(1)));
        }
        summaryErrorsVerify.add(suma);

        if (summaryErrorsVerify.size() > 1) {
            double t1 = summaryErrorsVerify.get(summaryErrorsVerify.size() - 1);
            double t2 = summaryErrorsVerify.get(summaryErrorsVerify.size() - 2);

            if (t1 > t2)
                return true;
        }
//        System.out.println(suma);
        return false;
    }

    private Boolean epsilonStop() {
        if (summaryErrors.size() > 1) {
            double p1 = summaryErrors.get(summaryErrors.size() - 1);
            double p2 = summaryErrors.get(summaryErrors.size() - 2);

            if (Math.abs(p1 - p2) < epsilon)
                return true;
        }
        return false;
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

    public int getIloscDanychTestowych() {
        return iloscDanychTestowych;
    }

    public void setIloscDanychTestowych(int iloscDanychTestowych) {
        this.iloscDanychTestowych = iloscDanychTestowych;
    }

    public int getIloscDanychWeryfikacyjnych() {
        return iloscDanychWeryfikacyjnych;
    }

    public void setIloscDanychWeryfikacyjnych(int iloscDanychWeryfikacyjnych) {
        this.iloscDanychWeryfikacyjnych = iloscDanychWeryfikacyjnych;
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
}
