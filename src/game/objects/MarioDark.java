package game.objects;
import game.objects.EnemyMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;




public class MarioDark extends EnemyLogic{

	private final static int MAXINSTANCE = 10;
	private static MarioDark[] marioDark = new MarioDark[MAXINSTANCE];
	private static RunTask[] runTask = new RunTask[MAXINSTANCE];
	private static Timer[] runTimer = new Timer[MAXINSTANCE];

	private volatile static int instanceCounter;
	private int IDNumber;
	private boolean constructionLock = false;
	

	
	private MarioDark(int IDNumber){
		System.err.println("construct MarioDark: "+IDNumber);
		this.IDNumber = IDNumber;
		runTimer[IDNumber] = new Timer();
		runTask[IDNumber] = new RunTask(IDNumber);
		runTimer[IDNumber].schedule(runTask[IDNumber], 500, 10);
		constructionLock = true;
		setMoveableID(IDNumber);
	}
	
	private MarioDark(){
		System.err.println("Caution: construct dummy MarioDark");
	}
	

	public void start(){

		if(getAlive()) {
			
			//patrolRectangle(-1,false,100,100,100,200);
			//System.out.println("Life: "+getLife());
			//System.out.println("isAlive: "+getAlive());
			executePattern();
		
		} else
			System.err.println("=====MarioDark.notAlive");
	}
	


	public static int getMaxInstance(){
		return MAXINSTANCE;
	}
	
	public static int getInstanceCounter(){
		return instanceCounter;
	}
	
	public static synchronized MarioDark getInstance(boolean newInstance, int IDNumber){
		
		if(newInstance){
			marioDark[IDNumber] = new MarioDark(IDNumber);
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
	
	public static void deleteInstance(int IDNumber){
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
			//System.out.println("RunTask "+IDNumber+" running");
			if(MarioDark.getInstance(false, IDNumber).getAlive())
				MarioDark.getInstance(false, IDNumber).start();
			else {
				MarioDark.getInstance(false, IDNumber).deleteInstance(IDNumber);
			}
		}
	}
	
}
