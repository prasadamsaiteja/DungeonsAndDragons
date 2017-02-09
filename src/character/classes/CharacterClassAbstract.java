package character.classes;

import GameComponents.Dice;
import character.Character;

abstract public class CharacterClassAbstract implements CharacterClassInterface {
	
	protected int numberOfRolls = 0;
	protected int diceSides = 0;
	protected Character character;
	
	@Override
	public void setCharacterObj(Character character){
		this.character = character;
	}
	
	@Override
	public int getNumberOfRolls(){
		return this.numberOfRolls;
	}

	@Override
	public int getDiceSides() {
		return this.diceSides;
	}

	@Override
	public int getHitScore(Dice dice) {
		int hitPoints = 0;
		
		hitPoints += this.character.getConstitution() * this.character.getLevel();
		
		if (this.character.getLevel() > 1){
			int diceRoll = dice.getRollSum();
			hitPoints += diceRoll; 
		}else{
			hitPoints += this.getDiceSides();
		}
		
		System.out.println("calculated hit points: " + hitPoints);
		
		return hitPoints;
	}
}
