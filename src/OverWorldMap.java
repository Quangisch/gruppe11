import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;


public final class OverWorldMap implements RectMapGrid{
	
	//bounds for worldMapNavigation
	static Rectangle rectNorth, rectEast,rectSouth,rectWest;
	
	//instance variable for MapMaker
	static URL mapSetURL;
	static BufferedReader mapDataBuff;

	
	static int worldMapX,worldMapY;
	static int oldWorldMapX, oldWorldMapY;
	static boolean newMap = false;
	
	//tmp for scrolling
	static int nextMapX = 0;
	static int nextMapY = 0;
	
	
	public OverWorldMap() {
		scrollData(0,0,true);
		rectNorth = new Rectangle(0,0,0,0);
		rectWest = new Rectangle(0,0,0,0);
		rectSouth = new Rectangle(0,0,0,0);
		rectEast = new Rectangle(0,0,0,0);
	}
	
	public OverWorldMap (boolean dummyConstructor){
		//initiated at start by player for collision detection
		//scrollData(0,0,false);
		
	}
	
	
	
	public void setWorldMap(int x, int y) {
		worldMapX = x;
		worldMapY = y;
	}
	
	public int getWorldMap(int x, int y) {
		if ((x != 0 && y != 0) || (x == 0 && y == 0))
			System.err.println("Wrong WorldMap Navigation");
		
		if (x != 0 && y == 0)
			return worldMapX;
		else
			return worldMapY;
	}
	
	//OverWorld MapChanger and Navigator
	 
	public OverWorldMap scrollData(int x, int y, boolean setMap){
		//System.out.println("setMap " +setMap);
		System.out.println("New: " +worldMapX+ ", " +worldMapY);
		System.out.println("Old: " +oldWorldMapX+ ", " +oldWorldMapY);
		if (setMap){
			worldMapX = x;
			worldMapY = y;
		} 
			
		//TODO: write OverWorldMapData into a file
		//if (!newMap)
		//	mapDataBuffOld = mapDataBuff;
			//mapDataBuffOld = new BufferedReader(new FileReader(FileLink.mapData00_00));
		
		
		try {
			
			
		if (worldMapX == 0 && worldMapY == 0){
			mapSetURL = this.getClass().getResource(FileLink.mapBasic1);
			mapDataBuff = new BufferedReader(new FileReader(FileLink.mapData00_00));
			
			resetBounds();
			rectSouth = new Rectangle(320,620, 80,10);
			rectEast = new Rectangle(800,250, 10,55);

		}
			
		if (worldMapX == 0 && worldMapY == 1){
			mapSetURL = this.getClass().getResource(FileLink.mapBasic1);
			mapDataBuff = new BufferedReader(new FileReader(FileLink.mapData00_01));
			
			resetBounds();
			rectNorth = new Rectangle(310,0,90,10);
		}
		
		if (worldMapX == 1 && worldMapY == 0){
			mapSetURL = this.getClass().getResource(FileLink.mapBasic1);
			mapDataBuff = new BufferedReader(new FileReader(FileLink.mapData01_00));
			
			resetBounds();
			rectWest = new Rectangle(0,250, 10,60);
		}
		
		
	
		
		
		} catch (FileNotFoundException fnfe) {
			System.err.println("MapData not found " + fnfe);
		} finally {
			//try{
				//mapData.close();
			//} catch (IOException ioe) {System.err.println("Can't close mapData Stream: " + ioe);}
			
		}
		return this;
	}
	
	
	
	static public void scrollMap(int toX, int toY) {
		try {
			MapMaker.scrollReady = false;
			if (toY == 1){worldMapY += 1;}	
			if (toX == 1){worldMapX += 1;}
			if (toY == -1 && worldMapY != 0){worldMapY -= 1;}
			if (toX == -1 && worldMapX != 0){worldMapX -= 1;}
		//} catch (IOException ie) {
		//	System.err.println(ie);
		} finally {
			
		}
	}
	
	private void resetBounds(){
		Rectangle nullRect = new Rectangle(0,0,0,0);
		rectNorth = nullRect;
		rectEast = nullRect;
		rectSouth = nullRect;
		rectWest = nullRect;
	}
	
	
	public BufferedReader getMapDataBuff(){return mapDataBuff;}
	
	public void hallo(){System.out.println("hallo overworldmap");}
	
}
