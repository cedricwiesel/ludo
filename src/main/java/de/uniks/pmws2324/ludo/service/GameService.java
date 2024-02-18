package de.uniks.pmws2324.ludo.service;

import de.uniks.pmws2324.ludo.Constants;
import de.uniks.pmws2324.ludo.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.uniks.pmws2324.ludo.Constants.FIELD_OFFSET;

public class GameService {
    Game game;
    List<Player> players = new ArrayList<>();
    List<Field> fields = new ArrayList<>();
    Map<Player, Integer> preGameRolls = new HashMap<>();

    public boolean initGame(String firstName, String secondName, String thirdName, String fourthName) {
        game = new Game();
        createPlayer(firstName);
        createPlayer(secondName);
        createPlayer(thirdName);
        createPlayer(fourthName);
        if (players.size() < 2) {
            players.clear();
            game = null;
            return false;
        }
        generateFields();
        generateOuts();
        generatePieces();
        game.setActivePlayer(players.get(0));
        game.setPhase(Phase.preGame);
        return true;
    }

    public void findStartingPlayer(int roll, Player player) {
        preGameRolls.put(player, roll);
        findNextPlayer();
        if (preGameRolls.size() == players.size()) {
            int highest = 0;
            for (Map.Entry<Player, Integer> entry : preGameRolls.entrySet()) {
                if (entry.getValue() > highest) {
                    highest = entry.getValue();
                    game.setActivePlayer(entry.getKey());
                }
            }
            game.setPhase(Phase.rolling);
        }
    }


    //------------------------ HELPERS ---------------------------------
    private void createPlayer(String playerName) {
        if (!playerName.isEmpty()) {
            Player player = new Player()
                    .setName(playerName)
                    .setGame(game);
            players.add(player);
        }
    }

    private void generateFields() {
        Field startField = new Field().setX(Constants.START_COORDINATE_X).setY(Constants.START_COORDINATE_Y);
        Field previousSpecialField = null;
        Field previousField = startField;
        int previousX = Constants.START_COORDINATE_X;
        int previousY = Constants.START_COORDINATE_Y;
        fields.add(startField);
        previousX += FIELD_OFFSET;
        previousField = createField(previousX, previousY, previousField);
        for (int i = 1; i < 5; i++) {
            HomeField currentHomefield = new HomeField().setColor(1);
            currentHomefield.setX(previousX);
            currentHomefield.setY(previousY + i * FIELD_OFFSET);
            currentHomefield.setPlayer(players.get(0));
            if (previousSpecialField != null) {
                currentHomefield.setPrevious(previousSpecialField);
            }
            previousSpecialField = currentHomefield;
            fields.add(currentHomefield);
        }
        previousSpecialField = null;
        previousX += FIELD_OFFSET;
        Field currentField = new Start()
                .setPlayer(players.get(0))
                .setColor(1)
                .setX(previousX)
                .setY(previousY)
                .setPrevious(previousField);
        previousField = currentField;
        fields.add(currentField);
        for (int i = 0; i < 4; i++) {
            previousY += FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        for (int i = 0; i < 4; i++) {
            previousX += FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        previousY += FIELD_OFFSET;
        previousField = createField(previousX, previousY, previousField);
        for (int i = 1; i < 5; i++) {
            HomeField currentHomeField = new HomeField().setColor(2);
            currentHomeField.setX(previousX - i * FIELD_OFFSET);
            currentHomeField.setY(previousY);
            if (players.size() > 2)
                currentHomeField.setPlayer(players.get(3));
            if (previousSpecialField != null) {
                currentHomeField.setPrevious(previousSpecialField);
            }
            previousSpecialField = currentHomeField;
            fields.add(currentHomeField);
        }
        previousSpecialField = null;
        previousY += FIELD_OFFSET;
        currentField = new Start()
                .setPlayer(players.get(1))
                .setColor(2)
                .setX(previousX)
                .setY(previousY)
                .setPrevious(previousField);
        fields.add(currentField);
        previousField = currentField;
        for (int i = 0; i < 4; i++) {
            previousX -= FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        for (int i = 0; i < 4; i++) {
            previousY += FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        previousX -= FIELD_OFFSET;
        previousField = createField(previousX, previousY, previousField);
        for (int i = 1; i < 5; i++) {
            HomeField currentHomeField = new HomeField().setColor(3);
            currentHomeField.setX(previousX);
            currentHomeField.setY(previousY - i * FIELD_OFFSET);
            currentHomeField.setPlayer(players.get(1));
            if (previousSpecialField != null) {
                currentHomeField.setPrevious(previousSpecialField);
            }
            previousSpecialField = currentHomeField;
            fields.add(currentHomeField);
        }
        previousSpecialField = null;
        previousX -= FIELD_OFFSET;
        currentField = new Start()
                .setColor(3)
                .setX(previousX)
                .setY(previousY)
                .setPrevious(previousField);
        if (players.size() > 2) {
            ((Start) currentField).setPlayer(players.get(2));
        }
        previousField = currentField;
        fields.add(currentField);
        for (int i = 0; i < 4; i++) {
            previousY -= FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        for (int i = 0; i < 4; i++) {
            previousX -= FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        previousY -= FIELD_OFFSET;
        previousField = createField(previousX, previousY, previousField);
        for (int i = 1; i < 5; i++) {
            HomeField currentHomeField = new HomeField().setColor(4);
            currentHomeField.setX(previousX + i * FIELD_OFFSET);
            currentHomeField.setY(previousY);
            if (players.size() > 3)
                currentHomeField.setPlayer(players.get(3));
            if (previousSpecialField != null) {
                currentHomeField.setPrevious(previousSpecialField);
            }
            previousSpecialField = currentHomeField;
            fields.add(currentHomeField);
        }
        previousY -= FIELD_OFFSET;
        currentField = new Start()
                .setColor(4)
                .setX(previousX)
                .setY(previousY)
                .setPrevious(previousField);
        if (players.size() > 3) {
            ((Start) currentField).setPlayer(players.get(3));
        }
        fields.add(currentField);
        previousField = currentField;
        for (int i = 0; i < 4; i++) {
            previousX += FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        for (int i = 0; i < 3; i++) {
            previousY -= FIELD_OFFSET;
            previousField = createField(previousX, previousY, previousField);
        }
        startField.setPrevious(previousField);
    }

    private void generateOuts() {
        createOutsForPlayer(players.get(0), 1, Constants.PLAYER_ONE_OUT_X, Constants.PLAYER_ONE_OUT_Y);
        createOutsForPlayer(players.get(1), 2, Constants.PLAYER_TWO_OUT_X, Constants.PLAYER_TWO_OUT_Y);
        if (players.size() > 2) {
            createOutsForPlayer(players.get(2), 3, Constants.PLAYER_THREE_OUT_X, Constants.PLAYER_THREE_OUT_Y);
            if (players.size() > 3) {
                createOutsForPlayer(players.get(3), 4, Constants.PLAYER_FOUR_OUT_X, Constants.PLAYER_FOUR_OUT_Y);
            } else {
                createOutsForPlayer(null, 4, Constants.PLAYER_FOUR_OUT_X, Constants.PLAYER_FOUR_OUT_Y);
            }
        } else {
            createOutsForPlayer(null, 3, Constants.PLAYER_THREE_OUT_X, Constants.PLAYER_THREE_OUT_Y);
            createOutsForPlayer(null, 4, Constants.PLAYER_FOUR_OUT_X, Constants.PLAYER_FOUR_OUT_Y);
        }
    }

    private void createOutsForPlayer(Player player, int color, int outX, int outY) {
        if (player != null) {
            player.withOutFields(createOutField(color, outX + FIELD_OFFSET / 2, outY + FIELD_OFFSET / 2));
            player.withOutFields(createOutField(color, outX + FIELD_OFFSET / 2, outY - FIELD_OFFSET / 2));
            player.withOutFields(createOutField(color, outX - FIELD_OFFSET / 2, outY + FIELD_OFFSET / 2));
            player.withOutFields(createOutField(color, outX - FIELD_OFFSET / 2, outY - FIELD_OFFSET / 2));
        } else {
            createOutField(color, outX + FIELD_OFFSET / 2, outY + FIELD_OFFSET / 2);
            createOutField(color, outX + FIELD_OFFSET / 2, outY - FIELD_OFFSET / 2);
            createOutField(color, outX - FIELD_OFFSET / 2, outY + FIELD_OFFSET / 2);
            createOutField(color, outX - FIELD_OFFSET / 2, outY - FIELD_OFFSET / 2);
        }
    }

    private OutField createOutField(int color, int x, int y) {
        OutField field = new OutField().setColor(color);
        field.setX(x);
        field.setY(y);
        fields.add(field);
        return field;
    }

    private Field createField(int x, int y, Field previousField) {
        Field field = new Field()
                .setX(x)
                .setY(y)
                .setPrevious(previousField);
        fields.add(field);
        return field;
    }

    private void generatePieces() {
        for (int i = 1; i < players.size() + 1; i++) {
            Player player = players.get(i - 1);
            for (OutField outField : player.getOutFields()) {
                outField.setPiece(new Piece()
                        .setFinished(false)
                        .setOwner(player)
                        .setPosition(outField)
                        .setColor(i)
                );
            }
        }
    }

    private void findNextPlayer() {
        int currentPlayerIndex = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(game.getActivePlayer())) {
                currentPlayerIndex = i;
            }
        }
        if (currentPlayerIndex == players.size() - 1) {
            game.setActivePlayer(players.get(0));
        } else {
            game.setActivePlayer(players.get(currentPlayerIndex + 1));
        }
    }

    //----------------------------- GETTERS ---------------------------------

    public Game getGame() {
        return this.game;
    }

    public List<Field> getFields() {
        return this.fields;
    }
}
