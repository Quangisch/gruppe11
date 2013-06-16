package core;

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
	
		while(!GameManager.mapLoaded){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		GameManager.updateGameObject();
		moveableObject = GameManager.getMoveableList();
	
		//System.out.println("moveableListSize@"+moveableObject.size());
		if(moveableObject.size() > 0){
			
			for(int index = 0; index < moveableObject.size();index++){
				
				if(moveableObject.get(index).getMoveableType() != -10){
					
					if(!moveableObject.get(index).isHumanPlayer()){
					
						int type = moveableObject.get(index).getMoveableType();
						int IDNumber = moveableObject.get(index).getMoveableID();
						
						if(Player.getInstance().getBoundCore().intersects(moveableObject.get(index).getBoundCore()) && type > 0){
							
							moveableObject.get(index).startWaitTimer(500);
							moveableObject.get(index).setObjectBack(10,0,true,moveableObject.get(index).getBoundCore());
						
							
							
							System.out.println("==>loseLife");
							
							Player.getInstance().loseLife(EnemyManager.getAttackDamage(type));
							Player.getInstance().startInvincibleTimer(1800);
							Player.getInstance().setObjectBack(20,0,true,moveableObject.get(index).getBoundCore());
							
							break;
						}
						
						if(Player.getInstance().getAttackBound().intersects(moveableObject.get(index).getBoundN().union(moveableObject.get(index).getBoundS()))){
							moveableObject.get(index).setObjectBack(50,0,true,Player.getInstance().getAttackBound());
							moveableObject.get(index).setLife(moveableObject.get(index).getLife()-Player.getInstance().getAttackDamage());
							
							//break;
						}
						
						
						
					}//if human
					
					if(GameManager.dungeon && GameManager.mapLoaded && GameManager.scrollDirection == 0)
						DungeonNavigator.getInstance().checkCollision(moveableObject.get(index));

					if(GameManager.overWorld && GameManager.mapLoaded)
						OverWorldNavigator.getInstance().checkCollision(moveableObject.get(index));	
					
					
				}// if moveableType != -10

			}//for index < size
		}//if moveable.size
		
		
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
