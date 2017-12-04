package neurons;

import java.lang.reflect.Array;
import java.util.Random;

public class Perceptron {

    private double[]weights;
    private Random random;
    private double lt; //wspolczynnik uczenia

    public Perceptron()
    {
        random = new Random();
    }

    public Perceptron(int n)
    {
        this();
        weights = new double[n];

//        for(int i=0;i<weights.length;i++)
//        {
//            weights[i] = random.nextFloat();
//        }
    }

    public Perceptron(int n, double lt)
    {
        this(n);
        this.lt = lt;
    }

    public double sums(double[]inputs)
    {
       float suma = 0;

       if(inputs.length != weights.length)
           return 0;


       for(int i=0;i<inputs.length;i++)
       {
           suma+=(inputs[i]*weights[i]);
       }

        return suma;
    }

    public double funkcjaSkoku(double u)
    {
        System.out.println("u="+u);
        if(u>=0)
            return 1;
        else
            return 0;
    }

    public void adaptujWagizMomentem(final double[]xn, final double pozadana, final double faktyczna, final double wspMomentu, double[]wczWagi)
    {
        if(pozadana == faktyczna){ return; }

        double[]propagacja = new double[weights.length];
//        double[]moment = new double[wagi.length];
//        double[]momentNew = wagi.clone();
        double blad = (pozadana - faktyczna)*lt;
        for(int i=0;i<xn.length;i++)
        {
            propagacja[i]=xn[i]*blad;
        }



//        if(pozadana == 1 && faktyczna ==0)
//        {
            for (int i = 0; i < xn.length; i++) {
//            propagacja[i]=wagi[i]+propagacja[i];
                weights[i]=weights[i]+propagacja[i];
            }
//        }

//        if(pozadana == 0 && faktyczna == 1)
//        {
//            for (int i = 0; i < xn.length; i++) {
////            propagacja[i]=wagi[i]+propagacja[i];
//                wagi[i]=wagi[i]-propagacja[i];
//            }
//        }

        //=======moment==========================//
//        for (int i = 0; i <moment.length ; i++) {
//            moment[i]=wspMomentu*(wagi[i]-wczWagi[i]);
//        }

        //====nowe wagi==========
//        for (int i = 0; i <wagi.length ; i++) {
//            wagi[i]= wagi[i]+moment[i];
//        }
//        wczWagi = momentNew.clone();
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
}
