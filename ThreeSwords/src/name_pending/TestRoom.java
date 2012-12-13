package name_pending;

import java.awt.Point;

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
		theGame.changeCursor("CursorCrossheir.png", new Point(15,15));
		theGame.getEntityHash().add(new Player(theGame, 200, 200));
		//theGame.getEntityHash().add(new Player(theGame, 400, 100));
		//create a bow
		//AH HAH! Note to self, you can't use a single Item for multiple ItemDrops. That was stupid of me.
		//ItemBow bow = new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null);
		//put it in the players inventory

		for(int i=0; i<10; i++){
			ItemDrop itemDrop = new ItemDrop(theGame, new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null), 50 + (50*i), 50 + (50*i));
			theGame.getEntityHash().add(itemDrop);
		}
		/*
		ItemDrop itemDropa = new ItemDrop(theGame, new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null), 300, 350);
		theGame.getEntityHash().add(itemDropa);
		
		ItemDrop itemDropb = new ItemDrop(theGame, new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null), 300, 400);
		theGame.getEntityHash().add(itemDropb);*/
	}

}
