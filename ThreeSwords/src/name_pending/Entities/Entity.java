package name_pending.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import name_pending.Game;
import name_pending.Resistance;
import name_pending.Sprite;

/****
 * 
 * @author Hawox
 *
 * All entities in the game should have this as a parent 
 * 	-so they can have their internal events processed
 * 
 * Here to copy paste important methods to other subclasses


public void onCreate()
{

}

public void onDelete()
{

}

public void checkCollisions()
{

}

public void paintMe()
{

}

public void step()
{

}

public void keyCheck(int keyCode,boolean pressed)
{

}

public void mouseCheck(MouseEvent event,String eventType)
	{

	}


 */
public abstract class Entity {

	//Basic variables all entity should have
	private int x = 0;
	private int y = 0;

	//The distance they will move in the next step
	private int dx = 0;
	private int dy = 0;

	//Where they were related to sprite drawn to the screen last frame
	private int lastViewX = 0;
	private int lastViewY = 0;
	
	//If it's an entity in the world it should have a sprite
	private Sprite sprite = null;
	
	//How fast the entity moves, but also used in calculation crit/dodge
	private int speed = 0; 
	
	//true if things can not pass threw them
	private boolean solid = false;
	private boolean moveThrewSolids = false;
	
	//Gets input from HID devices
	private EntityHIDListener entityHIDListener;
	
	//Direction the entity is facing
	private String direction = "right";
	
	//If they are on the players side or not
	private boolean friendly;

	/*
	 * I didn't want to add this before, now I am trying to remember why.
	 * Oh well, lets see how it ends up
	 */
	private Game theGame = null;

	//Defaults to delete me so if a blank entity exists it will be removed from the entity hash
	private String name = "deleteMe"; //Name before list edit
	private String nameInList = "deleteMe"; //this name will have a number added to the end so entities in the list don't all have the same name
	
	Point reletivePosition = new Point(x,y);

	/**
	 * 
	 * @param theGame
	 * @param x
	 * @param y
	 * @param SpeedString
	 */
	protected Entity(Game theGame, int x, int y, int Speed, String name)
	{
		this.theGame = theGame;
		this.setX(x);
		this.setY(y);
		this.setSpeed(speed);
		//Lets see if changing their names helps with java.util.ConcurrentModificationException
		int number = 0;
		for(Entity e : theGame.getEntityHash())
			if(e.getName() == name.split("_")[0])
				number++;
		//Name in the entity
		this.name = name;
		//Name in the list
		this.nameInList = (name + "_" + Integer.toString(number));
		onCreate();
		//All entities should set their sprites in the onCreate, add this here to give each entity it's own sprite object
		//setSprite(getSprite().clone()); //Not needed?
		//Start the sprites animation
		this.sprite.setAnimation(1);
		
		//add listener
		entityHIDListener = new EntityHIDListener();
		getTheGame().getFrame().addKeyListener(entityHIDListener);
		getTheGame().getGameArea().addMouseListener(entityHIDListener);
	}

	//The script that is run for the entity upon it's creation
	public void onCreate()
	{
		//Setup the sprite
		//setSprite(theGame.getSpriteLoader().getSprite("Error.png"));
		//if(theGame.getResourceLoader().getImages().containsKey("Error.png"))
		setSprite(theGame.getResourceLoader().getSprite("Error.png"));
	}

	//Objects can't really be deleted, so this is ran when the object is removed form the entity list
	public void onDelete()
	{
		//I don't want to save a reference to theGame in each object
		//So we are renaming them "deleteMe" and having theGame remove them from it's list
		this.setName("deleteMe");
		theGame.getEntityHash().remove(this);
	}

	//Collision processing
	/**
	 * @return false if it should not move to the next space
	 */
	public boolean checkCollisions()
	{
		//see if you are hit by a projectile
		HashSet<Entity> collision = this.checkForCollision();
		if(collision != null)
		{
			for(Entity e : collision)
			{
				if(e instanceof Projectile)
				{
					Projectile p = (Projectile) e;
					//Take damage and delete projectile

					//friendly entities don't take damage from friendly projectiles
					if(this.friendly != e.friendly)
					{
						//if being take damage, otherwise do nothing but block the projectile for now
						if(this instanceof Being)
						{
							Being being = (Being) this;
							being.takeDamage(p.getDamage(), null);
							
							//if you take damage from an opposite team and are an npc, aggro onto what shot at you
							if(this instanceof Enemy)
							{
								Enemy enemy = (Enemy) being;
								enemy.setAggroed(true);
								enemy.setAggroedTo(p.getSource());
							}
						}
						
						//delete projectile
						e.onDelete();
					}
				}
			}
		}
		
		//don't move if you collide with a solid
		if(this.moveThrewSolids == false)
		{
			try{
				for(Entity e : this.checkForCollision())
				{
					if(e.isSolid())
					{
						//undo move
						return false;
					}
				}
			}catch(NullPointerException e){}
		}
		return true;
	}

	//Used to draw whatever is related to the entity to the main game panel
	public void paintMe(Graphics g)
	{
		//move it's x to the orintation of the room view
		Point origin = getTheGame().getGameArea().getOriginPoint();

		int newX = this.getX() - origin.x;
		int newY = this.getY() - origin.y;
		
		newX -= (getSprite().getWidth() /2);
		newY -= (getSprite().getHeight() /2);
		getSprite().setPosition(newX, newY);
		
		setLastViewX(newX);
		setLastViewY(newY);

		//TODO proof of concept rework or fix but for now if the player is not in view, don't draw it
			//TODO Currently things off screen can't check collisions (I think) Fix this

		this.sprite.paint(g);
		this.sprite.continueAnimation();
		//TODO DEBUG REMOVEME
		if(theGame.isDEBUG())
		{
			g.setColor(Color.RED);
			g.drawString(getNameInList(), getSprite().getX(), getSprite().getY()-10);
			g.drawString("X: " + Integer.toString(getX()) + "  Y: " + Integer.toString(getY()), getSprite().getX() - 20, getSprite().getY()-20);
			g.drawRect(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
		}
	}

	//Is checked every frame of the game
	public void step()
	{
		//see if the entity is moving and move it
		if( (dx != 0) || (dy != 0) )
			this.moveMe();
		
		//If the entity is out of the bounds of the Room then delete it UNLESS IT IS THE PLAYER
		if(!(this instanceof Player))
		{
			if(   (this.getX() > getTheGame().getGameArea().getCurrentRoom().getWidth()) || (this.getX() < 0 )  )
			{
				//Out of x bounds
				this.onDelete();
			}
			if(   (this.getY() > getTheGame().getGameArea().getCurrentRoom().getHeight()) || (this.getY() < 0 )  )
			{
				//out of y bounds
				this.onDelete();
			}
		}
	}

	//move the entity based on it's direction
	public void moveMe()
	{
		//We want to check for collisions every 1 pixel movement to be more accurate with collision checking
		//positive movement
		if(dx > 0)
		{
			for(int i =0; i < dx; i++)
			{
				x += 1;
				if(this.checkCollisions() == false)
					x-=2;
			}
		}else //Negative movement
		{
			for(int i = 0; i < (dx* -1); i++)
			{
				x -= 1;
				if(this.checkCollisions() == false)
					x+=2;
			}
		}
		
		//positive movement
		if(dy > 0)
		{
			for(int i =0; i < dy; i++)
			{
				y += 1;
				if(this.checkCollisions() == false)
					y-=2;
			}
		}else //Negative movement
		{
			for(int i = 0; i < (dy* -1); i++)
			{
				y -= 1;
				if(this.checkCollisions() == false)
					y+=2;
			}
		}
	}

	//Override to allow the entity to be moved to an exact x y
	public void moveMe(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public HashSet<Entity> checkForCollision()
	{
		try{
			HashSet<Entity> returns = new HashSet<Entity>();
			for(Entity e : theGame.getEntityHash())
			{
				//We don't want to add itself to the list
				if(e == this)
					continue;
				//See if they have collided
				if(this.getSprite().collidesWith(e.getSprite(), true))
				//if(Sprite.collidesWith(this.getSprite(), e.getSprite(), false));
				{
					returns.add(e);
				}
			}
			//To make it easier for other methods to see if there was a collision or not, we will return null on no collision
			if(returns.isEmpty())
				return null;
			else
				return returns;
		}catch(NullPointerException e) { } //should mean theres an empty list and nothing should happen
		return null;
	}
	
	
	
	/**
	 * Overloading this method to check for specific collisions
	 */
	public HashSet<Entity> checkForCollision(String name)
	{
		//Since we are checking for an exact collision lets see if one even exists
		try{
			HashSet<Entity> returns = new HashSet<Entity>();
			for(Entity e : theGame.getEntityHash())
			{
				//Check for the right name
				if(e.getName() == name)
				{
					//We don't want to add itself to the list
					if(e == this)
						continue;
					//See if they have collided
					if(this.getSprite().collidesWith(e.getSprite(), true))
					{
						returns.add(e);
					}
				}
			}
			//To make it easier for other methods to see if there was a collision or not, we will return null on no collision
			if(returns.isEmpty())
				return null;
			else
				return returns;
		}catch(NullPointerException e) { } //should mean theres an empty list and nothing should happen
		return null;
	}
	
	/**
	 * @return true if it collides with somthing solid
	 */
	/*public boolean checkForSolidCollision()
	{
		//can move threw solids, skip
		if(this.isMoveThrewSolids())
			return false;
		HashSet<Entity> collision = this.checkForCollision();
		if(collision != null)
			for(Entity e : collision)
				if(e.isSolid())
					return true; // entity is solid and this should not move threw solids so do not move
		//Nothing solid and can not move threw soilds
		return false;
	}*/
	
	public void setDestination(Point point, int speed)
	{
		//Set the movement of the projectile to the point provided
		//projectile.setDx(dx);
		//projectile.setDy(dy);
		
		//TODO figure out how to make it move at a constant speed. Might need to have a speed limiter every frame instead of just in this method
		//float ySpeed = getDy();
		//float xSpeed = getDx();
		
		//ySpeed =  ySpeed * (float) (2.5 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
		//xSpeed = xSpeed * (float) (2.5 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
		
		
		int targetX = point.x - this.getX();
		int targetY = point.y - this.getY();
		
		//pythagorean theorem
		double distance = Math.sqrt(targetX * targetX + targetY * targetY);
		
		//movement
		setDx( (int) ((targetX / distance) * speed));
		setDy( (int) ((targetY / distance) * speed));
		
		
		
		
		
		/** Works to a degress
		int targetX = point.x;
		int targetY = point.y;
		
		double totalX = targetX - getX();
		double totalY = targetY - getY();
		
		double totalDistance = Math.sqrt( (totalX * totalX) + (totalX * totalY)  );
		
		double modifier = totalDistance / speed;
		
		double distanceX = totalX / modifier;
		double distanceY = totalY / modifier;
		
		int intDistanceX = (int) distanceX;
		int intDistanceY = (int) distanceY;
		
		//if the ints drop them both to zero, turn them to 1 to keep moving
		if( ( (intDistanceX == 0) && (intDistanceY == 0) ) && ( (distanceX != 0) || (distanceY != 0) ) )
		{
		//	intDistanceX = 1;
		//	intDistanceY = 1;
		}
		
		setDx( intDistanceX );
		setDy( intDistanceY );*/
		
		/*
		int targetX = point.x;
		int targetY = point.y;
		
		int framesX = 0; //Number of frames it will take to get from x and y to target
		int framesY = 0;
		
		//simple (time it takes) = distance / speed
		int totalXDistance = targetX - getX();
		int totalYDistance = targetY - getY();
		
		framesX = Math.abs(totalXDistance / speed);
		framesY = Math.abs(totalYDistance / speed);
		
		//advoid / by 0
		//if(framesX == 0)framesX = 1;
		//if(framesY == 0)framesY = 1;
		try{
		setDx((int) (totalXDistance / framesX));
		}catch(java.lang.ArithmeticException e){}
		try{
		setDy((int) (totalYDistance / framesY)); //speed is how many frames it will take to get to that location
		}catch(java.lang.ArithmeticException e){}*/
		
		
		
		
		
		
		
		/* WORKING
		setDx((int) ((point.x - getX()) / speed));
		setDy((int) ((point.y - getY()) / speed)); //speed is how many frames it will take to get to that location
		*/
		
		//Make sure it is not over max speed
		/*Is positive
		if(getDx() > 0)
		{
			if(this.getDx() > speed)
				setDx(speed);
		}else //negitive
			if((getDx() * -1) < (speed * -1))
				setDx(speed * -1);
				
		//Is positive
		if(getDy() > 0)
		{
			if(this.getDy() > speed)
				setDy(speed);
		}else //negitive
			if((getDy() * -1) < (speed * -1))
				setDy(speed * -1);*/
	}
	
	

	/**
	 * Will need to check on mouse events and key events to know when clicked or when it needs to close, etc
	 * Check for keyboard input
	 * @param keyCode key used
	 * @param pressed state = [true] pressed | [false] released
	 */
	public void keyCheck(int keyCode,boolean pressed) //copied from Entity
	{

	}
	
	/**
	 * Will be triggered by the FrameMouseListener event
	 * @param event Event that was passed from the MouseListener
	 * @param eventType Compatable Strings "Clicked", "Pressed", and "Released.
	 */
	public void mouseCheck(MouseEvent event,String eventType)
	{

	}
	
	class EntityHIDListener implements KeyListener, MouseListener
	{
		//TODO fix me to only send events to self instead of all entities

		public void keyPressed(KeyEvent e) {
			sendKeyEvent(e.getKeyCode(), true);
		}

		public void keyReleased(KeyEvent e) {
			sendKeyEvent(e.getKeyCode(), false);
		}

		@Override
		public void mouseClicked(MouseEvent event)
		{
			sendMouseEvent(event, "clicked");
		}

		@Override
		public void mousePressed(MouseEvent event)
		{
			sendMouseEvent(event, "pressed");
		}

		@Override
		public void mouseReleased(MouseEvent event)
		{
			sendMouseEvent(event, "released");
		}
		
		
		
		private void sendMouseEvent(MouseEvent event, String eventType)
		{
			for (Entity e: theGame.getEntityHash())
			{
				e.mouseCheck(event, eventType);
			}
		}
		
		private void sendKeyEvent(int keyCode, boolean pressed)
		{
			for (Entity e: theGame.getEntityHash())
			{
				e.keyCheck(keyCode, pressed);
			}
		}
		
		
		//Not currently used
		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void keyTyped(KeyEvent e) {}
	}

	

	/**
	 * Getters and setters
	 */

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Game getTheGame() {
		return theGame;
	}

	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}

	public String getNameInList() {
		return nameInList;
	}

	public void setNameInList(String nameInList) {
		this.nameInList = nameInList;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Point getReletivePosition() {
		return reletivePosition;
	}

	public void setReletivePosition(Point reletivePosition) {
		this.reletivePosition = reletivePosition;
	}

	public int getLastViewX() {
		return lastViewX;
	}

	public void setLastViewX(int lastViewX) {
		this.lastViewX = lastViewX;
	}

	public int getLastViewY() {
		return lastViewY;
	}

	public void setLastViewY(int lastViewY) {
		this.lastViewY = lastViewY;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isMoveThrewSolids() {
		return moveThrewSolids;
	}

	public void setMoveThrewSolids(boolean moveThrewSolids) {
		this.moveThrewSolids = moveThrewSolids;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public EntityHIDListener getEntityHIDListener() {
		return entityHIDListener;
	}

	public void setEntityHIDListener(EntityHIDListener entityHIDListener) {
		this.entityHIDListener = entityHIDListener;
	}

	public boolean isFriendly() {
		return friendly;
	}

	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}


}
