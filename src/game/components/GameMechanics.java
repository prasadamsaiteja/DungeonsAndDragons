package game.components;

import java.util.ArrayList;

import javax.swing.JPanel;

import game.model.Item;
import game.model.Map;
import game.model.character.Character;

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
        if(((Character) mapCellObject).getIsPlayer())
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
   * @param Map this is the map object
   * @return return array of int first int contains row number and second int contains col number
   */
    public static int[] getPlayerPosition(Map currentMap){
    
      for (int i = 0; i < currentMap.mapWidth; i++)      
        for (int j = 0; j < currentMap.mapHeight; j++)
               if(currentMap.mapData[i][j] instanceof Character && ((Character) currentMap.mapData[i][j]).getIsPlayer())
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
}
