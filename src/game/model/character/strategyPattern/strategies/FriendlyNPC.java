package game.model.character.strategyPattern.strategies;

import java.util.Random;

import game.components.Console;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.character.Character;
import game.model.character.strategyPattern.MomentStrategy;
import game.model.item.Item;
import game.model.item.decoratorPattern.MeleeWeapon;
import game.model.item.decoratorPattern.RangedWeapon;
import game.model.item.decoratorPattern.Weapon;
import game.model.item.decoratorPattern.WeaponDecorator;
import game.model.item.decoratorPattern.enchantments.BurningEnchantment;
import game.model.item.decoratorPattern.enchantments.FreezingEnchantment;
import game.model.item.decoratorPattern.enchantments.FrighteningEnchantment;
import game.model.item.decoratorPattern.enchantments.PacifyingEnchantment;
import game.model.item.decoratorPattern.enchantments.SlayingEnchantment;
import game.views.jpanels.GamePlayScreen;

/**
 * This class is for  player that is friendly in nature
 * @author teja
 * @version 1.0.0
 */
public class FriendlyNPC implements MomentStrategy{

    private Character character;
    private GamePlayScreen gamePlayScreen;
    boolean isAttackPerformed = false;
    public Object previousMapCellObject = SharedVariables.DEFAULT_CELL_STRING;
    private int playerMomentCount;
    
    /**
     * Default constructor of  friendly Player
     * @param gamePlayScreen screen play
     * @param character player
     */
    public FriendlyNPC(GamePlayScreen gamePlayScreen, Character character) {
        this.gamePlayScreen = gamePlayScreen;
        this.character = character;
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
        
        if(!(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character)){
            
            Object tempPreviousMapCellObject = previousMapCellObject;
            previousMapCellObject = gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];             
            gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] = character;
            gamePlayScreen.currentMap.mapData[fromRowNumber][fromColNumber] = tempPreviousMapCellObject;                          
            Console.printInConsole(message);
            
            if(previousMapCellObject instanceof Item && character.backpack.backpackItems.size() < 10)
                pickItemsFromChest();
            
            tryPerformAttackIfAnyNearByMonster();
            gamePlayScreen.repaintMap(); 
        }  
        
        else if(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character){
            
            Character besidePlayer = (Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];
                        
            if((!besidePlayer.getIsFriendlyMonster() && !besidePlayer.isPlayer()) && isAttackPerformed == false)
                attack(toRowNumber, toColNumber);
        }
        
    }

    /**
     * This method is for attacking character 
     * @param toRowNumber  final row position
     * @param toColNumber final col position
     */
    @Override
    public void attack(int toRowNumber, int toColNumber) {
        
        int attackPoints = character.attackPoint(); 
        playerMomentCount--;
        isAttackPerformed = true;
        
        if(attackPoints >= ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass()){
                                    
            Weapon weapon;
            if(character.getWeaponObject().getItemType().equalsIgnoreCase("Melee"))
                weapon = new WeaponDecorator(new MeleeWeapon());
            else
                weapon = new WeaponDecorator(new RangedWeapon());
            
            if(character.getWeaponObject() != null && character.getWeaponObject().weaponEnchatments != null)
                for (String enchatment : character.getWeaponObject().weaponEnchatments) {
                    
                    switch(enchatment){
                        
                        case "Freezing":
                            weapon = new FreezingEnchantment(gamePlayScreen, weapon, ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]));
                            break;
                            
                        case "Burning":
                            weapon = new BurningEnchantment(weapon, ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]));
                            break;
                        
                        case "Slaying":  
                            weapon = new SlayingEnchantment(weapon, ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]));
                            break;
                            
                        case "Frightening":
                            weapon = new FrighteningEnchantment(gamePlayScreen, weapon, ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]), character);
                            break;
                            
                        case "Pacifying": 
                            weapon = new PacifyingEnchantment(weapon, ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]), gamePlayScreen);
                            break;
                    }
                    
                }
                                    
            int damagePoints = weapon.damagePoints(character);
            ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).hit(damagePoints);
            Console.printInConsole("   => " + character.getName() + " hitted " + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + " with " + damagePoints + " damage points");
        }

        Console.printInConsole("   => " + character.getName() + " missed hitting a hostile monster (" + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + " - "+ ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass() + " armor class) with " + attackPoints + " attack points");    
    }

    /**
     * This method pick Items From Chest
     */
    @Override
    public void pickItemsFromChest() {
        
        Item item = (Item) previousMapCellObject;                                                    
        if(character.items.containsKey(item.itemType) && character.items.get(item.itemType) != null)
            character.backpack.backpackItems.put(item.itemType, item.itemName);                            
        else    
            character.items.put(item.itemType, item.itemName);
                           
        previousMapCellObject = new String(SharedVariables.DEFAULT_CELL_STRING);  
        Console.printInConsole("   => " + character.getName() + " has collected a " + item.getItemType() + "(" + item.getItemName() + ") from the chest");
        character.draw();
    }

    /**
     * This method is for playing respective turn
     */
    @Override
    public void playTurn() {
        
        isAttackPerformed = false;
        tryPerformAttackIfAnyNearByMonster();
        
        for(playerMomentCount = 0; playerMomentCount < 3; playerMomentCount++){
            
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
            
            if(isMomentSuccessfull && !gamePlayScreen.isTesting)
                try { Thread.sleep(800); } catch(InterruptedException ignored) {}
            
            if(isMomentSuccessfull == false)
                playerMomentCount--;
        }
        
        gamePlayScreen.isTurnFinished = true;
    }

    /**
     * This method for try's Perform Attack If Any Near By Monster
     */
    private void tryPerformAttackIfAnyNearByMonster() {
        
        int playerPosition[] = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, character);
        
        try{
            
            if(gamePlayScreen.currentMap.mapData[playerPosition[0] - 1][playerPosition[1] + 0] instanceof Character){
                Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] - 1][playerPosition[1] + 0];
                if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                    movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] - 1, playerPosition[1] + 0);
            }
            
            else if(gamePlayScreen.currentMap.mapData[playerPosition[0] + 1][playerPosition[1] + 0] instanceof Character){
                Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] + 1][playerPosition[1] + 0];
                if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                    movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] + 1, playerPosition[1] + 0);
            }
            
            else if(gamePlayScreen.currentMap.mapData[playerPosition[0] + 0][playerPosition[1] + 1] instanceof Character){
                Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] + 0][playerPosition[1] + 1];
                if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                    movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] + 0, playerPosition[1] + 1);
            }
            
            else if(gamePlayScreen.currentMap.mapData[playerPosition[0] + 0][playerPosition[1] - 1] instanceof Character){
                Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] + 0][playerPosition[1] - 1];
                if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                    movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] + 0, playerPosition[1] - 1);
            }      
            
            else if(character.getWeaponObject().itemClass.equals("Ranged")){
                
                if(gamePlayScreen.currentMap.mapData[playerPosition[0] - 1][playerPosition[1] - 1] instanceof Character){
                    Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] - 1][playerPosition[1] - 1];
                    if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                        movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] - 1, playerPosition[1] - 1);
                }
                
                else if(gamePlayScreen.currentMap.mapData[playerPosition[0] + 1][playerPosition[1] + 1] instanceof Character){
                    Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] + 1][playerPosition[1] + 1];
                    if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                        movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] + 1, playerPosition[1] + 1);
                }
                
                else if(gamePlayScreen.currentMap.mapData[playerPosition[0] - 1][playerPosition[1] + 1] instanceof Character){
                    Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] - 1][playerPosition[1] + 1];
                    if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                        movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] - 1, playerPosition[1] + 1);
                }
                
                else if(gamePlayScreen.currentMap.mapData[playerPosition[0] + 1][playerPosition[1] - 1] instanceof Character){
                    Character nearByCharacter = (Character) gamePlayScreen.currentMap.mapData[playerPosition[0] + 1][playerPosition[1] - 1];
                    if(!nearByCharacter.getIsFriendlyMonster() && nearByCharacter.getHitScore() > 0)
                        movePlayer(null, playerPosition[0], playerPosition[1], playerPosition[0] + 1, playerPosition[1] - 1);
                }    
            }
        }
        
        catch(Exception ignored){}
        
        finally{}
    }
    
    /**
     * This method changes the map if the player completes the current map
     */
    @Override
    public void moveToNextMap() {
        // TODO Auto-generated method stub
        
    }

    /**
     * This method adds Border If Ranged Weapon
     */
    @Override
    public void addBorderIfRangedWeapon() {
        // TODO Auto-generated method stub
        
    }

}