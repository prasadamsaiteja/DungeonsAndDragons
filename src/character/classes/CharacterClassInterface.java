package character.classes;

import GameComponents.Dice;
import GameComponents.DiceImplementationInterface;
import character.Character;

public interface CharacterClassInterface extends DiceImplementationInterface {
	public void setCharacterObj(Character character);
	public int getHitScore(Dice dice);
}
