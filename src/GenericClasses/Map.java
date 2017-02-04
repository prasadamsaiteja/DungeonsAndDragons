package GenericClasses;

import javax.swing.JPanel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import GameComponents.SharedVariables;

@XmlRootElement(name="Map")
/**
 * This class is a generic model for maps.
 * 
 * @author saiteja prasadam
 * @version 1.0
 * @since   2/3/2017
 */
public class Map {
 
    /** The map name. */
    @XmlElement(name="Name")   
    private String mapName;    

    /** The map width. */
    @XmlElement(name="Width")  
    private int mapWidth;    
    
    /** The map height. */
    @XmlElement(name="Height")  
    private int mapHeight;   
    
    /** The map cell values. */
    @XmlElement(name="Map rows")  
    public String[][] mapCellValues;

    /**
     * This constructor initializes a map.
     * @param mapName   name of the map.
     * @param mapWidth  width of the map.
     * @param mapHeight height of the map.
     * @param mapPanel  2D array of map JPanel, which is then converted to integer 2D array.
     */
    public Map(String mapName, int mapWidth, int mapHeight, JPanel mapPanel[][]){
        this.mapName = mapName;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapCellValues = convertMapJPanelToStringArray(mapPanel);        
    }
    
    /**
     * Instantiates a new map.
     */
    public Map(){}
    
    /**
     * Gets the map name.
     * @return the map name
     */
    public String getMapName() {
      return mapName;
    }
    
    /**
     * This method converts JPanel map to String array map.
     * @param mapPanel  This is the map cell Panel array to converted into string array.
     * @return  Converted string array from JPanel array.
     */
    private String[][] convertMapJPanelToStringArray(JPanel mapPanel[][]){
      
      String[][] tempMapCellValues = new String[mapWidth][mapHeight];
      
      for(int i = 0; i < mapWidth; i++)     
          for(int j = 0; j < mapHeight; j++)                       
            tempMapCellValues[i][j] = SharedVariables.getCellStringFromColor(mapPanel[i][j].getBackground());          
      
      return tempMapCellValues;
    }
  
}