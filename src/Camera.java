
public class Camera implements Runnable{
	static int cameraX, cameraY;
	final static int cameraOffsetX = 405;
	final static int cameraOffsetY = 315;
	final static int cameraSpeed = 2;
	static boolean moveable = false;
	static boolean cameraOn = true;
	
	
	public Camera (){
		cameraX = Player.x;
		cameraY = Player.y;
	}
	
	public void run(){
		//moveCamera();
		/*
		if (cameraX > Player.absoluteX)
			Camera.cameraX--;
		
		if (cameraX < Player.absoluteX)
			Camera.cameraX++;
		
		if (cameraY > Player.absoluteY)
			Camera.cameraX--;
		
		if (cameraY < Player.absoluteY)
			Camera.cameraX++;
		*/
		if(moveable)
		moveCamera();
	}
	
	public static void moveCamera(){
		System.out.println("moveable" +moveable);
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
