package de.uniks.pmws2324.ludo.model;
import java.beans.PropertyChangeSupport;

public class Piece
{
   public static final String PROPERTY_FINISHED = "finished";
   public static final String PROPERTY_OWNER = "owner";
   public static final String PROPERTY_POSITION = "position";
   private boolean finished;
   private Player owner;
   private Field position;
   protected PropertyChangeSupport listeners;

   public boolean isFinished()
   {
      return this.finished;
   }

   public Piece setFinished(boolean value)
   {
      if (value == this.finished)
      {
         return this;
      }

      final boolean oldValue = this.finished;
      this.finished = value;
      this.firePropertyChange(PROPERTY_FINISHED, oldValue, value);
      return this;
   }

   public Player getOwner()
   {
      return this.owner;
   }

   public Piece setOwner(Player value)
   {
      if (this.owner == value)
      {
         return this;
      }

      final Player oldValue = this.owner;
      if (this.owner != null)
      {
         this.owner = null;
         oldValue.withoutPieces(this);
      }
      this.owner = value;
      if (value != null)
      {
         value.withPieces(this);
      }
      this.firePropertyChange(PROPERTY_OWNER, oldValue, value);
      return this;
   }

   public Field getPosition()
   {
      return this.position;
   }

   public Piece setPosition(Field value)
   {
      if (this.position == value)
      {
         return this;
      }

      final Field oldValue = this.position;
      if (this.position != null)
      {
         this.position = null;
         oldValue.setPiece(null);
      }
      this.position = value;
      if (value != null)
      {
         value.setPiece(this);
      }
      this.firePropertyChange(PROPERTY_POSITION, oldValue, value);
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
      this.setOwner(null);
      this.setPosition(null);
   }
}
