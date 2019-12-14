package net.celestialdata.panel;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.celestialdata.client.ApiClient;
import net.celestialdata.client.ApiException;
import net.celestialdata.client.api.DefaultApi;
import net.celestialdata.client.model.ArrayOfStatus;
import net.celestialdata.client.model.ArrayOfStatusInner;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main application launcher.
 *
 * @author Brandan Schmitz
 */
public class Main extends Application {

    /**
     * The main stage that the application GUI is built upon.
     */
    private static Stage mainStage;
    private static DefaultApi api;

    /**
     * Return the main stage for building elements on the GUI.
     *
     * @return The main stage.
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    public static DefaultApi getApi() {
        return api;
    }

    public static void updateApi() {
        api.setApiClient(api.getApiClient().setBasePath("http://" + DatabaseManager.getApiServerIP() + "/v1"));
    }

    /**
     * The default method called upon application launch.
     *
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        api = new DefaultApi(new ApiClient().setBasePath("http://" + DatabaseManager.getApiServerIP() + "/v1"));

        // Schedule checking for changes to the park states every 2 seconds
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable statusCheck = () -> {
            try {
                ArrayOfStatus result = api.getStatus();
                States.setLastPingTime();

                for (ArrayOfStatusInner status : result) {
                    switch (status.getName()) {
                        case "Play Structure":
                            States.setPlayStructure(status.isRunning());
                            break;
                        case "Lazy River":
                            States.setLazyRiver(status.isRunning());
                            break;
                        case "River Fountains":
                            States.setRiverFountains(status.isRunning());
                            break;
                        case "Center Jets":
                            States.setCenterJets(status.isRunning());
                            break;
                        case "Yellow Slide":
                            States.setYellowSlide(status.isRunning());
                            break;
                        case "Blue Slide":
                            States.setBlueSlide(status.isRunning());
                            break;
                        case "Emergency Stop":
                            States.setEmergencyStop(status.isRunning());
                            break;
                    }
                }
            } catch (ApiException e) {
                Logger.networkLog(e.toString() + "\n  - API Server: " + DatabaseManager.getApiServerIP() + "\n  - Location: " + "statusCheck()\n\n");
            }
        };
        scheduler.scheduleAtFixedRate(statusCheck, 10, 2, TimeUnit.SECONDS);

        Logger.launchLog();
        launch(args);
    }

    /**
     * Starts the building process of the GUI and sets the default window information.
     *
     * @param primaryStage The stage that everything in the GUI is built on.
     * @throws Exception Throws any exceptions thrown whe building the GUI.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the main stage FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Main.fxml")));

        // Set the window decorator and set the window to full screen.
        JFXDecorator decorator = new JFXDecorator(primaryStage, root, true, false, false);

        // Configure the application title and icon and exit hint
        primaryStage.setTitle("TRAC Waterpark Controller");
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        // Set the default close action for the application
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        // Set the window to the stage and show it
        primaryStage.setScene(new Scene(decorator, 800, 480));
        primaryStage.show();

        // Set the main stage equal to the primary stage for external method access
        mainStage = primaryStage;
    }
}
