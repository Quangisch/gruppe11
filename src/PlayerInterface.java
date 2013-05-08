import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;


public class PlayerInterface extends JComponent implements Runnable{
	Graphics2D g2d;
	BufferedImage interfaceBuff;
	
	public PlayerInterface(){
		
	}
	
	public void run(){
		if(Board.printMsg)
			System.out.println("PlayerInterface.run");
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
	}

}
