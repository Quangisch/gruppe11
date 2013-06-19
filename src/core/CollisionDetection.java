package core;



import java.util.ArrayList;

import objects.Damage;
import objects.Moveable;
import objects.Player;

import map.DungeonNavigator;
import map.OverWorldNavigator;

public class CollisionDetection implements Runnable{
	
	private static CollisionDetection collisionDetection;
	
	private ArrayList<Moveable> moveableObject = new ArrayList<Moveable>();
	
	private CollisionDetection(){
		System.err.println("construct CollisionDetection");
	}
	
	public void run(){
	
		while(!GameManager.getInstance().mapLoaded){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			GameManager.updateGameObject();
			moveableObject = GameManager.getMoveableList();
		
			//System.out.println("moveableListSize@"+moveableObject.size());
			if(moveableObject.size() > 0){
				
				for(int index = 0; index < moveableObject.size();index++){
					
					if(moveableObject.get(index).getMoveableType() >= 0){
						
						if(!moveableObject.get(index).isHumanPlayer()){
						
							int type = moveableObject.get(index).getMoveableType();
							//int IDNumber = moveableObject.get(index).getMoveableID();
							
							if(Player.getInstance().getBoundCore().intersects(moveableObject.get(index).getBoundCore())){
								
								moveableObject.get(index).startWaitTimer(500);
								moveableObject.get(index).setObjectBack(50,0,true,moveableObject.get(index).getBoundCore());
								
								
								
								System.out.println("==>loseLife against moveableType@"+type);
								if(moveableObject.get(index).getMoveableBoss())
									Damage.inflictDamage(2, -1, Player.getInstance());
								else
									Damage.inflictDamage(1, -1, Player.getInstance());
								
								Player.getInstance().startInvincibleTimer(1800);
								Player.getInstance().setObjectBack(20,0,true,moveableObject.get(index).getBoundCore());
								
								break;
							}
							
							if(Player.getInstance().getAttackBound().intersects(moveableObject.get(index).getBoundN().union(moveableObject.get(index).getBoundS()))){
								//moveableObject.get(index).setObjectBack(50,0,true,Player.getInstance().getAttackBound());
								Damage.inflictDamage(Player.getInstance().getAttackDamage(), 0, moveableObject.get(index));
								moveableObject.get(index).startFallBackTimer(3, -Player.getInstance().getLastDirection(), false,null);
								break;
							}
							
							
							
						}//if human

					}// if moveableType >= 0
					
					if(GameManager.getInstance().dungeon && GameManager.getInstance().mapLoaded && GameManager.getInstance().scrollDirection == 0)
						DungeonNavigator.getInstance().checkCollision(moveableObject.get(index));

					if(GameManager.getInstance().overWorld && GameManager.getInstance().mapLoaded)
						OverWorldNavigator.getInstance().checkCollision(moveableObject.get(index));	

				}//for index < size
			}//if moveable.size
			
		} catch(Exception e){
			System.out.println("DungeonCollision.Exception@ "+e);
			//System.exit(-1);
		}
		
		
		
		
	}
	
	public void getObjects(){
		moveableObject = GameManager.getMoveableList();
	}
	
	public static void resetInstance(){
		if(collisionDetection != null)
			collisionDetection = new CollisionDetection();
	}
	
	public static CollisionDetection getInstance(){
		if(collisionDetection == null){
			collisionDetection = new CollisionDetection();
		}
		
		return collisionDetection;
	}
	

}
