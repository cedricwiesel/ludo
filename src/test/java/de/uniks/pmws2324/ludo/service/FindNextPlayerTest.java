package de.uniks.pmws2324.ludo.service;

import de.uniks.pmws2324.ludo.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindNextPlayerTest {

    GameService gameService = new GameService(true);

    @Test
    void simpleTest() {
        // create game to implement test scenario in
        Player playerOne = new Player();
        Player playerTwo = new Player();
        gameService.createGame(playerOne, playerTwo, new Player(), new Player());

        // move on active player for game
        gameService.setNextPlayer();

        // check if active player is now player two
        assertEquals(playerTwo, gameService.getGame().getActivePlayer());
    }

    @Test
    void moveTwiceTest() {
        // create game to implement test scenario in
        Player playerOne = new Player();
        Player playerTwo = new Player();
        Player playerThree = new Player();
        gameService.createGame(playerOne, playerTwo, playerThree, new Player());

        // move on active player for game
        gameService.setNextPlayer();

        // check if active player is now playerTwo
        assertEquals(playerTwo, gameService.getGame().getActivePlayer());

        // move on active player for game
        gameService.setNextPlayer();

        // check if active player is now playerThree
        assertEquals(playerThree, gameService.getGame().getActivePlayer());
    }

    @Test
    void fullCycleTest() {
        // create game to implement test scenario in
        Player playerOne = new Player();
        Player playerTwo = new Player();
        Player playerThree = new Player();
        Player playerFour = new Player();
        gameService.createGame(playerOne, playerTwo, playerThree, playerFour);

        // move on active player for game
        gameService.setNextPlayer();

        // check if active player is now playerTwo
        assertEquals(playerTwo, gameService.getGame().getActivePlayer());

        // move on active player for game
        gameService.setNextPlayer();

        // check if active player is now playerThree
        assertEquals(playerThree, gameService.getGame().getActivePlayer());

        // move on active player for game
        gameService.setNextPlayer();

        // check if active player is now playerFour
        assertEquals(playerFour, gameService.getGame().getActivePlayer());

        // move on active player for game
        gameService.setNextPlayer();

        // check if active player is now playerOne again
        assertEquals(playerOne, gameService.getGame().getActivePlayer());
    }
}
