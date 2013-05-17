package characters;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import map.DungeonNavigator;
import map.OverWorldCamera;
import map.OverWorldMap;


import core.Board;
import core.FileLink;
import core.GameManager;
import core.GameObjects;


import java.awt.*; 
import java.awt.event.*; 
import java.awt.geom.AffineTransform;

public class Player extends JComponent implements Runnable, FileLink, GameObjects {
	
	static OverWorldMap overWorldMap;
	static DungeonNavigator dungeonNavigator;
	static OverWorldCamera overWorldCamera;
	
	
	Graphics2D g2d;
	BufferedImage playerMove, playerMoveBuff;
	
	//player coordinates
	volatile int x,y;
	int oldX, oldY;
	int absoluteX, absoluteY;
	
	//animation
	final int spriteGridX = 30*3;
	final int spriteGridY = 40*3;
	
	boolean moveUp, moveRight, moveDown, moveLeft;
	double punchCounter = 1;
	boolean punchNow, block;
	int lastDirection = 5;
	int newDirection;
	int moveStep = 0;
	double interStep = 0.1;
	
	//movement
	int dx, dy;
	double SPEED = 1;
	final double SPEEDUP = 2;
	double tmpSpeed = SPEED;
	boolean moveable = true;

	//interaction
	Rectangle attackBound;
	int life = 3;
	int coins = 0;
	boolean visible = true;
	volatile boolean loseLife = false;
	int loseLifeType = 0;
	//loseLifeTyp: 0 = bouncing off short, 1 = bouncing off long, 2 = respawn at entry point
	
	//worldmapnavigation
	Rectangle boundN,boundE,boundS,boundW,boundDirection;
	
	
	public Player(){
		System.err.println("->Player");
		
		try {
			playerMove = ImageIO.read(player1_move);
			playerMoveBuff = new BufferedImage(1260, 960, BufferedImage.TYPE_INT_ARGB);
			} catch (IOException e) {
				System.err.println("file not found");
				System.exit(0);
			}
	}
	
	public Player(OverWorldMap overWorldMap,DungeonNavigator dungeonNavigator){
		this.overWorldMap = overWorldMap;
		this.dungeonNavigator = dungeonNavigator;
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		//System.out.println("Player.paintComponents");
		if(visible)
			g2d.drawImage(playerMoveBuff,x,y,this);
	}
	
	public void run(){
		
		if (GameManager.printMsg)
			System.out.println("Player.run");

		if (life <= 0)
			GameManager.gameOver = true;

		if (!block || moveable)
			move();
		
		if(!moveable){
			moveUp = moveLeft = moveDown = moveRight = false;
		}
		
		paintPlayer();
		
		if(punchCounter < 1){
			punchCounter += 0.1;
			System.out.println("punchCounter: "+punchCounter); 
		}
			
	

	}
	
	
	private void move(){
		dx=dy=0;
		
		if (moveLeft) dx = -1;
		if (moveRight) dx = 1;
		if (moveUp) dy = -1;
		if (moveDown) dy = 1;
		
		
		//System.out.println("X: " + x + ", Y: " + y);

		double length = Math.sqrt(dx * dx + dy * dy);
		
		if (length != 0) {
			
		    dx *= tmpSpeed;
		    dy *= tmpSpeed;

		    //toogles movement sprites/animation
		    if (dx != 0 || dy != 0){
		    	if (moveStep >= 7 || newDirection != lastDirection || interStep > 8)
		    		interStep = 0;
		    		interStep += 0.08*tmpSpeed;
		    		moveStep = Math.round((int)interStep);
		    }
		}
		
		
		absoluteX += dx;
		absoluteY += dy;
		
			
    	//fixed Camera
    	if(!overWorldMap.getCameraStatus()){
    		x += dx;
        	y += dy;
    	}

 
		//moveable Camera
		if(overWorldMap.getCameraStatus()){
			if(absoluteY < 2065 || absoluteY > 0){
		    	overWorldMap.setCameraY(absoluteY);
		    } 
		    if(absoluteX < 1900|| absoluteX > 0){
				overWorldMap.setCameraX(absoluteX);
			} 
		    overWorldMap.alignCamera();
 		 }		
	

		setBounds();
		dx = dy = 0;
	} //move
	
	
	private void paintPlayer() {
		
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
		
		if (punchNow && punchCounter >= 1){
		
			System.out.println("punchCounter: "+punchCounter); 
			playerMoveBuff = playerMove.getSubimage((9+(int)punchCounter)*spriteGridX, (lastDirection-1)*spriteGridY, spriteGridX, spriteGridY);
			if (punchCounter < 3)
				punchCounter += 0.1;
			if(punchCounter >= 3) {
				punchCounter = -5;
				punchNow = false;
			}
		}
		
		if (block) {
			playerMoveBuff = playerMove.getSubimage(14*spriteGridX, 4*spriteGridY, spriteGridX, spriteGridY);
		}
		
		
		
		//System.out.println("X: " + x + ", Y: " + y);
		//g2d.drawImage(playerMoveBuff ,x ,y , this);
		
		//boundS
		

	}
	

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		GameManager.repaintNow = true;

			if (key == KeyEvent.VK_UP && moveable){
				moveUp = true;
			}
			if (key == KeyEvent.VK_RIGHT && moveable){
				moveRight = true;
			}
			if (key == KeyEvent.VK_DOWN && moveable){	
				moveDown = true;
			}
			if (key == KeyEvent.VK_LEFT && moveable){
				moveLeft = true;
			}
			
			if (key == KeyEvent.VK_D && moveable){
				punchNow = true;
			}

			
			
			
			if (key == KeyEvent.VK_SPACE){
				overWorldMap.setMoveFocus(true);
			}
			
			if (key == KeyEvent.VK_X){
				System.out.println("Player: "+x+","+y);
				System.out.println("Camera: "+overWorldMap.getCameraX()+","+overWorldMap.getCameraY());
				System.err.println("Absolut:"+absoluteX+","+absoluteY);
			}
				
			if (key == KeyEvent.VK_F){
				tmpSpeed = SPEED*SPEEDUP;
			}

	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		GameManager.repaintNow = true;
		
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
		

		if (key == KeyEvent.VK_D){
			
			if(punchCounter > 1){
				punchNow = false;
				punchCounter = -5;
			}
				
		}

		if (key == KeyEvent.VK_F){
			tmpSpeed = SPEED;
		}
		
		if (key == KeyEvent.VK_C){
			if(!overWorldMap.getCameraLock()){
				if(!overWorldMap.getCameraStatus()){
					System.out.println("OverWorld Camera on");
					overWorldMap.toogleCamera(true);
				} else {
					System.out.println("OverWorld Camera off");
					overWorldMap.toogleCamera(false);
				}
			} else
				System.err.println("Camera locked!");
			
			
				
		}
		
		if(key == KeyEvent.VK_Y){
			GameManager.printMsg = !GameManager.printMsg;
		}
		
		
		if (key == KeyEvent.VK_M){
			GameManager.menu = !GameManager.menu;
			
			//Board.menuThread = !Board.menuThread;
			//Board.ingameThread = !Board.ingameThread;
			
		}
		
		if (key == KeyEvent.VK_N){
			GameManager.ingame = !GameManager.ingame;
			
			//Board.menuThread = !Board.menuThread;
			//Board.ingameThread = !Board.ingameThread;

		}
		
		
		if (key == KeyEvent.VK_B){
			GameManager.paintBounds = !GameManager.paintBounds;
		}
		if (key == KeyEvent.VK_SPACE){
			overWorldMap.setMoveFocus(false);
		}
		
	}
	
	public void setBounds(){
		
			boundN = new Rectangle (x+10,y+10,60,10);
			boundW = new Rectangle (x+10,y+10,10,90);
			boundS = new Rectangle (x+18,y+90,44,10);
			boundE = new Rectangle (x+60,y+10,10,90);
			
			switch(lastDirection){
			case 1:	boundDirection = boundN;
					break;
			case 3:	boundDirection = boundE;
					break;
			case 5:	boundDirection = boundS;
					break;
			case 7:	boundDirection = boundW;
					break;
			}
				
		
	
	}
	
	public void setAttackBounds(){
		attackBound = new Rectangle(0,0,0,0);
		
		if(punchNow && punchCounter >= 1.3 && lastDirection == 1){
			attackBound = new Rectangle(x+30,y,40,50);
		}
		if(punchNow && punchCounter >= 1.3 && lastDirection == 3){
			attackBound = new Rectangle(x+50,y+10,40,50);
		}
		if(punchNow && punchCounter >= 1.3 && lastDirection == 5){
			attackBound = new Rectangle(x,y+60,40,50);
		}
		if(punchNow && punchCounter >= 1.3 && lastDirection == 7){
			attackBound = new Rectangle(x,y+10,40,50);
		}
		
		if(punchNow && punchCounter >= 1.3 && lastDirection == 2){
			attackBound = new Rectangle(x+40,y,40,70);
		}
		if(punchNow && punchCounter >= 1.3 && lastDirection == 4){
			attackBound = new Rectangle(x+40,y+40,40,70);
		}
		if(punchNow && punchCounter >= 1.3 && lastDirection == 6){
			attackBound = new Rectangle(x,y+25,40,70);
		}
		if(punchNow && punchCounter >= 1.3 && lastDirection == 8){
			attackBound = new Rectangle(x,y,40,70);
		}
	}
	
	public BufferedImage getImage(){
		return playerMoveBuff;
	}
	
	public void resetPosition(){
		x = oldX;
		y = oldY;
	}
	
	
	//get,set variables
	public int getX(){return x;}
	public int getY(){return y;}
	public int getAbsoluteX(){return absoluteX;}
	public int getAbsoluteY(){return absoluteY;}
	public int getLastDirection(){return lastDirection;}
	public int getLife(){return life;}
	public int getCoins(){return coins;}
	public boolean getVisible(){return visible;}
	public boolean getMoveable(){return moveable;}
	public int getLoseLifeType(){return loseLifeType;}
	public boolean getLoseLife(){return loseLife;}
	
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public void setOldX(int oldX){this.oldX = oldX;}
	public void setOldY(int oldY){this.oldY = oldY;}
	public void setAbsoluteX(int absoluteX){this.absoluteX = absoluteX;}
	public void setAbsoluteY(int absoluteY){this.absoluteY = absoluteY;}
	public void setLastDirection(int lastDirection){this.lastDirection = lastDirection;}
	public void setLife(int life){this.life = life;}
	public void setCoins(int coins){this.coins = coins;}
	public void setVisible(boolean visible){this.visible = visible;}
	public void setMoveable(boolean moveable){this.moveable = moveable;}
	public void setLoseLifeType(int loseLifeType){this.loseLifeType = loseLifeType;}
	public void setLoseLife(boolean loseLife){this.loseLife = loseLife;}
	
	
	//get Bounds
	public Rectangle getBoundN(){return boundN;}
	public Rectangle getBoundE(){return boundE;}
	public Rectangle getBoundS(){return boundS;}
	public Rectangle getBoundW(){return boundW;}
	public Rectangle getBoundDirection(){return boundDirection;}
	public Rectangle getAttackBound(){return attackBound;}
}
