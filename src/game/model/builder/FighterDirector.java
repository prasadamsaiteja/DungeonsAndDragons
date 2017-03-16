package game.model.builder;

/**
 * 
 * @author RahulReddy
 *
 */
public class FighterDirector {

	private FighterBuilder fb;

	public void constructFighter() 
	{
		fb.createNewFighter();
		fb.buildConstitution();
		fb.buildDexterity();
		fb.buildStrength();
	}

	public Fighter getFighter() 
	{
		return fb.getFighter();
	}

	public void setFb(FighterBuilder fighterbuilder) 
	{
		this.fb = fighterbuilder;
	}

}
