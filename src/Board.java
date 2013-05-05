import javax.imageio.ImageIO;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

public class Board extends JPanel implements ActionListener, FileLink{
	//instantiates classes
	final static MainMenu mainMenu = new MainMenu();
	final static Map map = new Map();
	final static Player player = new Player();
	
	
	//Threads
	final static ScheduledThreadPoolExecutor threadScheduler = new ScheduledThreadPoolExecutor(5);
	final static Thread mainMenuThread = new Thread(mainMenu);
	final static Thread mapThread = new Thread(map);
	final static Thread playerThread = new Thread(player);
	
	Timer repaintTimer;
	static Graphics2D g2d;
	BufferedImage playerMove;
	
	//instance variables
	static boolean repaintNow = false;
	static boolean printMsg = false;
	static boolean menuThread = false, ingameThread = true;
	static int clickCount = 0;

	public Board(){
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(Color.BLACK);
		
		
		//Listeners
		this.addMouseListener(new MAdapter());
		this.addKeyListener(new KAdapter());
		
		//Timer
		repaintTimer = new Timer(50, this);
		repaintTimer.start();
		
		
		System.out.println("->Board");
		startNow();
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g2d = (Graphics2D) g;

		mainMenu.paintComponents(g2d);
		map.paintComponents(g2d);
		player.paintComponents(g2d);
		
		//paint player
		g2d.setColor(Color.red);
        g2d.drawRect(Player.x+10,Player.y+10,60,10);
        g2d.drawRect(Player.x+10,Player.y+90,60,10);
        g2d.drawRect(Player.x+10,Player.y+10,10,90);
        g2d.drawRect(Player.x+60,Player.y+10,10,90);
		g2d.drawImage(player.getImage(),Player.x,Player.y,this);
		
	
		g.dispose();

	
	}

	public void startNow(){
		System.out.println("Board.startNow");
		
		
	}
	
	public void actionPerformed (ActionEvent aE){
		repaint(Player.x-150, Player.y-200,600,800);
		if (repaintNow == true){
			System.out.println("repaintNow:" +repaintNow);
			repaintNow = false;
			repaint();	
		}
		
		
		if(ingameThread){
			System.out.println("ingame Threads start");
			ingameThread = false;
			threadScheduler.scheduleWithFixedDelay(mapThread, 10, 10,TimeUnit.MILLISECONDS);
			threadScheduler.scheduleWithFixedDelay(playerThread, 10, 10,TimeUnit.MILLISECONDS);
			
		}
		
		if(menuThread){
			System.out.println("menu Threads start");
			menuThread = false;
			threadScheduler.scheduleWithFixedDelay(mainMenuThread, 500, 10,TimeUnit.MILLISECONDS);
			
		}
		
	}
	
	
	private class KAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent kE){
			player.keyReleased(kE);
		}	
		public void keyPressed(KeyEvent kE){
			player.keyPressed(kE);
		}
	}
	
	private class MAdapter extends MouseAdapter{ 
	      @Override 
	      public void mouseClicked( MouseEvent e ) { 
	    	  clickCount++;
	    	  System.out.println(clickCount);
	      } 
	 }
	
	
}
