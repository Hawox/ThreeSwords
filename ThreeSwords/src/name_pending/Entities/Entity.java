package name_pending.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import name_pending.Game;
import name_pending.Room;
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

	//If it's an entity in the world it should have a sprite
	private Sprite sprite = null;
	
	//How fast the entity moves, but also used in calculation crit/dodge
	private int speed = 0; 

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
	public void checkCollisions()
	{

	}

	//Used to draw whatever is related to the entity to the main game panel
	public void paintMe(Graphics g)
	{
		//Set the sprites location before we draw it
		//It needs to be drawn on the reletive point of the game room, not on the exact point of the JPanel
		//if(!(this instanceof Player))
			//this.sprite.setPosition(x+theGame.getGameArea().getReletivePoint().x, y+theGame.getGameArea().getReletivePoint().y);
		Room theRoom = getTheGame().getGameArea().getCurrentRoom();
		int newX = getX();//getX() - (theRoom.getWidth() / 2);
		int newY = getY();//getY() - (theRoom.getHeight() / 2);
		//make sure it does not draw outside the frames bounds
		Player player = theGame.getPlayer();
		
		int shouldCenter = 2;
		//Player should be at the center of screen but everything else should just be drawn at its reletive positions
		if(this instanceof Player)
			shouldCenter = 2;

		//Set the reletive position of the player
		if(player.getX() >  (getTheGame().getFrame().getWidth() / shouldCenter) )
		{
			//Check if it's on the right boundery
			if( (getX() + (getTheGame().getFrame().getWidth() / 2) > theRoom.getWidth()))
				newX = getTheGame().getFrame().getWidth() - (theRoom.getWidth() - getX()); 
			else{ //Center of screen
				if((this instanceof Player))
					newX = (getTheGame().getFrame().getWidth() / 2);
				else
				{
					//Converts the difference back to the correct direction
					int sideModifier = -1;
					//if(this.getX() > player.getX())
					//	sideModifier = -1;
					newX = (getTheGame().getFrame().getWidth() / 2) + ( (player.getX() - this.getX()) * sideModifier);
				}
			}
		}

		//Set the reletive position of the player
		if(player.getY() >  (getTheGame().getFrame().getHeight() / shouldCenter) )
		{
			//Check if it's on the right boundery
			if( (getY() + (getTheGame().getFrame().getHeight() / 2) > theRoom.getHeight()))
				newY = getTheGame().getFrame().getHeight() - (theRoom.getHeight() - getY()); 
			else{ //Center of screen
				if((this instanceof Player))
					newY = (getTheGame().getFrame().getHeight() / 2);
				else
				{
					//Converts the difference back to the correct direction
					int sideModifier = -1;
					//if(this.getY() > player.getY())
					//	sideModifier = -1;
					newY = (getTheGame().getFrame().getHeight() / 2) + ( (player.getY() - this.getY()) * sideModifier);
				}
			}
		}

		newX -= (getSprite().getWidth() /2);
		newY -= (getSprite().getHeight() /2);
		getSprite().setPosition(newX, newY);

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
		//move our sprite to the correct location
		//Don't think we need this here. Moved to the paint step.
		// - Having this here might be the reason we can only have one entity per sprite
		this.sprite.setPosition(x, y);
		
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
		//x += dx;
		//y += dy;
		//We want to check for collisions every 1 pixal movement to be more accurate with collision checking
		//positive movement
		if(dx > 0)
		{
			for(int i =0; i < dx; i++)
			{
				x += 1;
				this.checkCollisions();
			}
		}else //negitive movement
		{
			for(int i = 0; i < (dx* -1); i++)
			{
				x -= 1;
				this.checkCollisions();
			}
		}
		
		//positive movement
		if(dy > 0)
		{
			for(int i =0; i < dy; i++)
			{
				y += 1;
				this.checkCollisions();
			}
		}else //negitive movement
		{
			for(int i = 0; i < (dy* -1); i++)
			{
				y -= 1;
				this.checkCollisions();
			}
		}
	}

	//Override to allow the entity to be moved to an exact x y
	public void moveMe(int x, int y)
	{
		setX(x);
		setY(y);
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

	/*Remade to check for collision with anything
	//Used to see if this entity collides with another specific entity; then returns it
	public HashSet<Entity> checkForCollision(String name)
	{
		HashSet<Entity> returns = new HashSet<Entity>();
		for(Entity e : theGame.getEntityHash())
		{
			//We don't want to add itself to the list
			if(e == this)
				continue;
			//Match!
			if(e.getName() == name)
			{
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
	}*/
	
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


}
