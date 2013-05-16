package core;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import characters.Enemy;
import characters.Player;
import characters.PlayerInterface;

import map.DungeonBuilder;
import map.DungeonCollision;
import map.DungeonNavigator;
import map.OverWorldCamera;
import map.OverWorldMap;
import menu.MenuIngame;
import menu.MenuMain;




public class Board extends JPanel implements ActionListener, FileLink{
	//instantiates classes
	/*
	final static MenuIngame ingameMenu = new MenuIngame();
	final static MenuMain mainMenu = new MenuMain();
	final static OverWorldMap overWorldMap = new OverWorldMap();
	final static Player player = new Player();
	final static OverWorldCamera camera = new OverWorldCamera();
	final static DungeonNavigator dungeonNavigator = new DungeonNavigator();
	final static CollisionDetection collisionDetection = new CollisionDetection();
	final static DungeonBuilder dungeonBuilder = new DungeonBuilder();
	final static PlayerInterface playerInterface = new PlayerInterface();
	*/
	
	MenuMain menuMain;
	MenuIngame menuIngame;
	
	Player player;
	PlayerInterface playerInterface;
	OverWorldMap overWorldMap;
	DungeonNavigator dungeonNavigator;
	DungeonBuilder dungeonBuilder;
	CollisionDetection collisionDetection;
	
	
	//threads
	/*
	static int ingameThreadCounter = 5;
	static int menuThreadCounter = 5;
	final static ScheduledThreadPoolExecutor ingameScheduler = new ScheduledThreadPoolExecutor(ingameThreadCounter);
	final static ScheduledThreadPoolExecutor menuScheduler = new ScheduledThreadPoolExecutor(menuThreadCounter);
	final static Thread ingameMenuThread = new Thread(ingameMenu);
	final static Thread mainMenuThread = new Thread(mainMenu);
	final static Thread mapThread = new Thread(overWorldMap);
	final static Thread dungeonBuilderThread = new Thread(dungeonBuilder);
	final static Thread dungeonNavigatorThread = new Thread(dungeonNavigator);
	final static Thread playerThread = new Thread(player);
	final static Thread playerInterfaceThread = new Thread(playerInterface);
	
	final static Thread cameraThread = new Thread(camera);
	final static Thread collisionDetectionThread = new Thread(collisionDetection);
	*/
	//instance variables
	

	private Timer repaintTimer;
	private Graphics2D g2d;

	public Board(MenuMain menuMain, MenuIngame menuIngame, Player player,PlayerInterface playerInterface,OverWorldMap overWorldMap,DungeonNavigator dungeonNavigator,DungeonBuilder dungeonBuilder,CollisionDetection collisionDetection){
		this.menuMain = menuMain;
		this.menuIngame = menuIngame;
		this.player = player;
		this.playerInterface = playerInterface;
		this.overWorldMap = overWorldMap;
		this.dungeonNavigator = dungeonNavigator;
		this.dungeonBuilder = dungeonBuilder;
		this.collisionDetection = collisionDetection;
		
		
		
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(Color.DARK_GRAY);
		
		
		//Listeners
		this.addMouseListener(new MAdapter());
		this.addKeyListener(new KAdapter());
		
		//Timer
		repaintTimer = new Timer(5, this);
		repaintTimer.start();
		

			
		System.out.println("->Board");
		start();
	}
	
	public void paint(Graphics g) {

		super.paint(g);
		g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/*
		
		if (!GameManager.ingame){
			//paint MenuMain
			menuMain.paintComponents(g2d);
		}
		
		if(GameManager.ingame && GameManager.menu){
			menuIngame.paintComponents(g2d);
			
		}
		*/
		if(GameManager.gameOver){
			GameManager.ingame = false; GameManager.menu = true;
			g2d.setColor(Color.red);
			Font textFont = new Font("Arial", Font.BOLD, 75);  
			g.setFont(textFont);  
			g2d.drawString("Game Over",220,300);
		}
		
		if(GameManager.win){
			GameManager.ingame = false; GameManager.menu = true;
			g2d.setColor(Color.yellow);
			Font textFont = new Font("Arial", Font.BOLD, 75);  
			g.setFont(textFont);  
			g2d.drawString("Win",330,330);
		}
		
		
		//TODO
		if (GameManager.ingame && !GameManager.menu && !GameManager.gameOver && !GameManager.win){
		//if(false){
			if(OverWorldMap.overWorld)
				overWorldMap.paintComponents(g2d);
			if(DungeonNavigator.dungeon)
				dungeonBuilder.paintComponents(g2d);
			
			player.paintComponents(g2d);
			playerInterface.paintComponents(g2d);
			//paint player interface
			
			
			
			
			if(GameManager.paintBounds){
			//if(false){
				g2d.setColor(Color.red);
				g2d.draw(player.getBoundN());g2d.draw(player.getBoundE());g2d.draw(player.getBoundW());
				g2d.setColor(Color.cyan);
				g2d.draw(player.getBoundDirection());
				g2d.setColor(Color.green);
				g2d.draw(player.getBoundS());
		        g2d.setColor(Color.blue); //Map Bounds
		        g2d.draw(CollisionDetection.BoundN);g2d.draw(CollisionDetection.BoundE);g2d.draw(CollisionDetection.BoundS);g2d.draw(CollisionDetection.BoundW);
		        g2d.setColor(Color.yellow); //Attack Bounds
		        player.setAttackBounds();
		        g2d.draw(player.getAttackBound());
		        g2d.setColor(Color.orange); //Navigation Bounds
		        overWorldMap.setDungeonBounds();
		        if(OverWorldMap.overWorld){
		        	g2d.draw(overWorldMap.getDungeonBounds());
		        }
		        if(DungeonNavigator.dungeon){
		        	g2d.draw(DungeonNavigator.toExit);
		        	g2d.draw(DungeonNavigator.toNorth);g2d.draw(DungeonNavigator.toEast);g2d.draw(DungeonNavigator.toSouth);g2d.draw(DungeonNavigator.toWest);
		        	g2d.draw(DungeonNavigator.toNorth2);g2d.draw(DungeonNavigator.toEast2);g2d.draw(DungeonNavigator.toSouth2);g2d.draw(DungeonNavigator.toWest2);
		        	
		        	for(int yTile = 0; yTile < 7; yTile++){
			        	for(int xTile = 0; xTile < 9; xTile++){
			        		g2d.draw(dungeonNavigator.getWallN(xTile, yTile));g2d.draw(dungeonNavigator.getWallE(xTile, yTile));g2d.draw(dungeonNavigator.getWallS(xTile, yTile));g2d.draw(dungeonNavigator.getWallW(xTile, yTile));
			        		g2d.draw(dungeonNavigator.getWallNE(xTile, yTile));g2d.draw(dungeonNavigator.getWallSE(xTile, yTile));g2d.draw(dungeonNavigator.getWallSW(xTile, yTile));g2d.draw(dungeonNavigator.getWallNW(xTile, yTile));
			        		
			        	}
			        }
		        }

			}
			
		}
		
		
		
		g.dispose();
	}
	
	public void start(){
	
	}
	//Timer loop
	public void actionPerformed (ActionEvent aE){
		start();
		//repaint(Player.x-150, Player.y-200,600,800);
		repaint();
		if (GameManager.repaintNow == true){
			//System.out.println("repaintNow:" +repaintNow);
			GameManager.repaintNow = false;
			repaint();	
		}
		
		/*
		if(ingameThread && GameManager.ingame && !GameManager.menu){
			System.out.println("ingame Threads start:"+ingameThread);
			menuThread = false;
			ingameThread = false;
			
			ingameScheduler.scheduleWithFixedDelay(dungeonBuilderThread, 500, 50,TimeUnit.MILLISECONDS);
			ingameScheduler.scheduleWithFixedDelay(mapThread, 50, 50,TimeUnit.MILLISECONDS);
			ingameScheduler.scheduleWithFixedDelay(playerThread, 400, 10,TimeUnit.MILLISECONDS);
			ingameScheduler.scheduleWithFixedDelay(cameraThread, 300, 5,TimeUnit.MILLISECONDS);
			ingameScheduler.scheduleWithFixedDelay(collisionDetectionThread, 450, 10, TimeUnit.MILLISECONDS);
			ingameScheduler.scheduleWithFixedDelay(dungeonNavigatorThread, 600, 50, TimeUnit.MILLISECONDS);
			ingameScheduler.scheduleWithFixedDelay(playerInterface, 600, 50, TimeUnit.MILLISECONDS);
			
			//shutdown menuThreads
			if(!menuScheduler.isShutdown())
				System.out.println("menu shuts down:"+!menuScheduler.isShutdown());
				//menuScheduler.shutdownNow();
		}
		
		
		if(menuThread && menu){
			System.out.println("menu Threads start:"+menuThread);
			menuThread = false;
			ingameThread = false;
			
			
			if(ingame){
				System.out.println("ingameMenu Threads start");
				menuScheduler.scheduleWithFixedDelay(ingameMenuThread, 10, 10,TimeUnit.MILLISECONDS);
			}
				
			if(!ingame){
				System.out.println("MainMenu Threads start");
				menuScheduler.scheduleWithFixedDelay(mainMenuThread, 10, 10,TimeUnit.MILLISECONDS);
				
			}			
		}
		
		if(!menu && !ingame){
			System.out.println("Game shutdown");
			System.exit(0);
		}
	
	*/
	}
	
	
	private class KAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent kE){
			player.keyReleased(kE);
		}	
		public void keyPressed(KeyEvent kE){
			player.keyPressed(kE);
		}
	}
	
	private class MAdapter extends MouseAdapter{ 
	      public void mouseClicked( MouseEvent e ) { 
	    	  System.out.println("Click");
	      } 
	 }
}
