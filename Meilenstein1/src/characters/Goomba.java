package characters;




import java.util.Random;

import map.DungeonCollision;

import core.Board;
import core.FileLink;
import core.GameManager;

public class Goomba extends AbstractEnemy implements Runnable, FileLink {
	
	private static Player player;
	private static DungeonCollision dungeonCollision;
	private int changeStep = 10;
	
	public Goomba(){
		initializeEnemy(FileLink.goomba, 400, 200, 65, 75, 1, 0, true, true, 0, 3);

		//public AbstractEnemy(File sprite, int x, int y, int height, int width, int speed, int life, boolean hostile, boolean dungeon, int xMap, int yMap)
		System.out.println("add goomba");
		
		
	}
	
	public Goomba(Player player, DungeonCollision dungeonCollision){
		this.player = player;
		this.dungeonCollision = dungeonCollision;
		setPlayerObject(this.player);
		setDungeonCollisionObject(this.dungeonCollision);
	}
	
	
	public void run(){
		
		Random randomBoolean = new Random();
	    randomBoolean.nextBoolean();
	    
		
		if (GameManager.printMsg)
			System.out.println("Enemy.run");
		
		if(life > 0){
			setBoundMain();
			setBoundS();
			
			followPlayer();
			move();
			paintEnemy();
			if(oldLife < life)
				oldLife = life;
			
			System.out.println(changeStep);
			
		}
		
		if(life <= 0){
			x = -100;
			y = -100;
		}

	}
	
	private void followPlayer(){
		if(GameManager.printMsg){
			System.out.println("goombaX:"+x+" to "+ player.getX());
			System.out.println("goombaY:"+y+" to "+ player.getY());
		}
			
		/*
		if(x > player.getX() && y > player.getY()){
			moveUp = true;
			moveRight = false;
			moveDown = false;
			moveLeft = true;
			
			lastDirection = 2;
		}
		
		if(x < player.getX() && y < player.getY()){
			moveUp = false;
			moveRight = true;
			moveDown = true;
			moveLeft = false;
			
			lastDirection = 4;
		}
		
		if(y < player.getY() && x < player.getX()){
			moveUp = false;
			moveLeft = false;
			moveRight = true;
			moveDown = true;
			
			lastDirection = 6;
		}
				
		if(y > player.getY() && x > player.getX()){
			moveUp = true;
			moveRight = false;
			moveDown = false;
			moveLeft = true;
			
			lastDirection = 8;
		}
		*/
		
		if(y > player.getY() && (Math.abs((y - player.getY())) > Math.abs((x - player.getX()))) && changeStep > 30 || lastDirection == 1){
			moveUp = true;
			moveRight = false;
			moveDown = false;
			moveLeft = false;
			
			lastDirection = 1;

			if(lastDirection != newDirection)
				changeStep = 0;
			changeStep++;
		}
		
		if(x < player.getX() && (Math.abs((y - player.getY())) < Math.abs((x - player.getX()))) && changeStep > 30 || lastDirection == 3){
			moveUp = false;
			moveLeft = false;
			moveDown = false;
			moveRight = true;
			
			lastDirection = 3;

			if(lastDirection != newDirection)
				changeStep = 0;
			changeStep++;
		}

		if(y < player.getY() && (Math.abs((y - player.getY())) > Math.abs((x - player.getX()))) && changeStep > 30 || lastDirection == 5){
			moveUp = false;
			moveRight = false;
			moveDown = true;
			moveLeft = false;

			lastDirection = 5;

			if(lastDirection != newDirection)
				changeStep = 0;
			changeStep++;
		}
		
		if(x > player.getX() && (Math.abs((y - player.getY())) < Math.abs((x - player.getX()))) && changeStep > 30 || lastDirection == 7){
			moveUp = false;
			moveRight = false;
			moveDown = false;
			moveLeft = true;
	
			lastDirection = 7;

			if(lastDirection != newDirection)
				changeStep = 0;
			changeStep++;
		}

		
		
		
	}
	
	public void loseLife(){
		System.out.println("loseLife");
		int fallBack = 3;
		int playerLastPosition = player.getLastDirection();
		
		for(int i = 0; i < 10; i++){
			switch(playerLastPosition){
			case 1: y -= fallBack;
					break;
			case 2: x += fallBack; y -= fallBack;
					break;
			case 3: x += fallBack;
					break;
			case 4: x += fallBack; y += fallBack;
					break;
			case 5: y += fallBack;
					break;
			case 6: x -= fallBack; y += fallBack;
					break;
			case 7: x -= fallBack;
					break;
			case 8: x -= fallBack;y -= fallBack;
					break;
			}
		}
		
		life -= 1;
	}
	
	public void setLife(int life){
		this.life = life;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	

	
}
