package game.model.builder;

/**
 * 
 * @author RahulReddy
 *
 */
public class FighterDriverClass {

	FighterDirector fighterDirector;
	Fighter selectedFighter ;
	FighterBuilder fighterType;
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
		
		selectedFighter = fighterDirector.getFighter();
	}
}
