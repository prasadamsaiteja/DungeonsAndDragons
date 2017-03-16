package game.model.builder;

import game.components.Dice;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that sets the abilities for the Bully Class
 * @author RahulReddy
 *
 */
public class BullyFighterBuilder extends FighterBuilder {

	Fighter bullyType;
	double strength[],constitution[],dexterity[];
	double strength2,constitution2,dexterity2;
	double[] unSortedabilityValues;
	double[] sortedAbilityValues;
		
	/**
	 * Constructor for Bully
	 */
	public BullyFighterBuilder() {
		this.bullyType = new Fighter();
		setAbilities();		
	}
	
	/**
	 * Method that sets the abilities of the Bully Class
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
		
		unSortedabilityValues[0] = strength[0];
		unSortedabilityValues[1] = constitution[0];
		unSortedabilityValues[2] = dexterity[0];
		
		Arrays.sort(unSortedabilityValues);
		sortedAbilityValues=unSortedabilityValues;
	}

	/**
	 * Overriden Method that builds the strength
	 */
	void buildStrength() {
		bullyType.setStrength((int) sortedAbilityValues[2]);
	}
	
	/**
	 * Overriden Method that builds the Constitution
	 */
	void buildConstitution() {
		bullyType.setConstitution((int) sortedAbilityValues[1]);
	}
	
	/**
	 * Overriden Method that builds the Dexterity
	 */
	void buildDexterity() {
		bullyType.setDexterity((int) sortedAbilityValues[0]);
	}
	

	/**
	 *  Method for calculating abilities
	 * @return array random generated values 
	 */
	private double calculateAbilities() {
		
		//for(int i=0 ; i < 4 ; i++) ability[i] = Math.random() * 6 + 1;
		//return ability[0]=(ability[0]+ability[1]+ability[2]+ability[3]);
		Dice dice=new Dice(4,6,3);
		return dice.getRollSum();
	}
	
}
