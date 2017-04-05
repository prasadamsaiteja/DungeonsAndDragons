package game.model.itemClasses.decoratorPattern.enchantments;

import game.model.character.Character;
import game.model.itemClasses.decoratorPattern.Weapon;
import game.model.itemClasses.decoratorPattern.WeaponDecorator;

public class SlayingEnchantment extends WeaponDecorator{

    private Character enemeyCharacter;
    
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