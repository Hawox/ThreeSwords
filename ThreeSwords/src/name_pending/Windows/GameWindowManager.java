package name_pending.Windows;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
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
	
	//TODO Maybe make a global hotkey manager at somepoint
	//All the hotkeys that are used in the inventory manager
	int HOTKEY_inventory = KeyEvent.VK_I;

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
		
		//Should we make some windows?
		if(keyCode == HOTKEY_inventory)
		{
			getGameWindowHash().add(new GameWindowInventory(theGame, 600, 300, 190, 290));
		}
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

	/**
	 * Paint all the windows ALL THE WINDOWS!
	 * @param g Graphics
	 */
	public void paintWindows(Graphics g)
	{
		for(GameWindow gw: getGameWindowHash())
			gw.paintMe(g);
	}
	
	
	/**
	 * Getters and setters
	 */
	
	
	public Game getTheGame() {
		return theGame;
	}

	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}

	private HashSet<GameWindow> getGameWindowHash() {
		return gameWindowHash;
	}

	@SuppressWarnings("unused")
	private void setGameWindowHash(HashSet<GameWindow> gameWindowHash) {
		this.gameWindowHash = gameWindowHash;
	}
}
