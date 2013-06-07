package core;

import java.util.ArrayList;

public class GameObjectManager <O>{
	
	private ArrayList<O> gameObjectList = new ArrayList<O>();
	
	private GameObjectManager(O object){
		gameObjectList.add(object);
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
	

}
