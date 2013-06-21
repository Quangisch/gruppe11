package core;


import java.io.File;

import objects.ItemDrop;

public enum ItemListManager implements FileLink, java.io.Serializable{

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
	
	
	
	private int[] itemData = new int[6];
		
		
	ItemListManager(int ID, int type, int member, File file, int xRow, int yRow, int cycle){
			this.ID = ID;
			this.type = type;
			this.member = member;
			this.file = file;
			
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
	
	
	
	
	public int getID(){return ID;}
	public int getType(){return type;}
	public int getMember(){return member;}
	public File getFile(){return file;}
	public int[] getItemData(){return itemData;}
	

	
	
}
