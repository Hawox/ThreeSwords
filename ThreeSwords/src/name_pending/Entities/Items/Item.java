package name_pending.Entities.Items;

import name_pending.Game;
import name_pending.Sprite;

/** The base class for every item in the game **/
public class Item {
	private Game theGame;
	//stats all items should have
	private String name = "Empty Item";
	private String decription = "I am an empty item. Here me ROAWR!";
	private String rarity = "white";
	private String type = "nothing";
	private Sprite sprite = null;
	
	/**
	 * 
	 * @param game
	 * @param name
	 * @param decription
	 * @param rarity
	 */
	public Item(Game game, String name, String decription, String rarity, String type)
	{
		this.theGame = game;
		this.name = name;
		this.decription = decription;
		this.rarity = rarity;
		this.type = type;
		
		//Set the sprite to an error sprite
		game.getResourceDataBank().getSprite("ItemError.png");
	}

	public Game getTheGame() {
		return theGame;
	}

	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
