package game.objects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import core.Board;
import core.FileLink;
import core.GameManager;


public class DrawableObject extends JComponent implements FileLink{

	private Graphics2D g2d;
	private volatile int x, y;
	private int oldX, oldY;
	private int oldXCam, oldYCam, oldLastDirection;
	
	private int width;
	private int height;
	
	private int cornerXOffset = 15;
	private int cornerYOffset = 9;
	
	private int coreXOffset;
	private int coreYOffset;
	private int coreWidth;
	private int coreHeight;
	
	private Rectangle attackBound = new Rectangle(0,0,0,0);
	
	private BufferedImage image;
	private volatile boolean visible = true;
	private volatile boolean alive;

	
	public DrawableObject(){
		
	}
	
	public void paintComponents(Graphics g){
		//System.out.println("draw Drawable");
		g2d = (Graphics2D) g;
		
		
		
		
		if(image != null && visible && GameManager.showBounds){
			//BoundDirRect
			g2d.setColor(Color.DARK_GRAY);
			g2d.draw(getBoundDirN());g2d.draw(getBoundDirE());
			g2d.draw(getBoundDirS());g2d.draw(getBoundDirW());
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.draw(getBoundDirNE());g2d.draw(getBoundDirSE());
			g2d.draw(getBoundDirSW());g2d.draw(getBoundDirNW());
			//BoundCore
			g2d.setColor(Color.GREEN);
			g2d.draw(getBoundCore());
			
			//BoundAttack
			g2d.setColor(Color.RED);
			g2d.draw(attackBound);
			
			g2d.setColor(Color.CYAN);
			g2d.draw(getBoundHitSpace());
			
			g2d.setColor(Color.RED);
			g2d.draw(getBoundN());
			g2d.draw(getBoundE());
			g2d.draw(getBoundS());
			g2d.draw(getBoundW());
		}
		
		if(image != null && visible)
			g2d.drawImage(image, x, y, Board.getInstance());

		
	}
	
	//set
	public void setImage(BufferedImage image){this.image = image;}
	public void setVisibleDrawable(boolean visible){this.visible = visible;}
	
	public synchronized void setX(int x){this.x = x;}
	public synchronized void setY(int y){this.y = y;}
	
	public void setAlive(boolean alive){this.alive = alive;}
	
	public void setWidth(int width){this.width = width-30;}
	public void setHeight(int height){this.height = height-10;}
	
	public void setCoreX(int coreXOffset){this.coreXOffset = coreXOffset;}
	public void setCoreY(int coreYOffset){this.coreYOffset = coreYOffset;}
	public void setCoreWidth(int coreWidth){this.coreWidth = coreWidth;}
	public void setCoreHeight(int coreHeight){this.coreHeight = coreHeight;}
	
	public Rectangle getBoundCore(){return new Rectangle(getX()+coreXOffset+cornerXOffset, getY()+coreYOffset+cornerYOffset, coreWidth, coreHeight);}
	public Rectangle getBound(){return new Rectangle(getX(), getY(), width, height);}
	
	public Rectangle getBoundN(){return new Rectangle(getX()+cornerXOffset, getY()+cornerYOffset, width-cornerXOffset, 20-cornerYOffset);}
	public Rectangle getBoundE(){return new Rectangle(getX()+width-20+cornerXOffset, getY()+cornerYOffset, 20-cornerXOffset, height-cornerYOffset);}
	public Rectangle getBoundS(){return new Rectangle(getX()+cornerXOffset, getY()+height-20+cornerYOffset, width-cornerXOffset, 20-cornerYOffset);}
	public Rectangle getBoundW(){return new Rectangle(getX()+cornerXOffset, getY()+cornerYOffset, 20-cornerXOffset, height-cornerYOffset);}
	
	public Rectangle getBoundHitSpace(){return new Rectangle(getX()+cornerXOffset, getY()+cornerYOffset, width-cornerXOffset, height-cornerYOffset);}
	
	//BoundDirection
	public Rectangle getBoundDirN(){return new Rectangle(getBoundCore().x, getBoundCore().y-30, getBoundCore().width, 30 );}
	public Rectangle getBoundDirE(){return new Rectangle(getBoundCore().x+getBoundCore().width, getBoundCore().y, 30, getBoundCore().height);}
	public Rectangle getBoundDirS(){return new Rectangle(getBoundCore().x, getBoundCore().y+getBoundCore().height, getBoundCore().width, 30);}
	public Rectangle getBoundDirW(){return new Rectangle(getBoundCore().x-30, getBoundCore().y, 30, getBoundCore().height);}
	public Rectangle getBoundDirNE(){return new Rectangle(getBoundCore().x+getBoundCore().width,getBoundCore().y-30,30,30 );}
	public Rectangle getBoundDirSE(){return new Rectangle(getBoundCore().x+getBoundCore().width, getBoundCore().y+getBoundCore().height, 30, 30);}
	public Rectangle getBoundDirSW(){return new Rectangle(getBoundCore().x-30, getBoundCore().y+getBoundCore().height, 30, 30);}
	public Rectangle getBoundDirNW(){return new Rectangle(getBoundCore().x-30, getBoundCore().y-30, 30, 30);}
	
	//AttackBound
	public void setAttackBound(int lastDirection, double interInteraction, int attackRangeX, int attackRangeY){
		
		int attWidth = (int)(attackRangeX*(interInteraction));
		int attHeight = (int)(attackRangeY*(interInteraction));
		int attXPlus = (int)(x + 10*interInteraction);
		int attYPlus = (int)(y + 10*interInteraction);
		int attXMinus = (int)(x - 10*interInteraction);
		int attYMinus = (int)(y - 10*interInteraction);
		
		if(lastDirection == 1)	attackBound = new Rectangle(x+30,30+attYMinus, attWidth, attHeight);
		else if(lastDirection == 3)	attackBound = new Rectangle(30+attXPlus,y+10, attWidth, attHeight);
		else if(lastDirection == 5)	attackBound = new Rectangle(x+15,attYPlus+attHeight, attWidth, attHeight);
		else if(lastDirection == 7)	attackBound = new Rectangle(attXMinus-attWidth+50,y+10, attWidth, attHeight);
		
		else if(lastDirection == 2)	attackBound = new Rectangle(x+40,y, attWidth, attHeight);
		else if(lastDirection == 4)	attackBound = new Rectangle(x+40,y+40, attWidth, attHeight);
		else if(lastDirection == 6)	attackBound = new Rectangle(x,y+25, attWidth, attHeight);
		else if(lastDirection == 8)	attackBound = new Rectangle(x,y, attWidth, attHeight);
		else  attackBound = new Rectangle(0,0,0,0);
		
		//System.out.println("attackBound@"+attackBound);
	}
	
	public Rectangle getAttackBound(){return attackBound;}
	
	//get
	public BufferedImage getImage(){return image;}
	public boolean getVisibleDrawable(){return visible;}
	
	@Override
	public int getX(){return x;}
	@Override
	public int getY(){return y;}
	
	public void setOldX(int oldX){this.oldX = oldX;}
	public void setOldY(int oldY){this.oldY = oldY;}
	public void setOldXCam(int oldXCam){this.oldXCam = oldXCam;}
	public void setOldYCam(int oldYCam){this.oldYCam = oldYCam;}
	public void setOldLastDirection(int oldLastDirection){this.oldLastDirection = oldLastDirection;}
	
	public int getOldX(){return oldX;}
	public int getOldY(){return oldY;}
	public int getOldXCam(){return oldXCam;}
	public int getOldYCam(){return oldYCam;}
	public int getOldLastDirection(){return oldLastDirection;}
	
	public boolean getAlive(){return alive;}
	

}
