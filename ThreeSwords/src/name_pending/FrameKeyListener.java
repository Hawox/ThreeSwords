package name_pending;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import name_pending.Entities.Entity;

public class FrameKeyListener implements KeyListener{
	
	//needs an instance of Game so it can check keys for entities
	protected Game theGame;

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
		sendEvent(event.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		sendEvent(event.getKeyCode(), false);
	}
	
	/**
	 * Noticed both events code was the same so I moved there code to this method
	 * @param pressed
	 */
	protected void sendEvent(int keyCode, boolean pressed)
	{
		for (Entity e: theGame.getEntityHash())
		{
			e.keyCheck(keyCode, pressed);
		}
		
		//Check if the windows manager needs it
		theGame.getGameWindowManager().keyCheck(keyCode, pressed);
				
		/* Moved to GameWindowManager
		 * for (GameWindow gw: theGame.getGameWindowHash())
		{
			gw.keyCheck(keyCode, pressed);
		}*/
		
		//Check if this effects the ui
		theGame.getUi().keyCheck(keyCode, pressed);
	}

	@Override
	//Not sure if I am going to need this in the current state of the game
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}


}
