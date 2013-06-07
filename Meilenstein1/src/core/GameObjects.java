package core;

import map.DungeonBuilder;
import map.DungeonCollision;
import map.DungeonNavigator;
import map.DynamicMapAnimation;
import map.OverWorldCamera;
import map.OverWorldMap;
import menu.MenuMain;
import characters.Goomba;
import characters.Player;
import characters.PlayerInterface;

public interface GameObjects{
	
	
	
	static Player player = new Player();
	final static Goomba goomba = new Goomba();
	final static PlayerInterface playerInterface = new PlayerInterface();
	final static CollisionDetection collisionDetection = new CollisionDetection();
	
	final static OverWorldMap overWorldMap = new OverWorldMap();
	final static OverWorldCamera overWorldCamera = new OverWorldCamera();
	
	final static DungeonBuilder dungeonBuilder = new DungeonBuilder();
	final static DungeonCollision dungeonCollision = new DungeonCollision();
	final static DungeonNavigator dungeonNavigator = new DungeonNavigator();
	
	final static DynamicMapAnimation dynamicMapAnimation = new DynamicMapAnimation();
	final static MenuMain menuMain = new MenuMain();
	
	final static Board board = new Board(menuMain, player,playerInterface,overWorldMap,dungeonNavigator,dungeonBuilder, collisionDetection,goomba);
	
	
	//===
	final static Player playerReference = new Player(overWorldMap,dungeonNavigator);
	final static Goomba goombaReference = new Goomba(player,dungeonCollision);
	final static PlayerInterface playerInterfaceReference = new PlayerInterface(player);
	final static CollisionDetection collisionDetectionReference = new CollisionDetection(player,goomba, overWorldMap,dungeonNavigator);
	
	final static OverWorldMap overWorldMapReference = new OverWorldMap(player,overWorldCamera);
	final static OverWorldCamera overWorldCameraReference = new OverWorldCamera(player);
	
	final static DungeonNavigator dungeonNavigatorReference = new DungeonNavigator(player,overWorldMap,dungeonBuilder,dungeonCollision);
	final static DungeonBuilder dungeonBuilderReference = new DungeonBuilder(player,goomba,dungeonNavigator,dynamicMapAnimation,board);
	final static DungeonCollision dungeonCollisionReference = new DungeonCollision(player,goomba,dungeonNavigator,dynamicMapAnimation);
	
	final static DynamicMapAnimation dynamicMapAnimationReference = new DynamicMapAnimation(dungeonNavigator);
	final static MenuMain menuMainReference = new MenuMain(player, goomba);
	
	
}