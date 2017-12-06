package neurons;

import animals.Zwierze;
import javafx.collections.ObservableList;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public class Macierz {
    private final double[][] odpOczekiwane; //101 * 7
    private double[][] inputs; //101 * 17
    private double[][] weights;  // 17 * 7
    private double[][] weightsOlder;   //wagi dla momentum
    private Random random;

    public Macierz() {
        //ustawienie odpowiedzi
        odpOczekiwane = new double[101][7];
        random = new Random();

        for (int i = 0; i < odpOczekiwane.length; i++) {
            for (int j = 0; j < odpOczekiwane[i].length; j++) {
                odpOczekiwane[i][j] = 0;
            }
        }
    }

    public void setInputs(ObservableList<Zwierze> zwierzes) {
        inputs = new double[zwierzes.size()][17];

        int i = 0;
        for (Zwierze item : zwierzes) {
            inputs[i][0] = boolToDouble(item.isSiersc());
            inputs[i][1] = boolToDouble(item.isPiora());
            inputs[i][2] = boolToDouble(item.isJaja());
            inputs[i][3] = boolToDouble(item.isMleko());
            inputs[i][4] = boolToDouble(item.isLatajacy());
            inputs[i][5] = boolToDouble(item.isWodny());
            inputs[i][6] = boolToDouble(item.isDrapieznik());
            inputs[i][7] = boolToDouble(item.isUzebiony());
            inputs[i][8] = boolToDouble(item.isKregoslup());
            inputs[i][9] = boolToDouble(item.isOddycha());
            inputs[i][10] = boolToDouble(item.isJadowity());
            inputs[i][11] = boolToDouble(item.isPletwy());
            inputs[i][12] = boolToDouble(item.isOgon());
            inputs[i][13] = boolToDouble(item.isDomowy());
            inputs[i][14] = boolToDouble(item.isRozmiarKota());
            inputs[i][15] = item.getNogi();
            inputs[i][16] = 1;
            setOdpOczekiwane(i, item.getTyp() - 1);
            i++;
        }
        initializeWeights(); //iniciowanie wag
    }

    private void setOdpOczekiwane(int index, int typ) {
        odpOczekiwane[index][typ] = 1;
    }

    private double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }


    public void initializeWeights() {
        weights = new double[17][7];
        double min = -1;
        double max = 1;

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = round((min + (max - min) * random.nextDouble()), 2);
            }
        }

        weightsOlder = weights.clone();
    }


    public void printInputs() {
        for (int i = 0; i < inputs.length; i++) {
            System.out.println(Arrays.toString(inputs[i]));
        }
    }

    public void printOdpowiedzi() {
        for (int i = 0; i < odpOczekiwane.length; i++) {
            System.out.println(Arrays.toString(odpOczekiwane[i]));
        }
    }

    public void printWags() {
        for (int i = 0; i < weights.length; i++) {
            System.out.println(Arrays.toString(weights[i]));
        }
        System.out.println("");
    }

    private double boolToDouble(Boolean value) {
        if (value)
            return 1.0;
        else
            return 0.0;
    }

    public double[][] multiplyWeightsInputs() {
        RealMatrix x1 = MatrixUtils.createRealMatrix(inputs);
        RealMatrix w1 = MatrixUtils.createRealMatrix(weights);
        return x1.multiply(w1).getData();
    }

    public double[][] activation(double[][] guess) {
        double[][] pp = new double[guess.length][guess[0].length];

        for (int i = 0; i < guess.length; i++) {
            for (int j = 0; j < guess[i].length; j++) {
                if (guess[i][j] >= 0)
                    pp[i][j] = 1;
                else
                    pp[i][j] = 0;
            }
        }
        return pp;

//        return (1/(1+Math.exp(-1*y))); // funkcja sigmoidalna
    }

    public double[][] errors(double[][] respond) {

        RealMatrix m1 = MatrixUtils.createRealMatrix(odpOczekiwane);
        RealMatrix m2 = MatrixUtils.createRealMatrix(respond);
        return m1.subtract(m2).getData();
    }

    public Boolean isEqual(double[][] res) {
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                if (res[i][j] != odpOczekiwane[i][j])
                    return false;
            }
        }
        return true;
    }

    public void updateWeigths(double[][] errors, double lt, double ltm) {
        double[][] tmp = weights.clone();

//           weights[i] += ((inputs[i]*error)+(ltm*(weights[i]-weightsOlder[i])));
        double[][]part1 = multipleErrorsInputsLt(errors, lt);
        double[][]part2 = substractWeightOlderWeight(ltm);

        addToWeights(part1);
        addToWeights(part2);

//        for (int i = 0; i < weights[0].length; i++) {
////           weights[i] += ((inputs[i]*error)+(ltm*(weights[i]-weightsOlder[i])));
//            weights[i] += (inputs[i] * error);
//        }
        weightsOlder = tmp.clone();
    }

    private double[][] substractWeightOlderWeight(double ltm)
    {
        RealMatrix w1 = MatrixUtils.createRealMatrix(weights);
        RealMatrix w2 = MatrixUtils.createRealMatrix(weightsOlder);
        RealMatrix w3 = w1.subtract(w2);

        return w3.scalarMultiply(ltm).getData();
    }

    private double[][] multipleErrorsInputsLt(double[][] errors, double lt) {
        RealMatrix m1 = MatrixUtils.createRealMatrix(errors);
        m1 = m1.scalarMultiply(lt);
        m1 = m1.transpose();
        RealMatrix m2 = MatrixUtils.createRealMatrix(inputs);
        return m1.multiply(m2).transpose().getData();
    }

    private void addToWeights(double[][] m) {

        RealMatrix m1 = MatrixUtils.createRealMatrix(weights);
        RealMatrix m2 = MatrixUtils.createRealMatrix(m);
//
        weights = m1.add(m2).getData();
    }

}
