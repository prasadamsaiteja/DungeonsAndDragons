package game.model.itemClasses.decoratorPattern.enchantments;

import game.components.Console;
import game.model.character.Character;
import game.model.itemClasses.decoratorPattern.Weapon;
import game.model.itemClasses.decoratorPattern.WeaponDecorator;
import game.model.strategyPattern.strategies.FrighteningStrategy;
import game.views.jpanels.GamePlayScreen;

public class FrighteningEnchantment extends WeaponDecorator{

    private GamePlayScreen gamePlayScreen;
    private Character character, frightenedByCharacter;
    
    public FrighteningEnchantment(GamePlayScreen gamePlayScreen, Weapon decoratedWeapon, Character character, Character frightenedByCharacter){
        super(decoratedWeapon);
        this.gamePlayScreen = gamePlayScreen;
        this.character = character;
        this.frightenedByCharacter = frightenedByCharacter;
        
    }
    
    @Override
    public int damagePoints(Character character) {
        this.character.pushMomentStrategy(new FrighteningStrategy(gamePlayScreen, this.character, frightenedByCharacter, character.getWeaponObject().getModifier()));
        if(frightenedByCharacter.isPlayer())
            Console.printInConsole("   => you have frightened a hostile monster(" + this.character.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");
        else
            Console.printInConsole("   => " + frightenedByCharacter.getName() + " has frightened a hostile monster(" + this.character.getName() + ") for " + character.getWeaponObject().getModifier() + " turns");
        return super.damagePoints(character);
    }

}