package name_pending;

import name_pending.Entities.Player;
import name_pending.Entities.Items.ItemBow;
import name_pending.Entities.Items.ItemDrop;

/**
 * Used to check stuff out in the games test room
 *
 */
public class TestRoom {
	
	Game theGame;
	
	TestRoom(Game theGame)
	{
		this.theGame = theGame;
	}
	
	public void start()
	{
		//add the player
		//change the cursor
		theGame.changeCursor("CursorCrossheir.png");
		theGame.getEntityHash().add(new Player(theGame, 200, 200));
		//theGame.getEntityHash().add(new Player(theGame, 400, 100));
		//create a bow
		//AH HAH! Note to self, you can't use a single Item for multiple ItemDrops. That was stupid of me.
		//ItemBow bow = new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null);
		//put it in the players inventory

		ItemDrop itemDrop = new ItemDrop(theGame, new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null), 100, 100);
		theGame.getEntityHash().add(itemDrop);
		
		ItemDrop itemDropa = new ItemDrop(theGame, new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null), 300, 350);
		theGame.getEntityHash().add(itemDropa);
		
		ItemDrop itemDropb = new ItemDrop(theGame, new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null), 300, 400);
		theGame.getEntityHash().add(itemDropb);
	}

}
