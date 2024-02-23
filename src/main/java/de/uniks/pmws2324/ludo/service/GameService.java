package de.uniks.pmws2324.ludo.service;

import de.uniks.pmws2324.ludo.Constants;
import de.uniks.pmws2324.ludo.model.*;

import java.util.*;

import static de.uniks.pmws2324.ludo.Constants.FIELD_OFFSET;

public class GameService {
    private final Random rnGenerator;
    Game game;
    List<Player> players = new ArrayList<>();
    List<Field> fields = new ArrayList<>();
    Map<Player, Integer> preGameRolls = new HashMap<>();
    private final boolean testRun;

    public GameService(boolean testRun) {
        this.testRun = testRun;
        if (this.testRun) {
            this.rnGenerator = new Random(1000);
        } else {
            this.rnGenerator = new Random();
        }
    }

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

    public void findStartingPlayer(Player player) {
        game.setRoll(rnGenerator.nextInt(1, 7));
        preGameRolls.put(player, game.getRoll());
        setNextPlayer();
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

    public void roll(Player activePlayer) {
        game.setRoll(rnGenerator.nextInt(1, 7));
        boolean movedOut = false;
        if (game.getRoll() == 6) {
            movedOut = moveOut(activePlayer);
        }
        if (checkPossibleMove(activePlayer) && !movedOut) {
            game.setPhase(Phase.movingPiece);
        } else if (!movedOut) {
            game.setGoAgain(false);
            setNextPlayer();
        }
    }

    private boolean moveOut(Player activePlayer) {
        for (OutField outField : activePlayer.getOutFields()) {
            if (outField.getPiece() != null) {
                if (activePlayer.getStart().getPiece() == null) {
                    outField.getPiece().setPosition(activePlayer.getStart());
                    return true;
                } else if (activePlayer.getStart().getPiece().getOwner() != activePlayer) {
                    kick(activePlayer.getStart().getPiece());
                    outField.getPiece().setPosition(activePlayer.getStart());
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean movePiece(Piece piece) {
        Field destination = findDestination(piece);
        if (game.getRoll() == 6) {
            game.setGoAgain(true);
        }
        if (destination.getPiece() != null && destination.getPiece().getOwner().equals(piece.getOwner())) {
            return false;
        } else if (destination.getPiece() != null && !destination.getPiece().getOwner().equals(piece.getOwner())) {
            kick(destination.getPiece());
            piece.setPosition(destination);
            if (game.isGoAgain()) {
                game.setGoAgain(false);
            } else {
                game.setPhase(Phase.rolling);
                setNextPlayer();
            }
            return true;
        } else {
            piece.setPosition(destination);
            game.setPhase(Phase.rolling);
            if (game.isGoAgain()) {
                game.setGoAgain(false);
            } else {
                setNextPlayer();
            }
            return true;
        }
    }


    public boolean checkEmptyOut(Player player) {
        for (OutField outField : player.getOutFields()) {
            if (outField.getPiece() != null) {
                return false;
            }
        }
        return true;
    }

    //------------------------ HELPERS ---------------------------------

    private void kick(Piece piece) {
        for (OutField outField : piece.getOwner().getOutFields()) {
            if (outField.getPiece() == null) {
                piece.setPosition(outField);
            }
        }
    }

    private boolean checkPossibleMove(Player player) {
        for (Piece piece : player.getPieces()) {
            if (piece.getPosition() instanceof OutField) {
                continue;
            } else {
                if (findDestination(piece) != null && (findDestination(piece).getPiece() == null
                        || !findDestination(piece).getPiece().getOwner().equals(player))) {
                    return true;
                }
            }
        }
        return false;
    }

    public Field findDestination(Piece piece) {
        Field destination = piece.getPosition();
        for (int i = 0; i < game.getRoll(); i++) {
            if (destination != null && destination.getNext() != null && destination.getNext() instanceof Start
                    && ((Start) destination.getNext()).getPlayer() != null
                    && ((Start) destination.getNext()).getPlayer().equals(piece.getOwner())) {
                destination = piece.getOwner().getHomeFields().get(0);
            } else if (destination != null && destination.getNext() != null){
                destination = destination.getNext();
            } else {
                destination = null;
            }
        }
        return destination;
    }

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
            currentHomeField.setPlayer(players.get(1));
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
            if (players.size() > 2)
                currentHomeField.setPlayer(players.get(2));
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

    public void createOutsForPlayer(Player player, int color, int outX, int outY) {
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
                        .setOwner(player)
                        .setPosition(outField)
                        .setColor(i)
                );
            }
        }
    }

    public void setNextPlayer() {
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

    //----------------------------- GETTERS & SETTERS ---------------------------------

    public void createGame(Player activePlayer, Player playerTwo, Player playerThree, Player playerFour) {
        this.game = new Game()
                .setActivePlayer(activePlayer)
                .withPlayers(activePlayer, playerTwo, playerThree, playerFour);
                this.players.add(activePlayer);
                this.players.add(playerTwo);
                this.players.add(playerThree);
                this.players.add(playerFour);

    }

    public Game getGame() {
        return this.game;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public Player checkEnd() {
        for (Player player : players) {
            int finishdedPieces = 0;
            for (Piece piece : player.getPieces()) {
                if (piece.getPosition() instanceof HomeField) {
                    finishdedPieces++;
                }
            }
            if (finishdedPieces == 4) {
                return player;
            }
        }
        return null;
    }

    public List<Player> findOtherPlayers(Player player) {
        List<Player> list = new ArrayList<>();
        for (Player gamePlayer : game.getPlayers()) {
            if (gamePlayer != player) {
                list.add(gamePlayer);
            }
        }
        return list;
    }
}
