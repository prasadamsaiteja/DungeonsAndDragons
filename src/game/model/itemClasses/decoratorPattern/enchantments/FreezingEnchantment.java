package game.model.itemClasses.decoratorPattern.enchantments;

import game.components.Console;
import game.model.character.Character;
import game.model.itemClasses.decoratorPattern.Weapon;
import game.model.itemClasses.decoratorPattern.WeaponDecorator;
import game.model.strategyPattern.strategies.FreezingStrategy;
import game.views.jpanels.GamePlayScreen;

public class FreezingEnchantment extends WeaponDecorator{
    
    private Character enemyCharacter;
    private GamePlayScreen gamePlayScreen;
    
    public FreezingEnchantment(GamePlayScreen gamePlayScreen, Weapon decoratedWeapon, Character enemyCharacter){
        super(decoratedWeapon);
        this.enemyCharacter = enemyCharacter;
        this.gamePlayScreen = gamePlayScreen;
    }
    
    @Override
    public int damagePoints(Character character) {
        
        enemyCharacter.pushMomentStrategy(new FreezingStrategy(gamePlayScreen, enemyCharacter, character.getWeaponObject().getModifier()));
        if(character.isPlayer())
            Console.printInConsole("   => you have freezed a hostile monster(" + enemyCharacter.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");
        else
            Console.printInConsole("   => " + character.getName() + " has freezed a hostile monster(" + enemyCharacter.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");        
        
        return super.damagePoints(character);
    }

}