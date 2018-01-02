package animals.dialog;

import controllers.NetSettingsTemplateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import neurons.NetSettings;

import java.io.IOException;
import java.util.Optional;

public class DialogNetSettings
{
    private Dialog<NetSettings> dialog;

    private ButtonType chooseButton;
    private NetSettings result;

    private NetSettingsTemplateController netSettingsTemplateController;

    public DialogNetSettings(NetSettings actualNetSettings)
    {
        dialog = new Dialog<NetSettings>();
        dialog.setTitle("Ustawienia sieci");
        chooseButton = new ButtonType("Zatwierdź", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(chooseButton, ButtonType.CANCEL);
        loadTemplateOfSettingsAndPut(actualNetSettings);
        netSettingsTemplateController.loadActualSettings();
        setResult();
    }


    public Optional<NetSettings> showAndGetResult()
    {
        return dialog.showAndWait();
    }

    private void loadTemplateOfSettingsAndPut(NetSettings actualNetSettings)
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/netSettings.fxml"));
        netSettingsTemplateController = new NetSettingsTemplateController(actualNetSettings);
        loader.setController(netSettingsTemplateController);

        try
        {
            dialog.getDialogPane().setContent((Node)loader.load());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setResult()
    {
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == chooseButton)
            {
                NetSettings netSettings = new NetSettings();
                netSettings.setNeurons(netSettingsTemplateController.getNumberOfNeuronsPerLayer());
                netSettings.setNumberOfHiddenLayers(netSettingsTemplateController.getNumberOfHiddenLayers());
                return netSettings;
            }
            return null;
        });
    }

}
