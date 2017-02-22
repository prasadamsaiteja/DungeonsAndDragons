package model.character;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import GameComponents.Dice;
import GameComponents.SharedVariables;
import model.character.classes.CharacterClass;
import model.character.classes.CharacterClassStructure;
import model.character.wearables.weapons.WeaponFactory;
import model.character.wearables.weapons.WeaponsInterface;

/**
 * Build a new character
 * 
 * @author Supreet Singh (s_supree)
 *
 */
public class Character extends Observable {
	private String characterClass;
	private String name;
	private int level;
	private int strength;
	private int dexterity;
	private int constitution;
	private boolean isBuilt = false;
	private int hitScore = 0;
	private String weaponName;
	private WeaponsInterface weapon;
	private String fname;

	/**
	 * @param String
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * retrieve character name
	 *
	 * @return character name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param String characterClass
	 */
	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}

	/**
	 * set character level
	 * 
	 * @param int level
	 */
	public Character setLevel(int level) {
		this.level = level;
		return this;
	}

	/**
	 * retrieve character level
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * @param int
	 *            strength
	 */
	public Character setStrength(int strength) {
		this.strength = strength;
		return this;
	}

	/**
	 * retrieve character strength
	 *
	 * @return int strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param dexterity
	 * @return
	 */
	public Character setDexterity(int dexterity) {
		this.dexterity = dexterity;
		return this;
	}

	/**
	 * @return dexterity
	 */
	public int getDexterity() {
		return dexterity;
	}

	/**
	 * @param int
	 *            constitution
	 */
	public Character setConstitution(int constitution) {
		this.constitution = constitution;
		return this;
	}

	/**
	 * return constitution
	 */
	public int getConstitution() {
		return constitution;
	}

	public String getCharacterClass() {
		return this.characterClass;
	}

	/**
	 * sets the weapon and redraws the character
	 *
	 * @param String
	 *            weapon
	 */
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	/**
	 * @return weapon
	 */
	public String getWeaponName() {
		return this.weaponName;
	}

	/**
	 * draws/redraws the character and notifies observers
	 */
	public void draw() {
		System.out.println(this.getWeaponName());
		// set weapon
		this.weapon = WeaponFactory.get(
										WeaponFactory.getValidParam(this.getWeaponName())
								  );
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * builds and initializes the character this method is run just once in a
	 * characters lifetime and calculates - hit score, based on chacaters class
	 */
	public void build() {
		// Build characters class and get hit score
		CharacterClass cClass = new CharacterClass(this.getCharacterClass(), this);
		CharacterClassStructure	cClassVal = cClass.get();
		
		cClass.calculateHitScore(
							new Dice(cClassVal.getNumberOfRolls(), 
									 cClassVal.getDiceSides(), 
									 cClassVal.getNumberOfRolls()
								)
						);
		this.hitScore = cClass.getHitScore();
		this.isBuilt = true;
	}

	public void hit(int damage) throws Exception {
		this.hitScore -= damage;
	}

	public int getHitScore() {
		return this.hitScore;
	}
	
	public boolean save() {	
		if (!this.isBuilt){
			this.build();
			Date d = new Date();
			long dTime = d.getTime();
			this.fname = this.name + dTime;
		}
		
		XStream xstream = new XStream(new StaxDriver());		
		String xml = xstream.toXML(this);
		FileWriter out;
		try {
			String filepath = SharedVariables.CharactersDirectory+File.separator+this.fname+".xml";
			System.out.println(filepath);
			out = new FileWriter(filepath);
			out.write(xml);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	/*
	public static void main(String[] args) {
		Character character = new Character();
		character.setConstitution(12);
		character.setLevel(2);
		character.setCharacterClass("Rogue");
		if (!character.isInitialized()) {
			character.build();
		}
		ArrayList<String> weapons = WeaponFactory.getAllowedWeapons(character.getLevel());
		System.out.println(weapons);
		System.out.println(character.getHitScore());
		try {
			character.hit(12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(character.getHitScore());
	}
	*/
}
