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
		gameManagerThread.start();
		//ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		//scheduler.scheduleWithFixedDelay(gameManagerThread, 10,20,MILLISECONDS);
	}
}