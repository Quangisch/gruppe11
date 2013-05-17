package core;

import map.DungeonBuilder;
import map.DungeonCollision;
import map.DungeonNavigator;
import map.DynamicMapAnimation;
import map.OverWorldCamera;
import map.OverWorldMap;
import menu.MenuIngame;
import menu.MenuMain;
import characters.Goomba;
import characters.Player;
import characters.PlayerInterface;

public interface GameObjects{
	final static DynamicMapAnimation dynamicMapAnimation = new DynamicMapAnimation();
	final static MenuIngame menuIngame = new MenuIngame();
	final static MenuMain menuMain = new MenuMain();
	
	
	final static Player player = new Player();
	final static Goomba goomba = new Goomba();
	final static PlayerInterface playerInterface = new PlayerInterface(player);
	
	final static OverWorldCamera overWorldCamera = new OverWorldCamera(player);
	final static DungeonBuilder dungeonBuilder = new DungeonBuilder(player,dynamicMapAnimation);
	final static DungeonCollision dungeonCollision = new DungeonCollision();
	
	final static OverWorldMap overWorldMap = new OverWorldMap(player,overWorldCamera);
	final static DungeonNavigator dungeonNavigator = new DungeonNavigator(player,dungeonBuilder,dungeonCollision,overWorldMap);
	final static CollisionDetection collisionDetection = new CollisionDetection(player,overWorldMap,dungeonNavigator,goomba);
	
	final static Board board = new Board(menuMain, menuIngame, player,playerInterface,overWorldMap,dungeonNavigator,dungeonBuilder, collisionDetection,goomba);
	
	
	//===
	final static Goomba goombaReference = new Goomba(player,dungeonCollision);
	final static Player playerReference = new Player(overWorldMap,dungeonNavigator);
	final static DungeonCollision dungeonCollisionReference = new DungeonCollision(player,goomba,dungeonNavigator,dynamicMapAnimation);
	final static DungeonBuilder dungeonBuilderReference = new DungeonBuilder(dungeonNavigator, board, goomba);
	final static DynamicMapAnimation dynamicMapAnimationReference = new DynamicMapAnimation(dungeonNavigator);
	
	
}