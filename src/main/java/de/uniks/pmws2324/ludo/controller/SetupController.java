package de.uniks.pmws2324.ludo.controller;

import de.uniks.pmws2324.ludo.App;
import de.uniks.pmws2324.ludo.Main;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SetupController extends Controller{
    private Parent parent;
    public TextField firstPlayerField;
    public TextField secondPlayerField;
    public TextField thirdPlayerField;
    public TextField fourthPlayerField;
    public Button startButton;

    public SetupController(App app, GameService gameService) {
        super(app, gameService);
    }

    @Override
    public void init() {
        final FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Setup.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            this.parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Parent render() {
        startButton.setOnAction(this::handleStart);
        return this.parent;
    }

    private void handleStart(ActionEvent actionEvent) {
        if (gameService.initGame(firstPlayerField.getText().trim(), secondPlayerField.getText().trim(),
                thirdPlayerField.getText().trim(), fourthPlayerField.getText().trim())) {
            app.showIngameView();
        }
    }
}
