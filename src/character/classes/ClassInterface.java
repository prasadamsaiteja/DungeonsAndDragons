package character.classes;

import GameComponents.Dice;
import GameComponents.DiceImplementationInterface;
import character.Character;

public interface ClassInterface extends DiceImplementationInterface {
	public void setCharacterObj(Character character);
	public void setLevel(int level);
	public int getHitScore(Dice dice);
}
