package core;

import game.objects.DrawableObject;
import game.objects.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import map.DungeonNavigator;
import map.OverWorldNavigator;


public class Board extends JPanel implements Runnable{
	private final int DELAY = 20;
	
	private static Board board = null;
	private Graphics2D g2d;
	private static Player player1;
	private Graphics g;
	
	//add Values, sort Values, paint according Values
	private static volatile ArrayList<DrawableObject> drawableList = new ArrayList<DrawableObject>();
	private static volatile ArrayList<DrawableObject> mapObjectList = new ArrayList<DrawableObject>();
	private volatile BufferedImage topMap;
	
	
	public final static Rectangle screenN = new Rectangle(0,90,810,1);
	public final static Rectangle screenE = new Rectangle(720,0,1,630);
	public final static Rectangle screenS = new Rectangle(0,540,810,1);
	public final static Rectangle screenW = new Rectangle(90,0,1,630);
	
	
	private Board(){
		System.err.println("construct Board");
		player1 = Player.getInstance();

		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(new Color(0, 0, 0, 0));
		
		
		this.addKeyListener(new KAdapter());
		this.addMouseListener(new MAdapter());		

	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.g = g;
		g2d = (Graphics2D) g;
		
		g2d.clearRect(0, 0, 810, 630);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		if(GameManager.getInstance().getMenu()){
			Menu.getInstance().paintComponents(g2d);
		}
		
		
		if(GameManager.getInstance().getIngame() && !GameManager.getInstance().getMenu() && GameManager.mapLoaded){
			
			//if(GameManager.mapLoaded){
			if(GameManager.overWorld)
				OverWorldNavigator.getInstance().draw(0, g2d);
			
			if(GameManager.dungeon){
				DungeonNavigator.getInstance().draw(0, g2d);
			}
			
			for(int index = 0; index < mapObjectList.size(); index ++){
				mapObjectList.get(index).paintComponents(g2d);
			}
			
			if(GameManager.dungeon){
				DungeonNavigator.getInstance().draw(1, g2d);
			}
		
			//sort objects, specified in sortYOrder(), to draw them according to their Y-Value, respectivly in the proper Z-Order
			ArrayList<DrawableObject> drawablePaint = sortDrawable();
			for(int i = 0; i < drawableList.size(); i++){
				drawablePaint.get(i).paintComponents(g2d);
			}
			
			
			
			if(GameManager.overWorld && topMap != null)
				g2d.drawImage(topMap, OverWorldNavigator.getInstance().getXCoordinate(), OverWorldNavigator.getInstance().getYCoordinate(), this);
			
			if(PlayerInterface.getInstance().getIniStatus());
				PlayerInterface.getInstance().draw(g2d);
			
			if(GameManager.ingameMenu)
				IngameMenu.getInstance().draw(g2d);
			
		}
		
		
		
		
	}
	
	public synchronized void addDrawable(DrawableObject drawableElement){
		
		double size = drawableList.size();
		int halfSize = (int)(size/2);
		
		if(drawableElement != null){
			drawableList.add(halfSize, drawableElement);
		}
		else
			System.err.println("Board: Can't add NullElements.");
		sortDrawable();
	
	}
	
	public synchronized void addMapObject(DrawableObject mapObject){
		mapObjectList.add(mapObject);
	}
	
	public static synchronized void updateMapObjectList(){
		for(int index = 0; index < mapObjectList.size(); index++){
			if(!mapObjectList.get(index).getAlive())
				mapObjectList.remove(index);
		}
	}
	
	public static synchronized void updateDrawableList(){
		for(int index = 0; index < drawableList.size(); index++){
			if(!drawableList.get(index).getAlive())
				drawableList.remove(index);
		}
	}
	
	public void setTopMap(boolean setImage,File topMapFile){
		
		try {
			if(setImage)
				topMap = ImageIO.read(topMapFile);
			else
				topMap = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.topMap = topMap;
	}
	
	private ArrayList<DrawableObject> sortDrawable(){

		for(int i = 0; i < drawableList.size()-1; i++){
			if(!drawableList.get(i).getAlive()){
				drawableList.remove(i);
				GameManager.updateGameObject();
			}
		}
		
		int size = drawableList.size();

		do{
			int counter = 1;
			for(int i = 0; i < size-1; i++){

				if(drawableList.get(i).getY() > drawableList.get(i+1).getY()){
					DrawableObject tmpDrawable = drawableList.get(i);
			
					drawableList.set(i, drawableList.get(i+1));
					drawableList.set(i+1, tmpDrawable);
					
					counter = i+1;
				}//end if
				
			}//end for
			size = counter;
		} while(size > 1);
		
		//System.out.println("yvalueIndex0: "+YValueList.get(0));
		return drawableList;
	}
	
	public void run(){
		long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            repaint();
            //sortDrawable();
            
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }
	
	public void setColor(Color color){
		g2d.setColor(color);
	}
	
	public Graphics2D getGraphics2D(){
		return g2d;
	}
	
	public static Board getInstance(){
		if(board == null)
			board = new Board();
		return board;
	}

	
	private class KAdapter extends KeyAdapter{
	
		public void keyPressed(KeyEvent kE){
			
			if(GameManager.getInstance().getIngame() && !GameManager.getInstance().getMenu())
				player1.keyPressed(kE);
			
			if(!GameManager.getInstance().getIngame())
				Menu.getInstance().keyPressed(kE);
			
		}
		public void keyReleased(KeyEvent kE){
			
			if(GameManager.getInstance().getIngame() && !GameManager.getInstance().getMenu())
				player1.keyReleased(kE);
			
			if(!GameManager.getInstance().getIngame())
				Menu.getInstance().keyReleased(kE);
		}
		
	}
	
	private class MAdapter extends MouseAdapter{ 

	      public void mouseClicked(MouseEvent mE) {
	    	 System.out.println("click");
	    	
	    	 if(!GameManager.getInstance().getIngame())
	    		 Menu.getInstance().mouseClicked(mE);
	    	 
	      } 
	 }
	


}
