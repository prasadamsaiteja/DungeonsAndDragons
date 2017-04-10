package game.model.character.strategyPattern.strategies;

import game.views.jpanels.GamePlayScreen;
import game.components.Console;
import game.model.character.Character;
import game.model.character.strategyPattern.MomentStrategy;

public class FreezingStrategy implements MomentStrategy{
    
    private int freezeTurns;
    private Character character;
    private GamePlayScreen gamePlayScreen;
    
    public FreezingStrategy(GamePlayScreen gamePlayScreen, Character character, int freezeTurns) {
        this.character = character;
        this.freezeTurns = freezeTurns;
        this.gamePlayScreen = gamePlayScreen;
    }

    @Override
    public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void attack(int toRowNumber, int toColNumber) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pickItemsFromChest() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void playTurn() {
        
        if(freezeTurns > 0){
            Console.printInConsole("   => " + character.getName() + " has been freezed for this turn");
            freezeTurns--;
            
            if(freezeTurns == 0)
                character.popMomentStrategy();
        }
        
        gamePlayScreen.isTurnFinished = true;
    }

    @Override
    public void moveToNextMap() {
        

    }

    @Override
    public void addBorderIfRangedWeapon() {
        // TODO Auto-generated method stub
        
    }

}