package core;



import java.awt.Rectangle;

import characters.Goomba;
import characters.Player;

import map.DungeonCollision;
import map.DungeonNavigator;
import map.OverWorldCamera;
import map.OverWorldMap;






public class CollisionDetection implements Runnable{
	
	private static Player player;
	private static Goomba goomba;
	private static OverWorldMap overWorldMap;
	private static DungeonNavigator dungeonNavigator;
	
	final static Rectangle BoundN = new Rectangle(0,0,810,1);
	final static Rectangle BoundE = new Rectangle(809,0,1,630);
	final static Rectangle BoundS = new Rectangle(0,629,810,1);
	final static Rectangle BoundW = new Rectangle(0,0,1,630);
	
	public CollisionDetection(){
		
	}
	
	public CollisionDetection(Player player, Goomba goomba, OverWorldMap overWorldMap, DungeonNavigator dungeonNavigator){
		this.player = player;
		this.goomba = goomba;
		this.overWorldMap = overWorldMap;
		this.dungeonNavigator = dungeonNavigator;
		
	}

	public void run(){
		if(GameManager.printMsg)
			System.out.println("CollisionDetection run");
			
		try {
			checkMap();
			
		} catch (InterruptedException ie) {
			System.err.println("CollisionDetection:" +ie);
		}
		
		
	}
	
	private void checkMap() throws InterruptedException{
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
					OverWorldMap.overWorld = false;dungeonNavigator.setDungeon(true);
					//dungeonNavigator.setEnemyLife(1);
					//dungeonNavigator.setEnemyPosition(200,300);
					}
			}	
		}
		
		
		if(dungeonNavigator.getDungeon()){
			dungeonNavigator.setBounds();
			overWorldMap.setCameraLock(true);
			checkEnemy();
			if(dungeonNavigator.getScrollReady()){
				dungeonNavigator.checkDungeonCollision();
				
			}
		}
	}
	
	private void checkEnemy(){
		goomba.setBoundS();
		goomba.setBoundMain();
		player.setAttackBounds();
		if(player.getBoundS().intersects(goomba.getBoundS())){
			System.err.println("hitEnemy!");
			player.setLoseLifeType(1);
			player.setLoseLife(true);
			sleepNow();
			player.setLoseLife(false);
		}
		
		if(player.getAttackBound().intersects(goomba.getBoundMain())){
			goomba.loseLife();
		}
		
		dungeonNavigator.checkEnemyCollisionWall();
		
	}

	private void sleepNow(){
		try{
			System.out.println("start.Sleep");
			Thread.sleep(100);
			System.out.println("stop.Sleep");
		} catch (InterruptedException ie){
			System.err.println("DungeonCollision.sleepNow:"+ie);
		}
	}
	
}
