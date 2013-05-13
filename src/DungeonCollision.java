import java.awt.Rectangle;


public class DungeonCollision {
	static Rectangle[][] wall1;
	static Rectangle[][] wall2;
	
	public DungeonCollision(){
		
	}
	
	public static void start(){
		readWalls();
		//checkCollision();
	}
	public static void readWalls(){
		
		//wall1[0][0] = new Rectangle(0*90,0*90,45,90);
		
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				System.out.println("check: " +DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]);
				
				switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
				case(11): 	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
							wall2[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
							System.out.println("A1 Rectangle");
							break;
				case(12):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				}
			}
		}
		
	}
	
	public static void checkCollision(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile <9; xTile++){
				if(Player.playerBoundN.intersects(wall1[xTile][yTile])){
					Player.moveable = false;
				}
			}
		}
		
	}
}
