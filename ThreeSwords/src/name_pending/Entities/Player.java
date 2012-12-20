package name_pending.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;

import name_pending.Game;
import name_pending.Sprite;
import name_pending.Entities.Items.ItemBow;
import name_pending.Entities.Items.ItemDrop;

public class Player extends Being{
	
	private boolean shootingRanged;
	
	private int mana;
	private int stamina;
	
	private Point mousePoint;
	
	PlayerMouseMotionListener playerMouseMotionListener;

	@SuppressWarnings("static-access")
	public Player(Game theGame, int x, int y)
	{
		super(theGame, x, y, theGame.getPlayerData().getSTARTING_SPEED(), theGame.getPlayerData().getSTARTING_NAME(), theGame.getPlayerData().getMaxHealth(), theGame.getPlayerData().getSTARTING_DEFENCE(), theGame.getPlayerData().getSTARTING_ATTACK(), theGame.getPlayerData().getSTARTING_DEXTERITY(), theGame.getPlayerData().getSTARTING_RESISTANCE());
	}

	/*
	 * Entity methods
	 */

	public void onCreate()
	{
		super.onCreate();
		setSprite(getTheGame().getResourceLoader().getSprite("Player.png"));
		playerMouseMotionListener = new PlayerMouseMotionListener();
		getTheGame().getGameArea().addMouseMotionListener(playerMouseMotionListener);
	}

	public void onDelete()
	{
		super.onDelete();
	}

	public boolean checkCollisions()
	{
		//collides with item on the ground
		HashSet<Entity> collision = this.checkForCollision("ItemDrop");
		if(collision != null)
		{
			for(Entity e : collision)
			{
				//ItemDrop collision
				if(e.getName() == "ItemDrop")
				{
					ItemDrop id = (ItemDrop) e;
					//Delete drop and add it's item to inventory < reverse that
					this.getTheGame().getPlayerData().getInventory().addItem(id.getItem());
					id.onDelete();
					
					//Update our inventory with the new item
					this.getTheGame().getGameWindowManager().updateWindows("InventoryWindow");
					break;
				}
			}
		}
		return super.checkCollisions();
	}

	public void paintMe(Graphics g)
	{
		super.paintMe(g);
		//if equip then draw the item equip
		Sprite weaponSprite = null;
		ItemBow rangedWeapon = (ItemBow) this.getTheGame().getPlayerData().getRangedWeapon();
		if(rangedWeapon != null)
		{
			weaponSprite = rangedWeapon.getSprite().clone();
		}
		if(weaponSprite != null)
		{
			weaponSprite.setPosition(getSprite().getX() + 5, getSprite().getY() + 5);
			weaponSprite.paint(g);
		}
		
		
		//TODO DEBUG
		g.setColor(Color.BLACK);
		g.drawString("Mouse x: " + Integer.toString(this.mousePoint.x) + " y: " + Integer.toString(this.mousePoint.y), 100, 100);
	}

	public void step()
	{
		super.step();
		
		if(isShootingRanged())
		{
			if(isCanShoot())
			{
				if(this.getAttackDelay() == 0)
				{
					ItemBow rangedWeapon = (ItemBow) getTheGame().getPlayerData().getRangedWeapon();			
					//TODO set this to a number
					int newX = getTheGame().getGameArea().getOriginPoint().x + mousePoint.x;
					int newY = getTheGame().getGameArea().getOriginPoint().y + mousePoint.y;
					this.fireProjectile(new Point(newX, newY), rangedWeapon, 66);
					setAttackDelay(33); //one second
				}
			}
		}
		
		//Decress attack timer
		if(getAttackDelay() > 0)
			setAttackDelay(getAttackDelay() - 1);
		
		//TODO debug
		if(getTheGame().isDEBUG())
			getTheGame().getPlayerData().addExp(1);
	}

	public void keyCheck(int keyCode,boolean pressed)
	{
		super.keyCheck(keyCode, pressed);
		//Key pressed down
		if(pressed == true)
		{
			if(keyCode == getTheGame().getHotkeys().getHOTKEY_moveUp())
			{
				this.setDy(this.getSpeed() * -1); // move up
			}
			if(keyCode == getTheGame().getHotkeys().getHOTKEY_moveDown())
			{
				this.setDy(this.getSpeed()); //move down
			}
			if(keyCode == getTheGame().getHotkeys().getHOTKEY_moveLeft())
			{
				this.setDx(this.getSpeed() * -1); //move left
			}
			if(keyCode == getTheGame().getHotkeys().getHOTKEY_moveRight()) //move right
			{
				this.setDx(this.getSpeed());
			}
		}else{ // Key released
			if( (keyCode == getTheGame().getHotkeys().getHOTKEY_moveUp()) || (keyCode == getTheGame().getHotkeys().getHOTKEY_moveDown()) )
			{
				this.setDy(0);
			}
			if( (keyCode == getTheGame().getHotkeys().getHOTKEY_moveLeft()) || (keyCode == getTheGame().getHotkeys().getHOTKEY_moveRight()) )
			{
				this.setDx(0);
			}
		}
	}
	
	public void mouseCheck(MouseEvent event,String eventType)
	{
		//check if we need to fire a ranged weapon
		if(event.getButton() == MouseEvent.BUTTON3)
		{
			if(eventType == "pressed")
			{
				ItemBow rangedWeapon = (ItemBow) getTheGame().getPlayerData().getRangedWeapon();
				if(rangedWeapon != null)
				{
					setShootingRanged(true);
				}
			}else
			if(eventType == "released")
				setShootingRanged(false);
		}
	}
	
	//used to get the position of the mouse
	class PlayerMouseMotionListener implements MouseMotionListener
	{

		@Override
		public void mouseDragged(MouseEvent event) {
			setMousePoint(event.getPoint());
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			setMousePoint(event.getPoint());
		}
		
	}
	
	

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public boolean isShootingRanged() {
		return shootingRanged;
	}

	public void setShootingRanged(boolean shootingRanged) {
		this.shootingRanged = shootingRanged;
	}

	public Point getMousePoint() {
		return mousePoint;
	}

	public void setMousePoint(Point mousePoint) {
		this.mousePoint = mousePoint;
	}

}