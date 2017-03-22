package game.model.builder;

/**
 * Director Class for the Fighter
 * @author RahulReddy
 * @version 1.0.0
 */
public class FighterDirector 
{

	private FighterBuilder fb;

	/**
	 * This Method constructs the fighter and builds the abilities
	 */
	public void constructFighter() 
	{
		fb.createNewFighter();
		fb.buildConstitution();
		fb.buildDexterity();
		fb.buildStrength();
	}
	
	/**
	 * Returns the selected Fighter
	 * @return Fighter fighter Object 
	 */
	public Fighter getFighter() 
	{
		return fb.getFighter();
	}

	/**
	 * Sets the selected Fighter builder
	 * @param fighterbuilder object
	 */
	public void setFb(FighterBuilder fighterbuilder) 
	{
		this.fb = fighterbuilder;
	}		
	
}
