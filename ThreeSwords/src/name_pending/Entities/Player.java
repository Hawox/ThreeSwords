package name_pending.Entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import name_pending.Game;
import name_pending.Sprite;
import name_pending.Entities.Items.ItemBow;
import name_pending.Entities.Items.ItemDrop;
import name_pending.Entities.Items.Projectile;

public class Player extends Being{
	
	int mana;
	int stamina;

	@SuppressWarnings("static-access")
	public Player(Game theGame, int x, int y)
	{
		super(theGame, x, y, theGame.getPlayerData().getSTARTING_SPEED(), theGame.getPlayerData().getSTARTING_NAME(), theGame.getPlayerData().getMaxHealth(), theGame.getPlayerData().getSTARTING_DEFENCE(), theGame.getPlayerData().getSTARTING_ATTACK(), theGame.getPlayerData().getSTARTING_DEXTERITY(), theGame.getPlayerData().getSTARTING_RESISTANCE());
//		this.mana = theGame.getPlayerData().getMaxMana();
//		this.stamina = theGame.getPlayerData().getMaxStamina();
	}

	/*
	 * Entity methods
	 */

	public void onCreate()
	{
		super.onCreate();
		setSprite(getTheGame().getResourceLoader().getSprite("Player.png"));
		//getSprite().setRefPixel(getX() - getSprite().getWidth(), getY() - getSprite().getHeight());
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
			weaponSprite.setPosition(getX(), getY());
			weaponSprite.paint(g);
		}
	}

	public void step()
	{
		super.step();
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
			//TODO do a check if the mouse is over a window
			ItemBow rangedWeapon = (ItemBow) getTheGame().getPlayerData().getRangedWeapon();
			if(rangedWeapon != null)
			{
				//get a random dmg number from the weapon
				int projectileDamage = getTheGame().getRandomGenerator().nextInt(rangedWeapon.getMaxDamage() - rangedWeapon.getMinDamage()) + rangedWeapon.getMinDamage();
				//fire the arrow at the speed of the ranged weapon
				Projectile projectile = new Projectile(getTheGame(), getX(), getY(), rangedWeapon.getDistance(), projectileDamage, true);
				
				//Set the destination to be reletive to the players
				//get the difference
				int newX = (event.getPoint().x - getTheGame().getPlayer().getX())*1;
				int newY = (event.getPoint().y - getTheGame().getPlayer().getX())*1;
				//add the difference
				newX += getTheGame().getPlayer().getX();
				newY += getTheGame().getPlayer().getY();
				Point moveTo = new Point(newX, newY);
				projectile.setDestination(moveTo);
				getTheGame().getEntityHash().add(projectile);
			}
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

}