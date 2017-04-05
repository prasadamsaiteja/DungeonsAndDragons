package game.model.character.builderPattern.builder;

import game.components.Dice;
import game.model.character.builderPattern.FighterBuilder;

import java.util.Arrays;

/**
 * Class that sets the abilities for the Tank Class
 * @author RahulReddy
 * @version 1.0.0
 */
public class TankFighterBuilder extends FighterBuilder
{

	int strength[],constitution[],dexterity[];
	int strength2,constitution2,dexterity2;
	int[] unSortedabilityValues;
	int[] sortedAbilityValues;
	
	/**
	 * Constructor for Tank
	 */	
	public TankFighterBuilder() 
	{
		setAbilities();		
	}
	
	/**
	 * Method that sets the abilities of the Tank Class
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
		
		unSortedabilityValues[0] = constitution[0];
		unSortedabilityValues[1] = dexterity[0];
		unSortedabilityValues[2] = strength[0];
		
		Arrays.sort(unSortedabilityValues);
		sortedAbilityValues=unSortedabilityValues;
	}

	/**
	 * Overriden Method that builds the strength
	 */
	protected void buildStrength() 
	{
		getFighter().setStrength(sortedAbilityValues[0]);
	}
	
	/**
	 * Overriden Method that builds the Constitution
	 */
	protected void buildConstitution() 
	{
		getFighter().setConstitution(sortedAbilityValues[2]);
	}
	
	/**
	 * Overriden Method that builds the Dexterity
	 */
	protected void buildDexterity() 
	{
		getFighter().setDexterity(sortedAbilityValues[1]);
	}

	/**
	 *  Method for calculating abilities
	 * @return array random generated values 
	 */
	private int calculateAbilities() 
	{
		Dice dice=new Dice(4,6,3);
	
		return dice.getRollSum();
	}

}
