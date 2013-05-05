
public class BoardGrid implements GridInterface{
	public int x,y;
	//MapMaker mapMaker;
	public BoardGrid mapGrid, tilesGrid;
	
	public BoardGrid(int x, int y){
		this.x =  15*6 * x;
		this.y = 15*6 * y;
	}
	public BoardGrid(String tmp){
		this.x = getCompGrid(tmp).getX();
		this.y = getCompGrid(tmp).getY();
	}
	
	public int getX() {return x;}
	
	public int getY() {return y;}
	

	public BoardGrid getCompGrid(String name){
		if (name.equals("A1")) {this.x = A1.x;this.y = A1.y;} if (name.equals("A2")) {this.x = A2.x;this.y = A2.y;} if (name.equals("A3")) {this.x=A3.x;this.y=A3.y;} if (name.equals("A4")) {this.x=A4.x;this.y=A4.y;} if (name.equals("A5")) {this.x=A5.x;this.y=A5.y;} if (name.equals("A6")) {this.x=A6.x;this.y=A6.y;} if (name.equals("A7")) {this.x=A7.x;this.y=A7.y;} if (name.equals("A8")) {this.x=A8.x;this.y=A8.y;} if (name.equals("A9")) {this.x=A9.x;this.y=A9.y;}
		if (name.equals("B1")) {this.x = B1.x;this.y = B1.y;} if (name.equals("B2")) {this.x = B2.x;this.y = B2.y;} if (name.equals("B3")) {this.x=B3.x;this.y=B3.y;} if (name.equals("B4")) {this.x=B4.x;this.y=B4.y;} if (name.equals("B5")) {this.x=B5.x;this.y=B5.y;} if (name.equals("B6")) {this.x=B6.x;this.y=B6.y;} if (name.equals("B7")) {this.x=B7.x;this.y=B7.y;} if (name.equals("B8")) {this.x=B8.x;this.y=B8.y;} if (name.equals("B9")) {this.x=B9.x;this.y=B9.y;}
		if (name.equals("C1")) {this.x = C1.x;this.y = C1.y;} if (name.equals("C2")) {this.x = C2.x;this.y = C2.y;} if (name.equals("C3")) {this.x=C3.x;this.y=C3.y;} if (name.equals("C4")) {this.x=C4.x;this.y=C4.y;} if (name.equals("C5")) {this.x=C5.x;this.y=C5.y;} if (name.equals("C6")) {this.x=C6.x;this.y=C6.y;} if (name.equals("C7")) {this.x=C7.x;this.y=C7.y;} if (name.equals("C8")) {this.x=C8.x;this.y=C8.y;} if (name.equals("C9")) {this.x=C9.x;this.y=C9.y;}
		if (name.equals("D1")) {this.x = D1.x;this.y = D1.y;} if (name.equals("D2")) {this.x = D2.x;this.y = D2.y;} if (name.equals("D3")) {this.x=D3.x;this.y=D3.y;} if (name.equals("D4")) {this.x=D4.x;this.y=D4.y;} if (name.equals("D5")) {this.x=D5.x;this.y=D5.y;} if (name.equals("D6")) {this.x=D6.x;this.y=D6.y;} if (name.equals("D7")) {this.x=D7.x;this.y=D7.y;} if (name.equals("D8")) {this.x=D8.x;this.y=D8.y;} if (name.equals("D9")) {this.x=D9.x;this.y=D9.y;}
		if (name.equals("E1")) {this.x = E1.x;this.y = E1.y;} if (name.equals("E2")) {this.x = E2.x;this.y = E2.y;} if (name.equals("E3")) {this.x=E3.x;this.y=E3.y;} if (name.equals("E4")) {this.x=E4.x;this.y=E4.y;} if (name.equals("E5")) {this.x=E5.x;this.y=E5.y;} if (name.equals("E6")) {this.x=E6.x;this.y=E6.y;} if (name.equals("E7")) {this.x=E7.x;this.y=E7.y;} if (name.equals("E8")) {this.x=E8.x;this.y=E8.y;} if (name.equals("E9")) {this.x=E9.x;this.y=E9.y;}
		if (name.equals("F1")) {this.x = F1.x;this.y = F1.y;} if (name.equals("F2")) {this.x = F2.x;this.y = F2.y;} if (name.equals("F3")) {this.x=F3.x;this.y=F3.y;} if (name.equals("F4")) {this.x=F4.x;this.y=F4.y;} if (name.equals("F5")) {this.x=F5.x;this.y=F5.y;} if (name.equals("F6")) {this.x=F6.x;this.y=F6.y;} if (name.equals("F7")) {this.x=F7.x;this.y=F7.y;} if (name.equals("F8")) {this.x=F8.x;this.y=F8.y;} if (name.equals("F9")) {this.x=F9.x;this.y=F9.y;}
		if (name.equals("G1")) {this.x = G1.x;this.y = G1.y;} if (name.equals("G2")) {this.x = G2.x;this.y = G2.y;} if (name.equals("G3")) {this.x=G3.x;this.y=G3.y;} if (name.equals("G4")) {this.x=G4.x;this.y=G4.y;} if (name.equals("G5")) {this.x=G5.x;this.y=G5.y;} if (name.equals("G6")) {this.x=G6.x;this.y=G6.y;} if (name.equals("G7")) {this.x=G7.x;this.y=G7.y;} if (name.equals("G8")) {this.x=G8.x;this.y=G8.y;} if (name.equals("G9")) {this.x=G9.x;this.y=G9.y;}
		
		return this;
	}

}

