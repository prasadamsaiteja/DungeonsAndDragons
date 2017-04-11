package game.model.item.decoratorPattern;

import game.model.character.Character;

/**
 * This class is for decorating a weapon
 * @author Teja
 * @version 1.0.0
 */
public class WeaponDecorator implements Weapon {

    protected final Weapon weapon;
    
    /**
     * Default constructor for the weapon decorator
     * @param weapon weapon
     */
    public WeaponDecorator(Weapon weapon){
        this.weapon = weapon;
    }
    
    /**
	 * This method calculates the damage points of character
	 * @param character attacking char
	 * @return int damage points
	 */
    @Override
    public int damagePoints(Character character) {
        return weapon.damagePoints(character);
    }
}