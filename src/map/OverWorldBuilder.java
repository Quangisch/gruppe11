package map;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GameManager;

abstract class OverWorldBuilder extends OverWorldObjectManager {

	int xRowSize, yRowSize;
	
	protected OverWorldBuilder(){
		
	}
	
	private BufferedImage mapBuff;
	
	protected boolean loadMap(File mapImage, File mapData){
		
		try {
			GameManager.mapLoaded = false;
			
			mapBuff = ImageIO.read(mapImage);
			readData(mapData);
			
			setMapImage(mapBuff);
			GameManager.mapLoaded = true;
			
		} catch (IOException e) {
			System.err.println("OverWorldMap: MapFile not found");
			System.exit(0);
		} 
		
		
		
		if(GameManager.mapLoaded)
			return true;
		else
			return false;
	}
	

	private void readData(File mapData){
		BufferedReader readDataBuff;
		String searchLine, dataLine;
		
		
		
		try {
			readDataBuff = new BufferedReader(new FileReader(mapData));
			searchLine = readDataBuff.readLine(); //mapID
			searchLine = readDataBuff.readLine();
			dataLine = searchLine;
	
			xRowSize = Integer.parseInt(searchLine.toString()) * 2;
			searchLine = readDataBuff.readLine();
			searchLine = readDataBuff.readLine();
			yRowSize = Integer.parseInt(searchLine.toString()) * 2;
		
			
			searchLine = readDataBuff.readLine(); //to Exit
			
			//
			do{
				readDataBuff.mark(20);
				searchLine = readDataBuff.readLine();
				dataLine = searchLine; //NavigationData
				
				if(dataLine.startsWith("#")){
					
					readDataBuff.reset();
					break;	
				}
				
				System.out.println("NavigationData: "+dataLine);
					
				dataLine = dataLine.replace(",", "");
				dataLine = dataLine.replace("x", "");
				
				String xData = dataLine.substring(0, 4);
				String yData = dataLine.substring(4, 8);
				String widthData = dataLine.substring(8, 12);
				String heightData = dataLine.substring(12, 16);
				
			
				writeToExitBound(xData, yData, widthData, heightData);
				
				//set NavigationData for toExit
				
					
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
					System.out.println("_MAP@ Data: "+dataLine);
					
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
					
				
					System.out.println("_MAPToExit ====>mapType: "+mapType+"@ID:"+mapIDData+" to Map:"+xMapData+"x"+yMapData+", Player to:"+xPlayerData+"x"+yPlayerData);

					//writeNavigationToExitData(xData, yData, widthData, heightData);
					writeNavigationToExitData(mapType,mapIDData,xMapData,yMapData,xPlayerData,yPlayerData);
				
				
			} while(!dataLine.startsWith("#"));
			
			searchLine = readDataBuff.readLine();
			dataLine = readDataBuff.readLine();
			//dataLine = readDataBuff.readLine();
			
			
			
			String upperLine = null;
			String lowerLine = null;
			for(int yRow = 0; yRow < yRowSize; yRow++){
		
				//System.out.println("yRow@"+yRow+", to max@"+yRowSize);
				
				String copyLine;
					//initialRoutine
					//if(yRow == 0){
						
					if(yRow == 0){
						upperLine = "";
						for(int i = 0; i < (xRowSize+2); i++)
							upperLine = upperLine.concat("x");
					} else {
							
							
						copyLine = readDataBuff.readLine();
						copyLine = copyLine.substring(2);
						copyLine = copyLine.concat("x");
						upperLine = "x";
						upperLine =  upperLine.concat(copyLine);
							
					}
						
					readDataBuff.mark(xRowSize*4);
						
					copyLine = readDataBuff.readLine();
					copyLine = copyLine.substring(2);
					copyLine = copyLine.concat("x");
					dataLine = "x";
					dataLine = dataLine.concat(copyLine);
					
					if(yRow < yRowSize - 1){
						copyLine = readDataBuff.readLine();
						copyLine = copyLine.substring(2);
						copyLine = copyLine.concat("x");
						lowerLine = "x";
						lowerLine = lowerLine.concat(copyLine);
					} else {
						lowerLine = "";
						for(int i = 0; i < (xRowSize+2); i++)
							lowerLine = lowerLine.concat("x");
					}
					
					
					
					readDataBuff.reset();
					
					/*
					System.err.println("UpperLine :"+upperLine);
					System.err.println("DataLine  :"+dataLine);
					System.err.println("LowerLine :"+lowerLine);
					*/
					
					//System.out.println(lowerLine+"@yRow:"+yRow+", to:"+yRowSize);
					
					readDataBuff.mark(xRowSize*4);
					
					for(int xRow = 0; xRow < xRowSize; xRow++){
				
						//System.out.println("xRow@"+xRow+", to max@"+xRowSize);
						
						String upperBlock;
						String dataBlock;
						String lowerBlock;
						
						upperBlock = upperLine.substring(xRow,3+xRow);
						dataBlock = dataLine.substring(xRow,3+xRow);
						lowerBlock = lowerLine.substring(xRow,3+xRow);
						
						/*
						System.out.println("UpperBlock: "+upperBlock+"@row"+xRow+"x"+yRow);
						System.out.println("DataBlock:  "+dataBlock);
						System.out.println("LowerBlock: "+lowerBlock);
						*/
						
						if(dataBlock.substring(1,2).contentEquals("x")){
							
							int pushDirection = 0;
							
							if((upperBlock.contentEquals("   ") && lowerBlock.contentEquals("xxx"))
									|| (upperBlock.contentEquals("x x") && lowerBlock.contentEquals("xxx")))
								pushDirection = 1;
							if((upperBlock.contentEquals("xx ") && lowerBlock.contentEquals("xxx"))
									|| (upperBlock.contentEquals("x  ") && lowerBlock.contentEquals("xxx")))
								pushDirection = 2;
							if((upperBlock.contentEquals("xx ") && lowerBlock.contentEquals("xx "))
								|| (upperBlock.contentEquals("xxx") && lowerBlock.contentEquals("xxx")))
								pushDirection = 3;
							if((upperBlock.contentEquals("xxx") && lowerBlock.contentEquals("xx "))
									|| (upperBlock.contentEquals("xxx") && lowerBlock.contentEquals("x  ")))
								pushDirection = 4;
							if((upperBlock.contentEquals("xxx") && lowerBlock.contentEquals("   "))
									|| (upperBlock.contentEquals("xxx") && lowerBlock.contentEquals("x x")))
								pushDirection = 5;
							if((upperBlock.contentEquals("xxx") && lowerBlock.contentEquals(" xx"))
									|| (upperBlock.contentEquals("xxx") && lowerBlock.contentEquals("  x")))
								pushDirection = 6;
							if((upperBlock.contentEquals(" xx") && lowerBlock.contentEquals(" xx"))
									|| (upperBlock.contentEquals("xxx") && lowerBlock.contentEquals("xxx")))
								pushDirection = 7;
							if((upperBlock.contentEquals(" xx") && lowerBlock.contentEquals("xxx"))
									|| (upperBlock.contentEquals("  x") && lowerBlock.contentEquals("xxx")))
								pushDirection = 8;
							
							if((upperBlock.contentEquals(" x ") && lowerBlock.contentEquals(" x "))
									|| (upperBlock.contentEquals("   ") && dataBlock.contentEquals("xxx") && lowerBlock.contentEquals("   "))
									|| (upperBlock.contentEquals(" x ") && dataBlock.contentEquals(" x ") && lowerBlock.contentEquals("   "))
									|| (upperBlock.contentEquals("   ") && dataBlock.contentEquals(" x ") && lowerBlock.contentEquals(" x ")))
								pushDirection = 0;
							
						
							String data = dataBlock.substring(1, 2);
							
							if(data.contentEquals("x"))
								addWallBound(pushDirection, new Rectangle(45*xRow,45*yRow,45,45));
							
							
						}// if data == x
						else {
							System.out.println("emptyBlock@ "+xRow+"x"+yRow);
						}

						readDataBuff.reset();
						
					} //for xRow End
				
			}//for yRow End
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	protected void writeToExitBound(String xData, String yData, String widthData, String heightData){
		
		int x, y, width, height;
		
		for(int i = 0; i < 3; i++){
			if(xData.startsWith("0"))
				xData = xData.substring(1);
			else
				break;
		}

		for(int i = 0; i < 3; i++){
			if(yData.startsWith("0"))
				yData = yData.substring(1);
			else
				break;
		}
		
		for(int i = 0; i < 3; i++){
			if(widthData.startsWith("0"))
				widthData = widthData.substring(1);
			else
				break;
		}
		
		for(int i = 0; i < 3; i++){
			if(heightData.startsWith("0"))
				heightData = heightData.substring(1);
			else
				break;
		}

		
		x = Integer.parseInt(xData.toString());
		y = Integer.parseInt(yData.toString());
		width = Integer.parseInt(widthData.toString());
		height = Integer.parseInt(heightData.toString());
		/*
		System.out.println("xData:"+xData+"="+x);
		System.out.println("yData:"+yData+"="+y);
		System.out.println("widthData:"+widthData+"="+width);
		System.out.println("heightData:"+heightData+"="+height);
		*/
		System.out.println("@Rectangle:"+x+"x"+y+","+width+"x"+height);
		
		addToExitBound(new Rectangle(x,y,width,height));
		
	}
	
	private void writeNavigationToExitData(int mapType,String mapIDData, String xMapData,String yMapData,String xPlayerData,String yPlayerData){
		

		int xMap, yMap;
		int xPlayer, yPlayer;
		int mapID;
		

		for(int i = 0; i < 1; i++){
			if(mapIDData.startsWith("0"))
				mapIDData = mapIDData.substring(1);
			else
				break;
		}
		
		for(int i = 0; i < 3; i++){
			if(xMapData.startsWith("0"))
				xMapData = xMapData.substring(1);
			else
				break;
		}
		
		for(int i = 0; i < 3; i++){
			if(yMapData.startsWith("0"))
				yMapData = yMapData.substring(1);
			else
				break;
		}
		
		for(int i = 0; i < 3; i++){
			if(xPlayerData.startsWith("0"))
				xPlayerData = xPlayerData.substring(1);
			else
				break;
		}
		
		for(int i = 0; i < 3; i++){
			if(yPlayerData.startsWith("0"))
				yPlayerData = yPlayerData.substring(1);
			else
				break;
		}

		
		/*
		System.err.println("===========>mapData:"+mapIDData);
		System.out.println("===========>xMap   :"+xMapData);
		System.out.println("===========>yMap   :"+yMapData);
		System.out.println("===========>xPlayer:"+xPlayerData);
		System.out.println("===========>yPlayer:"+yPlayerData);
		*/
		
		mapID = Integer.parseInt(mapIDData.toString());
		xMap = Integer.parseInt(xMapData.toString());
		yMap = Integer.parseInt(yMapData.toString());
		xPlayer = Integer.parseInt(xPlayerData.toString());
		yPlayer = Integer.parseInt(yPlayerData.toString());
		
		System.out.println("mapData:"+mapIDData+"="+mapID);
		System.out.println("xMap   :"+xMapData+"="+xMap);
		System.out.println("yMap   :"+yMapData+"="+yMap);
		System.out.println("xPlayer:"+xPlayerData+"="+xPlayer);
		System.out.println("yPlayer:"+yPlayerData+"="+yPlayer);
		
		
		addToExitBoundData(mapType, mapID, xMap, yMap, xPlayer, yPlayer);

	}
	
	protected int getXRowSize(){
		return xRowSize;
	}
	
	protected int getYRowSize(){
		return yRowSize;
		
	}
}
