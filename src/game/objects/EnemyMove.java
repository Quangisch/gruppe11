package game.objects;

import java.awt.Rectangle;
import java.util.Map;
import java.util.Random;

import core.Board;
import core.ItemListManager;


abstract class EnemyMove extends Initializer{

	
	private boolean hostile = true;
	
	//movement locks
	private boolean xLock;
	private boolean yLock;
	private int destinationCounter;
	private int cycleCounter;
	private boolean cycleEnd;
	
	
	
	protected EnemyMove(){
		setHumanPlayer(false);
	}
	
	
	 void stopNow(){
		setMoveUp(false);
		setMoveRight(false);
		setMoveDown(false);
		setMoveLeft(false);
		setMoveStep(0);
		
		xLock = false;
		yLock = false;
	}
	

	public boolean rotateNow(int cycles, boolean clockwise, int cycleSpeed){
		setMoveable(false);
		move();
		
		if(clockwise && (cycleCounter <= cycles*4) || cycles == -1){
			if(getLastDirection() < 7)
				setLastDirection(getLastDirection()+2);
			else
				setLastDirection(1);
		}
		else if(!clockwise && (cycleCounter <= cycles*4) || cycles == -1){
			if(getLastDirection() > 1)
				setLastDirection(getLastDirection()+2);
			else
				setLastDirection(7);
		}
		
		try {
			Thread.sleep(cycleSpeed);
		} catch (InterruptedException e) {}
		
		cycleCounter++;
		
		if(cycleCounter <= cycles*4)
			return false;
		else
			return true;
	}
	
	public boolean moveToY(int yDestination){
		if(!yLock){
			yLock = true;
			if(getY() > yDestination && !getMoveDown())
				setMoveUp(true);
			if(getY() < yDestination && !getMoveUp())
				setMoveDown(true);
		}
		if(yLock){
			if(getMoveUp() && !getMoveDown() && (getY() <= yDestination)){
				setMoveUp(false);
				yLock = false;
				setY(yDestination); 
				//System.out.println("reach xDestination");
				destinationCounter++;
			}
				
			if(getMoveDown() && !getMoveUp() && (getY() >= yDestination)){
				setMoveDown(false);
				yLock = false;
				setY(yDestination); 
				//System.out.println("reach yDestination");
				destinationCounter++;
			}
			
		}
		
		move();
		
		if(!getMoveLeft() && !getMoveRight())
			return true;	
		else
			return false;
	}
	
	public boolean moveToX(int xDestination){
		if(!xLock){
			xLock = true;
			if(getX() < xDestination && !getMoveLeft())
				setMoveRight(true);
			if(getX() > xDestination && !getMoveRight())
				setMoveLeft(true);
		}
		if(xLock){
			if(getMoveRight() && !getMoveLeft() && (getX() >= xDestination)){
				setMoveRight(false);
				xLock = false;
				setX(xDestination); 
				//System.out.println("reach xDestination");
				destinationCounter++;
			}
				
			if(getMoveLeft() && !getMoveRight() && (getX() <= xDestination)){
				setMoveLeft(false);
				xLock = false;
				setX(xDestination); 
				//System.out.println("reach xDestination");
				destinationCounter++;
			}
		}
		
		move();
		
		if(!getMoveLeft() && !getMoveRight())
			return true;	
		else
			return false;
	}
	
	public boolean patrolLineX(int cycles, int point1x, int point2x){
		switch(destinationCounter){
		case 0: moveToX(point1x);
				break;
		case 1: moveToX(point2x);
				break;
		case 2: cycleCounter++;
				if(cycleCounter == cycles && cycles != -1){
					cycleEnd = true;
					System.out.println("reach end of cycle");
				}
				else if(cycleCounter < cycles)
					destinationCounter = 0;
				break;
		}
			
		if(destinationCounter == 2*cycles && cycles != -1)
			return true;
		else
			return false;
	}
	
	public boolean patrolLineY(int cycles, int point1y, int point2y){
		switch(destinationCounter){
		case 0: moveToY(point1y);
				break;
		case 1: moveToY(point2y);
				break;
		case 2: cycleCounter++;
				if(cycleCounter == cycles && cycles != -1){
					cycleEnd = true;
					System.out.println("reach end of cycle");
				}
				else if(cycleCounter < cycles)
					destinationCounter = 0;
				break;
		}
			
		if(destinationCounter == 2*cycles && cycles != -1)
			return true;
		else
			return false;
	}
	
	public boolean patrolRectangle(int cycles, boolean clockwise, int pointX, int pointY, int width, int height){
		if(clockwise){
	
			switch(destinationCounter){
			case 0: moveToX(pointX);
					break;
			case 1: moveToY(pointY);
					break;
					
			case 2: moveToX(pointX+width);
					break;
			case 3:	moveToY(pointY+height);
					break;
			case 4: moveToX(pointX);
					break;
			case 5:	moveToY(pointY);
					break;
					
			case 6:	cycleCounter++;
					if(cycleCounter == cycles){
						cycleEnd = true;
						System.out.println("reach end of cycle");
					}
					else if(cycleCounter < cycles || cycles == -1)
						destinationCounter = 2;
					break;
			}
		}
		
		if(!clockwise){
			switch(destinationCounter){
			case 0: moveToY(pointY);
					break;
			case 1: moveToX(pointX);
					break;
					
			case 2: moveToY(pointY+height);
					break;
			case 3:	moveToX(pointX+width);
					break;
			case 4:	moveToY(pointY);
					break;
			case 5: moveToX(pointX);
					break;
			
			case 6:	cycleCounter++;
					if(cycleCounter == cycles){
						cycleEnd = true;
						System.out.println("reach end of cycle");
					}
					else if(cycleCounter < cycles || cycles == -1)
						destinationCounter = 2;
					break;
			}
				
		}
		if(destinationCounter == 2*cycles)
			return true;
		else
			return false;
	}
	
	public boolean followObject(DrawableObject drawable){
		if(getY() > drawable.getY() && (Math.abs((getY() - drawable.getY())) >= Math.abs((getX() - drawable.getX())))){
			setMoveUp(true);
			setMoveRight(false);
			setMoveDown(false);
			setMoveLeft(false);
			move();
		}
		
		if(getX() < drawable.getX() && (Math.abs((getY() - drawable.getY())) < Math.abs((getX() - drawable.getX())))){
			setMoveUp(false);
			setMoveLeft(false);
			setMoveDown(false);
			setMoveRight(true);
			move();
		}

		if(getY() < drawable.getY() && (Math.abs((getY() - drawable.getY())) >= Math.abs((getX() - drawable.getX())))){
			setMoveUp(false);
			setMoveRight(false);
			setMoveDown(true);
			setMoveLeft(false);
			move();
		}
		
		if(getX() > drawable.getX() && (Math.abs((getY() - drawable.getY())) < Math.abs((getX() - drawable.getX())))){
			setMoveUp(false);
			setMoveRight(false);
			setMoveDown(false);
			setMoveLeft(true);
			move();
			
		}
			
		return false;
	}
	
	public void punchObject(Moveable object, int wait){
		Rectangle bound = getBoundDirection(0);
		Rectangle punchRadius = new Rectangle(bound.x, bound.y, bound.width, bound.height);
		
		if(punchRadius.intersects(object.getBound())){
			
			startWaitTimer(1000);
			
			try {
				setMoveStep(0);
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			setAttack();
			
			if(this.getBoundHitSpace().intersects(object.getBound())){
				object.setLife(object.getLife()-1.5);
				object.setObjectBack(10, 0, false, null);
				this.startWaitTimer(2000);
			}
		}

	}
	
	public void castObject(Moveable object, int spellType){
		
		
		if(getSightBound().intersects(object.getBoundCore())){
			
			try {
				setMoveStep(0);
				Thread.sleep(300);
			
			Magic.addInstance(spellType, this);
			
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void sprintToObject(Moveable object, int radius){
		
		Rectangle sprintBound = new Rectangle(getX()-radius, getY()-radius, radius*3, radius *3);
		
		if(!sprintBound.intersects(object.getBound())){
			//if(getSpeedUp() == 0.7)
				setSpeedUp(getSpeedUp()+0.01);
				
			System.err.println("sprint@speed:"+getSpeedUp());
		}
			
		else{
			setSpeedUp(0.7);
			System.out.println("slowDown");
		}

	}
	
	public void goBerserk(){
	
			
			Magic.addInstance(0, this);
			startWaitTimer(1000);
			setAttack();
			
			if(this.getBoundHitSpace().intersects(Player.getInstance().getBound())){
				Player.getInstance().setLife(Player.getInstance().getLife()-1.5);
				Player.getInstance().setObjectBack(10, 0, false, null);
				this.startWaitTimer(2000);
			}
		
			int random = new Random().nextInt(100 - 0 + 1) + 0;
			
			if(random < 20){
				
				if(getSightBound().intersects(Board.screenN))
					ItemListManager.dropItem(getX(), getY()-100, 0, 2, 0);
				if(getSightBound().intersects(Board.screenE))
					ItemListManager.dropItem(getX()+100, getY(), 0, 2, 0);
				if(getSightBound().intersects(Board.screenS))
					ItemListManager.dropItem(getX(), getY()+100, 0, 2, 0);
				if(getSightBound().intersects(Board.screenW))
					ItemListManager.dropItem(getX()-100, getY(), 0, 2, 0);
				
			}
			
			
			
		
	}
	
	public Rectangle getSightBound(){
		
		Rectangle bound = getBoundDirection(0);
		Rectangle sight = bound;
		
		switch(getLastDirection()){
		case(1):	sight = new Rectangle(bound.x, bound.y-630, bound.width, bound.height+630);break;
		case(3):	sight = new Rectangle(bound.x, bound.y, bound.width+810, bound.height);break;
		case(5):	sight = new Rectangle(bound.x, bound.y, bound.width, bound.height+630);break;
		case(7):	sight = new Rectangle(bound.x-810, bound.y, bound.width+810, bound.height);break;

		}
		
		return sight;
		
	}


}
