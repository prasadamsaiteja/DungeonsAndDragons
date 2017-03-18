package game.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import game.model.character.Character;
import game.model.character.CharactersList;

public class Inventory extends Observable implements Observer
{
    private Character character;
    
    /**
     * @param c character object
     */
    public Inventory(Character c)
    {
        this.character = c;
        this.character.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * get all backpack items for character
     * 
     * @return
     */
    public HashMap<String, ArrayList<String>> getBackpackItems(){
        try
        {
            return this.character.getBackpack().getAll();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * @return
     */
    public HashMap<String, String> getCharacterItems(){
        return this.character.getAllItems();
    }
    
    /**
     * @return
     * @throws IOException 
     */
    public HashMap<String, ArrayList<String>> getAllItems() throws IOException{
        HashMap<String, ArrayList<String>> itemsMap = new HashMap<String, ArrayList<String>>();
        Set<String> itemKeySet = new HashSet<String>();
        
        Set<String> backpackItemKeySet = this.character.getBackpack().getItemTypes();        
        if (backpackItemKeySet != null && !backpackItemKeySet.isEmpty())
            itemKeySet.addAll(backpackItemKeySet);
        
        HashMap<String, String> characterItems = this.character.getAllItems();
        
        
        Set<String> characterItemKeySet = null;
        if (characterItems != null && !characterItems.isEmpty()){
            characterItemKeySet = characterItems.keySet();
            itemKeySet.addAll(characterItemKeySet);
        }
        
        if (itemKeySet == null || itemKeySet.isEmpty()){
            System.out.println("No items present for character: either in backpack or wearing");
            return new HashMap<String, ArrayList<String>>();
        }
        
        
        characterItemKeySet.addAll(backpackItemKeySet);
        
        for (String k: characterItemKeySet){
            ArrayList<String> items = new ArrayList<String>();
            if (backpackItemKeySet.contains(k)){
                ArrayList<String> backpackItems = this.character.getBackpack().getByType(k);
                items.addAll(backpackItems);
            }
            
            if (characterItemKeySet.contains(k)){
                characterItemKeySet.add(characterItems.get(k));
            }
            
            itemsMap.put(k, items);
        }
        
        return itemsMap;
    }
    
    /**
     * @param c
     * @return
     */
    public static Inventory initialize(Character c)
    {
        return (new Inventory(c));
    }
    
    /**
     * @param args
     */
    public static void main(String[] args){
        ArrayList<Character> cList = CharactersList.get();
        Iterator<Character> i = cList.iterator();
        
        while (i.hasNext()){
            Character c = i.next();
            Inventory cI = c.getInventory();
            
            try
            {
                HashMap<String, ArrayList<String>> allItems = cI.getAllItems();
                Set<String> itemsKeySet = allItems.keySet();
                for (String ks: itemsKeySet){
                    System.out.println(ks);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }        
    }
}
