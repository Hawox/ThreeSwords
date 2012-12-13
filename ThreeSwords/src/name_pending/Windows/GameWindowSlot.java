package name_pending.Windows;

import java.awt.event.MouseEvent;

/**
 * The use will vary with these. This should just rest as a parent for other slot classes
 * @author Hawox
 *
 */
public class GameWindowSlot {
	//All should stem from window classes. Not sure if we will ever need them though
	GameWindow parentWindow;
	int x;
	int y;
	int width;
	int height;
	
	GameWindowSlot(GameWindow parentWindow, int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parentWindow = parentWindow;
	}
	
	public void mouseCheck(MouseEvent event,String eventType)
	{
		//If the mouse is right clicked on these a list window popup should appear
		//Might work with other ways of doing this later
		
		
		//Right clicked
		if(event.getButton() == MouseEvent.BUTTON2)
		{
			//See if it was within out borders
			int mousex = event.getPoint().x;
			int mousey = event.getPoint().y;
			//           check x                                      check y
			if(  (mousex > x) && (mousex < (width+x))  &&  (mousey > y) && (mousey < (y+height))  )
			{
				rightClicked();
			}
		}
	}
	
	public void rightClicked()
	{
		
	}
}

