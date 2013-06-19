package core;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import objects.Player;

import map.Camera;
import map.DungeonNavigator;
import map.OverWorldNavigator;

public class IngameMenu implements FileLink{

	private static IngameMenu ingameMenu;
	private BufferedImage igmBorderBuff = new BufferedImage(810,630,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage itemListBuff = new BufferedImage(100,225,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage manaBarBuff = new BufferedImage(249,47,BufferedImage.TYPE_INT_ARGB);
	private BufferedImage manaPoolBuff = new BufferedImage(249,47, BufferedImage.TYPE_INT_ARGB);
	
	
	private int[] slotCounter = new int[2];
	
	private IngameMenu(){
		initializeInstance();
	}
	
	public void draw(Graphics2D g2d){
		
		
		drawMiniMap(g2d);
		g2d.drawImage(igmBorderBuff, 0, 0, null);
		
		double[] inventoryList = Player.getInstance().getInventory();
		int length;
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		for(int i = 0; i < (new Double(inventoryList[2]).intValue()); i++)
			g2d.drawImage(itemListBuff.getSubimage(0, 25, 25, 25),480+i*33,305,75,75,null);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));		
		for(int i = 0; i < (new Double(inventoryList[1]).intValue()); i++)
			g2d.drawImage(itemListBuff.getSubimage(0, 25, 25, 25),480+i*33,305,75,75,null);
		
		//Mana
		if(Player.getInstance().getManaPool() > 0){
			g2d.drawImage(manaPoolBuff, 510-(int)(10*inventoryList[3]), 365, (int)(inventoryList[3]*100), 25, null);
		}
		g2d.drawImage(manaBarBuff, 500, 365, (int)(100*inventoryList[4]),25,null);
		
		//Level
		String lvl = ("Lvl "+(new Double(inventoryList[5]).intValue()));
		length = lvl.length();
		for(int i = 0; i < length; i++){
			String singleChar = lvl.substring(0, 1);
			lvl = lvl.substring(1);
			g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),500+i*10,395,30,30,null);
		}
		
		//Key
		g2d.drawImage(itemListBuff.getSubimage(0, 200, 25, 25),650,385,50,50,null);
		String key = ("x"+(new Double(inventoryList[8]).intValue()));
		length = key.length();
		for(int i = 0; i < length; i++){
			String singleChar = key.substring(0, 1);
			key = key.substring(1);
			g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),660+i*10,395,30,30,null);
		}
		
		//Coins
		g2d.drawImage(itemListBuff.getSubimage(0, 0, 25, 25),700,380,60,60,null);
		String coin = ("x"+(new Double(inventoryList[0]).intValue()));
		length = coin.length();
		for(int i = 0; i < length; i++){
			String singleChar = coin.substring(0, 1);
			coin = coin.substring(1);
			g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),720+i*10,395,30,30,null);
		}
		
		/*
		 * case 0: setArmor(0.8, 25); break;
		case 1: setArmor(1.0, 50); break;
		case 0:	weaponDamage = 1; break;
		case 1: weaponDamage = 1.7; break;
		 */
		
		//weapon
		if(inventoryList[9] >= 0.5){
			g2d.drawImage(itemListBuff.getSubimage(0, 125, 25, 25),500,490,60,60,null);
			
			String weaponLevel = "";
			if(inventoryList[9] >= 0.5 && inventoryList[9] < 1.5)
				weaponLevel = "Lvl 1";
			if(inventoryList[9] >= 1.5 && inventoryList[10] < 2.5)
				weaponLevel = "Lvl 2";
			
			length =  weaponLevel.length();
			for(int i = 0; i < length; i++){
				String singleChar = weaponLevel.substring(0, 1);
				weaponLevel = weaponLevel.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),500+i*10, 530, 20, 20,null);
			}
			
		}
			
		
		//armor
		if(inventoryList[10]>0){
			g2d.drawImage(itemListBuff.getSubimage(0, 150, 25, 25),580,490,60,60,null);
			String armor = ((new Double(inventoryList[10]*100).intValue())+"%");
			length =  armor.length();
			for(int i = 0; i < length; i++){
				String singleChar = armor.substring(0, 1);
				armor = armor.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),600+i*10,470,30,30,null);
			}
			
			String armorLevel = "";
			if(Player.getInstance().getArmorDurability() >= 0 && Player.getInstance().getArmorDurability() < 50)
				armorLevel = "Lvl 1";
			if(Player.getInstance().getArmorDurability() >= 50 && Player.getInstance().getArmorDurability() < 100)
				armorLevel = "Lvl 2";
			
			length =  armorLevel.length();
			for(int i = 0; i < length; i++){
				String singleChar = armorLevel.substring(0, 1);
				armorLevel = armorLevel.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),580+i*10,530,20,20,null);
			}
		}
		
		//MagicLevel
		if(inventoryList[6] > 0){
			for(int i = 0; i < inventoryList[6]; i++)
				g2d.drawImage(itemListBuff.getSubimage(0+i*25, 175, 25, 25),650+i*50,490,60,60,null);
			
			String magicLevel = "";
			if(inventoryList[6]  == 1)
				magicLevel = "Lvl 1";
			if(inventoryList[6] == 2)
				magicLevel = "Lvl 2";
			
			length =  magicLevel.length();
			for(int i = 0; i < length; i++){
				String singleChar = magicLevel.substring(0, 1);
				magicLevel = magicLevel.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),660+i*10,530,20,20,null);
			}
		}
		
		
		//HealthPotion
		if(inventoryList[11] > 0){
			g2d.drawImage(itemListBuff.getSubimage(0, 75, 25, 25),105,105,60,60,null);
			String hpPotion = ("x"+(new Double(inventoryList[11]).intValue()));
			length = hpPotion.length();
			for(int i = 0; i < length; i++){
				String singleChar = hpPotion.substring(0, 1);
				hpPotion = hpPotion.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),110+i*10,110,30,30,null);
			}
		}
		
		//ManaPotion
		if(inventoryList[12]>0){
			g2d.drawImage(itemListBuff.getSubimage(0, 100, 25, 25),105,150,60,60,null);
			String manaPotion = ("x"+(new Double(inventoryList[12]).intValue()));
			length = manaPotion.length();
			for(int i = 0; i < length; i++){
				String singleChar = manaPotion.substring(0, 1);
				manaPotion = manaPotion.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),110+i*10,155,30,30,null);
			}
		}
		
		//NextLevel
		g2d.setColor(Color.RED);
		g2d.fillRect(500, 440, 250, 10);
		g2d.setColor(Color.GREEN);
		g2d.fillRect(500, 440, (int)(250*(inventoryList[7]/(7*inventoryList[5]))), 10);
		String nextLevel = ((new Double(7*inventoryList[5]-inventoryList[7])).intValue()+"xp to next Level");
		length = nextLevel.length();
		
		for(int i =0; i < length; i++){
			String singleChar = nextLevel.substring(0, 1);
			nextLevel = nextLevel.substring(1);
			g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar), 500+i*12, 450, 20, 20, null);
		}
		g2d.drawImage(manaBarBuff, 495, 437, 265, 16,null);
	
	}
	
	/*
 	inventoryList[0] = coinInventory;
	
	inventoryList[1] = getLife();
	inventoryList[2] = maxLife;
	inventoryList[3] = manaPool;
	inventoryList[4] = maxMana;
	
	inventoryList[5] = level;
	inventoryList[6] = magicLevel;
	inventoryList[7] = experience;
	
	inventoryList[8] = keyInventory;
	inventoryList[9] = weaponDamage;
	inventoryList[10] = armor;
	
	inventoryList[11] = healthPotionInventory;
	inventoryList[12] = manaPotionInventory;
 */
	
	private void drawMiniMap(Graphics2D g2d){
		
		if(GameManager.getInstance().dungeon)
			g2d.drawImage(DungeonNavigator.getInstance().getMapImage().getSubimage(Camera.getInstance().getX(), Camera.getInstance().getY(), 810, 630),500,120,248,190,null);
		if(GameManager.getInstance().overWorld)
			g2d.drawImage(OverWorldNavigator.getInstance().getMapImage().getSubimage(Camera.getInstance().getX(), Camera.getInstance().getY(), 810, 630),500,120,248,190,null);
		
		
		/*TODO
		int xMap = 0;
		int yMap = 0;
		int widthMap = 0;
		int heightMap = 0;
		
		if(GameManager.dungeon){
			xMap = DungeonNavigator.getInstance().getXCoordinate();
			yMap = DungeonNavigator.getInstance().getYCoordinate();
			widthMap = DungeonNavigator.getInstance().getWidthMap();
			heightMap = DungeonNavigator.getInstance().getHeightMap();
		}
			
		
		if(GameManager.overWorld){
			xMap = OverWorldNavigator.getInstance().getXCoordinate();
			yMap = OverWorldNavigator.getInstance().getYCoordinate();
			widthMap = OverWorldNavigator.getInstance().getWidthMap();
			heightMap = OverWorldNavigator.getInstance().getHeightMap();
		}
			
			
			int xOffset = -405;
			int yOffset = -315;
			int width = 1620;
			int height = 1260;
			
			if(Camera.getInstance().getX() + 810 == widthMap)
				width -= 405;
				
	
			if (Camera.getInstance().getX() == 0){
				width -= 405;
				xOffset = 0;
			}
				
			
			if(Camera.getInstance().getY() + 630 == heightMap)
				height -= 315;
				
			if((Camera.getInstance().getY() == 0)){
				height -= 315;
				yOffset = 0;
			}
			
			if(GameManager.dungeon)
				g2d.drawImage(DungeonNavigator.getInstance().getMapImage().getSubimage(-xMap+xOffset, -yMap+yOffset, height,width),500,120,245,190,null);
			if(GameManager.overWorld)
				g2d.drawImage(OverWorldNavigator.getInstance().getMapImage().getSubimage(-xMap+xOffset, -yMap+yOffset, height,width),500,120,245,190,null);
			*/
			
			
	}
	
	
	public int[] setSlot(){
		
		if(slotCounter[0] == 5){
			slotCounter[1]++;
			slotCounter[0] = 0;
		}
			
		slotCounter[0]++;
		
		return slotCounter;
	}
	
	private void initializeInstance(){
		try {
			
			igmBorderBuff = ImageIO.read(igmBorderFile);
			itemListBuff = ImageIO.read(itemListID00);
			manaPoolBuff = ImageIO.read(manaPool);
			manaBarBuff = ImageIO.read(manaBar);
				
			} catch (IOException e) {
				System.err.println("Sprite: File not found. "+e);
				System.exit(0);
			}
	}
	
	public static void resetInstance(){
		if(ingameMenu != null)
			ingameMenu = new IngameMenu();
	}
	
	public static IngameMenu getInstance(){
		if(ingameMenu == null)
			ingameMenu = new IngameMenu();
		return ingameMenu;
	}
	
}
