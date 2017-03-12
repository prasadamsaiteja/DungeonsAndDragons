package game.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import game.components.SharedVariables;

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
    private int maxAllowedItems = 17;
    private static String fileName = "backpack.xml";
    private HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
    private static Backpack _inst = null;

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
     * notify observers of a change in state
     */
    private void update()
    {
        this.setChanged();
        this.notifyObservers();
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
        update();
    }

    /**
     * saves the state of backpack in xml file
     * 
     * @throws IOException if save file fails
     */
    public void save() throws IOException // NO_UCD (unused code)
    {
        String filePath = SharedVariables.DataDirectory + File.separator + fileName;

        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(this);
        FileWriter out;
        try
        {
            out = new FileWriter(filePath);
            out.write(xml);
            out.close();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
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
     * Initializes backpack
     * 
     * @return Backpack instance
     */
    public static Backpack init()
    {
        /*
         * Flow: - Checks if an existing instance exists and returns it - if
         * not, then it checks data folder for existence of xml file and if
         * found, uses it to load the instance - else, returns a new instance
         */
        if (Backpack._inst == null)
        {
            // if backpack xml file exist then read it and load it
            String filePath = SharedVariables.DataDirectory + File.separator + fileName;
            File f = new File(filePath);
            if (f.exists() && f.canRead())
            {
                BufferedReader r;
                try
                {
                    r = new BufferedReader(new FileReader(f.getPath()));
                    String sCurrentLine, xml = "";
                    try
                    {
                        while ((sCurrentLine = r.readLine()) != null)
                        {
                            xml += sCurrentLine;
                        }
                        XStream xstream = new XStream(new StaxDriver());
                        Backpack._inst = (Backpack) xstream.fromXML(xml);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
            }
            else
            {
                Backpack._inst = new Backpack();
            }
        }

        return Backpack._inst;
    }

    /**
     * Resets Backpack and initializes
     * 
     * @param forceReset true or false
     * @return Backpack instance
     */
    public static Backpack init(boolean forceReset) // NO_UCD (unused code)
    {
        /*
         * if forced, it will delete any existence instance or file and reset
         * backpack
         */
        if (forceReset)
        {
            Backpack._inst = null;
            String filePath = SharedVariables.DataDirectory + File.separator + fileName;
            File f = new File(filePath);
            f.delete();
        }
        return Backpack.init();
    }

    public static Backpack reload() // NO_UCD (unused code)
    {
        Backpack._inst = null;
        return Backpack.init();
    }

}
