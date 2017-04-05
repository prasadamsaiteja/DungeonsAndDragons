package game.model.character.builderPattern;

import game.model.character.Character;

/**
 * Model Class for Fighter
 * @author RahulReddy
 * @version 1.0.0
 */
public class Fighter extends Character
{

	/**
	 * Sets the strength of the fighter
	 * @param strength  strength value
	 * @return Character instance of character
	 */
	public Character setStrength(int strength)
	{
	     return super.setStrength(strength);	     
	}

	/**
	 * Sets the constitution of the fighter
	 * @param constitution  constitution value
         * @return Character instance of character
	 */
	public Character setConstitution(int constitution) 
	{
	    return super.setConstitution(constitution);
	}

	/**
	 * Sets the dexterity of the fighter
	 * @param dexterity  dexterity value
         * @return Character instance of character
	 */
	public Character setDexterity(int  dexterity) 
	{
	    return super.setDexterity(dexterity);
	}
	
	/**
	 * This method return the strength of the fighter
	 * @return strength  strength value
	 */
	public int  getStrength()
	{
	    return super.getStrength();
	}

	/**
	 * Thos method the constitution of the fighter
	 * @return constitution  constitution value
	 */
	public int  getConstitution()
	{
	    return super.getConstitution();
	}

	/**
	 * Returns the dexterity of the fighter
	 * @return dexterity  dexterity value
	 */
	public int  getDexterity()
	{
	    return super.getDexterity();
	}

}
