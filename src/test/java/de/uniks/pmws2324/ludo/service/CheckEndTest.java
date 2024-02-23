package de.uniks.pmws2324.ludo.service;

import de.uniks.pmws2324.ludo.model.HomeField;
import de.uniks.pmws2324.ludo.model.Piece;
import de.uniks.pmws2324.ludo.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CheckEndTest {

    GameService gameService = new GameService(true);

    @Test
    void fullHomeTest() {
        // create game to implement test scenario in
        Player playerOne = new Player();
        Player playerTwo = new Player();
        gameService.createGame(playerOne, playerTwo, new Player(), new Player());

        // give both players their home fields
        playerOne.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());
        playerTwo.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());

        // add pieces to all of playerOne's homefields
        for (HomeField homeField : playerOne.getHomeFields()) {
            homeField.setPiece(new Piece().setOwner(playerOne));
        }

        // test if anyone one has won. should obviously return playerOne since they have all four pieces in their home
        assertEquals(playerOne, gameService.checkEnd());
    }

    @Test
    void emptyHomeTest() {
        // create game to implement test scenario in
        Player playerOne = new Player();
        Player playerTwo = new Player();
        gameService.createGame(playerOne, playerTwo, new Player(), new Player());

        // give the two first players their home fields
        playerOne.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());
        playerTwo.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());

        // add pieces to playerOne
        playerOne.withPieces(new Piece(), new Piece(), new Piece(), new Piece());

        // check if somebody has won, obviously is true since none of the players have pieces in their home
        assertNull(gameService.checkEnd());
    }

    @Test
    void halfHomeTest() {
        // create game to implement test scenario in
        Player playerOne = new Player();
        Player playerTwo = new Player();
        gameService.createGame(playerOne, playerTwo, new Player(), new Player());

        // give the two first players their home fields
        playerOne.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());
        playerTwo.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());

        // add pieces to playerOne
        playerOne.withPieces(new Piece(), new Piece(), new Piece(), new Piece());

        // move two of playerOne's pieces to his home
        playerOne.getPieces().get(0).setPosition(playerOne.getHomeFields().get(0));
        playerOne.getPieces().get(1).setPosition(playerOne.getHomeFields().get(1));

        // check if anyone has won, should return null since none of the players have four pieces in their home
        assertNull(gameService.checkEnd());
    }
}
