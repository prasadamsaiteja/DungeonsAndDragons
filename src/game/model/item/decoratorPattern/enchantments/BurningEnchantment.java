package game.model.item.decoratorPattern.enchantments;

import game.components.Console;
import game.model.character.Character;
import game.model.item.decoratorPattern.Weapon;
import game.model.item.decoratorPattern.WeaponDecorator;

/**
 * This Enchantment have the effect on the item 
 * @author RahulReddy
 * @version 1.0.0
 */
public class BurningEnchantment extends WeaponDecorator{

    private Character enemyCharacter;
    
    public BurningEnchantment(Weapon decoratedWeapon, Character enemyCharacter){
        super(decoratedWeapon);
        this.enemyCharacter = enemyCharacter;
    }
    
    @Override
    public int damagePoints(Character character) {
        enemyCharacter.startBurning(character.getWeaponObject().getModifier() * 5);
        if(character.isPlayer())
            Console.printInConsole("   => you have burned a hostile monster(" + enemyCharacter.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");
        else
            Console.printInConsole("   => " + character.getName() + " has burned a hostile monster(" + enemyCharacter.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");
        return super.damagePoints(character);
    }

}