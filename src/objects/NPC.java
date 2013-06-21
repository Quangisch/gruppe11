package objects;

abstract class NPC extends Initializer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4410950013709266820L;
	private double attackDamage;
	
	protected NPC(){

		
	}
	
	
	
	
	
	protected void initializeNeutralNPC(int posX, int posY){
			initializeAttributes(2, 9999, true,0,75,45,20);
			initializePosition(posX, posY, 5);
			initializeImage(neutralNPC, 90,120,8);
			
	}





	public double getAttackDamage() {
		return attackDamage;
	}





	public void setAttackDamage(double attackDamage) {
		this.attackDamage = attackDamage;
	}

}
