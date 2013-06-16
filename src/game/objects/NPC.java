package game.objects;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import core.GameManager;

abstract class NPC extends NPCLogic {
	
	
	
	protected NPC(){

		
	}
	
	
	
	
	
	protected void initializeNeutralNPC(int posX, int posY){
			setMoveableType(0);
			initializeAttributes(2, 9999, true,0,75,45,20);
			initializePosition(posX, posY, 5);
			initializeImage(neutralNPC, 90,120,8);
			
	}

}
