package de.uniks.pmws2324.ludo.controller;

import de.uniks.pmws2324.ludo.App;
import de.uniks.pmws2324.ludo.Main;
import de.uniks.pmws2324.ludo.model.Phase;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class IngameController extends Controller{
    private Parent parent;
    public StackPane stackPane;
    public Label activePlayerLabel;
    public Canvas boardCanvas;
    public Button rollButton;

    public IngameController(App app, GameService gameService) {
        super(app, gameService);
    }

    @Override
    public void init() {
        final FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Ingame.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            this.parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Parent render() {
        if (gameService.getGame().getPhase().equals(Phase.rolling)) {
            activePlayerLabel.setText(gameService.getGame().getActivePlayer().getName() + " it's your turn to roll!");
        } else {
            activePlayerLabel.setText(gameService.getGame().getActivePlayer().getName() + ", now move your Piece");
        }
        return this.parent;
    }
}
