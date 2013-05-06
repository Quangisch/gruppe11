import java.awt.Image;
import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class MainMenu extends JComponent implements Runnable{
	Graphics g2d;
	public MainMenu(){
		System.err.println("->MainMenu");
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		if(Board.printMsg)
			System.out.println("MainMenu.paintComponents");
	}
	
	public void run(){
		if (Board.printMsg)
			System.out.println("MainMenu.run");
	}
	
}

