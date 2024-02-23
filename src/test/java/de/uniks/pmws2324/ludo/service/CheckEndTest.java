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
        Player playerOne = new Player();
        Player playerTwo = new Player();
        gameService.createGame(playerOne, playerTwo);

        playerOne.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());
        playerTwo.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());

        for (HomeField homeField : playerOne.getHomeFields()) {
            homeField.setPiece(new Piece().setOwner(playerOne));
        }

        assertEquals(playerOne, gameService.checkEnd());
    }

    @Test
    void emptyHomeTest() {
        Player playerOne = new Player();
        Player playerTwo = new Player();
        gameService.createGame(playerOne, playerTwo);

        playerOne.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());
        playerTwo.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());

        playerOne.withPieces(new Piece(), new Piece(), new Piece(), new Piece());

        assertNull(gameService.checkEnd());
    }

    @Test
    void halfHomeTest() {
        Player playerOne = new Player();
        Player playerTwo = new Player();
        gameService.createGame(playerOne, playerTwo);

        playerOne.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());
        playerTwo.withHomeFields(new HomeField(), new HomeField(), new HomeField(), new HomeField());

        playerOne.withPieces(new Piece(), new Piece(), new Piece(), new Piece());

        playerOne.getPieces().get(0).setPosition(playerOne.getHomeFields().get(0));
        playerOne.getPieces().get(1).setPosition(playerOne.getHomeFields().get(1));

        assertNull(gameService.checkEnd());
    }
}
