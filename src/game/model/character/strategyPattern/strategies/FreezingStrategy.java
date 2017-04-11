package game.model.character.strategyPattern.strategies;

import game.views.jpanels.GamePlayScreen;
import game.components.Console;
import game.model.character.Character;
import game.model.character.strategyPattern.MomentStrategy;

/**
 * This class is for  player that is frozen by sword
 * @author teja
 * @version 1.0.0
 */
public class FreezingStrategy implements MomentStrategy{
    
    private int freezeTurns;
    private Character character;
    private GamePlayScreen gamePlayScreen;
    
    /**
     * Default constructor for the player that is frozen
     * @param gamePlayScreen screen play
     * @param character player
     * @param freezeTurns turns count
     */
    public FreezingStrategy(GamePlayScreen gamePlayScreen, Character character, int freezeTurns) {
        this.character = character;
        this.freezeTurns = freezeTurns;
        this.gamePlayScreen = gamePlayScreen;
    }

    /**
     * This method moves the player according the action performed
     * @param message action performed by the character
     * @param fromRowNumber initial row position
     * @param fromColNumber initial col position
     * @param toRowNumber  final row position
     * @param toColNumber final col position
     */
    @Override
    public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber) {
        // TODO Auto-generated method stub
        
    }

    /**
     * This method is for attacking character 
     * @param toRowNumber  final row position
     * @param toColNumber final col position
     */
    @Override
    public void attack(int toRowNumber, int toColNumber) {
        // TODO Auto-generated method stub
        
    }

    /**
     * This method pick Items From Chest
     */
    @Override
    public void pickItemsFromChest() {
        // TODO Auto-generated method stub
        
    }

    /**
     * This method is for playing respective turn
     */
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

    /**
     * This method changes the map if the player completes the current map
     */
    @Override
    public void moveToNextMap() {
        

    }

    /**
     * This method adds Border If Ranged Weapon
     */
    @Override
    public void addBorderIfRangedWeapon() {
        // TODO Auto-generated method stub
        
    }

}