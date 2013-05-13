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
				System.out.println("check: " +DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]);
				
				switch(DungeonBuilder.xWall1[DungeonNavigator.x][DungeonNavigator.y][xTile][yTile]){
				case(11): 	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
							wall2[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
							System.out.println("A1 Rectangle");
							break;
				case(12):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,50);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(13):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(14):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,50);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(15):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(16):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,50);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(17):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,40);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(18):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(19):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,45);
							wall2[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
							break;
				case(21):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(22):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(23):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+25,90,65);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(24):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+30,90,60);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;	
				case(25):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,50,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;	
				case(26):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;	
				case(27):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,90,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;	
				case(28):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,40,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;	
				case(29):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,30);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(31):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,50,90);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(32):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,90);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;	
				case(33):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,90,30);
							wall2[xTile][yTile] = new Rectangle(xTile*90,yTile*90,30,90);
							break;	
				case(34):	wall1[xTile][yTile] = new Rectangle((xTile*90)+25,yTile*90,65,45);
							wall2[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,70);
							break;	
				case(35):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(36):	wall1[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+45,45,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(37):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,30);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(38):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,90);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(39):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+45,45,45);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(41):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,50,90);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(42):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,40);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(43):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,30,90);
							wall2[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+60,90,30);
							break;
				case(44):	wall1[xTile][yTile] = new Rectangle((xTile*90)+65,yTile*90,25,90);
							wall2[xTile][yTile] = new Rectangle(xTile*90,(yTile*90)+60,90,30);
							break;
				case(45):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,yTile*90,45,25);
							wall2[xTile][yTile] = new Rectangle((xTile*90)+65,yTile*90,25,45);
							break;
				case(46):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,40);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(47):	wall1[xTile][yTile] = new Rectangle((xTile*90)+45,(yTile*90)+60,45,30);
							wall2[xTile][yTile] = wall1[xTile][yTile];
							break;
				case(48):	wall1[xTile][yTile] = new Rectangle(xTile*90,yTile*90,45,35);
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
