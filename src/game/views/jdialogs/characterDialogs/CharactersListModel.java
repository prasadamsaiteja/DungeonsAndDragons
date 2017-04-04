package game.views.jdialogs.characterDialogs;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import game.model.character.Character;
import game.model.character.CharactersList;

/**
 * This class extends the default list model. It's also observing characters
 * list class and updates the list model anytime a new character is added or
 * updated
 * 
 * @author Supreet Singh (s_supree)
 * @version 1.0.0
 */
public class CharactersListModel extends DefaultListModel<String> implements Observer
{

    private static final long serialVersionUID = 1698789327638828761L;

    /**
     * Default constructor that adds the observer
     */
    public CharactersListModel()
    {
        CharactersList cList = CharactersList.init();
        cList.addObserver(this);
        this.getCharacterList(cList);
    }

    /**
     * Get all the characters in the list and add them to the panel
     * 
     * @param CharactersList cList
     */
    private void getCharacterList(CharactersList cList)
    {
        ArrayList<Character> list = cList.getCharacters();
        this.removeAllElements();
        for (Character c : list)
        {
            String listElement = c.getName();
            this.addElement(listElement);
        }
    }

    /** 
     * Overriden method of the observer interface
     * 
     * @param o Observable 
     * @param arg Object
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof CharactersList)
        {
            CharactersList cList = ((CharactersList) o);
            this.getCharacterList(cList);
        }
    }
}
