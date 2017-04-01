package game.model.strategyPattern;

import game.components.Console;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.Item;
import game.model.character.Character;
import game.views.jpanels.GamePlayScreen;

public class AggresiveNPC implements MomentStrategy{
    
    private Character character;
    private GamePlayScreen gamePlayScreen;
    boolean isAttackPerformed = false;
    
    public AggresiveNPC(GamePlayScreen gamePlayScreen, Character character) {
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
            
            if((besidePlayer.isPlayer() || besidePlayer.getIsFriendlyMonster()) && isAttackPerformed == false)
                attack(fromRowNumber, fromColNumber, toRowNumber, toColNumber);
        }
                    
    }

    @Override
    public void attack(int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber) {
        
        Character besidePlayer = (Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];
        if(!besidePlayer.isPlayer())
            besidePlayer.hit(character.getAttackBonus());
        else
            gamePlayScreen.character.hit(character.getAttackBonus());
        
        isAttackPerformed = true;
        if(!besidePlayer.isPlayer())
            Console.printInConsole("   => " + character.getName() + " hit a friendly character(" + besidePlayer.getName() + ") with " + character.getAttackBonus() + " Attack power");
        else
            Console.printInConsole("   => " + character.getName() + " has hitted you with " + character.getAttackBonus() + " Attack power");
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
            int[] playerLocation = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
            
            int horizontalDistance = playerLocation[0] - characterLocation[0];
            int verticalDistance = playerLocation[1] - characterLocation[1];
            
            if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
                String message = "   => " + character.getName() + " moving left";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
            }
            
            else if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + character.getName() + " moving right";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
            }
                
            else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] - 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + character.getName() + " moving up";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] - 1), characterLocation[1]);
            }
                
            else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] + 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + character.getName() + " moving down";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] + 1), characterLocation[1]);
            }
            
            else if(verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
                String message = "   => " + character.getName() + " moving left";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
            }
            
            else if(verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + character.getName() + " moving right";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
            }
            
            try { Thread.sleep(800); } catch(InterruptedException ignored) {}                           
        }        
        
        gamePlayScreen.isTurnFinished = true;
    }

}
