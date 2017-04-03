package game.model.decorator;

import game.model.Item;

public abstract class SimpleWeapon extends Item{

	private String itemName,itemType, itemClass;
	int itemLevel;
	
	public SimpleWeapon(String name, String type,String itemclass,int level ){
		super(name,type, itemclass, level);
	}
	
	public abstract String getEnchantmentType();
	public abstract String getWeaponType();
	public abstract int getTargetDamage(Character Targetcharacter);
	public abstract void setEffect();
	public abstract int getRange();
}
