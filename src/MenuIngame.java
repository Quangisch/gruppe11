/*====> Ausgangspunkt sind: Sound/Music Regler
 * 							Component fŸr Leben & Geldanzeige 
 * 							Component fŸr Items
 * 							Component fŸr MiniMap
 * 							Back Button (->zurŸck ins Spiel)
 * 							Quit Button (->zurŸck ins Main Menu)
 * 
 * Um ins ingame Menu zu kommen mŸssen folgende Variabeln gesetzt sein:
 * Board.ingame = true; Board.menu = true;
 * 
 * Back Button setzt: Board.ingame = true; Board.menu = false;
 * Quit Button setzt: Board.ingame = false; Board.menu = true;
 * 
 * Die einzelnen Components existieren noch nicht und werden erst
 * spŠter erstellt, lege erst einmal die Infrastruktur fŸr die Darstellung
 * dieser Sachen fest. Lege erst einmal fixe Bilder dafŸr fest und gestalte die 
 * Anordnung im GUI fest.
 * 
 * Sound/Music als Schieberegler mit Zuweisung von Board.musicVolume/Board.soundVolume
 * zwischen 0 und 100.
 * 
 */


import java.awt.Image;
import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class MenuIngame extends JComponent implements Runnable{
	Graphics g2d;
	BufferedImage menuIngame;
	
	
	public MenuIngame(){
		System.err.println("->IngameMenu");
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;

	}
	
	public void run(){
		if (Board.printMsg)
			System.out.println("IngameMenu.run");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public BufferedImage getImage(){
		return menuIngame;
	}
	
}

