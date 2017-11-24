package neurons;

import java.lang.reflect.Array;

public class Perceptron {


    public Perceptron()
    {

    }

    public double sumujNeurony(double[]xn, double[]wagi)
    {
        double suma = 0.0;

       if(xn.length != wagi.length)
           return -1000;

       for(int i=0;i<xn.length;i++)
       {
           suma+=(xn[i]*wagi[i]);
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

    public void adaptujWagizMomentem(final double[]xn, double[]wagi, final double wspUczenia, final double pozadana, final double faktyczna, final double wspMomentu, double[]wczWagi)
    {
        if(pozadana == faktyczna){ return; }

        double[]propagacja = new double[wagi.length];
//        double[]moment = new double[wagi.length];
//        double[]momentNew = wagi.clone();
        double blad = (pozadana - faktyczna)*wspUczenia;
        for(int i=0;i<xn.length;i++)
        {
            propagacja[i]=xn[i]*blad;
        }



//        if(pozadana == 1 && faktyczna ==0)
//        {
            for (int i = 0; i < xn.length; i++) {
//            propagacja[i]=wagi[i]+propagacja[i];
                wagi[i]=wagi[i]+propagacja[i];
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
}
