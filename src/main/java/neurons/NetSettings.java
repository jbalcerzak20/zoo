package neurons;

import java.util.ArrayList;
import java.util.List;

public class NetSettings
{
    private int numberOfHiddenLayers = 1;
    private  List<Integer> neurons;

    public NetSettings()
    {
        neurons = new ArrayList<>();
        neurons.add(10);
    }


    public List<Integer> getNeurons()
    {
        return neurons;
    }

    public void setNeurons(List<Integer> neurons)
    {
        this.neurons = neurons;
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
