package game.views.jdialogs;

import javax.swing.JDialog;
import javax.swing.JTextField;

import game.model.Item;
import game.model.builder.FighterDriverClass;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.character.classes.CharacterClass;
import game.model.character.classes.CharacterClassStructure;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This Class is for the dialog that creates the character with a set of properties
 * 
 * @author Supreet
 * @version 1.0.0
 */
@SuppressWarnings("serial")
class CreateCharacterDialog extends JDialog
{

    @SuppressWarnings("unused")
    private JDialog parent;
    private JTextField txtName;
    private JTextField txtStr;
    private JTextField txtDex;
    private JTextField txtCons;
    private JButton btnRoll;
    private JButton btnSave;
    private JComboBox<String> cbLvl;
    private JComboBox<String> cbClass;
    private JComboBox<String> cbType;
    private JComboBox<String> cbWeapon;
    private JComboBox<String> cbArmor;
    private JComboBox<String> cbRing;
    private JComboBox<String> cbHelmet;
    private JComboBox<String> cbShield;
    private JComboBox<String> cbBoots;
    private JComboBox<String> cbBelt;
    private Character character = null;
    private boolean characterExistsFlag = false;

    /**
     * Action Listener when save button is pressed
     */
    private class BtnSaveActionListener implements ActionListener
    {

        private ArrayList<Component> dialogComponents;
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

        /**
         * Initiate the action listener by providing an ArrayList of all the
         * components that define a character
         * 
         * @param dialogComponents ArrayList
         * @param existingCharacter character
         */
        public BtnSaveActionListener(ArrayList<Component> dialogComponents, Character existingCharacter)
        {
            this.dialogComponents = dialogComponents;
            this.character = existingCharacter;
        }

        /**
         * Called when action is performed 
         * 
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (character == null)
                character = new Character();

            Iterator<Component> ci1 = this.dialogComponents.iterator();

            // if ability modifiers have not been set yet, then show a dialog
            while (ci1.hasNext())
            {
                Component c1 = ci1.next();
                if (c1 instanceof JTextField)
                {
                    JTextField txtField1 = (JTextField) c1;
                    switch (txtField1.getName())
                    {
                        case "name":
                            if (txtField1.getText().length() < 4)
                            {
                                DialogHelper.showBasicDialog("Character name needs to be atleast 4 characters long");
                                return;
                            }
                        case "strength":
                        case "dexterity":
                        case "constitution":
                            if (txtField1.getText().equals(""))
                            {
                                DialogHelper.showBasicDialog("Ability modifiers need to be rolled");
                                return;
                            }
                    }
                }
            }

            Iterator<Component> ci2 = this.dialogComponents.iterator();

            // Iterate through the component list and set the appropriate value
            // in character object
            while (ci2.hasNext())
            {
                Component c2 = ci2.next();
                if (c2 instanceof JTextField)
                {
                    JTextField txtField = (JTextField) c2;
                    switch (txtField.getName())
                    {
                        case "name":
                            character.setName(txtField.getText());
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
                else if (c2 instanceof JComboBox)
                {
                    @SuppressWarnings("unchecked")
                    JComboBox<String> cBox = (JComboBox<String>) c2;
                    Integer index = cBox.getSelectedIndex();
                    String item = cBox.getItemAt(index);
                    String name = cBox.getName();

                    switch (name)
                    {
                        case "characterClass":
                            character.setCharacterClass(item);
                            break;
                            
                        case "characterType":
                            System.out.println(item);
                            character.setCharacterType(item);
                            break;
                            
                        case "level":
                            character.setLevel(Integer.valueOf(item));
                            break;
                            
                        case "weapon":
                            character.setWeaponName(item);
                            break;
                            
                        case "armor":
                            character.setArmor(item);
                            break;
                            
                        case "shield":
                            character.setShield(item);
                            break;
                            
                        case "boots":
                            character.setBoots(item);
                            break;
                            
                        case "ring":
                            character.setRingName(item);
                            break;
                            
                        case "belt":
                            character.setBeltName(item);
                            break;
                            
                        case "helmet":
                            character.setHelmet(item);
                            break;
                    }
                }
            }

            character.save();
            CharactersList cList = CharactersList.init();
            cList.updateCharactersList();
            dispose();
        }
    }

    /**
     * This class is for updating  Items list
     * 
     * @author Supreet
 	 * @version 1.0.0
     */
    private class ItemsListUpdater
    {
        private HashMap<String, JComboBox<String>> itemList = new HashMap<String, JComboBox<String>>();
        private int level = 1;

        /**
         * This method adds the item combo box
         * @param itemType type of item into cbModel
         * @param cbModel data model
         */
        public void addItemComboBox(String itemType, JComboBox<String> cbModel)
        {
            this.itemList.putIfAbsent(itemType, cbModel);
            this.draw(itemType);
        }

        /**
         * This method sets the level
         * @param level current level of character
         */
        public void setLevel(int level)
        {
            this.level = level;
            this.draw();
        }

        /**
         * This method draws the items
         */
        public void draw()
        {
            Set<String> itemTypes = itemList.keySet();
            for (String itemType : itemTypes)
            {
                draw(itemType);
            }
        }

        /**
         * This method draws the items of specific type
         * @param itemType type of item
         */
        public void draw(String itemType)
        {
            if (itemList.containsKey(itemType))
            {
                JComboBox<String> cbModel = itemList.get(itemType);
                cbModel.removeAllItems();
                ArrayList<Item> items = Item.getItems(itemType, level);
                Iterator<Item> itemsIterator = items.iterator();
                while (itemsIterator.hasNext())
                {
                    Item item = itemsIterator.next();
                    cbModel.addItem(item.getItemName());
                }
            }
        }
    }
    
    /**
     * This class is for updating  Items list
     * @author Supreet
 	 * @version 1.0.0
     */
    private class CharacterTypeUpdater
    {
        private HashMap<String, JComboBox<String>> characterType = new HashMap<String, JComboBox<String>>();
        private String characterClass = null;

        /**
         * This method adds the character type combo box
         * @param cType type of character into cbModel
         * @param cbModel data model
         */
        public void addCharacterTypeComboBox(String cType, JComboBox<String> cbModel)
        {
            this.characterType.putIfAbsent(cType, cbModel);
            this.draw(cType);
        }

        /**
         * This method sets the character Class
         * @param characterClass class of character
         */
        public void setClass(String characterClass)
        {
            this.characterClass = characterClass;
            this.draw();
        }

        /**
         * This method draws the character type 
         */
        public void draw()
        {
            Set<String> cTypes = characterType.keySet();
            for (String cType : cTypes)
            {
                draw(cType);
            }
        }

        /**
         * This method draws the character of specific type 
         * @param cType character type
         */
        public void draw(String cType)
        {
            if (characterType.containsKey(cType))
            {
                JComboBox<String> cbModel = characterType.get(cType);
                cbModel.removeAllItems();
                try
                {
                    if (this.characterClass != null)
                    {
                        CharacterClass cClass = new CharacterClass(this.characterClass, null);
                        CharacterClassStructure cClassStructure = cClass.get();
                        ArrayList<String> types = cClassStructure.getTypes();
                        Iterator<String> iTypes = types.iterator();
                        
                        while (iTypes.hasNext()){
                            cbModel.addItem(iTypes.next());
                        }
                    }
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Constructor that initializes and sets the properties for the selected dialog
     * @param jdialog dialog that shows the character components
     */
    CreateCharacterDialog(JDialog jdialog)
    {
        this.parent = jdialog;
        DialogHelper.setDialogProperties(this, "New Character", new Rectangle(0, 0, 700, 320));
        this.btnRoll = new JButton("Roll");
        this.btnSave = new JButton("Save");
        this.txtStr = new JTextField();
        this.drawDialog();
        this.initActionListeners();
    }

    /**
     * Constructor that initializes and sets the properties for the selected dialog with a exisiting character     * @param jdialog
     * @param existingCharacter object
     */
    CreateCharacterDialog(JDialog jdialog, Character existingCharacter)
    {
        this.parent = jdialog;
        this.character = existingCharacter;
        this.characterExistsFlag = true;
        DialogHelper.setDialogProperties(this, this.character.getName(), new Rectangle(0, 0, 800, 320));
        this.btnSave = new JButton("Save");
        this.txtStr = new JTextField();
        this.drawDialog();
        this.initActionListeners();
    }

    /**
     * This method creates the dailog with GridBagLayout that has a components 
     */
    private void drawDialog()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]
        { 36, 11, 77, 39, 20, 32, 49, 34, 93, 84, 0 };
        gridBagLayout.rowHeights = new int[]
        { 27, 16, 26, 29, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[]
        { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]
        { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        // getContentPane().setLayout(gridBagLayout);

        int x = 0;
        int y = 0;
        // create a new instance of items list updater
        ItemsListUpdater listUpdater = new ItemsListUpdater();
        
        // create a new instance of character type list updater
        CharacterTypeUpdater characterListUpdater = new CharacterTypeUpdater();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel Name = new JLabel("Name");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = x;
        gbc.gridy = y;
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
        gbc.gridx = x+1;
        gbc.gridy = y;
        contentPanel.add(this.txtName, gbc);

        JLabel lblLevel = new JLabel("Level");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = x+7;
        gbc.gridy = y;
        contentPanel.add(lblLevel, gbc);

        cbLvl = new JComboBox<String>();
        this.cbLvl.setName("level");

        for (int i = 1; i <= 30; i++)
        {
            this.cbLvl.addItem(String.valueOf(i));
        }

        this.cbLvl.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                listUpdater.setLevel(Integer.valueOf(cbLvl.getSelectedItem().toString()));
            }
        });

        if (this.characterExistsFlag)
        {
            this.cbLvl.setSelectedItem(String.valueOf(this.character.getLevel()));
            this.cbLvl.setEnabled(false);
        }

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+8;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        contentPanel.add(this.cbLvl, gbc);

        y += 1;
        JLabel lblClass = new JLabel("Class");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        contentPanel.add(lblClass, gbc);

        this.cbClass = new JComboBox<String>();
        this.cbClass.setName("characterClass");

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = x+1;
        gbc.gridy = y;
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
        
        this.cbClass.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                characterListUpdater.setClass(cbClass.getSelectedItem().toString());
            }
        });
        
        JLabel lblType = new JLabel("Type");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = x+7;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        contentPanel.add(lblType, gbc);

        this.cbType = new JComboBox<String>();
        this.cbType.setName("characterType");

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = x+8;
        gbc.gridy = y;
        contentPanel.add(this.cbType, gbc);
        
        characterListUpdater.addCharacterTypeComboBox("type", this.cbType);
        characterListUpdater.setClass(cbClass.getSelectedItem().toString());
        
        if (this.characterExistsFlag)
        {
            System.out.println(this.character.getCharacterType());
            this.cbType.setSelectedItem(this.character.getCharacterType());
            this.cbType.setEnabled(false);
        }
       
        y += 1;
        JLabel lblAbilities = new JLabel("Abilities:");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 2;
        gbc.gridx = x;
        gbc.gridy = y;
        contentPanel.add(lblAbilities, gbc);

        y += 1;
        JLabel lblStrength = new JLabel("Strength");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        gbc.gridx = x;
        gbc.gridy = y;
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
        gbc.gridx = x+2;
        gbc.gridy = y;
        contentPanel.add(this.txtStr, gbc);

        JLabel lblDexterity = new JLabel("Dexterity");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        gbc.gridx = x+4;
        gbc.gridy = y;
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
        gbc.gridx = x+6;
        gbc.gridy = y;
        contentPanel.add(this.txtDex, gbc);

        JLabel lblConstitution = new JLabel("Constitution");
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = x+8;
        gbc.gridy = y;
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
        gbc.gridx = x+9;
        gbc.gridy = y;
        contentPanel.add(this.txtCons, gbc);

        y += 1;
        JLabel lblItems = new JLabel("Items:");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 2;
        gbc.gridx = x;
        gbc.gridy = y;
        contentPanel.add(lblItems, gbc);

        y += 1;
        JLabel Weapons = new JLabel("Weapon");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = x;
        gbc.gridy = y;
        contentPanel.add(Weapons, gbc);

        this.cbWeapon = new JComboBox<String>();
        this.cbWeapon.setName("weapon");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+2;
        gbc.gridy = y;
        contentPanel.add(this.cbWeapon, gbc);

        listUpdater.addItemComboBox("Weapon", this.cbWeapon);

        if (this.characterExistsFlag)
        {
            this.cbWeapon.setSelectedItem(String.valueOf(this.character.getWeaponName()));
        }

        // Add Armor combo box
        JLabel Armors = new JLabel("Armor");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = x+4;
        gbc.gridy = y;
        contentPanel.add(Armors, gbc);

        this.cbArmor = new JComboBox<String>();
        this.cbArmor.setName("armor");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+6;
        gbc.gridy = y;
        contentPanel.add(this.cbArmor, gbc);

        listUpdater.addItemComboBox("Armor", this.cbArmor);

        if (this.characterExistsFlag)
        {
            this.cbArmor.setSelectedItem(String.valueOf(this.character.getArmor()));
        }

        // Add Shield combo box
        JLabel Shields = new JLabel("Shield");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = x+8;
        gbc.gridwidth = 1;
        gbc.gridy = y;
        contentPanel.add(Shields, gbc);

        this.cbShield = new JComboBox<String>();
        this.cbShield.setName("shield");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+9;
        gbc.gridwidth = 2;
        gbc.gridy = y;
        contentPanel.add(this.cbShield, gbc);

        listUpdater.addItemComboBox("Shield", this.cbShield);

        if (this.characterExistsFlag)
        {
            this.cbShield.setSelectedItem(String.valueOf(this.character.getShield()));
        }

        y += 1;
        // new row

        // Add helmet combo box
        JLabel Helmets = new JLabel("Helmet");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = x;
        gbc.gridwidth = 2;
        gbc.gridy = y;
        contentPanel.add(Helmets, gbc);

        this.cbHelmet = new JComboBox<String>();
        this.cbHelmet.setName("helmet");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+2;
        gbc.gridwidth = 2;
        gbc.gridy = y;
        contentPanel.add(this.cbHelmet, gbc);

        listUpdater.addItemComboBox("Helmet", this.cbHelmet);

        if (this.characterExistsFlag)
        {
            this.cbHelmet.setSelectedItem(String.valueOf(this.character.getHelmet()));
        }

        // Add Armor combo box
        JLabel Boots = new JLabel("Boots");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = x+4;
        gbc.gridwidth = 2;
        gbc.gridy = y;
        contentPanel.add(Boots, gbc);

        this.cbBoots = new JComboBox<String>();
        this.cbBoots.setName("boots");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+6;
        gbc.gridwidth = 2;
        gbc.gridy = y;
        contentPanel.add(this.cbBoots, gbc);

        listUpdater.addItemComboBox("Boots", this.cbBoots);

        if (this.characterExistsFlag)
        {
            this.cbBoots.setSelectedItem(String.valueOf(this.character.getBoots()));
        }

        // Add belts combo box
        JLabel Belts = new JLabel("Belt");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = x+8;
        gbc.gridwidth = 1;
        gbc.gridy = y;
        contentPanel.add(Belts, gbc);

        this.cbBelt = new JComboBox<String>();
        this.cbBelt.setName("belt");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+9;
        gbc.gridwidth = 2;
        gbc.gridy = y;
        contentPanel.add(this.cbBelt, gbc);

        listUpdater.addItemComboBox("Belt", this.cbBelt);

        if (this.characterExistsFlag)
        {
            this.cbBelt.setSelectedItem(String.valueOf(this.character.getBeltName()));
        }

        // new row

        // Add rings combo box

        y += 1;
        JLabel Rings = new JLabel("Ring");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = x;
        gbc.gridy = y;
        contentPanel.add(Rings, gbc);

        this.cbRing = new JComboBox<String>();
        this.cbRing.setName("ring");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x+2;
        gbc.gridy = y;
        contentPanel.add(this.cbRing, gbc);

        listUpdater.addItemComboBox("Ring", this.cbRing);

        if (this.characterExistsFlag)
        {
            this.cbRing.setSelectedItem(String.valueOf(this.character.getRingName()));
        }

        gbc.insets = new Insets(10, 5, 5, 5);

        y += 1;
        // don't show roll button if character is in edit mode
        if (!this.characterExistsFlag)
        {
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridwidth = 2;
            gbc.gridx = x+3;
            gbc.gridy = y;
            contentPanel.add(this.btnRoll, gbc);
        }

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 2;
        gbc.gridx = x+6;
        gbc.gridy = y;
        contentPanel.add(this.btnSave, gbc);

        getContentPane().add(contentPanel);
    }

    /**
     * This method initializes the action listeners and adds the abilities to 
     */
    private void initActionListeners()
    {

        // don't initialize roll action if the character is in edit mode
        if (!this.characterExistsFlag)
        {
            ArrayList<JTextField> txtFieldAbilities = new ArrayList<JTextField>();
            txtFieldAbilities.add(this.txtStr);
            txtFieldAbilities.add(this.txtDex);
            txtFieldAbilities.add(this.txtCons);
            this.btnRoll.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {

                	FighterDriverClass fighterDriver = new FighterDriverClass(cbType.getSelectedItem().toString()); 
                 
                    int str = fighterDriver.fighterDirector.getFighter().getStrength();
                    int dext = fighterDriver.fighterDirector.getFighter().getDexterity();
                    int con = fighterDriver.fighterDirector.getFighter().getConstitution();
                   
                    // Iterate through each text field component and calculate
                    // the roll score
                    int[] abilityData = { str, dext, con };
                    Iterator<JTextField> txtFieldIterator = txtFieldAbilities.iterator();
                    int index = 0;
                    while (txtFieldIterator.hasNext())
                    {                    
                        JTextField txtField = txtFieldIterator.next();
                        txtField.setText(Integer.toString(abilityData[index]));
                        index++;
                    }
                }
            });
        }

        ArrayList<Component> dialogComponents = new ArrayList<Component>();
        dialogComponents.add(this.txtStr);
        dialogComponents.add(this.txtDex);
        dialogComponents.add(this.txtCons);
        dialogComponents.add(this.txtName);
        dialogComponents.add(this.cbLvl);
        dialogComponents.add(this.cbClass);
        dialogComponents.add(this.cbType);
        dialogComponents.add(this.cbWeapon);
        dialogComponents.add(this.cbArmor);
        dialogComponents.add(this.cbShield);
        dialogComponents.add(this.cbHelmet);
        dialogComponents.add(this.cbRing);
        dialogComponents.add(this.cbBoots);
        dialogComponents.add(this.cbBelt);

        ActionListener btnSaveActionListener;
        if (this.characterExistsFlag)
            btnSaveActionListener = new BtnSaveActionListener(dialogComponents, this.character);
        else
            btnSaveActionListener = new BtnSaveActionListener(dialogComponents);

        this.btnSave.addActionListener(btnSaveActionListener);
    }

}