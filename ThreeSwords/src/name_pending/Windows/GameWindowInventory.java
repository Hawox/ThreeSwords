package name_pending.Windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import name_pending.Game;
import name_pending.Inventory;
import name_pending.Sprite;
import name_pending.Entities.Player;
import name_pending.Entities.Items.Item;

public class GameWindowInventory extends GameWindow {

	ArrayList<GameWindowInventorySlot>	inventorySlots = new ArrayList<GameWindowInventorySlot>();
	/**
	 * 
	 * @param theGame
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	GameWindowInventory(Game theGame, int x, int y, int width, int height) {
		super(theGame, x, y, width, height, "InventoryWindow", "playergui");
		//populateInventorySlots();
	}
	
	public void populateInventorySlots()
	{
		try{
		int row = 1;
		int vert = 1;
		int startx = x - 40;
		int starty = y - 40;
		int drawx = startx;
		int drawy = starty;
		Inventory inventory = theGame.getPlayer().getInventory();
		for(int i=0; i<inventory.getMaxSize(); i++)
		{
			this.inventorySlots.add(new GameWindowInventorySlot(this, drawx+(35*vert), drawy+(35*row), 32, 32, inventory.getItems().get(i), i));
			//check if at end of row
			vert++;
			if( (drawx+(35*vert+1) + 32) > theGame.getFrame().getWidth() )
			{
				drawx = startx;
				row ++;
				vert = 1;
			}
		}
		}catch(IndexOutOfBoundsException e){}
	}

	public void paintMe(Graphics g)
	{
		super.paintMe(g);
		//Only paint this if the player exists
		Player player = theGame.getPlayer();
		if(player != null)
		{
			//Fill
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, width, height);
			g.setColor(Color.CYAN);
			g.fillRect(x, y, 190, 15);

			//text
			g.setColor(Color.black);
			g.drawString("Inventory", 665, 312);

			//borders
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			g.drawRect(x, y, 190, 15);

			//Draw the items
			populateInventorySlots();
			for(GameWindowInventorySlot gwis : this.inventorySlots)
			{
				gwis.paintMe(g);
			}
		}
	}
	
	/**
	 * Internal classes!
	 */
	
	public class GameWindowInventorySlot extends GameWindowSlot
	{
		int slotNumber = 0;
		
		//These will contain items so we need them to hold item objects
		Item item;
		PopupListener popupListener = new PopupListener();
		
		GameWindowInventorySlot(GameWindow parentWindow, int x, int y,
				int width, int height, Item item, int slotNumber) {
			super(parentWindow, x, y, width, height);
			this.item = item;
			this.slotNumber = slotNumber;
		}
		
		public void paintMe(Graphics g)
		{
			g.setColor(Color.WHITE);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			g.setColor(Color.RED);
			g.drawString(Integer.toString(slotNumber), x+16, y+16);
			//TODO draw item sprite inside of it
			Sprite s = null;
			try{
				if(item != null)
					s = item.getSprite();
				if(s != null)
				{
					s.setPosition(x, y);
					s.paintOrig(g);
				}
			}catch(IndexOutOfBoundsException e){}
		}
		
		
		
		/**
		 * Menus we want are:
		 * 	Use
		 *  Equip
		 *  Move to slot x
		 *  Drop
		 * @return 
		 */
		
		//Needed to get our popup working
		JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItem;
		
		public void rightClicked()
		{
			//create our menu
			menuItem = new JMenuItem("Use");
			menuItem.addActionListener((ActionListener) popupListener);
			popup.add(menuItem);
			menuItem = new JMenuItem("Equip");
			menuItem.addActionListener((ActionListener) popupListener);
			popup.add(menuItem);
			menuItem = new JMenuItem("Move To Slot x");
			menuItem.addActionListener((ActionListener) popupListener);
			popup.add(menuItem);
			menuItem = new JMenuItem("Drop");
			menuItem.addActionListener((ActionListener) popupListener);
			popup.add(menuItem);
		}
		
		//needed to get input from the popup menu
		class PopupListener extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(),
							e.getX(), e.getY());
				}
			}
		}
	}
}
		/*
			//...where instance variables are declared:
			JPopupMenu popup;

			    //...where the GUI is constructed:
			    //Create the popup menu.
			    popup = new JPopupMenu();
			    menuItem = new JMenuItem("A popup menu item");
			    menuItem.addActionListener(this);
			    popup.add(menuItem);
			    menuItem = new JMenuItem("Another popup menu item");
			    menuItem.addActionListener(this);
			    popup.add(menuItem);

			    //Add listener to components that can bring up popup menus.
			    MouseListener popupListener = new PopupListener();
			    output.addMouseListener(popupListener);
			    menuBar.addMouseListener(popupListener);
			...
			class PopupListener extends MouseAdapter {
			    public void mousePressed(MouseEvent e) {
			        maybeShowPopup(e);
			    }

			    public void mouseReleased(MouseEvent e) {
			        maybeShowPopup(e);
			    }

			    private void maybeShowPopup(MouseEvent e) {
			        if (e.isPopupTrigger()) {
			            popup.show(e.getComponent(),
			                       e.getX(), e.getY());
			        }
			    }
		}
		
	}
}
*/