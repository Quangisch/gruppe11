package map;

import java.awt.Rectangle;
import java.util.ArrayList;

abstract class OverWorldObjectManager extends Map{

	private final Rectangle[] navigationBound = {new Rectangle(0,-10,810,20),
												new Rectangle(800,0,20,630),
												new Rectangle(0,620,810,20),
												new Rectangle(-10,0,20,630)};
	private ArrayList<Rectangle> toExitBound = new ArrayList<Rectangle>();
	private ArrayList<int[]> toExitBoundData = new ArrayList<int[]>();
	private ArrayList<WallBound<Integer, Rectangle>> wallBoundList = new ArrayList<WallBound<Integer, Rectangle>>();
	private ArrayList<MapObjectBound<Integer, Rectangle>> mapObjectBoundList = new ArrayList<MapObjectBound<Integer, Rectangle>>();
	
	private ArrayList<EnemyData<Integer, int[], int[]>> enemyData = new ArrayList<EnemyData<Integer, int[], int[]>>();
	
	
	protected OverWorldObjectManager(){
		
	}
	
	protected Rectangle[] getNavigationBound(){
		return navigationBound;	
	}
	
	protected void addWallBound(int pushDirection, Rectangle rectangle){
		
		WallBound<Integer, Rectangle> wallBound = new WallBound<Integer, Rectangle>(pushDirection, rectangle);
		wallBoundList.add(wallBound);
		
		if(pushDirection == 1)
			addWallBoundNPaint(rectangle);
		else if(pushDirection == 5)
			addWallBoundSPaint(rectangle);
		else
			addWallBoundPaint(rectangle);
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
	
	protected void clearBoundArrayList(){
		wallBoundList.clear();
		toExitBound.clear();
		toExitBoundData.clear();
		mapObjectBoundList.clear();
	}
	
	protected ArrayList<int[]> getNavigationToExitData(){
		return toExitBoundData;
	}
	
	protected void addMapObjectBound(int type, Rectangle rectangle){
		mapObjectBoundList.add(new MapObjectBound<Integer, Rectangle>(type, rectangle));
		addMapObjectBoundPaint(rectangle);
	}
	
	protected ArrayList<MapObjectBound<Integer, Rectangle>> getMapObjectBoundList(){
		return mapObjectBoundList;
	}
	
	protected void addEnemyData(int type, int[] position, int[] attributes){
		
		Integer typeInteger = (Integer) type;
		EnemyData<Integer, int[], int[]> element = new EnemyData<Integer, int[], int[]>(typeInteger, position, attributes);
		
		
		enemyData.add(element);
		
		int[] pos = enemyData.get(0).getPosition();
		System.out.println("EnemyData@Map_"+pos[0]+"x"+pos[1]+", Pos_"+pos[2]+"x"+pos[3]);
		
	}
	
	protected ArrayList<EnemyData<Integer, int[], int[]>> getEnemyDataMap(){
		
		ArrayList<EnemyData<Integer, int[], int[]>> enemyDataMap = new ArrayList<EnemyData<Integer, int[], int[]>>();
		
		for(int index = 0; index < enemyData.size(); index++){
	
			enemyDataMap.add(enemyData.get(index));
			
		}
		
		return enemyDataMap;
		
	}
	
	public void resetObjectManager(){
		toExitBound = new ArrayList<Rectangle>();
		toExitBoundData = new ArrayList<int[]>();
		wallBoundList = new ArrayList<WallBound<Integer, Rectangle>>();
		mapObjectBoundList = new ArrayList<MapObjectBound<Integer, Rectangle>>();
		
		enemyData = new ArrayList<EnemyData<Integer, int[], int[]>>();
		
	}
	
	
	
	public class WallBound<P, R>{
		final P pushDirection;
		final R rectangle;
		
		private WallBound(P pushDirection, R rectangle){
			this.pushDirection = pushDirection;
			this.rectangle = rectangle;
		}
		
		public P getPushDirection(){return pushDirection;}
		public R getRectangle(){return rectangle;}
	}
	
	public class MapObjectBound<T, R>{
		final T type;
		final R rectangle;
		
		private MapObjectBound(T type, R rectangle){
			this.type = type;
			this.rectangle = rectangle;
		}
		
		public T getType(){return type;}
		public R getRectangle(){return rectangle;}
	}
	
	protected class EnemyData<T, P, A>{
		public final T type;
		public final P position;
		public final A attributes;
		
		public EnemyData(T type, P position, A attributes){
			this.type = type;
			this.position = position;
			this.attributes = attributes;
			
			System.err.println("====>New EnemyData=====>");
		}
		
		public T getType(){
			return type;
		}
		
		public P getPosition(){
			return position;
		}
		
		public A getAttributes(){
			return attributes;
		}
		
	}

}
