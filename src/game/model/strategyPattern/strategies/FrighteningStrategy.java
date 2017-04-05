package game.model.strategyPattern.strategies;

import game.components.Console;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.character.Character;
import game.model.strategyPattern.MomentStrategy;
import game.views.jpanels.GamePlayScreen;

public class FrighteningStrategy implements MomentStrategy{

    private GamePlayScreen gamePlayScreen;
    private Character character, frightenedByCharacter;
    private int frightenedTurns;
    
    public FrighteningStrategy(GamePlayScreen gamePlayScreen, Character character, Character frightenedByCharacter, int frightenedTurns) {
        this.gamePlayScreen = gamePlayScreen;
        this.character = character;
        this.frightenedByCharacter = frightenedByCharacter;
        this.frightenedTurns = frightenedTurns;
    }
    
    @Override
    public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber) {
        
        if(!(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character)){            
            Object tempPreviousMapCellObject = gamePlayScreen.previousMapCellObject;
            gamePlayScreen.previousMapCellObject = gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];             
            gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] = character;
            gamePlayScreen.currentMap.mapData[fromRowNumber][fromColNumber] = tempPreviousMapCellObject;                          
            Console.printInConsole(message);                        
            gamePlayScreen.repaintMap(); 
        }  
        
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
        
        if(frightenedTurns > 0){
            
            for(int index = 0; index < 3; index++){
                
                int[] characterLocation = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, character);
                int[] playerLocation = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, frightenedByCharacter);
                
                int horizontalDistance = playerLocation[0] - characterLocation[0];
                int verticalDistance = playerLocation[1] - characterLocation[1];
                
                if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){                
                    String message = "   => " + character.getName() + " is frightened by " + frightenedByCharacter.getName() + " and moving left";
                    movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
                }
                
                else if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){
                    String message = "   => " + character.getName() + " is frightened by " + frightenedByCharacter.getName() + " and moving right";
                    movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
                }
                    
                else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] + 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                    String message = "   => " + character.getName() + " is frightened by " + frightenedByCharacter.getName() + " and moving up";
                    movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] + 1), characterLocation[1]);
                }
                    
                else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] - 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                    String message = "   => " + character.getName() + " is frightened by " + frightenedByCharacter.getName() + " and moving down";
                    movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] - 1), characterLocation[1]);
                }
                
                else if(verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
                    String message = "   => " + character.getName() + " is frightened by " + frightenedByCharacter.getName() + " and moving left";
                    movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
                }
                
                else if(verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
                    String message = "   => " + character.getName() + " is frightened by " + frightenedByCharacter.getName() + " and moving right";
                    movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
                }
                
                try { Thread.sleep(800); } catch(InterruptedException ignored) {}                           
            }      
            
            frightenedTurns--;
            
            if(frightenedTurns == 0)
                character.popMomentStrategy();
        }
        
        gamePlayScreen.isTurnFinished = true;
        
    }

    @Override
    public void moveToNextMap() {
        // TODO Auto-generated method stub
        
    }

}
