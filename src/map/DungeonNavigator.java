package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

import characters.Goomba;
import characters.Player;



import core.Board;
import core.FileLink;
import core.GameManager;



public class DungeonNavigator extends JComponent implements Runnable{
	
	private static Player player;
	private static DungeonBuilder dungeonBuilder;
	private static DungeonCollision dungeonCollision;
	private static OverWorldMap overWorldMap;
	
	Graphics2D g2d;
	
	public int x, y;
	public int xOld, yOld;
	public boolean dungeon;
	public int areaID = 1;
	
	boolean leadNewContent = true;
	boolean loadNewMap = true;
	private boolean scrollReady = true;
	
	static int goombaLife = 2;
	
	Rectangle toExit;
	Rectangle toNorth, toEast, toSouth, toWest;
	Rectangle toNorth2, toEast2, toSouth2,toWest2;
	
	public DungeonNavigator(){
		
	}
	
	public DungeonNavigator(Player player,OverWorldMap overWorldMap, DungeonBuilder dungeonBuilder, DungeonCollision dungeonCollision){
		this.player = player;
		this.overWorldMap = overWorldMap;
		this.dungeonBuilder = dungeonBuilder;
		this.dungeonCollision = dungeonCollision;
	}
	
	public void paintComponent(Graphics g){
		g2d = (Graphics2D) g;
		dungeonBuilder.paintComponents(g2d);
	}
	
	public void run(){
		try{
			if(GameManager.printMsg)
				System.out.println("DungeonNavigator.run");
			
			if(dungeon && scrollReady)
				navigate();
			
			if(dungeon && !scrollReady){
				dungeonBuilder.scrollBetweenMaps();
				loadNewMap = true;
			}
			
			
		} catch (InterruptedException ie) {
			System.err.println("DungeonNavigator.navigate:"+ie);
		}
		
	}
	
	public void readData(){
		dungeonBuilder.readMapData();
		dungeonCollision.readMapTiles();
		System.out.println("DungeonNavigator.readData");
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
				if(player.getBoundDirection().intersects(toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(toEast2)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(toSouth)){scrollReady = false;y = 1;DungeonBuilder.scrollY = 630;}
			}
			
			//map 0100
			if(x == 1 && y == 0){
				if(player.getBoundDirection().intersects(toEast)){scrollReady = false;x = 2;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}
				if(player.getBoundDirection().intersects(toWest2)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}	
			}
			
			//map 0200
			if(x == 2 && y == 0){
				if(player.getBoundDirection().intersects(toNorth)){	//TO map 0203
					scrollReady = false;
					x = 2; y = 3;player.setX(360);player.setY(400);
				}	
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 1;DungeonBuilder.scrollX = -810;}
			}
			
			//map 0300
			if(x == 3 && y == 0){
				
			}
			
			//map 0400
			if(x == 4 && y == 0){
				
			}
			
			//map 0001
			if(x == 0 && y == 1){
				if(player.getBoundDirection().intersects(toNorth)){scrollReady = false;y = 0;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
			}
			
			//map 0101
			if(x == 1 && y == 1){
				if(player.getBoundDirection().intersects(toEast)){scrollReady = false;x = 2;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(toSouth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}			
			}
			
			//map 0201
			if(x == 2 && y == 1){
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 1;DungeonBuilder.scrollX = -810;}
				
			}
			//map 0301
			if(x == 3 && y == 1){
				if(player.getBoundDirection().intersects(toExit)){ //TO OverWorldMap.3
					//Camera.cameraX = ; Camera.cameraY = ;
					//Player.absoluteX = ; Player.absoluteY = ;
					player.setX(20);
					DungeonBuilder.scrollY = 630;
					
					//Camera.cameraLock = false;
					//DungeonNavigator.dungeon = false; OverWorldMap.overWorld = true; 
					//OverWorldMap.areaID = 3;
					System.out.println("leave Dungeon1 -> OverWorldMap 3");
				}
				if(player.getBoundDirection().intersects(toSouth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(toSouth2)){scrollReady = false;y = 2;DungeonBuilder.scrollY = 630;}
			}
			
			//map 0401
			if(x == 4 && y == 1){
				
			}

			//map 0002
			if(x == 0 && y == 2){
				if(player.getBoundDirection().intersects(toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(toExit)){
					scrollReady = false;
					System.out.println("leave Dungeon1 -> OverWorldMap 2");
					GameManager.win = true;
				}
			}
			
			//map 0102
			if(x == 1 && y == 2){
				if(player.getBoundDirection().intersects(toNorth)){scrollReady = false;y = 1;DungeonBuilder.scrollY = -630;}
				//if(player.getBoundDirection.intersects(toEast)){scrollReady = false;x = 2;DungeonBuilder.scrollX = 810;}
				if(player.getBoundDirection().intersects(toSouth)){scrollReady = false;y = 3;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}
			}
			
			//map 0202
			if(x == 2 && y == 2){
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 1;DungeonBuilder.scrollX = -810;}
			}
			
			//map 0302
			if(x == 3 && y == 2){
				if(player.getBoundDirection().intersects(toNorth)){scrollReady = false;y = 1;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(toNorth2)){scrollReady = false;y = 1;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(toSouth)){scrollReady = false;y = 3;DungeonBuilder.scrollY = 630;}
				if(player.getBoundDirection().intersects(toSouth2)){scrollReady = false;y = 3;DungeonBuilder.scrollY = 630;}
			}
			
			//map 0402
			if(x == 4 && y == 2){
				
			}
			
			//map 0003
			if(x == 0 && y == 3){
				if(player.getBoundDirection().intersects(toExit)){ //TO OverWorld ID01
					overWorldMap.setCameraX(0); overWorldMap.setCameraY(0);
					player.setAbsoluteX(0);player.setAbsoluteY(0);
					player.setX(110); player.setY(20);
					
					//overWorldMap.setLoadNewMap(true);
					overWorldMap.setCameraLock(false);
					OverWorldMap.overWorld = true; dungeon = false;
					dungeonCollision.resetBounds();
					//GameManager.changeMapModus = true;
					System.out.println("leave dungeon1 -> OverWorldMap1"); 
					dungeonBuilder.setEnemyLife(0);
					
	
					/*
					OverWorldMap.overWorld = true;
					dungeon = false;
					overWorldMap.setCameraX(0); overWorldMap.setCameraY(0);
					player.setAbsoluteX(0);player.setAbsoluteY(0);
					player.setX(110); player.setY(20);
					*/
					
				}
				
				if(player.getBoundDirection().intersects(toEast)){scrollReady = false;x = 1;DungeonBuilder.scrollX = 810;}
			}
			
			//map 0103
			if(x == 1 && y == 3){
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 0;DungeonBuilder.scrollX = -810;}
				if(player.getBoundDirection().intersects(toNorth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = -630;}
			}
						
			//map 0203
			if(x == 2 && y == 3){
				if(player.getBoundDirection().intersects(toSouth)){ //TO map 0200
					x = 2; y = 0;
					player.setX(450); player.setY(110);
					}
				
				if(player.getBoundDirection().intersects(toEast)){scrollReady = false;x = 3;DungeonBuilder.scrollX = 810;}
			}
			
			//map 0303
			if(x == 3 && y == 3){
				if(player.getBoundDirection().intersects(toNorth)){scrollReady = false;y = 2;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(toNorth2)){scrollReady = false;y = 2;DungeonBuilder.scrollY = -630;}
				if(player.getBoundDirection().intersects(toWest)){scrollReady = false;x = 2;DungeonBuilder.scrollX = -810;}
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

	}
	
	

	
	public void setBounds(){
		
		//reset Bounds
		toExit = new Rectangle(0,0,0,0);
		toNorth = toEast = toSouth = toWest = new Rectangle(0,0,0,0);
		toNorth2 = toEast2 = toSouth2 = toWest2 = new Rectangle(0,0,0,0);
		
		if(areaID == 1){
			if(x == 0 && y == 0){
				toEast = new Rectangle(809,140,1,90);
				toEast2 = new Rectangle(809,410,1,90);
				toSouth = new Rectangle(45,629,350,1);
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
				toEast = new Rectangle(809,400,50,100);
				toExit= new Rectangle(-49,400,50,100); //TO OverWorld ID02
			}
			if(x == 1 && y == 2){
				toNorth = new Rectangle(620,0,1,1);
				toEast = new Rectangle(809,130,1,100);
				toSouth = new Rectangle(580,629,100,50);
				toWest = new Rectangle(-49,400,50,100);
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
				toExit = new Rectangle(220,629,100,50); //TO OverWorld ID01
				toEast = new Rectangle(809,120,50,100);
			}
			if(x == 1 && y == 3){
				toWest = new Rectangle(-49,130,50,100);
				toNorth = new Rectangle(580,-49,100,50);
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
	
	public void spawnEnemy(){
		
		
		if(x == 1 && y == 2){
			System.err.println("spawn Enemy");
			dungeonBuilder.setEnemyLife(goombaLife);
			dungeonBuilder.setEnemyPosition(DungeonBuilder.scrollX+200, DungeonBuilder.scrollY+50);
		} else {
			dungeonBuilder.setEnemyLife(0);
		}
		
	}
	
	public void setEnemyLife(int life){
		dungeonBuilder.setEnemyLife(life);
	}
	public void setEnemyPosition(int x, int y){
		dungeonBuilder.setEnemyPosition(x,y);
	}
	
	public void checkDungeonCollision(){
		dungeonCollision.checkCollisionFloor();
		dungeonCollision.checkCollisionDoor();
	}
	
	public void checkEnemyCollisionWall(){
		dungeonCollision.checkGoombaCollisionWall1();
	}


	//get set
	public int getX(){return x;} public int getY(){return y;}
	public int getOldX(){return xOld;} public int getOldY(){return yOld;}
	public boolean getDungeon(){return dungeon;} public int getAreaID(){return areaID;}
	public boolean getLoadNewMap(){return loadNewMap;}
	public boolean getScrollReady(){return scrollReady;}
	
	
	public void setX(int x){this.x = x;} public void setY(int y){this.y = y;}
	public void setOldX(int xOld){this.xOld = xOld;} public void setOldY(int yOld){this.yOld = yOld;}
	public void setDungeon(boolean dungeon){this.dungeon = dungeon;} public void setAreaID(int areaID){this.areaID = areaID;}
	public void setLoadNewMap(boolean loadNewMap){this.loadNewMap = loadNewMap;}
	public void setScrollReady(boolean scrollReady){this.scrollReady = scrollReady;}
	public static void setGoombaLife(int life){goombaLife = life;}
	
	//get Rectangles for Board
	public Rectangle getToExit(){return toExit;}
	public Rectangle getToNorth(){return toNorth;}public Rectangle getToEast(){return toEast;}public Rectangle getToSouth(){return toSouth;}public Rectangle getToWest(){return toWest;}
	public Rectangle getToNorth2(){return toNorth2;}public Rectangle getToEast2(){return toEast2;}public Rectangle getToSouth2(){return toSouth2;}public Rectangle getToWest2(){return toWest2;}
	
	public Rectangle getObjectN(int layer,int xTile, int yTile){return DungeonCollision.objectN[layer][xTile][yTile];}
	public Rectangle getObjectE(int layer,int xTile, int yTile){return DungeonCollision.objectE[layer][xTile][yTile];}
	public Rectangle getObjectS(int layer,int xTile, int yTile){return DungeonCollision.objectS[layer][xTile][yTile];}
	public Rectangle getObjectW(int layer,int xTile, int yTile){return DungeonCollision.objectW[layer][xTile][yTile];}

}