
public class Camera implements Runnable{
	static int cameraX, cameraY;
	static int scrollX = 0;
	static int scrollY = 0;
	final static int cameraSpeed = 2;
	static boolean moveFocus = false;
	static boolean cameraOn = true;
	static int scrollLock = 0;
	
	
	public Camera (){
		cameraX = Player.x;
		cameraY = Player.y;
	}
	
	public void run(){
	
		moveCamera();
		scrollCamera();
	}
	
	public static void moveCamera(){
		//when S is pressed
		if(moveFocus) {
			
			if(Camera.cameraX < Player.absoluteX) {
				Camera.cameraX += cameraSpeed;
				Player.x -= cameraSpeed;
			}
			if(Camera.cameraX > Player.absoluteX) {
				Camera.cameraX -= cameraSpeed;
				Player.x += cameraSpeed;
			}
		
			if(Camera.cameraY < Player.absoluteY) {
				Camera.cameraY += cameraSpeed;
				Player.y -= cameraSpeed;
			}
			if(Camera.cameraY > Player.absoluteY) {
				Camera.cameraY -= cameraSpeed;
				Player.y += cameraSpeed;
			}
			
		}
		
		
			
			/*
			while (Player.x != 405-15*3 && Player.y != 315-20*3){
				if(Player.x > 405-15*3 )
					Camera.cameraX--;
				if(Player.x < 405-15*3 )
					Camera.cameraX++;
				if(Player.x > 315-20*3 )
					Camera.cameraY--;
				if(Player.x < 315-20*3 )
					Camera.cameraY++;
			}
			*/

				
	}
	
	
	public static void scrollCamera(){
		//scroll between maps
		if(Camera.scrollLock == 1){
			System.out.println("scrollY: " +Camera.scrollY);
			
			Camera.cameraY -= 5;
			Camera.scrollY -= 5;
			Player.y += 5;
			
			if(Camera.scrollY == -500){
				Camera.scrollLock = 0;
				Camera.scrollY = 0;
			}
	
		}
		
		if(Camera.scrollLock == 3){
			System.out.println("scrollY: " +Camera.scrollY);
			
			Camera.cameraY += 5;
			Camera.scrollY += 5;
			Player.y -= 5;
			
			if(Camera.scrollY == 530){
				Camera.scrollLock = 0;
				Camera.scrollY = 0;
			}
				
		}	
	}
	
	public static void toogleCamera(boolean cOn){
		cameraOn = cOn;
		if(cameraOn){
			if(Player.absoluteX > 0)
				Camera.cameraX = Player.absoluteX;
			if(Player.absoluteY > 0)
				Camera.cameraY = Player.absoluteY;
			Player.x = 405-15*3;
			Player.y = 315-20*3;
			//moveCamera();
		}
		if(!cameraOn){
			
		}
		
			
		
	}
	
	

}
