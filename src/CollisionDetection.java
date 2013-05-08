import java.awt.Rectangle;



public class CollisionDetection implements Runnable{
	
	public CollisionDetection(){
		
	}

	public void run(){
		//if(Board.printMsg)
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
		if(Player.playerBoundS.intersects(Map.BoundN)){Camera.scrollLock = 1;}
		if(Player.playerBoundN.intersects(Map.BoundS)){Camera.scrollLock = 3;}
		if(Player.playerBoundE.intersects(Map.BoundW)){Camera.scrollLock = 2;}
		if(Player.playerBoundW.intersects(Map.BoundE)){Camera.scrollLock = 4;}
		
		//check Player-dungeon
		Map.setBounds();
		if(Player.playerBoundN.intersects(Map.intoDungeon1)){System.out.println("enter dungeon"); Map.overWorld = false; Map.dungeon = true;}
			
	
			
			/*
			if(Map.BoundS.intersects(Player.BoundS)||Map.BoundW.intersects(Player.BoundW)
					||Map.BoundN.intersects(Player.BoundN)||Map.BoundE.intersects(Player.BoundE))
				Camera.cameraOn = true;
			*/
	}
}
