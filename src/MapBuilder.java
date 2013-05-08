import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class MapBuilder extends JComponent implements FileLink{
	Graphics2D g2d;
	
	BufferedImage tileBuff, subMapBuff, mapBuff;
	BufferedReader readDataBuff;
	
	BufferedImage doorBuff, wall1Buff, wall2Buff, floor1Buff, floor2Buff;	
	int xWall1[][] = new int[9][7];
	int yWall1[][] = new int [9][7];
	int counter = 0;
	
	public MapBuilder(){
		readData();
	}
	
	public void start(){
		readData();
		for(int y = 0; y < 7; y++){			
			for(int x = 0; x < 9; x++){
				System.out.println("xwall:"+xWall1[x][y]+",ywall:"+yWall1[x][y]);
				
			}
			System.out.println();
		}
		//return mapBuff;
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
		//TODO
		int x1 = 5;
		int y1 = 1;
		//g2d.drawImage(mapBuff.getSubimage(xWall1[0][0],yWall1[0][0],90,90),0,0,this);
		for(int y = 0; y < 7; y++){			
			for(int x = 0; x < 9; x++){
				g2d.drawImage(mapBuff.getSubimage(xWall1[x1][y1]*90,yWall1[x1][y1]*90,90,90),1*90,0*90,this);
				//g2d.drawImage(mapBuff.getSubimage(6*90,2*90,90,90),1*90,0*90,this);
				//System.out.println("xwall:"+xWall1[x][y]+",ywall:"+yWall1[x][y]);
			}
		}
		//System.out.println("xwall:"+xWall1[x1][x1]+",ywall:"+yWall1[x1][y1]);
		
		
	}
	
	private void readData(){
		
		String searchLine,dataLine,tmpLine, tmpLine2;
		String coordinatesData = "##00_03##";
		String layerData = "#wall1#";
		
		try {
			BufferedReader readDataTmp = new BufferedReader(new FileReader(mapData_Dungeon01));
			readDataBuff = readDataTmp;
			mapBuff = ImageIO.read(tiles_Dungeon01);
			
			//BufferedImage mapBuff = new BufferedImage(0,0,BufferedImage.TYPE_INT_ARGB);
			
			searchLine = readDataBuff.readLine();
			
			//search coordinates
			while(!searchLine.contentEquals(coordinatesData) || searchLine == null){searchLine = readDataBuff.readLine();}

			while(!searchLine.contentEquals(layerData) || searchLine == null){searchLine = readDataBuff.readLine();}
			
			if(searchLine.contentEquals(layerData)){
				
				for(int y = 0; y < 7; y++){			
					searchLine = readDataBuff.readLine();
					searchLine = searchLine.replace(" ", "");
					for(int x = 0; x < 9; x++){
						//translates to int x,y 2dimensional arrays
						translateCoordinates(x,y,searchLine.substring(3*x,1+3*x), searchLine.substring(3*x+1,3+3*x));
						counter++;
					}
				}
			}
		
			
			for(int i = 0; i < 7; i++){			
				for(int j = 0; j < 9; j++){
					//if ((wallData[i][j]) = null)System.err.println("corrupt map");
					}
			}
		} catch (FileNotFoundException e){
			System.err.println("MapBuilder: "+e);
		} catch (IOException e) {
			System.err.println("MapBuilder: "+e);
		}
	}
	
	
	public BufferedImage getSubimages(int x, int y){
		
		subMapBuff = mapBuff.getSubimage(xWall1[x][y]*90-90, yWall1[x][y]*90-90, 90, 90);
		return subMapBuff;
		
	}
	
	public void translateCoordinates(int x, int y, String yString, String xString){
		
		if(yString.contentEquals("a")){yWall1[x][y] = 1;}
		if(yString.contentEquals("b")){yWall1[x][y] = 2;}
		if(yString.contentEquals("c")){yWall1[x][y] = 3;}
		if(yString.contentEquals("d")){yWall1[x][y] = 4;}
		if(yString.contentEquals("e")){yWall1[x][y] = 5;}
		if(yString.contentEquals("f")){yWall1[x][y] = 6;}
		if(yString.contentEquals("g")){yWall1[x][y] = 7;}
		if(yString.contentEquals("h")){yWall1[x][y] = 8;}
		if(yString.contentEquals("i")){yWall1[x][y] = 9;}
		if(yString.contentEquals("y")){yWall1[x][y] = 10;}
		if(yString.contentEquals("k")){yWall1[x][y] = 11;}
		if(yString.contentEquals("l")){yWall1[x][y] = 12;}
		if(yString.contentEquals("m")){yWall1[x][y] = 13;}
		if(yString.contentEquals("n")){yWall1[x][y] = 14;}
		
		if(yString.contentEquals("x")){yWall1[x][y] = 14; xWall1[x][y] = 18;}
		
		if(!xString.contentEquals("0x"))
			xWall1[x][y] = Integer.parseInt(xString.toString());
		//System.out.println(yString);
		System.out.println("x: "+xWall1[x][y]+", y: "+yWall1[x][y]);
	}

}
