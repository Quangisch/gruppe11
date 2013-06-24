package objects;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import core.GameManager;
import core.ItemListManager;

public class ItemAchieve extends Item implements Runnable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 957778348175896766L;
	transient private Thread runThread;
	transient private ScheduledExecutorService execRun;
	
	private ItemAchieve(int xPos, int yPos, int[]itemIDData){
		System.err.println("construct ItemAchieve");
		setX(xPos+12);
		setY(yPos+1);
		setMoveableType(-10);
		initializeInstance(itemIDData);
	}
	
	public void run(){
		
		if(Player.getInstance().getInteractionLock()){
			move();
			setStaticSubSprite(3,0.1);
			
		} else 
			deleteInstance();
		
		if(GameManager.getInstance().menu)
			deleteInstance();
	}
	
	private void initializeInstance(int[]itemIDData){
		
		
		int[] data = ItemListManager.getItemData(itemIDData[0],itemIDData[1],itemIDData[2]);
		File file = ItemListManager.getItemFile(itemIDData[0],itemIDData[1],itemIDData[2]);
		
		setStaticX(data[3]*25);
		setStaticY(data[4]*25);
		setStaticCycle(data[5]);
		setFile(file);
		
		loadSprite();

		runThread = new Thread(this);
		execRun = Executors.newSingleThreadScheduledExecutor();
		execRun.scheduleWithFixedDelay(runThread, 0, 10, TimeUnit.MILLISECONDS);

		GameManager.getInstance().addGameObject(this);
	}
	
	private void deleteInstance(){
		System.out.println("delete ItemAchieve");
		setAlive(false);
		setVisible(false);
		GameManager.getInstance().updateGameObject();
		execRun.shutdown();
		execRun = null;
	}

	public static void addInstance(int xPos, int yPos, int[]itemIDData){
		new ItemAchieve(xPos, yPos, itemIDData);
	}
	
}
