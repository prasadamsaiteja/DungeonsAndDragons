package game.model.character.classes;

import java.util.HashMap;
import java.util.Set;

import javax.management.RuntimeErrorException;

import game.components.Dice;
import game.model.character.Character;

/**
 * Get character class - allowed classes and hit score
 * @author Supreet Singh (s_supree)
 * @version 1.0.0
 */
public class CharacterClass {
	
	/**
	 * define allowed classes
	 * 
	 * @author Supreet Singh (s_supree)
	 */
	private static class DefinedClasses {
	  
		/**
		 * @return a hashmap with character class and corresponding structure
		 */
		public static HashMap<String, CharacterClassStructure> getCharacterList(){
		  
			HashMap<String, CharacterClassStructure> characterList = new HashMap<>();
			
			// Add character classes

			// add fighter class
			CharacterClassStructure fighterStructure = new CharacterClassStructure();
			fighterStructure.setDiceSides(10);
			fighterStructure.setNumberOfRolls(1);
			characterList.put("Fighter", fighterStructure);
			
			// add ninja class
			CharacterClassStructure ninjaStructure = new CharacterClassStructure();
			ninjaStructure.setDiceSides(5);
			ninjaStructure.setNumberOfRolls(4);
			characterList.put("Ninja", ninjaStructure);	
			
			// add rogue class
			CharacterClassStructure rogueStructure = new CharacterClassStructure();
			rogueStructure.setDiceSides(8);
			rogueStructure.setNumberOfRolls(1);
			characterList.put("Rogue", rogueStructure);	
			
			return characterList;
		}
	}

	private Character character;
	private int hitPoints = 0;
	private String name;
	// Store allowed character classes
	
	/**
	 * @param String name
	 * @param String character
	 * @throws {@link RuntimeErrorException}
	 */
	public CharacterClass(String name, Character character){
		if (!CharacterClass.isClassAllowed(name)){
			throw new RuntimeErrorException(null);
		}
		
		this.name = name;
		this.character = character;
	}

	/**
	 * @param cClass
	 * @return true or false 
	 */
	public static boolean isClassAllowed(String cClass){
		return DefinedClasses.getCharacterList().containsKey(cClass);
	}
	
	/**
	 * @return a string set of allowed classes
	 */
	public static Set<String> getAllowedClasses(){
		return DefinedClasses.getCharacterList().keySet();
	}
	
	/**
	 * calculate hit score
	 * @param {@link Dice}
	 */
	public void calculateHitScore(Dice dice){
		CharacterClassStructure cStructure = DefinedClasses.getCharacterList().get(this.name);
	
		hitPoints += this.character.getConstitution() * this.character.getLevel();
		
		if (this.character.getLevel() > 1){
			int diceRoll = dice.getRollSum();
			hitPoints += diceRoll; 
		}else{
			hitPoints += cStructure.getDiceSides();
		}
	}
	
	/**
	 * @return hit points
	 */
	public int getHitScore(){
		return hitPoints;
	}
	
	/**
	 * get character class structure of instantiated class
	 * @return {@link CharacterClassStructure}
	 */
	public CharacterClassStructure get(){
		return DefinedClasses.getCharacterList().get(this.name);
	}
	
}