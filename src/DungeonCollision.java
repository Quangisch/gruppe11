import java.awt.Rectangle;


public class DungeonCollision {
	//static Rectangle[][] wall1;
	//static Rectangle[][] wall2;
	static Rectangle wallN[][] = new Rectangle[9][7];static Rectangle wallE[][] = new Rectangle[9][7];static Rectangle wallS[][] = new Rectangle[9][7];static Rectangle wallW[][] = new Rectangle[9][7];
	static Rectangle wallNE[][] = new Rectangle[9][7];static Rectangle wallSE[][] = new Rectangle[9][7];static Rectangle wallSW[][] = new Rectangle[9][7];static Rectangle wallNW[][] = new Rectangle[9][7];
	
	public DungeonCollision(){
		
	}
	
	public static void start(){
		if(Board.printMsg)
			System.out.println("DungeonCollision.start");
		if(DungeonNavigator.loadNewMap);
			readWalls();
		checkCollision();
	}
	public static void readWalls(){
		
		
		
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile < 9; xTile++){
				
				wallN[xTile][yTile] = wallE[xTile][yTile] = wallS[xTile][yTile] = wallW[xTile][yTile] = new Rectangle(0,0,0,0);
				wallNE[xTile][yTile] = wallSE[xTile][yTile] = wallSW[xTile][yTile] = wallNW[xTile][yTile] = new Rectangle(0,0,0,0);
		
				
				
				//yWall A
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 1){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1): 	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								break;
					case(2):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								break;
					case(3):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								break;
					case(4):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								break;
					case(5):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								break;
					case(7):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wallW[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(8):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallE[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(9):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								break;
					}
				}//yWall A
				
				
				//yWall B
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 2){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wallS[xTile][yTile] = wallE[xTile][yTile];
								break;
					case(3):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;	
					case(5):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wallW[xTile][yTile] = wallS[xTile][yTile];
								break;	
					case(6):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wallS[xTile][yTile] = wallE[xTile][yTile];
								break;	
					case(7):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;	
					case(8):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wallW[xTile][yTile] = wallS[xTile][yTile];
								break;	
					case(9):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallE[xTile][yTile] = wallN[xTile][yTile];
								break;
					}
				}//yWall B
				
				//yWall C
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 3){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								break;	
					case(3):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								break;	
					case(4):	wallN[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallW[xTile][yTile] = wallN[xTile][yTile];
								break;	
					case(5):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wallE[xTile][yTile] = wallS[xTile][yTile];
								break;
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wallW[xTile][yTile] = wallS[xTile][yTile];
								break;
					case(7):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallN[xTile][yTile] = wallE[xTile][yTile];
								break;
					case(8):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								break;
					case(9):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wallE[xTile][yTile] = wallS[xTile][yTile];
								break;
					}
						
				}//ywall C
				
				//ywall D
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 4){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallN[xTile][yTile] = wallE[xTile][yTile];
								break;
					case(3):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					case(4):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					case(5):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallN[xTile][yTile] = wallE[xTile][yTile];
								break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wallW[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(7):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wallE[xTile][yTile] = wallS[xTile][yTile];
								break;
					case(8):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wallW[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								break;
					}
				}//ywall D
	
				//ywall E
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 5){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wallN[xTile][yTile] = wallW[xTile][yTile];
								break;
					case(2):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wallE[xTile][yTile] = wallS[xTile][yTile];
								break;
					case(3):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								break;
					case(4):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								break;
					case(5):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								break;
					case(7):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								wallW[xTile][yTile] = wallS[xTile][yTile];
								break;
					case(8):	wallN[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wallW[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								break;
					}
				}//ywall E
				
				//yWall F
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 6){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wallS[xTile][yTile] = wallW[xTile][yTile];
								break;
					case(2):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
								wallN[xTile][yTile] = wallE[xTile][yTile];
								break;	
					case(3):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								break;	
					case(4):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								break;	
					case(5):	wallS[xTile][yTile] = new Rectangle(xTile*90,yTile*90,0,0);
								break;
					case(6):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wallE[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(7):	wallN[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
								wallE[xTile][yTile] = wallN[xTile][yTile];
								break;
					case(8):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,45);
								wallN[xTile][yTile] = wallW[xTile][yTile];
								break;
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								break;
					}
						
				}//ywall F
				
				//yWall G
				if(DungeonBuilder.yWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile] == 7){
					switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
					case(1):	wallW[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
								wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					case(2):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					case(3):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
								wallW[xTile][yTile] = wallS[xTile][yTile];
								break;
					case(4):	wallS[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
								wallE[xTile][yTile] = wallS[xTile][yTile];
								break;	
					case(5):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;	
					case(6):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;	
					case(7):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;	
					case(8):	wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;	
					case(9):	wallE[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
								wallS[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
								break;
					}
				}//yWall G
			}
						
		}
		
	}
				//System.out.println("check: " +DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]);
				
	
	public static void checkCollision(){
		for(int yTile = 0; yTile < 7; yTile++){
			for(int xTile = 0; xTile <9; xTile++){
				
				if(Player.playerBoundN.intersects(wallN[xTile][yTile])){
					Player.y += 1;
					System.out.println("Wall North!");
				}
				if(Player.playerBoundE.intersects(wallE[xTile][yTile])){
					Player.x -= 1;
					System.out.println("Wall East!");
				}
				if(Player.playerBoundS.intersects(wallS[xTile][yTile])){
					Player.y -= 1;
					System.out.println("Wall South!");
				}
				if(Player.playerBoundW.intersects(wallW[xTile][yTile])){
					Player.x += 1;
					System.out.println("Wall West!");
				}
				
				if(Player.playerBoundN.intersects(wallN[xTile][yTile] ) && Player.playerBoundE.intersects(wallE[xTile][yTile])){
					Player.y += 1;
					Player.x -= 1;
					System.out.println("Wall North-east!");
				}
				if(Player.playerBoundN.intersects(wallN[xTile][yTile] ) && Player.playerBoundW.intersects(wallW[xTile][yTile])){
					Player.y += 1;
					Player.x += 1;
					System.out.println("WallNorth-west!");
				}
				if(Player.playerBoundS.intersects(wallS[xTile][yTile]) && Player.playerBoundE.intersects(wallE[xTile][yTile])){
					Player.y -= 1;
					Player.x -= 1;
					System.out.println("Wall South-east!");
				}
				if(Player.playerBoundS.intersects(wallS[xTile][yTile]) && Player.playerBoundW.intersects(wallW[xTile][yTile])){
					Player.y -= 1;
					Player.x += 1;
					System.out.println("Wall South-west!");
				}
				
			}
		}
		
	}
}
