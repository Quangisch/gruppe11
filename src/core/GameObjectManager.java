package core;

import java.util.ArrayList;

import map.DungeonNavigator;

public class GameObjectManager{
	
	//private ArrayList<O> gameObjectList = new ArrayList<O>();
	//private static int[][][] doorIDCounter = new int[4][4][10];
	private static GameObjectManager gameObjectManager;
	private static ArrayList<DoorIDStatus<Integer,Boolean>> doorIDList = new ArrayList<DoorIDStatus<Integer, Boolean>>();
	
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
	
	
	//public static void openDoor(int xMap, int yMap, int ID){doorIDCounter[xMap][yMap][ID] = 1;}
	
	public void constructDoor(int ID){
		DoorIDStatus<Integer, Boolean> door = new DoorIDStatus<Integer, Boolean>(ID,false);
		doorIDList.add(door);
	}
	
	public static boolean getDoorStatusOpen(int ID){
		boolean lockOpen = false;
		
		for(int index = 0; index < doorIDList.size(); index++){
			
			if(doorIDList.get(index).getID() == ID && doorIDList.get(index).getLockStatus()){
				lockOpen = true;
				break;
			}
		}

		return lockOpen;
	}
	
	public static void openDoor(int ID){
		
		for(int index = 0; index < doorIDList.size(); index++){
			
			if(doorIDList.get(index).getID() == ID){
				doorIDList.get(index).setLockStatus(true);
				System.err.println("open Door@ID:"+ID+"and lock:"+doorIDList.get(index).getLockStatus());
				break;
			}
		}
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

}
