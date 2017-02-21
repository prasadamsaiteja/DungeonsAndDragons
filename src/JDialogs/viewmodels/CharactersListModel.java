package JDialogs.viewmodels;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import model.character.Character;
import model.character.CharactersList;

public class CharactersListModel extends DefaultListModel<String> implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1698789327638828761L;

	public CharactersListModel() {
		CharactersList cList = CharactersList.init();
		cList.addObserver(this);
		this.getCharacterList(cList);
	}
	
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