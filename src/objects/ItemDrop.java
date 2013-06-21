package objects;


import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import core.GameManager;

public class ItemDrop extends Item{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -579282741659010805L;
	private int duration;
	private int counter;
	private int[] itemIDData = new int[3];
	
	transient private Thread runThread = new Thread(new RunTimer());
	transient private ScheduledExecutorService execRun = Executors.newSingleThreadScheduledExecutor();
	
	private ItemDrop(int x, int y, int[] data, File file, int duration){
		setMoveableType(-10);
		System.out.println("--> construct new Item");

		setX(x);
		setY(y);
	
		this.duration = duration;
		initializeItemDrop(data, file);
	}
	
	public void running(){
		
		//System.out.println(getUpLock()+","+getDownLock()+"__"+getRightLock()+","+getLeftLock());
		
		move();
	
		//System.out.println("Item alive@"+counter+", to "+duration);
		//System.out.println("Item visible:"+getVisibleDrawable());
		
		setStaticSubSprite(4,0.1);
		
		if(Player.getInstance().getBoundDirection(0).intersects(getBoundCore()) || GameManager.getInstance().menu){
			Player.getInstance().addItem(itemIDData);
			setAlive(false);
		}
		
	}

	private void initializeItemDrop(int[] data, File file){
		
		/*
		 * itemData[0] = ID;
			itemData[1] = type;
			itemData[2] = member;
			itemData[3] = xRow;
			itemData[4] = yRow;
			itemData[5] = cycle;
		 */
		itemIDData[0] = data[0];
		itemIDData[1] = data[1];
		itemIDData[2] = data[2];
		
		setStaticX(data[3]*25);
		setStaticY(data[4]*25);
		setStaticCycle(data[5]);
		setFile(file);
		loadSprite();
		
		runThread = new Thread(new RunTimer());
		execRun = Executors.newSingleThreadScheduledExecutor();
		execRun.scheduleWithFixedDelay(runThread, 10, 20, TimeUnit.MILLISECONDS);
		
		GameManager.addGameObject(this);
	}
	
	public static ItemDrop addInstance(int xPosition, int yPosition, int[] data, File file,int duration){
		System.out.println("add Item@Pos "+xPosition+"x"+yPosition);
		return new ItemDrop(xPosition, yPosition, data, file, duration);
	}
	
	private class RunTimer implements Runnable{
		
		private RunTimer(){
			
		}
		
		public void run(){
			
			//System.out.println("runnerTimer");
			
			if(counter == duration){
				setAlive(false);
			}
			
			counter++;
	
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
