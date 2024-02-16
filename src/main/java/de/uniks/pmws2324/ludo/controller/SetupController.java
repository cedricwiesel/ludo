package de.uniks.pmws2324.ludo.controller;

import de.uniks.pmws2324.ludo.App;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SetupController extends Controller{
    public TextField yellowPlayerField;
    public TextField greenPlayerField;
    public TextField blackPlayerField;
    public TextField redPlayerField;
    public Button startButton;

    public SetupController(App app, GameService gameService) {
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
