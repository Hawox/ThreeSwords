package name_pending.Entities;

import name_pending.Game;

public class Enviroment extends Entity{

	public Enviroment(Game theGame, int x, int y, int Speed, String name) {
		super(theGame, x, y, Speed, name);
	}
	
	public void onCreate()
	{
		this.setSprite(getTheGame().getResourceLoader().getSprite("ErrorEnviroment.png"));
		this.setSolid(true);
	}

}
