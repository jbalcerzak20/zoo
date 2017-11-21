import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.setStage(primaryStage);

        primaryStage.setTitle("Zoo");
        primaryStage.setScene(new Scene(root,500,500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
