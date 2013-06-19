package objects;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import map.Camera;
import map.OverWorldNavigator;

import core.GameManager;
import core.Sound;


public class Moveable extends Sprite{
	private int speed;
	private double speedUp = 0.9;
	private double life;
	private double maxLife;
	private boolean invincible;
	private boolean moveUp, moveRight, moveDown, moveLeft;
	private boolean moveable;
	private int dx, dy;
	private static boolean upLock, rightLock, downLock, leftLock;
	private int moveModifier = 1;
	
	private boolean humanPlayer;
	private int moveableType;
	private int moveableID;
	private boolean moveableBoss;
	private boolean inputLock;
	private boolean stopFallBack;
	
	private int flashCounter, cycleFlash;
	
	
	private Thread flashThread, invincibleThread, waitThread, rotateThread, fallBackThread;
	private ScheduledExecutorService execFlash = Executors.newSingleThreadScheduledExecutor();
	private ScheduledExecutorService execInvincible = Executors.newSingleThreadScheduledExecutor();
	private ScheduledExecutorService execWait = Executors.newSingleThreadScheduledExecutor();
	private ScheduledExecutorService execRotate = Executors.newSingleThreadScheduledExecutor();
	private ScheduledExecutorService execFallBack = Executors.newSingleThreadScheduledExecutor();
	
	protected Moveable(){
		
	}
	
	public void move(){
		dx = 0;
		dy = 0;
	
		
		if(moveUp)
			dy = -1*moveModifier;
		if(moveRight)	
			dx = 1*moveModifier;
		if(moveDown)	
			dy = 1*moveModifier;
		if(moveLeft)	
			dx = -1*moveModifier;
		
		
		
		dx *= (int)(speed*speedUp);
		dy *= (int)(speed*speedUp);
		
		/*
		setX(getX()+dx);
		setY(getY()+dy);
		moveSubSprite(speed, moveable, moveUp, moveRight, moveDown, moveLeft);
		*/
		
		if(!getInteractionLock()){
			setMovement(dx, dy);
			moveSubSprite(speed, moveable, moveUp, moveRight, moveDown, moveLeft);
		}
		
		
	
	}
	
	public void setMovement(int dx, int dy){
		OverWorldNavigator map = OverWorldNavigator.getInstance();
		Camera camera = Camera.getInstance();
		
		if(!isHumanPlayer()){
			//if(!GameManager.getInstance().cameraOn){
				setX(getX()+dx);
				setY(getY()+dy);
			//} 

			
		}
		
		if(isHumanPlayer()){
			
			if(GameManager.getInstance().dungeon || (GameManager.getInstance().overWorld && !GameManager.getInstance().cameraOn)){
				setX(getX()+dx);
				setY(getY()+dy);
			} 
			
			if (GameManager.getInstance().overWorld && GameManager.getInstance().cameraOn){
			
				ArrayList<Moveable> object = GameManager.getMoveableList();
				
				for(int index = 0; index < object.size(); index++){
					if (!leftLock && !rightLock)
						object.get(index).setX(object.get(index).getX()-dx);
					if(!upLock && !downLock)
						object.get(index).setY(object.get(index).getY()-dy);
				}
				
				if (!leftLock && !rightLock){
					map.setXCoordinate(map.getXCoordinate()-dx);
					camera.setX(camera.getX()+dx);
					setX(400);
				}
				
				if(!upLock && !downLock){
					map.setYCoordinate(map.getYCoordinate()-dy);
					camera.setY(camera.getY()+dy);
					setY(300);
					
				}
				
				if(leftLock){
					setX(getX()+dx);
					if(getX() > 410) leftLock = false;
				}
				
				if(rightLock){
					setX(getX()+dx);
					if(getX() < 390) rightLock = false;
				}

				if(upLock){
					setY(getY()+dy);
					if(getY() > 310) upLock = false;
				}
				
				if(downLock){
					setY(getY()+dy);
					if(getY() < 290) downLock = false;
				}
				
				
				//set Locks to align Camera at mapBorders

					if(map.getXCoordinate() > 0){
						
						if(getX() <= 400){
							setX(getX()+dx);
							map.setXCoordinate(0);
							System.err.println("<=======align Left @ 0");
							leftLock = true;
						}
						
						if(getX() > 400){
							map.setXCoordinate(map.getXCoordinate()-dx);
							camera.setX(camera.getX()+dx);
							setX(400);
						}
					}
					
					else if(map.getXCoordinate() < -(map.getWidthMap()-810)){
						if(getX() < 400){
							map.setXCoordinate(map.getXCoordinate()-dx);
							camera.setX(camera.getX()+dx);
							setX(400);
						}
						
						if(getX() >= 400){
							setX(getX()+dx);
							map.setXCoordinate(-(map.getWidthMap()-810));
							System.err.println("align Right========>@ "+(-(map.getWidthMap()-810)));
							rightLock = true;
						}
						
					} 

					//X Axis
					
					
					//Y Axis

					if(map.getYCoordinate() > 0){
						
						if(getY() >= 300){
							setY(getY()+dy);
							map.setYCoordinate(0);
							upLock = true;
							System.err.println("^^^^^^^^align Top@ 0");
						}
						
						if(getX() < 300){
							map.setYCoordinate(map.getYCoordinate()-dy);
							camera.setY(camera.getY()+dy);
							setY(300);
						}
					}
					
					else if(map.getYCoordinate() < -(map.getHeightMap()-630)){
						if(getY() < 300){
							map.setYCoordinate(map.getYCoordinate()-dy);
							camera.setY(camera.getY()+dy);
							setY(300);
						}
						
						if(getY() >= 300){
							setY(getY()+dy);
							System.err.println("______align Bot @"+-((map.getHeightMap()-630)));
							map.setYCoordinate(-(map.getHeightMap()-630));
							downLock = true;
						}
						
					} 

					//yAxis
			
			} //if GameManager.getInstance().overWorld && GameManager.getInstance().cameraOn
		
		}//if humanPlayer


	}
	
	public void setDirectionLock(int lock){
		switch(lock){
		case 0: upLock = rightLock = downLock = leftLock = true; break;			
		case 1:	upLock = true; break;		
		case 2: rightLock = true; break;		
		case 3: downLock = true; break;		
		case 4:	leftLock = true; break;		
		case 10: leftLock = rightLock = downLock = leftLock = false; break;
		}
	}
	
	public boolean getDirectionLock(){
		if(upLock || rightLock || downLock || leftLock)
			return true;
		else
			return false;	
	}
	
	public void resetMovementLock(){
		inputLock = false;
		moveable = true;
		moveUp = false;
		moveRight = false;
		moveDown = false;
		moveLeft = false;
		setMoveStep(0);
		System.out.println("resetMoveMentLock");
	}
	
	public void slowDownHalf(){
		moveable = !moveable;
	}
	
	public void setObjectBack(int distance, int direction, boolean referenceObject, Rectangle objectRect){
		//System.out.println("setBack");
		int toDirection = 0;
		
		if(!referenceObject)
			objectRect = new Rectangle(0,0,810,630);
		
		if(direction == 0)
			toDirection = getLastDirection();
		else
			toDirection = direction;
		
		switch(toDirection){
		
		case(1):	if(objectRect.intersects(getBoundDirN()))
					setMovement(0, distance);
					//if(speed <= 1){setX(getX());setY(getY()+distance);}
					break;
			
		case(2):	if(objectRect.intersects(getBoundDirNE()))
					setMovement(-distance, distance);
					//if(speed <= 1){setX(getX()-distance);setY(getY()+distance);}
					break;
		
		case(3):	if(objectRect.intersects(getBoundDirE()))
					setMovement(-distance, 0);
					//if(speed <= 1){setX(getX()-distance);setY(getY());}
					break;
					
		case(4):	if(objectRect.intersects(getBoundDirSE()))
					setMovement(-distance,-distance);
					//if(speed <= 1){setX(getX()-distance);setY(getY()-distance);}
					break;
					
		case(5):	if(objectRect.intersects(getBoundDirS()))
					setMovement(0, -distance);
					//if(speed <= 1){setX(getX());setY(getY()-distance);}
					break;
					
		case(6):	if(objectRect.intersects(getBoundDirSW()))
					setMovement(distance, -distance);
					//if(speed <= 1){setX(getX()+distance);setY(getY()-distance);}
					break;
					
		case(7):	if(objectRect.intersects(getBoundDirW()))
					setMovement(distance, 0);
					//if(speed <= 1){setX(getX()+distance);setY(getY());}
					break;
					
		case(8):	if(objectRect.intersects(getBoundDirNW()))
					setMovement(distance,distance);
					//if(speed <= 1){setX(getX()+distance);setY(getY()+distance);}
					break;
			
		}//switch(lastdirection)
	
		
		
		
	}
	
	//set
	public void setSpeed(int speed){this.speed = speed;}
	public void setSpeedUp(double speedUp){this.speedUp = speedUp;}
	public synchronized void setLife(double life){

		if(life > this.life && !isHumanPlayer())
			maxLife = life;
		
		if(!invincible){
			if(this.life > life && life > 0){
				startInvincibleTimer(500);
				startFlashTimer(150,5);
			}
				
			this.life = life; 
			if(life <= 0 && !isHumanPlayer()){
				setVisible(false);
				setAlive(false);	
			}
			if(Player.getInstance().getLife() <= 0){
				GameManager.getInstance().lose = true;
			}
			

		} 
		
	}
	
	public void setMoveable(boolean moveable){this.moveable = moveable;}
	public void setMoveUp(boolean moveUp){this.moveUp = moveUp;}
	public void setMoveRight(boolean moveRight){this.moveRight = moveRight;}
	public void setMoveDown(boolean moveDown){this.moveDown = moveDown;}
	public void setMoveLeft(boolean moveLeft){this.moveLeft = moveLeft;}
	public void setMovementModifier(int moveModifier){this.moveModifier = moveModifier;}
	
	public void setHumanPlayer(boolean humanPlayer){this.humanPlayer = humanPlayer;}
	public void setInputLock(boolean inputLock){this.inputLock = inputLock;}
	public void setMoveableType(int type){this.moveableType = type;}
	public void setMoveableID(int ID){this.moveableID = ID;}
	
	public void setAttack(){
	
		//interactType: 1 = attack, 2 = achieve, 3 = levelUp	
		setInteraction(1);
		setInteractionLock(true);
		Sound.getInstance().playSound(1);
		
	}
	
	public void setAchieve(){
		setInteraction(2);
		setInteractionLock(true);
		Sound.getInstance().playSound(0);
	}
	
	public void setLevelUp(){
		setInteraction(3);
		setInteractionLock(true);
		Sound.getInstance().playSound(0);
		//Sound.getInstance().playSound(10);
	}

	
	//get
	public synchronized double getLife(){return life;}
	public boolean getInputLock(){return inputLock;}
	public int getDX(){return dx;}
	public int getDY(){return dy;}
	
	public double getSpeedUp(){return speedUp;}
	public boolean getMoveable(){return moveable;}
	public boolean getMoveUp(){return moveUp;}
	public boolean getMoveRight(){return moveRight;}
	public boolean getMoveDown(){return moveDown;}
	public boolean getMoveLeft(){return moveLeft;}
	
	public boolean isHumanPlayer(){return humanPlayer;}
	public int getMoveableType(){return moveableType;}
	public int getMoveableID(){return moveableID;}
	
	public boolean getUpLock(){return upLock;}
	public boolean getRightLock(){return rightLock;}
	public boolean getDownLock(){return downLock;}
	public boolean getLeftLock(){return leftLock;}
	
	
	public Rectangle getBoundDirection(int direction){
		
		if(!(direction == 1 || direction == -1 || direction == 0))
			System.out.println("Moveable.Error: Illegal directionModifier@getBoundDirection");
		
			if (getLastDirection() == 1 && direction == 1)
				return getBoundDirN();
			
			else if (getLastDirection() == 3 && direction == 1)
				return getBoundDirE();
			
			else if (getLastDirection() == 5 && direction == 1)
				return getBoundDirS();
			
			else if (getLastDirection() == 7 && direction == 1)
				return getBoundDirW();

			//revertLastDirBound.return
			else if (getLastDirection() == 1 && direction == -1)
				return getBoundDirS();
			
			else if (getLastDirection() == 3 && direction == -1)
				return getBoundDirW();
			
			else if (getLastDirection() == 5 && direction == -1)
				return getBoundDirN();
			
			else if (getLastDirection() == 7 && direction == -1)
				return getBoundDirE();
			
			//getOuterBounds
			else if (getLastDirection() == 1 && direction == 0)
				return getBoundN();
			
			else if (getLastDirection() == 3 && direction == 0)
				return getBoundE();
			
			else if (getLastDirection() == 5 && direction == 0)
				return getBoundS();
			
			else if (getLastDirection() == 7 && direction == 0)
				return getBoundW();
			
		 else 
			 return getBoundCore();
	
	}

	public synchronized void startFallBackTimer(int distance, int direction, boolean referenceObject, Rectangle referenceRect){
		fallBackThread = new Thread(new FallBackTimer(distance, interpretDirection(direction), referenceObject, referenceRect));
		execFallBack.scheduleWithFixedDelay(fallBackThread, 0, 10, TimeUnit.MILLISECONDS);
		System.err.println("startFallBackTimer!");
	}
	
	private int interpretDirection(int direction){
		int directionOut = 0;
		
		if(direction > 0)
			directionOut = direction;
		if(direction < 0){
			switch(direction){
			case -1: directionOut = 5;break;
			case -2: directionOut = 6;break;
			case -3: directionOut = 7;break;
			case -4: directionOut = 8;break;
			case -5: directionOut = 1;break;
			case -6: directionOut = 2;break;
			case -7: directionOut = 3;break;
			case -8: directionOut = 4;break;
			}
		}
		
		System.out.println("Direction_in@"+direction+",out@"+directionOut);
		
		return directionOut;
	}
	
	public synchronized void stopFallBackTimer(){
		stopFallBack = true;
	}
	
	public synchronized void startFlashTimer(int period, int duration){

		flashThread = new Thread(new FlashTimer());
		execFlash.scheduleWithFixedDelay(flashThread, 0, period, TimeUnit.MILLISECONDS);
		cycleFlash = duration;
		System.err.println("startFlashTimer!");
	}
	
	
	public synchronized void startInvincibleTimer(int delay){
		
		invincibleThread = new Thread(new InvincibleTimer());
		execFlash.schedule(invincibleThread, delay, TimeUnit.MILLISECONDS);
		
		invincible = true;

		//startFlashTimer(500, 100);
		//System.err.println("====>Invincible.start@WDH:"+(int)(delay / 200));
			
	}
	
	public synchronized void startWaitTimer(int time){
		waitThread = new Thread(new WaitTimer());
		execWait.schedule(waitThread, time, TimeUnit.MILLISECONDS);
		moveable = false;
	}
	
	public synchronized void startRotateTimer(int delay, int speed, int cycles){
		rotateThread = new Thread(new RotateTimer(cycles));
		execRotate.scheduleWithFixedDelay(rotateThread, delay, speed, TimeUnit.MILLISECONDS);
	}

	public boolean getMoveableBoss() {
		return moveableBoss;
	}

	public void setMoveableBoss(boolean moveableBoss) {
		this.moveableBoss = moveableBoss;
	}

	public double getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(double maxLife) {
		this.maxLife = maxLife;
	}

	private class FlashTimer implements Runnable{
		
		private FlashTimer(){ }
	
		public void run() {
			System.out.println(flashCounter+","+cycleFlash);
			
			if(getOpacity() >= 1)
				setOpacity(0.7f);
			else
				setOpacity(1f);
			
			System.out.println(getVisible());
			flashCounter++;
			
			if(flashCounter > cycleFlash || flashCounter == 0){
				flashCounter = cycleFlash = 0;
				setOpacity(1f);
				
				execFlash.shutdown();
				execFlash = Executors.newSingleThreadScheduledExecutor();
				flashThread = new Thread(new FlashTimer());
			}
				
		}
	}
	
	private class InvincibleTimer implements Runnable{
		private InvincibleTimer(){ }
		
		public void run () {
			invincible = false;
			System.err.println("====>Invincible.false");
			
			execInvincible.shutdown();
			execInvincible = Executors.newSingleThreadScheduledExecutor();
			invincibleThread = new Thread(new InvincibleTimer());
		}
	}
	
	private class WaitTimer implements Runnable{
		private WaitTimer(){ }
		
		public void run(){
			moveable = true;
		}
	}
	
	private class RotateTimer implements Runnable{
		int cycleMax;
		int cycle;
		private RotateTimer(int cycleMax){
			this.cycleMax = cycleMax;
		}
		public void run(){
			System.out.println("Cycle@"+cycle+"to"+cycleMax);
			cycle++;
			setLastDirection(((getLastDirection()+1)%8)+1);
			if(cycle == cycleMax){
				execRotate.shutdown();
				execRotate = Executors.newSingleThreadScheduledExecutor();
				rotateThread = new Thread(new RotateTimer(0));
			}
		}
	}
	
	private class FallBackTimer implements Runnable{
		private int distance;
		private int distanceCounter;
		private int direction;
		private boolean referenceObject;
		private Rectangle referenceRect;
		private FallBackTimer(int distance, int direction, boolean referenceObject, Rectangle referenceRect){ 
			this.distance = distance;
			this.direction = direction;
			this.referenceObject = referenceObject;
			this.referenceRect = referenceRect;
		}
		
		public void run(){
			distanceCounter++;
			setObjectBack(2,direction,referenceObject,referenceRect);
			System.out.println("fallBack@"+distanceCounter+",to"+distance);
			if(distanceCounter/2 >= distance || stopFallBack){
				stopFallBack = false;
				distanceCounter = 0;
				distance = 0;
				execFallBack.shutdown();
				execFallBack = Executors.newSingleThreadScheduledExecutor();
				fallBackThread = new Thread(new FallBackTimer(0,0,false,null));
			}
			
		}
	}

}
