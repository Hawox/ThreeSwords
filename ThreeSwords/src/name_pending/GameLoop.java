package name_pending;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ConcurrentModificationException;
import java.util.HashSet;

import name_pending.Entities.Entity;

public class GameLoop implements Runnable{

	//Pass an instance of theGame to handle all the fun stuff
	Game theGame = null;

	int frames = 0;

	GameLoop(Game game)
	{
		this.theGame = game;
	}

	@Override
	public void run() {
		do{
		HashSet<Entity> entities = theGame.getEntityHash();
		
		/***Run entity step events***/
		this.steps(entities);
		
		/***Check for collisions***/
		this.collisions(entities);

		/***Paint everything***/
		this.paintStuff(entities);

		//TODO Somehow make this actually 30 fps while accounting for the lag while the script runs
		//1000 ms = 1 second
		//33 ms is around 30 fps
		try {
			Thread.sleep(33);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //We want to get 30 fpsish
	}while(true);
		//realized this causes problems, not sure what I was thinking, let's do a while loop
		//this.run(); // we never want the game loop to stop running
	}

	/**
	 * Moved down here to make the run methods not as cramped with data
	 * @param entities
	 */
	private void collisions(HashSet<Entity> entities)
	{
		try{
		for(Entity e : entities)
		{
			e.checkCollisions();
		}
		}catch(ConcurrentModificationException e){}
	}

	private void paintStuff(HashSet<Entity> entities)
	{
		//Get our double buffer ready
		BufferStrategy bf = theGame.getFrame().getBufferStrategy();
		Graphics g = null;

		//render 1 frame
		do
		{
			//These do/whiles ensure consistency of the buffer in case the back image is recreated
			do
			{
				try
				{
					//Get our drawing pane
					g = bf.getDrawGraphics();
					
					//clear the image to draw onto
					g.setColor(Color.GRAY);
					g.fillRect(0, 0, theGame.getFrame().getWidth(), theGame.getFrame().getHeight());

					//Draw our entities
					for(Entity e : entities)
					{
						e.paintMe(g);
					}
					

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
					}*/
					
					//Draw the ui last so it goes ontop of everything
					theGame.getUi().drawUI(g);

				} finally {
					//You should dispose() of a Graphics object whern you are done with it
					g.dispose();
				}

			}while (bf.contentsRestored());

			//Paint the graphics to the screen
			bf.show();

			//Force the screen to draw the fame now
			Toolkit.getDefaultToolkit().sync();

		}while (bf.contentsLost());
	}

	private void steps(HashSet<Entity> entities)
	{
		for(Entity e : entities)
		{
			e.step();
		}
	}

}
