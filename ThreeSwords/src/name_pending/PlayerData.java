package name_pending;

import name_pending.Entities.Items.Item;


/**
 * I have split the player and the players data into two parts. This will house everything player related as in stat's and inventory.
 * The other player object will manage collisions etc
 * @author Hawox
 *
 */
public class PlayerData {
	private static String STARTING_NAME = "Player";
	private static int STARTING_HEALTH = 1000;
	private static int STARTING_DEFENCE = 0;
	private static int STARTING_ATTACK = 1;
	private static int STARTING_SPEED = 5;
	private static int STARTING_DEXTERITY = 0;
	private static String[] STARTING_RESISTANCE = null;
	
	//player specific stats 23514
	private int level = 0;
	private int maxMana = 1000;
//	private int mana = maxMana;
	private int maxStamina = 1000;
//	private int stamina = maxStamina;
	private int experianceTillLevel = 100;
	private int experiance = 1;
	private int totalExperiance = experiance;
	
	Game thegame;
	
	//Players inventory
	private Inventory inventory = new Inventory("Players Inventory", 15);
	private Inventory equipment = new Inventory("Players Equipment", 8);
	
	public PlayerData(Game theGame)
	{
		this.thegame = theGame;
		for(int i=0; i<9; i++)
		{
			this.getEquipment().getItems().add(i, null);
			//this.getEquipment().getItems().add(i,new ItemBow(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 100, 150, null), 50 + (50*i), 50 + (50*i));
		}
	}
	/*
	 * 0 Clockwork weapon
	 * 1 Melee weapon
	 * 2 Ranged Weapon
	 * 3 Head
	 * 4 Chest
	 * 5 Gloves
	 * 6 Boots
	 * 7 Amulet
	 * 8 Ring
	 */
	
	/**
	 * I don't want to have to remember the above stats so I am going to add in different methods to clean up the code
	 *  And make it more readable
	 */
	public Item getClockworkWeapon()
	{
		try{
		return getEquipment().getItems().get(0);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	public Item getMeleeWeapon()
	{
		try{
		return getEquipment().getItems().get(1);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	public Item getRangedWeapon()
	{
		try{
		return getEquipment().getItems().get(2);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;}
	}
	
	public Item getHead()
	{
		try{
		return getEquipment().getItems().get(3);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	public Item getChest()
	{
		try{
		return getEquipment().getItems().get(4);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	public Item getGloves()
	{
		try{
		return getEquipment().getItems().get(5);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	public Item getBoots()
	{
		try{
		return getEquipment().getItems().get(6);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	public Item getAmulet()
	{
		try{
		return getEquipment().getItems().get(7);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	public Item getRing()
	{
		try{
		return getEquipment().getItems().get(8);
		}catch(java.lang.IndexOutOfBoundsException e){ return null;	}
	}
	
	
	
	public boolean setClockworkWeapon(Item item)
	{
		if(item.getType() == "clockworkweapon")
		{
			this.getEquipment().getItems().set(0, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setMeleeWeapon(Item item)
	{
		if(item.getType() == "meleeweapon")
		{
			this.getEquipment().getItems().set(1, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setrangedWeapon(Item item)
	{
		if(item.getType() == "rangedweapon")
		{
			this.getEquipment().getItems().set(2, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setHead(Item item)
	{
		if(item.getType() == "head")
		{
			this.getEquipment().getItems().set(3, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setChest(Item item)
	{
		if(item.getType() == "chest")
		{
			this.getEquipment().getItems().set(4, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setGloves(Item item)
	{
		if(item.getType() == "gloves")
		{
			this.getEquipment().getItems().set(5, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setBoots(Item item)
	{
		if(item.getType() == "boots")
		{
			this.getEquipment().getItems().set(6, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setAmulet(Item item)
	{
		if(item.getType() == "amulet")
		{
			this.getEquipment().getItems().set(7, item);
			return true;
		}else
			return false;
	}	
	
	public boolean setRing(Item item)
	{
		if(item.getType() == "ring")
		{
			this.getEquipment().getItems().set(8, item);
			return true;
		}else
			return false;
	}

	
	
	/**
	 * Getters and setters
	 */

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

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
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

	public Inventory getEquipment() {
		return equipment;
	}

	public void setEquipment(Inventory equipment) {
		this.equipment = equipment;
	}


}
