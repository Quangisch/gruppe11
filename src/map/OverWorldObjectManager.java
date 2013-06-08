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
	
	private ArrayList<WallBound<Integer, Rectangle[]>> wallBoundList = new ArrayList<WallBound<Integer, Rectangle[]>>(); 
	
	protected OverWorldObjectManager(){
		
	}
	
	protected Rectangle[] getNavigationBound(){
		return navigationBound;	
	}
	
	protected void addWallBound(int orientation, Rectangle[] rectangle){
		
		WallBound<Integer, Rectangle[]> wallBound = new WallBound<Integer, Rectangle[]>(orientation, rectangle);
		wallBoundList.add(wallBound);
		
		for(int i = 0; i < 4; i++)
			addWallBoundPaint(rectangle[i]);
	
	}
	
	protected ArrayList<WallBound<Integer, Rectangle[]>> getWallBound(){
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
		
		System.out.println("yolo?");
		
		System.err.println("Player@"+dataType[4]+"x"+dataType[5]);
		toExitBoundData.add(dataType);
		
		System.out.println("yolo!!!");
	}
	
	protected ArrayList<int[]> getNavigationToExitData(){
		return toExitBoundData;
	}
	
	public class WallBound<O, R>{
		final O orientation;
		final R rectangleArray;
		
		private WallBound(O orientation, R rectangleArray){
			this.orientation = orientation;
			this.rectangleArray = rectangleArray;
		}
		
		public O getOrientation(){
			return orientation;
		}
		
		public R getRectangleArray(){
			return rectangleArray;
		}
	}

}
