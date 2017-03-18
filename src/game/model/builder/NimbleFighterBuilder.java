package game.model.builder;

import game.components.Dice;

import java.util.Arrays;

/**
 * Class that sets the abilities for the Nimble Class
 * @author RahulReddy
 * @version 1.0.0
 */
public class NimbleFighterBuilder extends FighterBuilder {

	int strength[],constitution[],dexterity[];
	int strength2,constitution2,dexterity2;
	int[] unSortedabilityValues;
	int[] sortedAbilityValues;
	
	/**
	 * Constructor for Nimble
	 */
	public NimbleFighterBuilder() {
		setAbilities();		
	}
	
	/**
	 * Method that sets the abilities of the Nimble Class
	 */
	private void setAbilities() {

		unSortedabilityValues=new int[3];
		sortedAbilityValues=new int[3];
		
		strength = new int[4];
		strength[0]= calculateAbilities();
		
		constitution = new int[4];
		constitution[0]= calculateAbilities();
		
		dexterity = new int[4];
		dexterity[0]= calculateAbilities();
		
		unSortedabilityValues[0] =  dexterity[0];
		unSortedabilityValues[1] = constitution[0];
		unSortedabilityValues[2] = strength[0];
		
		Arrays.sort(unSortedabilityValues);
		sortedAbilityValues=unSortedabilityValues;
	}

	/**
	 * Overriden Method that builds the strength
	 */
	void buildStrength() {
		getFighter().setStrength(sortedAbilityValues[0]);
	}
	
	/**
	 * Overriden Method that builds the Constitution
	 */
	void buildConstitution() {
		getFighter().setConstitution(sortedAbilityValues[1]);
	}
	
	/**
	 * Overriden Method that builds the Dexterity
	 */
	void buildDexterity() {
		getFighter().setDexterity(sortedAbilityValues[2]);

	}
	
	/**
	 *  Method for calculating abilities
	 * @return array random generated values 
	 */
	private int calculateAbilities() {
		Dice dice=new Dice(4,6,3);
		return dice.getRollSum();		
	}
	
}
