package game.objects;

import java.awt.Rectangle;

abstract class Item extends Moveable{
	
	protected Item(){
		setWidth(25);
		setHeight(25);
		setSubSpriteHeight(25);
		setSubSpriteWidth(25);
		
		setCoreX(10);
		setCoreY(15);
		setCoreWidth(40);
		setCoreHeight(40);
		
		setAlive(true);
	}

}
