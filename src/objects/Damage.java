package objects;

public class Damage {

	
	private Damage(){
		
	}
	
	//type 0: physical, type 1: magic
	public static void inflictDamage(double damage, int type, Moveable object){

		System.out.println("inflict DMG "+damage+",type"+type+" on MoveableType: "+object.getMoveableID()+" life@"+object.getLife());
		
		
		if(!object.isHumanPlayer()){
			if(type == object.getMoveableType() && type  == 1){
				object.setLife(object.getLife()- damage*0.6);
			}
				
				
			else if (type == 0 && object.getMoveableType() == 1)
				object.setLife(object.getLife() - damage*1.4);
				
			else
				object.setLife(object.getLife() - damage);
		} else {
			if(type == object.getMoveableType())
				Player.getInstance().loseLife(damage*0.4);
				
			else if (type == 0 && object.getMoveableType() == 1)
				Player.getInstance().loseLife(damage*1.4);
				
			else
				Player.getInstance().loseLife(damage);
		}
		
		
		
		
	}

}
