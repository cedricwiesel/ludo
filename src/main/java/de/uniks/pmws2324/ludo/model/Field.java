package de.uniks.pmws2324.ludo.model;
import java.beans.PropertyChangeSupport;

public class Field
{
   public static final String PROPERTY_X = "x";
   public static final String PROPERTY_Y = "y";
   public static final String PROPERTY_PIECE = "piece";
   public static final String PROPERTY_PREVIOUS = "previous";
   public static final String PROPERTY_NEXT = "next";
   private int x;
   private int y;
   private Piece piece;
   private Field previous;
   private Field next;
   protected PropertyChangeSupport listeners;

   public int getX()
   {
      return this.x;
   }

   public Field setX(int value)
   {
      if (value == this.x)
      {
         return this;
      }

      final int oldValue = this.x;
      this.x = value;
      this.firePropertyChange(PROPERTY_X, oldValue, value);
      return this;
   }

   public int getY()
   {
      return this.y;
   }

   public Field setY(int value)
   {
      if (value == this.y)
      {
         return this;
      }

      final int oldValue = this.y;
      this.y = value;
      this.firePropertyChange(PROPERTY_Y, oldValue, value);
      return this;
   }

   public Piece getPiece()
   {
      return this.piece;
   }

   public Field setPiece(Piece value)
   {
      if (this.piece == value)
      {
         return this;
      }

      final Piece oldValue = this.piece;
      if (this.piece != null)
      {
         this.piece = null;
         oldValue.setPosition(null);
      }
      this.piece = value;
      if (value != null)
      {
         value.setPosition(this);
      }
      this.firePropertyChange(PROPERTY_PIECE, oldValue, value);
      return this;
   }

   public Field getPrevious()
   {
      return this.previous;
   }

   public Field setPrevious(Field value)
   {
      if (this.previous == value)
      {
         return this;
      }

      final Field oldValue = this.previous;
      if (this.previous != null)
      {
         this.previous = null;
         oldValue.setNext(null);
      }
      this.previous = value;
      if (value != null)
      {
         value.setNext(this);
      }
      this.firePropertyChange(PROPERTY_PREVIOUS, oldValue, value);
      return this;
   }

   public Field getNext()
   {
      return this.next;
   }

   public Field setNext(Field value)
   {
      if (this.next == value)
      {
         return this;
      }

      final Field oldValue = this.next;
      if (this.next != null)
      {
         this.next = null;
         oldValue.setPrevious(null);
      }
      this.next = value;
      if (value != null)
      {
         value.setPrevious(this);
      }
      this.firePropertyChange(PROPERTY_NEXT, oldValue, value);
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
      this.setPiece(null);
      this.setPrevious(null);
      this.setNext(null);
   }
}
