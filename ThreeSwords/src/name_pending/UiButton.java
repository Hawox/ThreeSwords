package name_pending;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import name_pending.Entities.Entity;

//TODO Make different classes for the different types
public class UiButton {

		//All should stem from window classes. Not sure if we will ever need them though
		private int x;
		private int y;
		private Sprite sprite;
		
		TheMouseListener theMouseListener = new TheMouseListener(this);
		//what happens when the button is pressed
		private String type;
		
		private Game theGame;
				
		UiButton(Game game, int x, int y, String type)
		{
			this.x = x;
			this.y = y;
			this.type = type;
			this.theGame = game;
			if(type == "inventory");
				this.sprite = game.getResourceLoader().getSprite("InventoryButton.png").clone();
			if(type == "equipment")
				this.sprite = game.getResourceLoader().getSprite("EquipmentButton.png").clone();
			this.sprite.setPosition(x, y);
			//theMouseListener
			game.getFrame().addMouseListener(theMouseListener);
		}
		
		public void paintMe(Graphics g)
		{
			sprite.paint(g);
		}
		
		public void mouseCheck(MouseEvent event,String eventType)
		{
			this.sprite = this.theGame.getResourceLoader().getSprite("Arrow.png");
			if(event.getButton() == MouseEvent.BUTTON1)
			{
				//See if it was within out borders
				int mousex = event.getPoint().x;
				int mousey = event.getPoint().y;
				//           check x                                      check y
				if(  (mousex > x) && (mousex < (sprite.getWidth()+x))  &&  (mousey > y) && (mousey < (y+sprite.getHeight()))  )
				{
					clicked(event);
				}
			}
		}
		
		protected void clicked(MouseEvent event)
		{
			//show inventory
			//if(type == "inventory")
			//{
				theGame.getGameWindowManager().openInventory();
			//}
			//else if(type == "equipment") //show equipment
			//{
				theGame.getGameWindowManager().openEquipment();
			//}
		}
		
		class TheMouseListener implements MouseListener
		{
			UiButton parrent;
			
			TheMouseListener(UiButton p)
			{
				this.parrent = p;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				parrent.mouseCheck(e, "release");
				//for (UiButton uib: theGame.getUi().getUiButtons())
				//{
				//	uib.mouseCheck(e, "release");
				//}
			}
			
		}


		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
}
