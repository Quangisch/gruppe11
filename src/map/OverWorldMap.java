package map;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

import core.Board;
import core.FileLink;
import core.GameManager;
import characters.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Image;

public class OverWorldMap extends JComponent implements Runnable, FileLink{
	
	private static Player player;
	private static OverWorldCamera overWorldCamera;
	
	
	static Rectangle enterDungeonBound;
	
	//instance variables
	public static boolean overWorld;
	public static int areaID = 1;
	public boolean loadNewMap = true;
	
	

	Graphics2D g2d;
	
	
	BufferedImage newMap;
	
	public OverWorldMap(){
		System.err.println("->Map");
		
	}
	
	public OverWorldMap(Player player, OverWorldCamera overWorldCamera){
		this.player = player;
		this.overWorldCamera = overWorldCamera;

	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		g2d.drawImage(newMap,-overWorldCamera.getCameraX(),-overWorldCamera.getCameraY(),null);
	}
	
	public void run(){
		if (GameManager.printMsg)
			System.out.println("Map.run");
		if(loadNewMap){
			loadNewMap = false;
			getMap();
		}
		
		overWorldCamera.run();
	}
	

	private void getMap(){
		try {
			if (overWorld){
				newMap = ImageIO.read(OWMap00_00);
			}
			
			
			} catch (IOException e) {
				System.err.println("map not found");
				System.exit(0);
			}
	}

	public void setDungeonBounds(){
		switch(areaID){
		
		case 1: enterDungeonBound = new Rectangle(110-overWorldCamera.getCameraX(), 0-overWorldCamera.getCameraY(),60,90 );
				break;
		}
		
	}
	
	public Rectangle getDungeonBounds(){
		return enterDungeonBound;
	}
	
	public void alignCamera(){
		overWorldCamera.alignCamera();
	}
	
	public void toogleCamera(boolean cOn){
		overWorldCamera.toogleCamera(cOn);
	}
	
	
	//get, set Camera
	public int getCameraX(){return overWorldCamera.cameraX;}
	public int getCameraY(){return overWorldCamera.cameraY;}
	
	public void setCameraX(int cameraX){overWorldCamera.cameraX = cameraX;}
	public void setCameraY(int cameraY){overWorldCamera.cameraX = cameraY;}
	
	
	//get, set Locks
	public int getScrollLock(){return overWorldCamera.getScrollLock();}
	public boolean getCameraStatus(){return overWorldCamera.getCameraStatus();}
	public boolean getCameraLock(){return overWorldCamera.getCameraLock();}
	public boolean getMoveFocus(){return overWorldCamera.getMoveFocus();}
	
	public void setCameraStatus(boolean cameraOn){overWorldCamera.setCameraStatus(cameraOn);}
	public void setScrollLock(int scrollLock){overWorldCamera.setScrollLock(scrollLock);}
	public void setCameraLock(boolean cameraLock){overWorldCamera.setCameraLock(cameraLock);}
	public void setMoveFocus(boolean moveFocus){overWorldCamera.setMoveFocus(moveFocus);}	
	
	
	public void setLoadNewMap(boolean loadNewMap){
		this.loadNewMap = loadNewMap;
	}

}
