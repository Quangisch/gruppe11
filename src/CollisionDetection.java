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
			
		
		//check Player-relative WindowBorders
		if(Player.playerBoundS.intersects(OverWorldMap.BoundN)){Camera.scrollLock = 1;}
		if(Player.playerBoundN.intersects(OverWorldMap.BoundS)){Camera.scrollLock = 3;}
		if(Player.playerBoundE.intersects(OverWorldMap.BoundW)){Camera.scrollLock = 2;}
		if(Player.playerBoundW.intersects(OverWorldMap.BoundE)){Camera.scrollLock = 4;}
		
		//check enter/leave overworld-dungeon
		OverWorldMap.setBounds();
		if(Player.playerBoundN.intersects(OverWorldMap.intoDungeon1)){System.out.println("enter dungeon"); OverWorldMap.overWorld = false; OverWorldMap.dungeon = true;}
		if(Player.playerBoundN.intersects(OverWorldMap.intoDungeon1)){System.out.println("enter dungeon"); OverWorldMap.overWorld = false; OverWorldMap.dungeon = true;}
			
			
			/*
			if(Map.BoundS.intersects(Player.BoundS)||Map.BoundW.intersects(Player.BoundW)
					||Map.BoundN.intersects(Player.BoundN)||Map.BoundE.intersects(Player.BoundE))
				Camera.cameraOn = true;
			*/
	}
}
