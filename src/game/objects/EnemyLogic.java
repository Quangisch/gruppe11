package game.objects;


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
			}
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
