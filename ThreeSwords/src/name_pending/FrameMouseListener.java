package name_pending;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import name_pending.Entities.Entity;

public class FrameMouseListener implements MouseListener{
	
	Game theGame = null;
	
	public FrameMouseListener(Game game)
	{
		this.theGame = game;
	}

	
	
	@Override
	public void mouseClicked(MouseEvent event)
	{
		sendEvent(event, "clicked");
	}

	@Override
	public void mousePressed(MouseEvent event)
	{
		sendEvent(event, "pressed");
	}

	@Override
	public void mouseReleased(MouseEvent event)
	{
		sendEvent(event, "released");
	}
	
	/**
	 * Noticed both events code was the same so I moved there code to this method
	 * @param event
	 * @param eventType
	 */
	protected void sendEvent(MouseEvent event, String eventType)
	{	
		for (Entity e: theGame.getEntityHash())
		{
			e.mouseCheck(event, eventType);
		}

		this.theGame.getGameWindowManager().mouseCheck(event, eventType);
		/* Moved this to the GameWindowManager
		for (GameWindow gw: theGame.getGameWindowHash())
		{
			gw.mouseCheck(event, eventType);
		}*/
	}
	
	//Not so important to me for the time being
	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

}
