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

	}

	public void pulseHeart(){
		pulseScale += 0.005;
		if(pulseScale >= 1.03)
			pulseScale = 1;
	}
	
}
