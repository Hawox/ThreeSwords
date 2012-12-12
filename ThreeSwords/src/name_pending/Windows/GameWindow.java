package name_pending.Windows;

import java.awt.event.MouseEvent;

import name_pending.Game;

/**
 * This will be the parent class for all the game windows that will popup in game
 */
public class GameWindow {
	
	//Variables all windows will need
	Game theGame = null;
	
	//This is assuming all my windows will be rectangles later on
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	//TODO get this setup to work with the sprites drawn by ChanceBandit
	
	//These are here incase I need them in the future.
	protected String name = "NoNameWindow";
	protected String windowType = "NoWindowType";
	
	/**
	 * 
	 * @param theGame Base Game instance
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	GameWindow(Game theGame, int x, int y, int width, int height)
	{
		this.theGame = theGame;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
	
	/**
	 * Will be triggered by the FrameMouseListener event
	 * @param event Event that was passed from the MouseListener
	 * @param eventType Compatable Strings "clicked", "pressed", and "released.
	 */
	public void mouseCheck(MouseEvent event,String eventType)
	{

	}
}