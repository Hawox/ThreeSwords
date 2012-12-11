package name_pending;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Vector;

import name_pending.Entities.Player;

/**
 * This will be in charge of drawin everything in the game that is considered the game UI
 * @author Hawox
 *
 */
public class UI {
	private Game theGame;
	
	//Decides whether or not game windows are drawn
	private boolean doDrawInventory = false;
	
	UI(Game game)
	{
		theGame = game;
	}
	
	
	//Do all the fun drawy stuff when this is called
	public void drawUI(Graphics g)
	{	
		//Draw player stuff only if there is a player on screen
		Player player = theGame.getPlayer();
		if(player != null)
			drawPlayerStuff(g, player);
		
		/* Draw active windows */
		if(doDrawInventory)
			drawInventoryWindow(g, player);
		
		
		/**DEBUG STUFF!**/
	//	if(theGame.isDEBUG())
	//		drawDebugStuff(g);
	}
	
	private void drawPlayerStuff(Graphics g, Player player)
	{
		//Draw player related HUD
		//Level
		g.setColor(Color.BLACK);
		g.drawString("Player Level: " + Integer.toString(player.getLevel()), 700, 50);
		//Health bar
		g.setColor(Color.GREEN);
		g.drawString("Health: " + Integer.toString(player.getHealth()), 700, 65);
		//Mana
		g.setColor(Color.BLUE);
		g.drawString("Mana: " + Integer.toString(player.getMana()), 700, 80);
		//Stamina
		g.setColor(Color.RED);
		g.drawString("Stamina: " + Integer.toString(player.getStamina()), 700, 95);
	}


	@SuppressWarnings("unused")
	private void drawDebugStuff(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.drawString("Sprite Array Size: " + theGame.getResourceLoader().getImages().size(), 200, 200);
		String[] wrap = UI.wrapText(theGame.getResourceLoader().getImages().toString(), 100);
		for(int i=0; i<wrap.length; i++)
		{
			g.drawString(wrap[i], 200, 215+(15*i));
		}
		Sprite s = theGame.getResourceLoader().getSprite("Error.png");
		g.drawString(s.toString(), 200, 150);
		s.setPosition(100, 100);
		s.paint(g);
		
	}
	
	
	/** Methods for drawing all of the cool windows **/
	private void drawInventoryWindow(Graphics g, Player player)
	{
		//Fill
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(600, 300, 190, 290);
		g.setColor(Color.CYAN);
		g.fillRect(600, 300, 190, 15);
		
		//text
		g.setColor(Color.black);
		g.drawString("Inventory", 665, 312);
		
		//borders
		g.setColor(Color.BLACK);
		g.drawRect(600, 300, 190, 290);
		g.drawRect(600, 300, 190, 15);
		
		//Draw spaces for the items
		int row = 1;
		int vert = 1;
		int startx = 615 - 40;
		int starty = 330 - 40;
		int drawx = startx;
		int drawy = starty;
		for(int i=0; i<player.getInventory().getMaxSize(); i++)
		{
			
			g.setColor(Color.WHITE);
			g.fillRect(drawx+(35*vert), drawy+(35*row), 32, 32);
			g.setColor(Color.BLACK);
			g.drawRect(drawx+(35*vert), drawy+(35*row), 32, 32);
			g.setColor(Color.RED);
			g.drawString(Integer.toString(i), drawx+(35*vert)+16, drawy+(35*row)+16);
			//TODO draw item sprite inside of it
			Sprite s = null;
			try{
				if(player.getInventory().getItems().get(i) != null)
					s = player.getInventory().getItems().get(i).getSprite();
					if(s != null)
					{
						s.setPosition(drawx+(35*vert), drawy+(35*row));
						s.paintOrig(g);
					}
			}catch(IndexOutOfBoundsException e){}
			
			//check if at end of row
			vert++;
			if( (drawx+(35*vert+1) + 32) > theGame.getFrame().getWidth() )
			{
				drawx = startx;
				row ++;
				vert = 1;
			}
		}
	}
	
	
	/** Keycheck method **/
	public void keyCheck(int keyCode, boolean pressed)
	{
		//Show or hide the inventory based on it's previous state
		if(keyCode == KeyEvent.VK_I)
		{
			if(pressed)
			{
				if(this.isDrawInventory())//inventory is open
					this.setDrawInventory(false); //hide it
				else //inventory is closed
					this.setDrawInventory(true); //show it
			}
		}
	}
	
	/**
	 * Static Methods
	 */
	
	/**
	 * Obtained at http://progcookbook.blogspot.com/2006/02/text-wrapping-function-for-java.html
	 * Author Robert Hanson (I think)
	 * @param text
	 * @param len
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static String [] wrapText (String text, int len)
	{
	  // return empty array for null text
	  if (text == null)
	  return new String [] {};

	  // return text if len is zero or less
	  if (len <= 0)
	  return new String [] {text};

	  // return text if less than length
	  if (text.length() <= len)
	  return new String [] {text};

	  char [] chars = text.toCharArray();
	  Vector lines = new Vector();
	  StringBuffer line = new StringBuffer();
	  StringBuffer word = new StringBuffer();

	  for (int i = 0; i < chars.length; i++) {
	    word.append(chars[i]);

	    if (chars[i] == ' ') {
	      if ((line.length() + word.length()) > len) {
	        lines.add(line.toString());
	        line.delete(0, line.length());
	      }

	      line.append(word);
	      word.delete(0, word.length());
	    }
	  }

	  // handle any extra chars in current word
	  if (word.length() > 0) {
	    if ((line.length() + word.length()) > len) {
	      lines.add(line.toString());
	      line.delete(0, line.length());
	    }
	    line.append(word);
	  }

	  // handle extra line
	  if (line.length() > 0) {
	    lines.add(line.toString());
	  }

	  String [] ret = new String[lines.size()];
	  int c = 0; // counter
	  for (Enumeration e = lines.elements(); e.hasMoreElements(); c++) {
	    ret[c] = (String) e.nextElement();
	  }

	  return ret;
	}
	
	
	/**
	 * 
	 * Getters and setters
	 * @return
	 */
	
	
	public Game getTheGame() {
		return theGame;
	}


	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}


	public boolean isDrawInventory() {
		return doDrawInventory;
	}


	public void setDrawInventory(boolean drawInventory) {
		this.doDrawInventory = drawInventory;
	}
	
	//All of it's other draw methods should be private because nothing else should call them
}