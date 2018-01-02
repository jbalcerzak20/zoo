package neurons.hidden;

import animals.Zwierze;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public void putToLayers(double[] input, double[] target) {
        Layer first = layers.get(0);
        Layer last = layers.get(layers.size() - 1);

        for (int i = 0; i < input.length; i++) {
            first.getNeurons().get(i).setWe(input[i]);
        }

        for (int i = 0; i < target.length; i++) {
            last.getNeurons().get(i).setOczekiwana(target[i]);
        }

    }


    private void putCorrectTarget(double[] target, Zwierze zwierze)
    {
        target[zwierze.getTyp() - 1] = 1;
    }

    private double[] constructInputVectorOf(Zwierze zwierze)
    {
        double[] input = new double[16];
        input[0] = boolToDouble(zwierze.isSiersc());
        input[1] = boolToDouble(zwierze.isPiora());
        input[2] = boolToDouble(zwierze.isJaja());
        input[3] = boolToDouble(zwierze.isMleko());
        input[4] = boolToDouble(zwierze.isLatajacy());
        input[5] = boolToDouble(zwierze.isWodny());
        input[6] = boolToDouble(zwierze.isDrapieznik());
        input[7] = boolToDouble(zwierze.isUzebiony());
        input[8] = boolToDouble(zwierze.isKregoslup());
        input[9] = boolToDouble(zwierze.isOddycha());
        input[10] = boolToDouble(zwierze.isJadowity());
        input[11] = boolToDouble(zwierze.isPletwy());
        input[12] = boolToDouble(zwierze.isOgon());
        input[13] = boolToDouble(zwierze.isDomowy());
        input[14] = boolToDouble(zwierze.isRozmiarKota());
        input[15] = normalize(zwierze.getNogi());

        return input;
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
        double[] wejscie;
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
                Box b = new Box(i);

                setArray(target, 0);
                wejscie = constructInputVectorOf(zwierzes.get(i));
                putCorrectTarget(target, zwierzes.get(i));
//                wejscie = setInputVector(i, target);
                b.setOczekiwane(target.clone());
                b.setWe(wejscie.clone());
                daneUczace.add(b);
            }

            if ((i >= iloscDanychUczacych) && (i < (iloscDanychUczacych + iloscDanychTestowych))) {
                Box b = new Box(i);

                setArray(target, 0);
//                setInputVector(i, wejscie, target);
                wejscie = constructInputVectorOf(zwierzes.get(i));
                putCorrectTarget(target, zwierzes.get(i));
                b.setOczekiwane(target.clone());
                b.setWe(wejscie.clone());
                daneTestowe.add(b);
            }

            if (i >= (iloscDanychUczacych + iloscDanychTestowych)) {
                Box b = new Box(i);

                setArray(target, 0);
//                setInputVector(i, wejscie, target);
                wejscie = constructInputVectorOf(zwierzes.get(i));
                putCorrectTarget(target, zwierzes.get(i));
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
                putToLayers(daneUczace.get(x).getWe(), daneUczace.get(x).getOczekiwane());

                passRecordThroughNet();

                suma = coutErrors(suma);

                daneUczace.get(x).setWy(layers.get(layerCount - 1).getOutputs(layers.get(layerCount - 1)));

                for (int i = 0; i < layers.size(); i++) {
                    layers.get(i).updateWeights();
                }
            }
            summaryErrors.add(suma / 2);

            if (verificateStop()) {
//                System.out.println("Verify stop");
//                break;
            }

            if (epsilonStop()) {
//                System.out.println("Epsilon stop");
                break;
            }
//            System.out.println(summaryErrors.get(summaryErrors.size() - 1));
//            System.out.println(t+" : "+summaryErrors.get(summaryErrors.size() - 1));
            t++;
        }

//        System.out.println(t);
    }

    public double[] guess(Zwierze animal)
    {
        double[] input = constructInputVectorOf(animal);
        double[] fakeTargets = new double[7];
        Arrays.fill(fakeTargets, 0);
        putToLayers(input, fakeTargets);
        passRecordThroughNet();
        return getOutputs();
    }

    public void tests() {
        double suma = 0;
        for (int x = 0; x < iloscDanychTestowych; x++) {
            putToLayers(daneTestowe.get(x).getWe(), daneTestowe.get(x).getWy());
            passRecordThroughNet();
            suma = coutErrors(suma);
            daneTestowe.get(x).setWy(getOutputs());
        }

        int poprawne = 0;
        for (Box item : daneTestowe) {
            //            System.out.println("ocz= "+Arrays.toString(item.getOczekiwane()));
//            System.out.println(Arrays.toString(item.getWy()));
//            System.out.println(item.isRight());
//            System.out.println("");
            if (item.isRight()) {
                poprawne += 1;
            }
        }

//        System.out.println("Ilosc poprawnie snormklasyfikowanych danych testowych: " + poprawne + "/" + daneTestowe.size());
    }

    private double[] getOutputs()
    {
        return layers.get(layers.size() - 1).getOutputs(layers.get(layers.size() - 1));
    }

    private void passRecordThroughNet()
    {
        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).setNextNeuronInner();
        }
    }

    private double coutErrors(double suma)
    {
        for (int i = layers.size() - 1; i >= 0; i--) {
            layers.get(i).setErrors();
            suma += layers.get(i).getLayerError();
        }
        return suma;
    }

    public List<Double> getSummaryErrors() {
        return summaryErrors;
    }

    public void setSummaryErrors(List<Double> summaryErrors) {
        this.summaryErrors = summaryErrors;
    }

    public List<Double> getSummaryErrorsVerify() {
        return summaryErrorsVerify;
    }

    public void setSummaryErrorsVerify(List<Double> summaryErrorsVerify) {
        this.summaryErrorsVerify = summaryErrorsVerify;
    }

    public int getIloscUczacychPoprawnych() {
        int poprawne = 0;
        for (Box item : daneUczace) {
            if (item.isRight()) {
                poprawne += 1;
            }
        }
        return poprawne;
    }

    public int getIloscTestowychPoprawnych() {
        int poprawne = 0;
        for (Box item : daneTestowe) {
            if (item.isRight()) {
                poprawne += 1;
            }
        }
        return poprawne;
    }

    public int getIloscWeryfikacyjnychPoprawnych() {
        int poprawne = 0;
        for (Box item : daneWeryfikacyjne) {
            if (item.isRight()) {
                poprawne += 1;
            }
        }
        return poprawne;
    }

    public Boolean verificateStop() {
        double suma = 0;
        for (int x = 0; x < iloscDanychWeryfikacyjnych; x++) {

            putToLayers(daneWeryfikacyjne.get(x).getWe(), daneWeryfikacyjne.get(x).getWy());

            for (int i = 0; i < layers.size(); i++) {
                layers.get(i).setNextNeuronInner();
            }
            for (int i = layers.size() - 1; i >= 0; i--) {
                layers.get(i).setErrors();
                suma += layers.get(i).getLayerError();
            }
            daneWeryfikacyjne.get(x).setWy(layers.get(layers.size() - 1).getOutputs(layers.get(layers.size() - 1)));
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
        Collections.shuffle(zwierzes);
        Collections.shuffle(zwierzes);
        Collections.shuffle(zwierzes);
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

    public String getUczaceSummary() {
        StringBuilder builder = new StringBuilder();

        for (Box box : daneUczace) {
            String s = "";

            if (!box.isRight()) {
                s = Arrays.toString(box.getOczekiwane()) + " " + Arrays.toString(box.getWy());
            }

            builder.append(zwierzes.get(box.getId()).getNazwa() + " " + box.getRodzajOczekiwany().getNazwa() + " - " + box.getRodzajWynikowy().getNazwa() + " " + s + " " + "\n");
        }

        return builder.toString();
    }

    public String getTestoweSummary() {
        StringBuilder builder = new StringBuilder();

        for (Box box : daneTestowe) {
            String s = "";

            if (!box.isRight()) {
                s = Arrays.toString(box.getOczekiwane()) + " " + Arrays.toString(box.getWy());
            }
            builder.append(zwierzes.get(box.getId()).getNazwa() + " " + box.getRodzajOczekiwany().getNazwa() + " - " + box.getRodzajWynikowy().getNazwa() + " " + s + " " + "\n");
        }

        return builder.toString();
    }
}
