package name_pending.Windows;

import name_pending.Game;

/**
 * This class will be the base controller for all the windows
 * I.E. What will be used to create and destory windows etc
 * @author Hawox
 *
 */
public class GameWindowManager {

	protected Game theGame = null;
	
	public GameWindowManager(Game game)
	{
		this.theGame = game;
	}
	
	/**
	 * Will need to check on mouse events and key events to know when clicked or when it needs to close, etc
	 * Check for keyboard input
	 * @param keyCode key used
	 * @param pressed state = [true] pressed | [false] released
	 */
	public void keyCheck(int keyCode,boolean pressed) //copied from Entity
	{

	}
}
