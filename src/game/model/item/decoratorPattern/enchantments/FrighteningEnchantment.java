package game.model.item.decoratorPattern.enchantments;

import game.components.Console;
import game.model.character.Character;
import game.model.character.strategyPattern.strategies.FrighteningStrategy;
import game.model.item.decoratorPattern.Weapon;
import game.model.item.decoratorPattern.WeaponDecorator;
import game.views.jpanels.GamePlayScreen;

public class FrighteningEnchantment extends WeaponDecorator{

    private GamePlayScreen gamePlayScreen;
    private Character enemyCharacter, frightenedByCharacter;
    
    public FrighteningEnchantment(GamePlayScreen gamePlayScreen, Weapon decoratedWeapon, Character enenmyCharacter, Character frightenedByCharacter){        
        super(decoratedWeapon);
        this.gamePlayScreen = gamePlayScreen;
        this.enemyCharacter = enenmyCharacter;
        this.frightenedByCharacter = frightenedByCharacter;
        
    }
    
    @Override
    public int damagePoints(Character character) {
        this.enemyCharacter.pushMomentStrategy(new FrighteningStrategy(gamePlayScreen, this.enemyCharacter, frightenedByCharacter, character.getWeaponObject().getModifier()));
        if(frightenedByCharacter.isPlayer())
            Console.printInConsole("   => you have frightened a hostile monster(" + this.enemyCharacter.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");
        else
            Console.printInConsole("   => " + frightenedByCharacter.getName() + " has frightened a hostile monster(" + this.enemyCharacter.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");
        return super.damagePoints(character);
    }

}