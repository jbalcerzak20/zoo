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
        perceptron = new Perceptron();
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





        daneUczace.getColumns().forEach(item->{
           TableColumn<String,String> s = (TableColumn)item;
            s.setPrefWidth(90);
        });
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
        double[][]b = {{1,0.1,0.9},{1,1,0.1}};

        double[]wy = {1,0};
        int K = 2;
        int k = 1;
        int lpd = 0;
        double n = 0.2;
        double[] wagi = {0.1,-0.1,-0.2};
        double y = 0;
        int it = 0;

        while(lpd!=K)
        {
            System.out.println("Iteracja nr "+it);
            System.out.println(Arrays.toString(b[k-1]));
            y = perceptron.funkcjaSkoku(perceptron.sumujNeurony(b[k-1],wagi));
            perceptron.adaptujWagizMomentem(b[k-1],wagi,n,wy[k-1],y,0,null);
            System.out.println("y="+y);

            if(y == wy[k-1])
            {
                lpd++;
            }

            k++;

            if(k>K)
            {
                k=1;
            }
            System.out.println(Arrays.toString(wagi)+" ||"+lpd);
            System.out.println("=====================");
            it++;
        }


    }


}
