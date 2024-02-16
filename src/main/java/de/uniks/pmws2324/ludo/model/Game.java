package de.uniks.pmws2324.ludo.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

public class Game
{
   public static final String PROPERTY_PHASE = "phase";
   public static final String PROPERTY_ROLL = "roll";
   public static final String PROPERTY_PLAYERS = "players";
   public static final String PROPERTY_ACTIVE_PLAYER = "activePlayer";
   private Phase phase;
   private int roll;
   private List<Player> players;
   private Player activePlayer;
   protected PropertyChangeSupport listeners;

   public Phase getPhase()
   {
      return this.phase;
   }

   public Game setPhase(Phase value)
   {
      if (Objects.equals(value, this.phase))
      {
         return this;
      }

      final Phase oldValue = this.phase;
      this.phase = value;
      this.firePropertyChange(PROPERTY_PHASE, oldValue, value);
      return this;
   }

   public int getRoll()
   {
      return this.roll;
   }

   public Game setRoll(int value)
   {
      if (value == this.roll)
      {
         return this;
      }

      final int oldValue = this.roll;
      this.roll = value;
      this.firePropertyChange(PROPERTY_ROLL, oldValue, value);
      return this;
   }

   public List<Player> getPlayers()
   {
      return this.players != null ? Collections.unmodifiableList(this.players) : Collections.emptyList();
   }

   public Game withPlayers(Player value)
   {
      if (this.players == null)
      {
         this.players = new ArrayList<>();
      }
      if (!this.players.contains(value))
      {
         this.players.add(value);
         value.setGame(this);
         this.firePropertyChange(PROPERTY_PLAYERS, null, value);
      }
      return this;
   }

   public Game withPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Player value)
   {
      if (this.players != null && this.players.remove(value))
      {
         value.setGame(null);
         this.firePropertyChange(PROPERTY_PLAYERS, value, null);
      }
      return this;
   }

   public Game withoutPlayers(Player... value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

   public Game withoutPlayers(Collection<? extends Player> value)
   {
      for (final Player item : value)
      {
         this.withoutPlayers(item);
      }
      return this;
   }

   public Player getActivePlayer()
   {
      return this.activePlayer;
   }

   public Game setActivePlayer(Player value)
   {
      if (this.activePlayer == value)
      {
         return this;
      }

      final Player oldValue = this.activePlayer;
      if (this.activePlayer != null)
      {
         this.activePlayer = null;
         oldValue.setActiveGame(null);
      }
      this.activePlayer = value;
      if (value != null)
      {
         value.setActiveGame(this);
      }
      this.firePropertyChange(PROPERTY_ACTIVE_PLAYER, oldValue, value);
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

   public void removeYou()
   {
      this.withoutPlayers(new ArrayList<>(this.getPlayers()));
      this.setActivePlayer(null);
   }
}
