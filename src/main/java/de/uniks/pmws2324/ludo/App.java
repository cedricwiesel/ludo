package de.uniks.pmws2324.ludo;

import de.uniks.pmws2324.ludo.controller.Controller;
import de.uniks.pmws2324.ludo.controller.GameOverController;
import de.uniks.pmws2324.ludo.controller.IngameController;
import de.uniks.pmws2324.ludo.controller.SetupController;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
    private final boolean testRun;
    private Stage stage;
    private Controller controller;
    private GameService service;

    public App() {
        this.testRun = false;
    }

    public App(boolean testRun) {
        this.testRun = testRun;
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        service = new GameService(this.testRun);

        final Scene scene = new Scene(new Label("Loading..."));
        stage.setScene(scene);

        showSetupView();

        stage.show();
    }

    public void showSetupView() {
        stage.setWidth(600);
        stage.setHeight(400);
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

    public void showGameOverView() {
        Controller gameOverController = new GameOverController(this, service);
        stage.setWidth(600);
        stage.setHeight(400);
        show(gameOverController);
        stage.setTitle("Ludo - Game Over");
    }

    public void quit() {
        stage.close();
        try {
            this.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
