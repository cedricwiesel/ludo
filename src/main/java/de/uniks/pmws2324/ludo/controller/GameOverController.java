package de.uniks.pmws2324.ludo.controller;

import de.uniks.pmws2324.ludo.App;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameOverController extends Controller{
    public Label winnerLabel;
    public Label loserLabel;
    public Button quitButton;
    public Button newGameButton;

    public GameOverController(App app, GameService gameService) {
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
