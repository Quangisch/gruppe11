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

import java.io.IOException;
import javax.imageio.ImageIO;

import core.Board;
import core.GameManager;


import java.awt.Rectangle;


public class Enemy implements Runnable{
	//non static classes
	public int x, y;
	public Rectangle enemyBound;
	public BufferedImage enemyImage;
	
	
	
	public Enemy(int x, int y){
		System.err.println("->Enemy");
		
		this.x = x;
		this.y = y;
		enemyBound = new Rectangle (x,y,0,0);
	}
	
	
	
	public void run(){
		if (GameManager.printMsg)
			System.out.println("Enemy.run");
		
		move();
		paintEnemy();
		
	}
	
	private void move(){
		//coordinate enemy movement

	}
	
	private void paintEnemy(){
		//get Image/Subimmages
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public BufferedImage getImage(){
		return enemyImage;
	}
	
	public Rectangle getBounds(){
		return enemyBound;
	}

}
