package map;

import java.awt.Rectangle;

import characters.Player;

import core.Board;
import core.GameManager;



public class DungeonCollision implements Runnable {
	
	Player player;

	static Rectangle wallN[][] = new Rectangle[9][7];static Rectangle wallE[][] = new Rectangle[9][7];static Rectangle wallS[][] = new Rectangle[9][7];static Rectangle wallW[][] = new Rectangle[9][7];
	static Rectangle wallNE[][] = new Rectangle[9][7];static Rectangle wallSE[][] = new Rectangle[9][7];static Rectangle wallSW[][] = new Rectangle[9][7];static Rectangle wallNW[][] = new Rectangle[9][7];
	
	public DungeonCollision(Player player){
		this.player = player;
	}
	
	public void run(){
		//if(GameManager.printMsg)
			System.out.println("DungeonCollision.start");
			
		if(DungeonNavigator.dungeon){
			if(DungeonNavigator.loadNewMap);
				readWalls();
			checkCollision();
		}
		
	}
	public static void readWalls(){
		
		resetBounds();
		
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				
				//yWall A
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 1){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1): 	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(2):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(3):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(4):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(5):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(7):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(8):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);wallE[xTile][yTile] = wallN[xTile][yTile];break;
					case(9):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10): 	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(11):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(12):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(13):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(14):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(15):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(16):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(17):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(18):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//yWall A
				
				
				//yWall B
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 2){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);wallS[xTile][yTile] = wallE[xTile][yTile];break;
					case(3):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(5):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;	
					case(6):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(7):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(8):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;	
					case(9):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(10):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(12):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(13):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(14):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;	
					case(15):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;	
					case(16):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;	
					case(17):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(18):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//yWall B
				
				//yWall C
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 3){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;	
					case(3):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;	
					case(4):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(5):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(7):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(8):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(9):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(10):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(12):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(13):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(14):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(15):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(16):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(17):	wallW[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(18):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
						
				}//ywall C
				
				//ywall D
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 4){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(3):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(4):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(5):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(7):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(8):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(12):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(13):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(14):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(15):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(16):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(17):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(18):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//ywall D
	
				//ywall E
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 5){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(2):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(3):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(5):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(7):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(8):	wallN[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(12):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(13):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(14):	wallW[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(15):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(16):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(17):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(18):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
				}//ywall E
				
				//yWall F
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 6){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(3):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(4):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;	
					case(5):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(7):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(8):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					case(10): 	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);break;
					case(11):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(12):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(13):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(14):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(15):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(16):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(17):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);break;
					case(18):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);break;
					}
						
				}//ywall F
				
				//yWall G
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 7){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(2):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(3):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(4):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(5):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(7):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(8):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;	
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					}
				}//yWall G
				//yWall H
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 8){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(2):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(3):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);break;
					case(5):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;	
					case(7):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					}
				}//yWall H

				//yWall I
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 8){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(2):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(3):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(5):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(7):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;		
					}
				}//yWall I

				//yWall J
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 9){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(2):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(3):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;
					case(5):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;	
					case(6):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);break;	
					case(7):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);break;
					case(8):	wallE[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(9):	wallE[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(10):	wallE[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//yWall J

				//yWall K
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 10){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(2):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(3):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;	
					case(5):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					case(6):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(7):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					}
				}//yWall K

				//yWall L
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 11){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(2):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(3):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;	
					case(5):	break;
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					}
				}//yWall L

				//yWall M
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 12){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(2):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(3):	//wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;	
					case(5):	break;
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(7):	break;
					case(8):	wallE[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(9):	wallE[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(10):	wallE[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);break;
					case(11):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);break;
					case(12):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);break;
					}
				}//yWall M

				//yWall N
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 13){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);
								break;	
					case(5):	break;
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,90);
								break;
					case(7):	break;
					case(8):	break;
					case(9):	break;
					case(10):	break;
					case(11):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallE[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(12):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wallW[xTile][yTile] = wallN[xTile][yTile];
								break;
					}
				}//yWall N

			}
						
		}
		
	}
				//System.out.println("check: " +DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]);
				
	
	public void checkCollision(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile <9; xTile++){
				
				if(player.getBoundS().intersects(wallN[xTile][yTile])){
					player.setY(player.getY()+10);
					System.out.println("Wall North!");
				}
				if(player.getBoundS().intersects(wallE[xTile][yTile])){
					player.setX(player.getX()-10);
					System.out.println("Wall East!");
				}
				if(player.getBoundS().intersects(wallS[xTile][yTile])){
					player.setY(player.getY()-10);
					System.out.println("Wall South!");
				}
				if(player.getBoundS().intersects(wallW[xTile][yTile])){
					player.setX(player.getX()+10);
					System.out.println("Wall West!");
				}
				
				if(player.getBoundS().intersects(wallN[xTile][yTile] ) && player.getBoundE().intersects(wallE[xTile][yTile])){
					player.setY(player.getY()+10);
					player.setX(player.getX()-10);
					System.out.println("Wall North-east!");
				}
				if(player.getBoundS().intersects(wallN[xTile][yTile] ) && player.getBoundW().intersects(wallW[xTile][yTile])){
					player.setY(player.getY()+10);
					player.setX(player.getX()+10);
					System.out.println("WallNorth-west!");
				}
				if(player.getBoundS().intersects(wallS[xTile][yTile]) && player.getBoundE().intersects(wallE[xTile][yTile])){
					player.setY(player.getY()-10);
					player.setX(player.getX()-10);
					System.out.println("Wall South-east!");
				}
				if(player.getBoundS().intersects(wallS[xTile][yTile]) && player.getBoundW().intersects(wallW[xTile][yTile])){
					player.setY(player.getY()-10);
					player.setX(player.getX()+10);
					System.out.println("Wall South-west!");
				}
				
			}
		}
		
	}
	
	public static void resetBounds(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				wallN[xTile][yTile] = wallE[xTile][yTile] = wallS[xTile][yTile] = wallW[xTile][yTile] = new Rectangle(0,0,0,0);
				wallNE[xTile][yTile] = wallSE[xTile][yTile] = wallSW[xTile][yTile] = wallNW[xTile][yTile] = new Rectangle(0,0,0,0);
			}
		}
	}
	
}
