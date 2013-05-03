
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import java.io.*;
import java.net.URL;

import javax.swing.JComponent;
import javax.imageio.ImageIO;


public class Player extends JComponent implements Runnable {

	//objects
	Graphics2D g2d;
	BufferedImage playerMove, playerMoveBuff;
	MapMaker mapMaker;
	OverWorldMap overWorldMap;
	static Player Player = new Player(false);

	//player coordinates
	static int x = 0;
	static int y = 0;
	static int xEnterNewMap, yEnterNewMap;
	
	//animation
	static final int spriteGridX = 30*3;
	static final int spriteGridY = 40*3;
	
	static boolean moveUp, moveRight, moveDown, moveLeft;
	static int lastDirection = 5;
	static int newDirection;
	static int moveStep = 0;
	static double interStep = 0.1;
	
	//movement
	static final double SPEED = 4;
	static final double SPEEDUP = 2.5;
	double tmpSpeed = SPEED;
	final static Rectangle rectBOARD = new Rectangle(-200,-200,1210,1030);
	
	//worldnavigation
	static Rectangle playerBoundN,playerBoundE,playerBoundS,playerBoundW;
	static boolean enterNewMap = false;
	static double dx, dy;
	
	
	public Player(int x, int y) {
		
		overWorldMap = new OverWorldMap(false);
		mapMaker = new MapMaker();
		try {
		URL url = this.getClass().getResource(FileLink.player1_move);
		playerMove = ImageIO.read(url);
		playerMoveBuff = new BufferedImage(1260, 960, BufferedImage.TYPE_INT_ARGB);
		} catch (IOException e) {
			
		}
		this.x = x;
		this.y = x;
	}
	
	public Player(boolean dummyConstructor){
		//Instance for OverWorldMap
	}
	
	
	//TODO PlayerThread
	public void run() {
		System.out.println("player Thread start");
		paintPlayer();
	}

	public void move(){
		//System.out.println("CenterX: " + getCenterX() + ", CenterY: " + getCenterY());
		
			dx=dy=0;
			if (moveLeft) dx -= 1;
			if (moveRight) dx += 1;
			if (moveUp) dy -= 1;
			if (moveDown) dy += 1;

			double length = Math.sqrt(dx * dx + dy * dy);
			if (length != 0) {
				
			    dx = dx/length;
			    dy = dy/length;

			    dx *= tmpSpeed;
			    dy *= tmpSpeed;
			   
			    //checks movement in board
			    if (rectBOARD.contains(playerBoundN)||rectBOARD.contains(playerBoundE)
			    		||rectBOARD.contains(playerBoundS)||rectBOARD.contains(playerBoundW)) {
			    	x += dx;
			    	y += dy;
			    } else {
			    	System.err.println("Illegal movement");
			    	x = 400;
			    	y = 300;
			    }
			  
			  
			    //toogles movement sprites/animation
			    if (dx != 0 || dy != 0){
			    	if (moveStep >= 7 || newDirection != lastDirection || interStep > 8)
			    		interStep = 0;
			    	interStep += 0.08*tmpSpeed;
			    	moveStep = Math.round((int)interStep);
				
			    }
			   
			    //System.out.println(interStep);
			}
			dx = dy = 0;
			
			setBounds();
			//System.out.println(getBounds().intersects(overWorldMap.getRectSouth()));
			
			
			System.out.println(overWorldMap.newMap);
		
			
			if(!overWorldMap.newMap && MapMaker.scrollReady){
			
				if (playerBoundN.intersects(overWorldMap.rectNorth)){
					overWorldMap.oldWorldMapX = overWorldMap.worldMapX;
					overWorldMap.oldWorldMapY = overWorldMap.worldMapY;
					overWorldMap.scrollData(overWorldMap.worldMapX,overWorldMap.worldMapY-1,true);
					//setPosition(getX(), 630-40*3);
					OverWorldMap.newMap = true;
					OverWorldMap.nextMapY = -1;
					xEnterNewMap = x;
					yEnterNewMap = y;
				}	
				if (playerBoundE.intersects(overWorldMap.rectEast)){
					overWorldMap.oldWorldMapX = overWorldMap.worldMapX;
					overWorldMap.oldWorldMapY = overWorldMap.worldMapY;
					overWorldMap.scrollData(overWorldMap.worldMapX+1,overWorldMap.worldMapY,true);
					//setPosition(0, getY());
					OverWorldMap.newMap = true;
					OverWorldMap.nextMapX = 1;
					xEnterNewMap = x;
					yEnterNewMap = y;
				}
				if (playerBoundS.intersects(overWorldMap.rectSouth)){
					overWorldMap.oldWorldMapX = overWorldMap.worldMapX;
					overWorldMap.oldWorldMapY = overWorldMap.worldMapY;
					overWorldMap.scrollData(overWorldMap.worldMapX,overWorldMap.worldMapY+1,true);
					//setPosition(getX(),0);
					OverWorldMap.newMap = true;
					OverWorldMap.nextMapY = 1;
					xEnterNewMap = x;
					yEnterNewMap = y;
				}	
				if (playerBoundW.intersects(overWorldMap.rectWest)){
					overWorldMap.oldWorldMapX = overWorldMap.worldMapX;
					overWorldMap.oldWorldMapY = overWorldMap.worldMapY;
					overWorldMap.scrollData(overWorldMap.worldMapX-1,overWorldMap.worldMapY,true);
					//setPosition(810-30*3, getY());
					OverWorldMap.newMap = true;
					OverWorldMap.nextMapX = -1;
					xEnterNewMap = x;
					yEnterNewMap = y;
				}
			}
			
				
			
			
		
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if (MapMaker.scrollReady){
			if (key == KeyEvent.VK_UP){
				moveUp = true;	
				OverWorldMap.newMap = false;
			}
			if (key == KeyEvent.VK_RIGHT){
				moveRight = true;
				OverWorldMap.newMap = false;
			}

			if (key == KeyEvent.VK_DOWN){	
				moveDown = true;
				OverWorldMap.newMap = false;
			}
			if (key == KeyEvent.VK_LEFT){
				moveLeft = true;
				OverWorldMap.newMap = false;
			}
			
			
			if (key == KeyEvent.VK_F){
				tmpSpeed = SPEED*SPEEDUP;
			}
		}
		
	

	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP){
			moveUp = false;
			moveStep = 0;
		}
		if (key == KeyEvent.VK_RIGHT){
			moveRight = false;
			moveStep = 0;
		}
		if (key == KeyEvent.VK_DOWN){
			moveDown = false;
			moveStep = 0;
		}	
		if (key == KeyEvent.VK_LEFT){
			moveLeft = false;
			moveStep = 0;
		}
		
		
		if (key == KeyEvent.VK_F){
			tmpSpeed = SPEED;
		}
		if (key == KeyEvent.VK_SPACE){
			System.out.println("Hit Space");
		}
		
		//temp worldMapNavigation ASWD
		if (key == KeyEvent.VK_W && mapMaker.getScrollStatus()){
			OverWorldMap.scrollMap(0,-1);
			MapMaker.scrollReady = true;
		}
		if (key == KeyEvent.VK_D && mapMaker.getScrollStatus()){
			OverWorldMap.scrollMap(1,0);
			MapMaker.scrollReady = true;
		}
		if (key == KeyEvent.VK_S && mapMaker.getScrollStatus()){
			System.out.println("MapScroll down");
			OverWorldMap.scrollMap(0, 1);
			MapMaker.scrollReady = true;
		}
		if (key == KeyEvent.VK_A && mapMaker.getScrollStatus()){
			OverWorldMap.scrollMap(-1,0);
			MapMaker.scrollReady = true;
		}	
	}
	

	public void paintComponents(Graphics g) {
		g2d = (Graphics2D) g;
	}
	
	//get subimages for animated movement
	public void paintPlayer() {
		if ( moveUp == true &&  moveRight != true && moveDown != true &&moveLeft != true || lastDirection == 1){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 0*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 1;
			newDirection = lastDirection;
		}
		if ( moveUp != true &&  moveRight == true && moveDown != true &&moveLeft != true || lastDirection == 3){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 2*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 3;
			newDirection = lastDirection;
		}					
		if ( moveUp != true &&  moveRight != true && moveDown == true &&moveLeft != true|| lastDirection == 5){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 4*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 5;
			newDirection = lastDirection;
		}		
		if ( moveUp != true &&  moveRight != true && moveDown != true &&moveLeft == true || lastDirection == 7){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 6*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 7;
			newDirection = lastDirection;
		}
		
		
		if ( moveUp == true &&  moveRight == true && moveDown != true &&moveLeft != true || lastDirection == 2){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 1*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 2;
			newDirection = lastDirection;
		}
		if ( moveUp != true &&  moveRight == true && moveDown == true &&moveLeft != true || lastDirection == 4){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 3*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 4;
			newDirection = lastDirection;
		}
		if ( moveUp != true &&  moveRight != true && moveDown == true &&moveLeft == true|| lastDirection == 6){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 5*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 6;
			newDirection = lastDirection;
		}
		if ( moveUp == true &&  moveRight != true && moveDown != true &&moveLeft == true || lastDirection == 8){
			if (newDirection != lastDirection || moveStep >= 8)
				moveStep = 0;
			playerMoveBuff = playerMove.getSubimage(moveStep*spriteGridX, 7*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 8;
			newDirection = lastDirection;
		}
		
		g2d.drawImage(playerMoveBuff ,x ,y , this);
		
		//playerBounds
		g2d.setColor(Color.red);
        g2d.drawRect(x+10,y+10,60,10);
        g2d.drawRect(x+10,y+90,60,10);
        g2d.drawRect(x+10,y+10,10,90);
        g2d.drawRect(x+60,y+10,10,90);
        
        //OverWorldNavigation Bounds
        g2d.setColor(Color.blue);
        g2d.draw(OverWorldMap.rectNorth);
        g2d.draw(OverWorldMap.rectEast);
        g2d.draw(OverWorldMap.rectSouth);
        g2d.draw(OverWorldMap.rectWest);
	}
	
	//worldmapnavigation and bounds
	public void setBounds(){playerBoundS = new Rectangle (x+10,y+10,60,10);
							playerBoundE = new Rectangle (x+10,y+10,10,90);
							playerBoundN = new Rectangle (x+10,y+90,60,10);
							playerBoundW = new Rectangle (x+60,y+10,10,90);}
	
	public void setPosition(int x, int y){this.x = x;this.y = y;}
	
	
	public void hallo(){System.out.println("hallo von player");}
	


}
