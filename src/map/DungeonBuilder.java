package map;

import game.objects.MapObject;
import game.objects.MarioDark;
import game.objects.Merchant;
import game.objects.Player;


import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import map.DungeonObjectManager.NavigationData;

import core.EnemyManager;
import core.FileLink;
import core.GameManager;
import core.GameObjectManager;


abstract class DungeonBuilder extends DungeonObjectManager implements Runnable, FileLink {
	
	//scroll
	private volatile int scrollX = 0; 
	private volatile int scrollY = 0;
	private int scrollPaintX = 0; 
	private int scrollPaintY = 0;
	final static int SCROLLSPEED_X = 60;  
	final static int SCROLLSPEED_Y = 50;
	
	/*
	 * layer 0 = Floor2
	 * layer 1 = Floor1
	 * layer 2 = Wall1
	 * layer 3 = Wall2
	 * layer 4 = Doors
	 * layer 5 = Objects
	 * layer 6 = Interaction
	 */
	
	//xTileData[xMap=maxMapRowX()][yMap=maxMapRowY()][layer=max7][xTile=max9][yTile=max7]
	private volatile int xTileData[][][][][];
	private volatile int yTileData[][][][][];
	
	/*
	 * type 0: toExit
	 * type 1: toNorth
	 * type 2: toEast
	 * type 3: toSouth
	 * type 4: toWest
	 */
	//navigationData[xMap][yMap][type=max5][dimension=max4]
	private volatile int xNavigationData[][][][];
	private volatile int yNavigationData[][][][];
	
	private BufferedImage mapBuff;
	//tileBuff[TileSet_Elements_X + 1][TileSet_Elements_Y + 1]: offset +1 in both Arrays to match TileData 
	private BufferedImage[][] tileBuff = new BufferedImage[19][15];
	
	//layerBuff[0 = currentMap 1 = threadBuild restMap][layer = max8] layer7: combined layer
	private BufferedImage[][] layerBuff = new BufferedImage[2][8];
	private BufferedImage mapImage = new BufferedImage(810*4,630*4,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage mapImageWall = new BufferedImage(810*4,630*4,BufferedImage.TYPE_INT_ARGB);
	private BufferedReader readDataBuff;
	
	private final String[] layerHeader = {"#floor2#","#floor1#","#wall2#","#wall1#","###"};
	//private final String[] layerHeader = {"#floor2#","#floor1#","#wall2#","#wall1#","###"};
	private final String[] navigationHeader = {"#toExit#", "#toNorth#", "#toEast#", "#toSouth#", "#toWest#"};
	private final String[] mapInformation = {"#enemy#","trap"};
	
	
	private String dungeonIDName, mapIDName;
	private BufferedImage clearImage;
	
	protected DungeonBuilder(){
		System.err.println("construct DungeonBuilder");	
		
		try {
			clearImage = new Robot().createScreenCapture(new Rectangle(0, 23, 810, 630));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void loadMapData(File mapData, File tileDungeon){
		if(!GameManager.mapLoaded){
			
			initializeArrays(mapData);
			readMapData(mapData);
			readMapTiles(tileDungeon);
		}

		buildMap(0);
	}
	
	
	protected void initializeArrays(File mapData){
		
		
		String searchLine;
		BufferedReader readDataBuff;
		try {
			readDataBuff = new BufferedReader(new FileReader(mapData));
			
			searchLine = readDataBuff.readLine();
			searchLine = readDataBuff.readLine();
			String stringXRow = searchLine;
			searchLine = readDataBuff.readLine();
			searchLine = readDataBuff.readLine();
			String stringYRow = searchLine;
			
			setMapRowX(Integer.parseInt(stringXRow.toString())+1);
			setMapRowY(Integer.parseInt(stringYRow.toString())+1);
			
	
			xTileData = new int[getMapRowX()][getMapRowY()][7][9][7];
			yTileData = new int[getMapRowX()][getMapRowY()][7][9][7];
			
			xNavigationData = new int[getMapRowX()][getMapRowY()][5][4];
			yNavigationData = new int[getMapRowX()][getMapRowY()][5][4];
			
			for(int j = 0; j < 2; j++){
				for(int i = 0; i < 8; i++){
					layerBuff[j][i] = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
				}
			}
			
			initializeBoundArrays();
	
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	protected boolean buildMap(int direction){
		
		GameManager.mapLoaded = false;
		
	
		
		for(int layer = 0; layer < 7; layer++){
			for(int i = 0; i < 2; i++){
				layerBuff[i][layer] = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
			}
			
		}
		Thread buildRestMap = new Thread(this);
		buildRestMap.start();
		
		for(int layer = 0; layer < 7; layer++){
			
			layerBuff[0][layer] = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);

			for(int yTile = 0; yTile < 7; yTile++){			
				for(int xTile = 0; xTile < 9; xTile++){
					
					//center: actual view
					if(!(xTileData[getXMap()][getYMap()][layer][xTile][yTile] == 18 && yTileData[getXMap()][getYMap()][layer][xTile][yTile] == 14))
					layerBuff[0][layer].createGraphics().drawImage(tileBuff[xTileData[getXMap()][getYMap()][layer][xTile][yTile]][yTileData[getXMap()][getYMap()][layer][xTile][yTile]],90*xTile, 90*yTile,null);
					//System.out.println("xTileData: "+xTileData[xMap][yMap][layer][xTile][yTile]);
					//System.out.println("yTileData: "+yTileData[xMap][yMap][layer][xTile][yTile]);

				}//xTile
			}//yTile

			mapImage.createGraphics().drawImage(layerBuff[0][layer], null, 810*getXMap(), 630*getYMap());

		}//for layer
		
		mapImageWall.createGraphics().drawImage(layerBuff[0][2], null, 810*getXMap(), 630*getYMap());
		mapImageWall.createGraphics().drawImage(layerBuff[0][3], null, 810*getXMap(), 630*getYMap());
		
		setMapImage(mapImage);
		setMapImageWall(mapImageWall);
		
		GameManager.mapLoaded = true;
		
		
		
		//addWallBoundPaint(Rectangle element)

		
		if(GameManager.mapLoaded)
			return true;
		else
			return false;
	}
	
	public void run(){

		
		//paint rest of Map
		for(int yMap = 0; yMap < 4; yMap++){
				
			for(int xMap = 0; xMap < 4; xMap++){	
				
				//System.out.println("_buildingMap@"+xMap+"x"+yMap);
				
				for(int layer = 0; layer < 7; layer++){
		
					layerBuff[1][layer] = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
					
					for(int yTile = 0; yTile < 7; yTile++){			
						for(int xTile = 0; xTile < 9; xTile++){
							
							if(xMap == getXMap() && yMap == getYMap())
								break;
							
							if(!(xTileData[xMap][yMap][layer][xTile][yTile] == 18 && yTileData[xMap][yMap][layer][xTile][yTile] == 14))
							//layerBuff[1][layer].setRGB(90*xTile, 90*yTile, 90, 90, tileBuff[xTileData[xMap][yMap][layer][xTile][yTile]][yTileData[xMap][yMap][layer][xTile][yTile]].getRGB(0, 0, 90, 90, null, 0, 90), 0, 90);
							layerBuff[1][layer].createGraphics().drawImage(tileBuff[xTileData[xMap][yMap][layer][xTile][yTile]][yTileData[xMap][yMap][layer][xTile][yTile]], 90*xTile, 90*yTile, null);
	
						}
					}
					
					mapImage.createGraphics().drawImage(layerBuff[1][layer], null, 810*xMap, 630*yMap);
					
					if(layer == 2 || layer == 3)
						mapImageWall.createGraphics().drawImage(layerBuff[1][layer], null, 810*xMap, 630*yMap);
				}
			
				
			}//for xMap
		}//for yMap
		
		
		setMapImage(mapImage);
		setMapImageWall(mapImageWall);

		System.out.println("_buildingMap complete");
		
		/*
		//setBounds for Paint
		ArrayList<Rectangle> navRectMap = getNavigationRectMap(getXMap(), getYMap());
		for(int index = 0; index < navRectMap.size(); index++){
			addNavigationBoundPaint(navRectMap.get(index));
		}
		*/
		
	}
	
	public void clearTileImage(int layer, int xTile, int yTile){
		//layerBuff[1][layer].createGraphics().drawImage(tileBuff[xTileData[xMap][yMap][layer][xTile][yTile]][yTileData[xMap][yMap][layer][xTile][yTile]], 90*xTile, 90*yTile, this);
		
		
		//layerBuff[0][layer].createGraphics().setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		//layerBuff[0][layer].createGraphics().fillRect(xTile, yTile, 90, 90);
		
		
		//AlphaComposite compositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
		
		/*
		layerBuff[0][layer].createGraphics().setComposite(compositeClear);
		layerBuff[0][layer].createGraphics().setColor(new Color(0,0,0,0));
		layerBuff[0][layer].createGraphics().fillRect(xTile, yTile, 90, 90);
		*/
		
		//layerBuff[0][layer].createGraphics().setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		//mapImage.createGraphics().drawImage(layerBuff[0][layer], null, 810*getXMap(), 630*getYMap());
		//layerBuff[0][layer].createGraphics().setPaint(Color.RED);
		//layerBuff[0][layer].createGraphics().fillOval(50,50,100,100);
		/*
		layerBuff[0][layer].getGraphics();
		layerBuff[0][layer].setBackground(new Color(255, 255, 255, 0));
        Rectangle screen = transformationContext.getScreen();
        graphics.clearRect(0,0, (int)screen.getWidth(), (int)screen.getHeight());
		*/
		//layerBuff[0][layer].setRGB(0, 0, 810, 630, mapImage.getRGB(810*getXMap(), 630*getYMap(), 810, 630, null, 0, 810), 0, 810);
	
		
		mapImage.createGraphics().drawImage(layerBuff[0][layer], null, 810*getXMap(), 630*getYMap());
		
		
		for(int layerTmp = 0; layerTmp < 7; layerTmp++){
			mapImage.createGraphics().drawImage(layerBuff[0][layerTmp], null, 810*getXMap(), 630*getYMap());
		}
		
		
		
		
		
		
		/*
		 * AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
Graphics2D g2d = (Graphics2D) image.getGraphics();
g2d.setComposite(composite);
g2d.setColor(new Color(0, 0, 0, 0));
g2d.fillRect(0, 0, 10, 10);
		 */
	}
	
	protected void setEnemy(){
		
		MarioDark.deleteAllInstances();
		//check and instantiate Enemy
		ArrayList<EnemyData<Integer, int[], int[]>> enemyDataMap = getEnemyDataMap(getXMap(), getYMap());
		
		
		for(int index = 0; index < enemyDataMap.size(); index++){
			int enemyType = enemyDataMap.get(index).getType();
			int[] enemyPosition = enemyDataMap.get(index).getPosition();
			int[] enemyAttributes = enemyDataMap.get(index).getAttributes();
			
			
			int xCoordinateMap = enemyPosition[2];
			int yCoordinateMap = enemyPosition[3] ;
			
			
			System.out.println("case.set Enemy1@Pos:"+xCoordinateMap+"x"+yCoordinateMap);
			//int xCoordinateMap = 0;
			//int yCoordinateMap = 0;
			
			switch(GameManager.scrollDirection){
			case(1):	yCoordinateMap -= 630;
						
						break;
			case(3):	xCoordinateMap += 810;
						break;
			case(5):	yCoordinateMap += 630;
						break;
			case(7):	xCoordinateMap -= 810;
						break;
			}
			
			
			
			//setBoss 2-1
			if(getXMap() == 2 && getYMap() == 1){
				
				if(!GameObjectManager.getBossStatusDefeated(21)){
					GameObjectManager.getInstance().constructBoss(21);
					EnemyManager.setNewEnemy(xCoordinateMap,yCoordinateMap,enemyType,enemyPosition,enemyAttributes);
					addMapObjectData(3, 2, 2, 1, 0, 370);
					System.out.println("setEnemyBoss 2");
				}	
			} else //setEnemy
				EnemyManager.setNewEnemy(xCoordinateMap,yCoordinateMap,enemyType,enemyPosition,enemyAttributes);
			

			System.err.println("======>DungeonBuilder.setEnemy");

		}
		

		//setMerchant
		if(getXMap() == 1 && getYMap() == 0){
			int xCoordinateMap = 180;
			int yCoordinateMap = 400;
			
			switch(GameManager.scrollDirection){
			case(1):	yCoordinateMap -= 630;
						
						break;
			case(3):	xCoordinateMap += 810;
						break;
			case(5):	yCoordinateMap += 630;
						break;
			case(7):	xCoordinateMap -= 810;
						break;
			}
			
			Merchant.getInstance(xCoordinateMap, yCoordinateMap);
		}
		
		//setBoss
		if((getXMap() == 2 && getYMap() == 2) || (getXMap() == 2 && getYMap() == 1) || (getXMap() == 2 && getYMap() == 0))
			setBossEnemy();
		
	
	}
	
	protected void setBossEnemy(){
		//setBoss 1
				
		int xCoordinateMap = 300;
		int yCoordinateMap = 300;
		
		switch(GameManager.scrollDirection){
		case(1):	yCoordinateMap -= 630;
					
					break;
		case(3):	xCoordinateMap += 810;
					break;
		case(5):	yCoordinateMap += 630;
					break;
		case(7):	xCoordinateMap -= 810;
					break;
		}
		
		int[][] enemyPositionBoss = new int[3][4];
		int[][] enemyAttributesBoss = new int[3][4];
		
		enemyPositionBoss[0][0] = 2; enemyPositionBoss[0][1] = 2; //mapXY
		enemyPositionBoss[0][2] = xCoordinateMap; enemyPositionBoss[0][3] = yCoordinateMap; //posXY
		
		enemyAttributesBoss[0][0] = 2; //enemySpeed
		enemyAttributesBoss[0][1] = 7; //enemyLife
		enemyAttributesBoss[0][2] = 5; //enemyLastDirection
		enemyAttributesBoss[0][3] = 11; //enemyPattern
		
		
		enemyPositionBoss[1][0] = 2; enemyPositionBoss[1][1] = 1; //mapXY
		enemyPositionBoss[1][2] = xCoordinateMap; enemyPositionBoss[1][3] = yCoordinateMap; //posXY
		
		enemyAttributesBoss[1][0] = 2; //enemySpeed
		enemyAttributesBoss[1][1] = 6; //enemyLife
		enemyAttributesBoss[1][2] = 5; //enemyLastDirection
		enemyAttributesBoss[1][3] = 12; //enemyPattern
		
		
		enemyPositionBoss[2][0] = 2; enemyPositionBoss[2][1] = 0; //mapXY
		enemyPositionBoss[2][2] = xCoordinateMap; enemyPositionBoss[2][3] = yCoordinateMap; //posXY
		
		enemyAttributesBoss[2][0] = 2; //enemySpeed
		enemyAttributesBoss[2][1] = 15; //enemyLife
		enemyAttributesBoss[2][2] = 5; //enemyLastDirection
		enemyAttributesBoss[2][3] = 13; //enemyPattern

		
		if(!GameObjectManager.getBossStatusDefeated(22) && getXMap() == 2 && getYMap() == 2){
			GameObjectManager.getInstance().constructBoss(22);
			EnemyManager.setNewEnemy(xCoordinateMap, yCoordinateMap, 22, enemyPositionBoss[0], enemyAttributesBoss[0]);
			System.out.println("setEnemyBoss 1 @Pos"+xCoordinateMap+"x"+yCoordinateMap);
		}
		/*
		if(!GameObjectManager.getBossStatusDefeated(21) && getXMap() == 2 && getYMap() == 1){
			GameObjectManager.getInstance().constructBoss(21);
			EnemyManager.setNewEnemy(xCoordinateMap, yCoordinateMap, 21, enemyPositionBoss[1], enemyAttributesBoss[1]);
			System.out.println("setEnemyBoss 2 @Pos"+xCoordinateMap+"x"+yCoordinateMap);
		}	
		*/
		if(!GameObjectManager.getBossStatusDefeated(20) && getXMap() == 2 && getYMap() == 0){
			GameObjectManager.getInstance().constructBoss(20);
			EnemyManager.setNewEnemy(xCoordinateMap, yCoordinateMap, 20, enemyPositionBoss[2], enemyAttributesBoss[2]);
			System.out.println("setEnemyBoss 3 @Pos"+xCoordinateMap+"x"+yCoordinateMap);
		}	
					
				
	}
	
	protected void readMapTiles(File mapTilesFile){
		try {
			mapBuff = ImageIO.read(mapTilesFile);
			//offset in tileBuff +1 to match tileData (Grid starting at 1-1)
			for(int yTile = 1; yTile < 15; yTile++){
				for(int xTile = 1; xTile < 19; xTile++){
					
					tileBuff[xTile][yTile] = new BufferedImage(90,90,BufferedImage.TYPE_INT_ARGB);
					tileBuff[xTile][yTile].setRGB(0, 0, 90, 90, mapBuff.getRGB(90*(xTile-1), 90*(yTile-1), 90, 90, null, 0, 90), 0, 90);
	
				}
			}
			
			
		} catch (IOException e) {
			System.err.println("DungeonBuilder.Error: Can't open MapTile");
		}
	}
	
	
	private void readMapData(File mapData){		
		
		String searchLine, dataLine;
		
		//mapData must contain 5x5 single mapID's, each containing 7 Layer, each 9x7
		//the whole mapData infomation is stored in static layerVariables during runtime
		//System.out.println("readData");
		
		try {
			BufferedReader readDataTmp = new BufferedReader(new FileReader(mapData));
			//System.out.println(readDataTmp.readLine());
			readDataBuff = readDataTmp;
		
			//start reading mapData
			searchLine = readDataBuff.readLine(); //DungeonName DungeonID
			dungeonIDName = searchLine;
			
			dungeonIDName = dungeonIDName.replace("#", "");
			dungeonIDName = dungeonIDName.replace("DungeonID","");
			
			
			if(dungeonIDName.startsWith("0"))
				dungeonIDName.substring(1);
			
			System.out.println("=mapID=>"+dungeonIDName);
			setMapID(Integer.parseInt(dungeonIDName.toString()));
			
			System.out.println("Dungeon: "+dungeonIDName);
			searchLine = readDataBuff.readLine();
			String stringXRow = searchLine;
			searchLine = readDataBuff.readLine();
			searchLine = readDataBuff.readLine();
			String stringYRow = searchLine;
			
			int mapRowXCompare = Integer.parseInt(stringXRow.toString())+1;
			int mapRowYCompare = Integer.parseInt(stringYRow.toString())+1;
			
			if(mapRowXCompare != getMapRowX() || mapRowYCompare != getMapRowY()){
				System.err.println("DungeonBuilder.Error: Wrong Checksum in mapRowXY");
				System.exit(0);
			}
			
			System.out.println("mapRowSize_X: "+getMapRowX());
			System.out.println("mapRowSize_Y: "+getMapRowY());
			setWidthMap(getMapRowX()*90*9);
			setHeightMap(getMapRowY()*90*7);
			
			int mapRowXSize = getMapRowX()-1;
			int mapRowYSize = getMapRowY()-1;

			readMapDataY:
				//map row Y	
				for(int mapIDY = 0; mapIDY < mapRowYSize; mapIDY++){
					
					readMapDataX:
					//map column X
					for(int mapIDX = 0; mapIDX < mapRowXSize; mapIDX++){
						
						searchLine = readDataBuff.readLine(); //mapID
						if(searchLine.contentEquals("###END###"))
							break readMapDataY;
			
						mapIDName = searchLine;
						mapIDName = mapIDName.replace("#", "");
						mapIDName = mapIDName.replace("_", "");
						String xMapIDString = mapIDName.substring(0, 2);
						String yMapIDString = mapIDName.substring(2,4);
						
						int xMapID = Integer.parseInt(xMapIDString.toString());
						int yMapID = Integer.parseInt(yMapIDString.toString());
						
						if(mapIDX != xMapID && mapIDY != yMapID){
							System.out.println("===>Map_"+xMapID+"x"+yMapID);
							System.out.println("<===Map_"+mapIDX+"x"+mapIDY);
							System.err.println("DungeonBuilder.Error: Wrong MapFormat");
							System.exit(0);
						}

					NavigationData:
					//Navigation
						for(int navigationID = 0; navigationID < 5; navigationID++){
							
							readDataBuff.mark(10);
							searchLine = readDataBuff.readLine(); //OrientationData
							dataLine = searchLine;
							
							
							do{ 
								
								if(!dataLine.contentEquals(navigationHeader[navigationID])){
									//System.out.println("NavigationHeader: "+navigationHeader[navigationID]+", DataLine: " +dataLine);
									
									navigationID++;
									if(navigationID > 4){
										readDataBuff.reset();
										break NavigationData;
									}
	
								}
							
								
							} while (!dataLine.contentEquals(navigationHeader[navigationID]));
								
							
							if(dataLine.contentEquals(navigationHeader[navigationID])){
								//System.out.println("Orientation: "+navigationHeader[navigationID]+" @navID: "+navigationID);
								
								do{
									readDataBuff.mark(20);
									searchLine = readDataBuff.readLine();
									dataLine = searchLine; //NavigationData
									
									if(dataLine.startsWith("#")){
										
										readDataBuff.reset();
										break;	
									}
									
									//System.out.println("NavigationData: "+dataLine);
										
									dataLine = dataLine.replace(",", "");
									dataLine = dataLine.replace("x", "");
									
									String xData = dataLine.substring(0, 4);
									String yData = dataLine.substring(4, 8);
									String widthData = dataLine.substring(8, 12);
									String heightData = dataLine.substring(12, 16);
									
									readDataBuff.mark(10);
									searchLine = readDataBuff.readLine();
									
									if(searchLine.contentEquals("Door"))
										writeNavigationBoundData(true, mapIDX, mapIDY, navigationID, xData, yData, widthData, heightData);
									else{
										writeNavigationBoundData(false, mapIDX, mapIDY, navigationID, xData, yData, widthData, heightData);
										readDataBuff.reset();
									}
										
									
									if(navigationID != 0){
										addNavigationToExitData(mapIDX, mapIDY, -1, -1, -1, -1, -1, -1);	
									}
									
									//set NavigationData for toExit
									if(navigationID == 0){
										
										String xMapData, yMapData;
										String mapIDData;
										String xPlayerData, yPlayerData;
										int mapType = -1;
										
										searchLine = readDataBuff.readLine();
										dataLine = searchLine;
										
										if(dataLine.contentEquals("dungeon"))
											mapType = 0;
										
										if(dataLine.contentEquals("overworld"))
											mapType = 1;
										
										dataLine = readDataBuff.readLine();
										//System.out.println("_MAP@"+mapIDX+"x"+mapIDY+" Data: "+dataLine);
										
										dataLine = dataLine.replace(",", "");
										dataLine = dataLine.replace("@ID", "");
										dataLine = dataLine.replace("x", "");
										
										xMapData = dataLine.substring(0, 4);
										yMapData = dataLine.substring(4, 8);
										mapIDData = dataLine.substring(8, 10);
										
										dataLine = readDataBuff.readLine();
										
										dataLine = dataLine.replace("x", "");
										xPlayerData = dataLine.substring(0, 4);
										yPlayerData = dataLine.substring(4, 8);
										
									
										System.out.println("_MAP@"+mapIDX+"x"+mapIDY+"====>mapType: "+mapType+"@ID:"+mapIDData+" to Map:"+xMapData+"x"+yMapData+", Player to:"+xPlayerData+"x"+yPlayerData);
										writeNavigationToExitData(mapIDX, mapIDY,mapType,mapIDData,xMapData,yMapData,xPlayerData,yPlayerData);
										
										
									}
									
								} while(!dataLine.startsWith("#"));
							}

						}
						
						//searchLine = readDataBuff.readLine();
						//searchLine = readDataBuff.readLine();
						
						
						
					//EnemyData
					EnemyData:
						for(int infoID = 0; infoID < 2; infoID++){
							
							readDataBuff.mark(10);
							searchLine = readDataBuff.readLine();
							
							
							System.out.println("EnemyData.for@"+infoID);
							
							if(searchLine.contentEquals("#Enemy#")){
								
								System.err.println("=>DungeonBuilder.EnemyData@Map_"+mapIDX+"x"+mapIDY);
								do{
									int enemyType;
									int[] enemyPosition = new int[4];
									int[] enemyAttributes = new int[4];
									
									dataLine = readDataBuff.readLine(); //EnemyType+X+Y
									dataLine = dataLine.replace("x", "");
									dataLine = dataLine.replace("@", "");
									
									
									enemyType = translateStringToInt(dataLine.substring(0, 4));
									
									enemyPosition[0] = mapIDX;
									enemyPosition[1] = mapIDY;
									enemyPosition[2] = translateStringToInt(dataLine.substring(4, 8)); //enemyX
									enemyPosition[3] = translateStringToInt(dataLine.substring(8,12)); //enemyY
									
									dataLine = readDataBuff.readLine();
									dataLine = dataLine.replace("x", "");
									enemyAttributes[0] = translateStringToInt(dataLine.substring(0, 4)); //enemySpeed
									enemyAttributes[1] = translateStringToInt(dataLine.substring(4, 8)); //enemyLife
									enemyAttributes[2] = translateStringToInt(dataLine.substring(8, 12)); //enemyLastDirection
									enemyAttributes[3] = translateStringToInt(dataLine.substring(12, 16)); //enemyPattern
									
									
									addEnemyData(enemyType, enemyPosition, enemyAttributes);
								
									readDataBuff.mark(10);
									searchLine = readDataBuff.readLine();
									
									if(!searchLine.startsWith("#")){
										readDataBuff.reset();
									} else {
										readDataBuff.reset();
										break;
									}
									
								
								
								} while (!searchLine.startsWith("#"));
								
							} else if(searchLine.contentEquals("#MapObject#")){
								
								do{
									readDataBuff.mark(10);
									
									dataLine = readDataBuff.readLine(); //Trap+X+Y
									dataLine = dataLine.replace("x", "");
									dataLine = dataLine.replace("@", "");
									System.out.println("====>TRAP@"+mapIDX+"x"+mapIDY+"@data:"+dataLine);
									String xPosition = dataLine.substring(0, 4);
									String yPosition = dataLine.substring(4, 8);
									String type = dataLine.substring(8, 9);
									String orientation = dataLine.substring(9, 10);
									writeMapObjectData(type, orientation, mapIDX, mapIDY, xPosition, yPosition);
									
									if(type.contentEquals("2")){
										dataLine = readDataBuff.readLine();
										dataLine = dataLine.replace("Item@", "");
										dataLine = dataLine.replace("x", "");
										int treasureID  = translateStringToInt(dataLine.substring(0, 1));
										int treasureType = translateStringToInt(dataLine.substring(1, 2));
										int treasureMember = translateStringToInt(dataLine.substring(2, 3));
										int[] treasureData = {treasureID, treasureType, treasureMember};
										GameObjectManager.getInstance().constructTreasure(mapIDX, mapIDY, translateStringToInt(xPosition), translateStringToInt(yPosition), treasureData);
									}
									
									readDataBuff.mark(10);
									searchLine = readDataBuff.readLine();
									
									if(!searchLine.startsWith("#")){
										readDataBuff.reset();
									} else {
										readDataBuff.reset();
										break;
									}
									
								} while (!searchLine.startsWith("#"));
								
								
							} else {
								readDataBuff.reset();
								break;
							}
							
						}//for mapInfo: enemy
						
						
						
						
				
					LayerData:
					//Layer
						for(int layerID = 3; layerID >= 0; layerID--){
							
							readDataBuff.mark(10);
							searchLine = readDataBuff.readLine(); //LayerName
							dataLine = searchLine;
							
							if(dataLine.startsWith("##")){
								
								for(int emptyLayerMap = 0; emptyLayerMap < 7; emptyLayerMap++){
									writeEmptyLayer(xMapID, yMapID, emptyLayerMap);
								}
								
								readDataBuff.reset();
								break LayerData;
							}
							
							//System.out.println("Map_"+mapIDX+"x"+mapIDY+" @Layer: "+dataLine);
							
							//compare and iterate layerHeader with dataLine (=layerName? !break)
							for(; !dataLine.contentEquals(layerHeader[layerID]); layerID--){
								writeEmptyLayer(mapIDX, mapIDY, layerID);
								if(layerID <= 0)
									break LayerData;
							}

							
							//System.out.println("LayerHeader: "+layerHeader[layerID]+", DataLine: " +dataLine);
							
							//single Layer
							for(int y = 0; y < 7; y++){	
								
								searchLine = readDataBuff.readLine(); //data
								dataLine = searchLine;
							
								dataLine = dataLine.replace(" ", "");
								for(int x = 0; x < 9; x++){
									//translates to int x,y 2dimensional arrays

									writeTileData(mapIDX,mapIDY,layerID,x,y,dataLine.substring(3*x,1+3*x), dataLine.substring(3*x+1,3+3*x));
									
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
	
protected void writeNavigationBoundData(boolean door, int xMap, int yMap, int orientation, String xData, String yData, String widthData, String heightData){

	int x = translateStringToInt(xData);
	int y = translateStringToInt(yData);
	int width = translateStringToInt(widthData);
	int height = translateStringToInt(heightData);
	
	/*
	System.out.println("xData:"+xData+"="+x);
	System.out.println("yData:"+yData+"="+y);
	System.out.println("widthData:"+widthData+"="+width);
	System.out.println("heightData:"+heightData+"="+height);
	*/
	System.out.println("Map_"+xMap+"x"+yMap+" @ "+navigationHeader[orientation]+":"+orientation+" Rectangle:"+x+"x"+y+","+width+"x"+height);
	
		addNavigationBound(xMap, yMap, orientation, new Rectangle(x,y,width,height));
	if(door)
		addMapObjectData(0, orientation, xMap, yMap, x, y);
	
	
}

protected void writeMapObjectData(String typeData, String orientationData, int xMap, int yMap, String xData, String yData){
	int type = translateStringToInt(typeData);
	int orientation = translateStringToInt(orientationData);
	int x = translateStringToInt(xData);
	int y = translateStringToInt(yData);
	
	addMapObjectData(type, orientation, xMap, yMap, x, y);
	
}

private void writeEmptyLayer(int xMap, int yMap, int layer){
	
	//System.out.println("writeTileData Map_"+xMap+"x"+yMap);
	
	for(int yTile = 0; yTile < 7; yTile++){
		for(int xTile = 0; xTile < 9; xTile++){
			yTileData[xMap][yMap][layer][xTile][yTile] = 14; 
			xTileData[xMap][yMap][layer][xTile][yTile] = 18;
		}
	}
}
	

private void writeNavigationToExitData(int mapIDX, int mapIDY, int mapType,String mapIDData, String xMapData,String yMapData,String xPlayerData,String yPlayerData){
	
	

	/*
	System.err.println("===========>mapData:"+mapIDData);
	System.out.println("===========>xMap   :"+xMapData);
	System.out.println("===========>yMap   :"+yMapData);
	System.out.println("===========>xPlayer:"+xPlayerData);
	System.out.println("===========>yPlayer:"+yPlayerData);
	*/
	
	int mapID = translateStringToInt(mapIDData);
	int xMap = translateStringToInt(xMapData);
	int yMap = translateStringToInt(yMapData);
	int xPlayer = translateStringToInt(xPlayerData);
	int yPlayer = translateStringToInt(yPlayerData);

	System.out.println("mapData:"+mapIDData+"="+mapID);
	System.out.println("xMap   :"+xMapData+"="+xMap);
	System.out.println("yMap   :"+yMapData+"="+yMap);
	System.out.println("xPlayer:"+xPlayerData+"="+xPlayer);
	System.out.println("yPlayer:"+yPlayerData+"="+yPlayer);
	
	
	addNavigationToExitData(mapIDX, mapIDY, mapType, mapID, xMap, yMap, xPlayer, yPlayer);

}

private int translateStringToInt(String numberString){

	int number = -1;
	//System.out.println("Check.translateStringToInt: Input@"+numberString+", lenght@"+numberString.length());
	
	if(numberString.length() > 1){
		for(int i = 0; i < numberString.length()+1; i++){
			if(numberString.startsWith("0"))
				numberString = numberString.substring(1);
			if(numberString.startsWith("0") && numberString.length() == 1)
				break;
		}
	}
	
	
	//System.out.println("Check.translateStringToInt: Output@"+numberString+", lenght@"+numberString.length());

	try{
		number = Integer.parseInt(numberString.toString());
		return number; 
	} catch(NumberFormatException e){
		System.err.println("DungeonBuilder.Error: translateStringToInt "+e);
	}
	
	if(number == -1)
		System.exit(0);

	return number;
	
	
}

protected void writeTileData(int xMap, int yMap, int layer, int xTile, int yTile, int xValue, int yValue){
	yTileData[xMap][yMap][layer][xTile][yTile] = xValue; 
	xTileData[xMap][yMap][layer][xTile][yTile] = yValue;
}

	//translation table, stores mapData in static Arrays
private void writeTileData(int xMap, int yMap, int layer, int xTile, int yTile, String yString, String xString){
	
	//System.out.println("writeTileData Map_"+xMap+"x"+yMap);
	//System.out.println("Map_"+xMap+"x"+yMap+" @layer: "+layer+" @Tile_"+xTile+"x"+yTile);

			if(yString.contentEquals("a")){yTileData[xMap][yMap][layer][xTile][yTile] = 1;}
			if(yString.contentEquals("b")){yTileData[xMap][yMap][layer][xTile][yTile] = 2;}
			if(yString.contentEquals("c")){yTileData[xMap][yMap][layer][xTile][yTile] = 3;}
			if(yString.contentEquals("d")){yTileData[xMap][yMap][layer][xTile][yTile] = 4;}
			if(yString.contentEquals("e")){yTileData[xMap][yMap][layer][xTile][yTile] = 5;}
			if(yString.contentEquals("f")){yTileData[xMap][yMap][layer][xTile][yTile] = 6;}
			if(yString.contentEquals("g")){yTileData[xMap][yMap][layer][xTile][yTile] = 7;}
			if(yString.contentEquals("h")){yTileData[xMap][yMap][layer][xTile][yTile] = 8;}
			if(yString.contentEquals("i")){yTileData[xMap][yMap][layer][xTile][yTile] = 9;}
			if(yString.contentEquals("j")){yTileData[xMap][yMap][layer][xTile][yTile] = 10;}
			if(yString.contentEquals("k")){yTileData[xMap][yMap][layer][xTile][yTile] = 11;}
			if(yString.contentEquals("l")){yTileData[xMap][yMap][layer][xTile][yTile] = 12;}
			if(yString.contentEquals("m")){yTileData[xMap][yMap][layer][xTile][yTile] = 13;}
			if(yString.contentEquals("n")){yTileData[xMap][yMap][layer][xTile][yTile] = 14;}
		
			if(yString.contentEquals("x")){
				yTileData[xMap][yMap][layer][xTile][yTile] = 14; 
				xTileData[xMap][yMap][layer][xTile][yTile] = 18;}
				
			if(!xString.contentEquals("0x"))
				xTileData[xMap][yMap][layer][xTile][yTile] = Integer.parseInt(xString.toString());
		
	
	}
	
	protected int[][][][][] getXTileDataArray(){return xTileData;}
	protected int[][][][][] getYTileDataArray(){return yTileData;}
	
	protected int getScrollX(){return scrollX;}
	protected int getScrollY(){return scrollY;}
	protected int getXTileDataElement(int xMap, int yMap, int layer, int xTile, int yTile){return xTileData[xMap][yMap][layer][xTile][yTile];}
	protected int getYTileDataElement(int xMap, int yMap, int layer, int xTile, int yTile){return yTileData[xMap][yMap][layer][xTile][yTile];}
	//
	protected void setScrollX(int scrollX){this.scrollX = scrollX;}
	protected void setScrollY(int scrollY){this.scrollY = scrollY;}
	protected synchronized void setXTileDataElement(int value, int xMap, int yMap, int layer, int xTile, int yTile){xTileData[xMap][yMap][layer][xTile][yTile] = value;}
	protected synchronized void setYTileDataElement(int value, int xMap, int yMap, int layer, int xTile, int yTile){yTileData[xMap][yMap][layer][xTile][yTile] = value;}


}
