package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JComponent;

import core.Board;
import core.GameManager;

abstract class Map{
	
	private Graphics2D g2d;
	private volatile int x = 0;
	private volatile int y = 0;
	private int width;
	private int height;
	

	private BufferedImage mapImage, mapImageWall;
	private volatile boolean visible = true;
	private volatile boolean alive;
	private boolean dungeon;
	private boolean overWorld;
	private int ID;
	
	
	private ArrayList<Rectangle> navigationBoundPaint = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> wallBoundNPaint = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> wallBoundSPaint = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> wallBoundPaint = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> mapObjectBoundPaint = new ArrayList<Rectangle>();
	
	protected final static Rectangle NULLRECT = new Rectangle(0,0,0,0);
	
	
	
	protected Map(){
		
	}
	
	
	public void draw(int layer, Graphics2D g2d){
		
		Camera cam = Camera.getInstance();
		//System.out.println("Camera_"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
	
		if(mapImage != null && visible && layer == 0)
			g2d.drawImage(mapImage, x, y, Board.getInstance());
		
		if(mapImage != null && visible && layer == 1)
			g2d.drawImage(mapImageWall, x, y, Board.getInstance());
		
		if(wallBoundNPaint.size() > 0 && GameManager.showBounds){
			g2d.setColor(Color.BLUE);
			for(int index = 0; index < wallBoundNPaint.size(); index++){
				Rectangle tmp = wallBoundNPaint.get(index);
				g2d.drawRect(tmp.x-cam.getX(),tmp.y-cam.getY(),45,45);
			}
		}
		
		if(wallBoundPaint.size() > 0 && GameManager.showBounds){
			g2d.setColor(Color.RED);
			for(int index = 0; index < wallBoundPaint.size(); index++){
				Rectangle tmp = wallBoundPaint.get(index);
				g2d.drawRect(tmp.x-cam.getX(),tmp.y-cam.getY(),45,45);
			}
		}
		
	
		
		if(wallBoundSPaint.size() > 0 && GameManager.showBounds){
			g2d.setColor(Color.CYAN);
			for(int index = 0; index < wallBoundSPaint.size(); index++){
				Rectangle tmp = wallBoundSPaint.get(index);
				g2d.drawRect(tmp.x-cam.getX(),tmp.y-cam.getY(),45,45);
			}
		}
		
		if(mapObjectBoundPaint.size() > 0 && GameManager.showBounds){
			g2d.setColor(Color.ORANGE);
			for(int index = 0; index < mapObjectBoundPaint.size(); index++){
				Rectangle tmp = mapObjectBoundPaint.get(index);
				g2d.drawRect(tmp.x-cam.getX(),tmp.y-cam.getY(),45,45);
			}
		}
		
		if(navigationBoundPaint.size() > 0 && GameManager.showBounds){
			
			
			g2d.setColor(Color.YELLOW);
			
			for(int index = 0; index < navigationBoundPaint.size(); index++){
				Rectangle tmp = navigationBoundPaint.get(index);
				
				g2d.drawRect(tmp.x-cam.getX(),tmp.y-cam.getY(),tmp.width,tmp.height);
				//System.out.println("Rect@"+index+" with: "+(tmp.x-cam.getX())+"x"+(tmp.y-cam.getY())+", "+"45x45");
				//enterDungeonBound = new Rectangle(110-overWorldCamera.getCameraX(), 0-overWorldCamera.getCameraY(),60,90 );
			}
		}
		
		
	}
	
	protected void resetMap(){
		
		navigationBoundPaint.clear();
		wallBoundNPaint.clear();
		wallBoundSPaint.clear();
		wallBoundPaint.clear();
		mapObjectBoundPaint.clear();
		
		x = y = width = height = 0;
		mapImage = null;

	}
	
	protected void clearPaintBounds(){
		navigationBoundPaint.clear();
		wallBoundNPaint.clear();
		wallBoundSPaint.clear();
		wallBoundPaint.clear();
		mapObjectBoundPaint.clear();
	}
	
	protected void addWallBoundNPaint(Rectangle element){
		wallBoundNPaint.add(element);
	}
	
	protected void addWallBoundSPaint(Rectangle element){
		wallBoundSPaint.add(element);
	}
	
	protected void addWallBoundPaint(Rectangle element){
		wallBoundPaint.add(element);
	}
	
	protected void addNavigationBoundPaint(Rectangle element){
		navigationBoundPaint.add(element);
	}
	
	protected void addMapObjectBoundPaint(Rectangle rectangle){
		mapObjectBoundPaint.add(rectangle);
	}
	
	protected void setMapImage(BufferedImage mapImage){
		this.mapImage = mapImage;
	}
	
	protected void setMapImageWall(BufferedImage mapImageWall){
		this.mapImageWall = mapImageWall;
	}
	
	
	protected void setDungeon(boolean dungeon){
		this.dungeon = dungeon;
	}
	
	protected void setOverWorld(boolean overWorld){
		this.overWorld = overWorld;
	}
	
	public BufferedImage getMapImage(){return mapImage;}
	public synchronized int getXCoordinate(){return x;}
	public synchronized int getYCoordinate(){return y;}
	public int getWidthMap(){return width;}
	public int getHeightMap(){return height;}
	protected boolean getAlive(){return alive;}
	protected boolean getVisible(){return visible;}
	public int getID(){return ID;}
	//
	public synchronized void setXCoordinate(int x){this.x = x;}
	public synchronized void setYCoordinate(int y){this.y = y;}
	protected void setWidthMap(int width){this.width = width; System.out.println("Width:"+width);}
	protected void setHeightMap(int height){this.height = height;System.out.println("Height:"+height);}
	protected void setAlive(boolean alive){this.alive = alive;}
	protected void setVisibleMap(boolean visible){this.visible = visible;}
	protected void setID(int ID){this.ID = ID;}
	

}
