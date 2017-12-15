package controllers;

import animals.Zwierze;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import loaders.ZooLoader;
import neurons.Perceptron;
import neurons.hidden.Layer;

import java.math.BigDecimal;
import java.util.Arrays;

public class MainController {
    private Stage stage;
    private ZooLoader zooLoader;
    private Perceptron perceptron;
    private ObservableList<Zwierze> zwierzes;
    private TableColumn<String,String > kolumnaNazwa;
    private TableColumn<String,String> kolumnaSiersc;
    private TableColumn<String,String> kolumnaPiora;
    private TableColumn<String,String> kolumnaJaja;
    private TableColumn<String,String> kolumnaMleko;
    private TableColumn<String,String> kolumnaLatajacy;
    private TableColumn<String,String> kolumnaWodny;
    private TableColumn<String,String> kolumnaDrapieznik;
    private TableColumn<String,String> kolumnaUzebiony;
    private TableColumn<String,String> kolumnaKregoslup;
    private TableColumn<String,String> kolumnaOddycha;
    private TableColumn<String,String> kolumnaJadowity;
    private TableColumn<String,String> kolumnaPletwy;
    private TableColumn<String,String> kolumnaNogi;
    private TableColumn<String,String> kolumnaOgon;
    private TableColumn<String,String> kolumnaDomowy;
    private TableColumn<String,String> kolumnaRozmiarKota;
    private TableColumn<String,String> kolumnaTyp;



    @FXML
    private TabPane tabPane;
    @FXML
    private TableView daneUczace;

    public MainController()
    {

    }

    @FXML
    public void initialize()
    {
        zooLoader = new ZooLoader();
        perceptron = new Perceptron(3);
        iniciowanieKolumn();
        ustawDaneUczace();
    }

    private void iniciowanieKolumn()
    {
        kolumnaNazwa = new TableColumn<>("Nazwa");
        kolumnaSiersc = new TableColumn<>("Sierść");
        kolumnaPiora = new TableColumn<>("Pióra");
        kolumnaJaja = new TableColumn<>("Jaja");
        kolumnaMleko = new TableColumn<>("Mleko");
        kolumnaLatajacy = new TableColumn<>("Latający");
        kolumnaWodny = new TableColumn<>("Wodny");
        kolumnaDrapieznik = new TableColumn<>("Drapieżnik");
        kolumnaUzebiony = new TableColumn<>("Uzębiony");
        kolumnaKregoslup = new TableColumn<>("Kręgosłup");
        kolumnaOddycha = new TableColumn<>("Oddycha");
        kolumnaJadowity = new TableColumn<>("Jadowity");
        kolumnaPletwy = new TableColumn<>("Płetwy");
        kolumnaNogi = new TableColumn<>("Nogi");
        kolumnaOgon = new TableColumn<>("Ogon");
        kolumnaDomowy = new TableColumn<>("Domowy");
        kolumnaRozmiarKota = new TableColumn<>("Catsize");
        kolumnaTyp = new TableColumn<>("Typ");

        kolumnaNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        kolumnaSiersc.setCellValueFactory(new PropertyValueFactory<>("siersc"));
        kolumnaPiora.setCellValueFactory(new PropertyValueFactory<>("piora"));
        kolumnaJaja.setCellValueFactory(new PropertyValueFactory<>("jaja"));
        kolumnaMleko.setCellValueFactory(new PropertyValueFactory<>("mleko"));
        kolumnaLatajacy.setCellValueFactory(new PropertyValueFactory<>("latajacy"));
        kolumnaWodny.setCellValueFactory(new PropertyValueFactory<>("wodny"));
        kolumnaDrapieznik.setCellValueFactory(new PropertyValueFactory<>("drapieznik"));
        kolumnaUzebiony.setCellValueFactory(new PropertyValueFactory<>("uzebiony"));
        kolumnaKregoslup.setCellValueFactory(new PropertyValueFactory<>("kregoslup"));
        kolumnaOddycha.setCellValueFactory(new PropertyValueFactory<>("oddycha"));
        kolumnaJadowity.setCellValueFactory(new PropertyValueFactory<>("jadowity"));
        kolumnaPletwy.setCellValueFactory(new PropertyValueFactory<>("pletwy"));
        kolumnaNogi.setCellValueFactory(new PropertyValueFactory<>("nogi"));
        kolumnaOgon.setCellValueFactory(new PropertyValueFactory<>("ogon"));
        kolumnaDomowy.setCellValueFactory(new PropertyValueFactory<>("domowy"));
        kolumnaRozmiarKota.setCellValueFactory(new PropertyValueFactory<>("rozmiarKota"));
        kolumnaTyp.setCellValueFactory(new PropertyValueFactory<>("typ"));


        daneUczace.getColumns().addAll(kolumnaNazwa,kolumnaSiersc,kolumnaPiora,kolumnaJaja,kolumnaMleko,kolumnaLatajacy,kolumnaWodny,kolumnaDrapieznik,kolumnaUzebiony);
        daneUczace.getColumns().addAll(kolumnaKregoslup,kolumnaOddycha,kolumnaJadowity,kolumnaPletwy,kolumnaNogi,kolumnaOgon,kolumnaDomowy,kolumnaRozmiarKota,kolumnaTyp);

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void ustawDaneUczace()
    {
        zwierzes = zooLoader.wczytajPlik(getClass().getResource("/dane/zoo.data").getFile());
        daneUczace.getItems().addAll(zwierzes);

        perceptronTest();
    }


    private void perceptronTest()
    {
        perceptron.setLt(0.2);
        perceptron.setLtm(0.1);
        perceptron.setMaxT(1000);
        perceptron.setEps(0.00001);
        perceptron.setZwierzes(zwierzes); //wejscia i wagi - init dwu wymiar. tablice
//        perceptron.teach();


        neurons.hidden.Perceptron p2 = new neurons.hidden.Perceptron();
        p2.setZwierzes(zwierzes);
        p2.setIloscDanychUczacych(70);
        p2.setMaxIteration(100);
        p2.setEpsilon(0.0001);
        p2.teach();


//        for (Layer l:p2.getLayers()) {
//            System.out.println(l.getSumaBledu());
//        }
    }


}
