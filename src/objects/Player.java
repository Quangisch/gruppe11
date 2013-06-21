package objects;

import java.awt.event.KeyEvent;

import map.Camera;
import map.DungeonNavigator;
import map.OverWorldNavigator;

import core.GameManager;
import core.ItemListManager;
import core.PlayerInterface;
import core.SaveGameManager;
import core.Sound;

public class Player extends PlayerInventory implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2112715920555154717L;
	private static Player player;
	private int spawnX; 
	private int spawnY;
	private boolean spawnLock;
	private boolean interactLock;
	private boolean godlikeModus;
	private boolean switchGameState;
	
	private Player(){
		System.err.println("construct Player");
		setHumanPlayer(true);
	}


	
	public void run(){
		//System.out.println("GameManager.getInstance().CameraOn:"+GameManager.getInstance().cameraOn);
		if(!getInitialized())
			System.err.println("Player not initialized");

		//System.out.println("Player@"+getX()+"x"+getY()+"@subSprite:"+getSubSpriteWidth());
		
		if(getInitialized()){
			move();
			automaticRegen();
			
			if(godlikeModus){
				if(getLife() +0.25 <= getMaxLife())
				setLife(getLife()+0.25, false);
				if(getManaPool() +0.1 <= getMaxMana())
				setManaPool(getManaPool()+0.1);
			}
		}
		
		if(GameManager.getInstance().scrollDirection != 0 || !GameManager.getInstance().mapLoaded){
			setOldPosition();
		}
		
		if(Player.getInstance().getLife() <= 0 && !GameManager.getInstance().saveGameLock){
			GameManager.getInstance().lose = true;
		}
		
		if(GameManager.getInstance().win){
			switchGameState = true;
			setAchieve();
			startRotateTimer(1000,70,8);
		}
		
		if(GameManager.getInstance().lose){
			switchGameState = true;
			setMoveable(false);
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
	
	private void keyPressedIngame(int key){
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
					//System.out.println("HitSpace");
					
					GameManager.getInstance().interact = true;
					GameManager.getInstance().promptText = true;
				
				}
				
				if(key == KeyEvent.VK_1){
					GameManager.getInstance().interactKey = 1;
				}
				if(key == KeyEvent.VK_2){
					GameManager.getInstance().interactKey = 2;
				}
				if(key == KeyEvent.VK_3){
					GameManager.getInstance().interactKey = 3;
				}
				if(key == KeyEvent.VK_4){
					GameManager.getInstance().interactKey = 4;
				}
				if(key == KeyEvent.VK_5){
					GameManager.getInstance().interactKey = 5;
				}
				
				//extended Camera
				if(key == KeyEvent.VK_V){
					GameManager.getInstance().moveFocus = true;
				}
	}
	
	public void keyPressed(KeyEvent kE){
		int key;
		if(!getInputLock())
			key = kE.getKeyCode();
		else
			key = 0;
		
		if(!GameManager.getInstance().ingameMenu)
			keyPressedIngame(key);
		
		if(key == 0)
			interactLock = false;
	}
	
	private void keyReleasedIngameMenu(KeyEvent kE){
		
		//saveFile
		if(kE.isControlDown() && kE.getKeyCode() == KeyEvent.VK_1){

			SaveGameManager.saveNow(0);
		}
		if(kE.isControlDown() && kE.getKeyCode() == KeyEvent.VK_2){

			SaveGameManager.saveNow(1);
		}
		
		//loadFile
		if(!kE.isControlDown() && kE.getKeyCode() == KeyEvent.VK_1)
			SaveGameManager.loadNow(0);
		if(!kE.isControlDown() && kE.getKeyCode() == KeyEvent.VK_2)
			SaveGameManager.loadNow(1);
		
		
	}
	
	private void keyReleasedPlayer(int key){
		//basic movement
		if (key == KeyEvent.VK_UP){
			setMoveUp(false);
			setMoveStep(0);
		}
		if (key == KeyEvent.VK_RIGHT){
			setMoveRight(false);
			setMoveStep(0);
		}
		if (key == KeyEvent.VK_DOWN){
			setMoveDown(false);
			setMoveStep(0);
		}	
		if (key == KeyEvent.VK_LEFT){
			setMoveLeft(false);
			setMoveStep(0);
		}
		
		if (key == KeyEvent.VK_F && getMoveable()){
			setSpeedUp(0.9);
		}
		
		
		//interact
		if(key == KeyEvent.VK_D){
			interactLock = false;
		}
		if(key == KeyEvent.VK_SPACE){
			//System.out.println("HitSpace");
			
			if(!GameManager.getInstance().showIngameText){
				GameManager.getInstance().interact = false;
				GameManager.getInstance().promptText = false;
			}
			
			if(switchGameState){
				GameManager.getInstance().switchGameState(true, false);
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
			if(!GameManager.getInstance().interact)
				useHealthPotion();
			else
				GameManager.getInstance().interactKey =  0;
		}
		
		if(key == KeyEvent.VK_2){
			if(!GameManager.getInstance().interact)
				useManaPotion();
			else
				GameManager.getInstance().interactKey = 0;
		}
		
		if(key == KeyEvent.VK_3){
			GameManager.getInstance().interactKey = 0;
		}
		
		if(key == KeyEvent.VK_4){
			GameManager.getInstance().interactKey = 0;
		}
		
		//camera
		if(key == KeyEvent.VK_C){
			if(!GameManager.getInstance().dungeon)
				GameManager.getInstance().cameraOn = !GameManager.getInstance().cameraOn;
			else
				System.err.println("Can't switch CameraMode in Dungeons.");

			
			if(GameManager.getInstance().cameraOn && !getDirectionLock()){
				int scrollX = 0;
				int scrollY = 0;
				
				if(!(getRightLock() || getLeftLock()))
					scrollX = getX()-400;
				
				if(!(getUpLock() || getDownLock()))
					scrollY = getY()-300;
				
				
				Camera.getInstance().switchToCameraMode(scrollX, scrollY);
			}
			
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(!GameManager.getInstance().ingameMenu)
			keyReleasedPlayer(key);
		else
			keyReleasedIngameMenu(e);
		
		//ingameMenu
		if(key == KeyEvent.VK_ESCAPE){
			GameManager.getInstance().ingameMenu = !GameManager.getInstance().ingameMenu;
			
			if(GameManager.getInstance().ingameMenu)
				this.setInteractionLock(true);
			else
				setInteractionLock(false);
			
		}
		
		//debug
		if(key == KeyEvent.VK_B){
			GameManager.getInstance().showBounds = !GameManager.getInstance().showBounds;
		}
		
		if(key == KeyEvent.VK_P){
			GameManager.getInstance().printMsg = !GameManager.getInstance().printMsg;
		}
		if(key == KeyEvent.VK_O){
			System.out.println("Player@"+getX()+"x"+getY());
			System.out.println("Camera@"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
			if(GameManager.getInstance().dungeon)
			System.out.println("Map...@"+DungeonNavigator.getInstance().getXCoordinate()+"x"+DungeonNavigator.getInstance().getYCoordinate());
			if(GameManager.getInstance().overWorld)
			System.out.println("Map...@"+OverWorldNavigator.getInstance().getXCoordinate()+"x"+OverWorldNavigator.getInstance().getYCoordinate());
			
		}
		

		
		if(key == KeyEvent.VK_T){
			System.out.println("printText");
			//PlayerInterface.getInstance().setText("test");
			GameManager.getInstance().promptText = true;
			GameManager.getInstance().showIngameText = true;
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
		
		if(key == KeyEvent.VK_G){
			godlikeModus = !godlikeModus;
		}
		
		if(key == KeyEvent.VK_BACK_SPACE){
			MarioDark.deleteAllInstances();
		}
		
		if(key == KeyEvent.VK_V){
			GameManager.getInstance().moveFocus = false;
		}
		

		if(key == KeyEvent.VK_8)
			Sound.getInstance().toogleMusic();
		
		if(key == KeyEvent.VK_9)
			Sound.getInstance().toogleAtmo();
		
		if(key == KeyEvent.VK_0){
			GameManager.getInstance().switchGameState(true, false);
		}
		
		
	}
	
	
	
	
	public int getSpawnX(){return spawnX;}
	public int getSpawnY(){return spawnY;}
	public boolean getSpawnLock(){return spawnLock;}
	//
	public void setSpawnX(int spawnX){this.spawnX = spawnX;}
	public void setSpawnY(int spawnY){this.spawnY = spawnY;}
	public void setSpawnLock(boolean spawnLock){this.spawnLock = spawnLock;}
	
	public static void resetInstance(){
		if(player != null){
			player.setAlive(false);
			player.setDirectionLock(10);
			player = new Player();
		}
			
	}
	
	public static void setInstance(Player playerSave){
		player = playerSave;
	}
	
	public static Player getInstance(){
		if(player == null)
			player = new Player();
		
		return player;
	}
}
