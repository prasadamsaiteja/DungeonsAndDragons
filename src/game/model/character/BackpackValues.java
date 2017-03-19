package game.model.character;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import game.components.SharedVariables;
import game.model.Item;

/**
 * Maintains the state of backpack by allowing to equip and unequip items. Uses
 * Singleton pattern to maintain the state and Observer patter to notify any
 * change in state.
 * 
 * @author Supreet Singh (s_supree)
 */
public class BackpackValues extends Observable
{
    private int backpackItemCount = 0;
    private int maxAllowedItems = 10;
    private String fileName;
    private HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
    /**
     * @return the backpackItemCount
     */
    public int getBackpackItemCount()
    {
        return backpackItemCount;
    }
    /**
     * @param backpackItemCount the backpackItemCount to set
     */
    public void setBackpackItemCount(int backpackItemCount)
    {
        this.backpackItemCount = backpackItemCount;
    }
    /**
     * @return the maxAllowedItems
     */
    public int getMaxAllowedItems()
    {
        return maxAllowedItems;
    }
    /**
     * @param maxAllowedItems the maxAllowedItems to set
     */
    public void setMaxAllowedItems(int maxAllowedItems)
    {
        this.maxAllowedItems = maxAllowedItems;
    }
    /**
     * @return the fileName
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * @return the items
     */
    public HashMap<String, ArrayList<String>> getItems()
    {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(HashMap<String, ArrayList<String>> items)
    {
        this.items = items;
    }
    
   
}
