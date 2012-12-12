package name_pending.Windows;

import java.awt.event.MouseEvent;
import java.util.HashSet;

import name_pending.Game;

/**
 * This class will be the base controller for all the windows
 * I.E. What will be used to create and destory windows etc
 * @author Hawox
 *
 */
public class GameWindowManager {

	protected Game theGame = null;
	//Will house all the game active windows
	private HashSet<GameWindow> gameWindowHash = new HashSet<GameWindow>();
	
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
		for (GameWindow gw: getGameWindowHash())
			gw.keyCheck(keyCode, pressed);
	}

	/**
	 * Will be triggered by the FrameMouseListener event
	 * @param event Event that was passed from the MouseListener
	 * @param eventType Compatable Strings "clicked", "pressed", and "released.
	 */
	public void mouseCheck(MouseEvent event,String eventType)
	{
		for(GameWindow gw: getGameWindowHash())
			gw.mouseCheck(event, eventType);
	}

	public Game getTheGame() {
		return theGame;
	}

	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}

	public HashSet<GameWindow> getGameWindowHash() {
		return gameWindowHash;
	}

	public void setGameWindowHash(HashSet<GameWindow> gameWindowHash) {
		this.gameWindowHash = gameWindowHash;
	}
}
