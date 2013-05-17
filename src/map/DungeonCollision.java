package map;

import java.awt.Rectangle;
import java.util.Random;

import characters.Player;

import core.Board;
import core.GameManager;



public class DungeonCollision implements Runnable {
	
	Player player;
	DynamicMapAnimation dynamicMapAnimation;
	static DungeonNavigator dungeonNavigator;
	
	/*object[layer][xTile][yTile]
	 * layer 0 = Interaction
	 * layer 1 = Objects
	 * layer 2 = Doors
	 * layer 3 = Wall1
	 * layer 4 = Wall2
	 * layer 5 = Floor1
	 * layer 6 = Floor2
	 */
	static Rectangle objectN[][][] = new Rectangle[7][9][7];static Rectangle objectE[][][] = new Rectangle[7][9][7];static Rectangle objectS[][][] = new Rectangle[7][9][7];static Rectangle objectW[][][] = new Rectangle[7][9][7];
	static Rectangle objectNE[][][] = new Rectangle[7][9][7];static Rectangle objectSE[][][] = new Rectangle[7][9][7];static Rectangle objectSW[][][] = new Rectangle[7][9][7];static Rectangle objectNW[][][] = new Rectangle[7][9][7];
	
	public DungeonCollision(Player player,DynamicMapAnimation dynamicMapAnimation){
		this.player = player;
		this.dynamicMapAnimation = dynamicMapAnimation;
	}
	
	public DungeonCollision(DungeonNavigator dungeonNavigator){
		this.dungeonNavigator = dungeonNavigator;
	}
	
	public void run(){
		if(GameManager.printMsg)
			System.out.println("DungeonCollision.start");
			
		if(dungeonNavigator.getDungeon()){
			if(dungeonNavigator.getLoadNewMap());
				readMapTiles();
			
			if(dungeonNavigator.getScrollReady()){
				checkCollisionWall1();
				
			}
			
		}
		
	}
	public void readMapTiles(){
		
		resetBounds();
		
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				
			
				//yWall A
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 1){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(1): 	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(2):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(3):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(4):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(5):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(6):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(7):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(8):	objectN[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);objectE[3][xTile][yTile] = objectN[3][xTile][yTile];break;
					case(9):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10): 	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(11):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(12):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(13):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(14):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(15):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(16):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(17):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(18):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//yWall A
				
				
				//yWall B
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 2){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(1):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(2):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);objectS[3][xTile][yTile] = objectE[3][xTile][yTile];break;
					case(3):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(4):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(5):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;	
					case(6):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(7):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(8):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;	
					case(9):	objectN[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(10):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(12):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(13):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(14):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;	
					case(15):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;	
					case(16):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;	
					case(17):	objectN[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(18):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//yWall B
				
				//yWall C
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 3){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(1):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(2):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;	
					case(3):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;	
					case(4):	objectN[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(5):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(6):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(7):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(8):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(9):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(10):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(12):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(13):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(14):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(15):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(16):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(17):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(18):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//ywall C

				//ywall D
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 4){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(1):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(2):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(3):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(4):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(5):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(6):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(7):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(8):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(9):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(12):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(13):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(14):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(15):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(16):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(17):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(18):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//ywall D
	
				//ywall E
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 5){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(1):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(2):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(3):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(5):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(6):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(7):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(8):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(9):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(12):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(13):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(14):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(15):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(16):	objectN[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(17):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(18):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//ywall E
				
				//yWall F
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 6){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(1):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(2):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(3):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(4):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;	
					case(5):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(6):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(7):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(8):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(9):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10): 	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	objectN[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(12):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(13):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(14):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(15):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(16):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(17):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(18):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}	
				}//ywall F
				
				//yWall G
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 7){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(1):	objectW[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(2):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(3):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(4):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(5):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(6):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(7):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(8):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(9):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					}
				}//yWall G
				//yWall H
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(5):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(6):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;	
					case(7):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					}
				}//yWall H

				//yWall I
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(5):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(6):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(7):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;		
					}
				}//yWall I

				//yWall J
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(5):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(6):	objectE[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(7):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(8):	objectE[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(9):	objectE[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(10):	objectE[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//yWall J

				//yWall K
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 10){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(5):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(6):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(7):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					}
				}//yWall K

				//yWall L
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 11){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(6):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//yWall L

				//yWall M
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 12){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(6):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(8):	objectE[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(9):	objectE[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(10):	objectE[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(11):	objectS[3][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(12):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					}
				}//yWall M

				//yWall N
				if(DungeonBuilder.yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 13){
					switch(DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(6):	objectS[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(11):	objectN[3][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(12):	objectN[3][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					}
				}//yWall N
				
				
				
				//__________Door-Layer 2_______________//
				//yDoor B
				if(DungeonBuilder.yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 2){
					switch(DungeonBuilder.xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(16):	objectN[2][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;	
					case(17):	objectN[2][xTile][yTile] = new Rectangle((xTile*90),yTile*90,45,90);break;
					}
				}
				//yDoor C
				if(DungeonBuilder.yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 3){
					switch(DungeonBuilder.xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(7):	objectE[2][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					}	
				}//yDoor D
				if(DungeonBuilder.yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 4){
					switch(DungeonBuilder.xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(7):	objectE[2][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					}
				}//yDoor H
				if(DungeonBuilder.yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8){
					switch(DungeonBuilder.xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectS[2][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(5):	objectS[2][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(6):	objectS[2][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;	
					case(7):	objectS[2][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					}
				}//yDoor I
				if(DungeonBuilder.yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9){
					switch(DungeonBuilder.xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectN[2][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(5):	objectN[2][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(6):	objectN[2][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(7):	objectN[2][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					}
				}//yDoor J
				if(DungeonBuilder.yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 10){
					switch(DungeonBuilder.xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectE[2][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(5):	objectW[2][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(6):	objectE[2][xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(7):	objectW[2][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					}
				}//yDoor K
				if(DungeonBuilder.yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 11){
					switch(DungeonBuilder.xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(4):	objectE[2][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(5):	objectW[2][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(6):	objectE[2][xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(7):	objectW[2][xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					}
				}//==================================================//
				
				//__________Floor 1 | Layer 5 (Edge)_______________//
				if(DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8){
					switch(DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(11):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(12):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(13):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(14):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					case(15):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(16):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(17):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(18):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					}
				}
				if(DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9){
					switch(DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(11):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(12):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(13):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(14):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					case(15):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(16):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(17):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(18):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					}
				}
				if(DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 10){
					switch(DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(11):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(12):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(13):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(14):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					case(15):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(16):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(17):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(18):	objectS[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					}
				}
				if(DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 11){
					switch(DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(11):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(12):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(13):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(14):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					case(15):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(16):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(17):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);break;	
					case(18):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90+45,90,45);objectN[5][xTile][yTile] = new Rectangle(xTile*90+45,yTile*90,45,90);break;
					}
				}//==================================================//
				//__________Floor 1-2 | Layer 5-6 (Traps)_______________//
				//yFloor1 N
				if(DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 14){
					switch(DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(7):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(9):	objectS[5][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//yFloor2 N
				if(DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 14){
					switch(DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(8):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(10):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//==================================================//
				
				//__________Floor 2 | Layer 6 (Water)_______________//
				//yFloor1 N
				if(DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8){
					switch(DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(8):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(9):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(10):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//yFloor2 N
				if(DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9){
					switch(DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(8):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(9):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(10):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//==================================================//
				//__________Floor 2 | Layer 6 (Lava)_______________//
				//yFloor1 N
				if(DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 11){
					switch(DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(8):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);System.out.println("Lava in here!");break;
					case(9):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);System.out.println("Lava in here!");break;
					case(10):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);System.out.println("Lava in here!");break;
					}
				}//yFloor2 N
				if(DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 12){
					switch(DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]){
					case(8):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);System.out.println("Lava in here!");break;
					case(9):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);System.out.println("Lava in here!");break;
					case(10):	objectS[6][xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);System.out.println("Lava in here!");break;
					}
				}//==================================================//
				
				
				
			}
						
		}
		dungeonNavigator.setLoadNewMap(false);
		
	}
				//System.out.println("check: " +DungeonBuilder.xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]);
	
	public void checkCollisionFloor(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile <9; xTile++){

				
				//Trap,Hole
				if((DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 14
						&&(DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 7 || DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9))
							&&(objectS[5][xTile][yTile]).intersects(player.getBoundS())){
					
					dynamicMapAnimation.startShake(false,-2,2,5,xTile,yTile);
				}
				if((objectS[5][xTile][yTile].contains(player.getBoundS())||objectS[6][xTile][yTile].contains(player.getBoundS()))
						&& ((DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 14 && (DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 7 || DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9))
								  ||(DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 14 && (DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8 || DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 10)))){
					DungeonBuilder.xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] = 18;
					DungeonBuilder.yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] = 14;
					System.err.println("Trap!");
					player.setLoseLifeType(2);
					player.setLoseLife(true);
					sleepNow();
					player.setLoseLife(false);			
				}//Trap,Hole
				
				//LavaPit
					/*
				if((DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 11 || DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 12)
						&& (DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9
						|| DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8
						|| DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 10))
					*/
					if(((DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 11 || DungeonBuilder.yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 12)
							&& (DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 9
							|| DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 8
							|| DungeonBuilder.xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile] == 10)) 
								&&objectS[6][xTile][yTile].contains(player.getBoundS()) && !(objectS[5][xTile][yTile].intersects(player.getBoundS())||objectN[5][xTile][yTile].intersects(player.getBoundS()))){
						System.err.println("Lava!");
						player.setLoseLifeType(1);
						player.setLoseLife(true);
						sleepNow();
						player.setLoseLife(false);
					}
					
				
				
			}
		}
		
				

	}
	
	public void checkCollisionDoor(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile <9; xTile++){
				if(player.getBoundS().intersects(objectN[2][xTile][yTile])){player.setY(player.getY()+10);System.err.println("Door Locked!");}
				if(player.getBoundS().intersects(objectE[2][xTile][yTile])){player.setX(player.getX()-10);System.err.println("Door Locked!");}
				if(player.getBoundS().intersects(objectS[2][xTile][yTile])){player.setY(player.getY()-10);System.err.println("Door Locked!");}
				if(player.getBoundS().intersects(objectW[2][xTile][yTile])){player.setX(player.getX()+10);System.err.println("Door Locked!");}
			}
		}
		
	}
	
	
	public void checkCollisionWall1(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile <9; xTile++){
				
				if(player.getBoundS().intersects(objectN[3][xTile][yTile])){player.setY(player.getY()+10);System.out.println("Wall North!");}
				if(player.getBoundS().intersects(objectE[3][xTile][yTile])){player.setX(player.getX()-10);System.out.println("Wall East!");}
				if(player.getBoundS().intersects(objectS[3][xTile][yTile])){player.setY(player.getY()-10);System.out.println("Wall South!");}
				if(player.getBoundS().intersects(objectW[3][xTile][yTile])){player.setX(player.getX()+10);System.out.println("Wall West!");}
				
				if(player.getBoundS().intersects(objectN[3][xTile][yTile] ) && player.getBoundE().intersects(objectE[3][xTile][yTile])){
					player.setY(player.getY()+10);
					player.setX(player.getX()-10);
					System.out.println("Wall North-east!");
				}
				if(player.getBoundS().intersects(objectN[3][xTile][yTile] ) && player.getBoundW().intersects(objectW[3][xTile][yTile])){
					player.setY(player.getY()+10);
					player.setX(player.getX()+10);
					System.out.println("WallNorth-west!");
				}
				if(player.getBoundS().intersects(objectS[3][xTile][yTile]) && player.getBoundE().intersects(objectE[3][xTile][yTile])){
					player.setY(player.getY()-10);
					player.setX(player.getX()-10);
					System.out.println("Wall South-east!");
				}
				if(player.getBoundS().intersects(objectS[3][xTile][yTile]) && player.getBoundW().intersects(objectW[3][xTile][yTile])){
					player.setY(player.getY()-10);
					player.setX(player.getX()+10);
					System.out.println("Wall South-west!");
				}
				
			}
		}
		
	}
	
	void resetBounds(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				for(int layer = 0; layer < 7; layer++){
					objectN[layer][xTile][yTile] = objectE[layer][xTile][yTile] = objectS[layer][xTile][yTile] = objectW[layer][xTile][yTile] = new Rectangle(0,0,0,0);
					objectNE[layer][xTile][yTile] = objectSE[layer][xTile][yTile] = objectSW[layer][xTile][yTile] = objectNW[layer][xTile][yTile] = new Rectangle(0,0,0,0);
				}
			}
		}
	}
	
	private void sleepNow(){
		try{
			System.out.println("start.Sleep");
			Thread.sleep(1000);
			System.out.println("stop.Sleep");
		} catch (InterruptedException ie){
			System.err.println("DungeonCollision.sleepNow:"+ie);
		}
	}
	
	
}
