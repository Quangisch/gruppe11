package game.objects;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import core.FileLink;

abstract class PlayerObjectManager extends Initializer{
	
	private double manaPool = 0.7; //value between 0 and 1: 1 = full Mana
	private double attackModifier = 1; //basis Value 1
	private double armor = 0; //value between 0 and 1: 1 = invinciblemode
	private double armorDurability = 0;
	
	private int healPotion = 0;
	private int manaPotion = 0;
	private int magicSpell = 0;
	private int magicSpellMax = 2;
	private double manaRegen = 1;
	
	
	protected PlayerObjectManager(){

	}
	
	


	//mana
	public void setManaPool(double mana){
		this.manaPool = mana;
	}
	
	public double getManaPool(){
		return manaPool;
	}
	
	//attack
	public void setAttackModifier(double attackModifier){
		this.attackModifier = attackModifier;
	}
	
	public double getAttackDamage(){
		return attackModifier;
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
	
	public void loseLife(int damage){
		
		System.out.println("pre.Life@"+getLife()+", @damage:"+damage);
		setLife(getLife() - (1-armor)*damage);
		
		
		if(armor > 0)
			armor = armor - (1 - 1/(damage*armorDurability));
		
		if(armor <= 0){
			System.out.println("Armor broken!");
			armor = 0;
			armorDurability = 0;
		}
		System.out.println("after.Life@"+getLife());
	}
	
	//magic
	public void scrollUpMagicSpell(){magicSpell = ((magicSpell + 1)%magicSpellMax);}
	public int getMagicSpell(){return magicSpell;}

	public void castMagicSpell(){
		boolean cast = false;
		
		switch(magicSpell){
		case(0):	if(manaPool >= 0.14){
					//manaPool -= 0.14;
					Magic.getInstance(magicSpell, this);
					cast = true;
					}break;
					
		case(1):	if(manaPool >= 0.28){
					//manaPool -= 0.28;
					Magic.getInstance(magicSpell, this);
					cast = true;
					}break;
		}
		
		if(!cast)
			System.err.println("Not enough Mana!");
	}
	
	public void setManaRegen(double manaRegen){this.manaRegen = manaRegen;}
	public void automaticManaRegen(){
		if(manaPool < 1)
			manaPool += 0.0001;
		
	}
	
}
