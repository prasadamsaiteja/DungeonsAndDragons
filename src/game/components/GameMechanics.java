package game.components;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import game.model.Map;
import game.model.character.Character;
import game.model.item.Item;
import game.views.jpanels.GamePlayScreen;

/**
 * This class contains all the method required for 
 * @author saite
 *
 */
public class GameMechanics {

  /**
     * This method sets the map cell color from map data information
   * @param jpanel Jpanel of the map cell
   * @param mapCellObject map cell object contains map cell object
   * @return returns jpanel with value set
   */
    public static JPanel setMapCellDetailsFromMapObjectData(JPanel jpanel, Object mapCellObject){
      
      if(mapCellObject instanceof String)
          jpanel.setBackground(SharedVariables.getCellColorFromString(mapCellObject.toString()));
      
      else if(mapCellObject instanceof Character){
        if(((Character) mapCellObject).isPlayer())
          jpanel.setBackground(SharedVariables.getCellColorFromString(SharedVariables.PLAYER_STRING));
        else
          jpanel.setBackground(SharedVariables.getCellColorFromString(SharedVariables.MONSTER_STRING));                  
      }
                  
      else if(mapCellObject instanceof Item)
          jpanel.setBackground(SharedVariables.getCellColorFromString(SharedVariables.CHEST_STRING));
              
      return jpanel;      
  }

    /**
     * This method returns the player position
     * @param currentMap this is the map object
     * @return return array of int, The first int contains row number and second int contains column number
     */
    public static int[] getPlayerPosition(Map currentMap){
    
      for (int i = 0; i < currentMap.mapWidth; i++)      
        for (int j = 0; j < currentMap.mapHeight; j++)
               if(currentMap.mapData[i][j] instanceof Character && ((Character) currentMap.mapData[i][j]).isPlayer())
                 return new int[]{i, j};
                                   
      return null;              
  }
    
    /**
     * This method returns the exit door position
     * @param currentMap this is the map object
     * @return return array of int, The first int contains row number and second int contains column number
     */
    public static int[] getExitDoorPosition(Map currentMap){
    
      for (int i = 0; i < currentMap.mapWidth; i++)      
        for (int j = 0; j < currentMap.mapHeight; j++)
               if(currentMap.mapData[i][j] instanceof String && ((String) currentMap.mapData[i][j]).equals(SharedVariables.EXIT_DOOR_STRING))
                 return new int[]{i, j};
                                   
      return null;              
    }  

    /**
     * This method returns the key position
     * @param currentMap this is the map object
     * @return return array of int, The first int contains row number and second int contains column number
     */
    public static int[] getKeyPosition(Map currentMap){
    
      for (int i = 0; i < currentMap.mapWidth; i++)      
        for (int j = 0; j < currentMap.mapHeight; j++)
               if(currentMap.mapData[i][j] instanceof String && ((String) currentMap.mapData[i][j]).equals(SharedVariables.KEY_STRING))
                 return new int[]{i, j};
                                   
      return null;              
    } 
  
    /**
     * This method returns all the characters in the map
     * @param currentMap Current map playing
     * @return  Arraylist of character found
     */
    public static ArrayList<Character> getAllCharacterObjects(final Map currentMap){
        
        ArrayList<Character> characters = new ArrayList<Character>();
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
          for (int j = 0; j < currentMap.mapHeight; j++)
            if(currentMap.mapData[i][j] instanceof Character)
              characters.add((Character) currentMap.mapData[i][j]);
        
        return characters;
    }

    /**
     * This method returns where there is a map contains a key or not.
     * @param currentMap Current map object
     * @return return true if key found else false
     */
    public static boolean checkIfKeyExistsInTheMap(Map currentMap){
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
          for (int j = 0; j < currentMap.mapHeight; j++)
              if(currentMap.mapCellValues[i][j].equals(SharedVariables.KEY_STRING))
                  return true;
        
        return false;
    }

    /**
     * This method returns current position of player
     * @param currentMap Current map playing
     * @param character character object
     * @return return array of int, The first int contains row number and second int contains column number
     */
    public static int[] getCharacterPosition(Map currentMap, Character character){
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
            for (int j = 0; j < currentMap.mapHeight; j++)
                if(currentMap.mapData[i][j] instanceof Character && (currentMap.mapData[i][j]) == character)
                    return new int[]{i, j};
        
        return null;          
    }

    /**
     * This method returns current position of nearest monster
     * @param currentMap Current map playing
     * @param checkNearestToCharacter check nearest positon related to this character position.
     * @return return array of int, The first int contains row number and second int contains column number
     */
    public static int[] getNearestMonsterPosition(Map currentMap, Character checkNearestToCharacter){
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
            for (int j = 0; j < currentMap.mapHeight; j++)
                if(currentMap.mapData[i][j] instanceof Character && !((Character) currentMap.mapData[i][j]).getIsFriendlyMonster()) 
                    return new int[]{i, j};
        
        return null;   
        
    }

    public static void addRangedBorder(GamePlayScreen gamePlayScreen, int[] playerPosition) {
        
        removeBorder(gamePlayScreen);
        
        if(playerPosition[0] - 1 >= 0 && playerPosition[1] - 1 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] - 1 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] - 1) //1
            gamePlayScreen.mapJPanelArray[playerPosition[0] - 1][playerPosition[1] - 1].setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.RED));         
        
        if(playerPosition[0] - 1 >= 0 && playerPosition[1] + 0 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] - 1 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] + 0) //2 
            gamePlayScreen.mapJPanelArray[playerPosition[0] - 1][playerPosition[1] + 0].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.RED));
        
        if(playerPosition[0] - 1 >= 0 && playerPosition[1] + 1 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] - 1 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] + 1) //3
            gamePlayScreen.mapJPanelArray[playerPosition[0] - 1][playerPosition[1] + 1].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.RED));
        
        if(playerPosition[0] + 0 >= 0 && playerPosition[1] + 1 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] + 0 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] + 1) //4
            gamePlayScreen.mapJPanelArray[playerPosition[0] + 0][playerPosition[1] + 1].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.RED));
        
        if(playerPosition[0] + 1 >= 0 && playerPosition[1] + 1 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] + 1 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] + 1) //5
            gamePlayScreen.mapJPanelArray[playerPosition[0] + 1][playerPosition[1] + 1].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.RED));
        
        if(playerPosition[0] + 1 >= 0 && playerPosition[1] + 0 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] + 1 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] + 0) //6
            gamePlayScreen.mapJPanelArray[playerPosition[0] + 1][playerPosition[1] + 0].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
        
        if(playerPosition[0] + 1 >= 0 && playerPosition[1] - 1 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] + 1 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] - 1) //7
            gamePlayScreen.mapJPanelArray[playerPosition[0] + 1][playerPosition[1] - 1].setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.RED));
        
        if(playerPosition[0] + 0 >= 0 && playerPosition[1] - 1 >= 0 && gamePlayScreen.currentMap.mapHeight > playerPosition[0] + 0 && gamePlayScreen.currentMap.mapWidth > playerPosition[1] - 1) //8
            gamePlayScreen.mapJPanelArray[playerPosition[0] + 0][playerPosition[1] - 1].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.RED));
        
    }

    public static void removeBorder(GamePlayScreen gamePlayScreen) {
        
        for (int i = 0; i < gamePlayScreen.currentMap.mapWidth; i++)      
            for (int j = 0; j < gamePlayScreen.currentMap.mapHeight; j++)
                gamePlayScreen.mapJPanelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public static Character getAFriendlyMonster(Map currentMap){
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
            for (int j = 0; j < currentMap.mapHeight; j++)
                if(currentMap.mapData[i][j] instanceof Character){
                    
                    Character character = (Character) currentMap.mapData[i][j];
                    if(!character.isPlayer() && character.getIsFriendlyMonster())
                        return character;
                }
        
        return null;
    }
    
    public static Character getAHostileMonster(Map currentMap){
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
            for (int j = 0; j < currentMap.mapHeight; j++)
                if(currentMap.mapData[i][j] instanceof Character){
                    
                    Character character = (Character) currentMap.mapData[i][j];
                    if(!character.isPlayer() && !character.getIsFriendlyMonster())
                        return character;
                }
        
        return null;
    }
}