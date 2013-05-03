
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
/*
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.*;

import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener, GridInterface, RectMapGrid{
	
	//class instances
	private Thread playerThread;
	private Player player;
	public static ScrollMapPainter scrollMapPainter;
	private MapMaker mapMaker;
	
	Timer timer;
	Graphics2D g2d;
	
	//firstTimeSet
	static boolean initialTimer;
	
	
	public Board(){
		
		addKeyListener(new TAdapter());

		
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		
		scrollMapPainter = new ScrollMapPainter();
		mapMaker = new MapMaker();
		player = new Player(B2.x, B2.y);
		
		
		timer = new Timer(5, this);
		initialTimer = true;
		
		//timer.start();
		//playerThread = new Thread(new PlayerThread());
		
	}
	
	public void dummyMethod(){
		System.out.println("dummy");
	}
	
	public void paint (Graphics g) {
		
		super.paint(g);
		g2d = (Graphics2D) g;

		player.paintComponents(g2d);
		mapMaker.paintComponents(g2d);
		scrollMapPainter.paintComponents(g2d);
		

		startGame();
	
	g.dispose();
	
	}
	
	public void startGame() {
		timer.start();
		
		//if(OverWorldMap.newMap && !MapMaker.scrollReady)
		//	scrollMapPainter.iniMapMaker();
		mapMaker.iniMapMaker();
		player.paintPlayer();
		
		
		
		//TODO Thread for player
		/*
		if (initialTimer){
			new Thread(player).start();
			initialTimer = false;
		}
		*/
		
	}

	public void actionPerformed(ActionEvent e){
		player.move();
		//repaint(player.x - B2.x/2, player.y - B2.y/2, C3.x, C3.y);
		
		//if(OverWorldMap.newMap || !MapMaker.scrollReady)
			repaint();
	}
	
	private class TAdapter extends KeyAdapter {
		
		public void keyReleased(KeyEvent e){
			player.keyReleased(e);
			repaint();
		}
		
		public void keyPressed(KeyEvent e){
			player.keyPressed(e);
		}
	}
	
	
	public void hallo(){
		System.out.println("hallo von board");
	}
	
	/*
	//TODO player thread
	private static class PlayerThread implements Runnable {
		
		Graphics2D g2d;
		Player player = new Player(B2.x, B2.y);
		
		public void run(){
			player.paintComponents(g2d);
			System.out.println("Run Player");
			player.paintPlayer();
		}
		
		public void paint (Graphics g) {
			g2d = (Graphics2D) g;

		}
	}
	*/
}