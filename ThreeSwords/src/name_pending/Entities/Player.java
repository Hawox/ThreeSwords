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
		//getSprite().setRefPixel(getX() - getSprite().getWidth(), getY() - getSprite().getHeight());
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
		
		//Move the players sprite with the view of the room
/*		Room theRoom = getTheGame().getGameArea().getCurrentRoom();
		int newX = getX();//getX() - (theRoom.getWidth() / 2);
		int newY = getY();//getY() - (theRoom.getHeight() / 2);
		//make sure it does not draw outside the frames bounds
		
		//Set the reletive position of the player
		if(getX() >  (getTheGame().getFrame().getWidth() / 2) )
		{
			//Check if it's on the right boundery
			if( (getX() + (getTheGame().getFrame().getWidth() / 2) > theRoom.getWidth()))
				newX = getTheGame().getFrame().getWidth() - (theRoom.getWidth() - getX()); 
			else //Center of screen
				newX = (getTheGame().getFrame().getWidth() / 2);
		}
		
		if(getY() >  (getTheGame().getFrame().getHeight() / 2) )
		{
			//Check if it's on the right boundery
			if( (getY() + (getTheGame().getFrame().getHeight() / 2) > theRoom.getHeight()))
				newY = getTheGame().getFrame().getHeight() - (theRoom.getHeight() - getY()); 
			else //Center of screen
				newY = (getTheGame().getFrame().getHeight() / 2);
		}
		
		getSprite().setPosition(newX, newY);
		getSprite().paint(g);
		getSprite().continueAnimation();
		//TODO DEBUG REMOVEME
		if(getTheGame().isDEBUG())
		{
			g.setColor(Color.RED);
			g.drawString(getNameInList(), getX(), getY()-10);
			g.drawRect(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
		}*/
			
			
		//if(newX < 0) newX = getX();
		//if(newY < 0) newY = getY();
		//if(newX > getWidth()) newX = getWidth();
		//if(newY > getHeight()) newY = getHeight();
		//TODO Magic numbers
		//cropArea.setBounds(newX, newY, 800, 600);
		//g2d.translate(newX, newY);
	}

	public void step()
	{
		super.step();
		getTheGame().getPlayerData().addExp(1);
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