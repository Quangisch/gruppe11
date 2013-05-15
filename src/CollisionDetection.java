import java.awt.Rectangle;



public class CollisionDetection implements Runnable{
	
	
	
	public CollisionDetection(){
		
	}

	public void run(){
		if(Board.printMsg)
			System.out.println("CollisionDetection run");
			
		try {
			check();
		} catch (InterruptedException ie) {
			System.err.println("CollisionDetection:" +ie);
		}
		
		
	}
	
	private  void check() throws InterruptedException{
		//PlayerBounds - MapBounds
		//Player.set
		
		//System.out.println("moveable" +Camera.moveFocus);
			
		
		//check Player position relative to windowBorders
		if(!DungeonNavigator.dungeon){
			if(Player.playerBoundS.intersects(OverWorldMap.BoundN)){Camera.scrollLock = 1;}
			if(Player.playerBoundN.intersects(OverWorldMap.BoundS)){Camera.scrollLock = 3;}
			if(Player.playerBoundE.intersects(OverWorldMap.BoundW)){Camera.scrollLock = 2;}
			if(Player.playerBoundW.intersects(OverWorldMap.BoundE)){Camera.scrollLock = 4;}
		}
		
		
		//handles overworld <-> dungeon navigation
		if(OverWorldMap.overWorld){
			OverWorldMap.setBounds();
			Camera.cameraLock = false;
			
			switch(OverWorldMap.areaID){

				
				case 1:	if(Player.playerBoundN.intersects(OverWorldMap.Over1Dungeon1)){
					Player.x = 220; Player.y = 500;
					DungeonNavigator.areaID = 1;
					DungeonNavigator.x = 0; DungeonNavigator.y = 3;
					System.out.println("leave OverWorldMap1 -> dungeon1");
					OverWorldMap.overWorld = false;DungeonNavigator.dungeon = true;}
				
			}
			
		}
		
		
		if(DungeonNavigator.dungeon){
			DungeonNavigator.setBounds();
			Camera.cameraLock = true;
			
			//TODO//DungeonCollision.start();
		}
		
	}
	

	
}
