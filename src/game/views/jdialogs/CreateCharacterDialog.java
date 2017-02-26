package game.views.jdialogs;

import javax.swing.JDialog;
import javax.swing.JTextField;

import game.components.Dice;
import game.model.Item;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.character.classes.CharacterClass;
import game.model.character.wearables.weapons.WeaponFactory;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("serial")
public class CreateCharacterDialog extends JDialog
{

    private JDialog parent;
    private JTextField txtName;
    private JTextField txtLvl;
    private JTextField txtStr;
    private JTextField txtDex;
    private JTextField txtCons;
    private JButton btnRoll;
    private JButton btnSave;
    private JComboBox<String> cbClass;
    private JComboBox<String> cbWeapon;
    private DefaultListModel<String> characterList;
    private Character character = null;
    private boolean characterExistsFlag = false;

    /**
     * Action Listener when roll button is pressed
     */
    private class BtnRollActionListener implements ActionListener
    {

        private ArrayList<JTextField> txtFields;

        /**
         * Initiate the action listener by providing the list of all text fields
         * which define character abilities.
         * 
         * @param txtFields
         */
        public BtnRollActionListener(ArrayList<JTextField> txtFields)
        {
            this.txtFields = txtFields;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Initiate the dice object with a 4D6 (returns sum of highest 3
            // rolls)
            Dice dice = new Dice(4, 6, 3);

            // Iterate through each text field component and calculate the roll
            // score
            Iterator<JTextField> txtFieldIterator = txtFields.iterator();
            while (txtFieldIterator.hasNext())
            {
                int txtFieldScore = dice.getRollSum();
                JTextField txtField = txtFieldIterator.next();
                txtField.setText(Integer.toString(txtFieldScore));
            }
        }
    }

    /**
     * Action Listener when save button is pressed
     */
    private class BtnSaveActionListener implements ActionListener
    {

        private ArrayList<Component> dialogComponents;
        private DefaultListModel<String> characterList;
        private Character character = null;

        /**
         * Initiate the action listener by providing an ArrayList of all the
         * components that define a character
         * 
         * @param dialogComponents ArrayList
         */
        public BtnSaveActionListener(ArrayList<Component> dialogComponents)
        {
            this.dialogComponents = dialogComponents;
        }

        public BtnSaveActionListener(ArrayList<Component> dialogComponents, Character existingCharacter)
        {
            this.dialogComponents = dialogComponents;
            this.character = existingCharacter;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (character == null)
                character = new Character();

            // Iterate through the component list and set the appropriate value
            // in character object
            Iterator<Component> componentIterator = this.dialogComponents.iterator();
            while (componentIterator.hasNext())
            {
                Component component = componentIterator.next();
                if (component instanceof JTextField)
                {
                    JTextField txtField = (JTextField) component;
                    switch (txtField.getName())
                    {
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
                }
                else if (component instanceof JComboBox)
                {
                    JComboBox<String> cBox = (JComboBox<String>) component;
                    cBox.addActionListener(new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                        }
                    });
                    Integer index = cBox.getSelectedIndex();
                    String item = cBox.getItemAt(index);
                    String name = cBox.getName();

                    switch (name)
                    {
                        case "characterClass":
                            character.setCharacterClass(item);
                            break;
                        case "weapon":
                            character.setWeaponName(item);
                            break;
                    }
                }
            }

            character.save();
            CharactersList cList = CharactersList.init();
            cList.updateCharactersList();
            JOptionPane.showMessageDialog(null, "Character saved!!!");
        }
    }

    public CreateCharacterDialog(JDialog jdialog)
    {
        this.parent = jdialog;
        DialogHelper.setDialogProperties(this, "New Character", new Rectangle(0, 0, 600, 250));
        this.btnRoll = new JButton("Roll");
        this.btnSave = new JButton("Save");
        this.txtStr = new JTextField();
        this.drawDialog();
        this.initActionListeners();
    }

    public CreateCharacterDialog(JDialog jdialog, Character existingCharacter)
    {
        this.parent = jdialog;
        this.character = existingCharacter;
        this.characterExistsFlag = true;
        DialogHelper.setDialogProperties(this, this.character.getName(), new Rectangle(0, 0, 600, 250));
        this.btnSave = new JButton("Save");
        this.txtStr = new JTextField();
        this.drawDialog();
        this.initActionListeners();
    }

    public void drawDialog()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]
        { 36, 11, 77, 39, 20, 32, 49, 34, 93, 84, 0 };
        gridBagLayout.rowHeights = new int[]
        { 27, 16, 26, 29, 0 };
        gridBagLayout.columnWeights = new double[]
        { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]
        { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        // getContentPane().setLayout(gridBagLayout);

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
        if (this.characterExistsFlag)
        {
            this.txtName.setText(this.character.getName());
            this.txtName.setEditable(false);
        }

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

        if (this.characterExistsFlag)
        {
            this.txtLvl.setText(String.valueOf(this.character.getLevel()));
            this.txtLvl.setEditable(false);
        }
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
        // this.cbClass.setModel(al);
        this.cbClass.setName("characterClass");

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 9;
        gbc.gridy = 0;
        contentPanel.add(this.cbClass, gbc);
        Set<String> characterClasses = CharacterClass.getAllowedClasses();

        for (String cClass : characterClasses)
        {
            this.cbClass.addItem(cClass);
        }

        if (this.characterExistsFlag)
        {
            this.cbClass.setSelectedItem(this.character.getCharacterClass());
            this.cbClass.setEnabled(false);
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
        if (this.characterExistsFlag)
        {
            this.txtStr.setText(String.valueOf(this.character.getOriginalStrength()));
        }
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
        if (this.characterExistsFlag)
        {
            this.txtDex.setText(String.valueOf(this.character.getOriginalDexterity()));
        }
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
        if (this.characterExistsFlag)
        {
            this.txtCons.setText(String.valueOf(this.character.getOriginalConstitution()));
        }
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

        this.cbWeapon = new JComboBox<String>();
        this.cbWeapon.setName("weapon");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 3;
        contentPanel.add(this.cbWeapon, gbc);

        int level = 1;
        if (this.characterExistsFlag)
        {
            level = this.character.getLevel();
        }

        ArrayList<Item> weapons = Item.getItems("Weapon", level);
        Iterator<Item> weaponsIterator = weapons.iterator();
        while (weaponsIterator.hasNext())
        {
            Item weapon = weaponsIterator.next();
            this.cbWeapon.addItem(weapon.itemName);
        }

        if (this.characterExistsFlag)
        {
            this.cbWeapon.setSelectedItem(String.valueOf(this.character.getWeaponName()));
        }

        gbc.insets = new Insets(10, 5, 5, 5);

        // don't show roll button if character is in edit mode
        if (!this.characterExistsFlag)
        {
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridwidth = 2;
            gbc.gridx = 3;
            gbc.gridy = 6;
            contentPanel.add(this.btnRoll, gbc);
        }

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 2;
        gbc.gridx = 6;
        gbc.gridy = 6;
        contentPanel.add(this.btnSave, gbc);

        getContentPane().add(contentPanel);
    }

    public void initActionListeners()
    {

        // don't initialize roll action if the character is in edit mode
        if (!this.characterExistsFlag)
        {
            ArrayList<JTextField> txtFieldAbilities = new ArrayList<JTextField>();
            txtFieldAbilities.add(this.txtStr);
            txtFieldAbilities.add(this.txtDex);
            txtFieldAbilities.add(this.txtCons);
            ActionListener btnRollActionListener = new BtnRollActionListener(txtFieldAbilities);
            this.btnRoll.addActionListener(btnRollActionListener);
        }

        ArrayList<Component> dialogComponents = new ArrayList<Component>();
        dialogComponents.add(this.txtStr);
        dialogComponents.add(this.txtDex);
        dialogComponents.add(this.txtCons);
        dialogComponents.add(this.txtName);
        dialogComponents.add(this.txtLvl);
        dialogComponents.add(this.cbClass);
        dialogComponents.add(this.cbWeapon);

        ActionListener btnSaveActionListener;
        if (this.characterExistsFlag)
            btnSaveActionListener = new BtnSaveActionListener(dialogComponents, this.character);
        else
            btnSaveActionListener = new BtnSaveActionListener(dialogComponents);

        this.btnSave.addActionListener(btnSaveActionListener);
    }

}
