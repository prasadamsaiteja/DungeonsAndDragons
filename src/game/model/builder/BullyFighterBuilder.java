package game.model.builder;

import game.components.Dice;

import java.util.Arrays;

/**
 * Class that sets the abilities for the Bully Class
 * @author RahulReddy
 * @version 1.0.0
 */
public class BullyFighterBuilder extends FighterBuilder 
{

	int strength[], constitution[], dexterity[];
	int strength2, constitution2, dexterity2;
	int[] unSortedabilityValues;
	int[] sortedAbilityValues;
		
	/**
	 * Constructor for Bully that sets abilities
	 */
	public BullyFighterBuilder() 
	{
		setAbilities();		
	}
	
	/**
	 * Method that sets the abilities of the Bully Class
	 */
	private void setAbilities() 
	{

		unSortedabilityValues=new int[3];
		sortedAbilityValues=new int[3];
		
		strength = new int[4];
		strength[0]= calculateAbilities();
		
		constitution = new int[4];
		constitution[0]= calculateAbilities();
		
		dexterity = new int[4];
		dexterity[0]= calculateAbilities();
		
		unSortedabilityValues[0] = strength[0];
		unSortedabilityValues[1] = constitution[0];
		unSortedabilityValues[2] = dexterity[0];
		
		Arrays.sort(unSortedabilityValues);
		sortedAbilityValues=unSortedabilityValues;
	}

	/**
	 * Overridden Method that builds the strength
	 */
	void buildStrength() 
	{
		getFighter().setStrength(sortedAbilityValues[2]);
	}
	
	/**
	 * Overridden Method that builds the Constitution
	 */
	void buildConstitution() 
	{
		getFighter().setConstitution(sortedAbilityValues[1]);
	}
	
	/**
	 * Overridden Method that builds the Dexterity
	 */
	void buildDexterity() 
	{
		getFighter().setDexterity( sortedAbilityValues[0]);
	}
	
	/**
	 *  Method for calculating abilities using Dice class
	 * @return array random generated values 
	 */
	private int calculateAbilities() 
	{
		Dice dice=new Dice(4,6,3);
		
		return dice.getRollSum();
	}
	
}
