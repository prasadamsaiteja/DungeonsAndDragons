package game.model.strategyPattern;

import java.util.Random;

import game.components.Console;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.Item;
import game.model.character.Character;
import game.views.jpanels.GamePlayScreen;

public class FriendlyNPC implements MomentStrategy{

    private Character character;
    private GamePlayScreen gamePlayScreen;
    boolean isAttackPerformed = false;
    
    public FriendlyNPC(GamePlayScreen gamePlayScreen, Character character) {
        this.gamePlayScreen = gamePlayScreen;
        this.character = character;
    }

    @Override
    public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber) {
        
        if(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character && ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getHitScore() < 1){
            
            Object tempPreviousMapCellObject = gamePlayScreen.previousMapCellObject;
            gamePlayScreen.previousMapCellObject = gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];             
            gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] = character;
            gamePlayScreen.currentMap.mapData[fromRowNumber][fromColNumber] = tempPreviousMapCellObject;                          
            Console.printInConsole(message);
            
            if(gamePlayScreen.previousMapCellObject instanceof Item && character.backpack.backpackItems.size() < 10)
                pickItemsFromChest();
                        
            gamePlayScreen.repaintMap(); 
        }  
        
        else if(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character){
            
            Character besidePlayer = (Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];
                        
            if((!besidePlayer.getIsFriendlyMonster() && !besidePlayer.isPlayer()) && isAttackPerformed == false)
                attack(fromRowNumber, fromColNumber, toRowNumber, toColNumber);
        }
        
    }

    @Override
    public void attack(int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber) {
        
        Character besidePlayer = (Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];        
        besidePlayer.hit(character.getAttackBonus());
        
        isAttackPerformed = true;
        Console.printInConsole("   => " + character.getName() + " hit a hostile character(" + besidePlayer.getName() + ") with " + character.getAttackBonus() + " Attack power");        
    }

    @Override
    public void pickItemsFromChest() {
        
        Item item = (Item) gamePlayScreen.previousMapCellObject;                                                    
        if(character.items.containsKey(item.itemType) && character.items.get(item.itemType) != null)
            character.backpack.backpackItems.put(item.itemType, item.itemName);                            
        else    
            character.items.put(item.itemType, item.itemName);
                           
        gamePlayScreen.previousMapCellObject = new String(SharedVariables.DEFAULT_CELL_STRING);  
        Console.printInConsole("   => " + character.getName() + " has collected a " + item.getItemType() + "(" + item.getItemName() + ") from the chest");
        character.draw();
    }

    @Override
    public void playTurn() {
        
        isAttackPerformed = false;
        
        for(int index = 0; index < 3; index++){
            
            int[] characterLocation = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, character);
            int randomMoment = (new Random()).nextInt((4 - 1) + 1) + 1;
            boolean isMomentSuccessfull = true;
            
            try{                
                switch (randomMoment) {
                    
                    case 1:
                        if(!gamePlayScreen.currentMap.mapData[characterLocation[0] - 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                            String message = "   => " + character.getName() + " moving up";
                            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] - 1), characterLocation[1]);
                        }
                        else
                            isMomentSuccessfull = false;
                            
                        break;

                    case 2:
                        if(!gamePlayScreen.currentMap.mapData[characterLocation[0] + 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                            String message = "   => " + character.getName() + " moving down";
                            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] + 1), characterLocation[1]);
                        }
                        else
                            isMomentSuccessfull = false;
                        
                        break;
                        
                    case 3:
                        if(!gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){
                            String message = "   => " + character.getName() + " moving left";
                            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
                        }
                        else
                            isMomentSuccessfull = false;
                        
                        break;
                        
                    case 4:
                        if(!gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
                            String message = "   => " + character.getName() + " moving right";
                            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
                        }
                        else
                            isMomentSuccessfull = false;
                        break;                    
                }
            }
            
            catch(ArrayIndexOutOfBoundsException ex){
                isMomentSuccessfull = false;
            }
            
            if(isMomentSuccessfull)
                try { Thread.sleep(800); } catch(InterruptedException ignored) {}
            
            if(isMomentSuccessfull == false)
                index--;
        }
        
        gamePlayScreen.isTurnFinished = true;
    }

}
