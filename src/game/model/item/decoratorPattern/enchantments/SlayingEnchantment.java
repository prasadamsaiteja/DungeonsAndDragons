package game.model.item.decoratorPattern.enchantments;

import game.model.character.Character;
import game.model.item.decoratorPattern.Weapon;
import game.model.item.decoratorPattern.WeaponDecorator;

/**
 * This Enchantment have the Slaying effect on the item 
 * @author RahulReddy
 * @version 1.0.0
 */
public class SlayingEnchantment extends WeaponDecorator{

    private Character enemeyCharacter;
    
    /**
     * Constructor for the Slaying effect
     * @param decoratedWeapon added effects on weapon
     * @param enemeyCharacter opposition character
     */
    public SlayingEnchantment(Weapon decoratedWeapon, Character enemeyCharacter){
        super(decoratedWeapon);
        this.enemeyCharacter = enemeyCharacter;        
    }
    
    @Override
    public int damagePoints(Character character) {
        int weaponDamage = super.damagePoints(character);  
        return (enemeyCharacter.getHitScore() - weaponDamage) + weaponDamage; //Adding addtional hitpoints to damage to competely kill the oponent
    }

}