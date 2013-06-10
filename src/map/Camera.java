package map;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.GameManager;

import game.objects.Moveable;
import game.objects.Player;

public class Camera implements Runnable{
	
	private volatile int x;
	private volatile int y;
	private int scrollX = 0;
	private int scrollY = 0;
	
	private final int SCROLLSPEEDX = 45; //810
	private final int SCROLLSPEEDY = 35; //630
	private final int CAMERASPEED = 20;
	


	
	private static Camera camera;
	
	private Camera(){
		System.err.println("construct OverWorldCamera");
	}
	
	public void run(){
		//System.out.println("OverWorldX:"+OverWorldMap.getInstance().getX());
		//System.out.println("OverWorldY:"+OverWorldMap.getInstance().getY());
		//System.out.println("Camera_"+x+"x"+y);
		
		if(GameManager.printMsg){
			if(GameManager.overWorld)
			System.out.println("Map____"+OverWorldNavigator.getInstance().getXCoordinate()+"x"+OverWorldNavigator.getInstance().getYCoordinate());
			if(GameManager.dungeon)
			System.out.println("Map____"+DungeonNavigator.getInstance().getXCoordinate()+"x"+DungeonNavigator.getInstance().getYCoordinate()+"@"+DungeonNavigator.getInstance().getXMap()+"x"+DungeonNavigator.getInstance().getYMap());
			
			System.out.println("Camera_"+Camera.getInstance().getX()+"x"+Camera.getInstance().getY());
		
		}
		
		//moveCamera();
		if(GameManager.overWorld){
			OverWorldNavigator.getInstance().navigate();
			
		}
			
		
		if(GameManager.dungeon){
			DungeonNavigator.getInstance().navigate();
			
		}
			

	}
	
	//center Player.getInstance() with fixed camera
		public void moveCamera(){
			
			OverWorldNavigator map = OverWorldNavigator.getInstance();
			Player player = Player.getInstance();
			
			if(GameManager.overWorld && GameManager.cameraOn /*&& GameManager.moveFocus*/ && !player.getDirectionLock()) {
			//if(GameManager.cameraOn){	
			
				ArrayList<Moveable> moveableList = GameManager.getMoveableList();
				
				if(Player.getInstance().getX() > 400 && !(map.getXCoordinate() < -(map.getWidthMap()-810))) {
					x += CAMERASPEED;
					map.setXCoordinate(map.getXCoordinate()-CAMERASPEED);
					//Player.getInstance().setX(Player.getInstance().getX()-CAMERASPEED);
					for(int index = 0; index < moveableList.size(); index++){
						moveableList.get(index).setX(moveableList.get(index).getX()-CAMERASPEED);
					}
				}
				
				if(Player.getInstance().getX() < 400 && !(map.getXCoordinate() > 0)) {
					x -= CAMERASPEED;
					map.setXCoordinate(map.getXCoordinate()+CAMERASPEED);
					//Player.getInstance().setX(Player.getInstance().getX()+CAMERASPEED);
					for(int index = 0; index < moveableList.size(); index++){
						moveableList.get(index).setX(moveableList.get(index).getX()+CAMERASPEED);
					}
				} 
	
				
				if(Player.getInstance().getY() > 300 && !(map.getYCoordinate() < -map.getHeightMap()+630)) {
					y += CAMERASPEED;
					map.setYCoordinate(map.getYCoordinate()-CAMERASPEED);
					//Player.getInstance().setY(Player.getInstance().getY()-CAMERASPEED);
					for(int index = 0; index < moveableList.size(); index++){
						moveableList.get(index).setY(moveableList.get(index).getY()-CAMERASPEED);
					}
				} 
				
				
				if(Player.getInstance().getY() < 300 && !(map.getYCoordinate() > 0)) {
					y -= CAMERASPEED;
					map.setYCoordinate(map.getYCoordinate()+CAMERASPEED);
					//Player.getInstance().setY(Player.getInstance().getY()+CAMERASPEED);
					for(int index = 0; index < moveableList.size(); index++){
						moveableList.get(index).setY(moveableList.get(index).getY()+CAMERASPEED);
					}
				} 
			}
			
		if (map.getYCoordinate() > 0) 
			map.setYCoordinate(0);
		
		if(map.getYCoordinate() < -map.getHeightMap()+630) 
			map.setYCoordinate(-(map.getHeightMap()-630));
		
		if (map.getXCoordinate() > 0)
			map.setXCoordinate(0);
		
		if(map.getXCoordinate() < -(map.getWidthMap()-810))
			map.setXCoordinate(-(map.getWidthMap()-810));
		
		
		}
		
		public void switchToCameraMode(int scrollX, int scrollY){
			
			
			if(GameManager.cameraOn){
				
	
				setX(400);
				setY(300);
				
				OverWorldNavigator.getInstance().setXCoordinate(OverWorldNavigator.getInstance().getXCoordinate()-scrollX);
				OverWorldNavigator.getInstance().setYCoordinate(OverWorldNavigator.getInstance().getYCoordinate()-scrollY);

			}
			
			//System.out.println("moveLockedCamera");
			
		}
		
		
		//scroll between map with fixed camera
		public void scrollCamera(Map map){

			if(GameManager.scrollDirection == 1){
				//System.out.println("North scrollY: " +scrollY);
				
				y -= SCROLLSPEEDY;
				scrollY -= SCROLLSPEEDY;
				//Player.getInstance().setY(Player.getInstance().getY()+SCROLLSPEEDY);
				Player.getInstance().setMoveUp(true);
				
				ArrayList<Moveable> moveableList = GameManager.getMoveableList();
				for(int index = 0; index < moveableList.size(); index++){
					moveableList.get(index).setY(moveableList.get(index).getY()+SCROLLSPEEDY);
				}
				
				map.setYCoordinate(map.getYCoordinate()+SCROLLSPEEDY);
				
				if(scrollY <= -610 || y <= 0){
					GameManager.scrollDirection = 0;
					scrollY = 0;
					Player.getInstance().resetMovementLock();
					if(y < 0)
						y = 0;
					
				}
			}
			
			if(GameManager.scrollDirection == 3){
				//System.out.println("East scrollX: " +scrollX);
			
				x += SCROLLSPEEDX;
				scrollX += SCROLLSPEEDX;
				//Player.getInstance().setX(Player.getInstance().getX()-SCROLLSPEEDX);
				Player.getInstance().setMoveRight(true);
				
				ArrayList<Moveable> moveableList = GameManager.getMoveableList();
				for(int index = 0; index < moveableList.size(); index++){
					moveableList.get(index).setX(moveableList.get(index).getX()-SCROLLSPEEDX);
				}
				map.setXCoordinate(map.getXCoordinate()-SCROLLSPEEDX);
				
				if(scrollX >= 810 || x >= map.getWidthMap()){
					GameManager.scrollDirection = 0;
					scrollX = 0;
					Player.getInstance().resetMovementLock();
					if(x > map.getWidthMap())
						x = map.getWidthMap();
					
				}	
			}
			
			if(GameManager.scrollDirection == 5){
				//System.out.println("South scrollY: " +scrollY);
				
				y += SCROLLSPEEDY;
				scrollY += SCROLLSPEEDY;
				//Player.getInstance().setY(Player.getInstance().getY()-SCROLLSPEEDY);
				Player.getInstance().setMoveDown(true);
				
				ArrayList<Moveable> moveableList = GameManager.getMoveableList();
				for(int index = 0; index < moveableList.size(); index++){
					moveableList.get(index).setY(moveableList.get(index).getY()-SCROLLSPEEDY);
				}
				
				map.setYCoordinate(map.getYCoordinate()-SCROLLSPEEDY);
		
				if(scrollY >= 630 || y >= map.getHeightMap()){
					GameManager.scrollDirection = 0;
					scrollY = 0;
					Player.getInstance().resetMovementLock();
					if(y > map.getHeightMap())
						y = map.getHeightMap();
					
				}	
			}	
			
			if(GameManager.scrollDirection == 7){
				//System.out.println("West scrollX: " +scrollX);
				
				x -= SCROLLSPEEDX;
				scrollX -= SCROLLSPEEDX;
				//Player.getInstance().setX(Player.getInstance().getX()+SCROLLSPEEDX);
				Player.getInstance().setMoveLeft(true);
				
				ArrayList<Moveable> moveableList = GameManager.getMoveableList();
				for(int index = 0; index < moveableList.size(); index++){
					moveableList.get(index).setX(moveableList.get(index).getX()+SCROLLSPEEDX);
				}
				
				map.setXCoordinate(map.getXCoordinate()+SCROLLSPEEDX);
				
				if(scrollX <= -810 || x <= 0){
					GameManager.scrollDirection = 0;
					scrollX = 0;
					Player.getInstance().resetMovementLock();
					if(x < 0)
						x = 0;
	
				}
			}
			
			
		}
	
	
	public int getX(){return x;}
	public int getY(){return y;}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	
	public int getScrollX(){return scrollX;}
	public int getScrollY(){return scrollY;}
	
	public static Camera getInstance(){
		if(camera == null)
			camera = new Camera();
		return camera;
	}

}
