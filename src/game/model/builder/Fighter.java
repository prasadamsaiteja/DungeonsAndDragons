package game.model.builder;

/**
 * 
 * @author RahulReddy
 *
 */
public class Fighter {

	private int strength, constitution, dexterity;

	public void setStrength(int strength)
	{
		this.strength = strength;
	}

	public void setConstitution(int constitution) 
	{
		this.constitution = constitution;
	}

	public void setDexterity(int dexterity) 
	{
		this.dexterity = dexterity;
	}
	
	public int getStrength()
	{
		return strength;
	}
	public int getConstitution()
	{
		return constitution;
	}
	public int getDexterity()
	{
		return dexterity;
	}

}
