package game.model.item.decoratorPattern.enchantments;

import game.components.Console;
import game.model.character.Character;
import game.model.character.strategyPattern.strategies.FriendlyNPC;
import game.model.item.decoratorPattern.Weapon;
import game.model.item.decoratorPattern.WeaponDecorator;
import game.views.jpanels.GamePlayScreen;

/**
 * This Enchantment have the Pacifying effect on the item 
 * @author RahulReddy
 * @version 1.0.0
 */
public class PacifyingEnchantment extends WeaponDecorator{
    
    private Character enemyCharacter;
    private GamePlayScreen gamePlayScreen;
    
    /**
     * Constructor for the Pacifying effect
     * 
     * @param decoratedWeapon added effects on weapon
     * @param enemyCharacter opposition character
     * @param gamePlayScreen screen play
     */
    public PacifyingEnchantment(Weapon decoratedWeapon, Character enemyCharacter, GamePlayScreen gamePlayScreen){
        super(decoratedWeapon);
        this.enemyCharacter = enemyCharacter;
        this.gamePlayScreen = gamePlayScreen;
    }
    
    /**
     *This method calculates damage points
     *@param character enemy character
     *@return int damage points
     */
    @Override
    public int damagePoints(Character character) {
        enemyCharacter.setFriendlyMonsterFlag(true);
        enemyCharacter.setMomentStrategy(new FriendlyNPC(gamePlayScreen, enemyCharacter));
        if(character.isPlayer())
            Console.printInConsole("   => you have pacified a hostile monster(" + this.enemyCharacter.getName() + ")");
        else
            Console.printInConsole("   => " + character.getName() + " has pacified a hostile monster(" + this.enemyCharacter.getName() + ")");
        return super.damagePoints(character);
    }

}