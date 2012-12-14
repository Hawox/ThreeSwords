package name_pending;


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

	public PlayerData(Game theGame)
	{
		this.thegame = theGame;
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


}
