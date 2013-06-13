package core;

import game.objects.MarioDark;

import java.io.File;

public enum EnemyManager implements FileLink {
	
	MARIODARK	(0, enemyDark, 90, 120, 8, 0, 75, 45, 20,1),
	MARIOBRIGHT	(1,enemyBright, 90, 120, 8, 0, 75, 45, 20,1),
	
	MARIOBOSS1	(22, enemyDark, 90, 120, 8, 0, 75, 45, 20,1),
	MARIOBOSS2	(21, enemyDark, 90, 120, 8, 0, 75, 45, 20,1),
	MARIOBOSS3	(20, enemyDark, 90, 120, 8, 0, 75, 45, 20,2);
	
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
			MARIODARK.initializeInstance(false, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern);
		if(enemyType == 1)
			MARIOBRIGHT.initializeInstance(false, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern);
		
		if(enemyType == 22)
			MARIOBOSS1.initializeInstance(true, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern);
		if(enemyType == 21)
			MARIOBOSS2.initializeInstance(true, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern);
		if(enemyType == 20)
			MARIOBOSS3.initializeInstance(true, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern);
		
	}
	
	private synchronized void initializeInstance(boolean boss, int xCoordinateMap, int yCoordinateMap, int direction, int speed, int life, boolean visible, int pattern){
		
		//if(!boss){
			int num = MarioDark.getInstanceCounter();
			
			MarioDark.getInstance(true, num, boss).initializeImage(file, subSpriteWidth, subSpriteHeight, moveStepCycle);
			MarioDark.getInstance(false, num, boss).initializeAttributes(speed, life, visible, coreX, coreY, coreWidth, coreHeight);
			MarioDark.getInstance(false, num, boss).initializePosition(xCoordinateMap, yCoordinateMap, direction);
			MarioDark.getInstance(false, num, boss).setPattern(pattern);
			MarioDark.getInstance(false, num, boss).setMoveableType(type);
			
			GameManager.addGameObject(MarioDark.getInstance(false, num, boss));
			System.err.println("========>EnemyManager.initializeInstance: type@"+type+" ID@"+num+" Behaviour@"+pattern);
			
		/*
		} else {
			
			int num = MarioDark.getInstanceCounter();
			
			MarioDark.getInstance(true, num, boss).initializeImage(file, subSpriteWidth, subSpriteHeight, moveStepCycle);
			MarioDark.getInstance(false, num, boss).initializeAttributes(speed, life, visible, coreX, coreY, coreWidth, coreHeight);
			MarioDark.getInstance(false, num, boss).initializePosition(xCoordinateMap, yCoordinateMap, direction);
			MarioDark.getInstance(false, num, boss).setPattern(pattern);
			MarioDark.getInstance(false, num, boss).setMoveableType(type);
			
			GameManager.addGameObject(MarioDark.getInstance(false, num, boss));
			System.err.println("========>EnemyManager.initializeInstance: type@"+type+" ID@"+num+" Behaviour@"+pattern);
			
			
		}
		
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
	

	public static synchronized MarioDark getMarioDark(int IDNumber, boolean boss){
		return MarioDark.getInstance(false, IDNumber, boss);
	}
	
	

}
