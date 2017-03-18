package game.views.jdialogs.viewmodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.DefaultListModel;

import game.model.Item;
import game.model.character.Backpack;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.jaxb.ItemJaxb;

/**
 * This class extends the default list model. It's also observing characters
 * list class and updates the list model anytime a new character is added or
 * updated
 * 
 * @author Supreet Singh (s_supree)
 * @since 1.0.0
 */
public class BackpackDialogBackpackListModel extends DefaultListModel<String> implements Observer
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Character c;
    private Backpack backpack;
    
    public BackpackDialogBackpackListModel(Character c)
    {
        this.c= c;
        try
        {
            this.backpack = this.c.getBackpack();
            this.backpack.addObserver(this);
            this.getBackPackItems();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get all the characters in the list and add them to the panel
     * 
     * @param CharactersList cList
     */
    private void getBackPackItems()
    {
        this.removeAllElements();
        HashMap<String, ArrayList<String>> backpackItems = this.backpack.getAll();     
        Set<String> itemsSet = backpackItems.keySet();
        
        for (String k : itemsSet)
        {
            Iterator<String> iItems = backpackItems.get(k).iterator();
            while (iItems.hasNext()){
                String itemName = iItems.next();
                this.addElement(itemName);
            }
        }
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof Backpack)
        {
            this.getBackPackItems();
        }
    }
}
