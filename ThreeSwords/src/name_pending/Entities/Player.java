package name_pending.Entities;

import java.awt.Graphics;
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
		super(theGame, x, y, theGame.getPlayerData().getSTARTING_NAME(), theGame.getPlayerData().getSTARTING_HEALTH(), theGame.getPlayerData().getSTARTING_DEFENCE(), theGame.getPlayerData().getSTARTING_ATTACK(), theGame.getPlayerData().getSTARTING_SPEED(), theGame.getPlayerData().getSTARTING_DEXTERITY(), theGame.getPlayerData().getSTARTING_RESISTANCE());
		this.mana = theGame.getPlayerData().getMaxMana();
		this.stamina = theGame.getPlayerData().getMaxStamina();
	}

	/*
	 * Entity methods
	 */

	public void onCreate()
	{
		super.onCreate();
		setSprite(getTheGame().getResourceLoader().getSprite("Player.png"));
	}

	public void onDelete()
	{
		super.onDelete();
	}

	public void checkCollisions()
	{
		super.checkCollisions();
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
	}

	public void paintMe(Graphics g)
	{
		super.paintMe(g);
		//if equip then draw the item equip
		ItemBow rangedWeapon = (ItemBow) this.getTheGame().getPlayerData().getRangedWeapon();
		if(rangedWeapon != null)
		{
			Sprite sprite = rangedWeapon.getSprite().clone();
			sprite.setPosition(getX(), getY());
			sprite.paint(g);
		}
	}

	public void step()
	{
		super.step();
	}

	public void keyCheck(int keyCode,boolean pressed)
	{
		super.keyCheck(keyCode, pressed);
		//Key pressed down
		if(pressed == true)
		{
			if(keyCode == KeyEvent.VK_W)
			{
				this.setDy(this.getSpeed() * -1); // move up
			}
			if(keyCode == KeyEvent.VK_S)
			{
				this.setDy(this.getSpeed()); //move down
			}
			if(keyCode == KeyEvent.VK_A)
			{
				this.setDx(this.getSpeed() * -1); //move left
			}
			if(keyCode == KeyEvent.VK_D) //move right
			{
				this.setDx(this.getSpeed());
			}
		}else{ // Key released
			if( (keyCode == KeyEvent.VK_W) || (keyCode == KeyEvent.VK_S) )
			{
				this.setDy(0);
			}
			if( (keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_D) )
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
			if(getTheGame().getPlayerData().getRangedWeapon() != null)
			{
				Projectile projectile = new Projectile(getTheGame(), getX(), getY(), true);
				projectile.setDestination(event.getPoint());
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