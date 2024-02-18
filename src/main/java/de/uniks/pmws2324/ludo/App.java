package de.uniks.pmws2324.ludo;

import de.uniks.pmws2324.ludo.controller.Controller;
import de.uniks.pmws2324.ludo.controller.IngameController;
import de.uniks.pmws2324.ludo.controller.SetupController;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
    private Stage stage;
    private Controller controller;
    private GameService service;
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        service = new GameService();

        final Scene scene = new Scene(new Label("Loading..."));
        stage.setScene(scene);

        showSetupView();

        stage.show();
    }

    public void showSetupView() {
        Controller setupController = new SetupController(this, service);
        show(setupController);
        stage.setTitle("Ludo - Setup");
    }

    public void showIngameView() {
        Controller ingameController = new IngameController(this, service);
        stage.setWidth(1280);
        stage.setHeight(720);
        show(ingameController);
        stage.setTitle("Ludo");
    }

    private void show(Controller controller) {
        cleanup();
        this.controller = controller;
        controller.init();
        stage.getScene().setRoot(controller.render());
    }

    private void cleanup() {
        if (controller != null) {
            controller.destroy();
            controller = null;
        }
    }
}
