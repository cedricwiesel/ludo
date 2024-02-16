package de.uniks.pmws2324.ludo.controller;

import de.uniks.pmws2324.ludo.App;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class IngameController extends Controller{
    public StackPane stackPane;
    public Label activePlayerLabel;
    public Canvas boardCanvas;
    public Button rollButton;

    public IngameController(App app, GameService gameService) {
        super(app, gameService);
    }

    @Override
    public void init() {

    }

    @Override
    public Parent render() {
        return null;
    }
}
