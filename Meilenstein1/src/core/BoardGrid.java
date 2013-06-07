package core;


public class BoardGrid implements GridInterface{
	public int x,y;

	public BoardGrid mapGrid, tilesGrid;
	
	public BoardGrid(int x, int y){
		this.x =  15*6 * x;
		this.y = 15*6 * y;
	}

	public int getX() {return x;}
	public int getY() {return y;}
	

}
