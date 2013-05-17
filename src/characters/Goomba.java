package characters;




import java.util.Random;

import map.DungeonCollision;

import core.Board;
import core.FileLink;
import core.GameManager;

public class Goomba extends AbstractEnemy implements Runnable, FileLink {
	
	private static Player player;
	private static DungeonCollision dungeonCollision;
	
	public Goomba(){
		initializeEnemy(FileLink.goomba, 400, 200, 65, 75, 1, 0, true, true, 0, 3);

		//public AbstractEnemy(File sprite, int x, int y, int height, int width, int speed, int life, boolean hostile, boolean dungeon, int xMap, int yMap)
		System.out.println("add goomba");
		
		
	}
	
	public Goomba(Player player, DungeonCollision dungeonCollision){
		this.player = player;
		this.dungeonCollision = dungeonCollision;
		setPlayerObject(player);
		setDungeonCollisionObject(dungeonCollision);
	}
	
	
	public void run(){
		
		Random randomBoolean = new Random();
	    randomBoolean.nextBoolean();
	    
		
		if (GameManager.printMsg)
			System.out.println("Enemy.run");
		
		if(life > 0){
			setBoundMain();
			setBoundS();
			move();
			paintEnemy();
			if(oldLife < life){
				
				oldLife = life;
			}
				
			
		}
		
		if(life <= 0){
			x = -100;
			y = -100;
		}

	}
	

	
	

	
}
