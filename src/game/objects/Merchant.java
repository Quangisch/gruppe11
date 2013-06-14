package game.objects;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import core.ItemListManager;
import core.FileLink;
import core.GameManager;
import core.PlayerInterface;

public class Merchant extends NPC implements Runnable, FileLink{

	private static Merchant merchant;
	private Thread runThread;
	private ScheduledExecutorService execRun;
	
	private String[] text = {"What do you want to buy?",
			"1 HP-Potion   2G/n2 ManaPotion  2G/n3 Armor Lvl2 10G/n4 Magic Lvl2 15G", 
			"Thank you!", 
			"You don't have enough money...",
			"You don't need anything?"};
	private int textCounter = 0;
	
	private Merchant(int posX, int posY){
		System.out.println("--> construct Merchant");
		initializeNeutralNPC(posX, posY);
		initializeThread();
		
	}
	
	
	public void run(){
		
		//System.out.println("Interact: "+GameManager.interact+", showText: "+GameManager.showIngameText+", prompt: "+GameManager.promptText);
		
		if(getAlive()){
			move();
			
			if(getBoundCore().intersects(Player.getInstance().getBoundCore()))
				Player.getInstance().setObjectBack(10, 0, false, null);
			
			if(this.getBound().intersects(Player.getInstance().getBoundCore()) && GameManager.interact){
				GameManager.showIngameText = true;
				setLastDirection(7);
				interact();
			} else {
				GameManager.interact = false;
				GameManager.showIngameText = false;
				textCounter = 0;
				setLastDirection(5);
			}
			
		} else
			deleteInstance();
		
		
	}
	
	private void interact(){
		
		boolean prompt = GameManager.promptText;
		
		if(GameManager.promptText){
			System.out.println("promptText:true -> buildText");
			PlayerInterface.getInstance().setText(text[textCounter]);
			PlayerInterface.getInstance().buildText();
			
		}
		
		if(textCounter == 0 && prompt){
			prompt = false;
			textCounter = 1;
		}
		
		
		
			
		if(textCounter == 1){
			/*
			if(prompt){
				prompt = false;
				textCounter = 4;
				PlayerInterface.getInstance().setText(text[textCounter]);
				PlayerInterface.getInstance().buildText();
				
			}
			*/
	
			if(GameManager.interactKey == 1){
				
				boolean buy = Player.getInstance().useMoney(2);
				
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,1,0,0);
					textCounter = 2;
					this.startWaitTimer(500);
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				
				GameManager.interactKey = 0;
				GameManager.promptText = true;
				System.out.println("Buy Potion@"+buy);
			}
			if(GameManager.interactKey == 2){
				boolean buy = Player.getInstance().useMoney(2);
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,1,1,0);
					textCounter = 2;
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				GameManager.interactKey = 0;
				GameManager.promptText = true;
			}
			if(GameManager.interactKey == 3){
				boolean buy = Player.getInstance().useMoney(10);
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,3,0,0);
					textCounter = 2;
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				GameManager.promptText = true;
				GameManager.interactKey = 0;
			}
			if(GameManager.interactKey == 4){
				boolean buy = Player.getInstance().useMoney(15);
				if(buy){
					ItemListManager.dropItem(getX(),getY()+50,4,0,1);
					textCounter = 2;
					setLastDirection(5);
				}
					
				else
					textCounter = 3;
				GameManager.interactKey = 0;
				GameManager.promptText = true;
			}
		}
			
		
		if(textCounter == 4 && prompt)
			GameManager.interact = false;
		
	
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
	
	public static Merchant getInstance(int posX, int posY){
		if(merchant == null)
			merchant = new Merchant(posX, posY);
		return merchant;
	}
	
}
