package de.uniks.pmws2324.ludo.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;
import org.fulib.builder.reflect.Type;

import java.util.List;

public class GenModel implements ClassModelDecorator {

    class Game {
        @Type("Phase")
        Object phase;
        int roll;

        @Link("game")
        List<Player> players;

        @Link ("activeGame")
        Player activePlayer;
    }

    class Field {
        int x;
        int y;

        @Link("position")
        Piece piece;

        @Link("next")
        Field previous;

        @Link("previous")
        Field next;
    }

    class Piece {
        boolean finished;

        @Link("pieces")
        Player owner;

        @Link("piece")
        Field position;
    }

    class Player {
        String name;

        @Link("owner")
        List<Piece> pieces;

        @Link("players")
        Game game;

        @Link("activePlayer")
        Game activeGame;

        @Link("player")
        List<HomeField> homeFields;

        @Link("player")
        Start start;

        @Link("player")
        List<OutField> outFields;
    }

    class HomeField extends Field {
        @Link("homeFields")
        Player player;
    }

    class Start extends Field {
        @Link("start")
        Player player;
    }

    class OutField extends Field {
        @Link("outFields")
        Player player;
    }

    @Override
    public void decorate(ClassModelManager m) {
        m.haveNestedClasses(GenModel.class);
    }
}
