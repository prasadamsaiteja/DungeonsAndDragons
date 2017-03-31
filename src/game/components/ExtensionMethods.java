package game.components;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Multimap;

import java.util.Map.Entry;

import game.model.character.Character;
import game.model.character.CharactersList;

/**
 * This class contains essential methods required by game components
 * 
 * @author saiteja prasadam
 * @since 2/5/2017
 */
public class ExtensionMethods
{

    /**
     * This method returns all the maps saved
     * 
     * @return List of maps names saved
     */
    public static String[] getMapsList()
    {

        if (!new File(SharedVariables.MapsDirectory).exists())
            return new String[0];

        File[] fileList = new File(SharedVariables.MapsDirectory).listFiles();
        String[] fileName = new String[fileList.length];

        for (int i = 0; i < fileList.length; i++)
            if (fileList[i].getName().endsWith(".xml"))
                fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");

        return fileName;
    }
    
    /**
     * This method returns all the saved games saved
     * 
     * @return List of saved games names saved
     */
    public static String[] getSavedGamesList()
    {

        if (!new File(SharedVariables.SavedGamesDirectory).exists())
            return new String[0];

        File[] fileList = new File(SharedVariables.SavedGamesDirectory).listFiles();
        String[] fileName = new String[fileList.length];

        for (int i = 0; i < fileList.length; i++)
            if (fileList[i].getName().endsWith(".xml"))
                fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");

        return fileName;
    }

    /**
     * This method returns all the items saved
     * 
     * @return List of items names saved
     */
    public static String[] getItemsList()
    {

        if (!new File(SharedVariables.ItemsDirectory).exists())
            return new String[0];

        File[] fileList = new File(SharedVariables.ItemsDirectory).listFiles();
        String[] fileName = new String[fileList.length];

        for (int i = 0; i < fileList.length; i++)
            if (fileList[i].getName().endsWith(".xml"))
                fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");

        return fileName;
    }

    /**
     * This method returns all the campaign saved
     * 
     * @return List of campaigns names saved
     */
    public static String[] getCampaignsList()
    {
        File f = new File(SharedVariables.CampaignsDirectory);
        if (!f.exists())
        {
            return new String[0];
        }
        File[] fileList = f.listFiles();
        String[] fileName = new String[fileList.length];
        for (int i = 0; i < fileList.length; i++)
        {
            if (fileList[i].getName().endsWith(".xml"))
                fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");

        }
        return fileName;
    }

    /**
     * This method returns all the characters saved
     * @return List of characters names saved
     */
    public static String[] getCharacterList(){
        ArrayList<String> chars = CharactersList.getNames();
        return chars.toArray(new String[chars.size()]);
    }
    
    /**
     * This method plays a error sound, to let the user know something is wrong
     */
    public static void playErrorSound(){
      
        try{
          new Thread(new Runnable() {
            
            @Override
            public void run() {
              final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
              if (runnable != null) runnable.run();
            }
          }).start();
          
        }
        
        catch(Exception ignored){}
    }

    /**
     * It returns the key from the value given
     * @param backpackItems Items Hashmap
     * @param searchValue value 
     * @return Entry Returned Key and Value
     */
    public static Entry<String, String> getByValue(Multimap<String, String> backpackItems, String searchValue){
                       
        for (Entry<String, String> entry : backpackItems.entries()) {
            if (entry.getValue() != null && entry.getValue().equals(searchValue)) 
                return entry;            
        }
        return null;
    }
    
    /**
     * It returns the key from the value given
     * @param backpackItems Items Hashmap
     * @param searchValue value 
     * @return Entry Returned Key and Value
     */
    public static Entry<String, String> getByValue(HashMap<String, String> backpackItems, String searchValue){
                       
        for (Entry<String, String> entry : backpackItems.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(searchValue)) 
                return entry;            
        }
        return null;
    }
    
    /**
     * Gets all the Item names for the character
     * @param character character object
     * @return Collection character Items
     */
    public static Collection<? extends String> fetchAllItemNames(Character character) {
        
        Set<String> characterItems = new HashSet<>();
        
        for (String string : ((Character) character).getAllItems().values()) {
            if(string != null)
                characterItems.add(string);
        }
                            
        for (String string : ((Character) character).backpack.getBackpackItems()) {
            if(string != null)
                characterItems.add(string);
        }  
        
        return characterItems;
    }

    /**
     * Gets all the Fetched Items of the character
     * @param character character object
     * @return List Equiped Items
     */
    public static ArrayList<String> fetchEquipedItems(Character character) {
        
        ArrayList<String> characterItems = new ArrayList<>();
        
        for (String string : character.getAllItems().values()) {
            if(string != null)
                characterItems.add(string);
        }           
        
        return characterItems;
    }
    
    /**
     * Gets all the Fetched Items of the character
     * @param character character object
     * @return List Backpack Items
     */
    public static ArrayList<String> fetchBackpackItems(Character character) {
        
        ArrayList<String> characterItems = new ArrayList<>();
           
        for (String string : character.backpack.getBackpackItems()) {
            if(string != null)
                characterItems.add(string);
        }  
        
        return characterItems;
    }
}