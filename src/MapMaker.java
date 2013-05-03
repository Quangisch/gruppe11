

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
	final static int SCROLLSPEED_X = 40,  SCROLLSPEED_Y = 30;
	

	
	public MapMaker(){
		overWorldMap = new OverWorldMap();
		player = new Player(false);
		scrollMapPainter = new ScrollMapPainter();
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
						oneLine = OverWorldMap.mapDataBuff.readLine();
						mapGrid = new BoardGrid(oneLine);
						
						mapBuff = mapTiles.getSubimage(mapGrid.getX(), mapGrid.getY(), B2.x, B2.y);
						g2d.drawImage(mapBuff , (x*6)-scrollX, (y*6)-scrollY, this);
						//g2d.drawImage(mapBuff , (x*6), (y*6), this);
						x += 15;
						
						//System.out.println("paint Tile: " + x*6 +", " + y*6 + " at this Position: " + mapGrid.getX() + ", " + mapGrid.getY());
						
					}
					
					dummyLine = OverWorldMap.mapDataBuff.readLine();
						if (dummyLine == null){
							System.err.println("DummyLine != 0");
							break readMapData;
						}
						
					x = 0;
					y += 15;	
				}
				
			} finally {
			if (OverWorldMap.mapDataBuff != null)
				OverWorldMap.mapDataBuff.close();
			}
			scrollBetweenMaps();
			
		}
	
	public void scrollBetweenMaps() throws IOException, NullPointerException{
		if(OverWorldMap.nextMapY == -1){
			scrollReady = false;
			scrollY += SCROLLSPEED_Y;
			player.setPosition(player.getX(), scrollY);
			if(scrollY >= 2*630){System.out.println("North");scrollY = 0;OverWorldMap.nextMapY = 0;scrollReady = true;}
		}
		if(OverWorldMap.nextMapX == 1){
			scrollReady = false;
			scrollX -= SCROLLSPEED_X;
			player.setPosition(scrollX, player.getY());
			if(scrollX <= 0){System.out.println("East");scrollX = 0;OverWorldMap.nextMapX = 0;scrollReady = true;}
		}
		if(OverWorldMap.nextMapY == 1){
			scrollReady = false;
			scrollY -= SCROLLSPEED_Y;
			System.out.println(scrollY);
			player.setPosition(player.getX(),scrollY);
			if(scrollY <= 0){System.out.println("South");scrollY = 0;OverWorldMap.nextMapY = 0;scrollReady = true;}
		}	
		if(OverWorldMap.nextMapX == -1){
			scrollReady = false;
			scrollX += SCROLLSPEED_X;
			player.setPosition(scrollX, player.getY());
			if(scrollX >= 2*810){System.out.println("West");scrollX = 0;OverWorldMap.nextMapX = 0;scrollReady = true;}
		}
	
		
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

