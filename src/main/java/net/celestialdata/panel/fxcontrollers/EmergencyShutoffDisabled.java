package net.celestialdata.panel.fxcontrollers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class EmergencyShutoffDisabled {

    private static boolean messageShown = false;

    @FXML
    JFXButton understandButton;

    @FXML
    private void onUnderstandButton() {
        messageShown = false;
    }

    static void setMessageShownTrue() {
        messageShown = true;
    }

    static boolean isMessageShown() {
        return messageShown;
    }
}
