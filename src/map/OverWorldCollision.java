package map;

import java.awt.Rectangle;
import java.util.ArrayList;

import game.objects.Player;

abstract class OverWorldCollision extends OverWorldBuilder{

	ArrayList<Rectangle> wallBound = new ArrayList<Rectangle>();
	
	protected OverWorldCollision(){
		initializeBounds();
	}
	
	public void checkCollision(){
		
	
		
		Camera cam = Camera.getInstance();
		Player player = Player.getInstance();
		int SETBACK = 5;
		
		for(int index = 0; index < wallBound.size(); index++){
			
			Rectangle tmp = wallBound.get(index);
			Rectangle objectRect = new Rectangle(tmp.x-cam.getX(),tmp.y-cam.getY(),tmp.width,tmp.height);
			
			if(player.getBoundCore().intersects(objectRect)){
				System.err.println("FallBack");
				
				
				switch(Player.getInstance().getLastDirection()){
				
				case(1):	if(objectRect.intersects(player.getBoundDirN()))
							player.setMovement(0, SETBACK);
							break;
					
				case(2):	if(objectRect.intersects(player.getBoundDirNE()))
							player.setMovement(-SETBACK, SETBACK);
							break;
				
				case(3):	if(objectRect.intersects(player.getBoundDirE()))
							player.setMovement(-SETBACK, 0);
							break;
							
				case(4):	if(objectRect.intersects(player.getBoundDirSE()))
							player.setMovement(-SETBACK,-SETBACK);
							break;
							
				case(5):	if(objectRect.intersects(player.getBoundDirS()))
							player.setMovement(0, -SETBACK);
							break;
							
				case(6):	if(objectRect.intersects(player.getBoundDirSW()))
							player.setMovement(SETBACK, -SETBACK);
							break;
							
				case(7):	if(objectRect.intersects(player.getBoundDirW()))
							player.setMovement(SETBACK, 0);
							break;
							
				case(8):	if(objectRect.intersects(player.getBoundDirNW()))
							player.setMovement(SETBACK,SETBACK);
							break;
					
				}//switch(lastdirection)

			}//if player.core intersect

		}//for parse wallObject
		
		
		
		
	}
	
	public void initializeBounds(){
		
		wallBound = getWallBound();
	}
}
