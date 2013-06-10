package core;
import game.objects.MarioDark;

import java.io.File;

public enum EnemyManager implements FileLink {
	
	MARIODARK(1, enemyDark, 90, 120, 8, 0, 75, 45, 20,1),
	MARIOBRIGHT(2, enemyBright, 90, 120, 8, 0, 75, 45, 20,1);
	
	int type;
	File file;
	int subSpriteWidth;
	int subSpriteHeight;
	int moveStepCycle; 
	int coreX; 
	int coreY; 
	int coreWidth;
	int coreHeight;
	int attackDamage;
	
	EnemyManager(int type, File file, int subSpriteWidth, int subSpriteHeight, int moveStepCycle, int coreX, int coreY, int coreWidth, int coreHeight, int attackDamage){
		
		this.type = type;
		this.file = file;
		this.subSpriteWidth = subSpriteWidth;
		this.subSpriteHeight = subSpriteHeight;
		this.moveStepCycle = moveStepCycle;
		
		this.coreX = coreX;
		this.coreY = coreY;
		this.coreWidth = coreWidth;
		this.coreHeight = coreHeight;
		
		this.attackDamage = attackDamage;
		
	}
	
	public static void setNewEnemy(int xCoordinateMap, int yCoordinateMap, int enemyType, int[] enemyPosition, int[]enemyAttributes){
		
		int speed = enemyAttributes[0];
		int life = enemyAttributes[1];
		int direction = enemyAttributes[2];
		int pattern = enemyAttributes[3];
		
		if(enemyType == 0)
			MARIODARK.initializeInstance(xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern);
		
		
		if(enemyType == 1)
			MARIOBRIGHT.initializeInstance(xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern);
		

	}
	
	private synchronized void initializeInstance(int xCoordinateMap, int yCoordinateMap, int direction, int speed, int life, boolean visible, int pattern){
		
		int num = MarioDark.getInstanceCounter();
		
		MarioDark.getInstance(true, num).initializeImage(file, subSpriteWidth, subSpriteHeight, moveStepCycle);
		MarioDark.getInstance(false, num).initializeAttributes(speed, life, visible, coreX, coreY, coreWidth, coreHeight);
		MarioDark.getInstance(false, num).initializePosition(xCoordinateMap, yCoordinateMap, direction);
		MarioDark.getInstance(false, num).setBehaviour(pattern);
		MarioDark.getInstance(false, num).setMoveableType(type);
		
		GameManager.addGameObject(MarioDark.getInstance(false, num));
		
		System.err.println("========>EnemyManager.initializeInstance: type@"+type+" ID@"+num+" Behaviour@"+pattern);
		
		/*
		MarioDark.getInstance(true, num+1).initializeImage(file, subSpriteWidth, subSpriteHeight, moveStepCycle);
		MarioDark.getInstance(false, num+1).initializeAttributes(2, 3, true, 0, 75, 45, 20);
		MarioDark.getInstance(false, num+1).initializePosition(xCoordinateMap, yCoordinateMap, direction);
		MarioDark.getInstance(false, num+1).setBehaviour(2);
		GameManager.addGameObject(MarioDark.getInstance(false, num+1));
		*/
	}

	public static synchronized int getAttackDamage(int type){
		
		int attackDamage = 0;
		
		for(EnemyManager enemyType: EnemyManager.values()){
			System.out.println("getAttackDamag.For @type:"+type);
			if(enemyType.type == type){
				attackDamage = enemyType.attackDamage;
				System.out.println("foundType!");
				break;
			}
			
		}
		return attackDamage;
	}
	

	public static synchronized MarioDark getMarioDark(int IDNumber){
		return MarioDark.getInstance(false, IDNumber);
	}
	
	

}
