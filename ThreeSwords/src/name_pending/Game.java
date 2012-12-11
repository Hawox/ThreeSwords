package name_pending;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import name_pending.Entities.Entity;
import name_pending.Entities.Player;


/**
 * Code snipits to remember
 * Set cursor to normal
 * setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 *
 */
public class Game
{
	//quick checks to see if the game is is test or debug mode
	private static boolean TESTING = true;
	private boolean DEBUG = true;
	private TestRoom testRoom = new TestRoom(this);
	private boolean fullscreen = false;

	//This will contain every entity currently in the game to run all of their internal functions
	private HashSet<Entity> entityHash = new HashSet<Entity>();
	//the keylistener
	private FrameKeyListener frameKeyListener;

	//The main game thread loop
	private GameLoop gameLoop = null;

	//Graphicsy Stuff
	private JFrame frame = new JFrame();
	private MainDrawPanel mainDrawPanel = new MainDrawPanel();
	private UI ui = new UI(this); //base UI for the game
	
	//Houses all of the games sprites
	ResourceLoader spriteLoader = new ResourceLoader();


	private Sprite currentCursor = null;

	//Launch when the game starts
	public void go()
	{
		setupFrame();

		//setup the keylistener
		frameKeyListener = new FrameKeyListener(this);
		frame.addKeyListener(frameKeyListener);

		if(TESTING)
			testRoom.start();

		//load the sprites
		spriteLoader.loadImages();
		
		//Start the loop
		gameLoop = new GameLoop(this);
		gameLoop.run();

	}

	//Sets all of our default variables to get the frame ready for action
	private void setupFrame()
	{
		//Create our frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainDrawPanel);
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.setResizable(false);

		//Have not setup fullscreen stuff yet but I think this will be needed
		if(fullscreen)
			frame.setUndecorated(true);

		//Gets double buffering ready
		frame.createBufferStrategy(2);
		
		//setup the cursor
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				//loadCursors();
	}

	//Load all of the games cursors into Memory
	/*
	 * Theres probably a way to do this based on loading everything in a folder and assigning the files
	 * names as the string but I want to remember and have control over what cursors I have at my disposal
	 * so this might be some clunky code
	 */
/*	public void loadCursors()
	{
		//Empty the hashset so we don't get any duplicates
		this.getCursors().clear();

		//Load in all the cursors individually
		Sprite sprCrossheir = null;
		try
		{
			sprCrossheir = new Sprite(ImageIO.read(this.getClass().getResource("Entities/Sprites/Cursor_Crossheir.png")));
		}catch(IOException e){}

		//add them all to the hashmap
		this.getCursors().put("Crossheir", sprCrossheir);
	}*/

	/**
	 * Returns true if it changed the cursor
	 * @param name -- String tied to the cursor sprite
	 */
	public boolean changeCursor(String name)
	{
		Sprite newCursor = null;

		//if(this.getResourceLoader().getImages().containsKey(name))
		//{
			//Change the variable
			newCursor = this.getResourceLoader().getSprite(name);
			//Now actually change the cursor that's drawn
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = newCursor.getImage();
			Cursor c = toolkit.createCustomCursor(image, new Point(15,15), name);
			frame.setCursor(c);
			return true;
		//}else
		//	return false;	//Cursor does not exist
			
		
		/*if(this.getCursors().containsKey(name))
		{
			//Change the variable
			newCursor = this.getCursors().get(name);
			//Now actually change the cursor that's drawn
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = newCursor.getImage();
			Cursor c = toolkit.createCustomCursor(image, new Point(15,15), name);
			frame.setCursor(c);
			return true;
		}else
			return false;	//Cursor does not exist
			*/
		/*
		if(newCursor != null)
		{
			this.setCurrentCursor(newCursor);
			return true;
		}else
			return false; // cursor change failed at some point*/
	}


	
	

	//Lots of other methods are going to want to get the player object so lets make a centeral method here
	public Player getPlayer()
	{
		//Get the player
		Player player = null;
		for(Entity e : this.getEntityHash())
		{
			if(e.getName() == "Player")
			{
				player = (Player)e;
				break;
			}
		}
		return player;
	}

	//TODO
	@SuppressWarnings("serial")
	//Top left panel where the main game is drawn. Most UI may not be in this screen.
	class MainDrawPanel extends JPanel
	{
		/*
		public void painComponet(Graphics g)
		{
			//Base background, should usually be covered by images. If we see this something is wrong
			g.setColor(Color.pink);

			//Draw every entity to the panel
			for (Entity e: entityHash)
			{
				e.paintMe(g);
			}

		}*/
	}
	

	/*****
	 * Getters and setters
	 */

	public HashSet<Entity> getEntityHash() {
		return entityHash;
	}

	public void setEntityHash(HashSet<Entity> entityHash) {
		this.entityHash = entityHash;
	}

	public FrameKeyListener getFrameKeyListener() {
		return frameKeyListener;
	}

	public void setFrameKeyListener(FrameKeyListener frameKeyListener) {
		this.frameKeyListener = frameKeyListener;
	}

	public static boolean isTESTING() {
		return TESTING;
	}

	public static void setTESTING(boolean tESTING) {
		TESTING = tESTING;
	}

	public boolean isDEBUG() {
		return DEBUG;
	}

	public void setDEBUG(boolean dEBUG) {
		DEBUG = dEBUG;
	}

	public TestRoom getTestRoom() {
		return testRoom;
	}

	public void setTestRoom(TestRoom testRoom) {
		this.testRoom = testRoom;
	}

	public GameLoop getGameLoop() {
		return gameLoop;
	}

	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public MainDrawPanel getMainDrawPanel() {
		return mainDrawPanel;
	}

	public void setMainDrawPanel(MainDrawPanel mainDrawPanel) {
		this.mainDrawPanel = mainDrawPanel;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public Sprite getCurrentCursor() {
		return currentCursor;
	}

	public UI getUi() {
		return ui;
	}

	public void setUiv(UI ui) {
		this.ui = ui;
	}

	public ResourceLoader getResourceLoader() {
		return spriteLoader;
	}

	public void setResourceLoader(ResourceLoader spriteLoader) {
		this.spriteLoader = spriteLoader;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

	//Different method for this above
	/*public void setCurrentCursor(Sprite currentCursor) {
		this.currentCursor = currentCursor;
	}*/
}




/************* Osolete code ***********************
 * /**
 * Runs in every step the game takes
 * Should be called from inside a runnable
 *
	public void step()
	{
		for(Entity e : entityHash)
		{
			//remove all entities that are called for deletion
			if(e.getName() == "deleteMe")
			{
				entityHash.remove(e);
			}

			//check for any collisions with entities
			e.checkCollisions();

			//run any step events for the entities
			e.step();
		}

	}*
 *
 * Moved to it's own thread class
 */