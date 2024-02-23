package de.uniks.pmws2324.ludo.controller;

import de.uniks.pmws2324.ludo.App;
import de.uniks.pmws2324.ludo.Main;
import de.uniks.pmws2324.ludo.model.Player;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class GameOverController extends Controller{
    private Parent parent;
    public Label winnerLabel;
    public Label loserLabel;
    public Button quitButton;
    public Button newGameButton;

    public GameOverController(App app, GameService gameService) {
        super(app, gameService);
    }

    @Override
    public void init() {
        final FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/GameOver.fxml"));
        loader.setControllerFactory(c -> this);
        try {
            this.parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Parent render() {
        winnerLabel.setText(gameService.checkEnd().getName() + " won the game!");
        List<Player> otherPlayers = gameService.findOtherPlayers(gameService.checkEnd());
        switch (otherPlayers.size()) {
            case 1:
                loserLabel.setText(otherPlayers.get(0).getName() + " lost.");
                break;
            case 2:
                loserLabel.setText(otherPlayers.get(0).getName() + " and " + otherPlayers.get(1).getName() + " lost.");
                break;
            case 3:
                loserLabel.setText(otherPlayers.get(0).getName() + ", " + otherPlayers.get(1).getName() + " and " +
                        otherPlayers.get(2).getName() + " lost.");
        }

        newGameButton.setOnAction(this::handleNewGame);

        quitButton.setOnAction(this::handleQuit);

        return this.parent;
    }

    private void handleQuit(ActionEvent actionEvent) {
        app.quit();
        this.destroy();
    }

    private void handleNewGame(ActionEvent actionEvent) {
        app.showSetupView();
    }
}
