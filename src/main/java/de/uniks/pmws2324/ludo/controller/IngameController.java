package de.uniks.pmws2324.ludo.controller;

import de.uniks.pmws2324.ludo.App;
import de.uniks.pmws2324.ludo.Main;
import de.uniks.pmws2324.ludo.model.*;
import de.uniks.pmws2324.ludo.service.GameService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Random;

import static de.uniks.pmws2324.ludo.Constants.*;

public class IngameController extends Controller {
    private GraphicsContext context;
    private final Random rnGenerator = new Random();
    private Parent parent;
    public StackPane stackPane;
    public Label activePlayerLabel;
    public Canvas boardCanvas;
    public Button rollButton;
    public Label lastRoll;

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
        context = boardCanvas.getGraphicsContext2D();

        activePlayerLabel.setText(gameService.getGame().getActivePlayer().getName() + " it's your turn to roll!");

        drawMap();

        rollButton.setOnAction(this::handleRole);

        return this.parent;
    }

    private void drawMap() {
        clearCanvas();

        for (Field field : gameService.getFields()) {
            context.setStroke(Color.BLACK);
            if (field instanceof OutField) {
                switch (((OutField) field).getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                }
            }
            if (field instanceof Start) {
                switch (((Start) field).getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                }
            }
            if (field instanceof HomeField) {
                switch (((HomeField) field).getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
                        break;
                }
            }
            context.strokeOval(field.getX(), field.getY(), FIELD_RADIUS, FIELD_RADIUS);
            if (field.getPiece() != null) {
                switch (field.getPiece().getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_RADIUS,
                                PIECE_RADIUS);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_RADIUS,
                                PIECE_RADIUS);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_RADIUS,
                                PIECE_RADIUS);
                        if ((field instanceof Start) || (field instanceof OutField) || (field instanceof HomeField)) {
                            context.setStroke(Color.WHITE);
                        }
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_RADIUS,
                                PIECE_RADIUS);
                        break;
                }

                context.strokeOval(
                        field.getX() + PIECE_OFFSET,
                        field.getY() + PIECE_OFFSET,
                        PIECE_RADIUS,
                        PIECE_RADIUS);
            }
        }
    }

    //-------------------------------------- HELPERS --------------------------------------

    private void clearCanvas() {
        GraphicsContext context = boardCanvas.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
    }

    //-------------------------------------- HANDLERS --------------------------------------

    private void handleRole(ActionEvent actionEvent) {
        int roll = rnGenerator.nextInt(1, 7);
        if (gameService.getGame().getPhase().equals(Phase.preGame)) {
            gameService.findStartingPlayer(roll, this.gameService.getGame().getActivePlayer());
            activePlayerLabel.setText(gameService.getGame().getActivePlayer().getName() + " it's your turn to roll!");
        }
        lastRoll.setText("Last roll: " + roll);
    }
}
