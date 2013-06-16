package core;

import java.io.IOException;

import core.Board;
import core.Menu;
import core.FileLink;

import javax.sound.*;
import sun.audio.*;

public class Sound implements FileLink{

	
	public static void getMenuSound(){
		
		AudioPlayer AP = AudioPlayer.player;
		AudioStream AS;
		AudioData AD;
		ContinuousAudioDataStream CA =null;
		
		try{
			AS = new AudioStream(new FileInputStream("menuSound"));
			AD = AS.getData();
			CA = new ContinuousAudioDataStream(AD);
		}catch(IOException e){
			System.out.print("Soundfehler");
		}
		//if(menu = true){
		AP.start(CA);
		//}
	}
	public static void getIngameSound(){
		
		AudioPlayer AP2 = AudioPlayer.player;
		AudioStream AS2;
		AudioData AD2;
		ContinuousAudioDataStream CA2 =null;
		
		try{
			AS2 = new AudioStream(new FileInputStream("ingameSound"));
			AD2 = AS2.getData();
			CA2 = new ContinuousAudioDataStream(AD2);
		}catch(IOException e){
			System.out.print("Soundfehler");
		}
		//if(menu = true){
		AP2.start(CA2);
		//}
	}
	
}
