import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.*;


import javax.imageio.ImageIO;
import javax.swing.JComponent;

//Old Map Class, not used anymore!

public class Map extends JComponent implements GridInterface{

	private Image mapImage;
	BufferedImage mapTiles = null;
	BufferedImage mapBuff = null;
	Graphics2D g2d;
	
	public Map(){
		System.out.println("Map object generated");
		try{
			
			URL url = this.getClass().getResource(FileLink.map);
			mapTiles = ImageIO.read(url);
			mapBuff = new BufferedImage(810, 630, BufferedImage.TYPE_INT_ARGB);
			mapBuff = mapTiles;
		} catch (IOException e){
			
		}
		
	}
	
	
	public void paintComponents (Graphics g) {
		g2d = (Graphics2D) g;

	}
	
	
	public void paintMap() {
		
			//paint grass Layer
			mapBuff = mapTiles.getSubimage(15*6, 15*6, 15*6, 15*6);
			int x = 0;
			int y = 0;	
			for (int i = 0; i < 7; i++){	
				for (int j = 0; j < 9; j++){
					g2d.drawImage(mapBuff ,6*x , 6*y, this);
					x += 15;
				}
				x = 0;
				y += 15;	
			}
			
			//paint wall
			mapBuff = mapTiles.getSubimage(A5.x, A5.y, 15*6*2, 15*6);
			x = 0;
			y = 0;
			for (int i = 0; i < 4; i++){	
				for (int j = 0; j < 9; j++){
					g2d.drawImage(mapBuff ,6*x , 6*y, this);
					x += 15*2;
				}
				x = 0;
				//y += 15;	
			}
			mapBuff = mapTiles.getSubimage(C6.x, C6.y, 15*6, 15*6);
			g2d.drawImage(mapBuff ,5*15*6 , 0, this);
			
			//paint left Border
			mapBuff = mapTiles.getSubimage(B6.x, B6.y, 15*6, 15*6);
			g2d.drawImage(mapBuff ,0 , 0, this);
			
			mapBuff = mapTiles.getSubimage(15*6, 3*15*6, 15*6, 15*6);
			x = 0;
			y = 0;
			for (int i = 0; i < 5; i++){	
				for (int j = 0; j < 9; j++){
					g2d.drawImage(mapBuff ,6*x , 6*y+15*6, this);
					//x += 15*2;
				}
				x = 0;
				y += 15;	
			}
			mapBuff = mapTiles.getSubimage(15*6, 4*15*6, 15*6, 15*6);
			g2d.drawImage(mapBuff ,0 , 6*15*6, this);
			
			
			paintLake1(C2);
			paintTree1(C7, D7);
			paintTree1(E4, F4);
			paintTree1(F8, G8);
	
		
	}
	
	
	// IN PROGRESS- STILL TO DO!!
	public void paintTree1 (BoardGrid gridTop, BoardGrid gridBot) {
		mapBuff = mapTiles.getSubimage(C1.x, C1.y, B3.x, B3.y);
		g2d.drawImage(mapBuff , gridTop.x , gridTop.y, this);
		mapBuff = mapTiles.getSubimage(E1.x, E1.y, B3.x, B3.y);
		g2d.drawImage(mapBuff , gridBot.x , gridBot.y, this);
	}
	
	public void paintTree2(BoardGrid gridTop, BoardGrid gridBot) {
		mapBuff = mapTiles.getSubimage(C1.x, C1.y, B3.x, B3.y);
		g2d.drawImage(mapBuff , gridTop.x , gridTop.y, this);
		mapBuff = mapTiles.getSubimage(E1.x, E1.y, B3.x, B3.y);
		g2d.drawImage(mapBuff , gridTop.x , gridTop.y, this);
		
	}
	
	public void paintLake1 (BoardGrid grid) {
		mapBuff = mapTiles.getSubimage(2*15*6, 15*6, 3*15*6, 2*15*6);
		g2d.drawImage(mapBuff, grid.x, grid.y, this);
	}
	
	

}
