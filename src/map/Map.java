package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JComponent;

import core.GameManager;

abstract class Map extends JComponent{

	private Graphics2D g2d;
	private volatile int x = 0;
	private volatile int y = 0;
	private int width;
	private int height;
	

	private BufferedImage mapImage;
	private volatile boolean visible = true;
	private volatile boolean alive;
	private boolean dungeon;
	private boolean overWorld;
	
	
	private ArrayList<Rectangle> navigationBoundPaint = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> wallBoundNPaint = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> wallBoundSPaint = new ArrayList<Rectangle>();
	
	protected Map(){
		
	}
	
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		
		Camera cam = Camera.getInstance();
		//System.out.println("Camera_"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
	
		if(mapImage != null && visible)
			g2d.drawImage(mapImage, x, y, this);
		
		
		if(wallBoundNPaint.size() > 0 && GameManager.showBounds){
			g2d.setColor(Color.BLUE);
			for(int index = 0; index < wallBoundNPaint.size(); index++){
				Rectangle tmp = wallBoundNPaint.get(index);
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
		
		if(navigationBoundPaint.size() > 0 && GameManager.showBounds){
			
			
			g2d.setColor(Color.CYAN);
			
			for(int index = 0; index < navigationBoundPaint.size(); index++){
				Rectangle tmp = navigationBoundPaint.get(index);
				
				g2d.drawRect(tmp.x-cam.getX(),tmp.y-cam.getY(),tmp.width,tmp.height);
				//System.out.println("Rect@"+index+" with: "+(tmp.x-cam.getX())+"x"+(tmp.y-cam.getY())+", "+"45x45");
				//enterDungeonBound = new Rectangle(110-overWorldCamera.getCameraX(), 0-overWorldCamera.getCameraY(),60,90 );
			}
		}
		
	
		
		
		/*
		else
			System.err.println("MapImage not initialized");
		
		*/
		
		/*
		//paint navigation & objectBounds
		if(dungeon && GameManager.showBounds){
			
			for(int orientation = 0; orientation < 5; orientation++){
				if(orientation == 0)
					g2d.setColor(Color.CYAN);
				else
					g2d.setColor(Color.GREEN);
				int navigationBoundSizeTmp = navigationBoundSize[xMap][yMap][orientation];
				for(int dimension = 0; dimension < navigationBoundSizeTmp; dimension++){
					g2d.draw(navigationBound[xMap][yMap][orientation][dimension]);
				}
			}
			
			
			for(int layer = 0; layer < 7; layer++){
				
				if(layer == 2 || layer == 3)
					g2d.setColor(Color.BLUE);
				
				if(layer == 4)
					g2d.setColor(Color.RED);
				
				if(layer == 5)
					g2d.setColor(Color.YELLOW);
				
				for(int orientation = 0; orientation < 13; orientation++){
					
					for(int yTile = 0; yTile < 7; yTile++){
						for(int xTile = 0; xTile < 9; xTile++){
							for(int element = 0; element < 3; element++){
								//objectBound[mapRowX][mapRowY][7][13][9][7][3];
								g2d.draw(objectBound[xMap][yMap][layer][orientation][xTile][yTile][element]);
							}
						}	
					}	
				}
			}
			
		}//if dungeon
		
		
		*/
		
		
		
	}
	
	protected void clearArrayList(){
		navigationBoundPaint.clear();
		wallBoundNPaint.clear();
		wallBoundSPaint.clear();
	}
	
	protected void addWallBoundNPaint(Rectangle element){
		wallBoundNPaint.add(element);
	}
	
	protected void addWallBoundSPaint(Rectangle element){
		wallBoundSPaint.add(element);
	}
	
	protected void addNavigationBoundPaint(Rectangle element){
		navigationBoundPaint.add(element);
	}
	
	/*
	protected ArrayList<Rectangle> getWallBoundPaint(){
		return wallBoundPaint;
	}
	*/
	protected void setMapImage(BufferedImage mapImage){
		this.mapImage = mapImage;
	}
	
	
	protected void setDungeon(boolean dungeon){
		this.dungeon = dungeon;
	}
	
	protected void setOverWorld(boolean overWorld){
		this.overWorld = overWorld;
	}
	
	
	public synchronized int getXCoordinate(){return x;}
	public synchronized int getYCoordinate(){return y;}
	public int getWidthMap(){return width;}
	public int getHeightMap(){return height;}
	protected boolean getAlive(){return alive;}
	protected boolean getVisible(){return visible;}
	//
	public synchronized void setXCoordinate(int x){this.x = x;}
	public synchronized void setYCoordinate(int y){this.y = y;}
	protected void setWidthMap(int width){this.width = width; System.out.println("Width:"+width);}
	protected void setHeightMap(int height){this.height = height;System.out.println("Height:"+height);}
	protected void setAlive(boolean alive){this.alive = alive;}
	protected void setVisibleMap(boolean visible){this.visible = visible;}
	
	

}
