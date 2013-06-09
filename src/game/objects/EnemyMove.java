package game.objects;


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
	

}
