package game.views.jpanels;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import game.GameLauncher;
import game.components.ExtensionMethods;
import game.components.SharedVariables;
import game.model.Map;
import game.model.character.CharactersList;
import game.model.jaxb.MapJaxb;
import game.views.jdialogs.CreateStuffDialog;
import game.views.jdialogs.DialogHelper;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

/**
 * Map Designer helps user to create custom maps.
 * 
 * @author saiteja prasadam
 * @since 30/1/2017
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MapDesigner extends JPanel
{

    private JPanel mapJPanelArray[][]; // Map grid
    private String mapName;
    private int mapWidth, mapHeight;
    private Map mapObject;

    /**
     * Map Designer helps user to create custom maps.
     * 
     * @param mapName Name of the map.
     * @param width Width of the map.
     * @param height Height of the map.
     * @wbp.parser.constructor
     */
    public MapDesigner(String mapName, int mapWidth, int mapHeight)
    {
        this.mapName = mapName;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        initComponents();
    }

    /**
     * This constructor is called when a map is being loaded
     * 
     * @param mapName This will specify map name
     */
    public MapDesigner(String mapName)
    {
        this.mapName = mapName;
        mapObject = MapJaxb.getMapFromXml(mapName);

        if (mapObject == null)
        {
            DialogHelper.showBasicDialog("Problem loading map");
            GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());
        }

        else
        {
            mapWidth = mapObject.mapWidth;
            mapHeight = mapObject.mapHeight;
            initComponents();
            loadMap(mapObject.convertStringArrayToJPanel());
        }
    }

    /**
     * Load previously saved map data to display on current map
     * 
     * @param loadedMapData This is the previous map data
     */
    private void loadMap(JPanel[][] loadedMapData)
    {
        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++){
                mapJPanelArray[i][j].setBackground(loadedMapData[i][j].getBackground());
                mapJPanelArray[i][j].setToolTipText(loadedMapData[i][j].getToolTipText());
            }               
    }

    /**
     * Initializes all the UI components.
     * 
     * @param mapName Name of the map.
     * @param mapWidth Width of the map.
     * @param mapHeight Height of the map.
     */
    private void initComponents()
    {

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]
        { 0, 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[]
        { 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[]
        { 1.0, 1.0, 1.0, 1.0, 0.5 };
        gridBagLayout.rowWeights = new double[]
        { 1.0, 1.0, 1.0, 1.0, 1.0 };

        ButtonGroup RadioButtonGroup = new ButtonGroup();

        setLayout(gridBagLayout);

        { // Map panel
            JPanel mapPanel = new JPanel();
            mapPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.GRAY, null));
            mapPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc_mapPanel = new GridBagConstraints();
            gbc_mapPanel.gridwidth = 4;
            gbc_mapPanel.gridheight = 5;
            gbc_mapPanel.insets = new Insets(0, 0, 0, 5);
            gbc_mapPanel.fill = GridBagConstraints.BOTH;
            gbc_mapPanel.gridx = 0;
            gbc_mapPanel.gridy = 0;
            add(mapPanel, gbc_mapPanel);

            mapPanel.setLayout(new GridLayout(20, 20));
            mapPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            mapJPanelArray = new JPanel[mapWidth][mapHeight];
            for (int i = 0; i < mapWidth; i++)
            {
                for (int j = 0; j < mapHeight; j++)
                {
                    mapJPanelArray[i][j] = new JPanel();
                    mapJPanelArray[i][j].setBackground(SharedVariables.MAP_DEFAULT_CELL_COLOR);
                    mapJPanelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    mapJPanelArray[i][j].addMouseListener(new MouseListener()
                    {

                        @Override
                        public void mouseReleased(MouseEvent e)
                        {

                        }

                        @Override
                        public void mousePressed(MouseEvent e)
                        {
                            mapCellClicked(((JPanel) e.getSource()), RadioButtonGroup);
                        }

                        @Override
                        public void mouseExited(MouseEvent e)
                        {
                            if (((JPanel) e.getSource()).getBackground() == SharedVariables.MAP_MOUSE_HOVER_COLOR)
                                ((JPanel) e.getSource()).setBackground(SharedVariables.MAP_DEFAULT_CELL_COLOR);
                        }

                        @Override
                        public void mouseEntered(MouseEvent e)
                        {
                            if (((JPanel) e.getSource()).getBackground() == SharedVariables.MAP_DEFAULT_CELL_COLOR)
                                ((JPanel) e.getSource()).setBackground(SharedVariables.MAP_MOUSE_HOVER_COLOR);
                        }

                        @Override
                        public void mouseClicked(MouseEvent e)
                        {

                        }
                    });
                    mapPanel.add(mapJPanelArray[i][j]);
                }
            }
        }

        initDesignPanel(mapName, RadioButtonGroup);

    }

    /**
     * Initializes Design panel
     * 
     * @param mapName This contains map name.
     * @param RadioButtonGroup RadioButtonGroup reference to link radio buttons.
     */
    private void initDesignPanel(String mapName, ButtonGroup RadioButtonGroup)
    {

        JPanel designPanel = new JPanel();

        { // Initialize Design panel
            designPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.GRAY, null));
            designPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc_designPanel_1 = new GridBagConstraints();
            gbc_designPanel_1.gridheight = 5;
            gbc_designPanel_1.fill = GridBagConstraints.BOTH;
            gbc_designPanel_1.gridx = 4;
            gbc_designPanel_1.gridy = 0;
            add(designPanel, gbc_designPanel_1);
            GridBagLayout gbl_designPanel_1 = new GridBagLayout();
            gbl_designPanel_1.columnWidths = new int[]
            { 0 };
            gbl_designPanel_1.rowHeights = new int[]
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            gbl_designPanel_1.columnWeights = new double[]
            { 1.0 };
            gbl_designPanel_1.rowWeights = new double[]
            { 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0 };
            designPanel.setLayout(gbl_designPanel_1);
        }

        { // Initialize Map name panel
            JPanel mapNamePanel = new JPanel();
            mapNamePanel.setBackground(Color.WHITE);
            mapNamePanel
                    .setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
            GridBagConstraints gbc_mapNamePanel = new GridBagConstraints();
            gbc_mapNamePanel.fill = GridBagConstraints.HORIZONTAL;
            gbc_mapNamePanel.anchor = GridBagConstraints.NORTH;
            gbc_mapNamePanel.insets = new Insets(0, 0, 5, 0);
            gbc_mapNamePanel.gridx = 0;
            gbc_mapNamePanel.gridy = 0;
            designPanel.add(mapNamePanel, gbc_mapNamePanel);
            mapNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            JLabel lblMapName = new JLabel("Map Name :");
            lblMapName.setHorizontalAlignment(SwingConstants.CENTER);
            lblMapName.setFont(new Font("Tahoma", Font.BOLD, 14));
            mapNamePanel.add(lblMapName);

            JLabel lblMapNameValue = new JLabel(mapName);
            lblMapNameValue.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lblMapNameValue.setHorizontalAlignment(SwingConstants.CENTER);
            mapNamePanel.add(lblMapNameValue);
        }

        { // Initialize Components panel
            JPanel componentsPanel = new JPanel();
            componentsPanel.setBackground(Color.WHITE);
            componentsPanel
                    .setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
            GridBagConstraints gbc_componentsPanel = new GridBagConstraints();
            gbc_componentsPanel.gridheight = 2;
            gbc_componentsPanel.insets = new Insets(0, 0, 5, 0);
            gbc_componentsPanel.fill = GridBagConstraints.BOTH;
            gbc_componentsPanel.gridx = 0;
            gbc_componentsPanel.gridy = 1;
            designPanel.add(componentsPanel, gbc_componentsPanel);
            componentsPanel.setLayout(new GridLayout(0, 2));

            // Wall
            JRadioButton rdbtnWall = new JRadioButton("");
            rdbtnWall.setSelected(true);
            rdbtnWall.setHorizontalAlignment(SwingConstants.CENTER);
            rdbtnWall.setBackground(Color.BLACK);
            rdbtnWall.setActionCommand(SharedVariables.WALL_STRING);
            componentsPanel.add(rdbtnWall);
            JLabel wallLabel = new JLabel("Wall");
            wallLabel.setLabelFor(rdbtnWall);
            wallLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            wallLabel.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(wallLabel);

            /*
             * //Player JRadioButton rdbtnPlayer = new JRadioButton("");
             * rdbtnPlayer.setBackground(Color.BLUE);
             * rdbtnPlayer.setHorizontalAlignment(SwingConstants.CENTER);
             * rdbtnPlayer.setActionCommand(SharedVariables.PLAYER_STRING);
             * componentsPanel.add(rdbtnPlayer); JLabel playerLabel = new
             * JLabel("Player");
             * playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
             * playerLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
             * componentsPanel.add(playerLabel);
             */

            // Monster
            JRadioButton rdbtnMonster = new JRadioButton("");
            rdbtnMonster.setBackground(Color.RED);
            rdbtnMonster.setHorizontalAlignment(SwingConstants.CENTER);
            rdbtnMonster.setActionCommand(SharedVariables.MONSTER_STRING);
            componentsPanel.add(rdbtnMonster);
            JLabel monsterLabel = new JLabel("Monster");
            monsterLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            monsterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(monsterLabel);

            // Entry door
            JRadioButton rdbtnEntryDoor = new JRadioButton("");
            rdbtnEntryDoor.setBackground(Color.MAGENTA);
            rdbtnEntryDoor.setHorizontalAlignment(SwingConstants.CENTER);
            rdbtnEntryDoor.setActionCommand(SharedVariables.ENTRY_DOOR_STRING);
            componentsPanel.add(rdbtnEntryDoor);
            JLabel entryDoorLabel = new JLabel("Entry door");
            entryDoorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            entryDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(entryDoorLabel);

            // Exit door
            JRadioButton rdbtnExitDoor = new JRadioButton("");
            rdbtnExitDoor.setBackground(Color.PINK);
            rdbtnExitDoor.setHorizontalAlignment(SwingConstants.CENTER);
            rdbtnExitDoor.setActionCommand(SharedVariables.EXIT_DOOR_STRING);
            componentsPanel.add(rdbtnExitDoor);
            JLabel exitDoorLabel = new JLabel("Exit door");
            exitDoorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            exitDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(exitDoorLabel);

            // Chest
            JRadioButton rdbtnChest = new JRadioButton("");
            rdbtnChest.setBackground(Color.YELLOW);
            rdbtnChest.setHorizontalAlignment(SwingConstants.CENTER);
            rdbtnChest.setActionCommand(SharedVariables.CHEST_STRING);
            componentsPanel.add(rdbtnChest);
            JLabel chestLabel = new JLabel("Chest");
            chestLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            chestLabel.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(chestLabel);

            // Key
            JRadioButton rdbtnKey = new JRadioButton("");
            rdbtnKey.setBackground(Color.CYAN);
            rdbtnKey.setHorizontalAlignment(SwingConstants.CENTER);
            rdbtnKey.setActionCommand(SharedVariables.KEY_STRING);
            componentsPanel.add(rdbtnKey);
            JLabel keyLabel = new JLabel("Key");
            keyLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(keyLabel);

            // Adding radio buttons to radio group
            RadioButtonGroup.add(rdbtnWall);
            // RadioButtonGroup.add(rdbtnPlayer);
            RadioButtonGroup.add(rdbtnMonster);
            RadioButtonGroup.add(rdbtnKey);
            RadioButtonGroup.add(rdbtnEntryDoor);
            RadioButtonGroup.add(rdbtnExitDoor);
            RadioButtonGroup.add(rdbtnChest);
        }

        initSaveAndCancelButtons(designPanel);

    }

    /**
     * Initializes clear map, save and cancel buttons
     * 
     * @param designPanel Reference to design panel
     */
    private void initSaveAndCancelButtons(JPanel designPanel)
    {

        JButton btnNewButton = new JButton("Clear map");
        btnNewButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                for (int i = 0; i < mapWidth; i++)
                    for (int j = 0; j < mapHeight; j++)
                        mapJPanelArray[i][j].setBackground(SharedVariables.MAP_DEFAULT_CELL_COLOR);
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton.gridx = 0;
        gbc_btnNewButton.gridy = 10;
        designPanel.add(btnNewButton, gbc_btnNewButton);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 11;
        designPanel.add(panel, gbc_panel);
        panel.setLayout(new GridLayout(1, 0, 0, 0));
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());
            }
        });
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(btnCancel);

        JButton btnSaveButton = new JButton("Save");
        btnSaveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                if (validateMap())
                {
                    MapJaxb.convertMapObjectToXml(new Map(mapName, mapWidth, mapHeight, mapJPanelArray));
                    GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());
                    new CreateStuffDialog(2, mapName);
                }
            }

            private boolean validateMap()
            {

                // boolean isPlayerExists =
                // isObjectPlaced(SharedVariables.MAP_PLAYER_CELL_COLOR);
                boolean isExitDoorExists = isObjectPlaced(
                        SharedVariables.mapCellHashMap.get(SharedVariables.EXIT_DOOR_STRING));
                boolean isEntryDoorExists = isObjectPlaced(
                        SharedVariables.mapCellHashMap.get(SharedVariables.ENTRY_DOOR_STRING));
                boolean isObjectiveExists = isObjectPlaced(
                        SharedVariables.mapCellHashMap.get(SharedVariables.MONSTER_STRING))
                                            || isObjectPlaced(
                                                    SharedVariables.mapCellHashMap.get(SharedVariables.KEY_STRING));

                /*
                 * if(!isPlayerExists){
                 * DialogHelper.showBasicDialog("Need a player to continue");
                 * return false; }
                 * 
                 * else
                 */if (!isExitDoorExists)
                {
                    DialogHelper.showBasicDialog("Need a exit door to continue");
                    return false;
                }

                else if (!isEntryDoorExists)
                {
                    DialogHelper.showBasicDialog("Need a entry door to continue");
                    return false;
                }

                else if (!isObjectiveExists)
                {
                    DialogHelper.showBasicDialog("Need a object to continue, place either a monster or a key");
                    return false;
                }

                return true;
            }
        });
        btnSaveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSaveButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(btnSaveButton);
    }

    /**
     * This method is called on map edit click event, which places the
     * components (player, wall etc).
     * 
     * @param jPanel This contains reference to the clicked JPanel
     * @param radioButtonGroup This contains reference to RadioButtonGroup,
     *        which states which component should be placed.
     */
    private void mapCellClicked(JPanel jPanel, final ButtonGroup radioButtonGroup)
    {

        if (getColorFromSelectedRadioButton(radioButtonGroup) == jPanel.getBackground())
            jPanel.setBackground(SharedVariables.MAP_DEFAULT_CELL_COLOR);

        else
        {
            if(getColorFromSelectedRadioButton(radioButtonGroup) == SharedVariables.mapCellHashMap.get(SharedVariables.MONSTER_STRING) && ExtensionMethods.getCampaignsList().length != 0){
                DialogHelper.showBasicDialog("You don't have any monsters to place");
                return;
            }
                          
            else if(getColorFromSelectedRadioButton(radioButtonGroup) == SharedVariables.mapCellHashMap.get(SharedVariables.CHEST_STRING) && ExtensionMethods.getItemsList().length == 0){               
                DialogHelper.showBasicDialog("You don't have any items to place in the chest");
                return;
            }
                       
            else if (getColorFromSelectedRadioButton(radioButtonGroup) == SharedVariables.mapCellHashMap.get(SharedVariables.ENTRY_DOOR_STRING) && isObjectPlaced(SharedVariables.mapCellHashMap.get(SharedVariables.ENTRY_DOOR_STRING)))
                DialogHelper.showBasicDialog("You have already placed a entry door");

            else if (getColorFromSelectedRadioButton(radioButtonGroup) == SharedVariables.mapCellHashMap.get(SharedVariables.EXIT_DOOR_STRING) && isObjectPlaced(SharedVariables.mapCellHashMap.get(SharedVariables.EXIT_DOOR_STRING)))
                DialogHelper.showBasicDialog("You have already placed a exit door");

            else{
                jPanel.setBackground(getColorFromSelectedRadioButton(radioButtonGroup));
                if(getColorFromSelectedRadioButton(radioButtonGroup) == SharedVariables.mapCellHashMap.get(SharedVariables.CHEST_STRING) && ExtensionMethods.getItemsList().length > 0){         
                    
                    JComboBox<String> itemsList = new JComboBox<String>(ExtensionMethods.getItemsList());
                    JOptionPane.showMessageDialog(null, itemsList, "Select a item to place in the chest", JOptionPane.QUESTION_MESSAGE);
                    jPanel.setToolTipText(itemsList.getSelectedItem().toString());
                }
                
                else if(getColorFromSelectedRadioButton(radioButtonGroup) == SharedVariables.mapCellHashMap.get(SharedVariables.MONSTER_STRING) && ExtensionMethods.getItemsList().length > 0){         
                                       
                    ArrayList<String> chars = CharactersList.getNames();
                    JComboBox<String> charcterList = new JComboBox<String>(chars.toArray(new String[chars.size()]));
                    ButtonGroup buttonGroup = new ButtonGroup();
                    
                    JRadioButton friendlyRadioButton = new JRadioButton("Friendly");
                    JRadioButton hostileRadioButton = new JRadioButton("Hostile");
                    
                    friendlyRadioButton.setSelected(true);
                    friendlyRadioButton.setActionCommand("Friendly");
                    hostileRadioButton.setActionCommand("Hostile");
                    
                    buttonGroup.add(friendlyRadioButton);
                    buttonGroup.add(hostileRadioButton);
                    
                    Object[] things = {
                        charcterList, friendlyRadioButton, hostileRadioButton
                    };
                    
                    JOptionPane.showMessageDialog(null, things, "Select a character as a monster", JOptionPane.QUESTION_MESSAGE);
                    jPanel.setToolTipText(charcterList.getSelectedItem().toString() + " - " + buttonGroup.getSelection().getActionCommand());
                }
                
                else
                  jPanel.setToolTipText("");
            }
                
        }

    }

    /**
     * This method returns selected radio button color.
     * 
     * @param radioButtonGroup This contains reference to Radio button group.
     * @return Returns selected radio button color.
     */
    private Color getColorFromSelectedRadioButton(final ButtonGroup radioButtonGroup)
    {
        return SharedVariables.mapCellHashMap.get(radioButtonGroup.getSelection().getActionCommand());
    }

    /**
     * This method check whether user played player character or not
     * 
     * @return Boolean value of the search
     */
    private Boolean isObjectPlaced(Color objectColor)
    {

        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
                if (mapJPanelArray[i][j].getBackground() == objectColor)
                    return true;

        return false;
    }

}
