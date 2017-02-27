package game.model;

import javax.swing.JPanel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import game.components.SharedVariables;

@XmlRootElement(name = "Map")
/**
 * This class is a generic model for maps.
 * 
 * @author saiteja prasadam
 * @version 1.0
 * @since 2/3/2017
 */
public class Map
{

    @XmlElement(name = "Name")
    private String mapName;
    @XmlElement(name = "Width")
    public int mapWidth;
    @XmlElement(name = "Height")
    public int mapHeight;
    @XmlElement(name = "Map_rows")
    public String[][] mapCellValues;

    /**
     * This constructor initializes a map.
     * 
     * @param mapName name of the map.
     * @param mapWidth width of the map.
     * @param mapHeight height of the map.
     * @param mapPanel 2D array of map JPanel, which is then converted to
     *            integer 2D array.
     */
    public Map(String mapName, int mapWidth, int mapHeight, JPanel mapPanel[][])
    {
        this.mapName = mapName;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapCellValues = convertMapJPanelToStringArray(mapPanel);
    }

    /**
     * Instantiates a new map.
     */
    public Map()
    {
    }

    /**
     * Gets the map name.
     * 
     * @return the map name
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * This method converts JPanel map to String array map.
     * 
     * @param mapPanel This is the map cell Panel array to converted into string
     *            array.
     * @return Converted string array from JPanel array.
     */
    private String[][] convertMapJPanelToStringArray(JPanel mapPanel[][])
    {

        String[][] tempMapCellValues = new String[mapWidth][mapHeight];

        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
                tempMapCellValues[i][j] = SharedVariables.getCellStringFromColor(mapPanel[i][j].getBackground());

        return tempMapCellValues;
    }

    /**
     * This methods converts String array to Colored JPanel array
     * 
     * @return Colored JPanel array
     */
    public JPanel[][] convertStringArrayToJPanel()
    {

        JPanel[][] tempMapCellValues = new JPanel[mapWidth][mapHeight];

        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
            {
                JPanel tempJPanel = new JPanel();
                tempJPanel.setBackground(SharedVariables.getCellColorFromString(mapCellValues[i][j]));
                tempMapCellValues[i][j] = tempJPanel;
            }

        return tempMapCellValues;
    }
}
