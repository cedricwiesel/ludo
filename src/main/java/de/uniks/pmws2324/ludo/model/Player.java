package de.uniks.pmws2324.ludo.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

public class Player
{
   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_OUT_FIELDS = "outFields";
   public static final String PROPERTY_START = "start";
   public static final String PROPERTY_HOME_FIELDS = "homeFields";
   public static final String PROPERTY_PIECES = "pieces";
   public static final String PROPERTY_GAME = "game";
   public static final String PROPERTY_ACTIVE_GAME = "activeGame";
   private String name;
   private List<OutField> outFields;
   private Start start;
   private List<HomeField> homeFields;
   private List<Piece> pieces;
   private Game game;
   private Game activeGame;
   protected PropertyChangeSupport listeners;

   public String getName()
   {
      return this.name;
   }

   public Player setName(String value)
   {
      if (Objects.equals(value, this.name))
      {
         return this;
      }

      final String oldValue = this.name;
      this.name = value;
      this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      return this;
   }

   public List<OutField> getOutFields()
   {
      return this.outFields != null ? Collections.unmodifiableList(this.outFields) : Collections.emptyList();
   }

   public Player withOutFields(OutField value)
   {
      if (this.outFields == null)
      {
         this.outFields = new ArrayList<>();
      }
      if (!this.outFields.contains(value))
      {
         this.outFields.add(value);
         value.setPlayer(this);
         this.firePropertyChange(PROPERTY_OUT_FIELDS, null, value);
      }
      return this;
   }

   public Player withOutFields(OutField... value)
   {
      for (final OutField item : value)
      {
         this.withOutFields(item);
      }
      return this;
   }

   public Player withOutFields(Collection<? extends OutField> value)
   {
      for (final OutField item : value)
      {
         this.withOutFields(item);
      }
      return this;
   }

   public Player withoutOutFields(OutField value)
   {
      if (this.outFields != null && this.outFields.remove(value))
      {
         value.setPlayer(null);
         this.firePropertyChange(PROPERTY_OUT_FIELDS, value, null);
      }
      return this;
   }

   public Player withoutOutFields(OutField... value)
   {
      for (final OutField item : value)
      {
         this.withoutOutFields(item);
      }
      return this;
   }

   public Player withoutOutFields(Collection<? extends OutField> value)
   {
      for (final OutField item : value)
      {
         this.withoutOutFields(item);
      }
      return this;
   }

   public Start getStart()
   {
      return this.start;
   }

   public Player setStart(Start value)
   {
      if (this.start == value)
      {
         return this;
      }

      final Start oldValue = this.start;
      if (this.start != null)
      {
         this.start = null;
         oldValue.setPlayer(null);
      }
      this.start = value;
      if (value != null)
      {
         value.setPlayer(this);
      }
      this.firePropertyChange(PROPERTY_START, oldValue, value);
      return this;
   }

   public List<HomeField> getHomeFields()
   {
      return this.homeFields != null ? Collections.unmodifiableList(this.homeFields) : Collections.emptyList();
   }

   public Player withHomeFields(HomeField value)
   {
      if (this.homeFields == null)
      {
         this.homeFields = new ArrayList<>();
      }
      if (!this.homeFields.contains(value))
      {
         this.homeFields.add(value);
         value.setPlayer(this);
         this.firePropertyChange(PROPERTY_HOME_FIELDS, null, value);
      }
      return this;
   }

   public Player withHomeFields(HomeField... value)
   {
      for (final HomeField item : value)
      {
         this.withHomeFields(item);
      }
      return this;
   }

   public Player withHomeFields(Collection<? extends HomeField> value)
   {
      for (final HomeField item : value)
      {
         this.withHomeFields(item);
      }
      return this;
   }

   public Player withoutHomeFields(HomeField value)
   {
      if (this.homeFields != null && this.homeFields.remove(value))
      {
         value.setPlayer(null);
         this.firePropertyChange(PROPERTY_HOME_FIELDS, value, null);
      }
      return this;
   }

   public Player withoutHomeFields(HomeField... value)
   {
      for (final HomeField item : value)
      {
         this.withoutHomeFields(item);
      }
      return this;
   }

   public Player withoutHomeFields(Collection<? extends HomeField> value)
   {
      for (final HomeField item : value)
      {
         this.withoutHomeFields(item);
      }
      return this;
   }

   public List<Piece> getPieces()
   {
      return this.pieces != null ? Collections.unmodifiableList(this.pieces) : Collections.emptyList();
   }

   public Player withPieces(Piece value)
   {
      if (this.pieces == null)
      {
         this.pieces = new ArrayList<>();
      }
      if (!this.pieces.contains(value))
      {
         this.pieces.add(value);
         value.setOwner(this);
         this.firePropertyChange(PROPERTY_PIECES, null, value);
      }
      return this;
   }

   public Player withPieces(Piece... value)
   {
      for (final Piece item : value)
      {
         this.withPieces(item);
      }
      return this;
   }

   public Player withPieces(Collection<? extends Piece> value)
   {
      for (final Piece item : value)
      {
         this.withPieces(item);
      }
      return this;
   }

   public Player withoutPieces(Piece value)
   {
      if (this.pieces != null && this.pieces.remove(value))
      {
         value.setOwner(null);
         this.firePropertyChange(PROPERTY_PIECES, value, null);
      }
      return this;
   }

   public Player withoutPieces(Piece... value)
   {
      for (final Piece item : value)
      {
         this.withoutPieces(item);
      }
      return this;
   }

   public Player withoutPieces(Collection<? extends Piece> value)
   {
      for (final Piece item : value)
      {
         this.withoutPieces(item);
      }
      return this;
   }

   public Game getGame()
   {
      return this.game;
   }

   public Player setGame(Game value)
   {
      if (this.game == value)
      {
         return this;
      }

      final Game oldValue = this.game;
      if (this.game != null)
      {
         this.game = null;
         oldValue.withoutPlayers(this);
      }
      this.game = value;
      if (value != null)
      {
         value.withPlayers(this);
      }
      this.firePropertyChange(PROPERTY_GAME, oldValue, value);
      return this;
   }

   public Game getActiveGame()
   {
      return this.activeGame;
   }

   public Player setActiveGame(Game value)
   {
      if (this.activeGame == value)
      {
         return this;
      }

      final Game oldValue = this.activeGame;
      if (this.activeGame != null)
      {
         this.activeGame = null;
         oldValue.setActivePlayer(null);
      }
      this.activeGame = value;
      if (value != null)
      {
         value.setActivePlayer(this);
      }
      this.firePropertyChange(PROPERTY_ACTIVE_GAME, oldValue, value);
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public PropertyChangeSupport listeners()
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      return this.listeners;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getName());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutOutFields(new ArrayList<>(this.getOutFields()));
      this.setStart(null);
      this.withoutHomeFields(new ArrayList<>(this.getHomeFields()));
      this.withoutPieces(new ArrayList<>(this.getPieces()));
      this.setGame(null);
      this.setActiveGame(null);
   }
}
