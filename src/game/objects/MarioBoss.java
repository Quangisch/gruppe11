package game.objects;
import game.objects.EnemyMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import map.DungeonNavigator;

import core.GameManager;
import core.ItemListManager;




public class MarioBoss extends EnemyLogic{

	private final static int MAXINSTANCE = 3;
	private static MarioBoss[] marioDark = new MarioBoss[MAXINSTANCE];
	private static RunTask[] runTask = new RunTask[MAXINSTANCE];
	private static Timer[] runTimer = new Timer[MAXINSTANCE];

	private volatile static int instanceCounter;
	private int IDNumber;
	private boolean constructionLock = false;
	private boolean spawnLock = true;
	

	
	private MarioBoss(int IDNumber){
		System.err.println("construct MarioDark: "+IDNumber);
		this.IDNumber = IDNumber;
		runTimer[IDNumber] = new Timer();
		runTask[IDNumber] = new RunTask(IDNumber);
		runTimer[IDNumber].schedule(runTask[IDNumber], 500, 10);
		constructionLock = true;
		setMoveableID(IDNumber);
	}
	
	private MarioBoss(){
		System.err.println("Caution: construct dummy MarioDark");
	}
	

	public void running(){

		if(getAlive()) {
			
			//patrolRectangle(-1,false,100,100,100,200);
			//System.out.println("Life: "+getLife());
			//System.out.println("isAlive: "+getAlive());
			executePattern();
		
		} else
			System.err.println("=====MarioDark.notAlive");
	}
	

	private void dropItem(){
		System.out.println("==>MarioDark.drops Item@"+getX()+"x"+getY());
		
		//heart/healthRandom rand;
	
		int random1 = new Random().nextInt(100 - 0 + 1) + 0;
		int random2 = new Random().nextInt(101) + 0;
		boolean dropKey = false;
		
		System.err.println("======>randomNum:"+random1+"x"+random2);
		
		if(DungeonNavigator.getInstance().getXMap() == 1 && DungeonNavigator.getInstance().getYMap() == 3){
			dropKey = ItemListManager.dropKey(getX(), getY(), 5, 0, 0, 0);
		}
		
		if(!dropKey){
			if(random1 < 20)
				ItemListManager.dropItem(getX(), getY(), 0, 1, 0);
			else if(random2 < 20)
				ItemListManager.dropItem(getX(), getY(), 0, 2, 0);
		}
		

	}

	public static int getMaxInstance(){
		return MAXINSTANCE;
	}
	
	public static int getInstanceCounter(){
		return instanceCounter;
	}
	
	public static synchronized MarioBoss getInstance(boolean newInstance, int IDNumber){
		
		if(newInstance){
			marioDark[IDNumber] = new MarioBoss(IDNumber);
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
			return new MarioBoss();
		}
			
	}
	
	public static synchronized boolean checkInstanceAlive(int IDNumber){
		if(marioDark[IDNumber] != null)
			return true;
		else
			return false;
		
	}
	
	private void deleteInstance(int IDNumber){
		marioDark[IDNumber].setVisible(false);
		marioDark[IDNumber] = null;
		runTimer[IDNumber].cancel();
		runTimer[IDNumber].purge();
		runTimer[IDNumber] = null;
		runTask[IDNumber] = null;
		System.err.println("remove MarioDark: "+IDNumber);
		instanceCounter--;
	}
	
	public static void deleteAllInstances(){
		for(int i = 0; i < MAXINSTANCE; i++){
			if(marioDark[i] != null){
				marioDark[i].setAlive(false);
			}
		}
		//instanceCounter = 0;	
	}
	
	public boolean getConstructionLock(){
		return constructionLock;
	}
	
	private class RunTask extends TimerTask{
		private int IDNumber;
		
		private RunTask(int IDNumber){
			this.IDNumber = IDNumber;
		}
		public void run() {
			
			if((!GameManager.mapLoaded && GameManager.dungeon) || (GameManager.scrollDirection != 0 && !spawnLock && GameManager.dungeon))
				setAlive(false);
			
			if(GameManager.scrollDirection == 0)
				spawnLock = false;
			
			
			//System.out.println("RunTask "+IDNumber+" running");
			if(MarioBoss.getInstance(false, IDNumber).getAlive())
				MarioBoss.getInstance(false, IDNumber).running();
			else {
				
				if(GameManager.scrollDirection == 0 && GameManager.mapLoaded)
					MarioBoss.getInstance(false, IDNumber).dropItem();
				
				GameManager.updateGameObject();
				MarioBoss.getInstance(false, IDNumber).deleteInstance(IDNumber);
			}
		}
	}
	
}
