package game.model.builder;

/**
 * Main Driver class for accessing the fighter object loaded with 
 * @author RahulReddy
 * @version 1.0.0
 */
public class FighterDriverClass {

	public FighterDirector fighterDirector;
	private FighterBuilder fighterType;
	
	/**
	 * Constructor that checks for the fighter type
	 * @param fighterName Name of the fighter
	 */
	public FighterDriverClass(String fighterName)
	{
		if(fighterName.equals("Bully"))
		{
			fighterType = new BullyFighterBuilder();
		}
		else if (fighterName.equals("Tank"))
		{
			fighterType = new TankFighterBuilder();
		}
		else if(fighterName.equals("Nimble"))
		{
			fighterType = new NimbleFighterBuilder();
		}
		
		fighterDirector = new FighterDirector();
		fighterDirector.setFb(fighterType);
		fighterDirector.constructFighter();
	}
}
