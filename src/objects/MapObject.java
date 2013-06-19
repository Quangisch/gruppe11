package objects;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import map.Camera;
import map.DungeonNavigator;

import core.Board;
import core.FileLink;
import core.GameManager;
import core.GameObjectManager;

public class MapObject extends Moveable implements Runnable, FileLink{

	private static ArrayList<MapObject> mapObjectList = new ArrayList<MapObject>();
	public static int listCounter;
	
	private int ID;
	private int type;
	private int orientation;
	private int xMap, yMap, xPos, yPos;
	private boolean interact;
	private int x = 0;
	private int y = 0;
	
	private Thread runThread;
	ScheduledExecutorService execRun = Executors.newSingleThreadScheduledExecutor();
	
	private MapObject(int ID, int type, int orientation, int xMap, int yMap, int xPos, int yPos){
		setMoveableType(-10);
		this.ID = ID;
		this.type = type;
		this.xMap = xMap;
		this.yMap = yMap;
		this.xPos = xPos;
		this.yPos = yPos;
		this.orientation = orientation;
		System.err.println("=====> construct MapObject@ID "+ID+", type:"+type+", orientation:"+orientation+", @Pos:"+xPos+"x"+yPos);
		initializeMapObject(type, orientation);
		
	}
	
	public void run(){
		//System.out.println(Player.getInstance().getKeyInventory());
		
		setX((x-Camera.getInstance().getX()));
		setY((y-Camera.getInstance().getY()));
		
		switch(type){
		case(0): handleDoor(); break;
		case(1): handleTrap(); break;
		case(2): handleTreasure(); break;
		case(3): handleBlockBoss(); break;
		case(10): handleAnimationTile(); break;
		case(11): handleAnimationTile(); break;
		case(12): handleAnimationTile(); break;
		
		}
		//System.out.println("Type@"+type);
		
		if(GameManager.getInstance().switchGameState)
			stop();

	}
	
	private void handleDoor(){
		
		x = xPos + xMap * 810;
		y = yPos + yMap * 630;
		
		switch(orientation){
		case(1):	break;
		case(2):	x -= 95; break;
		case(3):	y -= 89; break;
		case(4):	x += 8; break;
		}
		
		if(getBoundCore().intersects(Player.getInstance().getBoundCore())){
			if(Player.getInstance().useKeyInventory())
				Player.getInstance().setObjectBack(10, 0, false, null);
			else{
				GameObjectManager.getInstance().openDoor(ID);
				stop();
			}
			
		}
	}
	
	private void handleTrap(){
		
		int shake = new Random().nextInt(2 - -2 + 1) + -2;
		

		
	
		
		if(getBoundCore().intersects(Player.getInstance().getBoundCore())){
			x = xPos + xMap * 810 + shake;
			y = yPos + yMap * 630 + shake;
			
			
			if(x > xPos + xMap * 810 + 10)
				x = xPos + xMap * 810;
					
			if(y > yPos + yMap * 630 + 10)
				y = yPos + yMap * 630;
		} else {
			x = xPos + xMap * 810;
			y = yPos + yMap * 630;
		}
		
		if(getBoundCore().contains(Player.getInstance().getBoundCore())){

			stop();
		}
		
	}
	
	private void handleBlockBoss(){
		
		x = xPos + xMap * 810;
		y = yPos + yMap * 630;
		
		if(this.getBoundCore().intersects(Player.getInstance().getBoundCore())&&GameManager.getInstance().scrollDirection == 0){
			switch(orientation){
			case 0: Player.getInstance().setObjectBack(5, 0, false, null);break;
			case 1: Player.getInstance().setObjectBack(5, 5, false, null);break;
			case 2: Player.getInstance().setObjectBack(5, 7, false, null);break;
			case 3: Player.getInstance().setObjectBack(5, 1, false, null);break;
			case 4: Player.getInstance().setObjectBack(5, 3, false, null);break;
			}
		}
			
		
		if(DungeonNavigator.getInstance().getXMap() == 2 && DungeonNavigator.getInstance().getYMap() == 1 && MarioDark.getInstanceCounter() == 0){
			stop();
		}
		if(DungeonNavigator.getInstance().getXMap() == 1 && DungeonNavigator.getInstance().getYMap() == 2 && GameManager.getInstance().scrollDirection == 0 && GameObjectManager.getInstance().getBossStatusDefeated(22))
			stop();
		if(DungeonNavigator.getInstance().getXMap() == 1 && DungeonNavigator.getInstance().getYMap() == 0 && GameManager.getInstance().scrollDirection == 0 && GameObjectManager.getInstance().getBossStatusDefeated(21))
			stop();

	}
	
	private void handleTreasure(){
		x = xPos + xMap * 810;
		y = yPos + yMap * 630;
		
		if((new Rectangle(getX()-405,getY()-315,810,630)).intersects(Player.getInstance().getBound())){
			//System.out.println("Pos@"+getX()+"x"+getY());
			if(this.getBoundCore().intersects(Player.getInstance().getBoundCore()))
				Player.getInstance().setObjectBack(10, 0, false, null);
			
			if(GameObjectManager.getInstance().openTreasureBox(false, this) && !interact){
				
				setStaticX(450);
				setStaticSubSprite(1,0);
				interact = true;
					
			} else if(getBoundCore().intersects(Player.getInstance().getBound()) && GameManager.getInstance().interact){
				
				GameObjectManager.getInstance().openTreasureBox(true, this);
				//System.out.println("Treasure:open@"+GameObjectManager.openTreasureBox(false, this));
				
			}

		}

	}
	
	private void handleAnimationTile(){
		x = xPos + xMap * 810;
		y = yPos + yMap * 630;
		setStaticSubSprite(1,0.01);
		
		setYRefPoint(getY()-48);
	
	}
	
	private void stop(){
		setVisible(false);
		setAlive(false);
		Board.updateMapObjectList();
		execRun.shutdown();
	}
	
	private void initializeMapObject(int type, int orientation){
		int[] data = new int[11];
		data = MapObjectList.getObjectData(type, orientation);

	
		System.err.println("==construct=>CameraPos@"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
		
		
		setStaticX(data[2]);
		setStaticY(data[3]);
		setSubSpriteWidth(data[4]);
		setSubSpriteHeight(data[5]);
		
		setCoreX(data[6]);
		setCoreY(data[7]);
		setCoreWidth(data[8]);
		setCoreHeight(data[9]);
		
		setStaticCycle(data[10]);
		setFile(mapObjects00);
		loadSprite();
		
		
		setVisible(true);
		setMoveable(false);
		setAlive(true);
		
		if(type == 1)
			Board.getInstance().addMapObject(this);
		else
			GameManager.addGameObject(this);
		setStaticSubSprite(1,0);
		runThread = new Thread(this);
		execRun.scheduleWithFixedDelay(runThread, 10, 10, TimeUnit.MILLISECONDS);
		

		
	}
	
	public Integer[] getPositionID(){
		Integer[] positionID = {xMap, yMap, xPos,yPos};
		return positionID;
	}
	private int getID(){return ID;}
	
	
	public static void deleteInstance(int ID){
		
		for(int index = 0; index < mapObjectList.size(); index ++){
			
			if(mapObjectList.get(index).getID() == ID){
				mapObjectList.remove(index);
				break;
			}
				
		}
	}
	
	public static void addInstance(int ID, int type, int orientation, int xMap, int yMap, int xPosition, int yPosition){
	
		mapObjectList.add(new MapObject(ID, type, orientation, xMap, yMap, xPosition, yPosition));
		listCounter++;
	}
	
	public static void deleteAllInstances(){
		resetInstance();
	}
	
	public static void resetInstance(){
		for(int index = 0; index < mapObjectList.size(); index++){
			mapObjectList.get(index).stop();
		}
		mapObjectList.clear();
		listCounter = 0;
	}
	
	public static ArrayList<MapObject> getMapObjectList(){
		return mapObjectList;
	}
	
	
	private enum MapObjectList{
		
		DOORNORTH(0,1,360,0,180,90,0,0,150,80,0),
		DOOREAST(0,2,180,0,90,180,30,0,90,180,0),
		DOORSOUTH(0,3,0,90,180,90,0,30,150,90,0),
		DOORWEST(0,4,270,0,90,160,-60,0,90,160,0),
		
		BROKENFLOOR1(1,0,0,180,90,90,-20,-10,100,100,0),
		BROKENFLOOR2(1,1,90,180,90,90,0,0,90,90,0),
		
		TREASURE(2,0,360,180,90,90,-15,20,75,55,0),
		
		BLOCKSTONEFULL(3,0,630,0,180,180,0,0,180,180,0),
		BLOCKSTONEN(3,1,630,0,180,90,0,0,180,90,0),
		BLOCKSTONEE(3,2,720,0,90,180,0,0,50,170,0),
		BLOCKSTONES(3,3,630,90,180,90,0,10,150,80,0),
		BLOCKSTONEW(3,4,630,0,90,180,0,0,90,180,0),
		
		WATER1(10,0,0,270,90,90,0,0,90,90,3),
		WATER2(10,1,0,360,90,90,0,0,90,90,3),
		
		LAVA1(11,0,270,270,90,90,0,0,90,90,3),
		LAVA2(11,1,270,360,90,90,0,0,90,90,3),
		
		GRASS1(12,0,0,450,90,90,0,0,90,90,3),
		GRASS2(12,1,0,540,90,90,0,0,90,90,3),
		GRASS3(12,2,270,450,90,90,0,0,90,90,3),
		GRASS4(12,3,270,540,90,90,0,0,90,90,3);
		
		private final int type;
		private final int orientation;
		private final int[] data = new int[11];
		
		private MapObjectList(int type, int orientation, int xPosition, int yPosition, int width, int height, int coreX, int coreY, int coreWidth, int coreHeight, int cycle){

			this.type = type;
			this.orientation = orientation;
			data[0] = type;
			data[1] = orientation;
			
			data[2] = xPosition;
			data[3] = yPosition;
			data[4] = width;
			data[5] = height;
			
			data[6] = coreX;
			data[7] = coreY;
			data[8] = coreWidth;
			data[9] = coreHeight;
			
			data[10] = cycle;
		}
		
	
		private static int[] getObjectData(int type, int orientation){
			
			int[] objectData = null;
			
			for(MapObjectList object : values()){
				
				if(object.type == type && object.orientation == orientation){
					objectData = object.data;
					break;
				}		
			}
			
			return objectData;
		}
		
	}
	
}
