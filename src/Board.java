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
	final static MenuIngame ingameMenu = new MenuIngame();
	final static MenuMain mainMenu = new MenuMain();
	final static Map map = new Map();
	final static Player player = new Player();
	final static Camera camera = new Camera();
	final static CollisionDetection collisionDetection = new CollisionDetection();
	
	//threads
	final static ScheduledThreadPoolExecutor threadScheduler = new ScheduledThreadPoolExecutor(5);
	final static Thread ingameMenuThread = new Thread(ingameMenu);
	final static Thread mainMenuThread = new Thread(mainMenu);
	final static Thread mapThread = new Thread(map);
	final static Thread playerThread = new Thread(player);
	final static Thread cameraThread = new Thread(camera);
	final static Thread collisionDetectionThread = new Thread(collisionDetection);
	
	//instance variables
	static boolean repaintNow = false;
	static boolean menuThread = false, ingameThread = false;
	static int clickCount = 0;
	
	/*switch menu/ingame with M*/	static boolean ingame = true;
									static boolean menu = false;
	
	/*sound/music volume*/			static int musicVolume = 50;
									static int soundVolume = 50;
								
	/*Debug tmpVaribles*/			static boolean paintBounds = false;
									static boolean printMsg = false;
	
	private Timer repaintTimer;
	private Graphics2D g2d;

	public Board(){
		
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(Color.BLACK);
		
		
		//Listeners
		this.addMouseListener(new MAdapter());
		this.addKeyListener(new KAdapter());
		
		//Timer
		repaintTimer = new Timer(5, this);
		repaintTimer.start();
		
		//initiate Threads
		if (ingame){
			ingameThread = true;
			menuThread = false;
		} else {
			ingameThread = false;
			menuThread = true;
		}

		//start point
		Player.absoluteX = Player.x = 405-20*3;
		Player.absoluteY = Player.y = 315-15*3;
		Camera.cameraX = Player.absoluteX;
		Camera.cameraY = Player.absoluteY;
		
		System.out.println("->Board");
		start();
	}
	
	public void paint(Graphics g) {

		super.paint(g);
		g2d = (Graphics2D) g;

		ingameMenu.paintComponents(g2d);
		map.paintComponents(g2d);
		player.paintComponents(g2d);
		
		
		if (!ingame){
			//paint MenuMain
		}
		
		if(ingame && menu){
			//paint MenuIngame
			//g2d.drawImage(menu
		}
		
		if (ingame && !menu){
			//paint map
			g2d.drawImage(map.getImage(),-Camera.cameraX,-Camera.cameraY,this);
			
			//paint player interface
			
			//paint player
			
			g2d.drawImage(player.getImage(),Player.x,Player.y,this);
			
			if(paintBounds){
				g2d.setColor(Color.red); //PlayerBounds
		        g2d.drawRect(Player.x+10,Player.y+10,60,10);g2d.drawRect(Player.x+10,Player.y+90,60,10);g2d.drawRect(Player.x+10,Player.y+10,10,90);g2d.drawRect(Player.x+60,Player.y+10,10,90);
		        g2d.setColor(Color.blue); //Map Bounds
		        g2d.draw(Map.BoundN);g2d.draw(Map.BoundE);g2d.draw(Map.BoundS);g2d.draw(Map.BoundW);
		        g2d.setColor(Color.yellow);
		        Player.setAttackBounds();
		        g2d.draw(Player.attackBound);
			}
		}
		
		g.dispose();
	}
	
	public void start(){
	
	}
	//Timer loop
	public void actionPerformed (ActionEvent aE){
		start();
		//repaint(Player.x-150, Player.y-200,600,800);
		repaint();
		if (repaintNow == true){
			//System.out.println("repaintNow:" +repaintNow);
			repaintNow = false;
			repaint();	
		}
		
		if(ingame && !menu){
			System.out.println("ingame Threads start");
			ingameThread = false;
			
			//shutdown menuThreads
			
			
			threadScheduler.scheduleWithFixedDelay(mapThread, 50, 10,TimeUnit.MILLISECONDS);
			threadScheduler.scheduleWithFixedDelay(playerThread, 100, 10,TimeUnit.MILLISECONDS);
			threadScheduler.scheduleWithFixedDelay(cameraThread, 200, 5,TimeUnit.MILLISECONDS);
			threadScheduler.scheduleWithFixedDelay(collisionDetectionThread, 500, 10, TimeUnit.MILLISECONDS);
			
		}
		
		if(menu){
			System.out.println("menu Threads start");
			menuThread = false;
			
			//shutdown ingameThreads
			
			if(ingame)
				threadScheduler.scheduleWithFixedDelay(ingameMenuThread, 500, 10,TimeUnit.MILLISECONDS);
			if(!ingame)
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
	      public void mouseClicked( MouseEvent e ) { 
	    	  clickCount++;
	    	  System.out.println(clickCount);
	      } 
	 }
	
	
}
