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
public class Backpack extends Observable
{

    private int backpackItemCount = 0;
    private int maxAllowedItems = 10;
    private String fileName;
    private HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();

    /**
     * increment item counter
     */
    private void incrementItemCount()
    {
        backpackItemCount += 1;
    }

    /**
     * decrement item counter
     */
    private void decrementItemCount()
    {
        backpackItemCount -= 1;
    }

    /**
     * @return file name
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * notify observers of a change in state
     */
    private void update()
    {
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * 
     * @param i
     * @throws Throwable 
     */
    public void equip(Item i) throws Throwable
    {
        equip(i.getItemType(), i.getItemName());
    }
    
    /**
     * equip an item
     * 
     * @param itemType item type to equip
     * @param itemName item name to equip
     * @throws Exception if item can not be added
     */
    public void equip(String itemType, String itemName) throws Throwable // NO_UCD
                                                                         // (unused
                                                                         // code)
    {
        ArrayList<String> itemList;
        if (!items.containsKey(itemType))
        {
            itemList = new ArrayList<>();
            items.put(itemType, itemList);
        }
        else
        {
            itemList = items.get(itemType);
        }

        // check if the array list already doesn't contain the item
        // and if there is still space in backpack for adding more items
        if (!itemList.contains(itemName))
        {
            if (backpackItemCount < maxAllowedItems)
            {
                itemList.add(itemName);
                incrementItemCount();
                update();
            }
            else
            {
                throw new Exception("No more space in backpack");
            }
        }
        else
        {
            throw new Exception("Item already exists in backpack");
        }
    }
    
    /**
     * unequips item using item object
     * @param i
     */
    public void unequip(Item i)
    {
        unequip(i.getItemType(), i.getItemName());
    }

    /**
     * un-equip an item
     * 
     * @param itemType item type to unequip
     * @param itemName item name to unequip
     */
    public void unequip(String itemType, String itemName) // NO_UCD (unused
                                                          // code)
    {
        ArrayList<String> itemList;
        if (!items.containsKey(itemType))
        {
            return;
        }

        itemList = items.get(itemType);

        if (!itemList.contains(itemName))
            return;
        

        itemList.remove(itemName);
        if (itemList.size() == 0)
        {
            items.remove(itemType);
        }
        decrementItemCount();
        System.out.println("updating observers");
        update();
    }
    
    /**
     * @return set of equipped item types
     */
    public Set<String> getItemTypes()
    {
        return items.keySet();
    }

    /**
     * @return backpack size
     */
    public int size() // NO_UCD (unused code)
    {
        return backpackItemCount;
    }

    /**
     * @param itemType item type
     * @return array list of all items for that item type
     */
    public ArrayList<String> getByType(String itemType)
    {
        if (!items.containsKey(itemType))
        {
            return null;
        }

        return items.get(itemType);
    }
    
    /**
     * @return
     */
    public HashMap<String, ArrayList<String>> getAll()
    {
        return this.items;
    }

    /**
     * saves the state of backpack in xml file
     * 
     * @throws IOException if save file fails
     */
    public void save() throws IOException // NO_UCD (unused code)
    {
        // check if the directory exists and if not then make it
        File dir = new File(SharedVariables.BackpackDirectory);
        if (!dir.isDirectory())
        {
            dir.mkdirs();
        }
        if (this.getFileName() == null)
        {
            Date d = new Date();
            long dTime = d.getTime();
            this.setFileName("Backpack" + dTime);
        }
        
        String filePath = SharedVariables.BackpackDirectory + File.separator + this.getFileName() + ".xml";

        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(this);
        FileWriter out;
        out = new FileWriter(filePath);
        out.write(xml);
        out.close();
    }

    /**
     * Initializes backpack
     * @param fileName Name of the file
     * @return Backpack instance
     * @throws IOException exception
     */ 
    public static Backpack get(String fileName) throws IOException
    {
        Backpack backpack = null;
    
        // if backpack xml file exist then read it and load it
        String filePath = SharedVariables.BackpackDirectory + File.separator + fileName + ".xml";
        File f = new File(filePath);
        if (f.exists() && f.canRead())
        {
            BufferedReader r;
            r = new BufferedReader(new FileReader(f.getPath()));
            String sCurrentLine, xml = "";
            while ((sCurrentLine = r.readLine()) != null)
            {
                xml += sCurrentLine;
            }
            XStream xstream = new XStream(new StaxDriver());
            backpack = (Backpack) xstream.fromXML(xml);
            r.close();
        }
        else
        {
            System.out.println("Can not read: " + filePath);
        }

        return backpack;
    }
}
