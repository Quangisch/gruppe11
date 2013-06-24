package map;


import java.awt.Rectangle;
import java.util.ArrayList;

import objects.MapObject;
import objects.MarioDark;
import objects.Player;


import core.GameManager;



public class DungeonNavigator extends DungeonCollision{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2164407733810407949L;

	private static DungeonNavigator dungeonNavigator;
	private volatile int areaID = 1;
	ArrayList<NavigationData<int[], Rectangle, int[]>> navDataMap;
	

	private DungeonNavigator(){
		System.err.println("construct DungeonNavigator");
		setDungeon(true);

	}
	
	public void initializeMap(int xMap, int yMap, int ID, int playerX, int playerY){
		
		GameManager.getInstance().mapLoaded = false;
		
		DungeonNavigator.getInstance().resetObjectManager();
		OverWorldNavigator.getInstance().resetObjectManager();
		MarioDark.deleteAllInstances();
		MapObject.deleteAllInstances();
		OverWorldNavigator.resetInstance();
		
		setID(ID);
		setXMap(xMap);
		setYMap(yMap);
		Player.getInstance().setX(playerX);
		Player.getInstance().setY(playerY);
		Camera.getInstance().setX(-getXCoordinate());
		Camera.getInstance().setY(-getYCoordinate());
		
		switch(ID){
		case(0)	:	loadMapData(dungeonDataID00, tiles_Dungeon00);
					break;
		}

		System.err.println("=>ini@Map:"+getXMap()+"x"+getYMap());
		System.out.println("Player@"+Player.getInstance().getX()+"x"+Player.getInstance().getY());
		System.out.println("Camera@"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
		System.out.println("Map...@"+DungeonNavigator.getInstance().getXCoordinate()+"x"+DungeonNavigator.getInstance().getYCoordinate());
		
		
		buildCollisionObjects();
		reloadMap();
		Player.getInstance().setOldPosition();

		GameManager.getInstance().mapLoaded = true;
	}
	
	
	public void reloadMap(){

		setXCoordinate(-810*getXMap());
		setYCoordinate(-630*getYMap());
		
		getMapObjectData();

		navDataMap = getNavigationDataMap(getXMap(), getYMap());

	}
	
	
	
	public synchronized void navigate() {
		
		
		Camera.getInstance().setX(-getXCoordinate());
		Camera.getInstance().setY(-getYCoordinate());
		
		
		if(GameManager.getInstance().scrollDirection == 0){
			setXCoordinate(-810*getXMap());
			setYCoordinate(-630*getYMap());
			//System.out.println("AbsolutMapCoordinates_"+getXCoordinate()+"x"+getYCoordinate());
		}
		
		//System.out.println("AbsolutMapCoordinates_"+getXCoordinate()+"x"+getYCoordinate());
		
		if(GameManager.getInstance().scrollDirection != 0){
			navDataMap = getNavigationDataMap(getXMap(), getYMap());
		}
		
		//System.out.println("Exit @Map_ "+getXMap()+"x"+getYMap()+" to "+mapTypeData+"@ID"+mapID+" to: Map_"+xMap+"x"+yMap+" and Player@"+xPlayer+"x"+yPlayer);
		
		if(GameManager.getInstance().scrollLock)
			System.out.println(GameManager.getInstance().scrollLock);
		
		if(GameManager.getInstance().scrollDirection == 0){
			for(int index = 0; index < navDataMap.size(); index++){
				
				int[] coordinates = navDataMap.get(index).getCoordinates();
				
				if(coordinates[2] == 0){
					
					if(Player.getInstance().getBoundDirection(1).intersects(navDataMap.get(index).getRect())){
						int[] data = navDataMap.get(index).getData();
						
						int mapType = data[0];
						int mapID = data[1];
						int xMap = data[2];
						int yMap = data[3];
						int xPlayer = data[4];
						int yPlayer = data[5];
						
						
						if(mapType == 0){
							GameManager.getInstance().dungeon = true;
							GameManager.getInstance().overWorld = false;
							GameManager.getInstance().mapLoaded = false;
							
							//if(mapID != getMapID())
								this.initializeMap(xMap, yMap, mapID, xPlayer, yPlayer);
							//else{
								setXMap(xMap);
								setYMap(yMap);
								Player.getInstance().setX(xPlayer);
								Player.getInstance().setY(yPlayer);
								
							//}
							System.out.println("dungeonMap_");
						}
							
						if(mapType == 1){
							GameManager.getInstance().dungeon = false;
							GameManager.getInstance().overWorld = true;
							GameManager.getInstance().mapLoaded = false;
							OverWorldNavigator.getInstance().initializeMap(false, mapID, xMap, yMap, xPlayer, yPlayer);
							//OverWorldNavigator.getInstance().initializeMap(xMap-810, yMap-630, mapID, 400, 300);
							System.out.println("overworldMap_"+xMap+"x"+yMap);
						}
						
					}	
				}//if coordinates[2] == 0
				
				if(coordinates[2] == 1){
					if(Player.getInstance().getBoundDirection(1).intersects(navDataMap.get(index).getRect())){
						Player.getInstance().setMovementLock();
						GameManager.getInstance().scrollDirection = 1;
						setYMap(getYMap()-1);
						System.err.println("Y_"+getYMap()+" to: "+(getYMap()+1));
						getMapObjectData();
						setEnemy();
						break;
					}
				}
				
				if(coordinates[2] == 2){
					if(Player.getInstance().getBoundDirection(1).intersects(navDataMap.get(index).getRect())){
						Player.getInstance().setMovementLock();
						GameManager.getInstance().scrollDirection = 3;
						setXMap(getXMap()+1);
						System.err.println("X_"+getXMap()+" to: " +(getXMap()+1));
						getMapObjectData();
						setEnemy();
						break;
					}
					
				}
				
				if(coordinates[2] == 3){
					if(Player.getInstance().getBoundDirection(1).intersects(navDataMap.get(index).getRect())){
						Player.getInstance().setMovementLock();
						GameManager.getInstance().scrollDirection = 5;
						setYMap(getYMap()+1);
						System.err.println("Y_"+getYMap()+" to: "+(getYMap()+1));
						getMapObjectData();
						setEnemy();
						break;
					}
					
				}
				
				if(coordinates[2] == 4){
					if(Player.getInstance().getBoundDirection(1).intersects(navDataMap.get(index).getRect())){
						Player.getInstance().setMovementLock();
						GameManager.getInstance().scrollDirection = 7;
						setXMap(getXMap()-1);
						System.err.println("X_"+getXMap()+" to: "+(getXMap()-1));
						getMapObjectData();
						setEnemy();
						System.out.println("yoyo Go West");
						break;
					}
					
				}
				
			}//for index < size
			
		}
		
		

		Camera.getInstance().scrollCamera(this);
		
	}

	
	public int getAreaID(){return areaID;}
	//
	public void setAreaID(int areaID){this.areaID = areaID;}
	
	public static void resetInstance(){
		if(dungeonNavigator != null)
			dungeonNavigator = new DungeonNavigator();
	}
	
	public static DungeonNavigator getInstance(){
		if(dungeonNavigator == null){
			dungeonNavigator = new DungeonNavigator();
		}
			
		return dungeonNavigator;
	}

}
