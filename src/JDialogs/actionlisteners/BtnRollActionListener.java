package JDialogs.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTextField;

import GameComponents.Dice;

public class BtnRollActionListener implements ActionListener {
	
	private ArrayList<JTextField> txtFields;
	
	public BtnRollActionListener(ArrayList<JTextField> txtFields){
		this.txtFields = txtFields;
	}
	
	public void actionPerformed(ActionEvent e) {
		Iterator<JTextField> txtFieldIterator = txtFields.iterator();
		Dice dice = new Dice(4, 6, 3);
		while (txtFieldIterator.hasNext()){
			int txtFieldScore = dice.getRollSum();
			JTextField txtField = txtFieldIterator.next();
			txtField.setText(Integer.toString(txtFieldScore));	
		}
	}
}
