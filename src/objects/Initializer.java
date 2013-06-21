package objects;

import java.io.File;

abstract class Initializer extends Moveable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1253696535547252573L;
	private volatile boolean iniPosition, iniImage, iniAttributes;
	
	protected Initializer(){
	}
	
	public void initializePosition(int x, int y, int lastDirection){
		setX(x);
		setY(y);
		setLastDirection(lastDirection);
		iniPosition = true;
		System.out.println("-->iniPosition");
	
	}

	public void initializeImage(File spriteFile, int subSpriteWidth, int subSpriteHeight, int moveStepCycle){
		setFile(spriteFile);
		setSubSpriteWidth(subSpriteWidth);
		setSubSpriteHeight(subSpriteHeight);
		setMoveStepCycle(moveStepCycle);
		loadSprite();
		iniImage = true;
		System.out.println("-->iniImage");
	}
	

	public void initializeAttributes(int speed, double life, boolean visible, 
						int coreX, int coreY, int coreWidth, int coreHeight){
		setMoveable(true);
		setSpeed(speed);
		setLife(life, false);
		setAlive(true);
		setVisible(visible);
		setCoreX(coreX);
		setCoreY(coreY);
		setCoreWidth(coreWidth);
		setCoreHeight(coreHeight);
		iniAttributes = true;
		System.out.println("-->iniAttributes");
	}
	

	
	public synchronized boolean getInitialized(){
		if(iniPosition && iniImage && iniAttributes && getLife() > 0){
			setAlive(true);
			return true;
		}
		else{
			//System.out.println("--->faultyIni");
			return false;
		}
			
	}
	
	protected void byPassInitialCheck(){
		iniAttributes = iniPosition = iniImage = true;
		setLife(9999, false);
	}
	
}
