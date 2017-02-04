package JDialogs.actionlisteners;

import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import GameComponents.Dice;
import character.Character;

public class BtnSaveActionListener implements ActionListener {
	
	private ArrayList<Component> dialogComponents;
	
	public BtnSaveActionListener(ArrayList<Component> dialogComponents){
		this.dialogComponents = dialogComponents;
	}
	
	public void actionPerformed(ActionEvent e) {
		Iterator<Component> componentIterator = this.dialogComponents.iterator();

		XStream xstream = new XStream(new StaxDriver());
		Character character = new Character();
		
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
		String xml = xstream.toXML(character);
		FileWriter out;
		try {
			out = new FileWriter("/Users/supreetuniversity/Documents/character.xml");
			out.write(xml);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
