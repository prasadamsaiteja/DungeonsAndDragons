package JDialogs.actionlisteners;

import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import GameComponents.SharedVariables;
import character.Character;

/**
 * Extends the action listener. Button:`Save` action when creating a new character. 
 * 
 * @author Supreet Singh (s_supree)
 */

public class BtnSaveActionListener implements ActionListener {
	
	private ArrayList<Component> dialogComponents;
	
	/**
	 * Initiate the action listener by providing an ArrayList of all the components that define a character
	 * 
	 * @param dialogComponents ArrayList
	 */
	public BtnSaveActionListener(ArrayList<Component> dialogComponents){
		this.dialogComponents = dialogComponents;
	}
	
	public void actionPerformed(ActionEvent e) {	
		Character character = new Character();
		
		// Iterate through the component list and set the appropriate value in character object
		Iterator<Component> componentIterator = this.dialogComponents.iterator();
		while (componentIterator.hasNext()){
			Component component = componentIterator.next();
			if (component instanceof JTextField) {
				JTextField txtField = (JTextField) component;				
				switch(txtField.getName()){
				case "name":
					character.setName(txtField.getText());
					break;
				case "level":
					character.setLevel(Integer.parseInt(txtField.getText()));
					break;
				case "strength":
					character.setStrength(Integer.parseInt(txtField.getText()));
					break;
				case "dexterity":
					character.setDexterity(Integer.parseInt(txtField.getText()));
					break;
				case "constitution":
					character.setConstitution(Integer.parseInt(txtField.getText()));
					break;
				}
			}else if(component instanceof JComboBox){
				JComboBox<String> cBox = (JComboBox<String>) component;
				Integer index = cBox.getSelectedIndex();
				String item = cBox.getItemAt(index);
				String name = cBox.getName();
				
				switch(name){
				case "class":
					character.setCharacterClass(item);
					break;
				}
			}
		}
		
		// Get the filename from the user for storing the character XML file
		String fname = JOptionPane.showInputDialog("Give file name");
		
		// Initiate a new XStream instance for creating XML and store the XML in fname 
		XStream xstream = new XStream(new StaxDriver());		
		String xml = xstream.toXML(character);
		FileWriter out;
		try {
			String filepath = SharedVariables.CharactersDirectory+File.separator+fname+".xml";
			System.out.println(filepath);
			out = new FileWriter(filepath);
			out.write(xml);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "File `"+fname+"` saved!!!");
	}
}
