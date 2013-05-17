package characters;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import core.Board;
import core.FileLink;
import core.GameManager;



public class PlayerInterface extends JComponent implements Runnable,FileLink{
	
	Player player;
	
	Graphics2D g2d;
	BufferedImage coinBuff, heartBuff;
	
	double pulseScale = 1;
	int fallBack;
	int counterTimer = 0;
	
	public PlayerInterface(Player player){
		this.player = player;
		
		try{
			heartBuff = ImageIO.read(heart);
			coinBuff = ImageIO.read(coin);
		} catch (IOException e) {
			System.err.println("PlayerInterface: " + e);
		}
		
	}
	
	public void run(){
		if(GameManager.printMsg)
			System.out.println("PlayerInterface.run");
		
		pulseHeart();
		
		if(player.getLoseLifeType() != -1){
			loseLife();
			counterTimer++;
		}
			
		
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
		for(int i = 0; i < player.getLife(); i++){
			g2d.drawImage(heartBuff,(int)(i*40),10,(int)(40*pulseScale),(int)(35*pulseScale),this);
		}
		g2d.setColor(Color.yellow);
		Font textFont = new Font("Arial", Font.BOLD, 25);  
		g.setFont(textFont);  
		g2d.drawString(Integer.toString(player.getCoins()),730,35);
		g2d.drawImage(coinBuff,760,10,this);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

	}

	private void pulseHeart(){
		pulseScale += 0.005;
		if(pulseScale >= 1.03)
			pulseScale = 1;
	}
	
	public void loseLife(){
		if(GameManager.printMsg){
			System.out.println(counterTimer);
			System.err.println(player.getLoseLifeType());
			System.out.println(player.getMoveable());
		}
	
			if(player.loseLifeType == -2){
				if((0 < counterTimer & counterTimer < 5))player.setVisible(false);
				if((5 < counterTimer & counterTimer< 50))player.setVisible(true);
				if((50 < counterTimer & counterTimer< 55))player.setVisible(false);
				if((55 < counterTimer & counterTimer< 100))player.setVisible(true);
				if((100 < counterTimer & counterTimer < 105))player.setVisible(false);
				if((105 < counterTimer & counterTimer < 150))player.setVisible(true);
				if((150 < counterTimer & counterTimer < 155))player.setVisible(false);
				if((155 < counterTimer & counterTimer < 200))player.setVisible(true);
				
			}
			
			
			if(player.loseLifeType == 0){
				
			}//Type0
			
			if(player.loseLifeType == 1){
				fallBack = 5;
				
				if(counterTimer < 10){
					player.setMoveable(false);
					switch(player.getLastDirection()){
					case 1: player.setY(player.getY()+fallBack);
							break;
					case 2: player.setX(player.getX()-fallBack); player.setY(player.getY()+fallBack);
							break;
					case 3: player.setX(player.getX()-fallBack);
							break;
					case 4: player.setX(player.getX()-fallBack); player.setY(player.getY()-fallBack);
							break;
					case 5: player.setY(player.getY()-fallBack);
							break;
					case 6: player.setX(player.getX()+fallBack); player.setY(player.getY()-fallBack);
							break;
					case 7: player.setX(player.getX()+fallBack);
							break;
					case 8: player.setX(player.getX()+fallBack); player.setY(player.getY()+fallBack);
							break;
					}
				} else {
					player.setMoveable(true);
					counterTimer = 201;
					player.setLife(player.getLife()-1);
				}
			}//Type1
			
			if(player.loseLifeType == 2){
				
				if(player.loseLife){
					player.setLoseLife(false);
					player.setLife(player.getLife()-1);
					if(player.getLife() > 0)
						player.resetPosition();
				}
			
				
				player.setMoveable(false);
				if((0 < counterTimer & counterTimer < 10))player.setVisible(false);
				if((10 < counterTimer & counterTimer < 50))player.setVisible(true);
				if((50 < counterTimer & counterTimer < 60))player.setVisible(false);
				if((60 < counterTimer & counterTimer < 100))player.setVisible(true);
				if((100 < counterTimer & counterTimer < 110))player.setVisible(false);
				if((110 < counterTimer & counterTimer < 150))player.setVisible(true);
				if((150 < counterTimer & counterTimer < 160))player.setVisible(false);
				if((160 < counterTimer & counterTimer < 200))player.setVisible(true);
				
				if(counterTimer > 100)
					player.setMoveable(true);
				
			}//Type2
			
			if(counterTimer > 200){
				counterTimer = 0;
				player.setVisible(true);
				player.setMoveable(true);
				fallBack = 0;
				if(player.getLife() > 1)
					player.setLoseLifeType(-1);
				if(player.getLife() == 1)
					player.setLoseLifeType(-2);
			}
			
		
		
		
	}
	
}
