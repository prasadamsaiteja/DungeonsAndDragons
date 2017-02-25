package game.model.character.classes;

import game.interfaces.DiceImplementationInterface;

/**
 * Custom data structure for storing charcter classes
 * 
 * @author Supreet Singh (s_supree)
 * @since 1.0.0
 */
public class CharacterClassStructure implements DiceImplementationInterface {
	private int numberOfRolls;
	private int diceSides;
	
	/**
	 * set number of dice rolls
	 * 
	 * @param nOfRolls
	 */
	public void setNumberOfRolls(int nOfRolls){
		this.numberOfRolls = nOfRolls;
	}
	
	/**
	 * set number of dice sides
	 * 
	 * @param sideDices
	 */
	public void setDiceSides(int sideDices){
		this.diceSides = sideDices;
	}
	
	/**
	 * @return number of dice rolls
	 */
	public int getNumberOfRolls(){
		return this.numberOfRolls;
	}
	
	/**
	 * @return number of dice sides
	 */
	public int getDiceSides(){
		return this.diceSides;
	}
}
