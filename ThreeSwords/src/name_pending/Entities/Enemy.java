package name_pending.Entities;

import name_pending.Game;


public class Enemy extends Entity{
	protected Enemy(Game theGame, int x, int y, int Speed, String name) {
		super(theGame, x, y, Speed, name);
		// TODO Auto-generated constructor stub
	}
	private boolean arrgoed;
	private Entity aggroedTo;
	
	
}
