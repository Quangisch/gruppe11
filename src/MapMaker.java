

import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import java.net.URL;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class MapMaker extends JComponent implements Runnable, GridInterface{
	
	//class instances
	OverWorldMap overWorldMap;
	static BufferedImage mapTiles, mapBuff;
	BoardGrid mapGrid;
	ScrollMapPainter scrollMapPainter;
	static Player player;
	
	static BufferedImage mapTilesOld, mapBuffOld;
	BoardGrid mapGridOld;
	
	Graphics2D g2d;
	
	//read and paint map
	static int mapGridX, mapGridY;
	static URL mapSetURL = null;
	
	//worldMapNavigation
	static int scrollX = 0, scrollY = 0;
	static boolean scrollReady = true;
	final static int SCROLLSPEED_X = 60,  SCROLLSPEED_Y = 50;
	

	
	public MapMaker(){
		overWorldMap = new OverWorldMap();
		player = new Player(false);
		scrollMapPainter = new ScrollMapPainter();
		setOldScrollMap();
	}
	
	//initiates MapMaking process with Exception Handler
	public void iniMapMaker() {
		
		try {
			paintMapData();
		} catch (IOException ioe){
			System.err.println("iniMapMaker-readMapData: " + ioe);
		} catch (NullPointerException npe) {
			System.err.println(npe);
		}	
	}
	
	//TODO mapMakerThread
	public void run(){
		
	}
	
	public void paintComponents (Graphics g) {
		g2d = (Graphics2D) g;
	}
	
	public void paintMapData() throws IOException, NullPointerException{
		
		String oneLine = null, dummyLine = null;
		try{
			switchMap();
			int x = 0;
			int y = 0;
			readMapData:
				for (int i = 0; i < 7; i++){
					
					for (int j = 0; j < 9; j++){
						//TODO THREAD: scrollMap(0,0);
						oneLine = overWorldMap.getMapDataBuff().readLine();
						mapGrid = new BoardGrid(oneLine);
						
						mapBuff = mapTiles.getSubimage(mapGrid.getX(), mapGrid.getY(), B2.x, B2.y);
						
						//g2d.drawImage(mapBuff , (x*6), (y*6), this);
						
						
						if(!ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && !ScrollMapPainter.toEast)
							g2d.drawImage(mapBuff , (x*6), (y*6), this);
						if(ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && !ScrollMapPainter.toEast){
							//System.out.println("scroll to North");
							g2d.drawImage(mapBuff , (x*6), -630+ scrollY + (y*6), this);	
						}
						if(!ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && ScrollMapPainter.toEast){
							//System.out.println("scroll to East");
							g2d.drawImage(mapBuff , (x*6)+810+scrollX, (y*6), this);	
						}
						if(!ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && ScrollMapPainter.toSouth && !ScrollMapPainter.toEast){
							//System.out.println("scroll to South");
							g2d.drawImage(mapBuff , (x*6), 630 + scrollY + (y*6), this);
						}
						if(!ScrollMapPainter.toNorth && ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && !ScrollMapPainter.toEast){
							//System.out.println("scroll to West");
							g2d.drawImage(mapBuff , (x*6)-810+scrollX, (y*6), this);	
						}
						
						//System.out.println("paint Tile: " + x*6 +", " + y*6 + " at this Position: " + mapGrid.getX() + ", " + mapGrid.getY());
						x += 15;
					}
					
					dummyLine = overWorldMap.getMapDataBuff().readLine();
						if (dummyLine == null){
							System.err.println("DummyLine != 0");
							break readMapData;
						}
						
					x = 0;
					y += 15;	
				}
				
			} finally {
			if (overWorldMap.getMapDataBuff() != null)
				overWorldMap.getMapDataBuff().close();
				//System.out.println("close mapdataBuff from MapMaker");
			}
		
			scrollBetweenMaps();
			
		}
	
	public void scrollBetweenMaps() throws IOException, NullPointerException{
		if(OverWorldMap.nextMapY == -1){
			scrollReady = false;
			scrollY += SCROLLSPEED_Y;
			//System.out.println("scroll to North: " + scrollY);
			
			player.setPosition(Player.x, 500+scrollY-560);
			ScrollMapPainter.toNorth = true;
			if(scrollY >= 630){System.out.println("North");scrollY = 0;OverWorldMap.nextMapY = 0;scrollReady = true;ScrollMapPainter.toNorth = false;setOldScrollMap();}
		}
		if(OverWorldMap.nextMapX == 1){
			scrollReady = false;
			scrollX -= SCROLLSPEED_X;
			//System.out.println("scroll to East: " + scrollX);
			
			player.setPosition(810+scrollX, Player.y);
			ScrollMapPainter.toEast = true;
			if(scrollX <= -810){System.out.println("East");scrollX = 0;OverWorldMap.nextMapX = 0;scrollReady = true;ScrollMapPainter.toEast = false;setOldScrollMap();}
		}
		if(OverWorldMap.nextMapY == 1){
			scrollReady = false;
			scrollY -= SCROLLSPEED_Y;
			//System.out.println("scroll to South: " + scrollY);

			player.setPosition(Player.x,630+scrollY-30);
			ScrollMapPainter.toSouth = true;
			if(scrollY <= -630){System.out.println("South");scrollY = 0;OverWorldMap.nextMapY = 0;scrollReady = true;ScrollMapPainter.toSouth = false;setOldScrollMap();}
		}	
		if(OverWorldMap.nextMapX == -1){
			scrollReady = false;
			scrollX += SCROLLSPEED_X;
			//System.out.println("scroll to West: " + scrollX);

			player.setPosition(-810+scrollX+730, Player.y);
			ScrollMapPainter.toWest = true;
			if(scrollX >= 810){System.out.println("West");scrollX = 0;OverWorldMap.nextMapX = 0;scrollReady = true;ScrollMapPainter.toWest = false;setOldScrollMap();}
		}
	
		
	}
	
	public void setOldScrollMap(){
		System.out.println("setOLD");
		ScrollOverWorld.worldMapX = overWorldMap.worldMapX;
		ScrollOverWorld.worldMapY = overWorldMap.worldMapY;
	}
	
	
	//get overWorldMap for MapMaker
	public void switchMap() throws IOException {
		
		try
		{	
			overWorldMap.scrollData(overWorldMap.worldMapX, overWorldMap.worldMapY,true);
			mapTiles = ImageIO.read(overWorldMap.mapSetURL);
			//mapData = overWorldMap.mapDataBuff;
			//mapBuff = new BufferedImage(810, 630, BufferedImage.TYPE_INT_ARGB);
	
		} finally {
			
		}
	}
	
	
	public boolean getScrollStatus(){return scrollReady;}
	
	
}

