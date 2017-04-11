package game.model.item.decoratorPattern;

import game.components.Dice;
import game.model.character.Character;

/**
 * This class is for Melee Weapon calculating damage points
 * @author teja
 *@version 1.0.0 
 */
public class RangedWeapon implements Weapon {

	/**
	 * This method calculates the damage points of character
	 * @param character attacking char
	 * @return int damage points
	 */
    @Override
    public int damagePoints(Character character) {
        return (new Dice(1, 8, 1)).getRollSum();
    }

}
