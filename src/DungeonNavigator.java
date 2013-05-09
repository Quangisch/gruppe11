import java.awt.Rectangle;


public class DungeonNavigator implements Runnable{
	public static int x, y;
	public static boolean dungeon;
	public static int areaID = 1;
	
	static boolean loadNewMap = true;
	
	public static Rectangle Dungeon1Over1, Dungeon1Over2;
	public static Rectangle toNorth, toEast, toSouth, toWest;
	
	
	public DungeonNavigator(){
		x = 0; y = 3;
	}
	
	public void run(){
		
		if(dungeon)
			navigate();
	}
	
	public static void navigate(){
		
		//System.out.println("DungeonAreaID:"+areaID);
		//if(Board.printMsg)
			System.out.println("Map x:"+x+", y:"+y);
		
		
		
			//map0003
			if(x == 0 && y== 3){
				if(Player.playerBoundN.intersects(DungeonNavigator.Dungeon1Over1)){
					Camera.cameraX = 0; Camera.cameraY = 0;
					Player.absoluteX = 0; Player.absoluteY = 0;
					Player.x = 110; Player.y = 20;
					System.out.println("leave dungeon1 -> OverWorldMap1"); 
					OverWorldMap.overWorld = true; DungeonNavigator.dungeon = false;
					setBounds();
				}
				
				if(Player.playerBoundW.intersects(DungeonNavigator.toEast)){
					x = 1; y = 3;
					Player.x = 30;
					setBounds();
				}
			}
			
			//map0103
			if(x == 1 && y == 3){
				if(Player.playerBoundE.intersects(DungeonNavigator.toWest)){
					x = 0; y = 3;
					Player.x = 700;
					setBounds();
				}
				
				if(Player.playerBoundS.intersects(DungeonNavigator.toNorth)){
					x = 1; y = 2;
					Player.y = 500;
					setBounds();
				}
			}
			
			//map0102
			if(x == 1 && y == 2){
				if(Player.playerBoundN.intersects(DungeonNavigator.toSouth)){
					x = 1; y = 3;
					Player.y = 20;
					setBounds();
				}
				
				if(Player.playerBoundE.intersects(DungeonNavigator.toWest)){
					x = 0; y = 2;
					Player.x = 700;
					setBounds();
				}
				/*
				if(Player.playerBoundS.intersects(DungeonNavigator.toNorth)){
					x = 1; y = 1;
					Player.y = 590;
					setBounds();
				}
				*/
				
			}
				
			//map 0002
			if(x == 0 && y == 2){
				if(Player.playerBoundW.intersects(DungeonNavigator.toEast)){
					Player.x = 20;
					x = 1; y = 2;
					setBounds();
				}
				
				if(Player.playerBoundE.intersects(DungeonNavigator.Dungeon1Over2)){
					setBounds();
					System.out.println("leave Dungeon1 -> OverWorldMap 2");
				}
			}
		
	}
	
	

	
	public static void setBounds(){
		
		//reset Bounds
		toNorth = toEast = toSouth = toWest = new Rectangle(0,0,0,0);
		
		if(areaID == 1){
			if(x == 0 && y == 3){
				Dungeon1Over1 = new Rectangle(220,600,100,20);
				toEast = new Rectangle(780,120,30,100);
			}
			if(x == 1 && y == 3){
				toWest = new Rectangle(0,120,30,100);
				toNorth = new Rectangle(580,0,100,30);
			}
			
			if(x == 1 && y == 2){
				toSouth = new Rectangle(580,600,100,30);
				toWest = new Rectangle(0,400,30,100);
				toNorth = new Rectangle(600,0,100,30);
			}
			
			if(x == 0 && y == 2){
				toEast = new Rectangle(780,400,30,100);
				Dungeon1Over2 = new Rectangle(0,400,30,100);
			}
			
		}
		
	}
}
