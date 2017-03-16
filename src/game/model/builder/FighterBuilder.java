package game.model.builder;

/**
 * Builder class that creates specific fighter 
 * @author RahulReddy
 * @version 1.0.0
 */
public abstract class FighterBuilder {

	private Fighter fighter;
	
	/**
	 * Returns the selected Fighter
	 * @return Fighter fighter Object 
	 */
	public Fighter getFighter()
	{
		return fighter;
	}
	
	/**
	 * Creates a new fighter
	 */
	public void createNewFighter()
	{
		fighter = new Fighter();
	}
	
	/**
	 * Abstract method for building Strength
	 */
	abstract void buildStrength();
	/**
	 * Abstract method for building Constitution
	 */
	abstract void buildConstitution();
	/**
	 * Abstract method for building  Dexterity
	 */
	abstract void buildDexterity();
	
}
