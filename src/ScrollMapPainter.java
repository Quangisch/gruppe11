

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


public class ScrollMapPainter extends JComponent implements Runnable, GridInterface{
	
	//class instances
	ScrollOverWorld scrollOverWorld;
	static BufferedImage mapTiles, mapBuff;
	BoardGrid mapGrid;
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
	static boolean toNorth, toEast, toSouth, toWest;
	final static int SCROLLSPEED_X = 40,  SCROLLSPEED_Y = 30;

	public ScrollMapPainter() {
		scrollOverWorld = new ScrollOverWorld();
		player = new Player(false);
		

		
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
					
						oneLine = scrollOverWorld.getMapDataBuff().readLine();
						mapGrid = new BoardGrid(oneLine);
						
						mapBuff = mapTiles.getSubimage(mapGrid.getX(), mapGrid.getY(), B2.x, B2.y);
						
						//g2d.drawImage(mapBuff , (x*6), (y*6), this);
						
						//System.out.println("draw old map");
						if(!ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && !ScrollMapPainter.toEast)
							g2d.drawImage(mapBuff , (x*6), (y*6), this);
						if(ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && !ScrollMapPainter.toEast){
							//System.out.println("scroll to North");
							g2d.drawImage(mapBuff , (x*6), MapMaker.scrollY + (y*6)-50, this);	
						}
						if(!ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && ScrollMapPainter.toSouth && !ScrollMapPainter.toEast){
							//System.out.println("scroll to South");
							g2d.drawImage(mapBuff , (x*6), MapMaker.scrollY + (y*6)+50, this);
						}
						if(!ScrollMapPainter.toNorth && !ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && ScrollMapPainter.toEast){
							//System.out.println("scroll to East");
							g2d.drawImage(mapBuff , (x*6)+MapMaker.scrollX+60, (y*6), this);	
						}
						if(!ScrollMapPainter.toNorth && ScrollMapPainter.toWest && !ScrollMapPainter.toSouth && !ScrollMapPainter.toEast){
							//System.out.println("scroll to West");
							g2d.drawImage(mapBuff , (x*6)+MapMaker.scrollX-60, (y*6), this);	
						}
						
						
						//System.out.println("paint Tile: " + x*6 +", " + y*6 + " at this Position: " + mapGrid.getX() + ", " + mapGrid.getY());
						x += 15;
					}
					
					dummyLine = scrollOverWorld.getMapDataBuff().readLine();
						if (dummyLine == null){
							System.err.println("DummyLine != 0");
							break readMapData;
						}
						
					x = 0;
					y += 15;	
				}
				
			} finally {
			if (scrollOverWorld.getMapDataBuff() != null)
				scrollOverWorld.getMapDataBuff().close();
			}
		
		
			
		}
	
	//get overWorldMap for MapMaker
	public void switchMap() throws IOException {
		
		try
		{	
			scrollOverWorld.scrollData(scrollOverWorld.worldMapX, scrollOverWorld.worldMapY,false);
			//oldWorldMap.scrollData(0, 0,false);
			mapTiles = ImageIO.read(scrollOverWorld.mapSetURL);
			//mapData = OldWorldMap.mapDataBuff;
			//mapBuff = new BufferedImage(810, 630, BufferedImage.TYPE_INT_ARGB);
	
		} finally {
			
		}
	}
	
	
	public boolean getScrollStatus(){return scrollReady;}
	
	
}

