import java.lang.Math;

public class Camera implements Runnable{
	static int cameraX, cameraY;
	static int scrollX = 0;
	static int scrollY = 0;
	final static int cameraSpeed = 2;
	static boolean moveFocus = false;
	static boolean cameraOn = false;
	static int scrollLock = 0;
	
	
	public Camera (){
		//toogleCamera(true);
	}
	
	public void run(){
		if(!cameraOn){
			moveCamera();
			scrollCamera();
		}
			
	
		//System.out.println("CscrollX: " +Camera.cameraX);
	}
	
	//center player with fixed camera
	public static void moveCamera(){
		
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
				
	}
	
	//scroll between map with fixed camera
	public static void scrollCamera(){
		if(Camera.scrollLock == 1){
			System.out.println("North scrollY: " +Camera.scrollY);
			
			Camera.cameraY -= 5;
			Camera.scrollY -= 5;
			Player.y += 5;
			
			if(Camera.scrollY == -500 || Camera.cameraY <= 0){
				Camera.scrollLock = 0;
				Camera.scrollY = 0;
			}
		}
		
		if(Camera.scrollLock == 4){
			System.out.println("East scrollX: " +Camera.scrollX);
			
			Camera.cameraX += 5;
			Camera.scrollX += 5;
			Player.x -= 5;
			
			if(Camera.scrollX == 750 || Camera.cameraX >= 1890){
				Camera.scrollLock = 0;
				Camera.scrollX = 0;
			}	
		}
		
		if(Camera.scrollLock == 3){
			System.out.println("South scrollY: " +Camera.scrollY);
			
			Camera.cameraY += 5;
			Camera.scrollY += 5;
			Player.y -= 5;
			
			if(Camera.scrollY == 530 || Camera.cameraY >= 2065){
				Camera.scrollLock = 0;
				Camera.scrollY = 0;
			}	
		}	
		
		if(Camera.scrollLock == 2){
			System.out.println("West scrollX: " +Camera.scrollX);
			
			Camera.cameraX -= 5;
			Camera.scrollX -= 5;
			Player.x += 5;
			
			if(Camera.scrollX == -750 || Camera.cameraX <= 0){
				Camera.scrollLock = 0;
				Camera.scrollX = 0;
			}
		}
		
		
	}
	
	public static void toogleCamera(boolean cOn){
		cameraOn = cOn;
		if(cameraOn){
			if(Player.absoluteX > 0)
				Camera.cameraX = Player.absoluteX + Math.abs((405-15*3)-Player.x) - Camera.cameraX;
			if(Player.absoluteY > 0)
				Camera.cameraY = Player.absoluteY + Math.abs((315-20*3)-Player.y) - Camera.cameraY;
			
			Player.x = 405-15*3;
			Player.y = 315-20*3;
			
			//moveCamera();
		}
		if(!cameraOn){
			
		}	
	}
	
	//allign Camera with auto Camera
	public static void alignCamera(){
		
    	if(Player.absoluteY > 2065) {
	    	Camera.cameraY = 2700-630;
	    	Player.y = - 2700 + Player.absoluteY+810;
	    }
		if(Player.absoluteY < 0){
			Camera.cameraY = 0;
			Player.y = Player.absoluteY+315;
		}
		
	    if(Player.absoluteX > 1880) {
	    	Camera.cameraX = 2700-810;
	    	Player.x = -2700 + Player.absoluteX + 810+315;
	    }
	    if(Player.absoluteX < 0) {
	    	Camera.cameraX = 0;
	    	Player.x = Player.absoluteX+405;
	    }   
	}
	
	

}
