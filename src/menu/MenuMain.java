package menu;
/*===> MainMenu mit 3 Buttons: Start, Options, Quit
 *Ausgangspunkt fŸrs MenŸ ist Board.ingame = false; Board.menu = true;
 *
 *"Start" 				Setzt Board.ingame = true und Board.menu = false
 *"Options" 		 	Sound Volume, Music Volume, Difficulty, Save, Load, Back
 *					 	Diese mŸssen erstmal nur als Regler/Buttons ohne Funktion 
 *						implementiert werden, die folgende static Varibles setzen:
 *						Sound/Music als Schieberegler mit Zuweisung von int Werten
 *						Board.musicVolume/Board.soundVolume zwischen 0 und 100.
 *  
 *"Quit"				System.exit(0);
 * 
 */

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.IOException;
import javax.imageio.ImageIO;

import core.Board;
import core.GameManager;




public class MenuMain implements Runnable{

	Graphics g2d;
	BufferedImage menuMain;
	
	
	public MenuMain(){
		System.err.println("->MainMenu");
	}
	
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;

	}
	
	public void run(){
		if (GameManager.printMsg)
			System.out.println("MainMenu.run");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public BufferedImage getImage(){
		return menuMain;
	}
}
