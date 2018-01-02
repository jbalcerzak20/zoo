package controllers;

import animals.Zwierze;
import animals.dialog.DialogNetSettings;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import loaders.ZooLoader;
import neurons.NetSettings;
import neurons.hidden.Perceptron;

import java.util.Optional;

public class MainController
{
    private Stage stage;
    private ZooLoader zooLoader;
    private ObservableList<Zwierze> zwierzes;
    private TableColumn<String, String> kolumnaNazwa;
    private TableColumn<String, String> kolumnaSiersc;
    private TableColumn<String, String> kolumnaPiora;
    private TableColumn<String, String> kolumnaJaja;
    private TableColumn<String, String> kolumnaMleko;
    private TableColumn<String, String> kolumnaLatajacy;
    private TableColumn<String, String> kolumnaWodny;
    private TableColumn<String, String> kolumnaDrapieznik;
    private TableColumn<String, String> kolumnaUzebiony;
    private TableColumn<String, String> kolumnaKregoslup;
    private TableColumn<String, String> kolumnaOddycha;
    private TableColumn<String, String> kolumnaJadowity;
    private TableColumn<String, String> kolumnaPletwy;
    private TableColumn<String, String> kolumnaNogi;
    private TableColumn<String, String> kolumnaOgon;
    private TableColumn<String, String> kolumnaDomowy;
    private TableColumn<String, String> kolumnaRozmiarKota;
    private TableColumn<String, String> kolumnaTyp;


    @FXML
    private TabPane tabPane;
    @FXML
    private TableView daneUczace;
    @FXML
    private Tab wynikiTab;
    @FXML
    private TextArea wynikiText;
    @FXML
    private Tab wykresyTab;
    @FXML
    private TextField ltText;
    @FXML
    private TextField ltmText;
    @FXML
    private TextField iteracjeText;
    @FXML
    private LineChart<Number, Number> uczenieWykres;
    private Perceptron perceptron;
    private XYChart.Series series;
    private XYChart.Series seriesV;

    private boolean chartNotRendered = false;

    @FXML
    ProgressIndicator progressIndicator;

    @FXML
    private TryingPerceptronController tryingNeuralNetTabController;

    private NetSettings netSettings;


    public MainController()
    {

    }

    @FXML
    public void initialize()
    {
//        makeInvisibleChartsTabResultTabAndTryingNeuralNetTab();
        netSettings = new NetSettings();

        uczenieWykres.getXAxis().setLabel("t");
        uczenieWykres.getYAxis().setLabel("u(t)");
        series = new XYChart.Series();
        series.setName("U");
        seriesV = new XYChart.Series();
        seriesV.setName("V");
        uczenieWykres.getData().add(series);
        uczenieWykres.getData().add(seriesV);
        zooLoader = new ZooLoader();
        perceptron = new Perceptron();
        iniciowanieKolumn();
        ustawDaneUczace();
//        testujWszystko();
    }

    private void makeInvisibleChartsTabResultTabAndTryingNeuralNetTab()
    {
        wykresyTab.getContent().setVisible(false);
        wynikiTab.getContent().setVisible(false);
        tryingNeuralNetTabController.getMainVBox().setVisible(false);
    }

    private void testujWszystko()
    {
        perceptron = new Perceptron();
        perceptron.setZwierzes(zwierzes);
        perceptron.setInitLayers(3);
        uczenieWykres.getData().get(0).getData().clear();
        uczenieWykres.getData().get(1).getData().clear();
        wynikiText.clear();

        int[] neurons = new int[3];
        neurons[0] = 16;
        neurons[1] = 20;
//        neurons[1]=7;
        neurons[2] = 7;
        perceptron.setInitNeurons(neurons);

        perceptron.podzielDane(70, 20, 11);
        perceptron.setMaxIteration(100000);
        perceptron.setEpsilon(0.00000001);
        perceptron.setLt(0.0002);
        perceptron.setLtm(0.001);


        perceptron.teach();


        System.out.println("Wyniki uczenia perceptronu: \n");
        System.out.println(perceptron.getUczaceSummary());
        System.out.println("Poprawne: " + String.valueOf(perceptron.getIloscUczacychPoprawnych()) + "/" + String.valueOf(perceptron.getIloscDanychUczacych()) + "\n");

        perceptron.tests();
        System.out.println("Wyniki testowania perceptronu: \n");
        System.out.println(perceptron.getTestoweSummary());
        System.out.println("Poprawne: " + String.valueOf(perceptron.getIloscTestowychPoprawnych()) + "/" + String.valueOf(perceptron.getIloscDanychTestowych()) + "\n");
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


        daneUczace.getColumns().addAll(kolumnaNazwa, kolumnaSiersc, kolumnaPiora, kolumnaJaja, kolumnaMleko, kolumnaLatajacy, kolumnaWodny, kolumnaDrapieznik, kolumnaUzebiony);
        daneUczace.getColumns().addAll(kolumnaKregoslup, kolumnaOddycha, kolumnaJadowity, kolumnaPletwy, kolumnaNogi, kolumnaOgon, kolumnaDomowy, kolumnaRozmiarKota, kolumnaTyp);

    }

    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    private void ustawDaneUczace()
    {
        zwierzes = zooLoader.wczytajPlik(getClass().getResource("/dane/zoo.data").getFile());
        daneUczace.getItems().addAll(zwierzes);
    }

    @FXML
    public void nauczAction(ActionEvent e) throws InterruptedException
    {
        Task task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                teach();
                chartNotRendered = true;
                updateProgress(1, 1);

                return null;
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.setVisible(true);
        Thread teachThread = new Thread(task);
        teachThread.start();

//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Zakończono testowanie");
//        alert.setHeaderText("Zakończono Uczenie");
//        alert.show();
    }

    public void chartsTabOnSelectionChanged()
    {
        if (chartNotRendered)
        {
            uczenieWykres.getData().get(0).getData().clear();
            uczenieWykres.getData().get(1).getData().clear();

            for (int i = 0; i < perceptron.getSummaryErrors().size(); i += 10)
            {
                XYChart.Data data = new XYChart.Data(String.valueOf(i), perceptron.getSummaryErrors().get(i));
                Rectangle rect = new Rectangle(0, 0);
                rect.setVisible(false);
                data.setNode(rect);
                uczenieWykres.getData().get(0).getData().add(data);

            }

            for (int i = 0; i < perceptron.getSummaryErrorsVerify().size(); i += 10)
            {
                XYChart.Data data = new XYChart.Data(String.valueOf(i), perceptron.getSummaryErrorsVerify().get(i));
                Rectangle rect = new Rectangle(0, 0);
                rect.setVisible(false);
                data.setNode(rect);
                uczenieWykres.getData().get(1).getData().add(data);
            }

            chartNotRendered = false;
        }
    }

    private void teach()
    {
        int iloscWarstw = netSettings.getNumberOfHiddenLayers() + 2;
        perceptron = new Perceptron();
        perceptron.setZwierzes(zwierzes);
        perceptron.setInitLayers(iloscWarstw);

        wynikiText.clear();

        int[] neurons = getNeurons();
        perceptron.setInitNeurons(neurons);

        perceptron.podzielDane(70, 20, 11);
        perceptron.setMaxIteration(Integer.parseInt(iteracjeText.getText()));
        perceptron.setEpsilon(0.00000001);
        perceptron.setLt(Double.parseDouble(ltText.getText()));
        perceptron.setLtm(Double.parseDouble(ltmText.getText()));

        perceptron.teach();

        wynikiText.appendText("Wyniki uczenia perceptronu: \n");
        wynikiText.appendText(perceptron.getUczaceSummary());
        wynikiText.appendText("Poprawne: " + String.valueOf(perceptron.getIloscUczacychPoprawnych()) + "/" + String.valueOf(perceptron.getIloscDanychUczacych()) + "\n");


        passLearnedPerceptronToTryingNeuralNetTab();
    }

    private int[] getNeurons()
    {
        int[] neurons = new int[netSettings.getNumberOfHiddenLayers() + 2];
        neurons[0] = 16;
        neurons[neurons.length - 1] = 7;
        int i = 1;
        for (Integer v : netSettings.getNeurons())
        {
            neurons[i] = v;
            i++;
        }
        return neurons;
    }

    @FXML
    public void testujAction(ActionEvent e)
    {
        perceptron.tests();
        wynikiText.appendText("Wyniki testowania perceptronu: \n");
        wynikiText.appendText(perceptron.getTestoweSummary());
        wynikiText.appendText("Poprawne: " + String.valueOf(perceptron.getIloscTestowychPoprawnych()) + "/" + String.valueOf(perceptron.getIloscDanychTestowych()) + "\n");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zakończono testowanie");
        alert.setHeaderText("Zakończono testowanie");
        alert.show();
    }

    private void passLearnedPerceptronToTryingNeuralNetTab()
    {
        this.tryingNeuralNetTabController.setPerceptron(this.perceptron);
    }

    public void openNetSettings()
    {
        DialogNetSettings dialogNetSettings = new DialogNetSettings(netSettings);
        Optional<NetSettings> settings = dialogNetSettings.showAndGetResult();
        netSettings = settings.orElse(netSettings);
    }
}
