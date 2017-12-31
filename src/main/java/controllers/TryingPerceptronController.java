package controllers;

import animals.Rodzaj;
import animals.Zwierze;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import neurons.hidden.Perceptron;

import java.text.MessageFormat;

public class TryingPerceptronController
{
    @FXML
    private VBox mainVBox;

    @FXML
    private CheckBox hairCheckBox;

    @FXML
    private CheckBox feathersCheckBox;

    @FXML
    private CheckBox eggsCheckBox;

    @FXML
    private CheckBox milkCheckBox;

    @FXML
    private CheckBox airborneCheckBox;

    @FXML
    private CheckBox aquaticCheckBox;

    @FXML
    private CheckBox predatorCheckBox;

    @FXML
    private CheckBox toothedCheckBox;

    @FXML
    private CheckBox backboneCheckBox;

    @FXML
    private CheckBox breathesCheckBox;

    @FXML
    private CheckBox venomousCheckBox;

    @FXML
    private CheckBox finsCheckBox;

    @FXML
    private ComboBox<Integer> numberOfLegsComboBox;

    @FXML
    private CheckBox tailCheckBox;

    @FXML
    private CheckBox domesticCheckBox;

    @FXML
    private CheckBox catsizeCheckBox;

    @FXML
    private Button guessButton;

    @FXML
    private Label resultOfGuessingLabel;

    private Zwierze zwierze;
    private Perceptron perceptron;


    public void initialize()
    {
        prepareNumberOfLegsComboBox();
        resultOfGuessingLabel.setText("");

    }

    public VBox getMainVBox()
    {
        return mainVBox;
    }

    public void setPerceptron(Perceptron perceptron)
    {
        this.perceptron = perceptron;
    }

    @FXML
    public void guessOnAction()
    {
        getAnimalFromUser();
        double[] output = perceptron.guess(zwierze);
        String result = checkWhatPerceptronReply(output);
        putMessegeToUser(result);
    }


    private void prepareNumberOfLegsComboBox()
    {
        ObservableList<Integer> numberOfLegsObservableList = FXCollections.observableArrayList();
        numberOfLegsObservableList.add(0);
        numberOfLegsObservableList.add(2);
        numberOfLegsObservableList.add(4);
        numberOfLegsObservableList.add(5);
        numberOfLegsObservableList.add(6);
        numberOfLegsObservableList.add(8);
        numberOfLegsComboBox.setItems(numberOfLegsObservableList);
        numberOfLegsComboBox.setValue(0);
    }

    private void getAnimalFromUser()
    {
        zwierze = new Zwierze();
        zwierze.setSiersc(hairCheckBox.isSelected());
        zwierze.setPiora(hairCheckBox.isSelected());
        zwierze.setJaja(eggsCheckBox.isSelected());
        zwierze.setMleko(milkCheckBox.isSelected());
        zwierze.setLatajacy(airborneCheckBox.isSelected());
        zwierze.setWodny(aquaticCheckBox.isSelected());
        zwierze.setDrapieznik(predatorCheckBox.isSelected());
        zwierze.setUzebiony(toothedCheckBox.isSelected());
        zwierze.setKregoslup(backboneCheckBox.isSelected());
        zwierze.setOddycha(breathesCheckBox.isSelected());
        zwierze.setJadowity(venomousCheckBox.isSelected());
        zwierze.setPletwy(finsCheckBox.isSelected());
        zwierze.setNogi(numberOfLegsComboBox.getValue());
        zwierze.setOgon(tailCheckBox.isSelected());
        zwierze.setDomowy(this.domesticCheckBox.isSelected());
        zwierze.setRozmiarKota(catsizeCheckBox.isSelected());
    }

    private String checkWhatPerceptronReply(double[] output)
    {
        int index = 0;
        double actualMax = output[0];
        for(int i = 1; i < output.length; i++)
        {
            if(output[i] > actualMax)
            {
                index = i;
            }
        }

        Rodzaj rodzaj = Rodzaj.getKlase((index + 1));
        return rodzaj.getNazwa();
    }

    private void putMessegeToUser(String result)
    {
        String msg = MessageFormat.format("Czy to {0}?", result.toUpperCase());
        this.resultOfGuessingLabel.setText(msg);
    }
}
