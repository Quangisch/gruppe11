package game.objects;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import core.Board;
import core.FileLink;
import core.GameManager;

public class Magic extends Initializer implements FileLink{
	
	private Timer runTimer;
	private TimerTask runTask;
	private Magic magicInstance;
	
	private int type;
	private Moveable caster;
	private ArrayList<Moveable> moveableList;
	
	private int direction;
	private static int[] speed = {10, 15};
	
	private static int[] xOffset = {2, 2};
	private static int[] yOffset = {3, 3};
	
	private static int[] width = {50, 50};
	private static int[] height = {50, 50};
	
	private static double[] damage = {0.8, 1.6};
	
	

	private Magic(int type, Moveable caster){
		
		this.type = type;
		this.caster = caster;
		System.out.println("new MagicCast @type "+type);
		
		initializeMagic();
	
	}
	
	public void initializeMagic(){
		
		File magicFile = null;
		moveableList = GameManager.getMoveableList();
		direction = caster.getLastDirection();
		
		switch(direction){
		
		case(1):	setMoveUp(true);
					break;
		case(2):	setMoveUp(true); setMoveRight(true);
					break;
		case(3):	setMoveRight(true);
					break;
		case(4):	setMoveRight(true); setMoveDown(true);
					break;
		case(5):	setMoveDown(true);
					break;
		case(6):	setMoveDown(true); setMoveLeft(true);
					break;
		case(7):	setMoveLeft(true);
					break;
		case(8):	setMoveLeft(true); setMoveUp(true);
					break;
					
		}
		
		switch(type){
		case(0):	magicFile = magicID00;
					break;
		case(1):	magicFile = magicID01;
					break;
		}

		initializeImage(magicFile, width[type], height[type], 6);
		initializeAttributes(speed[type], 1, true, -15,0,40,40);
		initializePosition(caster.getX(), caster.getY()+30, direction);
		setWidth(50);
		setHeight(50);
		setSubRowY(0);
		
		runTimer = new Timer();
		runTask = new RunTask();
		runTimer.scheduleAtFixedRate(runTask, 10, 20);
		Board.getInstance().addDrawable(this);
		
		
	}
	
	private void running(){
		
		move();
		
		if(getAlive()){
		
			//System.out.println("Position@"+getX()+"x"+getY()+",visible:"+getVisible());
			//System.out.println(getLastDirection()+","+getMoveUp()+"x"+getMoveRight()+"x"+getMoveDown()+"x"+getMoveLeft());
			
			for(int index = 0; index < moveableList.size(); index++){
				

				
				if(getBoundCore().intersects(moveableList.get(index).getBoundCore()) && !moveableList.get(index).equals(caster)){
					moveableList.get(index).setLife(moveableList.get(index).getLife()-damage[type]);
					moveableList.get(index).setObjectBack(20,true,this.getBoundCore());
					setAlive(false);
					System.out.println("magicHit");
					break;
				}
			}
			
			
			//
			if((getX() > 1000 && (direction == 2 || direction == 3 || direction == 4))
					|| (getX() < -200 && (direction == 6 || direction == 7 || direction == 8))
					|| (getY() < -200 && (direction == 8 || direction == 1 || direction == 2))
					|| (getY() > 800 && (direction == 4 || direction == 5 || direction == 6)))
				setAlive(false);
		} else {
			
		}

	}
	

	
	public static Magic getInstance(int type, Moveable caster){
		return new Magic(type, caster);
	}
	
	private class RunTask extends TimerTask{
		
		private RunTask(){
			
		}
		
		public void run(){
			
			
			
			if(getAlive())
				running();
			
			else {
				setVisible(false);
				magicInstance = null;
				runTimer.cancel();
				runTimer.purge();
				runTimer = null;
				runTask = null;
				System.err.println("remove Magic, @type "+type);
				
			}
		}
		
	}

}

