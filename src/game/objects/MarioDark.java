package game.objects;
import game.objects.NPCMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import map.DungeonNavigator;
import map.OverWorldNavigator;

import core.GameManager;
import core.GameObjectManager;
import core.ItemListManager;




public class MarioDark extends NPCLogic implements Runnable{

	private final static int MAXINSTANCE = 10;
	private static MarioDark[] marioDark = new MarioDark[MAXINSTANCE];
	
	private Thread runThread;
	private ScheduledExecutorService execRun;
	
	
	private volatile static int instanceCounter;
	private int IDNumber;
	private boolean constructionLock = false;
	private boolean spawnLock = true;
	private boolean boss;
	private boolean overWorldSpawn;
	

	
	private MarioDark(int IDNumber, boolean boss){
		System.err.println("construct MarioDark: "+IDNumber);
		overWorldSpawn = GameManager.overWorld;
		this.IDNumber = IDNumber;
		this.boss = boss;
		
		initializeInstance();
		constructionLock = true;
		setMoveableID(IDNumber);
	}
	
	private MarioDark(){
		System.err.println("Caution: construct dummy MarioDark");
	}
	
	private void initializeInstance(){
		
		runThread = new Thread(this);
		execRun = Executors.newSingleThreadScheduledExecutor();
		execRun.scheduleWithFixedDelay(runThread, 10, 10, TimeUnit.MILLISECONDS);
		
		//GameManager.addGameObject(this);
	}

	public void run(){
		
		if((!GameManager.mapLoaded && GameManager.dungeon) || (GameManager.scrollDirection != 0 && !spawnLock && GameManager.dungeon))
			setAlive(false);
		
		if((GameManager.scrollDirection == 0 && getInitialized()))
			spawnLock = false;

		if(overWorldSpawn != GameManager.overWorld){
			setAlive(false);
			spawnLock = false;
		}
		
		if(GameManager.scrollDirection == 0 && getAlive()) {
			
			//System.out.println("@Pos:"+getX()+"x"+getY());
			//patrolRectangle(-1,false,100,100,100,200);
			//System.out.println("Life: "+getLife());
			//System.out.println("isVisible: "+getVisibleDrawable());
			executePattern();
		}
		
		if(!getAlive() && !spawnLock)
			deleteInstance();
			
	}
	
	private void deleteInstance(){
		
		//if(GameManager.scrollDirection == 0 && GameManager.mapLoaded)
		//	MarioDark.getInstance(false, IDNumber, boss).dropItem();

		setVisible(false);
		
		System.out.print("delete MarioDark@ID: "+IDNumber);

		if(GameManager.scrollDirection == 0 && GameManager.mapLoaded)
			MarioDark.getInstance(false, IDNumber, boss).dropItem();
		
		instanceCounter--;
		
		execRun.shutdown();
		execRun = null;
		GameManager.updateGameObject();
		
	
	}

	private void dropItem(){
		
		if(!boss)
			Player.getInstance().addExperience(1);
		else
			Player.getInstance().addExperience(3);
		
		
		//heart/healthRandom rand;
	
		int random1 = new Random().nextInt(100 - 0 + 1) + 0;
		boolean dropKey = false;
		
		System.out.println("==>MarioDark.drops Item@"+getX()+"x"+getY());
		
		if(DungeonNavigator.getInstance().getXMap() == 1 && DungeonNavigator.getInstance().getYMap() == 3){
			dropKey = ItemListManager.dropKey(getX(), getY(), 5, 0, 0, 0);
		}
		
		if(DungeonNavigator.getInstance().getXMap() == 1 && DungeonNavigator.getInstance().getYMap() == 1 && instanceCounter == 1){
			dropKey = ItemListManager.dropKey(getX(), getY(), 5, 0, 0, 1);
		}
		
		if(OverWorldNavigator.getInstance().getID() == 1 && GameManager.overWorld){
			ItemListManager.dropItem(getX(), getY(), 1, 1, 0);
		}
		
		
		if(!dropKey){
			if(random1 < 75){
				if(random1 > 55)
					ItemListManager.dropItem(getX(), getY(), 0, 1, 0);
				else if(random1 > 35)
					ItemListManager.dropItem(getX(), getY(), 0, 2, 0);
				else
					ItemListManager.dropItem(getX(), getY(), 0, 0, 0);
			}
				
				
		}
		
		setBossDefeatedFlag();
	}

	private void setBossDefeatedFlag(){
		if(DungeonNavigator.getInstance().getXMap() == 2 && DungeonNavigator.getInstance().getYMap() == 2){
			GameObjectManager.defeatBoss(22);
			ItemListManager.dropItem(getX(), getY(), 3, 0, 0);
		}
		if(DungeonNavigator.getInstance().getXMap() == 2 && DungeonNavigator.getInstance().getYMap() == 1 && instanceCounter == 1){
			GameObjectManager.defeatBoss(21);
		}
		if(DungeonNavigator.getInstance().getXMap() == 2 && DungeonNavigator.getInstance().getYMap() == 0){
			GameObjectManager.defeatBoss(20);
		}
	}
	
	public static int getMaxInstance(){
		return MAXINSTANCE;
	}
	
	public static int getInstanceCounter(){
		return instanceCounter;
	}
	
	public static synchronized MarioDark getInstance(boolean newInstance, int IDNumber, boolean boss){
		
		if(newInstance){
			marioDark[IDNumber] = new MarioDark(IDNumber, boss);
			instanceCounter++;
			System.out.println("MarioDark.NEWInstance:"+IDNumber);
			return marioDark[IDNumber];
		}
		if(!newInstance && IDNumber <= MAXINSTANCE){
			//System.out.println("MarioDark.getInstance:"+IDNumber);
			return marioDark[IDNumber];
		}
			
		else {
			System.err.println("Caution: Problem with Instance.return");
			return new MarioDark();
		}
			
	}
	
	public static synchronized boolean checkInstanceAlive(int IDNumber){
		if(marioDark[IDNumber] != null)
			return true;
		else
			return false;
		
	}
	
	public static void deleteAllInstances(){
		for(int i = 0; i < MAXINSTANCE; i++){
			if(marioDark[i] != null){
				marioDark[i].setAlive(false);
				marioDark[i].spawnLock = false;
			}
		}
		//instanceCounter = 0;	
	}
	
	public boolean getConstructionLock(){
		return constructionLock;
	}
	
	
}
