package name_pending.Entities.Items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import name_pending.Game;
import name_pending.Entities.Entity;

public class Projectile extends Entity{
	//If the bullet will hurt the player or enemies
	private boolean friendly = false;
	
	public Projectile(Game theGame, int x, int y, boolean friendly) {
		super(theGame, x, y, "Projectile");
		this.friendly = friendly;
	}
	
	public void setDestination(Point point)
	{
		//Set the movement of the projectile to the point provided
		//projectile.setDx(dx);
		//projectile.setDy(dy);
		float ySpeed = getDy();
		float xSpeed = getDx();
		
		ySpeed =  ySpeed * (float) (2.5 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
		xSpeed = xSpeed * (float) (2.5 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
		setDx((point.x - getX()) / 3);
		setDy((point.y - getY()) / 3);
	}

	public void onCreate()
	{
		super.onCreate();
	}

	public void onDelete()
	{
		super.onDelete();
	}

	public void checkCollisions()
	{
		super.checkCollisions();
	}

	public void paintMe(Graphics g)
	{
		super.paintMe(g);
		//TODO make this a sprite that sets an angle based on the direction it is moving
		g.setColor(Color.BLACK);
		g.drawOval(getX(), getY(), 5, 5);
	}

	public void step()
	{
		super.step();
	}

	public void keyCheck(int keyCode,boolean pressed)
	{
		super.keyCheck(keyCode, pressed);
	}

	public void mouseCheck(MouseEvent event,String eventType)
	{
		super.mouseCheck(event, eventType);
	}
	
	
	
	
	
	
	public boolean isFriendly() {
		return friendly;
	}

	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}
	
}
