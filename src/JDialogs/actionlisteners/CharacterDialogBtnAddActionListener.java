package JDialogs.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;

import JDialogs.CreateCharacterDialog;
import JDilaogs.CreateStuffDialog;

public class CharacterDialogBtnAddActionListener implements ActionListener {
	private DefaultListModel<String> characterList;
	private CreateStuffDialog jdialog;
	
	public CharacterDialogBtnAddActionListener(CreateStuffDialog jdialog, DefaultListModel<String> characterList){
		this.jdialog = jdialog;
		this.characterList = characterList;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new CreateCharacterDialog(this.jdialog, this.characterList);
	}
}
