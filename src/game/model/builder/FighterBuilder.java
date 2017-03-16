package game.model.builder;

public abstract class FighterBuilder {

	Fighter fighter;
	
	public Fighter getFighter()
	{
		return fighter;
	}
	
	public void createNewFighter()
	{
		fighter = new Fighter();
	}
	
	abstract void buildStrength();
	abstract void buildConstitution();
	abstract void buildDexterity();
	
}
