package map;

import java.awt.Rectangle;
import java.util.ArrayList;

import objects.Moveable;
import objects.Player;


abstract class OverWorldCollision extends OverWorldBuilder{

	private ArrayList<WallBound<Integer, Rectangle>> wallBoundList = new ArrayList<WallBound<Integer, Rectangle>>();
	private ArrayList<MapObjectBound<Integer, Rectangle>> mapObjectBoundList = new ArrayList<MapObjectBound<Integer, Rectangle>>();
	private final int SETBACK = 4;
	
	protected OverWorldCollision(){
	
	}
	
	public void checkCollision(Moveable object){
		
		Camera cam = Camera.getInstance();
		Player player = Player.getInstance();
		
	
			
			for(int indexWall = 0; indexWall < wallBoundList.size(); indexWall++){
				
				Rectangle wallRect = wallBoundList.get(indexWall).getRectangle();
				int pushDirection = wallBoundList.get(indexWall).getPushDirection();
				
				
				if(object.getBoundCore().intersects(wallRect.x-cam.getX(),wallRect.y-cam.getY(),wallRect.width,wallRect.height)){
					
					//System.out.println("setBack@case:"+pushDirection);
					
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

		
		
		for(int indexMapObject = 0; indexMapObject < mapObjectBoundList.size(); indexMapObject++){
			Rectangle rect = mapObjectBoundList.get(indexMapObject).getRectangle();
			
			if(player.getBoundCore().intersects(rect.x-cam.getX(),rect.y-cam.getY(),rect.width,rect.height)){
				/*TODO
				for(int i = 0; i < GameManager.getMoveableList().size(); i++){
					if(!GameManager.getMoveableList().get(i).isHumanPlayer()){

						GameManager.getMoveableList().get(i).setX(GameManager.getMoveableList().get(i).getX()-player.getOldXCam());
						GameManager.getMoveableList().get(i).setY(GameManager.getMoveableList().get(i).getX()-player.getOldYCam());
					}
				}
				*/
				player.setLife(player.getLife()-1);
				player.setX(player.getOldX());
				player.setY(player.getOldY());
				cam.setX(player.getOldXCam());
				cam.setY(player.getOldYCam());
				setXCoordinate(-player.getOldXCam());
				setYCoordinate(-player.getOldYCam());
				
				
				
				break;
			}
			
		}
		
		
	}
		
	
	public void initializeBounds(){
		wallBoundList = null;
		wallBoundList = getWallBoundList();
		mapObjectBoundList = null;
		mapObjectBoundList = getMapObjectBoundList();

	}
}
