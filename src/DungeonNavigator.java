import java.awt.Rectangle;


public class DungeonNavigator {
	public static int dungeonX, dungeonY;
	public static boolean dungeon;
	public static int areaID = 1;
	public static int mapID = 0003;
	
	public static Rectangle Dungeon1Over1, Dungeon1Over2;
	public static Rectangle toNorth, toEast, toSouth, toWest;
	
	
	public DungeonNavigator(){
		
	}
	
	public static void navigate(){
		
		//System.out.println("DungeonAreaID:"+areaID);
		//System.out.println("DungeonMapID: "+mapID);
		toNorth = toEast = toSouth = toWest = new Rectangle(0,0,0,0);
		
		switch(DungeonNavigator.areaID){
		
		case 1:
			switch(DungeonNavigator.mapID){
			
				case 3:	setBounds(1, 3);
					if(Player.playerBoundN.intersects(DungeonNavigator.Dungeon1Over1)){
						Camera.cameraX = 0; Camera.cameraY = 0;
						Player.absoluteX = 0; Player.absoluteY = 0;
						Player.x = 110; Player.y = 20;
						System.out.println("leave dungeon1 -> OverWorldMap1"); 
						OverWorldMap.overWorld = true; DungeonNavigator.dungeon = false;
					}
					
					if(Player.playerBoundW.intersects(DungeonNavigator.toEast)){
						System.out.println("go East");
						Player.x = 30;
						System.out.println("DungeonMapID: "+mapID);
						DungeonNavigator.mapID = 103;
						System.out.println("DungeonMapID: "+mapID);
					}
					break;
					
				case 103:	setBounds(1, 103);
					if(Player.playerBoundE.intersects(DungeonNavigator.toWest)){
						Player.x = 700;
						DungeonNavigator.mapID = 3;
					
					}
					
					if(Player.playerBoundS.intersects(DungeonNavigator.toNorth)){
						Player.y = 500;
						DungeonNavigator.mapID = 102;
					}
					break;
					
				case 102: 	setBounds(1, 102);
					if(Player.playerBoundN.intersects(DungeonNavigator.toSouth)){
						Player.y = 20;
						DungeonNavigator.mapID = 103;
					}
					
					if(Player.playerBoundE.intersects(DungeonNavigator.toWest)){
						Player.x = 700;
						DungeonNavigator.mapID = 2;
					}
					
					if(Player.playerBoundS.intersects(DungeonNavigator.toNorth)){
						Player.y = 590;
						DungeonNavigator.mapID = 101;
					}
					break;
					
				case 2:	setBounds(1, 2);
					if(Player.playerBoundW.intersects(DungeonNavigator.toEast)){
						Player.x = 20;
						DungeonNavigator.mapID = 102;
					}
					
					if(Player.playerBoundE.intersects(DungeonNavigator.Dungeon1Over2)){
						System.out.println("leave Dungeon1 -> OverWorldMap 2");
					}
					break;
			}
			
	
			
		}
		
	}
	
	

	
	public static void setBounds(int areaID, int mapID){
		DungeonBuilder.loadNewMap = true;
		//System.out.println("actual mapID:"+mapID);
		switch(areaID){
		
		case 1:
			switch(mapID){
			
			case 3:	Dungeon1Over1 = new Rectangle(220,600,100,20);
						toEast = new Rectangle(780,120,30,100);
						break;
			
			case 103:	toWest = new Rectangle(0,120,30,100);
						toNorth = new Rectangle(580,0,100,30);
						break;
			
			case 102: 	toSouth = new Rectangle(580,600,100,30);
						toWest = new Rectangle(0,400,30,100);
						toNorth = new Rectangle(600,0,100,30);
						break;
						
			case 2:		toEast = new Rectangle(780,400,30,100);
						Dungeon1Over2 = new Rectangle(0,400,30,100);
						break;
			
			}
		
		}
		
		
	}
	
	

}
