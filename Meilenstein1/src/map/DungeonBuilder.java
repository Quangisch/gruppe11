package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import characters.Goomba;
import characters.Player;

import core.Board;
import core.FileLink;
import core.GameManager;


public class DungeonBuilder extends JComponent implements FileLink {
	
	private static Player player;
	private static Board board;
	private static DungeonNavigator dungeonNavigator;
	private static Goomba goomba;
	private static DynamicMapAnimation dynamicMapAnimation;
	
	Graphics2D g2d;
	
	BufferedImage tileBuff, subMapBuff, mapBuff;
	BufferedReader readDataBuff;
	
	//scroll
	static int scrollX = 0; static int scrollY = 0;
	static int scrollPaintX = 0; static int scrollPaintY = 0;
	final static int SCROLLSPEED_X = 60,  SCROLLSPEED_Y = 50;
	
	Graphics2D doorBuff, wall1Buff, wall2Buff, floor1Buff, floor2Buff;
	
	//layer variables
	static int xInteraction[][][][] = new int[5][5][9][7];static int yInteraction[][][][] = new int [5][5][9][7];
	static int xObjects[][][][] = new int[5][5][9][7];static int yObjects[][][][] = new int [5][5][9][7];
	static int xDoor[][][][] = new int[5][5][9][7];static int yDoor[][][][] = new int [5][5][9][7];
	static int xWall1[][][][] = new int[5][5][9][7];static int yWall1[][][][] = new int [5][5][9][7];
	static int xWall2[][][][] = new int[5][5][9][7];static int yWall2[][][][] = new int [5][5][9][7];
	static int xFloor1[][][][] = new int[5][5][9][7];static int yFloor1[][][][] = new int [5][5][9][7];
	static int xFloor2[][][][] = new int[5][5][9][7];static int yFloor2[][][][] = new int [5][5][9][7];
	
	static final String[] layerData = {"#interaction#","#objects#","#door#","#wall1#","#wall2#","#floor1#","#floor2#","###"};
	
	static boolean mapDataBuffered = false;
	
	String dungeonIDName, mapIDName;
	
	
	public DungeonBuilder(){
		System.err.println("->DungeonBuilder");
		
		
	}
	
	public DungeonBuilder(Player player, Goomba goomba, DungeonNavigator dungeonNavigator, DynamicMapAnimation dynamicMapAnimation, Board board){
		this.player = player;
		this.dynamicMapAnimation = dynamicMapAnimation;
		this.dungeonNavigator = dungeonNavigator;
		this.board = board;
		this.goomba = goomba;
	}

	public void paintComponents(Graphics g){
		g2d = (Graphics2D) g;
	
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,(yFloor2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollX+scrollX+dynamicMapAnimation.getShakeX(6,xTile,yTile),yTile*90+scrollY+scrollX+dynamicMapAnimation.getShakeY(6,xTile,yTile),this);
				}
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,(yFloor1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollX+dynamicMapAnimation.getShakeX(5,xTile,yTile),yTile*90+scrollY+dynamicMapAnimation.getShakeY(5,xTile,yTile),this);
				}
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xWall2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,(yWall2[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollX,yTile*90+scrollY,this);
				}
				
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,(yWall1[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollX,yTile*90+scrollY,this);
				}
			}
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,(yDoor[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollX,yTile*90+scrollY,this);
				}
			}
			
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xObjects[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,(yObjects[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollX,yTile*90+scrollY,this);
				}
			}
			
			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					g2d.drawImage(mapBuff.getSubimage((xInteraction[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,(yInteraction[dungeonNavigator.getX()][dungeonNavigator.getY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollX,yTile*90+scrollY,this);
				}
			}
			
			
			
	
			if(!dungeonNavigator.getScrollReady()){
				for(int yTile = 0; yTile < 7; yTile++){			
					for(int xTile = 0; xTile < 9; xTile++){
						g2d.drawImage(mapBuff.getSubimage((xFloor2[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,(yFloor2[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollPaintX,yTile*90+scrollPaintY,this);
					}
				}
				for(int yTile = 0; yTile < 7; yTile++){			
					for(int xTile = 0; xTile < 9; xTile++){
						g2d.drawImage(mapBuff.getSubimage((xFloor1[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,(yFloor1[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollPaintX,yTile*90+scrollPaintY,this);
					}
				}
				for(int yTile = 0; yTile < 7; yTile++){			
					for(int xTile = 0; xTile < 9; xTile++){
						g2d.drawImage(mapBuff.getSubimage((xWall2[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,(yWall2[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollPaintX,yTile*90+scrollPaintY,this);
					}
					
				}
				for(int yTile = 0; yTile < 7; yTile++){			
					for(int xTile = 0; xTile < 9; xTile++){
						g2d.drawImage(mapBuff.getSubimage((xWall1[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,(yWall1[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollPaintX,yTile*90+scrollPaintY,this);
					}
				}
				for(int yTile = 0; yTile < 7; yTile++){			
					for(int xTile = 0; xTile < 9; xTile++){
						g2d.drawImage(mapBuff.getSubimage((xDoor[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,(yDoor[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollPaintX,yTile*90+scrollPaintY,this);
					}
				}
				
				for(int yTile = 0; yTile < 7; yTile++){			
					for(int xTile = 0; xTile < 9; xTile++){
						g2d.drawImage(mapBuff.getSubimage((xObjects[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,(yObjects[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollPaintX,yTile*90+scrollPaintY,this);
					}
				}
				
				for(int yTile = 0; yTile < 7; yTile++){			
					for(int xTile = 0; xTile < 9; xTile++){
						g2d.drawImage(mapBuff.getSubimage((xInteraction[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,(yInteraction[dungeonNavigator.getOldX()][dungeonNavigator.getOldY()][xTile][yTile]-1)*90,90,90),xTile*90+scrollPaintX,yTile*90+scrollPaintY,this);
					}
				}
			}
			
	}
	

	
	public void readMapData(){		
		
		
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
						//System.out.println("Map: "+mapIDName);
						
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

		dungeonNavigator.setLoadNewMap(false);
	}
	
	public void scrollBetweenMaps(){
		if(GameManager.printMsg){
			System.out.println("scrollBetween");
			System.out.println(dungeonNavigator.getOldY() + ", "+dungeonNavigator.getY());
			System.out.println(dungeonNavigator.getOldX() + ", "+dungeonNavigator.getX());
		}
		
		if(dungeonNavigator.getOldY() > dungeonNavigator.getY()){
			dungeonNavigator.setScrollReady(false);
			scrollY += SCROLLSPEED_Y;
			scrollPaintY += SCROLLSPEED_Y;
			//System.out.println("scroll to North: " + scrollY);
			
			player.setY(540+scrollY);
			player.setOldX(player.getX());
			player.setOldY(player.getY());
			if(scrollY >= 0){System.out.println("North");scrollY = 0;scrollPaintY = 0;dungeonNavigator.setScrollReady(true);}
		}
		if(dungeonNavigator.getOldX() < dungeonNavigator.getX()){
			dungeonNavigator.setScrollReady(false);
			scrollX -= SCROLLSPEED_X;
			scrollPaintX -= SCROLLSPEED_X;
			//System.out.println("scroll to East: " + scrollX);
			
			player.setX(scrollX+15);
			player.setOldX(player.getX());
			player.setOldY(player.getY());
			if(scrollX <= 0){System.out.println("East");scrollX = 0;scrollPaintX = 0;dungeonNavigator.setScrollReady(true);}
		}
		if(dungeonNavigator.getOldY() < dungeonNavigator.getY()){
			dungeonNavigator.setScrollReady(false);
			scrollY -= SCROLLSPEED_Y;
			scrollPaintY -= SCROLLSPEED_Y;
			//System.out.println("scroll to South: " + scrollY);

			player.setY(scrollY);
			player.setOldX(player.getX());
			player.setOldY(player.getY());
			if(scrollY <= 0){System.out.println("South");scrollY = 0;scrollPaintY = 0;dungeonNavigator.setScrollReady(true);}
		}	
		if(dungeonNavigator.getOldX() > dungeonNavigator.getX()){
			dungeonNavigator.setScrollReady(false);
			scrollX += SCROLLSPEED_X;
			scrollPaintX += SCROLLSPEED_X;
			//System.out.println("scroll to West: " + scrollX);

			player.setX(720+scrollX);
			player.setOldX(player.getX());
			player.setOldY(player.getY());
			if(scrollX >= 0){System.out.println("West");scrollX = 0;scrollPaintX = 0;dungeonNavigator.setScrollReady(true);}
		}
	
		
	}
	
	
	
	//translation table, stores mapData in static Arrays
	public void translateCoordinates(String layer, int xMap, int yMap, int xLayer, int yLayer, String yString, String xString){
		
		if(layer.contentEquals("#interaction#")){
			if(yString.contentEquals("a")){yInteraction[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yInteraction[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yInteraction[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yInteraction[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yInteraction[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yInteraction[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yInteraction[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yInteraction[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yInteraction[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("j")){yInteraction[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yInteraction[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yInteraction[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yInteraction[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yInteraction[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yInteraction[xMap][yMap][xLayer][yLayer] = 14; xInteraction[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xInteraction[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#objects#")){
			if(yString.contentEquals("a")){yObjects[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yObjects[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yObjects[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yObjects[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yObjects[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yObjects[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yObjects[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yObjects[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yObjects[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("j")){yObjects[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yObjects[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yObjects[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yObjects[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yObjects[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yObjects[xMap][yMap][xLayer][yLayer] = 14; xObjects[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xObjects[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#door#")){
			if(yString.contentEquals("a")){yDoor[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yDoor[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yDoor[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yDoor[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yDoor[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yDoor[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yDoor[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yDoor[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yDoor[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("j")){yDoor[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yDoor[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yDoor[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yDoor[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yDoor[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yDoor[xMap][yMap][xLayer][yLayer] = 14; xDoor[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xDoor[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#wall1#")){
			if(yString.contentEquals("a")){yWall1[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yWall1[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yWall1[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yWall1[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yWall1[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yWall1[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yWall1[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yWall1[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yWall1[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("j")){yWall1[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yWall1[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yWall1[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yWall1[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yWall1[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yWall1[xMap][yMap][xLayer][yLayer] = 14; xWall1[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xWall1[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
		if(layer.contentEquals("#wall2#")){
			if(yString.contentEquals("a")){yWall2[xMap][yMap][xLayer][yLayer] = 1;}if(yString.contentEquals("b")){yWall2[xMap][yMap][xLayer][yLayer] = 2;}if(yString.contentEquals("c")){yWall2[xMap][yMap][xLayer][yLayer] = 3;}
			if(yString.contentEquals("d")){yWall2[xMap][yMap][xLayer][yLayer] = 4;}if(yString.contentEquals("e")){yWall2[xMap][yMap][xLayer][yLayer] = 5;}if(yString.contentEquals("f")){yWall2[xMap][yMap][xLayer][yLayer] = 6;}
			if(yString.contentEquals("g")){yWall2[xMap][yMap][xLayer][yLayer] = 7;}if(yString.contentEquals("h")){yWall2[xMap][yMap][xLayer][yLayer] = 8;}if(yString.contentEquals("i")){yWall2[xMap][yMap][xLayer][yLayer] = 9;}
			if(yString.contentEquals("j")){yWall2[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yWall2[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yWall2[xMap][yMap][xLayer][yLayer] = 12;}
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
			if(yString.contentEquals("j")){yFloor2[xMap][yMap][xLayer][yLayer] = 10;}if(yString.contentEquals("k")){yFloor2[xMap][yMap][xLayer][yLayer] = 11;}if(yString.contentEquals("l")){yFloor2[xMap][yMap][xLayer][yLayer] = 12;}
			if(yString.contentEquals("m")){yFloor2[xMap][yMap][xLayer][yLayer] = 13;}if(yString.contentEquals("n")){yFloor2[xMap][yMap][xLayer][yLayer] = 14;}if(yString.contentEquals("x")){yFloor2[xMap][yMap][xLayer][yLayer] = 14; xFloor2[xMap][yMap][xLayer][yLayer] = 18;}
			if(!xString.contentEquals("0x"))
				xFloor2[xMap][yMap][xLayer][yLayer] = Integer.parseInt(xString.toString());
		}
		
	}
	
	public void setEnemyLife(int life){
		goomba.setLife(life);	
	}
	
	public void setEnemyPosition(int x, int y){
		goomba.setX(x);
		goomba.setY(y);
	}

	

}
