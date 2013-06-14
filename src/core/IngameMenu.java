package core;

import game.objects.Player;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

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
	private int lineCounter;
	
	private IngameMenu(){
		initializeInstance();
	}
	
	public void draw(Graphics2D g2d){
		
		
		drawMiniMap(g2d);
		g2d.drawImage(igmBorderBuff, 0, 0, null);
		
		double[] inventoryList = Player.getInstance().getInventory();
		
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		for(int i = 0; i < (new Double(inventoryList[2]).intValue()); i++)
			g2d.drawImage(itemListBuff.getSubimage(0, 25, 25, 25),480+i*33,305,75,75,null);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));		
		for(int i = 0; i < (new Double(inventoryList[1]).intValue()); i++)
			g2d.drawImage(itemListBuff.getSubimage(0, 25, 25, 25),480+i*33,305,75,75,null);
		
		
		if(Player.getInstance().getManaPool() > 0){
			g2d.drawImage(manaPoolBuff, 510-(int)(10*inventoryList[3]), 365, (int)(inventoryList[3]*100*inventoryList[4])-5, 25, null);
		}
		g2d.drawImage(manaBarBuff, 500, 365, (int)(100*inventoryList[4]),25,null);
		
		String lvl = ("Lvl "+(new Double(inventoryList[5]).intValue()));
		for(int i = 0; i < 5; i++){
			String singleChar = lvl.substring(0, 1);
			lvl = lvl.substring(1);
			g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),500+i*10,395,30,30,null);
		}
		
		g2d.drawImage(itemListBuff.getSubimage(0, 200, 25, 25),650,385,50,50,null);
		String key = ("x"+(new Double(inventoryList[8]).intValue()));
		for(int i = 0; i < key.length()+2; i++){
			String singleChar = key.substring(0, 1);
			key = key.substring(1);
			g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),660+i*10,395,30,30,null);
		}
		
		g2d.drawImage(itemListBuff.getSubimage(0, 0, 25, 25),700,380,60,60,null);
		String coin = ("x"+(new Double(inventoryList[0]).intValue()));
		for(int i = 0; i < coin.length()+1; i++){
			String singleChar = coin.substring(0, 1);
			coin = coin.substring(1);
			g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),720+i*10,395,30,30,null);
		}
		
		if(inventoryList[9]>0)
			g2d.drawImage(itemListBuff.getSubimage(0, 125, 25, 25),500,440,60,60,null);
		
		if(inventoryList[10]>0){
			g2d.drawImage(itemListBuff.getSubimage(0, 150, 25, 25),580,440,60,60,null);
			String armor = ((new Double(inventoryList[10]*100).intValue())+"%");
			for(int i = 0; i < armor.length()+2; i++){
				String singleChar = armor.substring(0, 1);
				armor = armor.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),600+i*10,470,30,30,null);
			}
		}
		
		
		for(int i = 0; i < inventoryList[6]; i++)
			g2d.drawImage(itemListBuff.getSubimage(0+i*25, 175, 25, 25),650+i*50,440,60,60,null);
		
	
		if(inventoryList[11]>0){
			g2d.drawImage(itemListBuff.getSubimage(0, 75, 25, 25),105,105,60,60,null);
			String hpPotion = ("x"+(new Double(inventoryList[11]).intValue()));
			for(int i = 0; i < hpPotion.length()+2; i++){
				String singleChar = hpPotion.substring(0, 1);
				hpPotion = hpPotion.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),110+i*10,110,30,30,null);
			}
		}
		
		if(inventoryList[12]>0){
			g2d.drawImage(itemListBuff.getSubimage(0, 100, 25, 25),105,150,60,60,null);
			String manaPotion = ("x"+(new Double(inventoryList[12]).intValue()));
			for(int i = 0; i < manaPotion.length()+2; i++){
				String singleChar = manaPotion.substring(0, 1);
				manaPotion = manaPotion.substring(1);
				g2d.drawImage(PlayerInterface.getInstance().translateTextTile(singleChar),110+i*10,155,30,30,null);
			}
		}
		

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
		
		if(GameManager.dungeon)
			g2d.drawImage(DungeonNavigator.getInstance().getMapImage().getSubimage(Camera.getInstance().getX(), Camera.getInstance().getY(), 810, 630),500,120,248,190,null);
		if(GameManager.overWorld)
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
	
	public static IngameMenu getInstance(){
		if(ingameMenu == null)
			ingameMenu = new IngameMenu();
		return ingameMenu;
	}
	
}
