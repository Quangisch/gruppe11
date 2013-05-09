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
	static int xInteraction[][][][] = new int[5][5][9][7];int yInteraction[][][][] = new int [5][5][9][7];
	static int xObjects[][][][] = new int[5][5][9][7];int yObjects[][][][] = new int [5][5][9][7];
	static int xDoor[][][][] = new int[5][5][9][7];int yDoor[][][][] = new int [5][5][9][7];
	static int xWall1[][][][] = new int[5][5][9][7];int yWall1[][][][] = new int [5][5][9][7];
	static int xWall2[][][][] = new int[5][5][9][7];int yWall2[][][][] = new int [5][5][9][7];
	static int xFloor1[][][][] = new int[5][5][9][7];int yFloor1[][][][] = new int [5][5][9][7];
	static int xFloor2[][][][] = new int[5][5][9][7];int yFloor2[][][][] = new int [5][5][9][7];
	
	static final String[] layerData = {"#interaction#","#objects#","#door#","#wall1#","#wall2#","#floor1#","#floor2#","###"};
	
	/*old*/static final String[][] coordinatesData = 		{{"##00_00##","##00_01##","##00_02##","##00_03##","##00_04##"},
													{"##01_00##","##01_01##","##01_02##","##01_03##","##01_04##"},
													{"##02_00##","##02_01##","##02_02##","##02_03##","##02_04##"},
													{"##03_00##","##03_01##","##03_02##","##03_03##","##03_04##"},
													{"##04_00##","##04_01##","##04_02##","##04_03##","##04_04##"}};
	
	String actualCoordinatesData;
	static boolean mapDataBuffered = false;
	
	static String dungeonIDName, mapIDName;
	
	
	public DungeonBuilder(){
		System.out.println("coordinatesData:"+coordinatesData[1][2]);
		System.err.println("->MapBuilder");
		
		for(int y = 0; y < 7; y++){			
			for(int x = 0; x < 9; x++){
				System.out.println("Objectx:"+xObjects[0][3][x][y]+",y:"+yObjects[0][3][x][y]);
			}
			System.out.println("");
		}
		for(int y = 0; y < 7; y++){			
			for(int x = 0; x < 9; x++){
				System.out.println("Interactionx:"+xInteraction[0][3][x][y]+",y:"+yInteraction[0][3][x][y]);
			}
			System.out.println("");
		}
		
	}
	
	public void run(){
		
		
		if(DungeonNavigator.loadNewMap){
			System.out.println("loadNewMap:"+DungeonNavigator.loadNewMap);
			readData();
			DungeonNavigator.loadNewMap = false;
		}
		
	}
	
	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
	
		
		//if(Map.dungeon){
		
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xFloor2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yFloor2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90),xTile*90,yTile*90,this);
				}
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xFloor1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yFloor1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90),xTile*90,yTile*90,this);
				}
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xWall2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yWall2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90),xTile*90,yTile*90,this);
				}
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90),xTile*90,yTile*90,this);
				}
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xDoor[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yDoor[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90),xTile*90,yTile*90,this);
				}
			}
			
			/*
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xObjects[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yObjects[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90),xTile*90,yTile*90,this);
				}
			}
			
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xInteraction[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yInteraction[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90),xTile*90,yTile*90,this);
				}
			}
			*/
			
		//}

	}
	

	
	private void readData(){		
		
		String searchLine, dataLine;
		
		//System.out.println("readData");
		
		try {
			BufferedReader readDataTmp = new BufferedReader(new FileReader(mapDataID01));
			//System.out.println(readDataTmp.readLine());
			readDataBuff = readDataTmp;
			mapBuff = ImageIO.read(tiles_Dungeon01);
			
			//mapData must contain 5x5 single mapID's, each containing 7 Layer, each 9x7
			//the whole mapData infomation is stored in static layerVariables during runtime
			
			//start reading mapData
			searchLine = readDataBuff.readLine(); //DungeonName DungeonID
			dungeonIDName = searchLine;
			System.out.println("Dungeon: "+dungeonIDName);
			
			
			readMapData:
				//map row Y	
				for(int mapIDY = 0; mapIDY < 5; mapIDY++){
					
					//map column X
					for(int mapIDX = 0; mapIDX < 5; mapIDX++){
						
						searchLine = readDataBuff.readLine(); //mapID
						if(searchLine.contentEquals("###END###"))
							break readMapData;
						mapIDName = searchLine;
						System.out.println("Map: "+mapIDName);
						
						//Layer
						for(int layerID = 0; layerID < 7; layerID++){
							searchLine = readDataBuff.readLine(); //LayerName
							//System.out.println("Layer: "+searchLine);
							
							//single Layer
							for(int y = 0; y < 7; y++){	
								
								searchLine = readDataBuff.readLine(); //data
								dataLine = searchLine;
							
								dataLine = dataLine.replace(" ", "");
								for(int x = 0; x < 9; x++){
									//translates to int x,y 2dimensional arrays
									translateCoordinates(layerData[layerID], mapIDX,mapIDY,x,y,dataLine.substring(3*x,1+3*x), dataLine.substring(3*x+1,3+3*x));
								}
							}//for: one Layer completed
							
						}//for: all Layer completed

					}//for: map colum X completed

				}//for: map row Y completed
				
		} catch (FileNotFoundException e){
			System.err.println("MapBuilder: "+e);
		} catch (IOException e) {
			System.err.println("MapBuilder: "+e);
		}
		

	}
	
	
	//OLD: not used anymore
	public BufferedImage getSubTiles(int zLayer, int xTile, int yTile){

		switch(zLayer){
		
		case 0: subMapBuff = mapBuff.getSubimage((xFloor2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yFloor2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90);
				break;
				
		case 1: subMapBuff = mapBuff.getSubimage((xFloor1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yFloor1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90);
				break;
		
		case 2: subMapBuff = mapBuff.getSubimage((xWall2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yWall2[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90);
				break;
				
		case 3: subMapBuff = mapBuff.getSubimage((xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90);
				break;
				
		case 4: subMapBuff = mapBuff.getSubimage((xDoor[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,(yDoor[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]-1)*90,90,90);
				break;
		
		}
		
		return subMapBuff;

	}
	
	public void translateCoordinates(String layer, int xMap, int yMap, int xLayer, int yLayer, String yString, String xString){
		
		if(layer.contentEquals("#door#")){
			if(yString.contentEquals("a")){yDoor[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yDoor[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yDoor[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yDoor[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yDoor[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yDoor[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yDoor[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yDoor[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yDoor[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("y")){yDoor[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yDoor[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yDoor[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yDoor[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yDoor[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yDoor[xMap][yMap][xLayer][yLayer] = 14; xDoor[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xDoor[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#wall1#")){
			if(yString.contentEquals("a")){yWall1[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yWall1[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yWall1[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yWall1[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yWall1[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yWall1[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yWall1[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yWall1[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yWall1[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("y")){yWall1[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yWall1[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yWall1[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yWall1[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yWall1[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yWall1[xMap][yMap][xLayer][yLayer] = 14; xWall1[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xWall1[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#wall2#")){
			if(yString.contentEquals("a")){yWall2[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yWall2[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yWall2[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yWall2[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yWall2[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yWall2[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yWall2[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yWall2[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yWall2[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("y")){yWall2[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yWall2[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yWall2[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yWall2[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yWall2[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yWall2[xMap][yMap][xLayer][yLayer] = 14; xWall2[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xWall2[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#floor1#")){
			if(yString.contentEquals("a")){yFloor1[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yFloor1[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yFloor1[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yFloor1[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yFloor1[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yFloor1[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yFloor1[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yFloor1[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yFloor1[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("j")){yFloor1[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yFloor1[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yFloor1[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yFloor1[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yFloor1[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yFloor1[xMap][yMap][xLayer][yLayer] = 14; xFloor1[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xFloor1[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#floor2#")){
			if(yString.contentEquals("a")){yFloor2[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yFloor2[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yFloor2[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yFloor2[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yFloor2[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yFloor2[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yFloor2[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yFloor2[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yFloor2[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("y")){yFloor2[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yFloor2[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yFloor2[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yFloor2[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yFloor2[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yFloor2[xMap][yMap][xLayer][yLayer] = 14; xFloor2[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xFloor2[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
	}


}
