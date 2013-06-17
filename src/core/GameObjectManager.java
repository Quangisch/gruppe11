package core;

import game.objects.ItemAchieve;
import game.objects.MapObject;
import game.objects.Player;

import java.util.ArrayList;

public class GameObjectManager{
	
	//private ArrayList<O> gameObjectList = new ArrayList<O>();
	//private static int[][][] doorIDCounter = new int[4][4][10];
	private static GameObjectManager gameObjectManager;
	private ArrayList<DoorIDStatus<Integer,Boolean>> doorIDList = new ArrayList<DoorIDStatus<Integer, Boolean>>();
	private ArrayList<BossIDStatus<Integer, Boolean>> bossIDList = new ArrayList<BossIDStatus<Integer, Boolean>>();
	public ArrayList<Treasure<Integer[],Integer[],Boolean>> treasureIDCounter = new ArrayList<Treasure<Integer[],Integer[],Boolean>>();

	
	private GameObjectManager(){
		//gameObjectList.add(object);
	}
	

	public void getGameObject(int type, int ID){
		
		int index = 0;
		//Enemy
		if(type > 0){
			
		}
		//neutral NPC
		if(type < 0){
			
		}
		
		//static Object
		if(type == 0){
			
		}
	}
	
public final boolean constructTreasure(int xMap, int yMap, int xPos, int yPos, int[]data){
		
		boolean newTreasure = true;
		
		Integer[] positionID = {xMap, yMap, xPos, yPos};
		Integer[] itemData = new Integer[data.length];
		int i = 0;
		for (int value : data) {
		    itemData[i++] = Integer.valueOf(value);
		}
		Boolean open = new Boolean(false);
		
		
		for(int index = 0; index < treasureIDCounter.size(); index++){
			if(treasureIDCounter.get(index).getPositionID() == positionID){
				System.out.println("-->treasure already exsists");
				newTreasure = false;
				break;
			}	
		}
		
		if(newTreasure){
			System.out.println("-->register new Treasure");
			Treasure<Integer[],Integer[],Boolean> treasure = new Treasure<Integer[],Integer[],Boolean>(positionID,itemData,open);
			treasureIDCounter.add(treasure);
		}
	
		return newTreasure;
	}
	

	//public static void openDoor(int xMap, int yMap, int ID){doorIDCounter[xMap][yMap][ID] = 1;}
	
	public void constructBoss(int ID){
		BossIDStatus<Integer, Boolean> boss = new BossIDStatus<Integer, Boolean>(ID,false);
		bossIDList.add(boss);
	}
	
	public void constructDoor(int ID){
		DoorIDStatus<Integer, Boolean> door = new DoorIDStatus<Integer, Boolean>(ID,false);
		doorIDList.add(door);
	}
	
	public final boolean openTreasureBox(boolean openNow, MapObject treasureObject){
		boolean treasureOpen = false;
		
		
		for(int index = 0; index < treasureIDCounter.size(); index++){
			//System.out.println("compTreasure@PositionID:"+treasureIDCounter.get(index).getPositionID()[0]+"x"+treasureIDCounter.get(index).getPositionID()[1]+","+treasureIDCounter.get(index).getPositionID()[2]+"x"+treasureIDCounter.get(index).getPositionID()[3]);
			//System.err.println("openTreasure@PositionID:"+treasureObject.getPositionID()[0]+"x"+treasureObject.getPositionID()[1]+","+treasureObject.getPositionID()[2]+"x"+treasureObject.getPositionID()[3]);
			//System.out.println(treasureIDCounter.get(index).getPositionID()[2]+"compTo"+treasureObject.getPositionID()[2]+"equals:"+treasureIDCounter.get(index).getPositionID()[2].equals(treasureObject.getPositionID()[2]));
			if(treasureIDCounter.get(index).getPositionID()[0].equals(treasureObject.getPositionID()[0])
					&& treasureIDCounter.get(index).getPositionID()[1].equals(treasureObject.getPositionID()[1])
					&& treasureIDCounter.get(index).getPositionID()[2].equals(treasureObject.getPositionID()[2])
					&& treasureIDCounter.get(index).getPositionID()[3].equals(treasureObject.getPositionID()[3])){
				
				
				if(treasureIDCounter.get(index).getOpen() == false){
					
					if(openNow){
						System.out.println("hit");
						 int[] treasureItemData = new int[treasureIDCounter.get(index).getItemData().length];
						 
						 int i = 0;
							for (int value : treasureIDCounter.get(index).getItemData()) {
							    treasureItemData[i++] = Integer.valueOf(value);
							}
						Player.getInstance().setAchieve();
						Player.getInstance().addItem(treasureItemData);
						ItemAchieve.addInstance(Player.getInstance().getX(), Player.getInstance().getY()+1, treasureItemData);
						
						treasureIDCounter.get(index).setOpen(true);
					}
			 
			} else
				return (treasureOpen = true);
				
			}
			
		}
		
		return treasureOpen;
	}
	
	public boolean getDoorStatusOpen(int ID){
		boolean lockOpen = false;
		
		for(int index = 0; index < doorIDList.size(); index++){
			
			if(doorIDList.get(index).getID() == ID && doorIDList.get(index).getLockStatus()){
				lockOpen = true;
				break;
			}
		}

		return lockOpen;
	}
	
	public boolean getBossStatusDefeated(int ID){
		boolean defeated = false;
		
		for(int index = 0; index < bossIDList.size(); index++){
			
			if(bossIDList.get(index).getID() == ID && bossIDList.get(index).getDefeatedStatus()){
				defeated = true;
				break;
			}
		}

		return defeated;
	}
	
	public void openDoor(int ID){
		
		for(int index = 0; index < doorIDList.size(); index++){
			
			if(doorIDList.get(index).getID() == ID){
				doorIDList.get(index).setLockStatus(true);
				System.err.println("open Door@ID:"+ID+"and lock:"+doorIDList.get(index).getLockStatus());
				break;
			}
		}
	}
	
	public void defeatBoss(int ID){
		
		for(int index = 0; index < bossIDList.size(); index++){
			
			if(bossIDList.get(index).getID() == ID){
				bossIDList.get(index).setDefeatedStatus(true);
				System.err.println("defeated Boss@ID:"+ID+"and defeated:"+bossIDList.get(index).getDefeatedStatus());
				break;
			}
		}
	}
	
	public static void resetInstance(){
		if(gameObjectManager != null)
			gameObjectManager = new GameObjectManager();
	}
	
	public static GameObjectManager getInstance(){
		if(gameObjectManager == null)
			gameObjectManager = new GameObjectManager();
		return gameObjectManager;
	}
	
	public class DoorIDStatus<I, L>{
		final I ID;
		L lockOpen;
		public DoorIDStatus(I ID, L lockOpen){
			this.ID = ID;
			this.lockOpen = lockOpen;
			System.err.println("construct Door@ID:"+ID+"and lock:"+lockOpen);
		}
		public I getID(){return ID;}
		public L getLockStatus(){return lockOpen;}
		
		public void setLockStatus(L open){this.lockOpen = open;}
	}
	
	public class BossIDStatus<I, D>{
		final I ID;
		D defeated;
		
		public BossIDStatus(I ID, D defeated){
			this.ID = ID;
			this.defeated = defeated;
			System.err.println("construct Boss@ID:"+ID+"and defeated:"+defeated);
		}
		
		public I getID(){return ID;}
		public D getDefeatedStatus(){return defeated;}
		
		public void setDefeatedStatus(D defeated){this.defeated = defeated;}
	}
	
	public class Treasure<P, I, O>{
		final P positionID; //int[] 0: xMap; 2: xPos; 3: yPos; (4: 0 = dungeon; 1 = overWorld;)
		final I itemData; //int[] 0: ID; 1: type; 2: member
		O open;
		private Treasure(P positionID, I itemData, O open){
			this.positionID = positionID;
			this.itemData = itemData;
			this.open = open;
		}
		
		public P getPositionID(){return positionID;}
		public I getItemData(){return itemData;}
		public O getOpen(){return open;}
		public void setOpen(O open){this.open = open;}
	}


}
