package game.model.item.decoratorPattern.enchantments;

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
        enemyCharacter.setMomentStrategy(new FriendlyNPC(gamePlayScreen, enemyCharacter));
        return super.damagePoints(character);
    }

}