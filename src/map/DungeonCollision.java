package map;

import game.objects.Moveable;
import game.objects.Player;

import java.awt.Rectangle;

import core.Board;
import core.GameManager;

abstract class DungeonCollision extends DungeonBuilder{
	
	/*
	 * layer 0 = Floor2
	 * layer 1 = Floor1
	 * layer 2 = Wall1
	 * layer 3 = Wall2
	 * layer 4 = Doors
	 * layer 5 = Objects
	 * layer 6 = Interaction
	 */
	
	
	//mapObjectBound[layer][orientation][xTile][yTile][element]
	Rectangle[][][][][] mapObjectBound = new Rectangle[7][13][9][7][3];
	
	protected DungeonCollision(){
	
	}
	
	public void checkCollision(Moveable object){
	
		checkCollisionFloor(object);
		checkCollisionWall(object);
		
		//System.out.println(getXTileDataElement(getXMap(), getYMap(), 0, 5, 5));
		//&& (getXTileDataElement(xMap, yMap, 0, xTile, yTile) == 14 && (getXTileDataElement(xMap, yMap, 0, xTile, yTile) == 8 || getXTileDataElement(xMap, yMap, 1, xTile, yTile) == 10))){
		
	}
	

	protected void buildCollisionObjects(){
		
		clearObjectBounds();
		int xTileData[][][][][] = getXTileDataArray();
		int yTileData[][][][][] = getYTileDataArray();
		
		int xMapSize = getMapRowX();
		int yMapSize = getMapRowY();
		
		for(int yMapTmp = 0; yMapTmp < xMapSize; yMapTmp++){
		for(int xMapTmp = 0; xMapTmp < yMapSize; xMapTmp++){	
		for(int layer = 0; layer < 7; layer++){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				
				
			
				//Tile yRow A
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 1){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1): 	addCornerBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(2):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(3):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(4):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(5):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(6):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(8):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(9):	addCornerBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(10): 	addCornerBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(18):	addCornerBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					}
				}//Tile yRow A
				
				
				//Tile yRow B
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 2){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(2):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(3):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(4):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(5):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(6):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(8):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(9):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(10):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(11):	addFullBlockRect(xMapTmp,yMapTmp,4,xTile,yTile);break;
					case(12):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(14):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(15):	break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,4,3,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,4,7,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(18):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					}
				}//Tile yRow B
				
				//Tile yRow C
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 3){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(2):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(3):	break;
					case(4):	break;
					case(5):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(6):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,4,3,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(8):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(9):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(10):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(18):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					}
				}//Tile yRow C

				//Tile yRow D
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 4){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(2):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(3):	break;
					case(4):	break;
					case(5):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(6):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,4,3,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(8):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(9):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(10):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(12):	break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(18):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					}
				}//Tile yRow D
	
				//Tile yRow E
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 5){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(2):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(3):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(4):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(5):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(6):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(7):	break;
					case(8):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(9):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(10):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(12):	break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(18):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					}
				}//Tile yRow E
				
				//Tile yRow F
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 6){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(2):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(3):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(4):	break;
					case(5):	break;
					case(6):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(8):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(9):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(10):	addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,3,1,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(18):	addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					}	
				}//Tile yRow F
				
				//Tile yRow G
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 7){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	addCornerBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(2):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(3):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(4):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(5):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(6):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(8):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(9):	addCornerBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(10):	addCornerBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,3,5,xTile,yTile);break;
					case(18):	addCornerBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					}
				}//Tile yRow G
				
				//Tile yRow H
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 8){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	addFullBlockRect(xMapTmp,yMapTmp,4,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,7,xTile,yTile);break;
					case(5):	addFullBlockRect(xMapTmp,yMapTmp,4,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,3,xTile,yTile);break;
					case(6):	addBlockRect(xMapTmp,yMapTmp,4,5,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,4,5,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(8):	break;
					case(9):	break;
					case(10):	break;
					case(11):	addCornerBlockRect(xMapTmp,yMapTmp,1,8,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,1,1,xTile,yTile);break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,1,1,xTile,yTile);break;
					case(14):	addCornerBlockRect(xMapTmp,yMapTmp,1,2,xTile,yTile);break;
					case(15):	addCornerBlockRect(xMapTmp,yMapTmp,1,8,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,1,1,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,1,1,xTile,yTile);break;
					case(18):	addCornerBlockRect(xMapTmp,yMapTmp,1,2,xTile,yTile);break;
					}
				}//Tile yRow H

				//Tile yRow I
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 9){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	break;
					case(5):	break;
					case(6):	break;
					case(7):	break;
					case(8):	break;
					case(9):	break;
					case(10):	break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,1,7,xTile,yTile);break;
					case(12):	addCornerBlockRect(xMapTmp,yMapTmp,1,8,xTile,yTile);break;
					case(13):	addCornerBlockRect(xMapTmp,yMapTmp,1,2,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,1,3,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,1,7,xTile,yTile);break;
					case(16):	addCornerBlockRect(xMapTmp,yMapTmp,1,8,xTile,yTile);break;
					case(17):	addCornerBlockRect(xMapTmp,yMapTmp,1,2,xTile,yTile);break;
					case(18):	addBlockRect(xMapTmp,yMapTmp,1,3,xTile,yTile);break;
					}
				}//Tile yRow I

				//Tile yRow J
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 10){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	addBlockRect(xMapTmp,yMapTmp,4,7,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(5):	break;
					case(6):	break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,4,3,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(8):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(9):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(10):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,1,7,xTile,yTile);break;
					case(12):	addCornerBlockRect(xMapTmp,yMapTmp,1,6,xTile,yTile);break;
					case(13):	addCornerBlockRect(xMapTmp,yMapTmp,1,4,xTile,yTile);break;
					case(14):	addBlockRect(xMapTmp,yMapTmp,1,3,xTile,yTile);break;
					case(15):	addBlockRect(xMapTmp,yMapTmp,1,7,xTile,yTile);break;
					case(16):	addCornerBlockRect(xMapTmp,yMapTmp,1,6,xTile,yTile);break;
					case(17):	addCornerBlockRect(xMapTmp,yMapTmp,1,4,xTile,yTile);break;
					case(18):	addBlockRect(xMapTmp,yMapTmp,1,3,xTile,yTile);break;
					}
				}//Tile yRow J

				//Tile yRow K
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 11){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	addBlockRect(xMapTmp,yMapTmp,4,7,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(5):	break;
					case(6):	break;
					case(7):	addBlockRect(xMapTmp,yMapTmp,4,3,xTile,yTile);addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(8):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(9):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(10):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(11):	addCornerBlockRect(xMapTmp,yMapTmp,1,6,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,1,5,xTile,yTile);break;
					case(13):	addBlockRect(xMapTmp,yMapTmp,1,5,xTile,yTile);break;
					case(14):	addCornerBlockRect(xMapTmp,yMapTmp,1,4,xTile,yTile);break;
					case(15):	addCornerBlockRect(xMapTmp,yMapTmp,1,6,xTile,yTile);break;
					case(16):	addBlockRect(xMapTmp,yMapTmp,1,5,xTile,yTile);break;
					case(17):	addBlockRect(xMapTmp,yMapTmp,1,5,xTile,yTile);break;
					case(18):	addCornerBlockRect(xMapTmp,yMapTmp,1,4,xTile,yTile);break;
					}
				}//Tile yRow K

				//Tile yRow L
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 12){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(5):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(6):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(7):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(8):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(9):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(10):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(11):	break;
					case(12):	break;
					case(13):	break;
					case(14):	break;
					case(15):	break;
					case(16):	break;
					case(17):	break;
					case(18):	break;
					}
				}//Tile yRow L

				//Tile yRow M
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 13){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(5):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(6):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(7):	addFullBlockRect(xMapTmp,yMapTmp,6,xTile,yTile);break;
					case(8):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(9):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(10):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,4,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,3,6,xTile,yTile);break;
					case(13):	break;
					case(14):	break;
					case(15):	break;
					case(16):	break;
					case(17):	break;
					case(18):	break;
					}
				}//Tile yRow M

				//Tile yRow N
				if(yTileData[xMapTmp][yMapTmp][layer][xTile][yTile] == 14){
					switch(xTileData[xMapTmp][yMapTmp][layer][xTile][yTile]){
					case(1):	break;
					case(2):	break;
					case(3):	break;
					case(4):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(5):	break;
					case(6):	addFullBlockRect(xMapTmp,yMapTmp,3,xTile,yTile);break;
					case(7):	addFullBlockRect(xMapTmp,yMapTmp,1,xTile,yTile);break;
					case(8):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(9):	addFullBlockRect(xMapTmp,yMapTmp,1,xTile,yTile);break;
					case(10):	addFullBlockRect(xMapTmp,yMapTmp,0,xTile,yTile);break;
					case(11):	addBlockRect(xMapTmp,yMapTmp,3,2,xTile,yTile);break;
					case(12):	addBlockRect(xMapTmp,yMapTmp,3,8,xTile,yTile);break;
					case(13):	break;
					case(14):	break;
					case(15):	addFullBlockRect(xMapTmp,yMapTmp,5,xTile,yTile);break;
					case(16):	addFullBlockRect(xMapTmp,yMapTmp,5,xTile,yTile);break;
					case(17):	break;
					case(18):	break;
					}
				}//Tile yRow N
				
			
			}			
		}
		}
		}
		}
		//addCornerBlockRect(int xMap, int yMap, int layer, int orientation, int xTile, int yTile){
	
	}
				//System.out.println("check: " +xTileData[getX()][getY()][xTile][yTile]);
	
	protected void getMapObjectData(){
		mapObjectBound = getMapObjectBounds(getXMap(),getYMap());
	}
	
	protected void checkCollisionFloor(Moveable object){
		int xMap = getXMap();
		int yMap = getYMap();
		
		
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				for(int element = 0; element < 3; element++){

					
					if(mapObjectBound[0][0][xTile][yTile][element].contains(object.getBoundCore()) 
							&& (getYTileDataElement(xMap, yMap, 0, xTile, yTile) == 14 && (getXTileDataElement(xMap, yMap, 0, xTile, yTile) == 8 || getXTileDataElement(xMap, yMap, 1, xTile, yTile) == 10))){
				
						System.err.println("Trap!");
						
						object.setObjectBack(100,0,false,null);
						object.setLife(object.getLife()-1);
						
						if(object.isHumanPlayer()){
							object.setX(object.getOldX());
							object.setY(object.getOldY());
							object.setLastDirection(object.getOldLastDirection());
						}
						
						break;
					}//Trap,Hole
					
					
					for(int index = 0; index < GameManager.getMoveableList().size()-1; index ++){
					
						if(mapObjectBound[0][0][xTile][yTile][element].contains(object.getBoundCore()) 
								&& !(mapObjectBound[1][0][xTile][yTile][element].intersects(object.getBoundCore())
								|| mapObjectBound[1][0][xTile][yTile][element].intersects(object.getBoundCore()))){
							System.err.println("checkCollision");
							object.setObjectBack(100,0,false, null);
							
							System.err.println("Lava!");
							object.setLife(object.getLife()-1);
							sleepNow();
							
						}
					}
					
				}
			
				
			}
		}		

	}
	
	protected void checkCollisionDoor(Moveable object){
		
		CheckDoor:
		for(int orientation = 0; orientation < 9; orientation++){
			for(int yTile = 0; yTile < 7; yTile++){
				for(int xTile = 0; xTile < 9; xTile++){
					for(int element = 0; element < 3; element++){
						
						//if(object.getBoundDirection(0).intersects(mapObjectBound[4][orientation][xTile][yTile][element]) && !object.isHumanPlayer())
						//	object.setY(Player.getInstance().getY()+10);
						
						if(Player.getInstance().getBoundCore().intersects(mapObjectBound[4][orientation][xTile][yTile][element])){
							
							boolean useKey = Player.getInstance().useKeyInventory();
							
							if(!useKey){
								Player.getInstance().setY(Player.getInstance().getY()+10);
								System.err.println("Door Locked!");
							} else {
								
								//addObjectBound(int xMapTmp, int yMapTmp, int layer, int orientation, int xTile, int yTile, int dimension, Rectangle objectElement)
								
								writeTileData(getXMap(), getYMap(), 4, xTile, yTile, 18, 14);
								clearBlockRect(getXMap(), getYMap(), 4, xTile, yTile);
								
								for(int e = 0; e < 3; e++){
									for(int o = 0; o < 9; o++){
										mapObjectBound[4][o][xTile][yTile][e] = new Rectangle(0,0,0,0);
									}
								}
									
							
								System.err.println("Door Unlocked!");
								break CheckDoor;
							}
							
						}
					}
					
				}
			}
		}
		
		
	}
	
	
	protected void checkCollisionWall(Moveable object){
	
		
		for(int layer = 2; layer < 4; layer++){
			for(int yTile = 0; yTile < 7; yTile++){
				for(int xTile = 0; xTile < 9; xTile++){
					for(int element = 0; element < 3; element++){
						
				
						if(object.getBoundCore().intersects(mapObjectBound[layer][1][xTile][yTile][element]))
							object.setObjectBack(10,1,true,(mapObjectBound[layer][1][xTile][yTile][element]));
						if(object.getBoundCore().intersects(mapObjectBound[layer][3][xTile][yTile][element]))
							object.setObjectBack(10,3,true,(mapObjectBound[layer][3][xTile][yTile][element]));
						if(object.getBoundCore().intersects(mapObjectBound[layer][5][xTile][yTile][element]))
							object.setObjectBack(10,5,true,(mapObjectBound[layer][5][xTile][yTile][element]));
						if(object.getBoundCore().intersects(mapObjectBound[layer][7][xTile][yTile][element]))
							object.setObjectBack(10,7,true,(mapObjectBound[layer][7][xTile][yTile][element]));
						if(object.getBoundCore().intersects(mapObjectBound[layer][2][xTile][yTile][element]))
							object.setObjectBack(10,0,true,(mapObjectBound[layer][2][xTile][yTile][element]));
						if(object.getBoundCore().intersects(mapObjectBound[layer][4][xTile][yTile][element]))
							object.setObjectBack(10,0,true,(mapObjectBound[layer][4][xTile][yTile][element]));
						if(object.getBoundCore().intersects(mapObjectBound[layer][6][xTile][yTile][element]))
							object.setObjectBack(10,0,true,(mapObjectBound[layer][6][xTile][yTile][element]));
						if(object.getBoundCore().intersects(mapObjectBound[layer][8][xTile][yTile][element]))
							object.setObjectBack(10,0,true,(mapObjectBound[layer][8][xTile][yTile][element]));
							
						
					}
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
	
	private void clearBlockRect(int xMap, int yMap, int layer, int xTile, int yTile){
	
		addObjectBound(xMap,yMap,layer,0,xTile,yTile,0,new Rectangle(xTile*90,yTile*90,90,90));
		
		 
	}
	
	private void addBlockRect(int xMap, int yMap, int layer, int orientation, int xTile, int yTile){
		
		int x = xTile * 90;
		int y = yTile * 90;
		
		if(orientation == 1)
			addObjectBound(xMap,yMap,layer,1,xTile,yTile,0,new Rectangle(x,y,90,45));
		if(orientation == 2)
			addObjectBound(xMap,yMap,layer,2,xTile,yTile,0,new Rectangle(x+45,y,45,45));
		if(orientation == 3)
			addObjectBound(xMap,yMap,layer,3,xTile,yTile,0,new Rectangle(x+45,y,45,90));
		
		
		if(orientation == 4)
			addObjectBound(xMap,yMap,layer,4,xTile,yTile,0,new Rectangle(x+45,y+45,45,45));
		
		
		if(orientation == 5)
			addObjectBound(xMap,yMap,layer,5,xTile,yTile,0,new Rectangle(x,y+45,90,45));
		if(orientation == 6)
			addObjectBound(xMap,yMap,layer,6,xTile,yTile,0,new Rectangle(x,y+45,45,45));
		if(orientation == 7)
			addObjectBound(xMap,yMap,layer,7,xTile,yTile,0,new Rectangle(x,y,45,90));
		if(orientation == 8)
			addObjectBound(xMap,yMap,layer,8,xTile,yTile,0,new Rectangle(x,y,45,45));
		
		if(orientation < 1 || orientation > 8)
			System.err.println("DungeonCollision.Error: Wrong Parameters - Can't add blockRect.");
		
	}
	
	private void addCornerBlockRect(int xMap, int yMap, int layer, int orientation, int xTile, int yTile){
		
		/*
		 * orientation 2: 6 free
		 * orientation 4: 8 free
		 * orientation 6: 2 free
		 * orientation 8: 4 free
		 */
		
		int x = xTile * 90;
		int y = yTile * 90;
		
		if(orientation == 2){
			addObjectBound(xMap,yMap,layer,1,xTile,yTile,0,new Rectangle(x,y,45,45));
			addObjectBound(xMap,yMap,layer,2,xTile,yTile,1,new Rectangle(x+45,y+45,45,45));
			addObjectBound(xMap,yMap,layer,3,xTile,yTile,2,new Rectangle(x+45,y,45,45));
		}
	
		if(orientation == 4){
			addObjectBound(xMap,yMap,layer,3,xTile,yTile,0,new Rectangle(x+45,y,45,45));
			addObjectBound(xMap,yMap,layer,4,xTile,yTile,1,new Rectangle(x,y+45,45,45));
			addObjectBound(xMap,yMap,layer,5,xTile,yTile,2,new Rectangle(x+45,y+45,45,45));
		}
		
		if(orientation == 6){
			addObjectBound(xMap,yMap,layer,4,xTile,yTile,0,new Rectangle(x,y,45,45));
			addObjectBound(xMap,yMap,layer,5,xTile,yTile,1,new Rectangle(x+45,y+45,45,45));
			addObjectBound(xMap,yMap,layer,6,xTile,yTile,2,new Rectangle(x,y+45,45,45));
		}
		
		if(orientation == 8){
			addObjectBound(xMap,yMap,layer,6,xTile,yTile,0,new Rectangle(x+45,y,45,45));
			addObjectBound(xMap,yMap,layer,7,xTile,yTile,1,new Rectangle(x,y+45,45,45));
			addObjectBound(xMap,yMap,layer,8,xTile,yTile,2,new Rectangle(x,y,45,45));
		}
		
		if(orientation != 2 && orientation != 4 && orientation != 6 && orientation != 8)
			System.err.println("DungeonCollision.Error: Wrong Parameters - Can't add blockCornerRect.");

	}
	
	private void addFullBlockRect(int xMap, int yMap, int layer, int xTile, int yTile){
		
		int x = xTile * 90;
		int y = yTile * 90;
		
		addObjectBound(xMap,yMap,layer,0,xTile,yTile,0,new Rectangle(x,y,90,90));
		//System.out.println("FullBlock@"+xMap+"x"+yMap+", Tile:"+xTile+"x"+yTile);
	}
}


