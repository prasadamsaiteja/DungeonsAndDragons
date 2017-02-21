package JDialogs.viewmodels;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import model.character.Character;
import model.character.CharactersList;

/**
 * This class extends the default list model. 
 * It's also observing characters list class and updates the list model anytime a new character
 * is added or updated
 * 
 * @author Supreet Singh (s_supree)
 * @since 1.0.0
 */
public class CharactersListModel extends DefaultListModel<String> implements Observer {

	private static final long serialVersionUID = 1698789327638828761L;

	public CharactersListModel() {
		CharactersList cList = CharactersList.init();
		cList.addObserver(this);
		this.getCharacterList(cList);
	}
	
	/**
	 * Get all the characters in the list and add them to the panel
	 * 
	 * @param CharactersList cList
	 */
	private void getCharacterList(CharactersList cList){
		ArrayList<Character> list = cList.getCharacters();
  	    this.removeAllElements();
		for (Character c : list){
	    	  String listElement = c.getName()+" (Level: "+c.getLevel()+")";
	          this.addElement(listElement);  
	     }
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof CharactersList){
			CharactersList cList = ((CharactersList) o);
			this.getCharacterList(cList);
		}
	}
}