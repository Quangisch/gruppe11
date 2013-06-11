package game.objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import map.Camera;

import core.Board;
import core.FileLink;
import core.GameManager;
import core.GameObjectManager;

public class MapObject extends Moveable implements Runnable, FileLink{

	private static ArrayList<MapObject> mapObjectList = new ArrayList<MapObject>();
	public static int listCounter;
	
	private MapObject mapObject;
	private int IDNumber;
	private int type;
	private int orientation;
	private int xMap, yMap, xPos, yPos;
	
	private Thread runThread;
	ScheduledExecutorService execRun = Executors.newSingleThreadScheduledExecutor();
	
	private MapObject(int IDNumber, int type, int orientation, int xMap, int yMap, int xPos, int yPos){
		mapObject = this;
		this.IDNumber = IDNumber;
		this.xMap = xMap;
		this.yMap = yMap;
		this.xPos = xPos;
		this.yPos = yPos;
		this.orientation = orientation;
		System.err.println("=====> construct MapObject@ID "+IDNumber+", type:"+type+", orientation:"+orientation+", @Pos:"+xPos+"x"+yPos);
		initializeMapObject(type, orientation);
		
	}
	
	public void run(){
		//System.out.println(Player.getInstance().getKeyInventory());
		
		
		int x = xPos + xMap * 810;
		int y = yPos + yMap * 630;
		
		switch(orientation){
		case(1):	break;
		case(2):	x -= 95; break;
		case(3):	y -= 89; break;
		case(4):	x += 8; break;
		}
		
		setX((x-Camera.getInstance().getX()));
		setY((y-Camera.getInstance().getY()));
		
		if(getBound().intersects(Player.getInstance().getBoundCore())){
			if(Player.getInstance().useKeyInventory())
				Player.getInstance().setObjectBack(10, 0, false, null);
			else{
				GameObjectManager.openDoor(IDNumber);
				stop();
			}
			
		}
			
		
	}
	
	private void stop(){
		setAlive(false);
		setVisible(false);
		execRun.shutdown();
		mapObject = null;
	}
	
	private void initializeMapObject(int type, int orientation){
		int[] data = new int[7];
		data = MapObjectList.getObjectData(type, orientation);
		/*
			data[0] = type;
			data[1] = orientation;
			data[2] = xPosition;
			data[3] = yPosition;
			data[4] = width;
			data[5] = height;
			data[6] = cycle;
		*/
		
	
		System.err.println("==construct=>CameraPos@"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
		
		
		/*
		 DOORNORTH(0,1,4,0,2,1,0),
		DOOREAST  (0,2,2,0,1,2,0),
		DOORSOUTH (0,3,0,1,2,1,0),
		DOORWEST  (0,4,3,0,1,2,0);
		 *//*
		setStaticX(0*90);
		setStaticY(1*90);
		setSubSpriteWidth(2*90);
		setSubSpriteHeight(1*90);
		//setWidth(200);
		//setHeight(200);
		*/
		
		//subSpriteBuff = spriteBuff.getSubimage(staticStep*subSpriteWidth+staticX, staticY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
		
			setStaticX(data[2]*90);
			setStaticY(data[3]*90);
			setSubSpriteWidth(data[4]*90);
			setSubSpriteHeight(data[5]*90);
		
	
		
		setStaticCycle(0);
		setFile(mapObjects00);
		loadSprite();
		
		
		setVisible(true);
		setMoveable(false);
		setAlive(true);
		
		Board.getInstance().addDrawable(this);
		setStaticSubSprite(1);
		runThread = new Thread(this);
		execRun.scheduleWithFixedDelay(runThread, 10, 10, TimeUnit.MILLISECONDS);
		

		
	}
	
	private int getIDNumber(){return IDNumber;}
	
	public static void deleteAllInstances(){
		
		
		for(int index = 0; index < mapObjectList.size(); index++){
			mapObjectList.get(index).stop();
		}
		mapObjectList.clear();
	}
	
	public static void deleteInstance(int IDNumber){
		
		for(int index = 0; index < mapObjectList.size(); index ++){
			
			if(mapObjectList.get(index).getIDNumber() == IDNumber){
				mapObjectList.remove(index);
				break;
			}
				
		}
	}
	
	public static void addInstance(int IDNumber, int type, int orientation, int xMap, int yMap, int xPosition, int yPosition){
	
		mapObjectList.add(new MapObject(IDNumber, type, orientation, xMap, yMap, xPosition, yPosition));
		listCounter++;

	}
	
	public static ArrayList<MapObject> getMapObjectList(){
		return mapObjectList;
	}
	
	
	private enum MapObjectList{
		
		DOORNORTH(0,1,4,0,2,1,0),
		DOOREAST(0,2,2,0,1,2,0),
		DOORSOUTH(0,3,0,1,2,1,0),
		DOORWEST(0,4,3,0,1,2,0),
		
		BROKENFLOOR1(1,0,0,2,1,1,0),
		BROKENFLOOR2(1,1,1,2,1,1,0),
		
		TREASUREC(2,0,4,2,1,1,0),
		TREASUREO(2,1,5,2,1,1,0),
		
		BLOCKSTONE(3,0,7,0,2,2,0),
		
		WATER1(10,0,0,3,1,1,3),
		WATER2(10,1,0,4,1,1,3),
		
		LAVA1(11,0,3,3,1,1,3),
		LAVA2(11,1,3,4,1,1,3),
		
		GRASS1(12,0,0,5,1,1,3),
		GRASS2(12,1,0,6,1,1,3),
		GRASS3(12,2,3,5,1,1,3),
		GRASS4(12,3,3,6,1,1,3);
		
		private final int type;
		private final int orientation;
		private final int[] data = new int[7];
		
		private MapObjectList(int type, int orientation, int xPosition, int yPosition, int width, int height, int cycle){

			this.type = type;
			this.orientation = orientation;
			data[0] = type;
			data[1] = orientation;
			data[2] = xPosition;
			data[3] = yPosition;
			data[4] = width;
			data[5] = height;
			data[6] = cycle;
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
