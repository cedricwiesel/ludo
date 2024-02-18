package de.uniks.pmws2324.ludo.model;

public class HomeField extends Field
{
   public static final String PROPERTY_PLAYER = "player";
   public static final String PROPERTY_COLOR = "color";
   private Player player;
   private int color;

   public Player getPlayer()
   {
      return this.player;
   }

   public HomeField setPlayer(Player value)
   {
      if (this.player == value)
      {
         return this;
      }

      final Player oldValue = this.player;
      if (this.player != null)
      {
         this.player = null;
         oldValue.withoutHomeFields(this);
      }
      this.player = value;
      if (value != null)
      {
         value.withHomeFields(this);
      }
      this.firePropertyChange(PROPERTY_PLAYER, oldValue, value);
      return this;
   }

   public int getColor()
   {
      return this.color;
   }

   public HomeField setColor(int value)
   {
      if (value == this.color)
      {
         return this;
      }

      final int oldValue = this.color;
      this.color = value;
      this.firePropertyChange(PROPERTY_COLOR, oldValue, value);
      return this;
   }

   @Override
   public void removeYou()
   {
      super.removeYou();
      this.setPlayer(null);
   }
}
