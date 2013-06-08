package core;

import game.objects.EnemyManager;
import game.objects.MarioDark;
import game.objects.Moveable;
import game.objects.Player;

import java.util.ArrayList;

import map.DungeonNavigator;
import map.OverWorldNavigator;

public class CollisionDetection implements Runnable{
	
	private static CollisionDetection collisionDetection;
	private static DungeonNavigator dungeonNavigator;
	private static OverWorldNavigator overWorldNavigator;
	
	private ArrayList<Moveable> moveableObject = new ArrayList<Moveable>();
	
	private CollisionDetection(){
		System.err.println("construct CollisionDetection");
	}
	
	public void run(){
	
		
		moveableObject = GameManager.getMoveableList();
	
		//System.out.println("moveableListSize@"+moveableObject.size());
		if(moveableObject.size() > 1){
			
			for(int index = 0; index < moveableObject.size();index++){
				
				
				if(!moveableObject.get(index).isHumanPlayer()){
					int type = moveableObject.get(index).getMoveableType();
					int IDNumber = moveableObject.get(index).getMoveableID();
					
					if(Player.getInstance().getBoundCore().intersects(moveableObject.get(index).getBoundCore())){
						
						//moveableObject.get(index).setObjectBack(10);
				
						if(EnemyManager.getAttackDamage(type) == 1 || EnemyManager.getAttackDamage(type) == 2)
							MarioDark.getInstance(false, IDNumber).waitNow(500);
			
						Player.getInstance().setObjectBack(20,true,moveableObject.get(index).getBoundCore());
						moveableObject.get(index).setObjectBack(10,true,moveableObject.get(index).getBoundCore());
						
						if(EnemyManager.getAttackDamage(type) == 1 || EnemyManager.getAttackDamage(type) == 2)
							MarioDark.getInstance(false, IDNumber).waitNow(500);
							
						moveableObject.get(index).startFlashTimer(100, 20);
						Player.getInstance().loseLife(EnemyManager.getAttackDamage(type));
						Player.getInstance().startInvincibleTimer(1000);
						break;
					}
					
					if(Player.getInstance().getAttackBound().intersects(moveableObject.get(index).getBoundCore())){
						moveableObject.get(index).setObjectBack(50,true,Player.getInstance().getAttackBound());
						moveableObject.get(index).setLife(moveableObject.get(index).getLife()-Player.getInstance().getAttackDamage());
						if(EnemyManager.getAttackDamage(type) == 1 || EnemyManager.getAttackDamage(type) == 2)
							MarioDark.getInstance(false, IDNumber).waitNow(500);
						
						moveableObject.get(index).startFlashTimer(100, 20);
						
					}
					
				}
				
			}
			
		}
		
		
		
		
		
		//checkCollision
		if(GameManager.dungeon && GameManager.mapLoaded && GameManager.scrollDirection == 0){
			for(int index = 0; index < moveableObject.size(); index++){
				DungeonNavigator.getInstance().checkCollision(moveableObject.get(index));
			}
		}
		
		if(GameManager.overWorld && GameManager.mapLoaded){
			for(int index = 0; index < moveableObject.size(); index++){
				//OverWorldNavigator.getInstance().checkCollision(moveableObject.get(index));
				
			}
			
			OverWorldNavigator.getInstance().checkCollision();
			
		}
		
		
		
	}
	
	public void getObjects(){
		moveableObject = GameManager.getMoveableList();
	}
	
	
	public static CollisionDetection getInstance(){
		if(collisionDetection == null){
			collisionDetection = new CollisionDetection();
			dungeonNavigator = DungeonNavigator.getInstance();
			overWorldNavigator = OverWorldNavigator.getInstance();
		}
			
		return collisionDetection;
	}
	

}
