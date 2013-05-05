import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Image;

public class Map extends JComponent implements Runnable, FileLink{
	Graphics2D g2d;
	BufferedImage playerMove, playerMoveBuff;
	
	public Map(){
		System.err.println("->MapMaker");
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
	}
	
	public void run(){
		if (Board.printMsg)
			System.out.println("Map.run");	
	}

}
