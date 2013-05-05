import java.awt.Image;
import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.awt.*; 
import java.awt.event.*; 

public final class Player extends JComponent implements Runnable, FileLink {
	Graphics2D g2d;
	BufferedImage playerMove, playerMoveBuff;
	
	//player coordinates
	static int x,y;
	
	//animation
	static final int spriteGridX = 30*3;
	static final int spriteGridY = 40*3;
	
	static boolean moveUp, moveRight, moveDown, moveLeft;
	static int lastDirection = 5;
	static int newDirection;
	static int moveStep = 0;
	static double interStep = 0.1;
	
	//movement
	static double dx, dy;
	static final double SPEED = 1;
	static final double SPEEDUP = 2.5;
	double tmpSpeed = SPEED;
	final static Rectangle rectBOARD = new Rectangle(-200,-200,1210,1030);
	
	//worldmapnavigation
	static Rectangle playerBoundN,playerBoundE,playerBoundS,playerBoundW;
	
	
	public Player(){
		System.err.println("->Player");
		
		try {
			playerMove = ImageIO.read(player1_move);
			playerMoveBuff = new BufferedImage(1260, 960, BufferedImage.TYPE_INT_ARGB);
			} catch (IOException e) {
				System.err.println("file not found");
				System.exit(0);
			}
		x = 10;
		y = 20;
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		//System.out.println("Player.paintComponents");
	}
	
	public void run(){
		if (Board.printMsg)
			System.out.println("Player.run");
		move();
		paintPlayer();
	}
	
	
	private void move(){
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
			    
			    x += dx;
		    	y += dy;
			   
			    //checks movement in board
			    if (x < 810 && x > 0 && y < 630 && y > 0) {
			    	System.out.println("X: " + x + ", Y: " + y);
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
			}
			dx = dy = 0;
			
			setBounds();
			
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
		
		//System.out.println("X: " + x + ", Y: " + y);
		//g2d.drawImage(playerMoveBuff ,x ,y , this);
		
		//playerBounds
		

	}
	
	

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		Board.repaintNow = true;

			if (key == KeyEvent.VK_UP){
				moveUp = true;
			}
			if (key == KeyEvent.VK_RIGHT){
				moveRight = true;
			}
			if (key == KeyEvent.VK_DOWN){	
				moveDown = true;
			}
			if (key == KeyEvent.VK_LEFT){
				moveLeft = true;
			}
			
			
			if (key == KeyEvent.VK_F){
				tmpSpeed = SPEED*SPEEDUP;
			}

	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		Board.repaintNow = true;
		
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
		
	}
	
	public void setBounds(){
	playerBoundS = new Rectangle (x+10,y+10,60,10);
	playerBoundE = new Rectangle (x+10,y+10,10,90);
	playerBoundN = new Rectangle (x+10,y+90,60,10);
	playerBoundW = new Rectangle (x+60,y+10,10,90);
	}
	
	public BufferedImage getImage(){
		return playerMoveBuff;
	}
	
}
