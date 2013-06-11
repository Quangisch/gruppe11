package game.objects;

import java.util.Random;


abstract class EnemyLogic extends EnemyMove {
	
	
	
	private int behaviourPattern;
	
	
	protected EnemyLogic(){

	}

	public void executePattern(){
		
		if(!getMoveable()){
			stopNow();
			rotateNow(-1,true,80);
		}
			
		if(getMoveable()){
			switch(behaviourPattern){
			case 0: stopNow();
					break;
			case 1: followObject(Player.getInstance());
					break;
			case 2: patrolRectangle(-1,false,100,100,100,200);
					break;
			case 3: patrolLineX(10, 500,200);
					break;
			case 4: rotateNow(2,true,200);
					break;
			
			case 11: 	followObject(Player.getInstance());
						punchObject(Player.getInstance(),500);
						break;
			case 12:	sprintToObject(Player.getInstance(),50);
						castObject(Player.getInstance(),0);
						break;
			case 13: 	combinatePattern();
						break;
			
			}
		}
	}
	
	public void combinatePattern(){
		
		int random = new Random().nextInt(100 - 0 + 1) + 0;
		
		if(random > 10){
			followObject(Player.getInstance());
			punchObject(Player.getInstance(), 200);
			
		} else if (random > 2){
			sprintToObject(Player.getInstance(),50);
			castObject(Player.getInstance(),0);
		} else if(random > 0){
			goBerserk();
		} else {
			patrolLineX(10, 500,200);
			
		}
		
	}
	
	private void checkLife(){
		if(getLife() <= 0){
			setAlive(false);
			setVisibleDrawable(false);
			System.out.println("Enemy.Death");
		}
	}
	
	public void setBehaviour(int pattern){
		this.behaviourPattern = pattern;
	}
	
	public int getBehaviour(){
		return behaviourPattern;
	}
	


}
