package map;

import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;

abstract class OverWorldObjectManager extends Map{

	private final Rectangle[] navigationBound = {new Rectangle(0,-10,810,20),
												new Rectangle(800,0,20,630),
												new Rectangle(0,620,810,20),
												new Rectangle(-10,0,20,630)};
	
	private ArrayList<Rectangle> toExitBound = new ArrayList<Rectangle>();
	private ArrayList<int[]> toExitBoundData = new ArrayList<int[]>();

	private final Rectangle NULLRECT = new Rectangle(0,0,0,0);
	
	private ArrayList<WallBound<Integer, Rectangle>> wallBoundList = new ArrayList<WallBound<Integer, Rectangle>>(); 
	
	protected OverWorldObjectManager(){
		
	}
	
	protected Rectangle[] getNavigationBound(){
		return navigationBound;	
	}
	
	protected void addWallBound(int pushDirection, Rectangle rectangle){
		
		WallBound<Integer, Rectangle> wallBound = new WallBound<Integer, Rectangle>(pushDirection, rectangle);
		wallBoundList.add(wallBound);
		
		addWallBoundNPaint(rectangle);
	
	}
	
	protected ArrayList<WallBound<Integer, Rectangle>> getWallBoundList(){
		return wallBoundList;
	}
	
	protected void addToExitBound(Rectangle element){
		toExitBound.add(element);
		addNavigationBoundPaint(element);
	
	}
	
	protected ArrayList<Rectangle> getToExitBound(){
		return toExitBound;
	}
	
	protected void addToExitBoundData(int mapType, int mapID, int xMap, int yMap, int xPlayer, int yPlayer){
		
		int[] dataType = new int[6];
		dataType[0] = mapType;
		dataType[1] = mapID;
		dataType[2] = xMap;
		dataType[3] = yMap;
		dataType[4] = xPlayer;
		dataType[5] = yPlayer;
	
		System.err.println("Player@"+dataType[4]+"x"+dataType[5]);
		toExitBoundData.add(dataType);
		
	}
	
	protected ArrayList<int[]> getNavigationToExitData(){
		return toExitBoundData;
	}
	
	
	public class WallBound<P, R>{
		final P pushDirection;
		final R rectangle;
		
		private WallBound(P pushDirection, R rectangle){
			this.pushDirection = pushDirection;
			this.rectangle = rectangle;
		}
		
		public P getPushDirection(){
			return pushDirection;
		}
		
		public R getRectangle(){
			return rectangle;
		}
	}

}
