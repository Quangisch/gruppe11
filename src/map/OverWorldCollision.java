package map;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.GameManager;

import game.objects.Moveable;
import game.objects.Player;

abstract class OverWorldCollision extends OverWorldBuilder{

	ArrayList<WallBound<Integer, Rectangle>> wallBoundList = new ArrayList<WallBound<Integer, Rectangle>>();
	private final int SETBACK = 4;
	
	protected OverWorldCollision(){
	
	}
	
	public void checkCollision(){
		
		Camera cam = Camera.getInstance();
		Player player = Player.getInstance();
		ArrayList<Moveable> objectList = GameManager.getMoveableList();
		
		
		for(int indexObject = 0; indexObject < objectList.size(); indexObject++){
			
			Moveable object = objectList.get(indexObject);
			
			for(int indexWall = 0; indexWall < wallBoundList.size(); indexWall++){
				
				Rectangle wallRect = wallBoundList.get(indexWall).getRectangle();
				int pushDirection = wallBoundList.get(indexWall).getPushDirection();
				
				
				if(Player.getInstance().getBoundCore().intersects(wallRect.x-cam.getX(),wallRect.y-cam.getY(),wallRect.width,wallRect.height)){
					
					//if(pushDirection != 0)
						System.out.println("setBack@case:"+pushDirection);
					
					//boolean tmpStateCamera = GameManager.cameraOn;
					//GameManager.cameraOn = false;
					//object.setObjectBack(SETBACK,pushDirection,false,null);
					
					switch(pushDirection){
					
					case(0):	object.setObjectBack(SETBACK,0, false, null); 
								break;
					case(1):	object.setMovement(0, -SETBACK);
								break;
					case(2):	object.setMovement(SETBACK, -SETBACK);
								break;
					case(3):	object.setMovement(SETBACK, 0);
								break;
					case(4):	object.setMovement(SETBACK,SETBACK);
								break;
					case(5):	object.setMovement(0, SETBACK);
								break;
					case(6):	object.setMovement(-SETBACK, SETBACK);
								break;
					case(7):	object.setMovement(-SETBACK, 0);
								break;
					case(8):	object.setMovement(-SETBACK,-SETBACK);
								break;	
							
				
					}
					
					
				//GameManager.cameraOn = tmpStateCamera;
					
				}//if object.intersect(wall)
					
						
				
				
				
			}//for indexWall
			
		}//for indexObject
		
		
	}
		
		
		
		
		
		
		/*
	
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
					

		 */

	
	
	public void initializeBounds(){
		wallBoundList = null;
		wallBoundList = getWallBoundList();

	}
}
