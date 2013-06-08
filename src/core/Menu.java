package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;

import core.Board;
import core.FileLink;
import core.GameManager;

public class Menu extends JComponent implements FileLink{
	
	private static Menu menu;
	private Graphics2D g2d;
	
	Rectangle startButton = new Rectangle();
	Rectangle optionsButton = new Rectangle();
	Rectangle beendeButton = new Rectangle();
	Rectangle tonButton = new Rectangle();
	
	BufferedImage menuBuff = new BufferedImage(810,630, BufferedImage.TYPE_INT_ARGB);
	BufferedImage buttonBuff = new BufferedImage(32, 82, BufferedImage.TYPE_INT_ARGB);
	
	
	private Menu(){
		initializeMenu();
	}
	
	public void paintComponents(Graphics g){
		
		g2d = (Graphics2D) g;
		
		g2d.drawImage(menuBuff, 0, 0, null);
		
		
		//mainMenu
		if(!GameManager.getInstance().getIngame()){
			
			g2d.setColor(Color.cyan);
			g2d.draw(startButton);
			
			g2d.setColor(Color.red);
			g2d.draw(beendeButton);
			
			
			
		}
		
		
		//ingameMenu
		if(GameManager.getInstance().getIngame()){
			
		}
		
	}
	
	public void initializeMenu(){
		
		try {

			menuBuff = ImageIO.read(menuFile);
			buttonBuff = ImageIO.read(buttonFile);
			
		} catch (IOException e) {
			System.err.println("Menu: menuFile not found");
			System.exit(0);
		} 
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP){
			
		}
		
		if(key == KeyEvent.VK_RIGHT){
					
		}
		
		if(key == KeyEvent.VK_DOWN){
			
		}
		
		if(key == KeyEvent.VK_LEFT){
			
		}
		
	}
	
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP){
			
		}
		
		if(key == KeyEvent.VK_RIGHT){
					
		}
		
		if(key == KeyEvent.VK_DOWN){
			
		}
		
		if(key == KeyEvent.VK_LEFT){
			
		}
	}
	
	public void mouseClicked(MouseEvent mE){
		int clickX = mE.getX();
		int clickY = mE.getY();
	
		if(startButton.contains(clickX, clickY)){
			GameManager.getInstance().switchGameState(false, true);
		}
		
		if(beendeButton.contains(clickX, clickY)){
			System.exit(0);
		}
		
	}

	public static Menu getInstance(){
		if(menu == null)
			menu = new Menu();
		return menu;
	}
}
