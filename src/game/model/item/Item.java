package game.model.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import game.components.ExtensionMethods;
import game.model.jaxb.ItemJaxb;

/**
 * This is an item class which is used to store the items currently used by the
 * character while playing the game. It affects various classes of selected
 * items based on the level of the character.
 * 
 * @author Priyanka A
 * 
 * @version 1.0
 * @since 2/24/2017
 */
@XmlRootElement(name = "Item")
public class Item
{

    @XmlElement(name = "itemName")
    public String itemName;
    @XmlElement(name = "itemType")
    public String itemType;
    @XmlElement(name = "itemClass")
    public String itemClass;
    @XmlElement(name = "itemLevel")
    public int itemLevel;
    @XmlElement(name = "weaponEnchatments")
    public ArrayList<String> weaponEnchatments;
    
    private int modifier;
    
    /**
     * This is the default constructor which create a new item 
     * @param name Name of the item
     * @param type type of the item
     * @param itemclass Class of item
     * @param level level of the item
     * @param selectedEnchantments selected enchantments for weapon
     */
    public Item(String name, String type, String itemclass, int level, ArrayList<String> selectedEnchantments)
    {
        this.itemName = name;
        this.itemType = type;
        this.itemClass = itemclass;
        this.itemLevel = level;
        this.weaponEnchatments = selectedEnchantments;
    }
    
    /**
     * Default constructor for Item class
     */
    public Item()
    {

    }

    /**
     * This method returns the item name
     * @return string of item name
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * This method returns item type
     * @return string of item type
     */
    public String getItemType()
    {
        return itemType;
    }

    /**
     * This method returns item modifier 
     * @return int item modifier.
     */
    public int getModifier()
    {
        modifier = (int) Math.ceil((double) itemLevel / (double) 4);        
        return modifier;
    }

    /**
     * This method gets the Items through a  key value mechanism 
     * @return a hash map containing all the items segment based on their item
     *         type
     */
    private static HashMap<String, ArrayList<Item>> getItems()
    {
        HashMap<String, ArrayList<Item>> hMap = new HashMap<String, ArrayList<Item>>();

        String[] items = ExtensionMethods.getItemsList();
        for (String item : items)
        {
            Item i = ItemJaxb.getItemFromXml(item);
            if (i != null)
            {
                if (hMap.containsKey(i.getItemType()))
                {
                    ArrayList<Item> hMapItemList = hMap.get(i.getItemType());
                    hMapItemList.add(i);
                }
                else
                {
                    ArrayList<Item> hMapItemList = new ArrayList<Item>();
                    hMap.put(i.itemType, hMapItemList);
                    hMapItemList.add(i);
                }
            }
        }
        return hMap;
    }

    /**
     * This method get all the item providing type and level of item
     * @param itemType Item type 
     * @param characterLevel level of the character
     * @return an array list of items based on provided item type and level
     */
    public static ArrayList<Item> getItems(String itemType, int characterLevel)
    {
        HashMap<String, ArrayList<Item>> hMap = getItems();

        if (!hMap.containsKey(itemType))
        {
            // no item found for this item type
            return new ArrayList<Item>();
        }

        ArrayList<Item> hMapList = hMap.get(itemType);
        Iterator<Item> hMapListIterator = hMapList.iterator();
        while (hMapListIterator.hasNext())
        {
            Item i = hMapListIterator.next();
            if (i.itemLevel > characterLevel)
            {
                hMapListIterator.remove();
            }
        }
        return hMapList;
    }
    
    public String getWeaponEnchatments(int index) {
    	return weaponEnchatments.get(index);
	}

}