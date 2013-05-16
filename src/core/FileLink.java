package core;

import java.io.File;

public interface FileLink{
	File player1_move = new File("resources/images/mario_sprite_move2_3x.png");
	
	File menu = new File( "resources/images/menu.png");
	File button = new File( "resources/images/interface/button.png");
	
	//interface
	File heart = new File( "resources/images/interface/heart.png");
	File coin = new File( "resources/images/interface/coin.png");
	
	//map images
	File OWMap00_00 = new File ("resources/images/newMap00_00.png");
	File tiles_Dungeon01 = new File ("resources/images/tiles_dungeon01.png");
	
	//map data
	File mapDataID01 = new File ("resources/data/mapDataID01.txt");
		
}