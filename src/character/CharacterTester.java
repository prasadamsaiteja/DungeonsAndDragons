package character;

import GameComponents.Dice;

public class CharacterTester {
	
	public static void main(String[] args)
	{
		Dice d = new Dice(4,6,3);
		System.out.println(d.getRollSum());
	}

}
