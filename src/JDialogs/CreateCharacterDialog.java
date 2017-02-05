package JDialogs;

import javax.swing.JDialog;
import javax.swing.JTextField;

import JDialogs.actionlisteners.BtnRollActionListener;
import JDialogs.actionlisteners.BtnSaveActionListener;
import JDilaogs.DialogHelper;
import character.CharactersList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	public CreateCharacterDialog(JDialog jdialog, DefaultListModel<String> characterList){
		this.parent = jdialog;
		this.characterList = characterList;
		DialogHelper.setDialogProperties(this, "New Character", new Rectangle(0, 0, 600, 200));
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
		this.cbClass.setName("class");
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridx = 9;
		gbc.gridy = 0;
		contentPanel.add(this.cbClass, gbc);
		this.cbClass.addItem("Fighter");
		
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
		
		gbc.insets = new Insets(10, 5, 5, 5);
		
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridwidth = 2;
		gbc.gridx = 3;
		gbc.gridy = 4;
		contentPanel.add(this.btnRoll, gbc);
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 2;
		gbc.gridx = 6;
		gbc.gridy = 4;
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
