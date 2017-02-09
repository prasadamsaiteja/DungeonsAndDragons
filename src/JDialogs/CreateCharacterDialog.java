package JDialogs;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import GameComponents.Dice;
import GameComponents.SharedVariables;
import JDilaogs.DialogHelper;
import character.Character;
import character.classes.CharacterClassFactory;
import character.wearables.weapons.WeaponFactory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class CreateCharacterDialog extends JDialog {
	private JDialog parent;
	private JTextField txtName;
	private JTextField txtLvl;
	private JTextField txtStr;
	private JTextField txtDex;
	private JTextField txtCons;
	private JButton btnRoll;
	private JButton btnSave;
	private JComboBox<String> cbClass;
	private DefaultListModel<String> characterList;
	
	/**
	 * Action Listener when roll button is pressed
	 */
	private class BtnRollActionListener implements ActionListener {
		
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
	
	/**
	 * Action Listener when save button is pressed
	 */
	private class BtnSaveActionListener implements ActionListener {
		
		private ArrayList<Component> dialogComponents;
		private DefaultListModel<String> characterList;
		
		/**
		 * Initiate the action listener by providing an ArrayList of all the components that define a character
		 * 
		 * @param dialogComponents ArrayList
		 */
		public BtnSaveActionListener(ArrayList<Component> dialogComponents, DefaultListModel<String> characterList){
			this.dialogComponents = dialogComponents;
			this.characterList = characterList;
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
			
			character.build();
			
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
			
			String listElement = character.getName()+" (Level: "+character.getLevel()+")";
			this.characterList.addElement(listElement);
			
			JOptionPane.showMessageDialog(null, "File `"+fname+"` saved!!!");
		}
	}
	
	private class LevelsComboBoxModel implements ComboBoxModel{

		private ArrayList<Integer> levels;
		
		public LevelsComboBoxModel() {
			for (int ctr=0; ctr<10; ctr++){
				this.levels.add(ctr+1);
			}
		}
		
		@Override
		public int getSize() {
			return this.levels.size();
		}

		@Override
		public Object getElementAt(int index) {
			return this.levels.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setSelectedItem(Object anItem) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object getSelectedItem() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public CreateCharacterDialog(JDialog jdialog, DefaultListModel<String> characterList){
		this.parent = jdialog;
		this.characterList = characterList;
		DialogHelper.setDialogProperties(this, "New Character", new Rectangle(0, 0, 600, 250));
		this.btnRoll = new JButton("Roll");
		this.btnSave = new JButton("Save");
		this.txtStr = new JTextField();
		this.drawDialog();
		this.initActionListeners();
	}
	
	public void drawDialog(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{36, 11, 77, 39, 20, 32, 49, 34, 93, 84, 0};
		gridBagLayout.rowHeights = new int[]{27, 16, 26, 29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		//getContentPane().setLayout(gridBagLayout);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(gridBagLayout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		
		JLabel Name = new JLabel("Name");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		contentPanel.add(Name, gbc);
		
		this.txtName = new JTextField();
		this.txtName.setColumns(10);
		this.txtName.setName("name");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 4;
		gbc.gridx = 1;
		gbc.gridy = 0;
		contentPanel.add(this.txtName, gbc);
		
		JLabel lblLevel = new JLabel("Level");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 5;
		gbc.gridy = 0;
		contentPanel.add(lblLevel, gbc);
		
		txtLvl = new JTextField();
		txtLvl.setColumns(4);
		this.txtLvl.setName("level");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		contentPanel.add(this.txtLvl, gbc);
		
		JLabel lblClass = new JLabel("Class");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 8;
		gbc.gridy = 0;
		contentPanel.add(lblClass, gbc);
		
		this.cbClass = new JComboBox<String>();
		ComboBoxModel cbBoxModel = new DefaultComboBoxModel(); 
		this.cbClass.setModel(al);
		this.cbClass.setName("class");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 9;
		gbc.gridy = 0;
		contentPanel.add(this.cbClass, gbc);
		ArrayList<String> characterClasses = CharacterClassFactory.getAllowedClasses();
		Iterator<String> characterClassesIterator = characterClasses.iterator();
		
		while(characterClassesIterator.hasNext())
		{
			String cClass = characterClassesIterator.next();
			this.cbClass.addItem(cClass);
		}
		
		JLabel lblAbilities = new JLabel("Abilities:");
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPanel.add(lblAbilities, gbc);
		
		JLabel lblStrength = new JLabel("Strength");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 2;
		contentPanel.add(lblStrength, gbc);
		
		this.txtStr.setColumns(10);
		this.txtStr.setEditable(false);
		this.txtStr.setName("strength");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2;
		gbc.gridy = 2;
		contentPanel.add(this.txtStr, gbc);
		
		JLabel lblDexterity = new JLabel("Dexterity");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		gbc.gridx = 4;
		gbc.gridy = 2;
		contentPanel.add(lblDexterity, gbc);
		
		this.txtDex = new JTextField();
		this.txtDex.setEditable(false);
		this.txtDex.setColumns(10);
		this.txtDex.setName("dexterity");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 6;
		gbc.gridy = 2;
		contentPanel.add(this.txtDex, gbc);
		
		JLabel lblConstitution = new JLabel("Constitution");
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 8;
		gbc.gridy = 2;
		contentPanel.add(lblConstitution, gbc);
		
		this.txtCons = new JTextField();
		this.txtCons.setEditable(false);
		this.txtCons.setColumns(10);
		this.txtCons.setName("constitution");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 9;
		gbc.gridy = 2;
		contentPanel.add(this.txtCons, gbc);
		
		JLabel Weapons = new JLabel("Weapon");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 3;
		contentPanel.add(Weapons, gbc);
		
		this.cbClass = new JComboBox<String>();
		this.cbClass.setName("weapon");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2;
		gbc.gridy = 3;
		contentPanel.add(this.cbClass, gbc);
		ArrayList<String> weapons = WeaponFactory.getAllowedWeapons(1);
		Iterator<String> weaponsIterator = weapons.iterator();
		while (weaponsIterator.hasNext()){
			String weapon = weaponsIterator.next();
			this.cbClass.addItem(weapon);
		}
		
		gbc.insets = new Insets(10, 5, 5, 5);
		
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridwidth = 2;
		gbc.gridx = 3;
		gbc.gridy = 6;
		contentPanel.add(this.btnRoll, gbc);
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 2;
		gbc.gridx = 6;
		gbc.gridy = 6;
		contentPanel.add(this.btnSave, gbc);
		
		getContentPane().add(contentPanel);
	}
	
	public void initActionListeners(){
		ArrayList<JTextField> txtFieldAbilities = new ArrayList<JTextField>();
		txtFieldAbilities.add(this.txtStr);
		txtFieldAbilities.add(this.txtDex);
		txtFieldAbilities.add(this.txtCons);
		ActionListener btnRollActionListener = new BtnRollActionListener(txtFieldAbilities);	
		this.btnRoll.addActionListener(btnRollActionListener);
		
		ArrayList<Component> dialogComponents = new ArrayList<Component>();
		dialogComponents.add(this.txtStr);
		dialogComponents.add(this.txtDex);
		dialogComponents.add(this.txtCons);
		dialogComponents.add(this.txtName);
		dialogComponents.add(this.txtLvl);
		dialogComponents.add(this.cbClass);
		ActionListener btnSaveActionListener = new BtnSaveActionListener(dialogComponents, this.characterList);	
		this.btnSave.addActionListener(btnSaveActionListener);	
	}
	
}



