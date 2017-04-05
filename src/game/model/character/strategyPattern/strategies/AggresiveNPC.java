package game.model.character.strategyPattern.strategies;

import game.components.Console;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.character.Character;
import game.model.character.strategyPattern.MomentStrategy;
import game.model.item.Item;
import game.model.item.decoratorPattern.MeleeWeapon;
import game.model.item.decoratorPattern.RangedWeapon;
import game.model.item.decoratorPattern.WeaponDecorator;
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
        
        if(!(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character)){
                        
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
                attack(toRowNumber, toColNumber);
        }
                    
    }

    @Override
    public void attack(int toRowNumber, int toColNumber) {
        
        int attackPoints = character.attackPoint();
        
        if(attackPoints >= ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass()){
            
            int damagePoints;
            if(character.getWeaponObject().getItemType().equalsIgnoreCase("Melee"))
                damagePoints = new WeaponDecorator(new MeleeWeapon()).damagePoints(character);
            else
                damagePoints = new WeaponDecorator(new RangedWeapon()).damagePoints(character);
            
            isAttackPerformed = true;
            if(((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).isPlayer()){
                gamePlayScreen.character.hit(damagePoints);
                Console.printInConsole("   => " + character.getName() + " hitted you with " + damagePoints + " damage points");    
            }
            
            else{
                ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).hit(damagePoints);
                Console.printInConsole("   => " + character.getName() + " hitted a friendly monster (" + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + ") with " + damagePoints + " damage points");
            }                
        }
        
        else if(((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).isPlayer())
            Console.printInConsole("   => " + character.getName() + " missed hitting you (" + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass() + " armor class) with " + attackPoints + " attack points");
        else
            Console.printInConsole("   => " + character.getName() + " missed hitting a friendly monster (" + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + " - "+ ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass() + " armor class) with " + attackPoints + " attack points");
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

    @Override
    public void moveToNextMap() {
        // TODO Auto-generated method stub
        
    }

}
