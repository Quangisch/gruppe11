package core;

import java.io.File;

public interface FileLink{
	File player1Sprite = new File("resources/images/mario_sprite_move2_3x.png");
	File enemyDark = new File("resources/images/marioDark_sprite_move2_3x.png");
	File enemyBright = new File("resources/images/marioBright_sprite_move2_3x.png");
	File neutralNPC = new File("resources/images/marioNeutral_sprite_move2_3x.png");
	
	//mainMenu
	File menuFile = new File("resources/images/menu/menu.png");
	File buttonFile = new File("resources/images/menu/button.png");
	File igmBorderFile = new File("resources/images/menu/ingameMenu.png");
	File winScreenFile = new File("resources/images/menu/winScreen.png");
	File loseScreenFile = new File("resources/images/menu/loseScreen.png");
	
	
	File magicID00 = new File("resources/images/magic/magicID00.png");
	File magicID01 = new File("resources/images/magic/magicID01.png");
	
	//item
	File itemListID00 = new File("resources/images/item/itemListID00.png");
	
	
	//interface
	File itemBar = new File("resources/images/interface/itemInterface.png");
	File manaPool = new File("resources/images/interface/manaPool.png");
	File manaBar = new File("resources/images/interface/manaBar.png");
	
	File coin = new File("resources/images/interface/coin.png");
	File text = new File("resources/images/interface/text.png");
	File heart4_4 = new File("resources/images/interface/heart4.png");
	File heart3_4 = new File("resources/images/interface/heart3.png");
	File heart2_4 = new File("resources/images/interface/heart2.png");
	File heart1_4 = new File("resources/images/interface/heart1.png");
	
	//mapImages
	File overWorldMapID00 = new File ("resources/images/map/overWorldID00.png");
	File overWorldTOPID00 = new File ("resources/images/map/overWorldID00TOP.png");
	File overWorldMapID01 = new File ("resources/images/map/overWorldID01.png");
	File overWorldTOPID01 = new File ("resources/images/map/overWorldID01TOP.png");
	File tiles_Dungeon00 = new File ("resources/images/map/tiles_dungeon00.png");
	
	//mapObjects
	File mapObjects00 = new File ("resources/images/mapObjects00.png");
	
	//mapData
	File dungeonDataID00 = new File ("resources/data/dungeonDataID00.txt");
	File overWorldDataID00 = new File ("resources/data/overWorldDataID00.txt");
	File overWorldDataID01 = new File ("resources/data/overWorldDataID01.txt");
	
	//enemySprite
	File goomba = new File("resources/images/goomba_sprite_move.png");
	

	//music/sounds
	File MFboss = new File("resources/sounds/music/boss.mp3");
	File MFdungeon = new File("resources/sounds/music/dungeon.mp3");
	File MFloseState = new File("resources/sounds/music/loseState.mp3");
	File MFmainMenu = new File("resources/sounds/music/mainMenu.mp3");
	File MFoverWorld = new File("resources/sounds/music/overWorld.mp3");
	
	File SFAtmoDungeon = new File("resources/sounds/sound/Atmo_dungeon.mp3");
	File SFAtmoOverWorld = new File("resources/sounds/sound/Atmo_overWorld.mp3");
	File SFgetAchieve = new File("resources/sounds/sound/getAchieve.mp3");
	File SFgetCoin = new File("resources/sounds/sound/getCoin.mp3");
	File SFmagicCast = new File("resources/sounds/sound/magicCast.mp3");
	File SFpunchHit = new File("resources/sounds/sound/punchHit.mp3");
	
	

}