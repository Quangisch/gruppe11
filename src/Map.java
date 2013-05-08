import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Image;

public class Map extends JComponent implements Runnable, FileLink{
	final static Rectangle BoundN = new Rectangle(0,0,810,1);
	final static Rectangle BoundE = new Rectangle(809,0,1,630);
	final static Rectangle BoundS = new Rectangle(0,627,810,1);
	final static Rectangle BoundW = new Rectangle(0,0,1,630);
	
	static Rectangle intoDungeon1;
	
	//instantias MapBuilder
	final static MapBuilder mapBuilder = new MapBuilder();
	
	//instance variables
	public static boolean overWorld = true;
	public static boolean dungeon = false;
	
	public static boolean loadNewMap = true;
	Graphics2D g2d;
	
	
	BufferedImage newMap;
	
	public Map(){
		System.err.println("->Map");

		
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		/*TODO*/g2d.drawImage(newMap,-Camera.cameraX,-Camera.cameraY,this);
		//System.out.println("paint map");
		//g2d.drawImage(mapBuilder.getSubimages(x, y))
		 BufferedImage newMap = new BufferedImage(810, 610, BufferedImage.TYPE_INT_ARGB);
		 Graphics2D g2d = newMap.createGraphics();
		   
		 
		 //g2d.drawImage(mapBuilder.getSubimages(2,3),0,0,this);
		
		
		for(int y = 0; y < 7; y++){			
			for(int x = 0; x < 9; x++){
				newMap = mapBuilder.getSubimages(x,y);
				//System.out.println("x: "+mapBuilder.xWall1[x][y]+", y: "+mapBuilder.yWall1[x][y]);
				//g2d.drawImage(newMap,x*90-90,y*90-90,this);
			}
		}
		
		

	}
	
	public void run(){
		if (Board.printMsg)
			System.out.println("Map.run");
		
		if(dungeon)
			Camera.cameraLock = true;
		
		if(loadNewMap){
			getMap();
			loadNewMap = false;
			System.out.println("new map loading");
		}

		
			
	}
	

	private void getMap(){
		
		try {
			if (overWorld && !dungeon){
				newMap = ImageIO.read(OWMap00_00);
				
				
			}
			
			if(dungeon && !overWorld){
				mapBuilder.start();
				

				
			}
			
			} catch (IOException e) {
				System.err.println("map not found");
				System.exit(0);
			}
	}

	public BufferedImage getImage(){
		return newMap;
	}
	
	public static void setBounds(){
		intoDungeon1 = new Rectangle(110-Camera.cameraX, 0-Camera.cameraY,60,90 );
	}
		
}
