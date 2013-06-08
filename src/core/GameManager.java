package core;

import java.util.ArrayList;

import game.objects.Moveable;

public class GameManager {
	
	public static volatile GameManager gameManager;
	
	public static volatile boolean gameInitialized;
	
	public static volatile int musicVolume;
	public static volatile int soundVolume;
	public static volatile int brightness;
	public static volatile int contrast;
	
	public static volatile boolean win;
	public static volatile boolean lose;
	public volatile boolean menu;
	public volatile boolean ingame;
	public static volatile boolean switchGameState;
	
	public static volatile boolean overWorld;
	public static volatile boolean dungeon;
	public static volatile boolean mapLoaded;
	
	public static volatile boolean moveFocus = false;
	public static volatile boolean cameraOn = false;
	public static volatile boolean cameraLock = false;
	public static volatile int scrollDirection = 0;
	public static volatile boolean scrollLock = false;
	public static volatile boolean videoSequence = false;
	
	public static volatile boolean showIngameText;
	public static volatile boolean printMsg;
	public static volatile boolean showBounds;
	
	private static volatile ArrayList<Moveable> moveableList = new ArrayList<Moveable>();
	
	private GameManager(){
		
	}
	
	public static synchronized void addGameObject(Moveable moveableElement){
		for(int i = 0; i < moveableList.size()-1; i++){
			if(moveableList.get(i) == null || !moveableList.get(i).getAlive())
				moveableList.remove(i);
		}
		
		if(moveableElement != null){
			moveableList.add(moveableElement);
			Board.getInstance().addDrawable(moveableElement);
		}
		else
			System.err.println("Board: Can't add NullElements.");
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

	public void switchGameState(boolean menu, boolean ingame){
		this.menu = menu;
		this.ingame = ingame;
		GameManager.switchGameState = true;
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
	
	public static GameManager getInstance(){
		if(gameManager == null)
			gameManager = new GameManager();
		
		return gameManager;
	}

}
