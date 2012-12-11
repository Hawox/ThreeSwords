package name_pending.Entities.Items;

import name_pending.Game;
import name_pending.Entities.Entity;

/**
 * Other items are not entities.
 * 
 * This exists so an item can be an entity when it is on the ground
 * @author Hawox
 *
 */
public class ItemDrop extends Entity{
	
	Item item;
	
	public ItemDrop(Game theGame, Item item, int x, int y)
	{
		super(theGame, x, y, "ItemDrop");
		this.item = item;
		//Set the entity sprite to the item sprite
		this.setSprite(item.getSprite());
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
}
