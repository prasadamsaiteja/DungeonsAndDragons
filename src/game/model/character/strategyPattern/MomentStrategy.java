package game.model.character.strategyPattern;

public interface MomentStrategy {

    public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber);
    public void attack(int toRowNumber, int toColNumber);
    public void pickItemsFromChest();
    public void playTurn();
    public void moveToNextMap();
    
}
