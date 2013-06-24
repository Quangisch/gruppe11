package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import map.Camera;
import map.OverWorldNavigator;

import objects.Guide;
import objects.MarioDark;
import objects.Player;


public class SaveGameManager implements FileLink{
	
	
	private SaveGameManager(){
		
	}
	
	public static void saveNow(int fileNumber){
		GameManager.getInstance().saveGameLock = true;
		
		try
	      {
			if(fileNumber < saveFile.length){
				
				if(!(saveFile[fileNumber].exists() || saveFile[fileNumber].canWrite()))
					saveFile[fileNumber].createNewFile();
				
				FileOutputStream fileOut = new FileOutputStream(saveFile[fileNumber]);
		        ObjectOutputStream out = new ObjectOutputStream(fileOut);
		        
		        //adds Objects to List for serialization
		        List<Object> saveList = new ArrayList<Object>();
		        
		        saveList.add(Player.getInstance());
		        saveList.add(Camera.getInstance());
		        saveList.add(OverWorldNavigator.getInstance());
		        saveList.add(GameObjectManager.getInstance());
		        
		        saveList.add(Guide.getInstance());
		        saveList.add(MarioDark.getAllInstances());
	
		        out.writeObject(saveList); 
		        out.close();
		        fileOut.close();
		        
		        System.out.println("=======Saved Sucessfully=======");
			} else 
				System.err.println("SaveGameManager.Error: invalid fileNumber@saveNow");
	         
	         
	      }catch(IOException e){
	          System.err.println("SaveGameManager.Error: saveNow - "+e);
	      }
		
		GameManager.getInstance().saveGameLock = false;
		
	}
	
	public static void loadNow(final int fileNumber){
		
		
		
		new Thread(){
			@SuppressWarnings("unchecked")
			public void run(){
				try
			      {
					GameManager.getInstance().mapLoaded = false;
					MainGame.stopThreads();
					resetInstances();
					
					if(fileNumber < saveFile.length && saveFile[fileNumber].exists()){
						FileInputStream fileIn = new FileInputStream(saveFile[fileNumber]);
				        ObjectInputStream in = new ObjectInputStream(fileIn);
				        
				        //loadList
				        List<Object> loadList = new ArrayList<Object>();
				        loadList = (ArrayList<Object>)in.readObject();
				        
				        in.close();
				        fileIn.close();
				        
				        Player.setInstance((Player)loadList.get(0));
				        Camera.setInstance((Camera)loadList.get(1));
				        OverWorldNavigator.setInstance((OverWorldNavigator)loadList.get(2)); 
				        GameObjectManager.setInstance((GameObjectManager)loadList.get(3));
				        
				        Guide.setInstance((Guide)loadList.get(4));
				        MarioDark.setInstance((MarioDark[])loadList.get(5));
				       
				        
				        
				        
				        System.out.println("=======Load Sucessfully=======");
					} else
						System.err.println("SaveGameManager.Error: invalid fileNumber@loadNow");
			         
			         
			      }catch(IOException ioe){
			    	  System.err.println("SaveGameManager.Error: loadNow - "+ioe);
			    	  
			      }catch(ClassNotFoundException cnfe){
			    	  System.err.println("SaveGameManager.Error: loadNow - "+cnfe);
			      }
				
				reinitializeInstances();
				MainGame.startThreads();
				
			}
		}.start();

	}
	
	private static void resetInstances(){
		Player.resetInstance();
		Guide.resetInstance();
		MarioDark.deleteAllInstances();
	}
	
	private static void reinitializeInstances(){
		Player.getInstance().initializeImage(player1Sprite, 90, 120, 8);
		OverWorldNavigator.getInstance().initializeMap(true, 0, 0, 0, 0, 0);
	    GameManager.getInstance().addGameObject(Player.getInstance());
	    
	    Guide.getInstance().initializeThread();
	    MarioDark.reinitializeAllInstances();
	}

}
