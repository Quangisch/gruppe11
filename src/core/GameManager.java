package core;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import map.*;
import menu.MenuIngame;
import menu.MenuMain;
import characters.*;

public class GameManager extends JFrame implements Runnable, GameObjects{

	ScheduledThreadPoolExecutor playerScheduler;
	ScheduledThreadPoolExecutor menuScheduler;
	ScheduledThreadPoolExecutor mapScheduler;
	ScheduledThreadPoolExecutor enemyScheduler;
	
	Thread menuIngameThread;
	Thread menuMainThread;
	Thread overWorldMapThread;
	Thread dungeonCollisionThread;
	Thread dungeonNavigatorThread;
	Thread playerThread;
	Thread playerInterfaceThread;
	Thread collisionDetectionThread;
	Thread dynamicMapAnimationThread;
	Thread goombaThread;
	
	
	public static boolean printMsg = false;
	public static boolean win = false;
	public static boolean gameOver = false;
	
	public static boolean repaintNow = false;
	public static boolean paintBounds = false;
	
	public static boolean menu = false;
	public static boolean ingame = true;
	
	public static int musicVolume = 50;
	public static int soundVolume = 50;
	
	public static boolean switchGameState = true;
	public static boolean changeMapModus = false;
	
	public GameManager(){
		
		add(GameObjects.board);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize (810, 653);
		//setLocationRelativeTo(null);
		setTitle("DC - Prototype");
		setResizable(false);
		setVisible(true);
		

		//initiate start dungeon
		if(false){
			dungeonNavigator.setAreaID(1);
			dungeonNavigator.setX(0); dungeonNavigator.setY(3);
			OverWorldMap.overWorld = false;
			dungeonNavigator.setDungeon(true);
			
			player.setX(230); 
			player.setY(500);
			player.setLastDirection(1);
		}
	
		
		
		//initiate start overWorld
		if(true){
			OverWorldMap.overWorld = true;
			dungeonNavigator.setDungeon(false);
			overWorldMap.setCameraX(0); overWorldMap.setCameraY(0);
			player.setAbsoluteX(0);player.setAbsoluteY(0);
			player.setX(110); player.setY(20);
			
		}
		
		playerScheduler = new ScheduledThreadPoolExecutor(3);
		menuScheduler = new ScheduledThreadPoolExecutor(2);
		mapScheduler = new ScheduledThreadPoolExecutor(3);
		enemyScheduler = new ScheduledThreadPoolExecutor(3);
		
		menuIngameThread = new Thread(menuIngame);
		menuMainThread = new Thread(menuMain);
		
		overWorldMapThread = new Thread(overWorldMap);
		dungeonCollisionThread = new Thread(dungeonCollision);
		dungeonNavigatorThread = new Thread(dungeonNavigator);
		playerThread = new Thread(player);
		playerInterfaceThread = new Thread(playerInterface);
		collisionDetectionThread = new Thread(collisionDetection);
		dynamicMapAnimationThread = new Thread(dynamicMapAnimation);
		goombaThread = new Thread(goomba);
		
	}
	
	public void run(){
		
		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie){
			System.err.println("GameManager:"+ie);
		}
		*/
		if(switchGameState){
			switchGameState = false;
			
			//main menu
			if(menu && !ingame){
				if(!playerScheduler.isShutdown() && !mapScheduler.isShutdown()){
					playerScheduler.shutdown();
					mapScheduler.shutdown();
				}
				if(menuScheduler.isShutdown())
					menuScheduler.scheduleWithFixedDelay(menuMainThread,10,20,TimeUnit.MILLISECONDS);
			}
			//ingame menu
			if(menu && ingame){
				if(menuScheduler.isShutdown())
					menuScheduler.scheduleWithFixedDelay(menuIngame, 10, 20, TimeUnit.MILLISECONDS);
			}
			
			//ingame
			if(ingame && !menu){
				if(!menuScheduler.isShutdown()){
					menuScheduler.shutdown();
				}
			
					playerScheduler.scheduleWithFixedDelay(playerThread, 400, 10,TimeUnit.MILLISECONDS);
					playerScheduler.scheduleWithFixedDelay(playerInterface, 600, 10, TimeUnit.MILLISECONDS);
					playerScheduler.scheduleWithFixedDelay(collisionDetectionThread, 450, 10, TimeUnit.MILLISECONDS);
					
					
					mapScheduler.scheduleWithFixedDelay(dungeonNavigatorThread, 30, 50, TimeUnit.MILLISECONDS);
					mapScheduler.scheduleWithFixedDelay(dungeonCollision, 20, 50,TimeUnit.MILLISECONDS);
					mapScheduler.scheduleWithFixedDelay(overWorldMapThread, 50, 50,TimeUnit.MILLISECONDS);
					mapScheduler.scheduleWithFixedDelay(dynamicMapAnimationThread,100,50,TimeUnit.MILLISECONDS);
					
					enemyScheduler.scheduleWithFixedDelay(goombaThread,200,15,TimeUnit.MILLISECONDS);
					
					
			/*TODO
				if(overWorldMap.overWorld){
					if(!mapScheduler.isShutdown()){
						mapScheduler.remove(dungeonNavigatorThread);
						mapScheduler.remove(dungeonBuilderThread);
					}
						mapScheduler.scheduleWithFixedDelay(overWorldMapThread, 50, 50,TimeUnit.MILLISECONDS);
				}
				
				if(dungeonNavigator.dungeon){
					if(!mapScheduler.isShutdown()){
						mapScheduler.remove(overWorldMapThread);
					}
						mapScheduler.scheduleWithFixedDelay(dungeonNavigatorThread, 50, 50, TimeUnit.MILLISECONDS);
						mapScheduler.scheduleWithFixedDelay(dungeonBuilderThread, 20, 50,TimeUnit.MILLISECONDS);
				}
			*/
			}
		}
		/*
		if(changeMapModus){
			System.out.println("hrr");
			changeMapModus = false;
			changeMapThreads();
		}
		*/
	}
	/*
	public void changeMapThreads(){
		if(dungeonNavigator.dungeon){
			mapScheduler.remove(overWorldMapThread);
			
			mapScheduler.scheduleWithFixedDelay(dungeonNavigatorThread, 50, 50, TimeUnit.MILLISECONDS);
			mapScheduler.scheduleWithFixedDelay(dungeonBuilderThread, 20, 50,TimeUnit.MILLISECONDS);
			
		}
		
		if(overWorldMap.overWorld){
			mapScheduler.remove(dungeonNavigatorThread);
			mapScheduler.remove(dungeonBuilderThread);
			
			mapScheduler.scheduleWithFixedDelay(overWorldMapThread, 50, 50,TimeUnit.MILLISECONDS);
		}
	}
	*/

}



