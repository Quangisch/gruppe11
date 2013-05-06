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
	static int absoluteX, absoluteY;
	
	//animation
	static final int spriteGridX = 30*3;
	static final int spriteGridY = 40*3;
	
	static boolean moveUp, moveRight, moveDown, moveLeft;
	static int lastDirection = 5;
	static int newDirection;
	static int moveStep = 0;
	static double interStep = 0.1;
	
	//movement
	static int dx, dy;
	static final double SPEED = 1;
	static final double SPEEDUP = 2;
	double tmpSpeed = SPEED;

	
	//worldmapnavigation
	static Rectangle BoundN,BoundE,BoundS,BoundW;
	
	
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
    	if(!Camera.cameraOn){
    		x += dx;
        	y += dy;
    	}
			
	
		//moveable Camera
		if(Camera.cameraOn){
			
		    if(absoluteX > 0){
		    	//x = 405-15*3;
				Camera.cameraX = absoluteX;
		    } else {
		    	Camera.cameraX = 0;
		    	x = absoluteX+405;
		    	
		    }

		    if(absoluteY > 0){
		    	
		    	//y = 315-20*3;
		    	Camera.cameraY = absoluteY;
			   	
		    } else {
		    	Camera.cameraY = 0;
		    	y = absoluteY+315;
		    	
		    }
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
		
		//System.out.println("X: " + x + ", Y: " + y);
		//g2d.drawImage(playerMoveBuff ,x ,y , this);
		
		//playerBounds
		

	}
	

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		Board.repaintNow = true;

			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
				moveUp = true;
			}
			if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
				moveRight = true;
			}
			if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S){	
				moveDown = true;
			}
			if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
				moveLeft = true;
			}
			
			
			if (key == KeyEvent.VK_SPACE){
				Camera.moveFocus = true;
			}
			
			if (key == KeyEvent.VK_X){
				System.out.println("Player: "+x+","+y);
				System.out.println("Camera: "+Camera.cameraX+","+Camera.cameraY);
				System.err.println("Absolut:"+absoluteX+","+absoluteY);
			}
				
			if (key == KeyEvent.VK_F){
				tmpSpeed = SPEED*SPEEDUP;
			}

	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		Board.repaintNow = true;
		
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
			moveUp = false;
			moveStep = 0;
		}
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
			moveRight = false;
			moveStep = 0;
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S){
			moveDown = false;
			moveStep = 0;
		}	
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
			moveLeft = false;
			moveStep = 0;
		}
		
		
		if (key == KeyEvent.VK_F){
			tmpSpeed = SPEED;
		}
		
		if (key == KeyEvent.VK_C){
			if(!Camera.cameraOn){
				System.out.println("OverWorld Camera on");
				Camera.toogleCamera(true);
			} else {
				System.out.println("OverWorld Camera off");
				Camera.toogleCamera(false);
			}
				
		}
		
		
		if (key == KeyEvent.VK_M){
			Board.ingame = !Board.ingame;
			if(!Board.ingame)
				System.out.println("menu");
			else
				System.out.println("ingame");
		}
		if (key == KeyEvent.VK_B){
			Board.paintBounds = !Board.paintBounds;
		}
		if (key == KeyEvent.VK_SPACE){
			Camera.moveFocus = false;
		}
		
	}
	
	public void setBounds(){
	BoundS = new Rectangle (x+10,y+10,60,10);
	BoundE = new Rectangle (x+10,y+10,10,90);
	BoundN = new Rectangle (x+10,y+90,60,10);
	BoundW = new Rectangle (x+60,y+10,10,90);
	}
	
	public BufferedImage getImage(){
		return playerMoveBuff;
	}
	
}
