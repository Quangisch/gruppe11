package objects;

abstract class Item extends Moveable{
	
	protected Item(){
		setWidth(25);
		setHeight(25);
		setSubSpriteHeight(25);
		setSubSpriteWidth(25);
		
		setCoreX(12);
		setCoreY(20);
		setCoreWidth(45);
		setCoreHeight(45);
		
		setAlive(true);
	}

}
