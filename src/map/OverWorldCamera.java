package map;

import java.lang.Math;

import characters.Player;

public class OverWorldCamera implements Runnable{
	
	Player player;
	
	
	int cameraX, cameraY;
	int scrollX = 0;
	int scrollY = 0;
	final int SCROLLSPEEDX = 40; //
	final int SCROLLSPEEDY = 30; //
	final int CAMERASPEED = 2;
	boolean moveFocus = false;
	boolean cameraOn = false;
	boolean cameraLock = false;
	int scrollLock = 0;
	
	
	public OverWorldCamera (Player player){
		System.err.println("->OverWorldCamera");
		this.player = player;
		//toogleCamera(true);
	}
	
	public void run(){
		if(!cameraOn){
			moveCamera();
			scrollCamera();
		}
			
	
		//System.out.println("CscrollX: " +cameraX);
	}
	
	//center player with fixed camera
	public void moveCamera(){
		
		if(moveFocus) {
			
			if(cameraX < player.getAbsoluteX()) {
				cameraX += CAMERASPEED;
				player.setX(player.getX()-CAMERASPEED);
			}
			if(cameraX > player.getAbsoluteX()) {
				cameraX -= CAMERASPEED;
				player.setX(player.getX()+CAMERASPEED);
			}
		
			if(cameraY < player.getAbsoluteY()) {
				cameraY += CAMERASPEED;
				player.setY(player.getY()-CAMERASPEED);
			}
			if(cameraY > player.getAbsoluteY()) {
				cameraY -= CAMERASPEED;
				player.setY(player.getY()+CAMERASPEED);
			}
			
		}
				
	}
	
	//scroll between map with fixed camera
	public void scrollCamera(){
		if(scrollLock == 1){
			System.out.println("North scrollY: " +scrollY);
			
			cameraY -= SCROLLSPEEDY;
			scrollY -= SCROLLSPEEDY;
			player.setY(player.getY()+SCROLLSPEEDY);
			
			if(scrollY <= -500 || cameraY <= 0){
				scrollLock = 0;
				scrollY = 0;
				if(cameraY < 0)
					cameraY = 0;
			}
		}
		
		if(scrollLock == 4){
			System.out.println("East scrollX: " +scrollX);
			
			cameraX += SCROLLSPEEDX;
			scrollX += SCROLLSPEEDX;
			player.setX(player.getX()-SCROLLSPEEDX);
			
			if(scrollX >= 750 || cameraX >= 1890){
				scrollLock = 0;
				scrollX = 0;
				if(cameraX > 1890)
					cameraX = 1890;
			}	
		}
		
		if(scrollLock == 3){
			System.out.println("South scrollY: " +scrollY);
			
			cameraY += SCROLLSPEEDY;
			scrollY += SCROLLSPEEDY;
			player.setY(player.getY()-SCROLLSPEEDY);
			
			if(scrollY >= 530 || cameraY >= 2065){
				scrollLock = 0;
				scrollY = 0;
				if(cameraY > 2065)
					cameraY = 0;
			}	
		}	
		
		if(scrollLock == 2){
			System.out.println("West scrollX: " +scrollX);
			
			cameraX -= SCROLLSPEEDX;
			scrollX -= SCROLLSPEEDX;
			player.setX(player.getX()+SCROLLSPEEDX);
			
			if(scrollX <= -750 || cameraX <= 0){
				scrollLock = 0;
				scrollX = 0;
				if(cameraX < 0)
					cameraX = 0;
			}
		}
		
		
	}
	
	public void toogleCamera(boolean cOn){
		cameraOn = cOn;
		if(cameraOn){
			if(player.getAbsoluteX() > 0)
				cameraX = player.getAbsoluteX() + Math.abs((405-15*3)-player.getX());
			if(player.getAbsoluteY() > 0)
				cameraY = player.getAbsoluteY() + Math.abs((315-20*3)-player.getY());
			
			player.setX(405-15*3);
			player.setY(315-20*3);
			
			//moveCamera();
		}
		if(!cameraOn){
			
		}	
	}
	
	//allign Camera with auto Camera
	public void alignCamera(){
		
    	if(player.getAbsoluteY() > 2065) {
	    	cameraY = 2700-630;
	    	player.setY(-2700 + player.getAbsoluteX() + 810);
	    }
		if(player.getAbsoluteY() < 0){
			cameraY = 0;
			player.setY(player.getAbsoluteY() + 350);
		}
		
	    if(player.getAbsoluteX() > 1880) {
	    	cameraX = 2700-810;
	    	player.setX(-2700 + player.getAbsoluteX() + 810 + 315);
	    }
	    if(player.getAbsoluteX() < 0) {
	    	cameraX = 0;
	    	player.setX(player.getAbsoluteX() + 405);
	    }   
	}

	
	public int getCameraX(){return cameraX;}
	public int getCameraY(){return cameraY;}
	public boolean getMoveFocus(){return moveFocus;}
	public boolean getCameraStatus(){return cameraOn;}
	public boolean getCameraLock(){return cameraLock;}
	public int getScrollLock(){return scrollLock;}
	
	public void setCameraX(int cameraX){this.cameraX = cameraX;}
	public void setCameraY(int cameraY){this.cameraY = cameraY;}
	public void setMoveFocus(boolean moveFocus){this.moveFocus = moveFocus;}
	public void setCameraStatus(boolean cOn){this.cameraOn = cOn;}
	public void setCameraLock(boolean cameraLock){this.cameraLock = cameraLock;}
	public void setScrollLock(int scrollLock){this.scrollLock = scrollLock;}
	 
	
	
	
	

}
