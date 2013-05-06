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
	
	
	BufferedImage newMap;
	
	public Map(){
		System.err.println("->Map");
	}
	
	public void paintComponents(Graphics g){
		//g2d = (Graphics2D) g;
	}
	
	public void run(){
		if (Board.printMsg)
			System.out.println("Map.run");
		paintMap();
	}
	

	public void paintMap(){
		try {
			newMap = ImageIO.read(newMap00_00);
			//playerMoveBuff = new BufferedImage(1260, 960, BufferedImage.TYPE_INT_ARGB);
			} catch (IOException e) {
				System.err.println("file not found");
				System.exit(0);
			}
	}
	
	public BufferedImage getImage(){
		return newMap;
	}
		
}
