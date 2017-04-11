package game.model.character.strategyPattern;

/**
 * This interface is for strategy to players
 * @author teja
 * @version 1.0.0
 */
public interface MomentStrategy {

	 /**
     * This method moves the player according the action performed
     * @param message action performed by the character
     * @param fromRowNumber initial row position
     * @param fromColNumber initial col position
     * @param toRowNumber  final row position
     * @param toColNumber final col position
     */
    public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber);
    
    /**
     * This method is for attacking character 
     * @param toRowNumber  final row position
     * @param toColNumber final col position
     */
    public void attack(int toRowNumber, int toColNumber);
    
    /**
     * This method pick Items From Chest
     */
    public void pickItemsFromChest();
   
    /**
     * This method is for playing respective turn
     */
    public void playTurn();
    
    /**
     * This method changes the map if the player completes the current map
     */
    public void moveToNextMap();
    
    /**
     * This method adds Border If Ranged Weapon
     */
    public void addBorderIfRangedWeapon();
}
