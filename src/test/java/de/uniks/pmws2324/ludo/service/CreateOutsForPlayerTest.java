package de.uniks.pmws2324.ludo.service;

import de.uniks.pmws2324.ludo.model.Player;
import org.junit.jupiter.api.Test;

import static de.uniks.pmws2324.ludo.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateOutsForPlayerTest {
    GameService gameService = new GameService(true);

    @Test
    void greenOutTest() {
        // create player two test method on
        Player player = new Player();

        // use method to create outs for player at position of green player in normal play
        gameService.createOutsForPlayer(player, 1, PLAYER_ONE_OUT_X, PLAYER_ONE_OUT_Y);

        // check x and y positions of all the players outFields
        assertAll(
                () -> assertEquals(PLAYER_ONE_OUT_X + FIELD_OFFSET / 2, player.getOutFields().get(0).getX()),
                () -> assertEquals(PLAYER_ONE_OUT_Y + FIELD_OFFSET / 2, player.getOutFields().get(0).getY()),
                () -> assertEquals(PLAYER_ONE_OUT_X + FIELD_OFFSET / 2, player.getOutFields().get(1).getX()),
                () -> assertEquals(PLAYER_ONE_OUT_Y - FIELD_OFFSET / 2, player.getOutFields().get(1).getY()),
                () -> assertEquals(PLAYER_ONE_OUT_X - FIELD_OFFSET / 2, player.getOutFields().get(2).getX()),
                () -> assertEquals(PLAYER_ONE_OUT_Y + FIELD_OFFSET / 2, player.getOutFields().get(2).getY()),
                () -> assertEquals(PLAYER_ONE_OUT_X - FIELD_OFFSET / 2, player.getOutFields().get(3).getX()),
                () -> assertEquals(PLAYER_ONE_OUT_Y - FIELD_OFFSET / 2, player.getOutFields().get(3).getY())
        );
    }

    @Test
    void redOutTest() {
        // create player two test method on
        Player player = new Player();

        // use method to create outs for player at position of red player in normal play
        gameService.createOutsForPlayer(player, 1, PLAYER_TWO_OUT_X, PLAYER_TWO_OUT_Y);

        // check x and y positions of all the players outFields
        assertAll(
                () -> assertEquals(PLAYER_TWO_OUT_X + FIELD_OFFSET / 2, player.getOutFields().get(0).getX()),
                () -> assertEquals(PLAYER_TWO_OUT_Y + FIELD_OFFSET / 2, player.getOutFields().get(0).getY()),
                () -> assertEquals(PLAYER_TWO_OUT_X + FIELD_OFFSET / 2, player.getOutFields().get(1).getX()),
                () -> assertEquals(PLAYER_TWO_OUT_Y - FIELD_OFFSET / 2, player.getOutFields().get(1).getY()),
                () -> assertEquals(PLAYER_TWO_OUT_X - FIELD_OFFSET / 2, player.getOutFields().get(2).getX()),
                () -> assertEquals(PLAYER_TWO_OUT_Y + FIELD_OFFSET / 2, player.getOutFields().get(2).getY()),
                () -> assertEquals(PLAYER_TWO_OUT_X - FIELD_OFFSET / 2, player.getOutFields().get(3).getX()),
                () -> assertEquals(PLAYER_TWO_OUT_Y - FIELD_OFFSET / 2, player.getOutFields().get(3).getY())
        );
    }

    @Test
    void blackOutTest() {
// create player two test method on
        Player player = new Player();

        // use method to create outs for player at position of black player in normal play
        gameService.createOutsForPlayer(player, 1, PLAYER_THREE_OUT_X, PLAYER_THREE_OUT_Y);

        // check x and y positions of all the players outFields
        assertAll(
                () -> assertEquals(PLAYER_THREE_OUT_X + FIELD_OFFSET / 2, player.getOutFields().get(0).getX()),
                () -> assertEquals(PLAYER_THREE_OUT_Y + FIELD_OFFSET / 2, player.getOutFields().get(0).getY()),
                () -> assertEquals(PLAYER_THREE_OUT_X + FIELD_OFFSET / 2, player.getOutFields().get(1).getX()),
                () -> assertEquals(PLAYER_THREE_OUT_Y - FIELD_OFFSET / 2, player.getOutFields().get(1).getY()),
                () -> assertEquals(PLAYER_THREE_OUT_X - FIELD_OFFSET / 2, player.getOutFields().get(2).getX()),
                () -> assertEquals(PLAYER_THREE_OUT_Y + FIELD_OFFSET / 2, player.getOutFields().get(2).getY()),
                () -> assertEquals(PLAYER_THREE_OUT_X - FIELD_OFFSET / 2, player.getOutFields().get(3).getX()),
                () -> assertEquals(PLAYER_THREE_OUT_Y - FIELD_OFFSET / 2, player.getOutFields().get(3).getY())
        );
    }
}
