package game.objects;

import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import map.Camera;
import map.DungeonNavigator;
import map.OverWorldNavigator;

import core.CollisionDetection;
import core.GameManager;


public class Moveable extends Sprite{
	private int speed;
	private double speedUp = 0.7;
	private double life;
	private boolean invincible;
	private boolean moveUp, moveRight, moveDown, moveLeft;
	private boolean moveable;
	private int dx, dy;
	private static boolean upLock, rightLock, downLock, leftLock;
	
	private boolean humanPlayer;
	private int moveableType;
	private int moveableID;
	private boolean inputLock;
	
	private static volatile Timer flashTimer, fallBackTimer, invincibleTimer;
	private static volatile TimerTask flashTask, fallBackTask, invincibleTask;
	private static int flashCounter;
	private static int fallBackCounter;
	private static int cycleFlash, cycleBack;
	
	
	
	protected Moveable(){
		
	}
	
	public void move(){
		dx = 0;
		dy = 0;
	
		
		if(moveUp)
			dy = -1;
		if(moveRight)	
			dx = 1;
		if(moveDown)	
			dy = 1;
		if(moveLeft)	
			dx = -1;
		
		
		
		dx *= (int)(speed*speedUp);
		dy *= (int)(speed*speedUp);
		
		setX(getX()+dx);
		setY(getY()+dy);
		moveSubSprite(speed, moveable, moveUp, moveRight, moveDown, moveLeft);
		/*
		if(!getInteractionLock()){
			setMovement(dx, dy);
			moveSubSprite(speed, moveable, moveUp, moveRight, moveDown, moveLeft);
		}
		*/
		
	
	}
	
	public void setMovement(int dx, int dy){
		OverWorldNavigator map = OverWorldNavigator.getInstance();
		Camera camera = Camera.getInstance();
		
		if(GameManager.dungeon || (GameManager.overWorld && !GameManager.cameraOn)){
			
			setX(getX()+dx);
			setY(getY()+dy);
			
		} 
		
		if (GameManager.overWorld && GameManager.cameraOn){
		
			if (!leftLock && !rightLock){
				if(humanPlayer){
					map.setXCoordinate(map.getXCoordinate()-dx);
					camera.setX(camera.getX()+dx);
					setX(400);
				}
				
				else
					setX(getX()+dx-Player.getInstance().getDX());
				
				
			}
			
			if(!upLock && !downLock){
				if(humanPlayer){
					map.setYCoordinate(map.getYCoordinate()-dy);
					camera.setY(camera.getY()+dy);
					setY(300);
				}
				
				else
					setY(getY()+dy-Player.getInstance().getDY());
			}
			
			if(leftLock){
				setX(getX()+dx);
				if(getX() > 410 && humanPlayer)
					leftLock = false;
			}
			
			if(rightLock){
				setX(getX()+dx);
				if(getX() < 390 && humanPlayer)
					rightLock = false;
			}

			if(upLock){
				setY(getY()+dy);
				if(getY() > 310 && humanPlayer)
					upLock = false;
			}
			
			if(downLock){
				setY(getY()+dy);
				if(getY() < 290 && humanPlayer)
					downLock = false;
			}
			
			
			//set Locks to align Camera at mapBorders
			if(humanPlayer){
			
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
						System.err.println("^^^^^^^^align Top@ 0");
						setY(getY()+dy);
						map.setYCoordinate(0);
						upLock = true;
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
						System.err.println("______align Bot @"+-((map.getHeightMap()-630)));
						setY(getY()+dy);
						map.setYCoordinate(-(map.getHeightMap()-630));
						downLock = true;
					}
					
				} 

				//yAxis
				
			} //if humanPlayer
				
				
			}


	}
	
	public void setLocks(boolean set){
		upLock = rightLock = downLock = leftLock = set;
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
	
	public void setObjectBack(int distance, boolean referenceObject, Rectangle objectRect){
		//System.out.println("setBack");
		
		if(!referenceObject)
			objectRect = new Rectangle(0,0,810,630);
		
		switch(getLastDirection()){
		
		case(1):	if(objectRect.intersects(getBoundDirN()))
					setMovement(0, distance);
					break;
			
		case(2):	if(objectRect.intersects(getBoundDirNE()))
					setMovement(-distance, distance);
					break;
		
		case(3):	if(objectRect.intersects(getBoundDirE()))
					setMovement(-distance, 0);
					break;
					
		case(4):	if(objectRect.intersects(getBoundDirSE()))
					setMovement(-distance,-distance);
					break;
					
		case(5):	if(objectRect.intersects(getBoundDirS()))
					setMovement(0, -distance);
					break;
					
		case(6):	if(objectRect.intersects(getBoundDirSW()))
					setMovement(distance, -distance);
					break;
					
		case(7):	if(objectRect.intersects(getBoundDirW()))
					setMovement(distance, 0);
					break;
					
		case(8):	if(objectRect.intersects(getBoundDirNW()))
					setMovement(distance,distance);
					break;
			
		}//switch(lastdirection)
		
		
		/*
		switch(getLastDirection()){
		case(1):	setMovement(0,+distance);
					break;
		case(3):	setMovement(-distance, 0);
					break;
		case(5):	setMovement(0,-distance);
					break;
		case(7):	setMovement(distance,0);
					break;
					
		case(2):	setMovement(-distance,distance);
					break;
		case(4):	setMovement(-distance,-distance);
					break;
		case(6):	setMovement(distance,-distance);
					break;
		case(8):	setMovement(distance,distance);
					break;
		}*/
	
	}
	
	//set
	public void setSpeed(int speed){this.speed = speed;}
	public void setSpeedUp(double speedUp){this.speedUp = speedUp;}
	public synchronized void setLife(double life){
		if(!invincible){
			this.life = life; 
			if(life <= 0){
				setAlive(false);
			}
		} else {
			System.out.println("Invincible Mode");
		}
		
	}
	
	public void setMoveable(boolean moveable){this.moveable = moveable;}
	public void setMoveUp(boolean moveUp){this.moveUp = moveUp;}
	public void setMoveRight(boolean moveRight){this.moveRight = moveRight;}
	public void setMoveDown(boolean moveDown){this.moveDown = moveDown;}
	public void setMoveLeft(boolean moveLeft){this.moveLeft = moveLeft;}
	
	public void setHumanPlayer(boolean humanPlayer){this.humanPlayer = humanPlayer;}
	public void setInputLock(boolean inputLock){this.inputLock = inputLock;}
	public void setMoveableType(int type){this.moveableType = type;}
	public void setMoveableID(int ID){this.moveableID = ID;}
	
	public void setAttack(){
	
		//interactType: 1 = attack, 2 = getHurt, 3 = getItem	
		setInteraction(1);
		setInteractionLock(true);
		
	}

	
	//get
	public synchronized double getLife(){return life;}
	public boolean getInputLock(){return inputLock;}
	public int getDX(){return dx;}
	public int getDY(){return dy;}
	
	
	public boolean getMoveable(){return moveable;}
	public boolean getMoveUp(){return moveUp;}
	public boolean getMoveRight(){return moveRight;}
	public boolean getMoveDown(){return moveDown;}
	public boolean getMoveLeft(){return moveLeft;}
	
	public boolean isHumanPlayer(){return humanPlayer;}
	public int getMoveableType(){return moveableType;}
	public int getMoveableID(){return moveableID;}
	
	public Rectangle getBoundDirection(int direction){
		
		if(direction != 1 && direction != -1)
			System.out.println("Moveable.Error: Illegal directionModifier@getBoundDirection");
		
			if (getLastDirection() == 1 && direction == 0)
				return getBoundDirN();
			
			else if (getLastDirection() == 3 && direction == 0)
				return getBoundDirE();
			
			else if (getLastDirection() == 5 && direction == 0)
				return getBoundDirS();
			
			else if (getLastDirection() == 7 && direction == 0)
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
			
		 else 
			 return getBoundCore();
			
			
		
			
		
	}

	public synchronized void startFlashTimer(int period, int duration){
			flashTimer = new Timer();
			flashTask = new TimerClass();
			flashTimer.schedule(flashTask, 10, period);
			cycleFlash = duration;

	}
	
	public synchronized void startFallBackTimer(int period, int duration){
		fallBackTimer = new Timer();
		fallBackTask = new TimerClass();
		fallBackTimer.schedule(fallBackTask, 10, period);
		cycleBack = duration;
		
	}
	
	public synchronized void startInvincibleTimer(int duration){
		invincible = true;
		invincibleTimer = new Timer();
		invincibleTask = new InvincibleDelayClass();
		startFallBackTimer(10,20);
		invincibleTimer.schedule(invincibleTask, duration);
		
		
	}

	private synchronized void stopFlashTimer(){

			flashTimer.cancel();
			flashTimer.purge();
			flashTimer = null;
			flashTask = null;
			flashCounter = cycleFlash = 0;
			
			System.out.println("stopFlash@"+flashCounter+"to"+cycleFlash);
			setVisible(true);
		
		
		
	}
	
	private synchronized void stopBackTimer(){

			fallBackTimer.cancel();
			fallBackTimer.purge();
			fallBackTimer = null;
			fallBackTask = null;
			fallBackCounter = cycleBack = 0;
		
	}
	
	private class TimerClass extends TimerTask{
		
		private TimerClass(){ }
		
		@Override
		public void run() {
			System.out.println(flashCounter+","+cycleFlash);
			setVisible(!getVisible());
			flashCounter++;
			fallBackCounter++;
			if(flashCounter >= cycleFlash || cycleFlash != -1)
				stopFlashTimer();
			
			if(fallBackCounter >= cycleBack || cycleBack != -1)
				stopBackTimer();
		}
	}
	
	private class InvincibleDelayClass extends TimerTask{
		private InvincibleDelayClass(){ }
		
		public void run () {
			invincible = false;
			setVisible(true);
		}
	}

}
