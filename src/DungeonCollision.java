import java.awt.Rectangle;


public class DungeonCollision {
	static Rectangle[][] wall1;
	static Rectangle[][] wall2;
	
	public DungeonCollision(){
		
	}
	
	public static void start(){
		readWalls();
		checkCollision();
	}
	public static void readWalls(){
		
		//wall1[0][0] = new Rectangle(0*90,0*90,45,90);
		
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				//yWall A
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 1){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1): 	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wall2[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								//System.out.println("A1 Rectangle");
								break;
					case(2):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(3):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(4):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(5):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(6):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(7):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(8):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(9):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wall2[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								break;
					}
				}//yWall A
				
				//yWall B
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 2){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(2):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(3):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(4):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;	
					case(5):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;	
					case(6):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;	
					case(7):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;	
					case(8):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;	
					case(9):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					}
				}//yWall B
				
				//yWall C
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 3){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(2):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;	
					case(3):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wall2[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								break;	
					case(4):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wall2[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								break;	
					case(5):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(6):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(7):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(8):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(9):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					}
						
				}//ywall C
				
				//ywall D
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 4){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(2):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(3):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wall2[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					case(4):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								wall2[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					case(5):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wall2[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								break;
					case(6):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(7):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(8):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					case(9):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								wall2[xTile][yTile] = wall1[xTile][yTile];
								break;
					}
				}//ywall D
				
			}
						
		}
	}
				//System.out.println("check: " +DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]);
				
	
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
