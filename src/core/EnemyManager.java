package core;


import java.io.File;

import objects.MarioDark;

public enum EnemyManager implements FileLink {
	
	MARIODARK	(0, enemyDark, 90, 120, 8, 0, 75, 45, 20),
	MARIOBRIGHT	(1,enemyBright, 90, 120, 8, 0, 75, 45, 20),
	
	MARIOBOSS1	(22, enemyDark, 90, 120, 8, 0, 75, 45, 20),
	MARIOBOSS2	(21, enemyDark, 90, 120, 8, 0, 75, 45, 20),
	MARIOBOSS3	(20, enemyDark, 90, 120, 8, 0, 75, 45, 20);
	
	int type;
	File file;
	int subSpriteWidth;
	int subSpriteHeight;
	int moveStepCycle; 
	int coreX; 
	int coreY; 
	int coreWidth;
	int coreHeight;
	
	EnemyManager(int type, File file, int subSpriteWidth, int subSpriteHeight, int moveStepCycle, int coreX, int coreY, int coreWidth, int coreHeight){
		
		this.type = type;
		this.file = file;
		this.subSpriteWidth = subSpriteWidth;
		this.subSpriteHeight = subSpriteHeight;
		this.moveStepCycle = moveStepCycle;
		
		this.coreX = coreX;
		this.coreY = coreY;
		this.coreWidth = coreWidth;
		this.coreHeight = coreHeight;
		
		
	}
	
	public static void setNewEnemy(int xCoordinateMap, int yCoordinateMap, int enemyType, int[] enemyPosition, int[]enemyAttributes){
		
		int speed = enemyAttributes[0];
		int life = enemyAttributes[1];
		int direction = enemyAttributes[2];
		int pattern = enemyAttributes[3];
		double attackDamage = enemyAttributes[4];
		
		if(enemyType == 0)
			MARIODARK.initializeInstance(enemyType, false, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern, attackDamage);
		if(enemyType == 1)
			MARIOBRIGHT.initializeInstance(enemyType, false, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern, attackDamage);
		
		if(enemyType == 22)
			MARIOBOSS1.initializeInstance(enemyType, true, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern, attackDamage);
		if(enemyType == 21)
			MARIOBOSS2.initializeInstance(enemyType, true, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern, attackDamage);
		if(enemyType == 20)
			MARIOBOSS3.initializeInstance(enemyType, true, xCoordinateMap, yCoordinateMap, direction, speed, life, true, pattern, attackDamage);
		
	}
	
	public static synchronized void reinitializeEnemyInstance(int enemyType, int ID, boolean boss){
		
		for(EnemyManager enemy : values()){
			if(enemy.type == enemyType){
				MarioDark.getInstance(false, ID, boss).initializeImage(enemy.file, enemy.subSpriteWidth, enemy.subSpriteHeight, enemy.moveStepCycle);
				GameManager.getInstance().addGameObject(MarioDark.getInstance(false, ID, boss));
			}
		}
		
	}
	
	private synchronized void initializeInstance(int enemyType, boolean boss, int xCoordinateMap, int yCoordinateMap, int direction, int speed, int life, boolean visible, int pattern, double attackDamage){
		
		//if(!boss){
			int num = MarioDark.getInstanceCounter();
			
			MarioDark.getInstance(true, num, boss).initializeImage(file, subSpriteWidth, subSpriteHeight, moveStepCycle);
			MarioDark.getInstance(false, num, boss).initializeAttributes(speed, life, visible, coreX, coreY, coreWidth, coreHeight);
			MarioDark.getInstance(false, num, boss).initializePosition(xCoordinateMap, yCoordinateMap, direction);
			MarioDark.getInstance(false, num, boss).setPattern(pattern);
			MarioDark.getInstance(false, num, boss).setAttackDamage(attackDamage);
			MarioDark.getInstance(false, num, boss).setEnemyManagerType(enemyType);
			
			GameManager.getInstance().addGameObject(MarioDark.getInstance(false, num, boss));
			System.err.println("========>EnemyManager.initializeInstance: type@"+type+" ID@"+num+" Behaviour@"+pattern);
		
	}

	public static synchronized MarioDark getMarioDark(int IDNumber, boolean boss){
		return MarioDark.getInstance(false, IDNumber, boss);
	}
	


}
