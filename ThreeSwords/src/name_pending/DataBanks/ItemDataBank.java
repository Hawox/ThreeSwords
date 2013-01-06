package name_pending.DataBanks;

import java.util.HashSet;

import name_pending.Game;
import name_pending.Entities.Items.Item;
import name_pending.Entities.Items.ItemArmor;
import name_pending.Entities.Items.ItemConsumeable;
import name_pending.Entities.Items.ItemMaterial;
import name_pending.Entities.Items.ItemMelee;
import name_pending.Entities.Items.ItemRanged;
import name_pending.Entities.Items.ItemWeapon;

public class ItemDataBank {
	//Storage for all enemies
	HashSet<Item> items = new HashSet<Item>();
	
	//We'll store these as items and get the load loop to convert them to their actual class
	HashSet<Item> weapons = new HashSet<Item>();
	HashSet<Item> armor = new HashSet<Item>();
	HashSet<Item> consume = new HashSet<Item>();
	HashSet<Item> material = new HashSet<Item>();
	
	Game theGame;
	
	Item nullItem;
	
	public ItemDataBank(Game theGame)
	{
		this.theGame = theGame;
		nullItem = new Item(theGame, "nullItem", "NullItem", "white", "nulltype", "ItemError.png");
		loadItems();
	}
	
	
	public Item getItem(String name)
	{
		return getItemType(name, "item");
	}
	
	public ItemWeapon getMeleeWeapon(String name)
	{
		return (ItemMelee) getItemType(name, "meleeweapons");
	}
	
	public ItemWeapon getRangedWeapon(String name)
	{
		return (ItemRanged) getItemType(name, "rangedweapon");
	}
	
	public ItemArmor getArmor(String name)
	{
		return (ItemArmor) getItemType(name, "armor");
	}
	
	public ItemConsumeable getConsumeable(String name)
	{
		return (ItemConsumeable) getItemType(name, "consume");
	}
	
	public ItemMaterial getMaterial(String name)
	{
		return (ItemMaterial) getItemType(name, "material");
	}
	
	
	private Item getItemType(String name, String type)
	{
		if(type == "item")
		{
			for(Item i : items)
			{
				if(i.getName() == name)
				{
					Item returnMe = (Item) i.clone();
					return returnMe;
				}
			}
		}
		if(type == "meleeweapon")
		{
			for(Item i : weapons)
			{
				ItemWeapon iw = (ItemWeapon) i;
				if((i.getName() == name) && (iw.getWeaponType() == "melee") )
				{
					Item returnMe = (Item) i.clone();
					return returnMe;
				}
			}
		}
		if(type == "rangedweapon")
		{
			for(Item i : weapons)
			{
				ItemWeapon iw = (ItemWeapon) i;
				if((i.getName() == name) && (iw.getWeaponType() == "ranged") )
				{
					Item returnMe = (Item) i.clone();
					return returnMe;
				}
			}
		}
		if(type == "armor")
		{
			for(Item i : armor)
			{
				if(i.getName() == name)
				{
					Item returnMe = (Item) i.clone();
					return returnMe;
				}
			}
		}
		if(type == "consume")
		{
			for(Item i : consume)
			{
				if(i.getName() == name)
				{
					Item returnMe = (Item) i.clone();
					return returnMe;
				}
			}
		}
		if(type == "material")
		{
			for(Item i : material)
			{
				if(i.getName() == name)
				{
					Item returnMe = (Item) i.clone();
					return returnMe;
				}
			}
		}

		return nullItem.clone();
	}
	
	//TODO make this load from a file of enemies instead of just writing them in the code
	private boolean loadItems()
	{
		this.items.add(new ItemRanged(theGame, "SuperBow", "This is the SuperBow of epicness", "white", 10, 20, 33, null, "ItemBow.png"));
		this.items.add(new ItemMelee(theGame, "SuperSword", "This is the SuperSword of epicness", "white", 20, 30, 10, null));
		//TODO sort the items into their classes based on the type of the item when it loads in
		//leave all the items in the item hashset incase we need to roll for a random item or something
		for(Item i : this.items)
		{
			//weapons
			if( (i.getType() == "meleeweapon") || (i.getType() == "rangedweapon") )
				this.weapons.add(i);
			//ranged weapons
			else if(i.getType() == "armor")
				this.armor.add(i);
			else if(i.getType() == "consume")
				this.consume.add(i);
			else if(i.getType() == "material")
				this.material.add(i);
		}
		theGame.getConsole().addText("Item Data Bank loaded.");
		return true;
	}
}
