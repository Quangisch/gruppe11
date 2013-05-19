package characters;
/*====> evtl. als abstrakte Klasse halten und 
*ne weitere spezielle Gegnerklasse machen,
*damit man darauf basierend spŠter weitere
*Gegnertypen einfŸgen kann
*
*
*Wir sollen keine sprites nehmen, die urheberrechtlich geschŸtzt sind.
*MarioSprite wird spŠter ausgetauscht, so kannst du das aber auch handhaben.
*
*Der Gegner soll von einem Spieler mit Schwert/Punch besiegt werden kšnnen
*und wenn mšglich in etwa im klassischen Zelda Genre gehalten werden.
*DafŸr wird im Verlauf der nŠchsten Tage ein Dungeon mit geschlo§enen
*RŠumen erstellt werden.
*
*/

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import map.DungeonCollision;

import core.Board;
import core.GameManager;


import java.awt.Rectangle;


abstract class AbstractEnemy extends JComponent {
	
	Graphics2D g2d;
	private Player player;
	private DungeonCollision dungeonCollision;
	
	//non static classes
	int x, y;
	int dx, dy;
	int height, width;
	int speed;
	int life = 0;
	int oldLife = life;
	boolean hostile;
	boolean dungeon;
	int xMap, yMap;
	boolean visible = true;
	
	
	boolean moveUp, moveRight, moveDown, moveLeft;
	int lastDirection, newDirection;
	double interStep = 0.1;
	int moveStep;
	
	BufferedImage enemyMove, enemyMoveBuff;
	final int spriteGridX = 30*3;
	final int spriteGridY = 40*3;
	

	Rectangle enemyBoundMain,enemyBoundS;
	Random random = new Random();
	int randomMove;
	public AbstractEnemy(){
		
	}
	
	public void initializeEnemy(File sprite, int x, int y, int width, int height, int speed, int life, 
			boolean hostile, boolean dungeon, int xMap, int yMap){
		
		System.err.println("->Enemy in " +xMap+", "+yMap);
		
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.speed = speed;
		this.life = life;
		this.hostile = hostile;
		this.dungeon = dungeon;
		this.xMap = xMap;
		this.yMap = yMap;
		
		try {
			enemyMove = ImageIO.read(sprite);
			enemyMoveBuff = new BufferedImage(1260, 960, BufferedImage.TYPE_INT_ARGB);
			} catch (IOException e) {
				System.err.println("file not found");
				System.exit(0);
			}
		
		
		//randomMove = random.nextInt(4);
		
		moveRight = true;
		lastDirection = 2;
		
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		if(visible)
			g2d.drawImage(enemyMoveBuff,x,y,this);
	}
	
	
	
	void move(){
		//moveUp = moveRight = moveDown = moveLeft = false;
		/*
		if(x > 270){
			moveRight = false;
			moveLeft = true;
			lastDirection = 6;
		}
		
		if(x < 90){
			moveLeft = false;
			moveRight = true;
			lastDirection = 2;
		}
		*/
		
		
			
		
		switch(randomMove){
		
		case 1: moveUp = moveRight = true;
				break;
		case 2: moveRight = moveDown = true;
				break;
		case 3:	moveDown = moveLeft = true;
				break;
		case 4: moveLeft = moveUp = true;
		}
		
		
		

		if (moveLeft) dx = -1;
		if (moveRight) dx = 1;
		if (moveUp) dy = -1;
		if (moveDown) dy = 1;
		
		double length = Math.sqrt(dx * dx + dy * dy);
		
		if (length != 0) {
			
		    dx *= speed;
		    dy *= speed;

		    //toogles movement sprites/animation
		    if (dx != 0 || dy != 0){
		    	if (moveStep >= 7 || newDirection != lastDirection || interStep > 8)
		    		interStep = 0;
		    		interStep += speed;
		    		//moveStep = Math.round((int)interStep);
		    		moveStep++;
		    		System.out.println("moveStep:"+moveStep);
		    }
		}
    		x += dx;
        	y += dy;
	}
	
	void paintEnemy(){
		//System.out.println("AbstractE.paintEnemy");
		//get Image/Subimages
		if ( moveUp == true &&  moveRight == true && moveDown != true &&moveLeft != true || lastDirection == 2){
			if (newDirection != lastDirection || moveStep >= 3)
				moveStep = 0;
			enemyMoveBuff = enemyMove.getSubimage(moveStep*spriteGridX, 1*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 2;
			newDirection = lastDirection;
		}
		if ( moveUp != true &&  moveRight == true && moveDown == true &&moveLeft != true || lastDirection == 4){
			if (newDirection != lastDirection || moveStep >= 3)
				moveStep = 0;
			enemyMoveBuff = enemyMove.getSubimage(moveStep*spriteGridX, 3*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 4;
			newDirection = lastDirection;
		}
		if ( moveUp != true &&  moveRight != true && moveDown == true &&moveLeft == true|| lastDirection == 6){
			if (newDirection != lastDirection || moveStep >= 3)
				moveStep = 0;
			enemyMoveBuff = enemyMove.getSubimage(moveStep*spriteGridX, 5*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 6;
			newDirection = lastDirection;
		}
		if ( moveUp == true &&  moveRight != true && moveDown != true &&moveLeft == true || lastDirection == 8){
			if (newDirection != lastDirection || moveStep >= 3)
				moveStep = 0;
			enemyMoveBuff = enemyMove.getSubimage(moveStep*spriteGridX, 7*spriteGridY, spriteGridX, spriteGridY);
			lastDirection = 8;
			newDirection = lastDirection;
		}
		

	}

	
	public void setNewRandomMove(){
		//randomMove = random.nextInt(4);
	}

	
	private void sleepNow(){
		try{
			System.out.println("start.Sleep");
			Thread.sleep(1000);
			System.out.println("stop.Sleep");
		} catch (InterruptedException ie){
			System.err.println("DungeonCollision.sleepNow:"+ie);
		}
	}
	
	void setPlayerObject(Player player){this.player = player;}
	void setDungeonCollisionObject(DungeonCollision dungeonCollision){this.dungeonCollision = dungeonCollision;}
	

	public Rectangle getBoundMain(){return enemyBoundMain;}
	public void setBoundMain(){enemyBoundMain = new Rectangle(x+12,y+15,width,height);}
	
	public Rectangle getBoundS(){return enemyBoundS;}
	public void setBoundS(){enemyBoundS = new Rectangle(x+21,y+60,54,40);}

	public void setLife(int life){this.life = life;}
	public int getLife(){return life;}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	
	public int getX(){return x;} 
	public int getY(){return y;}
	
}
