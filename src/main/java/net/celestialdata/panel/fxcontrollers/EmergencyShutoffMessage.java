package net.celestialdata.panel.fxcontrollers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import net.celestialdata.client.ApiException;
import net.celestialdata.client.model.FeatureStatus;
import net.celestialdata.panel.Main;
import net.celestialdata.panel.States;

public class EmergencyShutoffMessage {

    @FXML
    JFXButton restartParkButton;

    @FXML
    private void onRestartParkButton() {
        try {
            FeatureStatus result = Main.getApi().toggleFeature("Emergency Stop");
            States.setEmergencyStop(result.isRunning());
            System.out.println(result);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
