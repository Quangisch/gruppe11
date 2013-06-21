package objects;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import core.FileLink;
import core.GameManager;
import core.Sound;

public class Magic extends Initializer implements FileLink{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3939105970367590365L;
	transient private Thread runThread;
	transient private ScheduledExecutorService execRun = Executors.newSingleThreadScheduledExecutor();

	private int type;
	private Moveable caster;
	private ArrayList<Moveable> moveableList;
	
	private int direction;
	private int[] speed = {10, 15};
	
	private int[] width = {50, 50};
	private int[] height = {50, 50};
	
	private double[] damage = {0.8, 1.4};
	


	private Magic(int type, Moveable caster){
		setMoveableType(-20);
		this.type = type;
		this.caster = caster;
		System.out.println("new MagicCast @type "+type);
		
		initializeMagic();
		Sound.getInstance().playSound(3);
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
		
		setCoreX(10);
		setCoreY(10);
		setCoreWidth(10);
		setCoreHeight(10);
		
		runThread = new Thread(new RunTimer());
		execRun = Executors.newSingleThreadScheduledExecutor();
		execRun.scheduleWithFixedDelay(runThread, 10, 20, TimeUnit.MILLISECONDS);
		
		GameManager.addGameObject(this);
		
	}
	
	private void running(){

		move();
		
		//System.out.println("Position@"+getX()+"x"+getY()+",visible:"+getVisibleDrawable());
		//System.out.println(getLastDirection()+","+getMoveUp()+"x"+getMoveRight()+"x"+getMoveDown()+"x"+getMoveLeft());
		
		for(int index = 0; index < moveableList.size(); index++){

			if(getBoundCore().intersects(moveableList.get(index).getBound()) && !moveableList.get(index).equals(caster) && !moveableList.get(index).equals(this) && moveableList.get(index).getMoveableType() != -10){
				setAlive(false);
				Damage.inflictDamage(1, damage[type], moveableList.get(index));
				moveableList.get(index).setObjectBack(20,0,true,this.getBoundCore());
				System.out.println("magicHit");
				Sound.getInstance().playSound(3);
				break;
			}

		}
		
		
		//
		if((getX() > 1000 && (direction == 2 || direction == 3 || direction == 4))
				|| (getX() < -200 && (direction == 6 || direction == 7 || direction == 8))
				|| (getY() < -200 && (direction == 8 || direction == 1 || direction == 2))
				|| (getY() > 800 && (direction == 4 || direction == 5 || direction == 6))
				|| !GameManager.getInstance().mapLoaded)
			setAlive(false);
		
		
		
			
	
	}
	

	
	public static Magic addInstance(int type, Moveable caster){
		return new Magic(type, caster);
	}
	
	private class RunTimer implements Runnable{
		
		private RunTimer(){
			
		}
		
		public void run(){
			
			
			
			if(getAlive())
				running();
			
			else {
				setVisible(false);
				execRun.shutdown();
				execRun = null;
				
			}
		}
		
	}

}

