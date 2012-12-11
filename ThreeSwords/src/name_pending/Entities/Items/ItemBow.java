package name_pending.Entities.Items;

import name_pending.Game;

public class ItemBow extends ItemWeapon{

	public ItemBow(Game game, String name, String decription, String rarity,
			int minDamage, int maxDamage, int distance, String[] bonuses) {
		super(game, name, decription, rarity, minDamage, maxDamage, distance, bonuses);
		setWeaponType("bow");
		setRanged(true);
		this.setSprite(this.getTheGame().getResourceLoader().getSprite("ItemBow.png"));
	}

}
