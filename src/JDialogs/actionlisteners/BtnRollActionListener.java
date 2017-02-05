package JDialogs.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTextField;

import GameComponents.Dice;

/**
 * Extends the action listener. Button:`Roll` action when creating a new character. 
 * 
 * @author Supreet Singh (s_supree)
 *
 */

public class BtnRollActionListener implements ActionListener {
	
	private ArrayList<JTextField> txtFields;
	
	/**
	 * Initiate the action listener by providing the list of all text fields which define character abilities.
	 * @param txtFields
	 */
		public BtnRollActionListener(ArrayList<JTextField> txtFields){
		this.txtFields = txtFields;
	}
	
	public void actionPerformed(ActionEvent e) {
		// Initiate the dice object with a 4D6 (returns sum of highest 3 rolls)
		Dice dice = new Dice(4, 6, 3);
		
		// Iterate through each text field component and calculate the roll score
		Iterator<JTextField> txtFieldIterator = txtFields.iterator();
		while (txtFieldIterator.hasNext()){
			int txtFieldScore = dice.getRollSum();
			JTextField txtField = txtFieldIterator.next();
			txtField.setText(Integer.toString(txtFieldScore));	
		}
	}
}
