package game.model.character;

import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * This Class is a backpack for Items to collect during a Gameplay by a character 
 * @author saiTeja
 * @version 1.0.0
 */
public class Backpack {
       
    //public HashMap<String, String> backpackItems;
    public Multimap<String, String> backpackItems;
    
    /**
     * Constructor for backpack for initialization of backpackItems
     */
    public Backpack(){
        backpackItems = ArrayListMultimap.create();
    }

    /**
     * This method returns the backpack items of a character
     * @return collection items in backpack
     */
    public Collection<String> getBackpackItems() {
        return backpackItems.values();
    }       

}