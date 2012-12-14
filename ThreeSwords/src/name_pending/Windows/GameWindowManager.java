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
	int HOTKEY_equipment = KeyEvent.VK_E;

	protected Game theGame = null;
	//Will house all the game active windows
	private HashSet<GameWindow> gameWindowHash = new HashSet<GameWindow>();
	
	public GameWindowManager(Game game)
	{
		this.theGame = game;
		//create our inventory
		getGameWindowHash().add(new GameWindowInventory(theGame, 600, 300, 190, 290));
		getGameWindowHash().add(new GameWindowEquipment(theGame, 300, 300, 190, 290));
	}
	
	/**
	 * Will need to check on mouse events and key events to know when clicked or when it needs to close, etc
	 * Check for keyboard input
	 * @param keyCode key used
	 * @param pressed state = [true] pressed | [false] released
	 */
	public void keyCheck(int keyCode,boolean pressed) //copied from Entity
	{
		if(pressed == false)
		{
			if(keyCode == HOTKEY_inventory)
			{
				for(GameWindow gw : gameWindowHash)
				{
					if(gw.getName() == "InventoryWindow")
					{
						gw.toggleVisibility();
						break;
					}
				}
			}
			
			if(keyCode == HOTKEY_equipment)
			{
				for(GameWindow gw : gameWindowHash)
				{
					if(gw.getName() == "EquipmentWindow")
					{
						gw.toggleVisibility();
						break;
					}
				}
			}
		}
		/* Windows will not be visiable and non-visiable instead of deleting and creating them
		 * Not sure why I thought that was a good idea
		for (GameWindow gw: getGameWindowHash())
			gw.keyCheck(keyCode, pressed);
		boolean hasInventory = false;
		//Should we make some windows?
		
		if(keyCode == HOTKEY_inventory)
		{
			for (GameWindow gw : this.gameWindowHash)
			{
				if(gw.getName() == "InventoryWindow")
				{
					//remove that window
					gw.onDelete();
					hasInventory = true;
					break;
				}
			}
			if(hasInventory == false)
				getGameWindowHash().add(new GameWindowInventory(theGame, 600, 300, 190, 290));
		}*/
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
	
	/*
	 * Updates all the windows
	 */
	public void updateWindows()
	{
		for(GameWindow gw : getGameWindowHash())
			gw.update();
	}
	
	/*
	 * Override to update a specific window
	 */
	public void updateWindows(String name)
	{
		for(GameWindow gw : getGameWindowHash())
			if(gw.getName() == name)
				gw.update();
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

	public HashSet<GameWindow> getGameWindowHash() {
		return gameWindowHash;
	}

	@SuppressWarnings("unused")
	private void setGameWindowHash(HashSet<GameWindow> gameWindowHash) {
		this.gameWindowHash = gameWindowHash;
	}
}
