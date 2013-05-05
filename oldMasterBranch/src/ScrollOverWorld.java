import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.io.*;


public final class ScrollOverWorld implements RectMapGrid{
	
	
	
	//instance variable for MapMaker
	URL mapSetURL;
	BufferedReader mapDataBuff;
	
	static int worldMapX,worldMapY;
	static boolean newMap = false;
	
	//tmp for scrolling
	static int nextMapX = 0;
	static int nextMapY = 0;
	
	
	public ScrollOverWorld() {

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
	

	 
	public ScrollOverWorld scrollData(int x, int y, boolean setMap){

		worldMapX = x;
		worldMapY = y;
		
		try {
		
		if (worldMapX == 0 && worldMapY == 0){
			mapSetURL = this.getClass().getResource(FileLink.mapBasic1);
			mapDataBuff = new BufferedReader(new FileReader(FileLink.mapData00_00));


		}
			
		if (worldMapX == 0 && worldMapY == 1){
			mapSetURL = this.getClass().getResource(FileLink.mapBasic1);
			mapDataBuff = new BufferedReader(new FileReader(FileLink.mapData00_01));

		}
		
		if (worldMapX == 1 && worldMapY == 0){
			mapSetURL = this.getClass().getResource(FileLink.mapBasic1);
			mapDataBuff = new BufferedReader(new FileReader(FileLink.mapData01_00));

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
	
	
	/*
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
	*/

	
	public BufferedReader getMapDataBuff(){return mapDataBuff;}

	
}
