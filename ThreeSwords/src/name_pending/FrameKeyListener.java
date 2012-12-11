package name_pending;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import name_pending.Entities.Entity;

public class FrameKeyListener implements KeyListener{
	
	//needs an instance of Game so it can check keys for entities
	Game theGame;

	FrameKeyListener(Game theGame)
	{
		this.theGame = theGame;
	}

	//TODO Change key input
	/*** Not sure of I want it to check the key input for all entities if it's not going to be needed
	//With the small game I am planning it should be fine, however the code is very unnessicary if
	not every entity is checking for keyboard input
	**/
	
	@Override
	public void keyPressed(KeyEvent event) {
		for (Entity e: theGame.getEntityHash())
		{
			e.keyCheck(event.getKeyCode(), true);
		}
		//Check if this effects the ui
		theGame.getUi().keyCheck(event.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		for (Entity e: theGame.getEntityHash())
		{
			e.keyCheck(event.getKeyCode(), false);
		}
		//Check if this effects the ui
		theGame.getUi().keyCheck(event.getKeyCode(), false);
	}

	@Override
	//Not sure if I am going to need this in the current state of the game
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}


}
