import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Image;

public class OverWorldMap extends JComponent implements Runnable, FileLink{
	final static Rectangle BoundN = new Rectangle(0,0,810,1);
	final static Rectangle BoundE = new Rectangle(809,0,1,630);
	final static Rectangle BoundS = new Rectangle(0,627,810,1);
	final static Rectangle BoundW = new Rectangle(0,0,1,630);
	
	static Rectangle Over1Dungeon1;
	
	//instantias MapBuilder
	final static DungeonBuilder dungeonBuilder = new DungeonBuilder();
	
	//instance variables
	public static boolean overWorld;
	public static int areaID = 1;
	
	

	Graphics2D g2d;
	
	
	BufferedImage newMap;
	
	public OverWorldMap(){
		System.err.println("->Map");
	
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		g2d.drawImage(newMap,-Camera.cameraX,-Camera.cameraY,this);
	}
	
	public void run(){
		if (Board.printMsg)
			System.out.println("Map.run");
		getMap();
	}
	

	private void getMap(){
		try {
			if (overWorld){
				newMap = ImageIO.read(OWMap00_00);
			}
			
			//thread goes to DungeonBuilder
			if(DungeonNavigator.dungeon){
				//dungeonBuilder.start();
			}
			
			} catch (IOException e) {
				System.err.println("map not found");
				System.exit(0);
			}
	}

	public static void setBounds(){
		
		Over1Dungeon1 = new Rectangle(110-Camera.cameraX, 0-Camera.cameraY,60,90 );

	}
		
}
