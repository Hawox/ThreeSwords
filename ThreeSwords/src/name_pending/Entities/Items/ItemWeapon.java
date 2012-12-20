package name_pending.Entities.Items;

import name_pending.Game;

public class ItemWeapon extends Item{
	//Variables weapons should have
	private int minDamage = 1;
	private int maxDamage = 2;
	private int distance = 1; //how many pixals a ranged weapon will go or how many pixals a melee weapon can reach
	
	//Should be set by it's children classes
	private String weaponType = "None";
	private boolean ranged = false;
	
	String[] bonuses; //used for bonus stats
	
	
	//Modifiers used later on in game
	//TODO slots class
	//TODO enchantment class

	/**
	 * 
	 * @param game
	 * @param name
	 * @param decription
	 * @param rarity
	 * @param minDamage
	 * @param maxDamage
	 * @param distance
	 * @param spriteName
	 */
	public ItemWeapon(Game game, String name, String decription, String rarity, String type, int minDamage, int maxDamage, int distance, String[] bonuses, String spriteName) {
		super(game, name, decription, rarity, type);
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.distance = distance;
		this.bonuses = bonuses;
		this.setSprite(getTheGame().getResourceLoader().getSprite(spriteName));
	}


	public int getMinDamage() {
		return minDamage;
	}


	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}


	public int getMaxDamage() {
		return maxDamage;
	}


	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}


	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}


	public String getWeaponType() {
		return weaponType;
	}


	public void setWeaponType(String weaponType) {
		this.weaponType = weaponType;
	}


	public boolean isRanged() {
		return ranged;
	}


	public void setRanged(boolean ranged) {
		this.ranged = ranged;
	}

}
