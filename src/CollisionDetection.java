import java.awt.Rectangle;



public class CollisionDetection implements Runnable{
	
	public CollisionDetection(){
		
	}

	public void run(){
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
				
			if(Player.BoundS.intersects(Map.BoundN)){
				//Camera.cameraX -= 630;
				Camera.scrollLock = 1;
			}
				
			if(Player.BoundN.intersects(Map.BoundS)){
				Camera.scrollLock = 3;
				//Camera.cameraX += 630;
				
			}
			
			if(Player.BoundE.intersects(Map.BoundW)){
				Camera.scrollLock = 2;
				//Camera.cameraX -= 810;

			}
				
			if(Player.BoundW.intersects(Map.BoundE)){
				Camera.scrollLock = 4;
				//Camera.cameraX += 810;
	
			}
	
			/*
			//Camera.lo
			
			if(Camera.cameraX < Player.absoluteX) {
				Camera.cameraX += Camera.cameraSpeed;
				Player.x -= Camera.cameraSpeed;
			}
			if(Camera.cameraX > Player.absoluteX) {
				Camera.cameraX -= Camera.cameraSpeed;
				Player.x += Camera.cameraSpeed;
			}
		
			if(Camera.cameraY < Player.absoluteY) {
				Camera.cameraY += Camera.cameraSpeed;
				Player.y -= Camera.cameraSpeed;
			}
			if(Camera.cameraY > Player.absoluteY) {
				Camera.cameraY -= Camera.cameraSpeed;
				Player.y += Camera.cameraSpeed;
			}
			*/
			
		
		 
		
			
			
			
			/*
			if(Map.BoundS.intersects(Player.BoundS)||Map.BoundW.intersects(Player.BoundW)
					||Map.BoundN.intersects(Player.BoundN)||Map.BoundE.intersects(Player.BoundE))
				Camera.cameraOn = true;
			*/
	}
}
