package character.classes;

import GameComponents.Dice;
import character.Character;

public class Fighter implements ClassInterface {

	private int numberOfRolls = 1;
	private int diceSides = 10;
	private int level = 1;
	private Character character;
	
	@Override
	public void setCharacterObj(Character character){
		this.character = character;
	}
	
	@Override
	public void setLevel(int level){
		this.level = level;
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
		
		if (this.level > 1){
			int diceRoll = dice.getRollSum();
			hitPoints += diceRoll; 
		}else{
			hitPoints += 10;
		}
		
		System.out.println("calculated hit points: " + hitPoints);
		
		return hitPoints;
	}
}
