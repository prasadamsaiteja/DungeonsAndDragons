package game.model.character.builderPattern;

import game.model.character.builderPattern.builder.BullyFighterBuilder;
import game.model.character.builderPattern.builder.NimbleFighterBuilder;
import game.model.character.builderPattern.builder.TankFighterBuilder;

/**
 * Main Driver class for accessing the fighter object loaded with 
 * @author RahulReddy
 * @version 1.0.0
 */
public class FighterDriverClass 
{

	/**
	 * Reference for fighter director
	 */
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
	
	/**
	 * This method returns the fighter type
	 * @return Fighter  fighter object
	 */
	public Fighter getFighter(){
		
		return fighterDirector.getFighter();
	}
	
	/**
	 * This method returns the fighter director type
	 * @return FighterDirector  fighterDirector  object
	 */
	public FighterDirector getFighterDirector(){
		
		return fighterDirector;
	}
		
		
}
