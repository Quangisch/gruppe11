package game.objects;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;

import map.Camera;
import map.DungeonNavigator;
import map.OverWorldNavigator;

import core.Board;
import core.GameManager;
import core.ItemListManager;
import core.PlayerInterface;

public class Player extends PlayerObjectManager implements Runnable{
	private static Player player1 = null;
	private int spawnX; 
	private int spawnY;
	private boolean spawnLock;
	private boolean horizontalLock;
	private boolean verticalLock;
	private boolean interactLock;
	
	
	private Player(){
		System.err.println("construct Player");
		setHumanPlayer(true);
	}


	
	public void run(){
		
		if(!getInitialized())
			System.err.println("Player not initialized");

		if(getInitialized()){
			move();
			automaticManaRegen();
			setMaxLife();
		}
		
		
		if(GameManager.scrollDirection != 0 || !GameManager.mapLoaded){
			setOldPosition();
		}

	}
	
	public void setOldPosition(){
		
		setOldX(getX());
		setOldY(getY());
		setOldXCam(Camera.getInstance().getX());
		setOldYCam(Camera.getInstance().getY());
		setOldLastDirection(getLastDirection());
		//System.out.println(getX()+"to"+getOldX()+", "+getY()+"to"+getOldY());
	}
	
	
	public void keyPressed(KeyEvent e){
		int key;
		if(!getInputLock())
			key = e.getKeyCode();
		else
			key = 0;
			
		
		//basic movement
		if (key == KeyEvent.VK_UP && getMoveable() && !getMoveDown()){
			setMoveUp(true);
		}
		if (key == KeyEvent.VK_RIGHT && getMoveable() && !getMoveLeft()){
			setMoveRight(true);
		}
		if (key == KeyEvent.VK_DOWN && getMoveable() && !getMoveUp()){	
			setMoveDown(true);
		}
		if (key == KeyEvent.VK_LEFT && getMoveable() && !getMoveRight()){
			setMoveLeft(true);
		}
		
		if (key == KeyEvent.VK_F && getMoveable()){
			setSpeedUp(1.2);
		}
		
		
		//interaction
		//attack
		if(key == KeyEvent.VK_D && !interactLock){
			interactLock = true;
			if(getAttackDamage() > 0)
				setAttack();
		}
		
		if(key == KeyEvent.VK_SPACE){
			System.out.println("HitSpace");
			
			GameManager.interact = true;
			GameManager.promptText = true;
		
		}
		
		if(key == KeyEvent.VK_1){
			GameManager.interactKey = 1;
		}
		if(key == KeyEvent.VK_2){
			GameManager.interactKey = 2;
		}
		if(key == KeyEvent.VK_3){
			GameManager.interactKey = 3;
		}
		if(key == KeyEvent.VK_4){
			GameManager.interactKey = 4;
		}
		
		//extended Camera
		if(key == KeyEvent.VK_V){
			GameManager.moveFocus = true;
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		//basic movement
		if (key == KeyEvent.VK_UP){
			setMoveUp(false);
			setMoveStep(0);
			verticalLock = false;
		}
		if (key == KeyEvent.VK_RIGHT){
			setMoveRight(false);
			setMoveStep(0);
			horizontalLock = false;
		}
		if (key == KeyEvent.VK_DOWN){
			setMoveDown(false);
			setMoveStep(0);
			verticalLock = false;
		}	
		if (key == KeyEvent.VK_LEFT){
			setMoveLeft(false);
			setMoveStep(0);
			horizontalLock = false;
		}
		
		if (key == KeyEvent.VK_F && getMoveable()){
			setSpeedUp(0.7);
		}
		
		
		//interact
		if(key == KeyEvent.VK_D){
			interactLock = false;
		}
		if(key == KeyEvent.VK_SPACE){
			System.out.println("HitSpace");
			
			if(!GameManager.showIngameText){
				GameManager.interact = false;
				GameManager.promptText = false;
			}
			
		}
		
		if(key == KeyEvent.VK_S){
			castMagicSpell();
		}
		if(key == KeyEvent.VK_E){
			scrollUpMagicSpell();
		}
		
		//useItem
		if(key == KeyEvent.VK_1){
			if(!GameManager.interact)
				useHealthPotion();
			else
				GameManager.interactKey =  0;
		}
		
		if(key == KeyEvent.VK_2){
			if(!GameManager.interact)
				useManaPotion();
			else
				GameManager.interactKey = 0;
		}
		
		if(key == KeyEvent.VK_3){
			GameManager.interactKey = 0;
		}
		
		if(key == KeyEvent.VK_4){
			GameManager.interactKey = 0;
		}
		
		//camera
		if(key == KeyEvent.VK_C){
			if(!GameManager.dungeon)
				GameManager.cameraOn = !GameManager.cameraOn;
			else
				System.err.println("Can't switch CameraMode in Dungeons.");

			
			if(GameManager.cameraOn && !getDirectionLock()){
				int scrollX = 0;
				int scrollY = 0;
				
				if(!(getRightLock() || getLeftLock()))
					scrollX = getX()-400;
				
				if(!(getUpLock() || getDownLock()))
					scrollY = getY()-300;
				
				
				Camera.getInstance().switchToCameraMode(scrollX, scrollY);
			}
			
		}
		
		//ingameMenu
		if(key == KeyEvent.VK_ESCAPE){
			GameManager.ingameMenu = !GameManager.ingameMenu;
			if(GameManager.ingameMenu)
				this.setInteractionLock(true);
			else
				setInteractionLock(false);
		}
		
		//debug
		if(key == KeyEvent.VK_B){
			GameManager.showBounds = !GameManager.showBounds;
		}
		
		if(key == KeyEvent.VK_P){
			GameManager.printMsg = !GameManager.printMsg;
		}
		if(key == KeyEvent.VK_O){
			System.out.println("Player@"+getX()+"x"+getY());
			System.out.println("Camera@"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
			if(GameManager.dungeon)
			System.out.println("Map...@"+DungeonNavigator.getInstance().getXCoordinate()+"x"+DungeonNavigator.getInstance().getYCoordinate());
			if(GameManager.overWorld)
			System.out.println("Map...@"+OverWorldNavigator.getInstance().getXCoordinate()+"x"+OverWorldNavigator.getInstance().getYCoordinate());
			
		}
		
		if(key == KeyEvent.VK_Y){
			int num = MarioDark.getInstanceCounter();
			MarioDark.getInstance(true, num, false).initializeImage(enemyDark, 90, 120, 8);
			MarioDark.getInstance(false, num, false).initializeAttributes(2, 3, true, 0, 75, 45, 20);
			MarioDark.getInstance(false, num, false).initializePosition(200, 100, 5);
			MarioDark.getInstance(false, num, false).setPattern(3);
			MarioDark.getInstance(false, num, false).setMoveableType(1);
			GameManager.addGameObject(MarioDark.getInstance(false, num, false));
			
		}
		
		if(key == KeyEvent.VK_X){
			int num = MarioDark.getInstanceCounter();
			MarioDark.getInstance(true, num, false).initializeImage(enemyBright, 90, 120, 8);
			MarioDark.getInstance(false, num, false).initializeAttributes(2, 3, true, 0, 75, 45, 20);
			MarioDark.getInstance(false, num, false).initializePosition(200, 100, 5);
			MarioDark.getInstance(false, num, false).setPattern(1);
			MarioDark.getInstance(false, num, false).setMoveableType(2);
			GameManager.addGameObject(MarioDark.getInstance(false, num, false));
			
			
			
		}
		
		if(key == KeyEvent.VK_T){
			System.out.println("printText");
			//PlayerInterface.getInstance().setText("test");
			GameManager.promptText = true;
			GameManager.showIngameText = true;
			PlayerInterface.getInstance().buildText();
		}
		
		if(key == KeyEvent.VK_L){
			ItemListManager.dropItem(getX(), getY(), 0, 1, 0);
			
		}
		
		if(key == KeyEvent.VK_M){
			ItemListManager.dropItem(getX(), getY(), 0, 2, 0);
			
		}
		
		if(key == KeyEvent.VK_K){
			ItemListManager.dropItem(getX(), getY(), 0, 0, 0);
			
		}
		
		if(key == KeyEvent.VK_0){
			MarioDark.deleteAllInstances();
		}
		
		if(key == KeyEvent.VK_V){
			GameManager.moveFocus = false;
		}
		
		if(key == KeyEvent.VK_BACK_SPACE){
			for(int layer = 0; layer < 7; layer++)
			DungeonNavigator.getInstance().clearTileImage(3, 5, 4);
		}
		
		
	}
	
	
	
	
	public int getSpawnX(){return spawnX;}
	public int getSpawnY(){return spawnY;}
	public boolean getSpawnLock(){return spawnLock;}
	//
	public void setSpawnX(int spawnX){this.spawnX = spawnX;}
	public void setSpawnY(int spawnY){this.spawnY = spawnY;}
	public void setSpawnLock(boolean spawnLock){this.spawnLock = spawnLock;}
	
	public static Player getInstance(){
		if(player1 == null)
			player1 = new Player();
		
		return player1;
	}
}
