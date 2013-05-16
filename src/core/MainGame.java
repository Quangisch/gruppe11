package core;
import javax.swing.JFrame;



import java.awt.Rectangle;
import java.io.File;
import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class MainGame{

	public static Board board;
	
	public MainGame(){
		
		
	}
	
	public static void main(String[] args){
		//new MainGame();
		GameManager gameManager = new GameManager();
		Thread gameManagerThread = new Thread(gameManager);
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(gameManagerThread, 10,20,MILLISECONDS);
	}
}




interface GridInterface{
	BoardGrid A1 = new BoardGrid(0,0);BoardGrid A2 = new BoardGrid(1,0);BoardGrid A3 = new BoardGrid(2,0);BoardGrid A4 = new BoardGrid(3,0);BoardGrid A5 = new BoardGrid(4,0);BoardGrid A6 = new BoardGrid(5,0);BoardGrid A7 = new BoardGrid(6,0);BoardGrid A8 = new BoardGrid(7,0);BoardGrid A9 = new BoardGrid(8,0);
	BoardGrid B1 = new BoardGrid(0,1);BoardGrid B2 = new BoardGrid(1,1);BoardGrid B3 = new BoardGrid(2,1);BoardGrid B4 = new BoardGrid(3,1);BoardGrid B5 = new BoardGrid(4,1);BoardGrid B6 = new BoardGrid(5,1);BoardGrid B7 = new BoardGrid(6,1);BoardGrid B8 = new BoardGrid(7,1);BoardGrid B9 = new BoardGrid(8,1);
	BoardGrid C1 = new BoardGrid(0,2);BoardGrid C2 = new BoardGrid(1,2);BoardGrid C3 = new BoardGrid(2,2);BoardGrid C4 = new BoardGrid(3,2);BoardGrid C5 = new BoardGrid(4,2);BoardGrid C6 = new BoardGrid(5,2);BoardGrid C7 = new BoardGrid(6,2);BoardGrid C8 = new BoardGrid(7,2);BoardGrid C9 = new BoardGrid(8,2);
	BoardGrid D1 = new BoardGrid(0,3);BoardGrid D2 = new BoardGrid(1,3);BoardGrid D3 = new BoardGrid(2,3);BoardGrid D4 = new BoardGrid(3,3);BoardGrid D5 = new BoardGrid(4,3);BoardGrid D6 = new BoardGrid(5,3);BoardGrid D7 = new BoardGrid(6,3);BoardGrid D8 = new BoardGrid(7,3);BoardGrid D9 = new BoardGrid(8,3);
	BoardGrid E1 = new BoardGrid(0,4);BoardGrid E2 = new BoardGrid(1,4);BoardGrid E3 = new BoardGrid(2,4);BoardGrid E4 = new BoardGrid(3,4);BoardGrid E5 = new BoardGrid(4,4);BoardGrid E6 = new BoardGrid(5,4);BoardGrid E7 = new BoardGrid(6,4);BoardGrid E8 = new BoardGrid(7,4);BoardGrid E9 = new BoardGrid(8,4);
	BoardGrid F1 = new BoardGrid(0,5);BoardGrid F2 = new BoardGrid(1,5);BoardGrid F3 = new BoardGrid(2,5);BoardGrid F4 = new BoardGrid(3,5);BoardGrid F5 = new BoardGrid(4,5);BoardGrid F6 = new BoardGrid(5,5);BoardGrid F7 = new BoardGrid(6,5);BoardGrid F8 = new BoardGrid(7,5);BoardGrid F9 = new BoardGrid(8,5);
	BoardGrid G1 = new BoardGrid(0,6);BoardGrid G2 = new BoardGrid(1,6);BoardGrid G3 = new BoardGrid(2,6);BoardGrid G4 = new BoardGrid(3,6);BoardGrid G5 = new BoardGrid(4,6);BoardGrid G6 = new BoardGrid(5,6);BoardGrid G7 = new BoardGrid(6,6);BoardGrid G8 = new BoardGrid(7,6);BoardGrid G9 = new BoardGrid(8,6);
	
}

interface RectMapGrid extends GridInterface{
	Rectangle rectA1 = new Rectangle(A1.getX(),A1.getY(),15*6,15*6);Rectangle rectA2 = new Rectangle(A2.getX(),A2.getY(),15*6,15*6);Rectangle rectA3 = new Rectangle(A3.getX(),A3.getY(),15*6,15*6);Rectangle rectA4 = new Rectangle(A4.getX(),A5.getY(),15*6,15*6);Rectangle rectA5 = new Rectangle(A5.getX(),A5.getY(),15*6,15*6);Rectangle rectA6 = new Rectangle(A6.getX(),A6.getY(),15*6,15*6);Rectangle rectA7 = new Rectangle(A7.getX(),A7.getY(),15*6,15*6);Rectangle rectA8 = new Rectangle(A8.getX(),A8.getY(),15*6,15*6);Rectangle rectA9 = new Rectangle(A9.getX(),A9.getY(),15*6,15*6);
	Rectangle rectB1 = new Rectangle(B1.getX(),B1.getY(),15*6,15*6);Rectangle rectB2 = new Rectangle(B2.getX(),B2.getY(),15*6,15*6);Rectangle rectB3 = new Rectangle(B3.getX(),B3.getY(),15*6,15*6);Rectangle rectB4 = new Rectangle(B4.getX(),B5.getY(),15*6,15*6);Rectangle rectB5 = new Rectangle(B5.getX(),B5.getY(),15*6,15*6);Rectangle rectB6 = new Rectangle(B6.getX(),B6.getY(),15*6,15*6);Rectangle rectB7 = new Rectangle(B7.getX(),B7.getY(),15*6,15*6);Rectangle rectB8 = new Rectangle(B8.getX(),B8.getY(),15*6,15*6);Rectangle rectB9 = new Rectangle(B9.getX(),B9.getY(),15*6,15*6);
	Rectangle rectC1 = new Rectangle(C1.getX(),C1.getY(),15*6,15*6);Rectangle rectC2 = new Rectangle(C2.getX(),C2.getY(),15*6,15*6);Rectangle rectC3 = new Rectangle(C3.getX(),C3.getY(),15*6,15*6);Rectangle rectC4 = new Rectangle(C4.getX(),C5.getY(),15*6,15*6);Rectangle rectC5 = new Rectangle(C5.getX(),C5.getY(),15*6,15*6);Rectangle rectC6 = new Rectangle(C6.getX(),C6.getY(),15*6,15*6);Rectangle rectC7 = new Rectangle(C7.getX(),C7.getY(),15*6,15*6);Rectangle rectC8 = new Rectangle(C8.getX(),C8.getY(),15*6,15*6);Rectangle rectC9 = new Rectangle(C9.getX(),C9.getY(),15*6,15*6);
	Rectangle rectD1 = new Rectangle(D1.getX(),D1.getY(),15*6,15*6);Rectangle rectD2 = new Rectangle(D2.getX(),D2.getY(),15*6,15*6);Rectangle rectD3 = new Rectangle(D3.getX(),D3.getY(),15*6,15*6);Rectangle rectD4 = new Rectangle(D4.getX(),D5.getY(),15*6,15*6);Rectangle rectD5 = new Rectangle(D5.getX(),D5.getY(),15*6,15*6);Rectangle rectD6 = new Rectangle(D6.getX(),D6.getY(),15*6,15*6);Rectangle rectD7 = new Rectangle(D7.getX(),D7.getY(),15*6,15*6);Rectangle rectD8 = new Rectangle(D8.getX(),D8.getY(),15*6,15*6);Rectangle rectD9 = new Rectangle(D9.getX(),D9.getY(),15*6,15*6);
	Rectangle rectE1 = new Rectangle(E1.getX(),E1.getY(),15*6,15*6);Rectangle rectE2 = new Rectangle(E2.getX(),E2.getY(),15*6,15*6);Rectangle rectE3 = new Rectangle(E3.getX(),E3.getY(),15*6,15*6);Rectangle rectE4 = new Rectangle(E4.getX(),E5.getY(),15*6,15*6);Rectangle rectE5 = new Rectangle(E5.getX(),E5.getY(),15*6,15*6);Rectangle rectE6 = new Rectangle(E6.getX(),E6.getY(),15*6,15*6);Rectangle rectE7 = new Rectangle(E7.getX(),E7.getY(),15*6,15*6);Rectangle rectE8 = new Rectangle(E8.getX(),E8.getY(),15*6,15*6);Rectangle rectE9 = new Rectangle(E9.getX(),E9.getY(),15*6,15*6);
	Rectangle rectF1 = new Rectangle(F1.getX(),F1.getY(),15*6,15*6);Rectangle rectF2 = new Rectangle(F2.getX(),F2.getY(),15*6,15*6);Rectangle rectF3 = new Rectangle(F3.getX(),F3.getY(),15*6,15*6);Rectangle rectF4 = new Rectangle(F4.getX(),F5.getY(),15*6,15*6);Rectangle rectF5 = new Rectangle(F5.getX(),F5.getY(),15*6,15*6);Rectangle rectF6 = new Rectangle(F6.getX(),F6.getY(),15*6,15*6);Rectangle rectF7 = new Rectangle(F7.getX(),F7.getY(),15*6,15*6);Rectangle rectF8 = new Rectangle(F8.getX(),F8.getY(),15*6,15*6);Rectangle rectF9 = new Rectangle(F9.getX(),F9.getY(),15*6,15*6);
	Rectangle rectG1 = new Rectangle(G1.getX(),G1.getY(),15*6,15*6);Rectangle rectG2 = new Rectangle(G2.getX(),G2.getY(),15*6,15*6);Rectangle rectG3 = new Rectangle(G3.getX(),G3.getY(),15*6,15*6);Rectangle rectG4 = new Rectangle(G4.getX(),G5.getY(),15*6,15*6);Rectangle rectG5 = new Rectangle(G5.getX(),G5.getY(),15*6,15*6);Rectangle rectG6 = new Rectangle(G6.getX(),G6.getY(),15*6,15*6);Rectangle rectG7 = new Rectangle(G7.getX(),G7.getY(),15*6,15*6);Rectangle rectG8 = new Rectangle(G8.getX(),G8.getY(),15*6,15*6);Rectangle rectG9 = new Rectangle(G9.getX(),G9.getY(),15*6,15*6);

}