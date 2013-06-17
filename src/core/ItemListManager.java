package core;

import game.objects.ItemDrop;
import game.objects.Player;

import java.io.File;
import java.util.ArrayList;

public enum ItemListManager implements FileLink{

	//(ID, type, member, file, xRow, yRow, cycle)
	/*
	 * ID 0 = instant, 1 = potion, 2 = weapon, 3 = armor, 4 = spell
	 * type 
	 * member - smaller/bigger effects
	 */
	
	COIN(0, 0, 0,itemListID00, 0, 0, 4),
	COIN5(0, 0, 1,itemListID00, 0, 0, 4),
	COIN10(0, 0, 2,itemListID00, 0, 0, 4),
	HEALTH(0, 1, 0, itemListID00, 0, 1, 2),
	MANA(0, 2, 0, itemListID00, 0, 2, 2),
	
	HEALTHPOTION(1, 0, 0, itemListID00, 0, 3, 2),
	MANAPOTION(1, 1, 0, itemListID00, 0, 4, 2),
	
	WEAPON1(2, 0, 0, itemListID00, 0, 5, 0),
	WEAPON2(2, 0, 1, itemListID00, 0, 5, 0),
	
	ARMOR1(3, 0, 0, itemListID00, 0, 6, 0),
	ARMOR2(3, 0, 1, itemListID00, 0, 6, 0),
	
	SPELL1(4, 0, 0, itemListID00, 0, 7, 0),
	SPELL2(4, 0, 1, itemListID00, 1, 7, 0),
	
	KEY1(5, 0, 0, itemListID00, 0, 8, 0);

	
	private final int ID;
	private final int type;
	private final int member;
	private final File file;
	private final int xRow;
	private final int yRow;
	private final int cycle;
	
	public static int[] keyIDCounter = {1,1,1,1,1};
	public static int[] weaponIDCounter = {1};
	public static int[] armorIDCounter = {1};
	public static int[] spellIDCounter = {0,1};
	
	private int[] itemData = new int[6];
		
		
	ItemListManager(int ID, int type, int member, File file, int xRow, int yRow, int cycle){
			this.ID = ID;
			this.type = type;
			this.member = member;
			this.file = file;
			this.xRow = xRow;
			this.yRow = yRow;
			this.cycle = cycle;
			
			itemData[0] = ID;
			itemData[1] = type;
			itemData[2] = member;
			itemData[3] = xRow;
			itemData[4] = yRow;
			itemData[5] = cycle;
	}
	
	
	
	public final static int[] getItemData(int ID, int type, int member){
		int[] itemDataElement = new int[6];
		
		for(ItemListManager element : values()){
			
			if(element.ID == ID && element.type == type && element.member == member){
				itemDataElement = element.itemData;
				break;
			}		
		}
		
		return itemDataElement;
	}
	
	public final static File getItemFile(int ID, int type, int member){
		File itemFile = null;
		
		for(ItemListManager element : values()){
			
			if(element.ID == ID && element.type == type && element.member == member){
				itemFile = element.file;
				break;
			}		
		}
		
		return itemFile;
	}
	
	public final static boolean dropItem(int x, int y, int itemID, int itemType, int member){
		
		//for(ItemListManager item : (item.getID() == itemID && item.getType() == itemType && item.getMember() == member)){
		for(ItemListManager item : values()){
			if(item.getID() == itemID && item.getType() == itemType && item.getMember() == member){
				//int x, int y, int[] data, File file,int duration
				if(itemID == 0 || itemID == 1)
					ItemDrop.addInstance(x, y, item.itemData, item.file, 500);
				else 
					ItemDrop.addInstance(x, y, item.itemData, item.file, 2000);
				
				return true;
			}
		}
		
		return false;
	
	}
	
	public final static boolean dropKey(int x, int y, int itemID, int itemType, int member, int keyID){
		
		boolean drop = false;
		for(ItemListManager item : values()){
			
			if(item.getID() == itemID && item.getType() == itemType && item.getMember() == member){
				
				if(keyIDCounter[keyID] == 1){
					
					ItemDrop.addInstance(x, y, item.itemData, item.file, 1500);
					keyIDCounter[keyID] = 0;
					drop = true;
					break;
				}
				
			}
		}
		
		return drop;
	}
	
	private int getID(){return ID;}
	private int getType(){return type;}
	private int getMember(){return member;}
	
	public static void resetInstance(){
		for(int i = 0; i < keyIDCounter.length; i++){
			keyIDCounter[i] = 1;
		}
		
		weaponIDCounter[0] = 1;
		armorIDCounter[0] = 1;
		spellIDCounter[0] = 0;
		spellIDCounter[1] = 1;
		
	}
	
	
}
