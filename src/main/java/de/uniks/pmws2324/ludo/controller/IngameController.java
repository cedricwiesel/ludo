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

import static de.uniks.pmws2324.ludo.Constants.*;

public class IngameController extends Controller {
    private GraphicsContext context;
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

        rollButton.setOnAction(this::handleRoll);

        this.boardCanvas.setOnMouseClicked(event -> {
            handleMouseClick(event.getX(), event.getY());
        });

        return this.parent;
    }

    private void drawMap() {
        //update top text
        clearCanvas();
        Player activePlayer = gameService.getGame().getActivePlayer();
        switch (this.gameService.getGame().getPhase()) {
            case rolling:
                if (gameService.getGame().isGoAgain()) {
                    activePlayerLabel.setText(activePlayer.getName() + " you rolled a 6, so it's your turn to roll " +
                            "again!");
                } else {
                    activePlayerLabel.setText(activePlayer.getName() + " it's your turn to roll!");
                }
                break;
            case movingPiece:
                activePlayer = gameService.getGame().getActivePlayer();
                activePlayerLabel.setText(activePlayer.getName() + " you rolled a " + gameService.getGame().getRoll() +
                        ". Now move your piece.");
                break;
        }

        //update board
        for (Field field : gameService.getFields()) {
            context.setStroke(Color.BLACK);
            if (field instanceof OutField) {
                switch (((OutField) field).getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                }
            }
            if (field instanceof Start) {
                switch (((Start) field).getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                }
            }
            if (field instanceof HomeField) {
                switch (((HomeField) field).getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
                        break;
                }
            }
            context.strokeOval(field.getX(), field.getY(), FIELD_DIAMETER, FIELD_DIAMETER);
            if (field.getPiece() != null) {
                switch (field.getPiece().getColor()) {
                    case 1:
                        context.setFill(Color.GREEN);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_DIAMETER,
                                PIECE_DIAMETER);
                        break;
                    case 2:
                        context.setFill(Color.RED);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_DIAMETER,
                                PIECE_DIAMETER);
                        break;
                    case 3:
                        context.setFill(Color.BLACK);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_DIAMETER,
                                PIECE_DIAMETER);
                        if ((field instanceof Start) || (field instanceof OutField) || (field instanceof HomeField)) {
                            context.setStroke(Color.WHITE);
                        }
                        break;
                    case 4:
                        context.setFill(Color.YELLOW);
                        context.fillOval(
                                field.getX() + PIECE_OFFSET,
                                field.getY() + PIECE_OFFSET,
                                PIECE_DIAMETER,
                                PIECE_DIAMETER);
                        break;
                }

                context.strokeOval(
                        field.getX() + PIECE_OFFSET,
                        field.getY() + PIECE_OFFSET,
                        PIECE_DIAMETER,
                        PIECE_DIAMETER);
            }
        }
    }


    //-------------------------------------- HELPERS --------------------------------------

    private void clearCanvas() {
        GraphicsContext context = boardCanvas.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
    }


    private void drawHover(Piece piece) {
        if (gameService.findDestination(piece) != null && (gameService.findDestination(piece).getPiece() == null
                || !gameService.findDestination(piece).getPiece().getOwner().equals(piece.getOwner()))) {
            int locationX = gameService.findDestination(piece).getX() + TARGET_OFFSET;
            int locationY = gameService.findDestination(piece).getY() + TARGET_OFFSET;
            context.setFill(Color.DARKBLUE);
            context.fillOval(locationX, locationY, TARGET_DIAMETER, TARGET_DIAMETER);
        }
    }
    //-------------------------------------- HANDLERS --------------------------------------

    private void handleRoll(ActionEvent actionEvent) {
        Player activePlayer = this.gameService.getGame().getActivePlayer();
        if (this.gameService.getGame().getPhase().equals(Phase.preGame)) {
            this.gameService.findStartingPlayer(activePlayer);
            activePlayer = this.gameService.getGame().getActivePlayer();
            activePlayerLabel.setText(activePlayer.getName() + " it's your turn to roll!");
        } else if (this.gameService.getGame().getPhase().equals(Phase.rolling)) {
            this.gameService.roll(activePlayer);
        }
        lastRoll.setText("" + gameService.getGame().getRoll());
        drawMap();
    }

    private void handleMouseClick(double x, double y) {
        if (gameService.getGame().getPhase().equals(Phase.movingPiece)) {
            for (Piece piece : gameService.getGame().getActivePlayer().getPieces()) {
                Field position = piece.getPosition();
                if (position.getX() <= x && x <= position.getX() + FIELD_DIAMETER
                        && position.getY() <= y && y <= position.getY() + FIELD_DIAMETER) {
                    drawMap();
                    boolean noInOut = gameService.checkEmptyOut(piece.getOwner());
                    if (piece.getOwner().getStart().getPiece() == null
                            || noInOut || piece.getOwner().getStart().getPiece().getOwner() != piece.getOwner()
                            || (gameService.findDestination(piece.getOwner().getStart().getPiece()).getPiece() != null
                            && gameService.findDestination(piece.getOwner().getStart().getPiece()).getPiece()
                            .getOwner() == piece.getOwner())
                    || piece.getOwner().getStart().getPiece().equals(piece)) {
                        drawHover(piece);
                        gameService.getGame().setHoveredPiece(piece);
                    }
                    break;
                } else if (gameService.getGame().getHoveredPiece() != null
                        && gameService.getGame().getHoveredPiece().equals(piece)) {
                    position = gameService.findDestination(piece);
                    if (position == null) {continue;}
                    if (position.getX() <= x && x <= position.getX() + FIELD_DIAMETER
                            && position.getY() <= y && y <= position.getY() + FIELD_DIAMETER) {
                        boolean moved = gameService.movePiece(piece);
                        drawMap();
                        if (moved) {
                            gameService.getGame().setHoveredPiece(null);
                            if (gameService.checkEnd() != null) {
                                app.showGameOverView();
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
