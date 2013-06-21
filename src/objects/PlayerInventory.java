package objects;

import core.PlayerInterface;
import core.Sound;

abstract class PlayerInventory extends Initializer{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3557445771253473743L;
	private double[] armor = {0,0}; //value between 0 and 1: 1 = invinciblemode
	private double[] armorDurability = {0,0};
	private int currentArmor = 0;
	private double weaponDamage = 0.2;
	private int magicLevel = 1;
	private int currentSpell = 0;
	
	private int level = 1;
	private int maxLife = 3;
	private double maxMana = 1;
	
	private double manaPool = 1; //value between 0 and maxMana (= full Mana)
	private double manaRegen = 0.00015;
	private double healthRegen = 0.0004;
	private int experience;
	
	private int coinInventory = 0;
	private int keyInventory = 0;
	private int healthPotionInventory = 0;
	private int manaPotionInventory = 0;
	
	

	protected PlayerInventory(){
		
	}
	
	
	
	
	public double[] getInventory(){
		double[] inventoryList = new double[13];
		
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
		inventoryList[10] = armor[currentArmor];
		
		inventoryList[11] = healthPotionInventory;
		inventoryList[12] = manaPotionInventory;

		return inventoryList;
	}
	
	public void addExperience(int xp){
		
		String xpString = (new Integer(xp)).toString() + "xp";
		PlayerInterface.getInstance().setDynamicInterface(xpString, this, false);
		int oldLevel = level;
		experience += xp;
		System.out.println("+"+xp+" to "+experience);
		
		if(experience >= 7*level){
			level++;
			manaRegen +=0.00005;
			experience -= 7;
		}

		if(oldLevel != level)
			levelUp();
	}
	
	private void levelUp(){
		
		switch(level){
			case 1: maxLife = 3; maxMana = 1.0; break;
			case 2: maxLife = 4; maxMana = 1.2; break;
			case 3: maxLife = 5; maxMana = 1.4; break;
			case 4: maxLife = 6; maxMana = 1.6; break;
			case 5: maxLife = 7; maxMana = 2.8; break;
		}
		System.out.println("Level Up! @Lvl:"+level);
		setLife(maxLife, false);
		manaPool = maxMana;
		setLevelUp();
		startRotateTimer(1000,70,8);
	}


	//mana
	public void setManaPool(double mana){
		this.manaPool = mana;
	}
	
	public double getManaPool(){
		return manaPool;
	}
	
	public double getMaxMana(){
		return maxMana;
	}
	
	public double getMaxLife(){
		return maxLife;
	}
	
	//attack

	public double getAttackDamage(){
		return weaponDamage;
	}
	
	//armor
	public void setArmor(int type, double armor, double durability){
		
		if(armor >= 0 && armor <= 1){
			this.armor[type] = armor;
			this.armorDurability[type] = durability;
		}
			
		else
			System.err.println("PlayerObjectManager.Error: Illegal Armor Value");
		if(armor == 1)
			System.out.println("==> setPlayer invincible");
	}
	
	public double getArmor(int type){
		if(type >= 0)
			return armor[type];
		else
			return armor[currentArmor];
	}
	public double getArmorDurability(int type){
		if(type >= 0)
			return armorDurability[type];
		else
			return armor[currentArmor];
	}
	
	public void loseLife(int type, double damage){

		//type 0 = physical, type 1 = magical dmg
		
		if(type == currentArmor)
			damage *= 0.7;
		else
			damage *= 1.3;
		
		System.out.println("@damage:"+damage+"@afterArmor:"+(1-armor[currentArmor])*damage);
		System.out.println("getLife@"+getLife()+",setLife@"+(getLife() - (1-armor[currentArmor])*damage));
		
		setLife(getLife() - (1-armor[currentArmor])*damage, true);
	
		
		if(armor[currentArmor] > 0)
			armor[currentArmor] = armor[currentArmor] - 1/(damage*armorDurability[currentArmor]);
		
		if(armor[currentArmor] < 0){
			System.out.println("Armor broken!");
			armor[currentArmor] = 0;
			armorDurability[currentArmor] = 0;
		}
	}
	
	//magic
	public void scrollUpMagicSpell(){currentSpell = ((currentSpell + 1)%magicLevel);}
	public int getMagicSpell(){return currentSpell;}

	public void castMagicSpell(){
		boolean cast = false;
		
		switch(currentSpell){
		case(0):	if(manaPool >= 0.08){
					manaPool -= 0.14;
					Magic.addInstance(currentSpell, this);
					cast = true;
					}break;
					
		case(1):	if(manaPool >= 0.18){
					manaPool -= 0.28;
					Magic.addInstance(currentSpell, this);
					cast = true;
					}break;
		}
		
		if(!cast)
			System.err.println("Not enough Mana!");
	}
	
	public void setManaRegen(double manaRegen){this.manaRegen = manaRegen;}
	public void setMaxLife(){
		if(getLife() > maxLife)
			setLife(maxLife,false);
		//System.out.println("life@"+getLife()+" to Max@"+maxLife);
	}
	
	public void automaticRegen(){
		if(manaPool < maxMana)
			manaPool += manaRegen;
		if(getLife() < maxLife)
			setLife(getLife() + healthRegen, false);
			
		if(manaPool > maxMana)
			manaPool = maxMana;
		
		if(getLife() > maxLife)
			setLife(maxLife, false);
		
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
				case(2):	coinInventory += 10; break;
				
				}
				Sound.getInstance().playSound(10);
			}
			
			if(type == 1){
				switch(member){
				case(0):	setLife(getLife()+1,false); break;
				case(1):	setLife(getLife()+2,false); break;
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
				case 0: setArmor(0, 0.8, 25); break;
				case 1: setArmor(0, 1.0, 50); break;
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
		if(ID == 4){
			if(type == 0){
				switch(member){
				case 0: magicLevel = 1; break;
				case 1: magicLevel = 2; manaRegen += 0.0001;break;
				}
			}
		}
		
		if(ID > 0){
			setAchieve();
			ItemAchieve.addInstance(getX(),getY(),itemIDData);
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
			setLife(getLife()+6,false);
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
	
	
}
