

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
	OverWorldMap overWorldMap;
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
	static int scrollX = 810, scrollY = 630;
	static boolean scrollReady = true;
	final static int SCROLLSPEED_X = 40,  SCROLLSPEED_Y = 30;
	

	
	public ScrollMapPainter(){
		overWorldMap = new OverWorldMap();
		player = new Player(false);
	}
	
	//initiates MapMaking process with Exception Handler
	public void iniMapMaker() {
		
		try {
			paintOldMap();
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
	
	public void paintOldMap() throws IOException, NullPointerException{
		
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
						//g2d.drawImage(mapBuff , (x*6)+(810-scrollX), (y*6)+(630-scrollY), this);
						g2d.drawImage(mapBuff , (x*6), (y*6), this);
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
			//scrollBetweenMaps();
			//scrollMapPainter.paintLeftBorder();
		}
	
	public void scrollBetweenMaps() throws IOException, NullPointerException{
		if(OverWorldMap.nextMapY == -1){
			scrollY += SCROLLSPEED_Y;
			player.setPosition(player.getX(), player.yEnterNewMap+scrollY-630-40*3);
			if(scrollY >= 2*630){System.out.println("OVER Y");scrollY = 630;OverWorldMap.nextMapY = 0;scrollReady = true;}
		}
		if(OverWorldMap.nextMapX == 1){
			scrollX -= SCROLLSPEED_X;
			player.setPosition(player.xEnterNewMap-810+scrollX+30*3, player.getY());
			if(scrollX <= 0){System.out.println("OVER X");scrollX = 810;OverWorldMap.nextMapX = 0;scrollReady = true;}
		}
		if(OverWorldMap.nextMapY == 1){
			System.out.println("south");
			scrollY -= SCROLLSPEED_Y;
			System.out.println(scrollY);
			player.setPosition(player.getX(),player.yEnterNewMap-630+scrollY+40*3);
			if(scrollY <= 0){System.out.println("OVER Y");scrollY = 630;OverWorldMap.nextMapY = 0;scrollReady = true;}
		}	
		if(OverWorldMap.nextMapX == -1){
			scrollX += SCROLLSPEED_X;
			player.setPosition(scrollX-810+player.xEnterNewMap-30*3, player.getY());
			if(scrollX >= 2*810){System.out.println("OVER X");scrollX = 810;OverWorldMap.nextMapX = 0;scrollReady = true;}
		}
	
		
	}
	
	//get overWorldMap for MapMaker
	public void switchMap() throws IOException {
		
		try
		{	
			overWorldMap.scrollData(overWorldMap.oldWorldMapX, overWorldMap.oldWorldMapY, false);
			mapTiles = ImageIO.read(overWorldMap.mapSetURL);
			//mapData = overWorldMap.mapDataBuff;
			//mapBuff = new BufferedImage(810, 630, BufferedImage.TYPE_INT_ARGB);
	
		} finally {
			
		}
	}
	
	
	
	public boolean getScrollStatus(){return scrollReady;}
	
	
}

