package objects;

public class Damage {

	
	private Damage(){
		
	}
	
	//type 0: physical, type 1: magic
	
	public static void inflictDamage(int type, double damage, Moveable object){

		System.out.println("inflict DMG "+damage+",type"+type+" on MoveableType: "+object.getMoveableType()+" life@"+object.getLife());
		
		if(!object.isHumanPlayer()){
			if(type == object.getMoveableType() && type  == 1){
				object.setLife(object.getLife()- damage*0.6,true);
			}
				
				
			else if (type == 0 && object.getMoveableType() == 1)
				object.setLife(object.getLife() - damage*1.4,true);
				
			else
				object.setLife(object.getLife() - damage,true);
		} else 
			Player.getInstance().loseLife(type, damage);
		
		
		
		
		
	}

}
