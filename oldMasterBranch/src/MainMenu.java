

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Color;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.Timer;



import java.io.*;

public class MainMenu extends JPanel implements GridInterface{
	
	Player player;
	Board board;
	Graphics g2d;
	BufferedImage menu, button;
	Rectangle button1, button2, button3;
	//Timer timer;
	
	
	
	public MainMenu(){
		
		
		button1 = new Rectangle(C4.x, C4.y, B5.x, B5.y);
		button2 = new Rectangle(D4.x, D4.y, B5.x, B5.y);
		button3 = new Rectangle(F4.x, F4.y, B5.x, B5.y);
		
		//timer = new Timer(5, this);
		//timer.start();
		


		try{
			
			URL url = this.getClass().getResource(FileLink.menu);
			menu = ImageIO.read(url);
			url = this.getClass().getResource(FileLink.button);
			button = ImageIO.read(url);
			
		} catch (IOException e){
			
		}
		
		
		
		
		
	}
	
	public void paintComponent(Graphics g){
		g2d = (Graphics2D) g;
	}
	
	public void paintMenu(){
		g2d.drawImage(menu, A1.x, A1.y, this);
		g2d.drawImage(button, C4.x, C4.y, this);
		g2d.drawImage(button, D4.x, D4.y, this);
		g2d.drawImage(button, F4.x, F4.y, this);
	
	}
	
	public boolean clickButton1(MouseEvent e){
		return button1.contains(e.getPoint());
	}
	public boolean clickButton2(MouseEvent e){
		return button2.contains(e.getPoint());
	}
	public boolean clickButton3(MouseEvent e){
		return button3.contains(e.getPoint());
	}

	
	public void hallo(){
		System.out.println("hallo von menu");
	}

}
//class
class ButtonArea extends JComponent implements GridInterface{
	Dimension buttonSize = new Dimension(G9.x, G9.y);
	
}


