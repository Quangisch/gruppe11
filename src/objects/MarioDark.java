package objects;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import map.DungeonNavigator;
import map.OverWorldNavigator;

import core.GameManager;
import core.GameObjectManager;
import core.ItemListManager;
import core.EnemyManager;



public class MarioDark extends NPCLogic implements Runnable, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3327169509412998041L;
	private final static int MAXINSTANCE = 10;
	private static MarioDark[] marioDark = new MarioDark[MAXINSTANCE];
	
	transient private Thread runThread;
	transient private ScheduledExecutorService execRun;
	private int enemyManagerType;
	private int ID;
	private boolean boss;
	
	private volatile static int instanceCounter;
	private boolean constructionLock = false;
	private boolean spawnLock = true;
	private boolean overWorldSpawn;
	

	
	private MarioDark(int IDNumber, boolean boss){
		System.err.println("construct MarioDark: "+IDNumber);
		overWorldSpawn = GameManager.getInstance().overWorld;
		this.ID = IDNumber;
		this.boss = boss;
		initializeThreads();
		constructionLock = true;
		setMoveableBoss(boss);
	}
	
	private MarioDark(){
		System.err.println("Caution: construct dummy MarioDark");
	}
	
	private void initializeThreads(){
		
		runThread = new Thread(this);
		execRun = Executors.newSingleThreadScheduledExecutor();
		execRun.scheduleWithFixedDelay(runThread, 10, 10, TimeUnit.MILLISECONDS);
		
	}

	public void run(){
		
		if((!GameManager.getInstance().mapLoaded && GameManager.getInstance().dungeon) || (GameManager.getInstance().scrollDirection != 0 && !spawnLock && GameManager.getInstance().dungeon))
			setAlive(false);
		
		if((GameManager.getInstance().scrollDirection == 0 && getInitialized()))
			spawnLock = false;

		if(overWorldSpawn != GameManager.getInstance().overWorld){
			setAlive(false);
			spawnLock = false;
		}
		
		if(GameManager.getInstance().scrollDirection == 0 && getAlive()) {
			
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
		
		System.out.print("delete MarioDark@ID: "+ID);

		if(GameManager.getInstance().scrollDirection == 0 && GameManager.getInstance().mapLoaded)
			MarioDark.getInstance(false, ID, getMoveableBoss()).dropItem();
		
		instanceCounter--;
		
		execRun.shutdown();
		execRun = null;
		GameManager.getInstance().updateGameObject();
		
	
	}

	private void dropItem(){
		
		if(!getMoveableBoss()){
			double getXP = getAttackDamage()*getMaxLife()/6;
			
			if(getXP >= 1)
				Player.getInstance().addExperience((int)(getXP));
			else
				Player.getInstance().addExperience(1);
		}
			
		else
			Player.getInstance().addExperience(3);
		
		
		//heart/healthRandom rand;
	
		int random1 = new Random().nextInt(100 - 0 + 1) + 0;
		boolean drop = false;
		
		System.out.println("==>MarioDark.drops Item@"+getX()+"x"+getY());
		
		if(DungeonNavigator.getInstance().getXMap() == 1 && DungeonNavigator.getInstance().getYMap() == 3)
			drop = GameObjectManager.getInstance().dropKey(getX(), getY(), 5, 0, 0, 0);
	
		if(DungeonNavigator.getInstance().getXMap() == 1 && DungeonNavigator.getInstance().getYMap() == 1 && instanceCounter == 1)
			drop = GameObjectManager.getInstance().dropKey(getX(), getY(), 5, 0, 0, 1);
		
		
		if(OverWorldNavigator.getInstance().getID() == 1 && GameManager.getInstance().overWorld){
		
			if(random1 < 75){
				if(random1 > 30)
					ItemListManager.dropItem(getX(), getY(), 0, 0, 0);
				else if(random1 > 15)
					ItemListManager.dropItem(getX(), getY(), 1, 1, 0);
				else
					ItemListManager.dropItem(getX(), getY(), 1, 0, 0);
			}
		
			drop = true;
		}
		
		drop = setBossDefeatedFlag();
		
		if(!drop){
			if(random1 < 75){
				if(random1 > 55)
					ItemListManager.dropItem(getX(), getY(), 0, 1, 0);
				else if(random1 > 35)
					ItemListManager.dropItem(getX(), getY(), 0, 2, 0);
				else
					ItemListManager.dropItem(getX(), getY(), 0, 0, 0);
			}
				
				
		}
		
		
	}

	private boolean setBossDefeatedFlag(){
		if(DungeonNavigator.getInstance().getXMap() == 2 && DungeonNavigator.getInstance().getYMap() == 2){
			GameObjectManager.getInstance().defeatBoss(22);
			ItemListManager.dropItem(getX(), getY(), 3, 0, 0);
			return true;
		}
		if(DungeonNavigator.getInstance().getXMap() == 2 && DungeonNavigator.getInstance().getYMap() == 1 && instanceCounter == 1){
			GameObjectManager.getInstance().defeatBoss(21);
			return true;
		}
		if(DungeonNavigator.getInstance().getXMap() == 2 && DungeonNavigator.getInstance().getYMap() == 0){
			GameObjectManager.getInstance().defeatBoss(20);
			GameManager.getInstance().win = true;
			return true;
		}
		return false;
	}
	
	public void setEnemyManagerType(int enemyManagerType){
		this.enemyManagerType = enemyManagerType;
	}
	
	
	public static int getMaxInstance(){
		return MAXINSTANCE;
	}
	
	public static int getInstanceCounter(){
		return instanceCounter;
	}
	
	public static void reinitializeAllInstances(){
		for(int i = 0; i < marioDark.length; i++){
			if(marioDark[i] != null){
				if(marioDark[i].getAlive()){
					EnemyManager.reinitializeEnemyInstance(marioDark[i].enemyManagerType, marioDark[i].ID, marioDark[i].boss);
					marioDark[i].initializeThreads();
					
				}
				
			}
		}
	}
	
	public static void setInstance(MarioDark[] marioDarkSave){
		marioDark = marioDarkSave;
	}
	
	public static MarioDark[] getAllInstances(){
		return marioDark;
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
