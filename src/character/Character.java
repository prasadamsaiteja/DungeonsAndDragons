package character;

import GameComponents.Dice;
import character.classes.CharacterClassFactory;
import character.classes.ClassInterface;

/**
 * Build a new character

 * @author Supreet Singh (s_supree)
 *
 */
public class Character {
	private String characterClass;
	private String name;
	private int level;
	private int strength;
	private int dexterity;
	private int constitution;
	private boolean isInitialized = false;
	private int hitScore = 0;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setCharacterClass(String characterClass){
		this.characterClass = characterClass;
	}

	public int getLevel(){
		return this.level;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public void setStrength(int strength){
		this.strength = strength;
	}

	public void setDexterity(int dexterity){
		this.dexterity = dexterity;
	}

	public void setConstitution(int constitution){
		this.constitution = constitution;
	}

	public String getCharacterClass() {
		return characterClass;
	}

	public int getStrength() {
		return strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getConstitution() {
		return constitution;
	}
	
	public void build(){
		if (!this.isInitialized)
		{
			ClassInterface cClass = CharacterClassFactory.get(CharacterClassFactory.getValidParam(this.characterClass));
			cClass.setLevel(3);
			cClass.setCharacterObj(this);
			this.hitScore = cClass.getHitScore(new Dice(cClass.getNumberOfRolls(), cClass.getDiceSides(), cClass.getNumberOfRolls()));
			this.isInitialized = true;
		}
	}
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	public void hit(int damage) throws Exception{
		if (!this.isInitialized()){
			throw new Exception("Character not initialized");
		}
		
		this.hitScore -= damage;
	}
	
	public int getHitScore(){
		return this.hitScore;
	}
	
	public static void main(String[] args){
		Character character = new Character();
		character.setConstitution(12);
		character.setCharacterClass("Fighter");
		if (!character.isInitialized()){
			character.build();
		}
		System.out.println(character.getHitScore());
		try {
			character.hit(12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(character.getHitScore());
	}
}
