package game.views.jdialogs.viewmodels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.DefaultListModel;

import game.model.character.Backpack;
import game.model.character.Character;

/**
 * This class extends the default list model. It's also observing characters
 * list class and updates the list model anytime a new character is added or
 * updated
 * 
 * @author Supreet Singh (s_supree)
 * @since 1.0.0
 */
public class BackpackDialogCharacterListModel extends DefaultListModel<String> implements Observer
{
    private Character c;
    private Backpack backpack;
    
    public BackpackDialogCharacterListModel(Character c)
    {
        this.c= c;
        this.c.addObserver(this);
        this.getCharacterItems();
    }

    /**
     * Get all the characters in the list and add them to the panel
     * 
     * @param CharactersList cList
     */
    private void getCharacterItems()
    {
        this.removeAllElements();
        HashMap<String, String> characterItems = this.c.getAllItems();     
        Set<String> itemsSet = characterItems.keySet();
        
        for (String k : itemsSet)
        {
            String itemName = characterItems.get(k);
            this.addElement(itemName);
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof Character)
        {
            this.getCharacterItems();
        }
    }
}
