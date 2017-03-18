package game.model.builder;

/**
 * Model Class for Fighter
 * @author RahulReddy
 *
 */
public class Fighter {

	private int strength, constitution, dexterity;

	/**
	 * Sets the strength of the fighter
	 * @param strength  strength value
	 */
	public void setStrength(int strength)
	{
		this.strength = strength;
	}

	/**
	 * Sets the constitution of the fighter
	 * @param constitution  constitution value
	 */
	public void setConstitution(int constitution) 
	{
		this.constitution = constitution;
	}

	/**
	 * Sets the dexterity of the fighter
	 * @param dexterity  dexterity value
	 */
	public void setDexterity(int  dexterity) 
	{
		this.dexterity = dexterity;
	}
	
	/**
	 * Return the strength of the fighter
	 * @return strength  strength value
	 */
	public int  getStrength()
	{
		return strength;
	}

	/**
	 * Return the constitution of the fighter
	 * @return constitution  constitution value
	 */
	public int  getConstitution()
	{
		return constitution;
	}

	/**
	 * Returns the dexterity of the fighter
	 * @return dexterity  dexterity value
	 */
	public int  getDexterity()
	{
		return dexterity;
	}

}
