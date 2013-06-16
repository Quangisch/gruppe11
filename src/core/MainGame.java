package core;

import game.objects.MarioDark;
import game.objects.Player;

import java.awt.Color;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.Timer;


import map.Camera;
import map.OverWorldNavigator;
import map.DungeonNavigator;

public class MainGame extends JFrame implements Runnable, FileLink{
	
	private static Board board;
	private static Player player;
	
	private ScheduledThreadPoolExecutor threadPool;
	private Thread paintBoardThread;
	private Thread playerThread;
	private Thread cameraThread;
	private Thread collisionThread;
	private Thread interfaceThread;
	private Thread managerThread;
	private ScheduledThreadPoolExecutor threadPoolManager;
	
	private MainGame(){
		board = Board.getInstance();
		add(board);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize (810, 653);
		//setLocationRelativeTo(null);
		setTitle("ProPraRPG-build02");
		setResizable(false);
		setVisible(true);
		
		initializeObjects();
		initializeMap();
		initializeThreads();
		
		
		paintBoardThread.start();
		managerThread = new Thread(this);
		threadPoolManager = new ScheduledThreadPoolExecutor(1);
		threadPoolManager.scheduleWithFixedDelay(managerThread,100,1000, TimeUnit.MILLISECONDS);
		
		GameManager.getInstance().setGameInitialized(true);
		
	}

	public static void main(String[] args){
		new MainGame();
		
		try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
		
	}
	
	private static void initializeObjects(){
		
		
		player = Player.getInstance();
		player.initializeImage(player1Sprite, 90, 120, 8);
		player.initializeAttributes(2, 3.5, true, 0, 75, 45, 20);
		player.initializePosition(600, 350, 5);
		GameManager.addGameObject(player);
		
		/*
		MarioDark.getInstance(true, 0).initializeImage(enemyDark, 90, 120, 8);
		MarioDark.getInstance(false, 0).initializeAttributes(2, 3, true, 0, 75, 45, 20);
		MarioDark.getInstance(false, 0).initializePosition(200, 100, 5);
		MarioDark.getInstance(false, 0).setBehaviour(3);
		GameManager.addGameObject(MarioDark.getInstance(false, 0));
		*/
		/*
		MarioDark.getInstance(true, 0).initializeImage(enemyBright, 90, 120, 8);
		MarioDark.getInstance(false, 0).initializeAttributes(2, 3, true, 0, 75, 45, 20);
		MarioDark.getInstance(false, 0).initializePosition(200, 300, 5);
		MarioDark.getInstance(false, 0).setBehaviour(2);
		MarioDark.getInstance(false, 0).setMoveableType(2);
		GameManager.addGameObject(MarioDark.getInstance(false, 0));
		*/
		//System.out.println("=======>>>"+MarioDark.checkInstanceAlive(1));
		
		PlayerInterface.getInstance().initializeInterface();
		PlayerInterface.getInstance().buildInterface();
		PlayerInterface.getInstance().setText("bli bla blub/nHRRRR/nwhut up?/n/Yoloooo/nyawn.../n...+_+/nstop it!");
		
		
		System.out.println("===>ini.Objects");

	}
	
	private void initializeMap(){
		
		GameManager.getInstance().switchGameState(true, false);
		
		GameManager.overWorld = false;
		GameManager.dungeon = true;
		
		if(GameManager.overWorld)
			OverWorldNavigator.getInstance().initializeMap(0,0,1,500,500);
		if(GameManager.dungeon)
			DungeonNavigator.getInstance().initializeMap(0,3,0,100,100);
	}
	
	private void initializeThreads(){
		paintBoardThread = new Thread(board);
		playerThread = new Thread(player);
		cameraThread = new Thread(Camera.getInstance());
		collisionThread = new Thread(CollisionDetection.getInstance());
		interfaceThread = new Thread(PlayerInterface.getInstance());

		threadPool = new ScheduledThreadPoolExecutor(4);

	}
	
	
	private void startThreads(){
		threadPool.scheduleWithFixedDelay(playerThread, 10, 10, TimeUnit.MILLISECONDS);
		threadPool.scheduleWithFixedDelay(cameraThread, 20, 50, TimeUnit.MILLISECONDS);
		threadPool.scheduleWithFixedDelay(collisionThread, 30, 10, TimeUnit.MILLISECONDS);
		threadPool.scheduleWithFixedDelay(interfaceThread, 40, 20, TimeUnit.MILLISECONDS);
		
		//PlayerInterface.getInstance().buildText();
	}
	
	public void run(){
		
		if(GameManager.getInstance().getMenu() && GameManager.switchGameState){
			GameManager.switchGameState = false;
			threadPool.shutdown();
		}
			
		
		if(GameManager.getInstance().getIngame() && GameManager.switchGameState){
			GameManager.switchGameState = false;
			startThreads();
		}
			
		
		System.out.println("ThreadPoolManager.check");
		
		/*
		if(!collisionThread.isAlive()){
			collisionThread = null;
			collisionThread = new Thread(CollisionDetection.getInstance());
			threadPool.scheduleWithFixedDelay(collisionThread, 30, 10, TimeUnit.MILLISECONDS);
			System.err.println("==========> COLLISION THREAD DEAD>=====================");
		}
		*/
		

		
	}


	
}
