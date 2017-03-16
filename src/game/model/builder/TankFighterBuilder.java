package game.model.builder;

import game.components.Dice;

import java.util.Arrays;

/**
 * 
 * @author RahulReddy
 *
 */
public class TankFighterBuilder extends FighterBuilder{

	Fighter tankType;
	double strength[],constitution[],dexterity[];
	double strength2,constitution2,dexterity2;
	double[] unSortedabilityValues;
	double[] sortedAbilityValues;
	
	/**
	 * Constructor for Tank
	 */	
	public TankFighterBuilder() {
		this.tankType = new Fighter();
		setAbilities();		
	}
	
	/**
	 * Method that sets the abilities of the Tank Class
	 */
	private void setAbilities() {

		unSortedabilityValues=new double[3];
		sortedAbilityValues=new double[3];
		
		strength = new double[4];
		strength[0]= calculateAbilities();
		
		constitution = new double[4];
		constitution[0]= calculateAbilities();
		
		dexterity = new double[4];
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
	void buildStrength() {
		tankType.setStrength((int) sortedAbilityValues[0]);
	}
	
	/**
	 * Overriden Method that builds the Constitution
	 */
	void buildConstitution() {
		tankType.setConstitution((int) sortedAbilityValues[2]);
	}
	
	/**
	 * Overriden Method that builds the Dexterity
	 */
	void buildDexterity() {
		tankType.setDexterity((int) sortedAbilityValues[1]);

	}

	/**
	 *  Method for calculating abilities
	 * @return array random generated values 
	 */
	private double calculateAbilities() {
		Dice dice=new Dice(4,6,3);
		return dice.getRollSum();
		}

}
