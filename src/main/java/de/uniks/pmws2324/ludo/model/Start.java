package de.uniks.pmws2324.ludo.model;

public class Start extends Field
{
   public static final String PROPERTY_PLAYER = "player";
   private Player player;

   public Player getPlayer()
   {
      return this.player;
   }

   public Start setPlayer(Player value)
   {
      if (this.player == value)
      {
         return this;
      }

      final Player oldValue = this.player;
      if (this.player != null)
      {
         this.player = null;
         oldValue.setStart(null);
      }
      this.player = value;
      if (value != null)
      {
         value.setStart(this);
      }
      this.firePropertyChange(PROPERTY_PLAYER, oldValue, value);
      return this;
   }

   @Override
   public void removeYou()
   {
      super.removeYou();
      this.setPlayer(null);
   }
}
