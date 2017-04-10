package game.model.character.strategyPattern.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import game.GameLauncher;
import game.components.Console;
import game.components.ExtensionMethods;
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
import game.views.jdialogs.DialogHelper;
import game.views.jpanels.GamePlayScreen;
import game.views.jpanels.LaunchScreen;

public class ComputerPlayer implements MomentStrategy{
    
    private GamePlayScreen gamePlayScreen;
    private int playerMomentCount;
    private boolean isAttackPerformed = false;
    public Object previousMapCellObject = SharedVariables.DEFAULT_CELL_STRING;

    public ComputerPlayer(GamePlayScreen gamePlayScreen) {
        this.gamePlayScreen = gamePlayScreen;
    }

    @Override
    public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber) {
        
        if(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character && ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getHitScore() < 1){
            
            Object temppreviousMapCellObject = previousMapCellObject;
            previousMapCellObject = gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];             
            gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] = gamePlayScreen.character;
            gamePlayScreen.currentMap.mapData[fromRowNumber][fromColNumber] = temppreviousMapCellObject;   
            playerMomentCount--;
            gamePlayScreen.repaintMap();
            tryPerformAttackIfAnyNearByMonster();
            
            if(previousMapCellObject instanceof Character){
                                    
                if(ExtensionMethods.fetchAllItemNames(((Character) previousMapCellObject)).size() < 1)
                    DialogHelper.showBasicDialog("No items found");
                                    
                else if(JOptionPane.showConfirmDialog(null, "Would you like to pick items from this dead monster", "You approched a dead monster", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    
                    if(gamePlayScreen.character.backpack.backpackItems.size() >= 10)
                        DialogHelper.showBasicDialog("Your backpack is full");
                    
                    else{
                        Set<String> characterItems = new HashSet<>();
                        
                        characterItems.addAll(ExtensionMethods.fetchAllItemNames(((Character) previousMapCellObject)));
                        
                        JComboBox<String> itemsList = new JComboBox<String>();
                        for (String string : characterItems)
                            itemsList.addItem(string);
                        
                        JOptionPane.showMessageDialog(null, itemsList, "Select a item to pick from dead monster", JOptionPane.QUESTION_MESSAGE);
                        Entry<String, String> entry = ExtensionMethods.getByValue(((Character) previousMapCellObject).backpack.backpackItems, itemsList.getSelectedItem().toString());                        
                        
                        if(entry == null)
                            entry = ExtensionMethods.getByValue(((Character) previousMapCellObject).items, itemsList.getSelectedItem().toString());
                        
                        if(entry != null){
                            
                            if(((Character) previousMapCellObject).backpack.backpackItems.remove(entry.getKey(), entry.getValue()) == false)
                                ((Character) previousMapCellObject).items.remove(entry.getKey(), entry.getValue());
                              
                            if(gamePlayScreen.character.items.containsKey(entry.getKey()))                                
                                gamePlayScreen.character.backpack.backpackItems.put(entry.getKey(), entry.getValue());
                            else
                                gamePlayScreen.character.items.put(entry.getKey(), entry.getValue());
                            
                            gamePlayScreen.character.draw();
                            ((Character) previousMapCellObject).draw();
                            DialogHelper.showBasicDialog("You have picked up a " + entry.getKey() + " (" + entry.getValue() + ") from a dead monster"); 
                        }
                                                                       
                    }
                }
                    
            }                               
        }
        
        else if(!gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber].equals(SharedVariables.WALL_STRING) && !(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character)){
          
            Object temppreviousMapCellObject = previousMapCellObject;
            previousMapCellObject = gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];             
            gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] = gamePlayScreen.character;
            gamePlayScreen.currentMap.mapData[fromRowNumber][fromColNumber] = temppreviousMapCellObject;              
            gamePlayScreen.repaintMap(); 
            Console.printInConsole(message);
            
            if(previousMapCellObject instanceof Item)
                pickItemsFromChest();                            
            
            else if(previousMapCellObject instanceof String && ((String) previousMapCellObject).equals(SharedVariables.KEY_STRING)){
                gamePlayScreen.character.setKeyCollectedFlag(true);
                previousMapCellObject = SharedVariables.DEFAULT_CELL_STRING;;
            }
            
            else if(previousMapCellObject instanceof String && ((String) previousMapCellObject).equals(SharedVariables.EXIT_DOOR_STRING)){
                if(checkIfTheObjectiveIsCompleted())
                    moveToNextMap();
                
                else
                    DialogHelper.showBasicDialog("You need to collect key (If map has one) or kill all the hostile enemies to clear this map");                        
            }
                
        }
        
        else if(gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber] instanceof Character){
            
            if(((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getIsFriendlyMonster() == false && isAttackPerformed == false)
                attack(toRowNumber, toColNumber);
            else if(((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getIsFriendlyMonster() == true)
                exchangeItemsFromFriendlyMonsters(toRowNumber, toColNumber);
        }

        playerMomentCount++;
        if(playerMomentCount >= 3){
            playerMomentCount = 0;
            gamePlayScreen.isTurnFinished = true;
        }
    }
    
    /**
     * This method lets player to exchange items from friendly monster
     * 
     * @param toRowNumber row number which user is trying to goto
     * @param toColNumber col number which user is trying to goto
     */
    private void exchangeItemsFromFriendlyMonsters(int toRowNumber, int toColNumber) {
       
        playerMomentCount--;
        Character friendlyMonster = (Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber];
        
        if(JOptionPane.showConfirmDialog(null, "Do you want to exchange items from this friendly monster (" + friendlyMonster.getName() + ") ?","You approched a friendly monster", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            
            if(ExtensionMethods.fetchAllItemNames(gamePlayScreen.character).size() == 0)
                DialogHelper.showBasicDialog("You don't have any item to exchange.");
            
            else if(gamePlayScreen.character.backpack.backpackItems.size() + gamePlayScreen.character.getAllItems().size() == 0)
                DialogHelper.showBasicDialog("Enemy doesn't have any items to exchange");
            
            else if(gamePlayScreen.character.backpack.backpackItems.size() >= 10)
                DialogHelper.showBasicDialog("Your backpack is full");
            
            else{
                Set<String> characterItems = new HashSet<>();
                
                characterItems.addAll(ExtensionMethods.fetchAllItemNames(gamePlayScreen.character));
                
                JComboBox<String> itemsList = new JComboBox<String>();
                for (String string : characterItems)
                    itemsList.addItem(string);
                
                JOptionPane.showMessageDialog(null, itemsList, "Select a item to exchange", JOptionPane.QUESTION_MESSAGE);
                Entry<String, String> entry = ExtensionMethods.getByValue(gamePlayScreen.character.backpack.backpackItems, itemsList.getSelectedItem().toString());
                
                if(entry == null)
                    entry = ExtensionMethods.getByValue(gamePlayScreen.character.getAllItems(), itemsList.getSelectedItem().toString());                                       
                
                if(gamePlayScreen.character.backpack.backpackItems.remove(entry.getKey(), entry.getValue()) == false)
                    gamePlayScreen.character.items.remove(entry.getKey(), entry.getValue());
                
                if(((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.size() > 0){
                    
                    Random random = new Random();
                    List<String> keys = new ArrayList<String>(((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.keySet());                        
                    String randomKey = keys.get(random.nextInt(keys.size()));
                    Collection<String> values = ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.get(randomKey);
                    
                    String value = (String) values.toArray()[new Random().nextInt(values.size())];
                    ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.remove(randomKey, value);
                    
                    if(gamePlayScreen.character.items.containsKey(randomKey))
                        gamePlayScreen.character.backpack.backpackItems.put(randomKey, value);
                    else
                        gamePlayScreen.character.items.put(randomKey, value);
                    
                    gamePlayScreen.character.draw();
                    ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.put(entry.getKey(), entry.getValue());
                    ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).draw();
                    DialogHelper.showBasicDialog("You have received a " + randomKey + " (" + value + ") by the exchange");                                                                                              
                }
                
                else{                        
                    Random random = new Random();
                    List<String> keys = new ArrayList<String>(((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).items.keySet());
                    String randomKey = keys.get(random.nextInt(keys.size()));
                    String value = ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).items.get(randomKey);
                    
                    ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).items.remove(randomKey, value);                        
                    
                    if(gamePlayScreen.character.items.containsKey(randomKey))                            
                        gamePlayScreen.character.backpack.backpackItems.put(randomKey, value);                                                
                    else
                        gamePlayScreen.character.items.put(randomKey, value);
                    
                    gamePlayScreen.character.draw();
                    ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.put(entry.getKey(), entry.getValue());
                    ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).draw();
                    DialogHelper.showBasicDialog("You have received a " + randomKey + " (" + value + ") by the exchange");  
                }           
                
                
            }
        }
    }
    
    /**
     * This method changes the map if the player completes the current map
     */
    public void moveToNextMap() {
        
        previousMapCellObject = new String(SharedVariables.DEFAULT_CELL_STRING);
        gamePlayScreen.character.setKeyCollectedFlag(false);
        
        if(gamePlayScreen.currentMapNumber + 1 == gamePlayScreen.campaign.getMapNames().size()){
            JOptionPane.showConfirmDialog(null, "Congrats, you have completed the campaign, you will now go back to main screen", "Map cleared", JOptionPane.PLAIN_MESSAGE);
            GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());
        }
        
        else{
            JOptionPane.showConfirmDialog(null, "Congrats, you have cleared this map, you will now go to next map", "Map cleared", JOptionPane.PLAIN_MESSAGE);
            gamePlayScreen.currentMapNumber++;                
            gamePlayScreen.currentMap = gamePlayScreen.campaign.getMapList().get(gamePlayScreen.currentMapNumber);
            gamePlayScreen.currentMap.initalizeMapData(gamePlayScreen.character.getName());
            gamePlayScreen.character.setLevel(gamePlayScreen.character.getLevel() + 1);
            gamePlayScreen.setMapLevel();
            gamePlayScreen.initComponents();
            gamePlayScreen.initalizeTurnBasedMechanism();
        }
        
    }
    
    /**
     * This method return true or false to state whether the object is completed or not
     * @return true if objective is completed else false
     */
    private boolean checkIfTheObjectiveIsCompleted(){
        
        if(gamePlayScreen.character.isKeyCollected() == true)
            return true;
        
        ArrayList<Character> characters = GameMechanics.getAllCharacterObjects(gamePlayScreen.currentMap);
        for(Character character : characters)
            if(!character.isPlayer() && !character.getIsFriendlyMonster() && character.getHitScore() > 0)
                return false;
        
        int count = 0;
        for(Character character : characters)
            if(!character.getIsFriendlyMonster() && character.getHitScore() > 0)
                count++;
        
        if(count > 0)
            return false;
        
        return true;
    }

    @Override
    public void attack(int toRowNumber, int toColNumber) {
                        
        int attackPoints = gamePlayScreen.character.attackPoint();
        playerMomentCount--;
        isAttackPerformed = true;
        
        if(attackPoints >= ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass()){
                                                
            Weapon weapon;
            if(gamePlayScreen.character.getWeaponObject().getItemType().equalsIgnoreCase("Melee"))
                weapon = new WeaponDecorator(new MeleeWeapon());
                
            else
                weapon = new WeaponDecorator(new RangedWeapon());
            
            if(gamePlayScreen.character.getWeaponObject() != null && gamePlayScreen.character.getWeaponObject().weaponEnchatments != null)
            for (String enchatment : gamePlayScreen.character.getWeaponObject().weaponEnchatments) {
                
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
                        weapon = new FrighteningEnchantment(gamePlayScreen, weapon, ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]), gamePlayScreen.character);
                        break;
                        
                    case "Pacifying": 
                        weapon = new PacifyingEnchantment(weapon, ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]), gamePlayScreen);
                        break;
                }                
            }
                        
            int damagePoints = weapon.damagePoints(gamePlayScreen.character);
            ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).hit(damagePoints);
            Console.printInConsole("   => you hitted a hostile monster(" + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + ") with " + damagePoints + " attack points");    
        }
        
        else
            Console.printInConsole("   => you missed hitting a hostile monster(" + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getName() + " - " + ((Character) gamePlayScreen.currentMap.mapData[toRowNumber][toColNumber]).getArmorClass() + " armor class) with " + attackPoints + " attack points");                       
    }

    @Override
    public void pickItemsFromChest() {
        
        if(JOptionPane.showConfirmDialog(null, "This chest contains a " + ((Item) previousMapCellObject).getItemType() + " (" + ((Item) previousMapCellObject).getItemName() + "), would you like to pick it?", "You approched a chest", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            if(gamePlayScreen.character.backpack.backpackItems.size() >= 10)
                DialogHelper.showBasicDialog("Your backpack is full");
            else{
                Item item = (Item) previousMapCellObject;                            
                
                if(gamePlayScreen.character.items.containsKey(item.itemType) && gamePlayScreen.character.items.get(item.itemType) != null)
                    gamePlayScreen.character.backpack.backpackItems.put(item.itemType, item.itemName);                            
                else    
                    gamePlayScreen.character.items.put(item.itemType, item.itemName);
                           
                gamePlayScreen.character.draw();
                previousMapCellObject = new String(SharedVariables.DEFAULT_CELL_STRING);
                DialogHelper.showBasicDialog("Awesome, you have picked up a " + item.itemType + " (" + item.itemName + ") from a abandoned chest");
            }
        }
    }

    @Override
    public void playTurn() {
        
        isAttackPerformed = false;
        tryPerformAttackIfAnyNearByMonster();
        for (playerMomentCount = 0; playerMomentCount < 3; playerMomentCount++) {            
            
            if(checkIfTheObjectiveIsCompleted() == true)
                moveTowardsExitDoor();
            else
                completeObjective();
            
            try { Thread.sleep(800); } catch(InterruptedException ignored) {}  
        }
        gamePlayScreen.isTurnFinished = true;
    }

    private void completeObjective() {
        
        if(GameMechanics.checkIfKeyExistsInTheMap(gamePlayScreen.currentMap))
            collectKey();
        else
            KillMonsters();
    }

    private void KillMonsters() {
        
        int[] characterLocation = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        int[] enemyLocation = GameMechanics.getNearestMonsterPosition(gamePlayScreen.currentMap, gamePlayScreen.character);       
        
        if(enemyLocation != null){
            
            int horizontalDistance = enemyLocation[0] - characterLocation[0];
            int verticalDistance = enemyLocation[1] - characterLocation[1];
            
            if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
                String message = "   => " + gamePlayScreen.character.getName() + " moving left";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
            }
            
            else if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + gamePlayScreen.character.getName() + " moving right";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
            }
                
            else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] - 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + gamePlayScreen.character.getName() + " moving up";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] - 1), characterLocation[1]);
            }
                
            else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] + 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + gamePlayScreen.character.getName() + " moving down";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] + 1), characterLocation[1]);
            }
            
            else if(verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
                String message = "   => " + gamePlayScreen.character.getName() + " moving left";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
            }
            
            else if(verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
                String message = "   => " + gamePlayScreen.character.getName() + " moving right";
                movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
            }
            
        }                
    }

    private void collectKey() {
        
        int[] characterLocation = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        int[] keyLocation = GameMechanics.getKeyPosition(gamePlayScreen.currentMap);
        
        int horizontalDistance = keyLocation[0] - characterLocation[0];
        int verticalDistance = keyLocation[1] - characterLocation[1];
        
        if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
            String message = "   => " + gamePlayScreen.character.getName() + " moving left";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
        }
        
        else if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving right";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
        }
            
        else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] - 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving up";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] - 1), characterLocation[1]);
        }
            
        else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] + 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving down";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] + 1), characterLocation[1]);
        }
        
        else if(verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
            String message = "   => " + gamePlayScreen.character.getName() + " moving left";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
        }
        
        else if(verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving right";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
        }
    }

    private void moveTowardsExitDoor() {
        
        int[] characterLocation = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        int[] doorLocation = GameMechanics.getExitDoorPosition(gamePlayScreen.currentMap);
        
        int horizontalDistance = doorLocation[0] - characterLocation[0];
        int verticalDistance = doorLocation[1] - characterLocation[1];
        
        if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
            String message = "   => " + gamePlayScreen.character.getName() + " moving left";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
        }
        
        else if((horizontalDistance * horizontalDistance < verticalDistance * verticalDistance) && verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving right";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
        }
            
        else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] - 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving up";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] - 1), characterLocation[1]);
        }
            
        else if((horizontalDistance * horizontalDistance > verticalDistance * verticalDistance) && horizontalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0] + 1][characterLocation[1]].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving down";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0] + 1), characterLocation[1]);
        }
        
        else if(verticalDistance < 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] - 1].equals(SharedVariables.WALL_STRING)){                
            String message = "   => " + gamePlayScreen.character.getName() + " moving left";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] - 1);
        }
        
        else if(verticalDistance > 0 && !gamePlayScreen.currentMap.mapData[characterLocation[0]][characterLocation[1] + 1].equals(SharedVariables.WALL_STRING)){
            String message = "   => " + gamePlayScreen.character.getName() + " moving right";
            movePlayer(message, characterLocation[0], characterLocation[1], (characterLocation[0]), characterLocation[1] + 1);
        }
    }

    @Override
    public void addBorderIfRangedWeapon() {
        // TODO Auto-generated method stub
        
    }

    private void tryPerformAttackIfAnyNearByMonster() {
        
        int playerPosition[] = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, gamePlayScreen.character);
        
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
            
            else if(gamePlayScreen.character.getWeaponObject().itemClass.equals("Ranged")){
                
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
}