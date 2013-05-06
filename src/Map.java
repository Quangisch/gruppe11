import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Image;

public class Map extends JComponent implements Runnable, FileLink{
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
