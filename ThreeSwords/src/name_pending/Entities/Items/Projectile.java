package name_pending.Entities.Items;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import name_pending.Game;
import name_pending.Entities.Entity;

public class Projectile extends Entity{
	//If the bullet will hurt the player or enemies
	private boolean friendly = false;
	private int damage = 0;
	
	public Projectile(Game theGame, int x, int y, int speed, int damage, boolean friendly) {
		super(theGame, x, y, speed, "Projectile");
		this.friendly = friendly;
		this.damage = damage;
		setSprite(theGame.getResourceLoader().getSprite("Arrow.png"));
		//getSprite().setRefPixel(getSprite().getWidth() / 2, getSprite().getWidth() / 2);
	}
	
	public void setDestination(Point point)
	{
		//Set the movement of the projectile to the point provided
		//projectile.setDx(dx);
		//projectile.setDy(dy);
		
		//TODO figure out how to make it move at a constant speed. Might need to have a speed limiter every frame instead of just in this method
		float ySpeed = getDy();
		float xSpeed = getDx();
		
		//ySpeed =  ySpeed * (float) (2.5 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
		//xSpeed = xSpeed * (float) (2.5 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
		setDx((int) ((point.x - getX()) / 8));
		setDy((int) ((point.y - getY()) / 8));
		
		//Make sure it is not over max speed
		/*Is positive
		if(getDx() > 0)
		{
			if(this.getDx() > this.getSpeed())
				setDx(getSpeed());
		}else //negitive
			if((getDx() * -1) < (this.getSpeed() * -1))
				setDx(getSpeed() * -1);
				
		//Is positive
		if(getDy() > 0)
		{
			if(this.getDy() > this.getSpeed())
				setDy(getSpeed());
		}else //negitive
			if((getDy() * -1) < (this.getSpeed() * -1))
				setDy(getSpeed() * -1);*/
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
		getSprite().setPosition(getX(), getY());
		getSprite().paint(g);
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
