package game.objects;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import core.FileLink;

abstract class PlayerObjectManager extends Initializer{
	
	private double manaPool = 0.7; //value between 0 and 1: 1 = full Mana
	private double armor = 0; //value between 0 and 1: 1 = invinciblemode
	private double armorDurability = 0;
	
	private int maxLife = 3;
	
	private int magicSpell = 0;
	private double manaRegen = 1;
	
	private int coinInventory = 5;
	private int keyInventory = 10;
	
	private int healthPotionInventory;
	private int manaPotionInventory;
	
	private int experience;
	private double weaponDamage = 1;
	private int magicLevel = 1;
	private int level = 1;
	
	
	protected PlayerObjectManager(){

	}
	
	public void addExperience(int xp){
		
		int oldLevel = level;
		experience += xp;
		System.out.println("+"+xp+" to "+experience);
		switch(experience){
			case 5:  level = 2; break;
			case 12: level = 3; break;
			case 20: level = 4; break;
			case 35: level = 5; break;
		}
		
		if(oldLevel != level)
			levelUp();
	}
	
	private void levelUp(){
		
		switch(level){
			case 1: maxLife = 4; break;
			case 2: maxLife = 5; break;
			case 3: maxLife = 6; break;
			case 4: maxLife = 7; break;
			case 5: maxLife = 8; break;
		}
		System.out.println("Level Up! @Lvl:"+level);
		setLife(maxLife);
		this.startFlashTimer(500, 3000);
	}


	//mana
	public void setManaPool(double mana){
		this.manaPool = mana;
	}
	
	public double getManaPool(){
		return manaPool;
	}
	
	//attack

	public double getAttackDamage(){
		return weaponDamage;
	}
	
	//armor
	public void setArmor(double armor, double durability){
		
		if(armor >= 0 && armor <= 1){
			this.armor = armor;
			this.armorDurability = durability;
		}
			
		else
			System.err.println("PlayerObjectManager.Error: Illegal Armor Value");
		if(armor == 1)
			System.out.println("==> setPlayer invincible");
	}
	
	public double getArmor(){return armor;}
	
	public void loseLife(int damage){
		
		System.out.println("pre.Life@"+getLife()+", @damage:"+damage);
		setLife(getLife() - (1-armor)*damage);
		
		
		if(armor > 0)
			armor = armor - 1/(damage*armorDurability);
		
		if(armor <= 0){
			System.out.println("Armor broken!");
			armor = 0;
			armorDurability = 0;
		}
		System.out.println("after.Life@"+getLife());
	}
	
	//magic
	public void scrollUpMagicSpell(){magicSpell = ((magicSpell + 1)%magicLevel);}
	public int getMagicSpell(){return magicSpell;}

	public void castMagicSpell(){
		boolean cast = false;
		
		switch(magicSpell){
		case(0):	if(manaPool >= 0.08){
					manaPool -= 0.14;
					Magic.addInstance(magicSpell, this);
					cast = true;
					}break;
					
		case(1):	if(manaPool >= 0.18){
					manaPool -= 0.28;
					Magic.addInstance(magicSpell, this);
					cast = true;
					}break;
		}
		
		if(!cast)
			System.err.println("Not enough Mana!");
	}
	
	public void setManaRegen(double manaRegen){this.manaRegen = manaRegen;}
	public void setMaxLife(){
		if(getLife() > maxLife)
			setLife(maxLife-0.0);
		//System.out.println("life@"+getLife()+" to Max@"+maxLife);
	}
	
	public void automaticManaRegen(){
		if(manaPool < 1){
			if(magicLevel == 1)
				manaPool += 0.0001;
			if(magicLevel == 2)
				manaPool += 0.0005;
		}
			
		
		if(manaPool > 1)
			manaPool = 1;
		
	}
	
	public void addItem(int[] itemIDData){
		System.out.println("getItem - ID:"+itemIDData[0]+", Type:"+itemIDData[1]+", Member:"+itemIDData[2]);
		int ID = itemIDData[0];
		int type = itemIDData[1];
		int member = itemIDData[2];
		if(ID == 0){
			
			if(type == 0){
				switch(member){
				case(0):	coinInventory += 1; break;
				case(1):	coinInventory += 5; break;
				case(3):	coinInventory += 10; break;
				}
			}
			
			if(type == 1){
				switch(member){
				case(0):	setLife(getLife()+1); break;
				case(1):	setLife(getLife()+2); break;
				}
			}
			
			if(type == 2){
				switch(member){
				case(0):	manaPool += 0.4; break;
				case(1):	manaPool += 0.7; break;
				}
				
				
			}
		}
		
		if(ID == 1){
			if(type == 0){
				healthPotionInventory++;
			}
				
			if(type == 1){
				manaPotionInventory++;
			}
		}
		
		if(ID == 3){
			if(type == 0){
				switch(member){
				case 0: setArmor(0.8, 30); break;
				}
			}
		}
		/*
		 * 	WEAPON(2, 0, 0, itemListID00, 0, 5, 0),
	
			ARMOR(3, 0, 0, itemListID00, 0, 6, 0),
	
			SPELL1(4, 0, 0, itemListID00, 0, 7, 0),
			SPELL2(4, 0, 1, itemListID00, 1, 7, 0),
		 */
		if(ID == 2){
			if(type == 0){
				switch(member){
				case 0:	weaponDamage = 1; break;
				case 1: weaponDamage = 1.7; break;
				}
			}
		}
		
		if(ID == 5){
			keyInventory++;
			System.out.println("=> added Key to Inventory@"+keyInventory);
		}
		
		
	}
	
	public void addCoin(int num){coinInventory += num;}
	public int getCoin(){return coinInventory;}
	
	public int getKeyInventory(){return keyInventory;}
	
	public boolean useKeyInventory(){
		
		boolean use = true;
		
		if(keyInventory > 0){
			keyInventory--;
			use = false;
			System.out.println("used Key: KeyInventory@"+keyInventory);
		}
		
		return use;
	}

	public boolean useHealthPotion(){
		boolean use = false;
		if(healthPotionInventory > 0){
			setLife(getLife()+3);
			healthPotionInventory--;
			use = true;
			System.out.println("used HealthPotion");
		}
		return use;
	}
	
	public boolean useMoney(int money){
		boolean use = false;
		if(money <= coinInventory){
			coinInventory -= money;
			use = true;
		}
		
		return use;
	}
	
	public boolean useManaPotion(){
		boolean use = false;
		if(manaPotionInventory > 0){
			manaPool += 0.7;
			manaPotionInventory--;
			use = true;
			System.out.println("used ManaPotion");
		}
		return use;
	}
	

	
	private class ItemInventory<I,T,M>{
		final I ID;
		final T type;
		final M member;
		
		private ItemInventory(I ID, T type, M member){
			this.ID = ID;
			this.type = type;
			this.member = member;
		}
	}
	
}
