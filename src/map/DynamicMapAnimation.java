package map;

import java.util.Random;

public class DynamicMapAnimation implements Runnable {

	private static DungeonNavigator dungeonNavigator;
	
	int shakeX[][][] = new int[7][9][7]; int shakeY[][][] = new int[7][9][7];
	int autoShakeX[][][] = new int[7][9][7]; int autoShakeY[][][] = new int[7][9][7];
	int randomNum;
	boolean startShake;
	Random rand = new Random();
	
	public DynamicMapAnimation() {
		
	}
	public DynamicMapAnimation(DungeonNavigator dungeonNavigator){
		this.dungeonNavigator = dungeonNavigator;
	}
	
	public void run(){
		resetShake();
		
	}
	
	
	int getShakeX(int z, int x, int y){
		return shakeX[z][x][y];
	}
	
	int getShakeY(int z, int x, int y){
		return shakeY[z][x][y];
	}
	
	
	void startShake(boolean on, int min, int max, int layer, int xTile, int yTile){
		
		randomNum = rand.nextInt(max - min + 1) + min;
		shakeX[layer][xTile][yTile] = randomNum;
		shakeY[layer][xTile][yTile] = randomNum;
		if(on){
			startShake = true;
			autoShakeX[layer][xTile][yTile] = randomNum;
			autoShakeY[layer][xTile][yTile] = randomNum;
		}
	}
	
	void resetShake(){
		for(int yTile = 0; yTile < 7; yTile++){			
			for(int xTile = 0; xTile < 9; xTile++){
				for(int layer = 0; layer < 7; layer++){
					shakeX[layer][xTile][yTile] = shakeY[layer][xTile][yTile] = 0;
				}
			}
		}
	}
	
	
}
