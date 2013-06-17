package core;

import java.util.ArrayList;

import objects.Moveable;


public class GameManager {
	
	public static volatile GameManager gameManager;
	
	public volatile boolean gameInitialized;
	
	public volatile int musicVolume;
	public volatile int soundVolume;
	public volatile int brightness;
	public volatile int contrast;
	
	public volatile boolean win;
	public volatile boolean lose;
	public volatile boolean menu = true;
	public volatile boolean ingame;
	public volatile boolean ingameMenu;
	public volatile boolean switchGameState;
	
	public volatile boolean overWorld;
	public volatile boolean dungeon;
	public volatile boolean mapLoaded;
	
	public volatile boolean moveFocus = false;
	public volatile boolean cameraOn = false;
	public volatile boolean cameraLock = false;
	public volatile int scrollDirection = 0;
	public volatile boolean scrollLock = false;
	public volatile boolean videoSequence = false;
	
	public volatile int interactKey;
	public volatile boolean interact;
	public volatile boolean showIngameText;
	public volatile boolean promptText;
	public volatile boolean printMsg;
	public volatile boolean showBounds;
	
	private static volatile ArrayList<Moveable> moveableList = new ArrayList<Moveable>();
	
	private GameManager(boolean menu, boolean ingame, boolean switchGameState){
		this.menu = menu;
		this.ingame = ingame;
		this.switchGameState = switchGameState;
	}
	
	public static synchronized void addGameObject(Moveable moveableElement){

		moveableList.add(moveableElement);
		Board.getInstance().addDrawable(moveableElement);
			
		if(moveableElement == null)
			System.err.println("Board: Can't add NullElements.");
		
		if(!moveableElement.getAlive())
		System.exit(0);
		
		updateGameObject();
		
	}
	
	
	public static synchronized void updateGameObject(){
		for(int i = 0; i < moveableList.size(); i++){
			if(moveableList.get(i) == null || !moveableList.get(i).getAlive())
				moveableList.remove(i);
		}
	}
	
	public static synchronized void clearGameObjects(){
		moveableList.clear();
	}
	
	public static ArrayList<Moveable> getMoveableList(){
		return moveableList;
	}
	
	public boolean getGameInitialized(){return gameInitialized;}
	//
	public void setGameInitialized(boolean gameInitialized){this.gameInitialized = gameInitialized;}
	
	public int getMusicVolume(){return musicVolume;}
	public int getSoundVolume(){return soundVolume;}
	public int getBrightness(){return brightness;}
	public int getContrast(){return contrast;}
	//
	public void setMusicVolume(int musicVolume){this.musicVolume = musicVolume;}
	public void setSoundVolume(int soundVolume){this.soundVolume = soundVolume;}
	public void setBrightness(int brightness){this.brightness = brightness;}
	public void setContrast(int contrast){this.contrast = contrast;}
	
	
	public boolean getWin(){return win;}
	public boolean getLose(){return lose;}
	public boolean getMenu(){return menu;}
	public boolean getIngame(){return ingame;}
	//
	public void setWin(boolean win){this.win = win;}
	public void setLose(boolean lose){this.lose = lose;}

	public void switchGameState(boolean menuArg, boolean ingameArg){
		this.menu = menuArg;
		this.ingame = ingameArg;
		switchGameState = true;
	}
	
	public boolean getMapLoaded(){return mapLoaded;}
	public boolean getOverWorld(){return overWorld;}
	public boolean getDungeon(){return dungeon;}
	public boolean getScrollLock(){return scrollLock;}
	//
	public void setMapLoaded(boolean mapLoaded){this.mapLoaded = mapLoaded;}
	public void setOverWorld(boolean overWorld){this.overWorld = overWorld;}
	public void setDungeon(boolean dungeon){this.dungeon = dungeon;}
	public void setScrollLock(boolean scrollLock){this.scrollLock = scrollLock;}
	
	
	public static void resetInstance(){
		boolean tmpMenu = GameManager.getInstance().menu;
		boolean tmpIngame = GameManager.getInstance().ingame;
		boolean tmpSwitchState = GameManager.getInstance().switchGameState;
		if(gameManager != null)
			gameManager = new GameManager(tmpMenu, tmpIngame, tmpSwitchState);

		
	}
	
	public static GameManager getInstance(){
		if(gameManager == null)
			gameManager = new GameManager(true, false, true);
		
		return gameManager;
	}

}
