package core;


import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Menu extends JComponent implements FileLink, ChangeListener{
	
	private static Menu menu;
	private Graphics2D g2d;

	private boolean options;
	private boolean sound;
	private JSlider slSouth;
	private JSlider slNorth;
	
	BufferedImage menuBuff = new BufferedImage(810,630, BufferedImage.TYPE_INT_ARGB);
	BufferedImage buttonBuff = new BufferedImage(322, 82, BufferedImage.TYPE_INT_ARGB);
	
	final int xStart = 270; 
	final int yStart = 180;
	final int xRec = 322;
	final int yRec = 82;
	
	Rectangle startButton = new Rectangle(xStart,yStart,xRec,yRec);
	Rectangle optionsButton = new Rectangle(xStart,yStart+yRec,xRec,yRec);
	Rectangle beendeButton = new Rectangle(xStart,yStart+3*yRec,xRec,yRec);
	

	Rectangle level1Button = new Rectangle(xStart,yStart-yRec,xRec,yRec);
	Rectangle level2Button = new Rectangle(xStart,yStart,xRec,yRec);
	Rectangle soundButton = new Rectangle(xStart,yStart+2*yRec,xRec,yRec);
	Rectangle returnButton = new Rectangle(xStart,yStart+3*yRec,xRec,yRec);
	
	
	private Menu(){
		initializeMenu();
	}

	
	public void paintComponents(Graphics2D g2d){
	
		
		g2d.drawImage(menuBuff, 0, 0, null);
		
		
		if(options){
			
			/*g2d.drawImage(...Buff, xStart, yStart-yRec, null);
			g2d.drawImage(...Buff, xStart, yStart, null);
			g2d.drawImage(...Buff, xStart, yStart+2*yRec, null);
			g2d.drawImage(...Buff, xStart, yStart+3*yRec, null);
			Bilder für Return-,Sound-,Level1 und Level2Button hinzufügen*/
			
			g2d.setColor(Color.pink);
			g2d.draw(soundButton);
		
			g2d.setColor(Color.green);
			g2d.draw(level1Button);
			
			g2d.setColor(Color.green);
			g2d.draw(level2Button);
			
			g2d.setColor(Color.red);
			g2d.draw(returnButton);
			
		}
		
		
		//mainMenu
		if(!options){
			
			g2d.drawImage(buttonBuff, xStart, yStart, null);
			g2d.drawImage(buttonBuff, xStart, yStart+yRec, null);
			g2d.drawImage(buttonBuff, xStart, yStart+3*yRec, null);
			
			g2d.setColor(Color.cyan);
			g2d.draw(startButton);
			
			g2d.setColor(Color.red);
			g2d.draw(beendeButton);
			
		}
		
		

		
	}
	
	public void initializeMenu(){
		
		try {

			//menuBuff = ImageIO.read(menuFile);

			buttonBuff = ImageIO.read(buttonFile);
		} catch (IOException e) {
			System.err.println(e);
			//System.exit(0);
		} 

	}
	public void slider(){
			
			/*
			slSouth = new JSlider(JSlider.HORIZONTAL,0,100,50);
			Board.getInstance().add(slSouth);
			slSouth.setMinorTickSpacing(10);//kleine Makierungsabstand;
			slSouth.setMajorTickSpacing(25);//große Markierungsabstand;
			slSouth.setPaintTicks(true);
			slSouth.setPaintLabels(true);
			slSouth.setSnapToTicks(false);//Zwischenposition wird angewählt
			slSouth.setBounds(xStart,yStart+2*yRec,50,50);
			slSouth.setPreferredSize(new Dimension(800,600));
			System.out.print("slSouth");
			
			slNorth = new JSlider(JSlider.HORIZONTAL,0,100,50);
			Board.getInstance().add(slNorth);
			slNorth.setMinorTickSpacing(10);//kleine Makierungsabstand;
			slNorth.setMajorTickSpacing(50);//große Markierungsabstand;
			slNorth.setPaintTicks(true);
			slNorth.setPaintLabels(true);
			slNorth.setSnapToTicks(false);//Zwischenposition wird angewählt
			slNorth.setBounds(100,100,50,50);
			slNorth.setPreferredSize(new Dimension(800,600));
			System.out.print("slNorth");
			
			Board.getInstance().validate();
			Board.getInstance().repaint();
			
	*/
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
		
		if(!options){
			if(startButton.contains(clickX, clickY)){
				GameManager.getInstance().switchGameState(false, true);
			}
			if(optionsButton.contains(clickX, clickY)){
				options = true;
			}
			if(beendeButton.contains(clickX, clickY)){
				System.exit(0);
			}
		}
		if(options){

			if(soundButton.contains(clickX, clickY)){
				if(sound)
					sound=false;
				else {
					sound=true;
					//slider();
				}
			}
			if(level1Button.contains(clickX, clickY)){
			}
			if(level2Button.contains(clickX, clickY)){
			}
			if(returnButton.contains(clickX, clickY)){
				options = false;
			}
			
		}
	
		
	}
	
	public static void resetInstance(){
		if(menu != null)
			menu = new Menu();
	}

	public static Menu getInstance(){
		if(menu == null)
			menu = new Menu();
		return menu;
	}


	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
