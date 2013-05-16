package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

import characters.Player;



import core.Board;
import core.GameManager;



public class DungeonNavigator extends JComponent implements Runnable{
	
	Player player;
	DungeonBuilder dungeonBuilder;
	DungeonCollision dungeonCollision;
	OverWorldMap overWorldMap;
	
	Graphics2D g2d;
	
	public static int x, y;
	public static int xOld, yOld;
	public static boolean dungeon;
	public static int areaID = 1;
	
	static boolean loadNewMap = true;
	static boolean scrollReady = true;
	
	public static Rectangle toExit;
	public static Rectangle toNorth, toEast, toSouth, toWest;
	public static Rectangle toNorth2, toEast2, toSouth2,toWest2;
	
	
	public DungeonNavigator(Player player, DungeonBuilder dungeonBuilder, DungeonCollision dungeonCollision,OverWorldMap overWorldMap){
		this.player = player;
		this.dungeonBuilder = dungeonBuilder;
		this.dungeonCollision = dungeonCollision;
		this.overWorldMap = overWorldMap;
		
	}
	
	public void paintComponent(Graphics g){
		g2d = (Graphics2D) g;
		dungeonBuilder.paintComponents(g2d);
	}
	
	public void run(){
		try{
			if(GameManager.printMsg)
				System.out.println("DungeonNavigator.run");
			
			if(dungeon)
				navigate();
		} catch (InterruptedException ie) {
			System.err.println("DungeonNavigator.navigate:"+ie);
		}
		
	}
	
	public void navigate() throws InterruptedException {
		
		//System.out.println("DungeonAreaID:"+areaID);
		if(GameManager.printMsg)
			System.out.println("Map x:"+x+", y:"+y);
			
		if(scrollReady){
			xOld = x; 
			yOld = y;
		}
			
		
			//map 0000
			if(x == 0 && y == 0){
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast2)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth)){scrollReady = false;y = 1;DungeonBuilder.scrollY = 630;}
			}
			
			//map 0100
			if(x == 1 && y == 0){
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast)){scrollReady = false;x = 2;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest2)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}	
			}
			
			//map 0200
			if(x == 2 && y == 0){
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth)){	//TO map 0203
					scrollReady = false;
					x = 2; y = 3;player.setX(360);player.setY(400);
				}	
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 1;DungeonBuilder.scrollX = -810;}
			}
			
			//map 0300
			if(x == 3 && y == 0){
				
			}
			
			//map 0400
			if(x == 4 && y == 0){
				
			}
			
			//map 0001
			if(x == 0 && y == 1){
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth)){scrollReady = false;y = 0;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
			}
			
			//map 0101
			if(x == 1 && y == 1){
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast)){scrollReady = false;x = 2;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}			
			}
			
			//map 0201
			if(x == 2 && y == 1){
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 1;DungeonBuilder.scrollX = -810;}
				
			}
			//map 0301
			if(x == 3 && y == 1){
				if(player.getBoundDirection().intersects(DungeonNavigator.toExit)){ //TO OverWorldMap.3
					//Camera.cameraX = ; Camera.cameraY = ;
					//Player.absoluteX = ; Player.absoluteY = ;
					player.setX(20);
					DungeonBuilder.scrollY = 630;
					
					//Camera.cameraLock = false;
					//DungeonNavigator.dungeon = false; OverWorldMap.overWorld = true; 
					//OverWorldMap.areaID = 3;
					System.out.println("leave Dungeon1 -> OverWorldMap 3");
				}
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth2)){scrollReady = false;y = 2;DungeonBuilder.scrollY = 630;}
			}
			
			//map 0401
			if(x == 4 && y == 1){
				
			}

			//map 0002
			if(x == 0 && y == 2){
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toExit)){
					scrollReady = false;
					System.out.println("leave Dungeon1 -> OverWorldMap 2");
					GameManager.win = true;
				}
			}
			
			//map 0102
			if(x == 1 && y == 2){
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth)){scrollReady = false;y = 1;DungeonBuilder.scrollY = -630;}
				//if(player.getBoundDirection.intersects(DungeonNavigator.toEast)){scrollReady = false;x = 2;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth)){scrollReady = false;y = 3;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}
			}
			
			//map 0202
			if(x == 2 && y == 2){
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 1;DungeonBuilder.scrollX = -810;}
			}
			
			//map 0302
			if(x == 3 && y == 2){
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth)){scrollReady = false;y = 1;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth2)){scrollReady = false;y = 1;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth)){scrollReady = false;y = 3;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth2)){scrollReady = false;y = 3;DungeonBuilder.scrollY = 630;}
			}
			
			//map 0402
			if(x == 4 && y == 2){
				
			}
			
			//map 0003
			if(x == 0 && y == 3){
				if(player.getBoundDirection().intersects(DungeonNavigator.toExit)){ //TO OverWorld ID01
					overWorldMap.setCameraX(0); overWorldMap.setCameraY(0);
					player.setAbsoluteX(0);player.setAbsoluteY(0);
					player.setX(110); player.setY(20);
					
					overWorldMap.setLoadNewMap(true);
					overWorldMap.setCameraLock(false);
					OverWorldMap.overWorld = true; DungeonNavigator.dungeon = false;
					DungeonCollision.resetBounds();
					GameManager.changeMapModus = true;
					System.out.println("leave dungeon1 -> OverWorldMap1"); 
				}
				
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
			}
			
			//map 0103
			if(x == 1 && y == 3){
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = -630;}
			}
						
			//map 0203
			if(x == 2 && y == 3){
				if(player.getBoundDirection().intersects(DungeonNavigator.toSouth)){ //TO map 0200
					x = 2; y = 0;
					player.setX(450); player.setY(110);
					}
				
				if(player.getBoundDirection().intersects(DungeonNavigator.toEast)){scrollReady = false;x = 3;DungeonBuilder.scrollX = 810;}
			}
			
			//map 0303
			if(x == 3 && y == 3){
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toNorth2)){scrollReady = false;y = 2;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(DungeonNavigator.toWest)){scrollReady = false;x = 2;DungeonBuilder.scrollX = -810;}
			}
			
			//map 0403
			if(x == 4 && y == 3){
				
			}
		
			//map 0004
			if(x == 0 && y ==4){
				
			}
			
			//map 0104
			if(x == 1 && y == 4){
				
			}
			
			//map 0204
			if(x == 2 && y == 4){
				
			}
			
			//map 0304
			if(x == 3 && y == 4){
				
			}
			
			//map 0404
			if(x == 4 && y == 4){
				
			}

		if(scrollReady)
			setBounds();
		
		
		if(!scrollReady)
			dungeonBuilder.scrollBetweenMaps();
	}
	
	

	
	public static void setBounds(){
		
		//reset Bounds
		toExit = new Rectangle(0,0,0,0);
		toNorth = toEast = toSouth = toWest = new Rectangle(0,0,0,0);
		toNorth2 = toEast2 = toSouth2 = toWest2 = new Rectangle(0,0,0,0);
		
		if(areaID == 1){
			if(x == 0 && y == 0){
				toEast = new Rectangle(809,135,1,100);
				toEast2 = new Rectangle(809,405,1,100);
				toSouth = new Rectangle(40,629,360,1);
			}
			if(x == 1 && y == 0){
				toEast = new Rectangle(809,225,1,100);
				toWest = new Rectangle(0,400,1,100);
				toWest2 = new Rectangle(0,130,1,100);
			}
			if(x == 2 && y == 0){
				toNorth = new Rectangle(365,60,80,10);
				toWest = new Rectangle(0,220,1,100);
			}
			if(x == 3 && y == 0){
				
			}
			if(x == 4 && y == 0){
				
			}
			if(x == 0 && y == 1){
				toNorth = new Rectangle(40,0,390,1);
				toEast = new Rectangle(809,130,1,100);
			}
			if(x == 1 && y == 1){
				toEast = new Rectangle(809,400,1,100);
				toSouth = new Rectangle(580,629,100,1);
				toWest = new Rectangle(0,130,1,100);
			}
			if(x == 2 && y == 1){
				toWest = new Rectangle(0,400,1,100);
			}
			if(x == 3 && y == 1){
				toExit = new Rectangle(490,0,100,1); //TO OverWorld ID02
				toSouth = new Rectangle(490,629,220,1);
				toSouth2 = new Rectangle(40,629,350,1);
			}
			if(x == 4 && y == 1){
				
			}
			if(x == 0 && y == 2){
				toEast = new Rectangle(809,400,1,100);
				toExit= new Rectangle(0,400,1,100); //TO OverWorld ID02
			}
			if(x == 1 && y == 2){
				toNorth = new Rectangle(580,0,100,1);
				toEast = new Rectangle(809,130,1,100);
				toSouth = new Rectangle(580,629,100,1);
				toWest = new Rectangle(0,400,1,100);
			}
			if(x == 2 && y == 2){
				toWest = new Rectangle(0,130,1,100);
			}
			if(x == 3 && y == 2){
				toNorth = new Rectangle(490,0,220,1);
				toSouth = new Rectangle(490,629,220,1);
				toSouth2 = new Rectangle(40,629,350,1);
				toNorth2 = new Rectangle(40,0,350,1);
			}
			if(x == 4 && y == 2){
				
			}
			if(x == 0 && y == 3){
				toExit = new Rectangle(220,629,100,1); //TO OverWorld ID01
				toEast = new Rectangle(809,120,1,100);
			}
			if(x == 1 && y == 3){
				toWest = new Rectangle(0,130,1,100);
				toNorth = new Rectangle(580,0,100,1);
			}
			if(x == 2 && y == 3){
				toEast = new Rectangle(809,220,1,100);
				toSouth = new Rectangle(275,570,80,1);
			}
			if(x == 3 && y == 3){
				toNorth = new Rectangle(490,0,220,1);
				toWest = new Rectangle(0,220,1,100);
				toNorth2 = new Rectangle(40,0,379,1);
			}
			if(x == 4 && y == 3){
				
			}
			if(x == 0 && y == 4){
				
			}
			if(x == 1 && y == 4){
				
			}
			if(x == 2 && y == 4){
				
			}
			if(x == 3 && y == 4){
				
			}
			if(x == 4 && y == 4){
				
			}
		}

	}
	
	
	public Rectangle getWallN(int xTile, int yTile){return DungeonCollision.wallN[xTile][yTile];}
	public Rectangle getWallE(int xTile, int yTile){return DungeonCollision.wallE[xTile][yTile];}
	public Rectangle getWallS(int xTile, int yTile){return DungeonCollision.wallS[xTile][yTile];}
	public Rectangle getWallW(int xTile, int yTile){return DungeonCollision.wallW[xTile][yTile];}
	
	public Rectangle getWallNE(int xTile, int yTile){return DungeonCollision.wallNE[xTile][yTile];}
	public Rectangle getWallSE(int xTile, int yTile){return DungeonCollision.wallSE[xTile][yTile];}
	public Rectangle getWallSW(int xTile, int yTile){return DungeonCollision.wallSW[xTile][yTile];}
	public Rectangle getWallNW(int xTile, int yTile){return DungeonCollision.wallNW[xTile][yTile];}
	
}
