package neurons.hidden;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Layer {

    private Random random;
    private List<Neuron> neurons;
    private double[][] weights;
    private double[][] weightsOld;
    private int typ = 1; //0 - input, 1 - hidden, 2 - output
    private int sizeNextLayer;
    private double lt;
    private double ltm;
    private double layerError;
    private double bias;
    private Layer nextLayer;
    private Layer prevLayer;

    public Layer() {
        neurons = new ArrayList<>();
        random = new Random();
        weights = new double[1][1];
        weightsOld = new double[1][1];
    }

    public Layer(int typ) {
        this();
        this.typ = typ;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public void randomWeights(int sizeNextLayer) {
        this.sizeNextLayer = sizeNextLayer;
        weights = new double[neurons.size()][sizeNextLayer];
        weightsOld = new double[neurons.size()][sizeNextLayer];

        double min = -1;
        double max = 1;

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = round((min + (max - min) * random.nextDouble()), 2);
            }
        }
    }

    private double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public void setNextNeuronInner() {
        if(nextLayer!=null)
        {
            RealMatrix m1 = MatrixUtils.createRealMatrix(1,neurons.size());
            m1.setRowVector(0,MatrixUtils.createRealVector(getInputs()));
            RealMatrix m2 = MatrixUtils.createRealMatrix(weights);

            double[] outputs = m1.multiply(m2).getRow(0);

            for (int i = 0; i < outputs.length; i++) {
                nextLayer.neurons.get(i).setWe(outputs[i]);
            }
        }
    }

    public double[] getInputs()
    {
        double[] d = new double[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            d[i] = neurons.get(i).getWe();
        }
        return d;
    }

    public double[] getOutputs(Layer layer)
    {
        double[] d = new double[layer.neurons.size()];

        for (int i = 0; i < layer.neurons.size(); i++) {
            d[i] = layer.neurons.get(i).getWy();
        }
        return d;
    }

    public void setErrors()
    {
        if(prevLayer!=null)
        {
            layerError = 0.0;
            for (int i = 0; i < neurons.size(); i++) {
                neurons.get(i).setNewError();
                layerError+=Math.pow(neurons.get(i).getWy()-neurons.get(i).getOczekiwana(),2);
            }
//            error = error/2;
        }
    }


    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public void printWeWy() {
        System.out.print(typ + " ");
        System.out.print("we[");
        neurons.forEach(item -> {
            System.out.print(item.getWe() + ", ");
        });
        System.out.print("] ");

        System.out.print("wy[");
        neurons.forEach(item -> {
            System.out.print(item.getWy() + ", ");
        });
        System.out.println("]");
    }

    public double getLt() {
        return lt;
    }

    public void setLt(double lt) {
        this.lt = lt;
    }

    public void updateWeights() {

        if(nextLayer!=null)
        {
            //pierwsza czesc
            RealMatrix m1 = MatrixUtils.createRealMatrix(nextLayer.neurons.size(),1);
            m1.setColumnVector(0,MatrixUtils.createRealVector(nextLayer.getErrors()));
            RealMatrix m2 = MatrixUtils.createRealMatrix(1,neurons.size());
            m2.setRowVector(0,MatrixUtils.createRealVector(getInputs()));
            m1 = m1.scalarMultiply(lt);
            RealMatrix wnew =  m1.multiply(m2).transpose();

            RealMatrix wold = MatrixUtils.createRealMatrix(weights);

            //momentum
            RealMatrix mwc = MatrixUtils.createRealMatrix(weightsOld);
            weightsOld = weights.clone();
            RealMatrix momentum = wold.subtract(mwc);
            momentum = momentum.scalarMultiply(ltm);

            RealMatrix mtmp = wold.add(wnew);
            mtmp.add(momentum);

            weights = mtmp.getData();
        }

    }

    public double[] getErrors() {
        double[] tmp = new double[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            tmp[i] = neurons.get(i).getError();
        }

        return tmp;
    }

    public void printWeights() {
        for (int i = 0; i < weights.length; i++) {
            System.out.println(Arrays.toString(weights[i]));
        }
        System.out.println("");
    }

    public double getLayerError() {
        return layerError;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public Layer getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public Layer getPrevLayer() {
        return prevLayer;
    }

    public void setPrevLayer(Layer prevLayer) {
        this.prevLayer = prevLayer;
    }

    public double[][] getWeightsOld() {
        return weightsOld;
    }

    public void setWeightsOld(double[][] weightsOld) {
        this.weightsOld = weightsOld;
    }

    public double getLtm() {
        return ltm;
    }

    public void setLtm(double ltm) {
        this.ltm = ltm;
    }
}
