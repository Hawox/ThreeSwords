package name_pending.DataBanks;

import java.util.HashSet;

import name_pending.Game;
import name_pending.Resistance;
import name_pending.Entities.Enemy;
import name_pending.Entities.Items.Item;

public class ItemDataBank {
	//Storage for all enemies
	HashSet<ItemTemplate> items = new HashSet<ItemTemplate>();
	//TODO
//	HashSet<WeaponTemplate> weapons = new HashSet<WeaponTemplate>();
//	HashSet<ArmorTemplate> armor = new HashSet<ArmorTemplate>();
	Game theGame;
	
	ItemTemplate nullItem;
	
	public ItemDataBank(Game theGame)
	{
		this.theGame = theGame;
		nullItem = new ItemTemplate(theGame, "nullItem", "NullItem", "white", "nulltype");
		loadItems();
	}
	
	public Item getItem(String name)
	{
		for(ItemTemplate i : items)
		{
			if(i.name == name)
			{
				Item returnMe = (Item) i.get();
				return returnMe;
			}
		}
		
		return nullItem.get();
	}

	//Use this so we don't actually store a bunch of ojects
	private class ItemTemplate
	{
		Game theGame;
		String name, decription, rarity, type;

		ItemTemplate(Game game, String name, String decription, String rarity, String type)
		{
			this.theGame = game;
			this.name = name;
			this.decription = decription;
			this.rarity = rarity;
			this.type = type;
		}

		public Item get()
		{
			return new Item(theGame, name, decription, rarity, type);
		}
	}
	
	//TODO make this load from a file of enemies instead of just writing them in the code
	private boolean loadItems()
	{
		return true;
	}
}
