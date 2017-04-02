package game.components;

import java.util.ArrayList;

import javax.swing.JPanel;

import game.model.Item;
import game.model.Map;
import game.model.character.Character;

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
}