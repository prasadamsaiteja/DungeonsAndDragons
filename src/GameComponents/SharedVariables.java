package GameComponents;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This class contains variables which can be used globally
 * 
 * @author saiteja prasadam
 * @since 1/31/2017
 * @version 1.0
 */
public class SharedVariables {
  
       //Map element colors       
       public static final Color MAP_MOUSE_HOVER_COLOR = Color.gray;
       public static final Color MAP_DEFAULT_CELL_COLOR = Color.WHITE;
       public static final Color MAP_PLAYER_CELL_COLOR = Color.BLUE;
       
       private static final Color MAP_WALL_CELL_COLOR = Color.BLACK;
       private static final Color MAP_MOSTER_CELL_COLOR = Color.RED;
       private static final Color MAP_KEY_CELL_COLOR = Color.CYAN;
       private static final Color MAP_CHEST_CELL_COLOR = Color.YELLOW;       
       private static final Color MAP_ENTRY_DOOR_CELL_COLOR = Color.GREEN;
       private static final Color MAP_EXIT_DOOR_CELL_COLOR = Color.GREEN;
       
       public static final String WALL_STRING = "WALL";
       public static final String MONSTER_STRING = "MONSTER";
       public static final String KEY_STRING = "KEY";
       public static final String CHEST_STRING = "CHEST";
       public static final String PLAYER_STRING = "PLAYER";
       public static final String ENTRY_DOOR_STRING = "ENTRY_DOOR";
       public static final String EXIT_DOOR_STRING = "EXIT_DOOR";
       private static final String DEFAULT_CELL_STRING = "EMPTY";
       
       public static final HashMap<String, Color> mapCellHashMap = new HashMap<>();
       static
       {          
           mapCellHashMap.put(DEFAULT_CELL_STRING, MAP_DEFAULT_CELL_COLOR);
           mapCellHashMap.put(WALL_STRING, MAP_WALL_CELL_COLOR);
           mapCellHashMap.put(MONSTER_STRING, MAP_MOSTER_CELL_COLOR);
           mapCellHashMap.put(KEY_STRING, MAP_KEY_CELL_COLOR);
           mapCellHashMap.put(CHEST_STRING, MAP_CHEST_CELL_COLOR);
           mapCellHashMap.put(PLAYER_STRING, MAP_PLAYER_CELL_COLOR);
           mapCellHashMap.put(ENTRY_DOOR_STRING, MAP_ENTRY_DOOR_CELL_COLOR);
           mapCellHashMap.put(EXIT_DOOR_STRING, MAP_EXIT_DOOR_CELL_COLOR);
       }
       
       /**
        * This method returns cell string value from color provided.
        * @param cellColor  Color of the map cell.
        * @return   This is the string representation of the cell value.
        */
       public static final String getCellStringFromColor(Color cellColor){
         
           for(Entry<String, Color> entry : mapCellHashMap.entrySet()){
             if(cellColor.equals(entry.getValue()))
               return entry.getKey();            
         }
          return null;
       }

       public static final String DataDirectory = "Data";
       public static final String MapsDirectory = DataDirectory + File.separator + "Maps";
}