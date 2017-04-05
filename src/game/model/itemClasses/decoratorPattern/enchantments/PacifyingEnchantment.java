package game.model.itemClasses.decoratorPattern.enchantments;

import game.model.character.Character;
import game.model.itemClasses.decoratorPattern.Weapon;
import game.model.itemClasses.decoratorPattern.WeaponDecorator;
import game.model.strategyPattern.strategies.FriendlyNPC;
import game.views.jpanels.GamePlayScreen;

public class PacifyingEnchantment extends WeaponDecorator{
    
    private Character enemyCharacter;
    private GamePlayScreen gamePlayScreen;
    
    public PacifyingEnchantment(Weapon decoratedWeapon, Character enemyCharacter, GamePlayScreen gamePlayScreen){
        super(decoratedWeapon);
        this.enemyCharacter = enemyCharacter;
        this.gamePlayScreen = gamePlayScreen;
    }
    
    @Override
    public int damagePoints(Character character) {
        enemyCharacter.setMomentStrategy(new FriendlyNPC(gamePlayScreen, enemyCharacter));
        return super.damagePoints(character);
    }

}