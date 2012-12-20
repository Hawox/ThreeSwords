package name_pending;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ConcurrentModificationException;

import javax.swing.JPanel;

import name_pending.Entities.Entity;

@SuppressWarnings("serial")
public class GameArea extends JPanel {
	private Game theGame;
	private Room currentRoom;
	//Where the center of the screen is in the view of the game world
	private Point originPoint = new Point(0,0);

	GameArea(Game theGame)
	{
		this.theGame = theGame;
		//this.setPreferredSize(new Dimension(8000, 6000));
		this.currentRoom = new Room(800,600);
		
	}

	

	public void paintComponent(Graphics g)
	{

		//clear the image to draw onto
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//get our new origin at the player
		if(getTheGame().getPlayer() != null)
		{
			//set at player
			Point center = new Point(getTheGame().getPlayer().getX(), getTheGame().getPlayer().getY());
			//move to "top left" of screen
			int newX = center.x - (this.getWidth() / 2);
			int newY = center.y - (this.getHeight() / 2);
			this.setOriginPoint(new Point(newX, newY));
		}

		//Draw our entities
		try{
			for(Entity e : theGame.getEntityHash())
			{
				e.paintMe(g);
			}
		}catch(ConcurrentModificationException e){}

		
		//Draw the ui last so it goes ontop of everything
		theGame.getUi().drawUI(g);
		
		//Draw point at screen center
		if(theGame.isDEBUG())
			g.drawRect(theGame.getFrame().getWidth()/2 - 1, theGame.getFrame().getHeight()/2 - 1, 3, 3);
		
		//TODO resize the game to fit the screen resolution
	}

	public Game getTheGame() {
		return theGame;
	}

	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}

	public Point getOriginPoint() {
		return originPoint;
	}

	public void setOriginPoint(Point reletivePoint) {
		this.originPoint = reletivePoint;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
}