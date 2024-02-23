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
        boolean goAgain;

        @Link("game")
        List<Player> players;

        @Link ("activeGame")
        Player activePlayer;

       @Link("hovered")
        Piece hoveredPiece;
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
        int color;

        @Link("pieces")
        Player owner;

        @Link("piece")
        Field position;

        @Link("hoveredPiece")
        Game hovered;
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
        int color;
        @Link("homeFields")
        Player player;
    }

    class Start extends Field {
        int color;
        @Link("start")
        Player player;
    }

    class OutField extends Field {
        int color;
        @Link("outFields")
        Player player;
    }

    @Override
    public void decorate(ClassModelManager m) {
        m.haveNestedClasses(GenModel.class);
    }
}
