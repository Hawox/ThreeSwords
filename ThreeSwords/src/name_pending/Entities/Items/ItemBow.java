package name_pending.Entities.Items;

import name_pending.Game;

public class ItemBow extends ItemWeapon{

	/**
	 * 
	 * @param game
	 * @param name
	 * @param decription
	 * @param rarity
	 * @param type
	 * @param minDamage
	 * @param maxDamage
	 * @param distance
	 * @param bonuses
	 */
	public ItemBow(Game game, String name, String decription, String rarity, int minDamage, int maxDamage, int distance, String[] bonuses) {
		super(game, name, decription, rarity, "rangedweapon", minDamage, maxDamage, distance, bonuses, "ItemBow.png");
		setWeaponType("bow");
		setRanged(true);
	}

}
