package neurons;

import java.util.ArrayList;
import java.util.List;

public class NetSettings
{
    private int numberOfHiddenLayers = 1;
    private  List<Integer> neuronsNumberOfHiddenLayers;

    public NetSettings()
    {
        neuronsNumberOfHiddenLayers = new ArrayList<>();
        neuronsNumberOfHiddenLayers.add(10);
    }


    public List<Integer> getNeuronsNumberOfHiddenLayers()
    {
        return neuronsNumberOfHiddenLayers;
    }

    public void setNeuronsNumberOfHiddenLayers(List<Integer> neuronsNumberOfHiddenLayers)
    {
        this.neuronsNumberOfHiddenLayers = neuronsNumberOfHiddenLayers;
    }

    public int getNumberOfHiddenLayers()
    {
        return numberOfHiddenLayers;
    }

    public void setNumberOfHiddenLayers(int numberOfHiddenLayers)
    {
        this.numberOfHiddenLayers = numberOfHiddenLayers;
    }
}
