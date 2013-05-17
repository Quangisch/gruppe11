package core;



import java.awt.Rectangle;

import characters.Player;

import map.DungeonCollision;
import map.DungeonNavigator;
import map.OverWorldCamera;
import map.OverWorldMap;






public class CollisionDetection implements Runnable{
	
	Player player;
	OverWorldMap overWorldMap;
	DungeonNavigator dungeonNavigator;
	
	final static Rectangle BoundN = new Rectangle(0,0,810,1);
	final static Rectangle BoundE = new Rectangle(809,0,1,630);
	final static Rectangle BoundS = new Rectangle(0,629,810,1);
	final static Rectangle BoundW = new Rectangle(0,0,1,630);
	
	
	public CollisionDetection(Player player, OverWorldMap overWorldMap, DungeonNavigator dungeonNavigator){
		this.player = player;
		this.overWorldMap = overWorldMap;
		this.dungeonNavigator = dungeonNavigator;
	}

	public void run(){
		if(GameManager.printMsg)
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
			
		
		//check Player position relative to windowBorders
		if(!dungeonNavigator.getDungeon()){
			if(player.getBoundN().intersects(BoundN)){overWorldMap.setScrollLock(1);}
			if(player.getBoundS().intersects(BoundS)){overWorldMap.setScrollLock(3);}
			if(player.getBoundW().intersects(BoundW)){overWorldMap.setScrollLock(2);}
			if(player.getBoundE().intersects(BoundE)){overWorldMap.setScrollLock(4);}
		}
		
		
		//handles overworld <-> dungeon navigation
		if(OverWorldMap.overWorld){
			overWorldMap.setDungeonBounds();
			overWorldMap.setCameraLock(false);
			
			switch(OverWorldMap.areaID){
				case 1:	if(player.getBoundS().intersects(overWorldMap.getDungeonBounds())){
					player.setX(220); player.setY(520);
					dungeonNavigator.setAreaID(1);
					dungeonNavigator.setX(0); dungeonNavigator.setY(3);
					GameManager.changeMapModus = true;
					System.out.println("leave OverWorldMap1 -> dungeon1");
					dungeonNavigator.setLoadNewMap(true);
					dungeonNavigator.readData();
					player.setOldX(player.getX());
					player.setOldY(player.getY());
					OverWorldMap.overWorld = false;dungeonNavigator.setDungeon(true);}
			
			}	
		}
		
		
		if(dungeonNavigator.getDungeon()){
			dungeonNavigator.setBounds();
			overWorldMap.setCameraLock(true);
			//dungeonNavigator.startCollision();
		}
		
	}
	

	
}
