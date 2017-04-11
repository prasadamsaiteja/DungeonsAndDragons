package game.model.item.decoratorPattern;

import game.model.character.Character;

/**
 * Interface for the weapon
 */
public interface Weapon {

	/**
	 * This method calculates the damage points of character
	 * @param character attacking char
	 * @return int damage points
	 */
    public int damagePoints(Character character);   
    
}
