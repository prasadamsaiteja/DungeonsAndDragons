package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import model.character.Character;
import java.lang.Math.*;

@XmlRootElement(name = "Item")
/**
 * This is an item class which is used to store the items currently used by the
 * character while playing the game. It affects various classes of selected
 * items based on the level of the character.
 * 
 * @author Priyanka A
 * 
 * @version 1.0
 * @since 2/24/2017
 */
public class Item {

	@XmlElement(name = "itemName")
	public String itemName;
	@XmlElement(name = "itemType")
	public String itemType;
	@XmlElement(name = "itemClass")
	public String itemClass;
	@XmlElement(name = "itemLevel")
	public int itemLevel;

	/** creating an object of class Character */

	Character charObj = new Character();

	private int armorClass = 0;
	private int strength = charObj.getStrength();
	private int damagebonus;
	private int attackbonus;
	private int dexterity = charObj.getDexterity();
	private int constitution = charObj.getConstitution();
	private int count;

	public Item(String name, String type, String itemclass, int level) {
		itemName = name;
		itemType = type;
		itemClass = itemclass;
		itemLevel = level;
	}

	public String getItemName() {
		return itemName;
	}

	/**
	 * Armor Class value should be set based on the armor type chosen. AC is
	 * used for almost all the items used.
	 */

	public void setInitialArmorClass() {
		if (itemClass.equalsIgnoreCase("Light")) {
			armorClass = armorClass + dexterity;
		} else if (itemName.equalsIgnoreCase("Medium")) {
			armorClass = armorClass + dexterity;
		} else
			armorClass = 14;
	}

	public void calculateLevelCount() {
		if (itemLevel <= 4) {
			count = 1;
		} else if (itemLevel <= 8) {
			count = 2;
		} else if (itemLevel <= 12) {
			count = 3;
		} else if (itemLevel <= 16) {
			count = 4;
		} else
			count = 5;
	}

	/**
	 * This method is used to calculate the ability modifier values for 
	 * each of the items used by the character.
	 */
	@SuppressWarnings("fallthrough")

	public void calculateModifiersValue() {
		setInitialArmorClass();
		calculateLevelCount();
		switch (itemType) {
		case "Belt":
			if (itemClass.equalsIgnoreCase("Strength")) {
				strength = strength + count;
				break;
			} else
				constitution = constitution + count;
			break;
		case "Weapon":
			if (itemClass.equalsIgnoreCase("Ranged")) {
				damagebonus = dexterity + count;
				break;
			} else
				attackbonus = strength + count;
			break;
		case "Armor":
			armorClass = armorClass + count;
			break;
		case "Ring":
			if (itemClass.equalsIgnoreCase("ArmorClass")) {
				armorClass = armorClass + count;
				break;
			} else if (itemClass.equalsIgnoreCase("Strength")) {
				strength = strength + count;
				break;
			} else
				constitution = constitution + count;
			break;
		case "Shield":
			armorClass = armorClass + count;
			break;
		case "Boots":
			if (itemClass.equalsIgnoreCase("ArmorClass")) {
				armorClass = armorClass + count;
				break;
			} else
				dexterity = dexterity + count;
			break;
		case "Helmet":
			armorClass = armorClass + count;
		}
	}
	
	/**
	 * this method is used to update these ability modifier values in the character class.
	 */

	public void setCharacterClassValues() {
		charObj.setArmorClass(armorClass);
		charObj.setDexterity(dexterity);
		charObj.setStrength(strength);
		charObj.setDamageBonus(damagebonus);
		charObj.setAttackBonus(attackbonus);
		charObj.setConstitution(constitution);
	}

	/**
	 * Instantiates a new map.
	 */
	public Item() {
	}
}
