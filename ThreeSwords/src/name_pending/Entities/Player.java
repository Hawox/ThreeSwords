package name_pending.Entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import name_pending.Game;
import name_pending.Inventory;
import name_pending.Entities.Items.ItemDrop;

public class Player extends Being{
	private static String STARTING_NAME = "Player";
	private static int STARTING_HEALTH = 1000;
	private static int STARTING_DEFENCE = 0;
	private static int STARTING_ATTACK = 1;
	private static int STARTING_SPEED = 5;
	private static int STARTING_DEXTERITY = 0;
	private static String[] STARTING_RESISTANCE = null;
	
	//player specific stats
	private int level = 0;
	private int maxMana = 1000;
	private int mana = maxMana;
	private int maxStamina = 1000;
	private int stamina = maxStamina;
	private int experianceTillLevel = 100;
	private int experiance = 1;
	private int totalExperiance = experiance;
	
	//Players inventory
	private Inventory inventory = new Inventory("Players Inventory", 15);

	public Player(Game theGame, int x, int y)
	{
		super(theGame, x, y, STARTING_NAME, STARTING_HEALTH, STARTING_DEFENCE, STARTING_ATTACK, STARTING_SPEED, STARTING_DEXTERITY, STARTING_RESISTANCE);
		setSprite(theGame.getResourceLoader().getSprite("Player.png"));
	}

	/*
	 * Entity methods
	 */

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
					this.getInventory().addItem(id.getItem());
					id.onDelete();
					//break;
				}
			}
		}
	}

	public void paintMe(Graphics g)
	{
		super.paintMe(g);
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

	public static String getSTARTING_NAME() {
		return STARTING_NAME;
	}

	public static void setSTARTING_NAME(String sTARTING_NAME) {
		STARTING_NAME = sTARTING_NAME;
	}

	public static int getSTARTING_HEALTH() {
		return STARTING_HEALTH;
	}

	public static void setSTARTING_HEALTH(int sTARTING_HEALTH) {
		STARTING_HEALTH = sTARTING_HEALTH;
	}

	public static int getSTARTING_DEFENCE() {
		return STARTING_DEFENCE;
	}

	public static void setSTARTING_DEFENCE(int sTARTING_DEFENCE) {
		STARTING_DEFENCE = sTARTING_DEFENCE;
	}

	public static int getSTARTING_ATTACK() {
		return STARTING_ATTACK;
	}

	public static void setSTARTING_ATTACK(int sTARTING_ATTACK) {
		STARTING_ATTACK = sTARTING_ATTACK;
	}

	public static int getSTARTING_SPEED() {
		return STARTING_SPEED;
	}

	public static void setSTARTING_SPEED(int sTARTING_SPEED) {
		STARTING_SPEED = sTARTING_SPEED;
	}

	public static int getSTARTING_DEXTERITY() {
		return STARTING_DEXTERITY;
	}

	public static void setSTARTING_DEXTERITY(int sTARTING_DEXTERITY) {
		STARTING_DEXTERITY = sTARTING_DEXTERITY;
	}

	public static String[] getSTARTING_RESISTANCE() {
		return STARTING_RESISTANCE;
	}

	public static void setSTARTING_RESISTANCE(String[] sTARTING_RESISTANCE) {
		STARTING_RESISTANCE = sTARTING_RESISTANCE;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getExperianceTillLevel() {
		return experianceTillLevel;
	}

	public void setExperianceTillLevel(int experianceTillLevel) {
		this.experianceTillLevel = experianceTillLevel;
	}

	public int getExperiance() {
		return experiance;
	}

	public void setExperiance(int experiance) {
		this.experiance = experiance;
	}

	public int getTotalExperiance() {
		return totalExperiance;
	}

	public void setTotalExperiance(int totalExperiance) {
		this.totalExperiance = totalExperiance;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


}
