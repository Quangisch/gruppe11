import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class DungeonBuilder extends JComponent implements Runnable, FileLink{
	Graphics2D g2d;
	
	BufferedImage tileBuff, subMapBuff, mapBuff;
	BufferedReader readDataBuff;
	
	Graphics2D doorBuff, wall1Buff, wall2Buff, floor1Buff, floor2Buff;
	
	//layer variables
	static int xDoor[][] = new int[9][7];int yDoor[][] = new int [9][7];
	static int xWall1[][] = new int[9][7];int yWall1[][] = new int [9][7];
	static int xWall2[][] = new int[9][7];int yWall2[][] = new int [9][7];
	static int xFloor1[][] = new int[9][7];int yFloor1[][] = new int [9][7];
	static int xFloor2[][] = new int[9][7];int yFloor2[][] = new int [9][7];
	
	static final String[] layerData = {"#door#","#wall1#","#wall2#","#floor1#","#floor2#","###"};
	static final String[][] coordinatesData = 		{{"##00_00##","##00_01##","##00_02##","##00_03##","##00_04##"},
													{"##01_00##","##01_01##","##01_02##","##01_03##","##01_04##"},
													{"##02_00##","##02_01##","##02_02##","##02_03##","##02_04##"},
													{"##03_00##","##03_01##","##03_02##","##03_03##","##03_04##"},
													{"##04_00##","##04_01##","##04_02##","##04_03##","##04_04##"}};
	
	String actualCoordinatesData;
	static boolean loadNewMap = true;
	static boolean mapDataBuffered = false;
	static boolean emptyLayer = false;
	
	public DungeonBuilder(){
		System.out.println("coordinatesData:"+coordinatesData[1][2]);
		System.err.println("->MapBuilder");
		readData();
	}
	
	public void run(){
		
		
		if(loadNewMap){
			readData();
		}
		
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
	
		
		//if(Map.dungeon){
			for(int y = 0; y < 7; y++){			
				for(int x = 0; x < 9; x++){
					g2d.drawImage(mapBuff.getSubimage((xFloor2[x][y]-1)*90,(yFloor2[x][y]-1)*90,90,90),x*90,y*90,this);
				}
			}
			for(int y = 0; y < 7; y++){			
				for(int x = 0; x < 9; x++){
					g2d.drawImage(mapBuff.getSubimage((xFloor1[x][y]-1)*90,(yFloor1[x][y]-1)*90,90,90),x*90,y*90,this);
				}
			}
			for(int y = 0; y < 7; y++){			
				for(int x = 0; x < 9; x++){
					g2d.drawImage(mapBuff.getSubimage((xWall2[x][y]-1)*90,(yWall2[x][y]-1)*90,90,90),x*90,y*90,this);
				}
			}
			for(int y = 0; y < 7; y++){			
				for(int x = 0; x < 9; x++){
					g2d.drawImage(mapBuff.getSubimage((xWall1[x][y]-1)*90,(yWall1[x][y]-1)*90,90,90),x*90,y*90,this);
				}
			}
			for(int y = 0; y < 7; y++){			
				for(int x = 0; x < 9; x++){
					g2d.drawImage(mapBuff.getSubimage((xDoor[x][y]-1)*90,(yDoor[x][y]-1)*90,90,90),x*90,y*90,this);
				}
			}
		//}

	}
	

	
	private void readData(){		
		
		String searchLine,dataLine,tmpLine, tmpLine2;
		
		//System.out.println("readData");
		getMapID();
		

		
		try {
			BufferedReader readDataTmp = new BufferedReader(new FileReader(mapData_Dungeon01));
			readDataBuff = readDataTmp;
			mapBuff = ImageIO.read(tiles_Dungeon01);
			searchLine = readDataBuff.readLine();
			
			int i = 0;
			
				//search coordinates/map

				System.out.println("actualcoordinates:"+actualCoordinatesData);
				while(!searchLine.contentEquals(actualCoordinatesData)){searchLine = readDataBuff.readLine();}
				//while(!searchLine.contentEquals("##00_02##")){searchLine = readDataBuff.readLine();}
				
				//set mapData for door,wall1,wall2,floor1,floor2
				getLayerData:
				do{
					
					searchLine = readDataBuff.readLine();
					if(Board.printMsg)
						System.err.println("==>"+searchLine);
					
					while(!searchLine.contentEquals(layerData[i])){searchLine = readDataBuff.readLine();}
					
					
						if(searchLine.contentEquals(layerData[i])){
							for(int y = 0; y < 7; y++){	
								
								if(!emptyLayer)
									searchLine = readDataBuff.readLine();
								
								//empty Layer
								if(searchLine.startsWith("#")){
									searchLine = "x0x x0x x0x x0x x0x x0x x0x x0x x0x";
									emptyLayer = true;
								}
									
								//read all Data
								if(searchLine.startsWith("###")){
									mapDataBuffered = true;
									break getLayerData;
								}
									

								searchLine = searchLine.replace(" ", "");
								for(int x = 0; x < 9; x++){
									//translates to int x,y 2dimensional arrays
									translateCoordinates(layerData[i], x,y,searchLine.substring(3*x,1+3*x), searchLine.substring(3*x+1,3+3*x));
								}
							}
							
							emptyLayer = false;
							
					}

					i++;

				} while (i < 5);
				
				i = 0;

			
		} catch (FileNotFoundException e){
			System.err.println("MapBuilder: "+e);
		} catch (IOException e) {
			System.err.println("MapBuilder: "+e);
		}
		
		loadNewMap = false;
	}
	
	
	//TODO
	public BufferedImage getSubTiles(int zLayer, int x, int y){

		switch(zLayer){
		
		case 0: subMapBuff = mapBuff.getSubimage((xFloor2[x][y]-1)*90,(yFloor2[x][y]-1)*90,90,90);
				break;
				
		case 1: subMapBuff = mapBuff.getSubimage((xFloor1[x][y]-1)*90,(yFloor1[x][y]-1)*90,90,90);
				break;
		
		case 2: subMapBuff = mapBuff.getSubimage((xWall2[x][y]-1)*90,(yWall2[x][y]-1)*90,90,90);
				break;
				
		case 3: subMapBuff = mapBuff.getSubimage((xWall1[x][y]-1)*90,(yWall1[x][y]-1)*90,90,90);
				break;
				
		case 4: subMapBuff = mapBuff.getSubimage((xDoor[x][y]-1)*90,(yDoor[x][y]-1)*90,90,90);
				break;
		
		}
		
		return subMapBuff;

	}
	
	public void translateCoordinates(String layer, int x, int y, String yString, String xString){
		
		if(layer.contentEquals("#door#")){
			if(yString.contentEquals("a")){yDoor[x][y] = 1;}if(yString.contentEquals("b")){yDoor[x][y] = 2;}if(yString.contentEquals("c")){yDoor[x][y] = 3;}
			if(yString.contentEquals("d")){yDoor[x][y] = 4;}if(yString.contentEquals("e")){yDoor[x][y] = 5;}if(yString.contentEquals("f")){yDoor[x][y] = 6;}
			if(yString.contentEquals("g")){yDoor[x][y] = 7;}if(yString.contentEquals("h")){yDoor[x][y] = 8;}if(yString.contentEquals("i")){yDoor[x][y] = 9;}
			if(yString.contentEquals("y")){yDoor[x][y] = 10;}if(yString.contentEquals("k")){yDoor[x][y] = 11;}if(yString.contentEquals("l")){yDoor[x][y] = 12;}
			if(yString.contentEquals("m")){yDoor[x][y] = 13;}if(yString.contentEquals("n")){yDoor[x][y] = 14;}if(yString.contentEquals("x")){yDoor[x][y] = 14; xDoor[x][y] = 18;}
			if(!xString.contentEquals("0x"))
				xDoor[x][y] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#wall1#")){
			if(yString.contentEquals("a")){yWall1[x][y] = 1;}if(yString.contentEquals("b")){yWall1[x][y] = 2;}if(yString.contentEquals("c")){yWall1[x][y] = 3;}
			if(yString.contentEquals("d")){yWall1[x][y] = 4;}if(yString.contentEquals("e")){yWall1[x][y] = 5;}if(yString.contentEquals("f")){yWall1[x][y] = 6;}
			if(yString.contentEquals("g")){yWall1[x][y] = 7;}if(yString.contentEquals("h")){yWall1[x][y] = 8;}if(yString.contentEquals("i")){yWall1[x][y] = 9;}
			if(yString.contentEquals("y")){yWall1[x][y] = 10;}if(yString.contentEquals("k")){yWall1[x][y] = 11;}if(yString.contentEquals("l")){yWall1[x][y] = 12;}
			if(yString.contentEquals("m")){yWall1[x][y] = 13;}if(yString.contentEquals("n")){yWall1[x][y] = 14;}if(yString.contentEquals("x")){yWall1[x][y] = 14; xWall1[x][y] = 18;}
			if(!xString.contentEquals("0x"))
				xWall1[x][y] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#wall2#")){
			if(yString.contentEquals("a")){yWall2[x][y] = 1;}if(yString.contentEquals("b")){yWall2[x][y] = 2;}if(yString.contentEquals("c")){yWall2[x][y] = 3;}
			if(yString.contentEquals("d")){yWall2[x][y] = 4;}if(yString.contentEquals("e")){yWall2[x][y] = 5;}if(yString.contentEquals("f")){yWall2[x][y] = 6;}
			if(yString.contentEquals("g")){yWall2[x][y] = 7;}if(yString.contentEquals("h")){yWall2[x][y] = 8;}if(yString.contentEquals("i")){yWall2[x][y] = 9;}
			if(yString.contentEquals("y")){yWall2[x][y] = 10;}if(yString.contentEquals("k")){yWall2[x][y] = 11;}if(yString.contentEquals("l")){yWall2[x][y] = 12;}
			if(yString.contentEquals("m")){yWall2[x][y] = 13;}if(yString.contentEquals("n")){yWall2[x][y] = 14;}if(yString.contentEquals("x")){yWall2[x][y] = 14; xWall2[x][y] = 18;}
			if(!xString.contentEquals("0x"))
				xWall2[x][y] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#floor1#")){
			if(yString.contentEquals("a")){yFloor1[x][y] = 1;}if(yString.contentEquals("b")){yFloor1[x][y] = 2;}if(yString.contentEquals("c")){yFloor1[x][y] = 3;}
			if(yString.contentEquals("d")){yFloor1[x][y] = 4;}if(yString.contentEquals("e")){yFloor1[x][y] = 5;}if(yString.contentEquals("f")){yFloor1[x][y] = 6;}
			if(yString.contentEquals("g")){yFloor1[x][y] = 7;}if(yString.contentEquals("h")){yFloor1[x][y] = 8;}if(yString.contentEquals("i")){yFloor1[x][y] = 9;}
			if(yString.contentEquals("j")){yFloor1[x][y] = 10;}if(yString.contentEquals("k")){yFloor1[x][y] = 11;}if(yString.contentEquals("l")){yFloor1[x][y] = 12;}
			if(yString.contentEquals("m")){yFloor1[x][y] = 13;}if(yString.contentEquals("n")){yFloor1[x][y] = 14;}if(yString.contentEquals("x")){yFloor1[x][y] = 14; xFloor1[x][y] = 18;}
			if(!xString.contentEquals("0x"))
				xFloor1[x][y] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#floor2#")){
			if(yString.contentEquals("a")){yFloor2[x][y] = 1;}if(yString.contentEquals("b")){yFloor2[x][y] = 2;}if(yString.contentEquals("c")){yFloor2[x][y] = 3;}
			if(yString.contentEquals("d")){yFloor2[x][y] = 4;}if(yString.contentEquals("e")){yFloor2[x][y] = 5;}if(yString.contentEquals("f")){yFloor2[x][y] = 6;}
			if(yString.contentEquals("g")){yFloor2[x][y] = 7;}if(yString.contentEquals("h")){yFloor2[x][y] = 8;}if(yString.contentEquals("i")){yFloor2[x][y] = 9;}
			if(yString.contentEquals("y")){yFloor2[x][y] = 10;}if(yString.contentEquals("k")){yFloor2[x][y] = 11;}if(yString.contentEquals("l")){yFloor2[x][y] = 12;}
			if(yString.contentEquals("m")){yFloor2[x][y] = 13;}if(yString.contentEquals("n")){yFloor2[x][y] = 14;}if(yString.contentEquals("x")){yFloor2[x][y] = 14; xFloor2[x][y] = 18;}
			if(!xString.contentEquals("0x"))
				xFloor2[x][y] = Integer.parseInt(xString.toString());
		}
	}
	

	private void getMapID(){
		switch(DungeonNavigator.mapID){
		case 0: 	actualCoordinatesData = coordinatesData[0][0];
					break;
		case 1: 	actualCoordinatesData = coordinatesData[0][1];
					break;			
		case 2: 	actualCoordinatesData = coordinatesData[0][2];
					break;
		case 3: 	actualCoordinatesData = coordinatesData[0][3];
					break;
		case 4: 	actualCoordinatesData = coordinatesData[0][4];
					break;
					
		case 100: 	actualCoordinatesData = coordinatesData[1][0];
					break;
		case 101: 	actualCoordinatesData = coordinatesData[1][1];
					break;
		case 102: 	actualCoordinatesData = coordinatesData[1][2];
					break;
		case 103: 	actualCoordinatesData = coordinatesData[1][3];
					break;
		case 104: 	actualCoordinatesData = coordinatesData[1][4];
					break;

		case 200: 	actualCoordinatesData = coordinatesData[2][0];
					break;
		case 201: 	actualCoordinatesData = coordinatesData[2][1];
					break;
		case 202: 	actualCoordinatesData = coordinatesData[2][2];
					break;
		case 203: 	actualCoordinatesData = coordinatesData[2][3];
					break;
		case 204: 	actualCoordinatesData = coordinatesData[2][4];
					break;
					
		case 300: 	actualCoordinatesData = coordinatesData[3][0];
					break;
		case 301: 	actualCoordinatesData = coordinatesData[3][1];
					break;
		case 302: 	actualCoordinatesData = coordinatesData[3][2];
					break;
		case 303: 	actualCoordinatesData = coordinatesData[3][3];
					break;
		case 304: 	actualCoordinatesData = coordinatesData[3][4];
					break;
					
		case 400: 	actualCoordinatesData = coordinatesData[4][0];
					break;
		case 401: 	actualCoordinatesData = coordinatesData[4][1];
					break;
		case 402: 	actualCoordinatesData = coordinatesData[4][2];
					break;
		case 403: 	actualCoordinatesData = coordinatesData[4][3];
					break;
		case 404: 	actualCoordinatesData = coordinatesData[4][4];
					break;
		}
	}


}
