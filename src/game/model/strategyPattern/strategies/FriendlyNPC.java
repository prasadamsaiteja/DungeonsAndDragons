package game.model.strategyPattern.strategies;

import java.util.Random;

import game.components.Console;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.character.Character;
import game.model.itemClasses.Item;
import game.model.itemClasses.decoratorPattern.MeleeWeapon;
import game.model.itemClasses.decoratorPattern.RangedWeapon;
import game.model.itemClasses.decoratorPattern.WeaponDecorator;
import game.model.strategyPattern.MomentStrategy;
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
                        
            if((!besidePlayer.getIsFriendlyMonster() && !besidePlayer.isPlayer()) && isAttackPerformed == false)
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
            ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).hit(damagePoints);
            Console.printInConsole("   => " + character.getName() + " hitted " + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + " with " + damagePoints + " damage points");
        }

        Console.printInConsole("   => " + character.getName() + " missed hitting a hostile monster (" + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + " - "+ ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass() + " armor class) with " + attackPoints + " attack points");    
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
            
            catch(ArrayIndexOutOfBoundsException | NullPointerException ex){
                isMomentSuccessfull = false;
            }
            
            if(isMomentSuccessfull)
                try { Thread.sleep(800); } catch(InterruptedException ignored) {}
            
            if(isMomentSuccessfull == false)
                index--;
        }
        
        gamePlayScreen.isTurnFinished = true;
    }

    @Override
    public void moveToNextMap() {
        // TODO Auto-generated method stub
        
    }

}
