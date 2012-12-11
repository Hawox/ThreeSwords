package name_pending.Entities;

import java.awt.Graphics;
import java.util.HashMap;

import name_pending.Game;

/*
 * Anything that protrays a living being I.E. Monsters, players, npcs
 */
public class Being extends Entity{

	//all being should have health
	//Yes NPC's should be killable in this game. Why not.

	//Indented stats are stats that are effected by the stats before it

	private int health = 0;
	private int defence = 0; // Blocks damage

	private int attack = 0; //base dmg delt

	private int speed = 0; //How fast the entity moves, but also used in calculation crit/dodge
	private int dexterity = 0;
		private int critChance = 0; //this number is a percentage 1-100 ((=> 100) = 100% crit
		private int dodgeChance = 0; //Calcuated the same as crit
	private HashMap<String, Integer> resistances = new HashMap<String, Integer>(); //Every array point will contain what element it has resistance too

	private boolean killable = true;

	Being(Game theGame, int x, int y, String name, int health, int defence, int attack, int speed, int dexterity, String[] resist)
	{
		//Set cords and name
		super(theGame, x, y, name);

		this.health = health;
		this.defence = defence;
		this.attack = attack;
		this.speed = speed;
		this.dexterity = dexterity;

		//Added the resists as a String[] to make it easier when calling the constructor
		//Set the resistances
		try
		{
			for(String r : resist)
			{
				//Resistances string form should be "name-#" ie "fire-20" where # is % resistant
				String[] moreInfo = r.split("-");
				this.resistances.put(moreInfo[0],Integer.getInteger(moreInfo[1]));
			}
		}catch(NullPointerException e){} //Don't need to do anything if there were no resistances
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
	}

	/**
	 * being methods
	 */

	//Checks whether or not the entity has resistances to the element
	public boolean checkResistance(String type)
	{
		//See if it exists in the set
		if(this.resistances.containsKey(type))
			return true;
		else
			return false;
	}

	/*
	 * Remove resistance
	 * 	-True if they had that resistance before this ran
	 */
	public boolean removeResistance(String type)
	{
		//See if it exists in the set
		if(this.resistances.containsKey(type))
			return false;
		else
		{
			this.resistances.remove(type);
			return true;
		}
	}

	/*
	 * add resistance
	 * 	-False if they had that resistance before this ran
	 */
	public boolean addResistance(String type)
	{
		//See if it exists in the set
		if(this.resistances.containsKey(type))
		{
			//Resistances string form should be "name-#" ie "fire-20" where # is % resistant
			String[] moreInfo = type.split("-");
			this.resistances.put(moreInfo[0],Integer.getInteger(moreInfo[1]));
			return true;
		}
		else
		{
			this.resistances.remove(type);
			return false;
		}
	}

	/***********
	 * Getters and setters
	 */


	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}


	public int getDefence() {
		return defence;
	}


	public void setDefence(int defence) {
		this.defence = defence;
	}


	public int getAttack() {
		return attack;
	}


	public void setAttack(int attack) {
		this.attack = attack;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public int getDexterity() {
		return dexterity;
	}


	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}


	public int getCritChance() {
		return critChance;
	}


	public void setCritChance(int critChance) {
		this.critChance = critChance;
	}


	public int getDodgeChance() {
		return dodgeChance;
	}


	public void setDodgeChance(int dodgeChance) {
		this.dodgeChance = dodgeChance;
	}


	public HashMap<String, Integer> getResistances() {
		return resistances;
	}


	public void setResistances(HashMap<String, Integer> resistances) {
		this.resistances = resistances;
	}


	public boolean isKillable() {
		return killable;
	}


	public void setKillable(boolean killable) {
		this.killable = killable;
	}
}
