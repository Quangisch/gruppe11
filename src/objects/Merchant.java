package objects;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import core.ItemListManager;
import core.FileLink;
import core.GameManager;
import core.PlayerInterface;

public class Merchant extends NPC implements Runnable, FileLink{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3217512311508188572L;
	private static Merchant merchant;
	transient private Thread runThread;
	transient private ScheduledExecutorService execRun;
	
	private String[] text = {"What do you want to buy?",
			"1 HP-Potion   2G/n2 ManaPotion  2G/n3 WeaponLvl2  8G/n4 Armor Lvl2 10G/n5 Magic Lvl2 12G", 
			"Thank you!", 
			"You dont have enough money...",
			"You dont need anything?"};
	private int textCounter = 0;
	private int oldCounter = -1;
	
	private Merchant(int posX, int posY){
		System.out.println("--> construct Merchant");
		initializeNeutralNPC(posX, posY);
		initializeThread();
		setMoveableType(-5);
		
	}
	
	
	public void run(){
		
		//System.out.println("Interact: "+GameManager.getInstance().interact+", showText: "+GameManager.getInstance().showIngameText+", prompt: "+GameManager.getInstance().promptText);
		
		if(getAlive()){
			move();
			
			if(getBoundCore().intersects(Player.getInstance().getBoundCore()))
				Player.getInstance().setObjectBack(10, 0, false, null);
			
			if(this.getBound().intersects(Player.getInstance().getBoundCore()) && GameManager.getInstance().interact){
				GameManager.getInstance().showIngameText = true;
				setLastDirection(7);
				interact();
			} else {
				GameManager.getInstance().interact = false;
				GameManager.getInstance().showIngameText = false;
				textCounter = 0;
				setLastDirection(5);
			}
			
		} else
			deleteInstance();
		
		
	}
	
	private void interact(){
		
		if(GameManager.getInstance().promptText){
			System.out.println("promptText:true -> buildText");
			if(oldCounter != textCounter)
				PlayerInterface.getInstance().setText(text[textCounter]);
			else{
				
				if(textCounter == 1){
					textCounter = 4;
					PlayerInterface.getInstance().setText(text[textCounter]);
				} else
					GameManager.getInstance().interact = false;
			}
				
			

			PlayerInterface.getInstance().buildText();
			oldCounter = textCounter;
		}
		
		if(textCounter == 0 && textCounter == oldCounter)
			textCounter = 1;
	
		if(textCounter == 1){
			
			if(GameManager.getInstance().interactKey == 1){
				
				boolean buy = Player.getInstance().useMoney(2);
				
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,1,0,0);
					textCounter = 2;
					this.startWaitTimer(500);
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				
				GameManager.getInstance().interactKey = 0;
				GameManager.getInstance().promptText = true;
				System.out.println("Buy Potion@"+buy);
			}
			
			if(GameManager.getInstance().interactKey == 2){
				boolean buy = Player.getInstance().useMoney(2);
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,1,1,0);
					textCounter = 2;
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				GameManager.getInstance().interactKey = 0;
				GameManager.getInstance().promptText = true;
			}
			
			if(GameManager.getInstance().interactKey == 3){
				boolean buy = Player.getInstance().useMoney(8);
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,2,0,1);
					textCounter = 2;
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				GameManager.getInstance().interactKey = 0;
				GameManager.getInstance().promptText = true;
			}
			if(GameManager.getInstance().interactKey == 4){
				boolean buy = Player.getInstance().useMoney(10);
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,3,0,1);
					textCounter = 2;
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				GameManager.getInstance().promptText = true;
				GameManager.getInstance().interactKey = 0;
			}
			if(GameManager.getInstance().interactKey == 5){
				boolean buy = Player.getInstance().useMoney(12);
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,4,0,1);
					textCounter = 2;
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				GameManager.getInstance().interactKey = 0;
				GameManager.getInstance().promptText = true;
			}
			
		}

	}
	
	
	private void initializeThread(){
		
		
		runThread = new Thread(this);
		execRun = Executors.newSingleThreadScheduledExecutor();
		
		execRun.scheduleWithFixedDelay(runThread, 10, 30, TimeUnit.MILLISECONDS);
		
		GameManager.addGameObject(this);
	}
	
	public void deleteInstance(){
		setVisible(false);
		setAlive(false);
		GameManager.updateGameObject();
		execRun.shutdown();
		execRun = null;
		merchant = null;
	}
	
	public static void resetInstance(){
		if(merchant != null){
			merchant.deleteInstance();
			merchant = null;
		}
		
	}
	
	public static Merchant getInstance(int posX, int posY){
		if(merchant == null)
			merchant = new Merchant(posX, posY);
		return merchant;
	}
	
}
