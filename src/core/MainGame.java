package core;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import objects.Guide;
import objects.MapObject;
import objects.MarioDark;
import objects.Merchant;
import objects.Player;


import map.Camera;
import map.OverWorldNavigator;
import map.DungeonNavigator;

public class MainGame extends JFrame implements Runnable, FileLink{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2516853831687335362L;
	private ScheduledThreadPoolExecutor threadPool;
	private Thread paintBoardThread;
	private Thread playerThread;
	private Thread cameraThread;
	private Thread collisionThread;
	private Thread managerThread;
	private Thread soundThread;
	private ScheduledThreadPoolExecutor threadPoolManager;
	
	private MainGame(){
		
		add(Board.getInstance());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize (810, 653);
		//setLocationRelativeTo(null);
		setTitle("ProPraRPG-build02");
		setResizable(false);
		setVisible(true);


		paintBoardThread = new Thread(Board.getInstance());
		paintBoardThread.start();
		soundThread = new Thread(Sound.getInstance());

		managerThread = new Thread(this);
		threadPoolManager = new ScheduledThreadPoolExecutor(3);
		
		threadPoolManager.scheduleWithFixedDelay(managerThread,100,1000, TimeUnit.MILLISECONDS);
		threadPoolManager.scheduleWithFixedDelay(soundThread,100,100, TimeUnit.MILLISECONDS);
		
		
		GameManager.getInstance().setGameInitialized(true);
		GameManager.getInstance().switchGameState(true, false);
		
		
	}

	public static void main(String[] args){
		new MainGame();
		
		try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
		
	}
	
	private void initializePlayer(){
		
		Player.getInstance().initializeImage(player1Sprite, 90, 120, 8);
		Player.getInstance().initializeAttributes(2, 3.5, true, 0, 75, 45, 20);
		Player.getInstance().initializePosition(600, 350, 5);
		GameManager.addGameObject(Player.getInstance());
	
	}
	
	private void initializeMap(){
	
		GameManager.getInstance().overWorld = true;
		GameManager.getInstance().dungeon = false;
		

		if(GameManager.getInstance().overWorld){

			int xStart = 1890;
			int yStart = 0;
			//int xStart = 100;
			//int yStart = 100;
			GameManager.getInstance().cameraOn = true;
			OverWorldNavigator.getInstance().initializeMap(xStart,yStart,0,500,200);
		}
			

		if(GameManager.getInstance().dungeon)
			DungeonNavigator.getInstance().initializeMap(0,0,0,100,300);


		if(GameManager.getInstance().cameraOn && GameManager.getInstance().overWorld){

				if(OverWorldNavigator.getInstance().getXCoordinate() > 0 && getX() <= 400){
					OverWorldNavigator.getInstance().setXCoordinate(0);
					Player.getInstance().setDirectionLock(2);
				}

				if(OverWorldNavigator.getInstance().getXCoordinate() < -(OverWorldNavigator.getInstance().getWidthMap()-810) && getX() >= 400){
					OverWorldNavigator.getInstance().setXCoordinate(-(OverWorldNavigator.getInstance().getWidthMap()-810));
					Player.getInstance().setDirectionLock(4);
				}
	
				if(OverWorldNavigator.getInstance().getYCoordinate() > 0 && getY() >= 300){
					OverWorldNavigator.getInstance().setYCoordinate(0);
					Player.getInstance().setDirectionLock(1);
				}
	
				if(OverWorldNavigator.getInstance().getYCoordinate() < -(OverWorldNavigator.getInstance().getHeightMap()-630) && getY() >= 300){
					OverWorldNavigator.getInstance().setYCoordinate(-(OverWorldNavigator.getInstance().getHeightMap()-630));
					Player.getInstance().setDirectionLock(3);
				}	
				
				if(!Player.getInstance().getDirectionLock()){
					int scrollX = 0;
					int scrollY = 0;
					
					if(!(Player.getInstance().getRightLock() || Player.getInstance().getLeftLock()))
						scrollX = getX()-400;
					
					if(!(Player.getInstance().getUpLock() || Player.getInstance().getDownLock()))
						scrollY = getY()-300;
					
					
					Camera.getInstance().switchToCameraMode(scrollX, scrollY);
				}
		}

	}
	
	private void initializeThreads(){
		playerThread = new Thread(Player.getInstance());
		cameraThread = new Thread(Camera.getInstance());
		collisionThread = new Thread(CollisionDetection.getInstance());
		
		threadPool = new ScheduledThreadPoolExecutor(4);

		threadPool.scheduleWithFixedDelay(playerThread, 10, 10, TimeUnit.MILLISECONDS);
		threadPool.scheduleWithFixedDelay(cameraThread, 20, 50, TimeUnit.MILLISECONDS);
		threadPool.scheduleWithFixedDelay(collisionThread, 30, 10, TimeUnit.MILLISECONDS);
		
	}
	

	public void run(){
		
		//System.out.println("System.Check");
		
		if(GameManager.getInstance().getMenu() && GameManager.getInstance().switchGameState){
			GameManager.getInstance().switchGameState = false;
			
			if(threadPool != null){
				threadPool.shutdown();
				threadPool = new ScheduledThreadPoolExecutor(4);	
			}
			
			System.out.println("==>Menu");
			resetGame();
			
			
		}
		
		
		if(GameManager.getInstance().getIngame() && GameManager.getInstance().switchGameState){
			GameManager.getInstance().switchGameState = false;
			System.out.println("==>InGame");
			resetGame();
			initializeGame();
			
		}
		
		
	}

	private void initializeGame(){
		initializePlayer();
		initializeMap();
		initializeThreads();
	}
	
	private void resetGame(){
		System.out.println("=====resetInstances=====");
		
		CollisionDetection.resetInstance();
		GameManager.resetInstance();
		GameObjectManager.resetInstance();
		IngameMenu.resetInstance();
		ItemListManager.resetInstance();
		PlayerInterface.resetInstance();
		
		Camera.resetInstance();
		DungeonNavigator.resetInstance();
		OverWorldNavigator.resetInstance();
		
		MapObject.resetInstance();
		Player.resetInstance();
		Guide.resetInstance();
		Merchant.resetInstance();
		MarioDark.deleteAllInstances();
		
	}
	
	
}
