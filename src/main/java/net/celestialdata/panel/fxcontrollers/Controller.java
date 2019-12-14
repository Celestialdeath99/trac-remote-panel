package net.celestialdata.panel.fxcontrollers;

import com.jfoenix.controls.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import net.celestialdata.client.ApiException;
import net.celestialdata.client.model.FeatureStatus;
import net.celestialdata.panel.DatabaseManager;
import net.celestialdata.panel.Logger;
import net.celestialdata.panel.Main;
import net.celestialdata.panel.States;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.Objects;

public class Controller {

    @FXML
    BorderPane mainPane;
    @FXML
    JFXButton playStructureButton;
    @FXML
    JFXButton lazyRiverButton;
    @FXML
    JFXButton riverFountainsButton;
    @FXML
    JFXButton centerJetsButton;
    @FXML
    JFXButton yellowSlideButton;
    @FXML
    JFXButton blueSlideButton;
    @FXML
    JFXButton emergencyShutoffButton;
    @FXML
    Circle statusIndicator;
    @FXML
    JFXButton settingsButton;

    private boolean prevEStop = false;
    private boolean prevEDisabled = false;
    private JFXAlert emergencyShutoffAlert;
    private JFXAlert emergencyShutoffDisabledAlert;
    private static JFXAlert settingsAlert;
    private static String notificationMessage = "";
    private long timeSinceLastPing = States.getTimeSinceLastPing();

    private static void setNotificationMessage() {
        notificationMessage = "Unable to connect to API";
    }

    @FXML
    private void initialize() throws IOException {

        emergencyShutoffAlert = new JFXAlert(Main.getMainStage());
        Parent emergencyShutoffMessageRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("EmergencyShutoffMessage.fxml")));
        emergencyShutoffAlert.initModality(Modality.APPLICATION_MODAL);
        emergencyShutoffAlert.initStyle(StageStyle.UNDECORATED);
        emergencyShutoffAlert.setContent(emergencyShutoffMessageRoot);

        emergencyShutoffDisabledAlert = new JFXAlert(Main.getMainStage());
        Parent emergencyShutoffDisabledRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("EmergencyShutoffDisabled.fxml")));
        emergencyShutoffDisabledAlert.initModality(Modality.APPLICATION_MODAL);
        emergencyShutoffDisabledAlert.initStyle(StageStyle.UNDECORATED);
        emergencyShutoffDisabledAlert.setContent(emergencyShutoffDisabledRoot);

        settingsAlert = new JFXAlert(Main.getMainStage());
        Parent settingsAlertRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SettingsView.fxml")));
        settingsAlert.initModality(Modality.APPLICATION_MODAL);
        settingsAlert.initStyle(StageStyle.UNDECORATED);
        settingsAlert.setContent(settingsAlertRoot);

        AnimationTimer updateTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (States.isPlayStructureOn()) {
                    playStructureButton.setStyle("-fx-background-color: #5cb85c");
                } else playStructureButton.setStyle("-fx-background-color: grey");

                if (States.isLazyRiverOn()) {
                    lazyRiverButton.setStyle("-fx-background-color: #5cb85c");
                } else lazyRiverButton.setStyle("-fx-background-color: grey");

                if (States.isRiverFountainsOn()) {
                    riverFountainsButton.setStyle("-fx-background-color: #5cb85c");
                } else riverFountainsButton.setStyle("-fx-background-color: grey");

                if (States.isCenterJetsOn()) {
                    centerJetsButton.setStyle("-fx-background-color: #5cb85c");
                } else centerJetsButton.setStyle("-fx-background-color: grey");

                if (States.isYellowSlideOn()) {
                    yellowSlideButton.setStyle("-fx-background-color: #5cb85c");
                } else yellowSlideButton.setStyle("-fx-background-color: grey");

                if (States.isBlueSlideOn()) {
                    blueSlideButton.setStyle("-fx-background-color: #5cb85c");
                } else blueSlideButton.setStyle("-fx-background-color: grey");

                if (States.isEmergencyStopOn() && !prevEStop) {
                    prevEStop = true;
                    showEmergencyShutoffMessage();
                } else if (!States.isEmergencyStopOn() && prevEStop) {
                    prevEStop = false;
                    hideEmergencyShutoffMessage();
                }

                if (!EmergencyShutoffDisabled.isMessageShown() && prevEDisabled) {
                    prevEDisabled = false;
                    emergencyShutoffButton.setVisible(true);
                    emergencyShutoffDisabledAlert.hide();
                }

                if (!notificationMessage.isEmpty()) {
                    Notifications.create().title("Error").text(notificationMessage).showError();
                    notificationMessage = "";
                }

                timeSinceLastPing = States.getTimeSinceLastPing();
                if (timeSinceLastPing < 6000) {
                    statusIndicator.setFill(Color.GREEN);
                } else if (timeSinceLastPing > 6000 && timeSinceLastPing < 10000) {
                    statusIndicator.setFill(Color.YELLOW);
                } else if (timeSinceLastPing > 11000) {
                    statusIndicator.setFill(Color.RED);
                }
            }
        };
        updateTimer.start();
    }

    private void showEmergencyShutoffMessage() {
        emergencyShutoffButton.setVisible(false);
        emergencyShutoffAlert.show();
    }

    private void hideEmergencyShutoffMessage() {
        emergencyShutoffButton.setVisible(true);
        emergencyShutoffAlert.hide();
    }

    @FXML
    private void onPlayStructureButton() {
        try {
            FeatureStatus result = Main.getApi().toggleFeature("Play Structure");
            States.setPlayStructure(result.isRunning());
        } catch (ApiException e) {
            setNotificationMessage();
            Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "onPlayStructureButton()\n\n");
        }
    }

    @FXML
    private void onLazyRiverButton() {
        try {
            FeatureStatus result = Main.getApi().toggleFeature("Lazy River");
            States.setLazyRiver(result.isRunning());
        } catch (ApiException e) {
            setNotificationMessage();
            Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "onLazyRiverButton()\n\n");
        }
    }

    @FXML
    private void onRiverFountainsButton() {
        try {
            FeatureStatus result = Main.getApi().toggleFeature("River Fountains");
            States.setRiverFountains(result.isRunning());
        } catch (ApiException e) {
            setNotificationMessage();
            Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "onRiverFountainsButton()\n\n");
        }
    }

    @FXML
    private void onCenterJetsButton() {
        try {
            FeatureStatus result = Main.getApi().toggleFeature("Center Jets");
            States.setCenterJets(result.isRunning());
        } catch (ApiException e) {
            setNotificationMessage();
            Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "onCenterJetsButton()\n\n");
        }
    }

    @FXML
    private void onYellowSlideButton() {
        try {
            FeatureStatus result = Main.getApi().toggleFeature("Yellow Slide");
            States.setYellowSlide(result.isRunning());
        } catch (ApiException e) {
            setNotificationMessage();
            Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "onYellowSlideButton()\n\n");
        }
    }

    @FXML
    private void onBlueSlideButton() {
        try {
            FeatureStatus result = Main.getApi().toggleFeature("Blue Slide");
            States.setBlueSlide(result.isRunning());
        } catch (ApiException e) {
            setNotificationMessage();
            Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "onBlueSlideButton()\n\n");
        }
    }

    @FXML
    private void onEmergencyShutoffButton() {
        if (States.areAllFeaturesOff()) {
            EmergencyShutoffDisabled.setMessageShownTrue();
            prevEDisabled = true;
            emergencyShutoffButton.setVisible(false);
            emergencyShutoffDisabledAlert.show();
        } else {
            try {
                FeatureStatus result = Main.getApi().toggleFeature("Emergency Stop");
                States.setEmergencyStop(result.isRunning());
            } catch (ApiException e) {
                setNotificationMessage();
                Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "onEmergencyShutoffButton()\n\n");
            }
        }
    }

    @FXML
    private void onSettingsButton() {
        settingsAlert.show();
    }

    static void closeSettings() {
        settingsAlert.hide();
    }
}