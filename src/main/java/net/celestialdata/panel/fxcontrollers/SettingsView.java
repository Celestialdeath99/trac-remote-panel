package net.celestialdata.panel.fxcontrollers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import net.celestialdata.panel.DatabaseManager;
import net.celestialdata.panel.Main;

import java.io.IOException;

public class SettingsView {

    @FXML
    JFXTextField ssidTextField;
    @FXML
    JFXPasswordField ssidPasswordField;
    @FXML
    JFXButton ssidSaveButton;
    @FXML
    JFXTextField apiServerTextField;
    @FXML
    JFXButton apiServerUpdateButton;
    @FXML
    JFXButton settingsCloseButton;

    @FXML
    private void initialize() {
        ssidTextField.setText(DatabaseManager.getSSID());
        ssidPasswordField.setText(DatabaseManager.getSSIDPassword());
        apiServerTextField.setText(DatabaseManager.getApiServerIP());
    }

    @FXML
    private void onSSIDSaveButton() {
        DatabaseManager.setSSID(ssidTextField.getText());
        DatabaseManager.setSSIDPassword(ssidPasswordField.getText());

        try {
            Runtime.getRuntime().exec("sed -i '6s/.*/    ssid=\"" + DatabaseManager.getSSID() + "\"/' /etc/wpa_supplicant/wpa_supplicant.conf");
            Runtime.getRuntime().exec("sed -i '7s/.*/    psk=\"" + DatabaseManager.getSSIDPassword() + "\"/' /etc/wpa_supplicant/wpa_supplicant.conf");
            Runtime.getRuntime().exec("wpa_cli -i wlan0 reconfigure");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onApiServerUpdateButton() {
        DatabaseManager.setApiServerIP(apiServerTextField.getText());
        Main.updateApi();
    }

    @FXML
    private void onSettingsCloseButton() {
        Controller.closeSettings();
    }
}