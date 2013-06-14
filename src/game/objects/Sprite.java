package game.objects;


import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

abstract class Sprite extends DrawableObject{
	File file = null;
	
	private BufferedImage spriteBuff;
	private BufferedImage subSpriteBuff;
	private int subSpriteWidth = 0;
	private int subSpriteHeight = 0;
	private int subRowY = 1;
	
	private int attackRangeX = 20;
	private int attackRangeY = 25;
	
	private int moveStep;
	private double interStep;
	private int moveStepCycle;
	private int lastDirection;
	private int newDirection;
	
	private boolean interactionLock;
	private int interactType;
	private double interInteraction = 0;
	private Timer interactionTimer;
	private TimerTask interactionTask;
	
	private double staticInterStep;
	private int staticCycle;
	private int staticX;
	private int staticY;
	
	protected Sprite(){
		
	}
	
	protected void loadSprite(){
		try {
			if(subSpriteWidth != 0 && subSpriteHeight != 0){
				spriteBuff = ImageIO.read(file);
				subSpriteBuff = new BufferedImage(subSpriteWidth, subSpriteHeight, BufferedImage.TYPE_INT_ARGB);
			} else 
				System.err.println("Sprite: Width/Height not initialized");
			
			} catch (IOException e) {
				System.err.println("Sprite: File not found. "+e);
				System.exit(0);
			}
		
	}
	
	protected void moveSubSprite(int speed, boolean moveable, boolean moveUp, boolean moveRight, boolean moveDown, boolean moveLeft){
		
		if(moveUp || moveRight || moveDown || moveLeft){
			if (interStep >= moveStepCycle || newDirection != lastDirection)
				interStep = 0;

			interStep += 0.08*speed;
			moveStep = Math.round((int)interStep);
			
			//System.out.println("moveStep:"+moveStep);
			//System.out.println("interStep:"+interStep);
		}
		
		
			//Up
			if ( moveUp == true &&  moveRight != true && moveDown != true &&moveLeft != true || lastDirection == 1){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
			
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 0*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 1;
				newDirection = lastDirection;
			} 
			//Right
			if ( moveUp != true &&  moveRight == true && moveDown != true &&moveLeft != true || lastDirection == 3){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 2*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 3;
				newDirection = lastDirection;
			}
			//Down
			if ( moveUp != true &&  moveRight != true && moveDown == true &&moveLeft != true|| lastDirection == 5){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 4*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 5;
				newDirection = lastDirection;
			}	
			//Left
			if ( moveUp != true &&  moveRight != true && moveDown != true &&moveLeft == true || lastDirection == 7){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 6*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 7;
				newDirection = lastDirection;
			}
			
			//Up-Right
			if ( moveUp == true &&  moveRight == true && moveDown != true &&moveLeft != true || lastDirection == 2){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 1*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 2;
				newDirection = lastDirection;
			}
			//Down-Right
			if ( moveUp != true &&  moveRight == true && moveDown == true &&moveLeft != true || lastDirection == 4){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 3*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 4;
				newDirection = lastDirection;
			}
			//Down-Left
			if ( moveUp != true &&  moveRight != true && moveDown == true &&moveLeft == true|| lastDirection == 6){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 5*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 6;
				newDirection = lastDirection;
			}
			//Up-Left
			if ( moveUp == true &&  moveRight != true && moveDown != true &&moveLeft == true || lastDirection == 8){
				if (newDirection != lastDirection || moveStep >= moveStepCycle || !moveable)
					moveStep = 0;
				subSpriteBuff = spriteBuff.getSubimage(moveStep*subSpriteWidth, 7*subRowY*subSpriteHeight, subSpriteWidth, subSpriteHeight);
				lastDirection = 8;
				newDirection = lastDirection;
			}
			
			
			setImage(subSpriteBuff);
			
	}
	
	protected void setInteraction(int interactType){
		this.interactType = interactType;
		interInteraction += 0.1;
		interactionTimer = new Timer();
		interactionTask = new InteractionTask(interactType);
		interactionTimer.schedule(interactionTask, 0);
	}
	
	protected void interactSubSprite(){
		
		//attack
		if (interactType == 1){
	
			//System.out.println("attackCounter: "+attackCounter); 
			if (interInteraction < 2.5)
				interInteraction += 0.000005;
			
			
			int attackStep = (int)(interInteraction);
			
			//System.out.println("interInteraction@"+interInteraction+", attackStep@"+attackStep);
			//System.out.println("intAtt@"+(int)(interInteraction));
			
			subSpriteBuff = spriteBuff.getSubimage((10+attackStep)*subSpriteWidth, (lastDirection-1)*subSpriteHeight, subSpriteWidth, subSpriteHeight);
			
		
			if(interInteraction >= 2.5) {
				interInteraction = 0;
				interactType = 0;
			}
	
		}
		
		if(interactType == 2 || interactType == 3){
			int achieveStep = 0;
			
			if (interInteraction < 5.5)
				interInteraction += 0.000005;
			
			if(interactType == 2){
				if(interInteraction >= 1 && interInteraction < 5)
					achieveStep = 1;
				else 
					achieveStep = 0;
			} 
			
			if(interactType == 3)
				achieveStep = ((int)(interInteraction)) % 2; 
			
			
			subSpriteBuff = spriteBuff.getSubimage((14+achieveStep)*subSpriteWidth, 4*subSpriteHeight, subSpriteWidth, subSpriteHeight);

			if(interInteraction >= 5.5) {
				interInteraction = 0;
				interactType = 0;
			}
		}
		
		setImage(subSpriteBuff);

	}
	
	//item Sprites
	protected void setStaticSubSprite(int resizeFactor){
		
		if(staticCycle != 0)
			staticInterStep += 0.1;
		
		if(staticInterStep >= staticCycle)
			staticInterStep = 0;
		
		
		
		int staticStep = (int)(staticInterStep);
			
		//System.out.println((staticStep*subSpriteWidth+staticX)+"x"+(staticY)+", "+subSpriteWidth+"x"+subSpriteHeight);

		subSpriteBuff = spriteBuff.getSubimage(staticStep*subSpriteWidth+staticX, staticY, subSpriteWidth, subSpriteHeight);
		
		BufferedImage resized = new BufferedImage(subSpriteBuff.getWidth()*resizeFactor, subSpriteBuff.getHeight()*resizeFactor, BufferedImage.TYPE_INT_ARGB);
	    resized.createGraphics().setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    resized.createGraphics().drawImage(subSpriteBuff, 0, 0, subSpriteBuff.getWidth()*resizeFactor, subSpriteBuff.getHeight()*resizeFactor, 0, 0, subSpriteBuff.getWidth(), subSpriteBuff.getHeight(), null);
		
		//resized.createGraphics().drawImage(subSpriteBuff.getSubimage(staticStep*subSpriteWidth+staticX, staticY, subSpriteWidth, subSpriteHeight), 0,0,subSpriteBuff.getWidth()*resizeFactor, subSpriteBuff.getHeight()*resizeFactor,0,0,subSpriteBuff.getWidth(), subSpriteBuff.getHeight(),null);
		
	    
		setImage(resized);
	}
	
	
	//set
	protected void setFile(File file){this.file = file;}
	protected void setSprite(BufferedImage spriteBuff){this.spriteBuff = spriteBuff;}
	protected void setSubSpriteWidth(int width){this.subSpriteWidth = width; setWidth(width);}
	protected void setSubSpriteHeight(int height){this.subSpriteHeight = height; setHeight(height);}
	protected void setMoveStepCycle(int moveStepCycle){this.moveStepCycle = moveStepCycle;}
	public void setLastDirection(int lastDirection){
		if(lastDirection > 0) this.lastDirection = lastDirection;
		if(lastDirection < 0) this.lastDirection = ((-(lastDirection-1) - 4) % 9)+1;
		if(lastDirection == 0 || (lastDirection > 8 && lastDirection < -8))
			System.err.println("Sprite.Error: Invalid lastDirection Value@setLastDirection");
		
		System.err.println("Sprite:LastDirection@"+this.lastDirection);
	}
	
	protected void setStaticX(int x){this.staticX = x;}
	protected void setStaticY(int y){this.staticY = y;}
	protected void setStaticCycle(int cycle){this.staticCycle = cycle;}
	
	protected void setMoveStep(int moveStep){this.moveStep = moveStep;}
	protected void setSubRowY(int subRowY){this.subRowY = subRowY;}
	protected void setInteractionLock(boolean lock){this.interactionLock = lock;} 
	
	
	//get
	public BufferedImage getSubSprite(){return subSpriteBuff;}
	public synchronized int getLastDirection(){return lastDirection;}
	
	protected boolean getInteractionLock(){return interactionLock;}
	

	private class InteractionTask extends TimerTask{
		
		private int interactType;
		
		private InteractionTask(int interactType){
			this.interactType = interactType;
		}
		public void run() {
			int counter = 0;
			
			while(interInteraction != 0){
				interactSubSprite();
				if(interactType == 1)
					setAttackBound(lastDirection, interInteraction, attackRangeX, attackRangeY);
				//System.out.println("running@"+counter+" &interInteraction@"+interInteraction);
			}
			
			if(interInteraction == 0){
				interactionLock = false;
				setAttackBound(0,0,0,0);
				
			}
				

		}
	}

}
