package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import neurons.NetSettings;

import java.util.ArrayList;
import java.util.List;

public class NetSettingsTemplateController
{
    @FXML
    private VBox hiddenLayerSettingVBox;

    @FXML
    private Slider numberOfLayerSlider;

    @FXML
    private Slider firstLayerSlider;

    private NetSettings actualNetSettings;


    public NetSettingsTemplateController(NetSettings netSettings)
    {
        this.actualNetSettings = netSettings;
    }

    public void initialize()
    {
        numberOfLayerSlider.valueProperty().addListener((new ChangeListener<Number>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                    {
                        hiddenLayerSettingVBox.getChildren().clear();
                        for (int layerIndex = 0; layerIndex < newValue.intValue() - 1; layerIndex++)
                        {
                            hiddenLayerSettingVBox.getChildren().add(createHBoxWithLabelAndSliderFor(layerIndex));
                        }
                        hiddenLayerSettingVBox.getScene().getWindow().sizeToScene();
                    }
                })
        );
    }


    public void loadActualSettings()
    {
        loadNumberOfHiddenLayers();
        loadNeouronsNumberOfHiddenLayer();
    }



    public int getNumberOfHiddenLayers()
    {
        return (int) numberOfLayerSlider.getValue();
    }


    public List<Integer> getNumberOfNeuronsPerLayer()
    {
        List<Integer> numberOfNeuronsPerLayer = new ArrayList();

        Integer v = (int)firstLayerSlider.getValue();
        numberOfNeuronsPerLayer.add(v);

        for(int layer = 0; layer < getNumberOfHiddenLayers()-1; layer++)
        {
            Integer value = getNumberOfNeuronsFor(layer);
            numberOfNeuronsPerLayer.add(value);
        }

        return numberOfNeuronsPerLayer;
    }

    private HBox createHBoxWithLabelAndSliderFor(int layerIndex)
    {
        HBox hBox = createHBoxForContent();

        Label label = createLabelForSlider(layerIndex);
        hBox.getChildren().add(label);

        Slider slider = createSlider();
        hBox.getChildren().add(slider);

        return hBox;
    }

    private HBox createHBoxForContent()
    {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        Insets padding = new Insets(0, 25, 0, 25);
        hBox.setPadding(padding);
        return hBox;
    }

    private Label createLabelForSlider(int layerIndex)
    {
        return new Label("Ilość neuronów w warstwie " + (layerIndex + 2));
    }

    private Slider createSlider()
    {
        Slider slider = new Slider();

        slider.setMax(15);
        slider.setMin(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setPrefWidth(312);
        return slider;
    }


    private void loadNumberOfHiddenLayers()
    {
        numberOfLayerSlider.setValue(actualNetSettings.getNumberOfHiddenLayers());
    }

    private void loadNeouronsNumberOfHiddenLayer()
    {
        firstLayerSlider.setValue(actualNetSettings.getNeurons().get(0));

        for(int layerIndex = 1; layerIndex < actualNetSettings.getNumberOfHiddenLayers(); layerIndex++)
        {
            setValueOnSliderLayerOf(layerIndex);
        }
    }

    private void setValueOnSliderLayerOf(int layerIndex)
    {
        HBox hBox = (HBox) hiddenLayerSettingVBox.getChildren().get(layerIndex -1);
        Slider slider = (Slider) hBox.getChildren().get(1);
        slider.setValue(actualNetSettings.getNeurons().get(layerIndex));
    }


    private int getNumberOfNeuronsFor(int layer)
    {
        HBox hBox = (HBox) hiddenLayerSettingVBox.getChildren().get(layer);
        Slider slider = (Slider) hBox.getChildren().get(1);
        return (int) slider.getValue();
    }


}
