package map;

import game.objects.MarioDark;
import game.objects.Moveable;
import game.objects.Player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.Board;
import core.FileLink;
import core.GameManager;

public class OverWorldNavigator extends OverWorldCollision implements FileLink{
	
	private static OverWorldNavigator overWorldMap;
	
	
	private OverWorldNavigator(){
		System.err.println("construct OverWorldMap");
		setOverWorld(true);
	}
	
	public void initializeMap(int x, int y, int ID, int playerX, int playerY){
		
		GameManager.mapLoaded = false;
		MarioDark.deleteAllInstances();
		Board.getInstance().setTopMap(false, null);
		
		switch(ID){
		case(0):	loadMap(overWorldMapID00,overWorldDataID00);
					Board.getInstance().setTopMap(true, overWorldTOPID00);
					setWidthMap(2700);
					setHeightMap(2700);
					Player.getInstance().setLocks(true);
					GameManager.cameraOn = true;
					break;
					
		case(1):	loadMap(overWorldMapID01,overWorldDataID01);
					Board.getInstance().setTopMap(true, overWorldTOPID01);
					setWidthMap(1620);
					setHeightMap(1260);
					Player.getInstance().setLocks(true);
					GameManager.cameraOn = true;
					break;
		
					
		}
		
		setXCoordinate(-x);
		setYCoordinate(-y);
		Camera.getInstance().setX(x);
		Camera.getInstance().setY(y);
		Player.getInstance().setX(playerX);
		Player.getInstance().setY(playerY);
		Player.getInstance().setOldPosition();
		

		setEnemy();
		
		System.out.println("xy@"+x+"x"+y);
		System.out.println("Player@"+playerX+"x"+playerY);
		
		initializeBounds();
		
		GameManager.mapLoaded = true;
	}
	

	public synchronized void navigate(){
		
		Camera.getInstance().setX(-getXCoordinate());
		Camera.getInstance().setY(-getYCoordinate());
		
		int x = getXCoordinate();
		int y = getYCoordinate();
		int width = getWidthMap();
		int height = getHeightMap();
		
		Rectangle[] mapNavigationBound = getNavigationBound();
		
		
			
			if(Player.getInstance().getBoundDirection(0).intersects(mapNavigationBound[0]) || GameManager.scrollDirection == 1){
				if(y < 0)
					GameManager.scrollDirection = 1;
				else{
					GameManager.scrollDirection = 0;
					Player.getInstance().setY(Player.getInstance().getY() + 10);
				}	
			}
			
			
			
			if(Player.getInstance().getBoundDirection(0).intersects(mapNavigationBound[1]) ||GameManager.scrollDirection == 3){
				if(x > -(width-810))
					GameManager.scrollDirection = 3;
				else{
					GameManager.scrollDirection = 0;
					Player.getInstance().setX(Player.getInstance().getX() - 10);
				}	
			}
			
			
			if(Player.getInstance().getBoundDirection(0).intersects(mapNavigationBound[2]) || GameManager.scrollDirection == 5){
				if(y > -(height-630))
					GameManager.scrollDirection = 5;
				else{
					GameManager.scrollDirection = 0;
					Player.getInstance().setY(Player.getInstance().getY() - 10);
				}			
			}
			
			
			if(Player.getInstance().getBoundDirection(0).intersects(mapNavigationBound[3]) || GameManager.scrollDirection == 7){
				if(x < 0)
					GameManager.scrollDirection = 7;
				else{
					GameManager.scrollDirection = 0;
					Player.getInstance().setX(Player.getInstance().getX() + 10);
				}	
			}

		
		
		if(GameManager.scrollDirection != 0){
			Player.getInstance().setInputLock(true);
			Player.getInstance().slowDownHalf();
			Camera.getInstance().scrollCamera(this);
		}
		
		
		ArrayList<Rectangle> toExitRect = getToExitBound();
		ArrayList<int[]> toExitData = getNavigationToExitData();
		
		for(int index = 0; index < toExitRect.size(); index++){
			
			Camera cam = Camera.getInstance();
			Rectangle rect = toExitRect.get(index);
			//System.out.println("toEXIT==========>@"+toExitRect.get(index).x+"x"+toExitRect.get(index).y+", "+toExitRect.get(index).width+"x"+toExitRect.get(index).height);
			//System.out.println("cam@"+cam.getX()+"x"+cam.getY());
			
			if((Player.getInstance().getBoundDirection(0).intersects(rect.x-cam.getX(), rect.y-cam.getY(),rect.width,rect.height))){
				System.err.println("intersectExit");
				
				
				int[] data = toExitData.get(index);
				
				int mapType = data[0];
				int mapID = data[1];
				int xMap = data[2];
				int yMap = data[3];
				int xPlayer = data[4];
				int yPlayer = data[5];
				
				System.err.println("Player@DungeonLoadMap:"+xPlayer+"x"+yPlayer);
				
				if(mapType == 0){
					GameManager.dungeon = true;
					GameManager.overWorld = false;
					GameManager.mapLoaded = false;
					DungeonNavigator.getInstance().initializeMap(xMap, yMap, mapID, xPlayer, yPlayer);
					System.out.println("dungeonMap_");
				}
					
				if(mapType == 1){
					GameManager.dungeon = false;
					GameManager.overWorld = true;
					GameManager.mapLoaded = false;
					this.initializeMap(xMap-810, yMap-630, mapID, xPlayer, yPlayer);
					//OverWorldNavigator.getInstance().initializeMap(xMap-810, yMap-630, mapID, 400, 300);
					System.out.println("overworldMap_"+xMap+"x"+yMap);
				}
			}
			
		}
		
		
			
	}
	
	
	public static OverWorldNavigator getInstance(){
		if(overWorldMap == null)
			overWorldMap = new OverWorldNavigator();
		return overWorldMap;
	}

}
