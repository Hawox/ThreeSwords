package name_pending;

import java.awt.Color;
import java.awt.Dimension;
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
	private Point reletivePoint = new Point(0,0);

	GameArea(Game theGame)
	{
		this.theGame = theGame;
		this.setPreferredSize(new Dimension(8000, 6000));
		this.currentRoom = new Room(800,600);
	}

	

	public void paintComponent(Graphics g)
	{
		//reset the relative point
		if(theGame.getPlayer() != null)
			reletivePoint.setLocation(theGame.getPlayer().getX(), theGame.getPlayer().getY());
		else
			reletivePoint.setLocation(0,0);

		//clear the image to draw onto
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());

		//Draw our entities
		try{
			for(Entity e : theGame.getEntityHash())
			{
				e.paintMe(g);
			}
		}catch(ConcurrentModificationException e){}

		//Draw the ui last so it goes ontop of everything
		theGame.getUi().drawUI(g);
		g.drawRect(theGame.getFrame().getWidth()/2, theGame.getFrame().getHeight()/2, 1, 1);
	}

	public Game getTheGame() {
		return theGame;
	}

	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}

	public Point getReletivePoint() {
		return reletivePoint;
	}

	public void setReletivePoint(Point reletivePoint) {
		this.reletivePoint = reletivePoint;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
}



/* Out of date code
 * 
		//Get our double buffer ready
		//BufferStrategy bf = theGame.getFrame().getBufferStrategy();
		//Graphics g = null;

		//render 1 frame
		//do
		//{
		//These do/whiles ensure consistency of the buffer in case the back image is recreated
		//do
		//{
		//try
		//{
		//Get our drawing pane
		//g = bf.getDrawGraphics();
		
		//TODO get room size

		//clear the image to draw onto
		Graphics2D g2d = (Graphics2D) g;
		//g2d.transform(new AffineTransform(AffineTransform.getScaleInstance( (.5) , (.5) )));
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		//Draw our entities
		try{
			for(Entity e : theGame.getEntityHash())
			{
				e.paintMe(g2d);
			}
		}catch(ConcurrentModificationException e){}


		/*if(theGame.isDEBUG())
							{
								g.setColor(Color.GREEN);
								if(theGame.isDEBUG())
									if(theGame.getEntityHash().isEmpty())
										g.setColor(Color.RED);
								g.drawRect(400, 400+frames, 20, 20);
								frames++;
								g.setColor(Color.black);
								g.drawString(Integer.toString(frames), 100, 100);
								if(frames == 60)
									for(Entity e : theGame.getEntityHash())
										if(e.getName() == "Player"){
											//e.setDy(-4);
											//theGame.getEntityHash().remove(e);
										}
							}*

		//Draw the ui last so it goes ontop of everything
		theGame.getUi().drawUI(g2d);

		//Crop the draw window to the player if the player exists
		/*
		if(theGame.getPlayer() != null)
		{
			//Rectangle cropArea = new Rectangle();
			//JPanel gameArea = theGame.getGameArea();
			int newX = theGame.getPlayer().getX() - (getWidth() / 2);
			int newY = theGame.getPlayer().getY() - (getHeight() / 2);
			//make sure it does not draw outside the frames bounds
			if(newX < 0) newX = 0;
			if(newY < 0) newY = 0;
			if(newX > getWidth()) newX = getWidth();
			if(newY > getHeight()) newY = getHeight();
			//TODO Magic numbers
			//cropArea.setBounds(newX, newY, 800, 600);
			g2d.translate(newX, newY);
			//g2d.setClip(cropArea);
			*
			
		}*/

		//} finally {
		//You should dispose() of a Graphics object whern you are done with it
		//g.dispose();
		//}

		//}while (bf.contentsRestored());

		//Paint the graphics to the screen
		//bf.show();

		//Force the screen to draw the fame now
		//Toolkit.getDefaultToolkit().sync();

		//}while (bf.contentsLost());*/
