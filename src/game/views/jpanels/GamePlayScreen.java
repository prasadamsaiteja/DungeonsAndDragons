package game.views.jpanels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import game.GameLauncher;
import game.components.Console;
import game.components.Dice;
import game.components.ExtensionMethods;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.Campaign;
import game.model.Item;
import game.model.Map;
import game.model.character.Backpack;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.jaxb.CampaignJaxb;
import game.model.jaxb.GamePlayJaxb;
import game.model.onclickListeners.MapClickListener;
import game.model.strategyPattern.AggresiveNPC;
import game.model.strategyPattern.FriendlyNPC;
import game.views.jdialogs.DialogHelper;
import game.views.jdialogs.InventoryViewDialog;

/**
 * This JPanel is the game play screen 
 * 
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 3/8/2017
 */
@SuppressWarnings("serial")
@XmlRootElement(name = "GamePlayScreen")
public class GamePlayScreen extends JPanel implements Observer{
  
    @XmlElement(name = "character")
    public Character character;
    @XmlElement(name = "campaign")
    public Campaign campaign;
    @XmlElement(name = "currentMap")
    public Map currentMap;
    public PlayerMomentMechanics playerMomentMechanics;
    
    private JPanel mapJPanelArray[][];       
    private int currentMapNumber = 0;
    public Object previousMapCellObject = SharedVariables.DEFAULT_CELL_STRING;
    private JPanel characterDetailsPanel;
    private InventoryViewDialog inventoryViewDialog;
    private ArrayList<Character> charactersTurnBasedMechnaism;
    public volatile boolean isTurnFinished = false;
    
    /**
     * This is constructor method for this class
     * 
     * @param camapaignName This is the campaign to be loaded
     * @param characterName This is the character to be loaded
     */
    public GamePlayScreen(String camapaignName, String characterName){
      
         this.campaign = CampaignJaxb.getCampaignFromXml(camapaignName);
         this.character = CharactersList.getByName(characterName).clone();
         this.character.setPlayerFlag(true);
         this.playerMomentMechanics = new PlayerMomentMechanics();
         
         if(campaign == null || character == null)
             DialogHelper.showBasicDialog("Error reading saved files");
         
         else{
             this.playerMomentMechanics.setKeyListeners(this);
             this.campaign.fetchMaps();
             this.character.backpack = new Backpack();
             this.currentMap = campaign.getMapList().get(currentMapNumber);             
             this.currentMap.initalizeMapData(this.character.getName());              
             this.setMapLevel();
             this.initComponents();
             this.initalizeTurnBasedMechanism();
         }
           
    }
    
    /**
     * This method set the level of the enemy and item using by players and enemies, matching to the player
     */
    private void setMapLevel(){
        
        Console.printInConsole("Player(" + character.getName() + ")  entered map : " + currentMap.getMapName() + "\n");
        for (int i = 0; i < currentMap.mapWidth; i++)      
            for (int j = 0; j < currentMap.mapHeight; j++)
                if(currentMap.mapData[i][j] instanceof Character && (!((Character) currentMap.mapData[i][j]).isPlayer())){
                    Character tempCharacter = ((Character) currentMap.mapData[i][j]);
                    
                    tempCharacter.setLevel(character.getLevel());        
                    
                    if(tempCharacter.getIsFriendlyMonster())
                        tempCharacter.setMomentStrategy(new FriendlyNPC(GamePlayScreen.this, tempCharacter));
                    else
                        tempCharacter.setMomentStrategy(new AggresiveNPC(GamePlayScreen.this, tempCharacter));
                }                               
    }
    
    /**
     * This method initializes turn based mechanism
     */
    private void initalizeTurnBasedMechanism(){
        
        Console.printInConsole("Calculating turn based mechanism");
        charactersTurnBasedMechnaism = new ArrayList<>();          
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
            for (int j = 0; j < currentMap.mapHeight; j++)
                if(currentMap.mapData[i][j] instanceof Character)
                    charactersTurnBasedMechnaism.add((Character) currentMap.mapData[i][j]);
                        
        for (Character character : charactersTurnBasedMechnaism) 
            character.turnPoints = new Dice(1, 20, 1).getRollSum() + character.getDexterityModifier();
        
        Collections.sort(charactersTurnBasedMechnaism, new Comparator<Character>(){
            public int compare(Character s1, Character s2) {
                return s2.turnPoints - s1.turnPoints;
            }
        });
        
        for (Character character : charactersTurnBasedMechnaism) 
            Console.printInConsole("  *" + character.getName() + " -> " + character.turnPoints);
        
        Console.printInConsole("");  
        Console.printInConsole("Starting gameplay");
        startGamePlay();        
    }
    
    /**
     * This method starts the game play 
     */
    private void startGamePlay() {
        
        Thread gameplayThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                
                while(character.getHitScore() >= 1)
                for (Character character : charactersTurnBasedMechnaism) {
                    
                    if(character.getHitScore() < 1)
                        continue;
                    
                    Console.printInConsole("");
                    Console.printInConsole("  *" + character.getName() + "'s turn");                   
                    
                    if(character.isPlayer())
                        GamePlayScreen.this.playerMomentMechanics.setKeyListeners(GamePlayScreen.this);
                    else{
                        GamePlayScreen.this.playerMomentMechanics.removeKeyListeners(GamePlayScreen.this);                              
                        character.getMomentStrategy().playTurn();
                    }
                         
                    
                    while (true) {
                        if (isTurnFinished){
                            isTurnFinished = false;
                            break;                 
                        }
                   }
                }
                
                if(character.getHitScore() < 1){
                    Console.printInConsole("You are dead, you will be now redirected to launch screen");
                    DialogHelper.showBasicDialog("You are dead, you will be now redirected to launch screen");
                    GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());
                }
            }     
            
        });
        
        gameplayThread.setDaemon(true);
        gameplayThread.start();
        
    }
    
    /**
     * This method initializes UI components
     */
    private void initComponents(){
      
        this.removeAll();
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 0.5 };
        gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0 };
        setLayout(gridBagLayout);       
        
        initMapPanel();
        initControlPanel();
    }    

    /**
     * This method initializes map panel
     */
    private void initMapPanel() {
      
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

      mapPanel.setLayout(new GridLayout(currentMap.mapWidth, currentMap.mapHeight));
      mapJPanelArray = new JPanel[currentMap.mapWidth][currentMap.mapHeight];
      
      for (int i = 0; i < currentMap.mapWidth; i++)      
        for (int j = 0; j < currentMap.mapHeight; j++)
        {
            mapJPanelArray[i][j] = new JPanel();
            mapJPanelArray[i][j] = GameMechanics.setMapCellDetailsFromMapObjectData(mapJPanelArray[i][j], currentMap.mapData[i][j]);
            mapJPanelArray[i][j].addMouseListener(new MapClickListener(this, currentMap.mapData[i][j]));
            mapJPanelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));              
            mapPanel.add(mapJPanelArray[i][j]);
        }
    }
          
  /** 
     * Initializes Control panel
     */
    private void initControlPanel()
    {

       JPanel designPanel = new JPanel();

       {    //Initialize Design panel
           designPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.GRAY, null));
           designPanel.setBackground(Color.WHITE);
           GridBagConstraints gbc_designPanel_1 = new GridBagConstraints();
           gbc_designPanel_1.gridheight = 5;
           gbc_designPanel_1.fill = GridBagConstraints.BOTH;
           gbc_designPanel_1.gridx = 4;
           gbc_designPanel_1.gridy = 0;
           add(designPanel, gbc_designPanel_1);
           GridBagLayout gbl_designPanel_1 = new GridBagLayout();
           gbl_designPanel_1.columnWidths = new int[] { 0 };
           gbl_designPanel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
           gbl_designPanel_1.columnWeights = new double[] { 1.0 };
           gbl_designPanel_1.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0 };
           designPanel.setLayout(gbl_designPanel_1);
       }

       {   //Initialize Game Details panel
           JPanel campaignDetailsPanel = new JPanel();
           campaignDetailsPanel.setBackground(Color.WHITE);
           campaignDetailsPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
           GridBagConstraints gbc_mapNamePanel = new GridBagConstraints();
           gbc_mapNamePanel.fill = GridBagConstraints.HORIZONTAL;
           gbc_mapNamePanel.anchor = GridBagConstraints.NORTH;
           gbc_mapNamePanel.insets = new Insets(0, 0, 5, 0);
           gbc_mapNamePanel.gridx = 0;
           gbc_mapNamePanel.gridy = 0;
           designPanel.add(campaignDetailsPanel, gbc_mapNamePanel);
           campaignDetailsPanel.setLayout(new GridLayout(0, 1, 0, 0));
           
           for(int index = 0; index < 4; index++){
             
               String key = "", value = "";
               if(index == 0){
                   key = "Campaign Name : ";
                   value = campaign.getCampaignName() + " (" + campaign.getMapNames().size() + " maps)";
               }
               
               else if(index == 1){
                   key = "Character Name : ";
                   value = character.getName();                   
               }
               
               else if(index == 2){
                   key = "Current Map Name : ";
                   value = currentMap.getMapName() + " (" + (currentMapNumber + 1) + " map out of " + campaign.getMapNames().size() + " maps)";
               }
               
               else{
                 key = "Level : ";
                 value = String.valueOf(character.getLevel());
               }
             
               JPanel campaignNameJpanel = new JPanel();
               campaignNameJpanel.setBackground(Color.WHITE);               
               campaignNameJpanel.setBackground(Color.WHITE);
               campaignNameJpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
               
               JLabel campaginLabel = new JLabel(key);
               campaginLabel.setHorizontalAlignment(SwingConstants.CENTER);
               campaginLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
               campaignNameJpanel.add(campaginLabel);
               
               JLabel campaignNameValueLabel = new JLabel(value);
               campaignNameValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
               campaignNameValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
               campaignNameJpanel.add(campaignNameValueLabel);
               campaignDetailsPanel.add(campaignNameJpanel);
           }  
                     
       }

       {    //Initialize Components panel
           JPanel componentsPanel = new JPanel();
           componentsPanel.setBackground(Color.WHITE);
           componentsPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
           GridBagConstraints gbc_componentsPanel = new GridBagConstraints();
           gbc_componentsPanel.gridheight = 2;
           gbc_componentsPanel.insets = new Insets(0, 0, 5, 0);
           gbc_componentsPanel.fill = GridBagConstraints.BOTH;
           gbc_componentsPanel.gridx = 0;
           gbc_componentsPanel.gridy = 1;
           designPanel.add(componentsPanel, gbc_componentsPanel);
           componentsPanel.setLayout(new GridLayout(0, 2));           

           // Wall
           JPanel wallJpanel = new JPanel();
           wallJpanel.setBackground(SharedVariables.mapCellHashMap.get(SharedVariables.WALL_STRING));
           JLabel wallLabel = new JLabel("Wall");
           wallLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
           wallLabel.setHorizontalAlignment(SwingConstants.CENTER);
           componentsPanel.add(wallJpanel);
           componentsPanel.add(wallLabel);
           
           //Player
           JPanel playerJpanel = new JPanel();
           playerJpanel.setBackground(Color.BLUE);
           JLabel playerLabel = new JLabel("Player");
           playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
           playerLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
           componentsPanel.add(playerJpanel);
           componentsPanel.add(playerLabel);           

           // Monster           
           JPanel monsterJpanel = new JPanel();
           monsterJpanel.setBackground(SharedVariables.mapCellHashMap.get(SharedVariables.MONSTER_STRING));
           JLabel monsterLabel = new JLabel("Monster");
           monsterLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
           monsterLabel.setHorizontalAlignment(SwingConstants.CENTER);
           componentsPanel.add(monsterJpanel);
           componentsPanel.add(monsterLabel);

           // Entry door       
           JPanel entryDoorJpanel = new JPanel();
           entryDoorJpanel.setBackground(SharedVariables.mapCellHashMap.get(SharedVariables.ENTRY_DOOR_STRING));
           JLabel entryDoorLabel = new JLabel("Entry door");
           entryDoorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
           entryDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
           componentsPanel.add(entryDoorJpanel);
           componentsPanel.add(entryDoorLabel);

           // Exit door           
           JPanel exitDoorJpanel = new JPanel();
           exitDoorJpanel.setBackground(SharedVariables.mapCellHashMap.get(SharedVariables.EXIT_DOOR_STRING));
           JLabel exitDoorLabel = new JLabel("Exit door");
           exitDoorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));    
           exitDoorLabel.setHorizontalAlignment(SwingConstants.CENTER);
           componentsPanel.add(exitDoorJpanel);
           componentsPanel.add(exitDoorLabel);

           // Chest      
           JPanel chestJpanel = new JPanel();
           chestJpanel.setBackground(SharedVariables.mapCellHashMap.get(SharedVariables.CHEST_STRING));
           JLabel chestLabel = new JLabel("Chest");
           chestLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
           chestLabel.setHorizontalAlignment(SwingConstants.CENTER);
           componentsPanel.add(chestJpanel);
           componentsPanel.add(chestLabel);

           // Key     
           JPanel keyJpanel = new JPanel();
           keyJpanel.setBackground(SharedVariables.mapCellHashMap.get(SharedVariables.KEY_STRING));
           JLabel keyLabel = new JLabel("Key");
           keyLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
           keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
           componentsPanel.add(keyJpanel);
           componentsPanel.add(keyLabel);          
       }
       
       characterDetailsPanel = new JPanel();
       characterDetailsPanel.setBackground(Color.WHITE);
       characterDetailsPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
       GridBagConstraints gbc_characterDetailsPanel = new GridBagConstraints();
       gbc_characterDetailsPanel.insets = new Insets(0, 0, 5, 0);
       gbc_characterDetailsPanel.fill = GridBagConstraints.HORIZONTAL;
       gbc_characterDetailsPanel.anchor = GridBagConstraints.NORTH;
       gbc_characterDetailsPanel.gridx = 0;
       gbc_characterDetailsPanel.gridy = 3;
       designPanel.add(characterDetailsPanel, gbc_characterDetailsPanel);
       characterDetailsPanel.setLayout(new GridLayout(0, 1, 0, 0));    
       
       JPanel panel = new JPanel();
       GridBagConstraints gbc_panel = new GridBagConstraints();
       gbc_panel.fill = GridBagConstraints.BOTH;
       gbc_panel.gridx = 0;
       gbc_panel.gridy = 11;
       designPanel.add(panel, gbc_panel);
       panel.setLayout(new GridLayout(1, 0, 0, 0));
       
       JButton btnAbortButton = new JButton("Abort game");
       btnAbortButton.setFont(new Font("Tahoma", Font.BOLD, 14));
       btnAbortButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
       btnAbortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());                
            }
        });
       panel.add(btnAbortButton);
       
       JButton btnSaveButton = new JButton("Save");
       btnSaveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
       btnSaveButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
       btnSaveButton.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileNmae = JOptionPane.showInputDialog("Enter name of the file to be saved");
                if(GamePlayJaxb.convertGameObjectToXml(fileNmae, GamePlayScreen.this))
                    DialogHelper.showBasicDialog("Game saved successfully");
                                
                else
                    DialogHelper.showBasicDialog("There was a issue saving the game");
                    
            }
        });
       panel.add(btnSaveButton);
       
      
       showPlayerDetails(character);
   }
    
    /**
     * This method displays the character details on the screen
     * 
     * @param character Character object that needs to be displayed on the screen
     */
    public void showPlayerDetails(Character character) {
              
          this.removePreviousObservables();
          character.addObserver(this);
          characterDetailsPanel.removeAll();
          
          for(int index = 0; index < 12; index++){
              
              String key = "", value = "";
              if(index == 0){
                  key = "Character Name : ";
                  value = character.getName();
              }
              
              else if(index == 1){
                
                  key = "Type of character : ";
                  
                  if(character.isPlayer())
                    value = "Player";
                  
                  else if(character.getIsFriendlyMonster() == true)
                    value = "Monster (Friendly)";
                  else
                    value = "Monster (Hostile)";
              }
              
              else if(index == 2){
                  key = "Level : ";
                  value = String.valueOf(character.getLevel());
              }
              
              else if(index == 3){
                  key = "Hit points : ";
                  value = String.valueOf(character.getHitScore() + "HP");
              }
              
              else if(index == 4){
                  key = "Strength : ";
                  value = String.valueOf(character.getStrength());
              }
              
              else if(index == 5){
                  key = "Strength modifier : ";
                  value = String.valueOf(character.getStrengthModifier());
              }
              
              else if(index == 6){
                  key = "Dexterity : ";
                  value = String.valueOf(character.getDexterity());
              }
              
              else if(index == 7){
                  key = "Dexterity modifier : ";
                  value = String.valueOf(character.getDexterityModifier());
              }
              
              else if(index == 8){
                  key = "Constitution : ";
                  value = String.valueOf(character.getConstitution());
              }
              
              else if(index == 9){
                  key = "Constitution modifier : ";
                  value = String.valueOf(character.getConstitutionModifier());
              }
              
              else if(index == 10){
                  key = "Armor Class : ";
                  value = String.valueOf(character.getArmorClass());
              }                            
                                         
              else if(character.isPlayer()){                
                  key = "Key collected : ";
                  if(character.isKeyCollected())
                      value = "Yes";
                  else
                      value = "No";
              }
            
              JPanel campaignNameJpanel = new JPanel();            
              campaignNameJpanel.setBackground(Color.WHITE);
              campaignNameJpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
              
              JLabel campaginLabel = new JLabel(key);
              campaginLabel.setHorizontalAlignment(SwingConstants.CENTER);
              campaginLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
              campaignNameJpanel.add(campaginLabel);
              
              JLabel campaignNameValueLabel = new JLabel(value);
              campaignNameValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
              campaignNameValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
              campaignNameJpanel.add(campaignNameValueLabel);
              characterDetailsPanel.add(campaignNameJpanel);              
          }          
          
          JButton openInventory = new JButton("Open Inventory");          
          openInventory.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryViewDialog = new InventoryViewDialog(character);
            }
        });
          
          characterDetailsPanel.add(openInventory);  
          characterDetailsPanel.revalidate();
          
          if(inventoryViewDialog != null)
              inventoryViewDialog.update(character, null);
    }                  
       
    /**
     * This method repaints the map after the action is completed
     */
    public void repaintMap() {
     
     for (int i = 0; i < currentMap.mapWidth; i++)      
       for (int j = 0; j < currentMap.mapHeight; j++) {
         
         for(MouseListener listener : mapJPanelArray[i][j].getMouseListeners())  
             mapJPanelArray[i][j].removeMouseListener(listener);
           
           mapJPanelArray[i][j].addMouseListener(new MapClickListener(this, currentMap.mapData[i][j]));
           mapJPanelArray[i][j] = GameMechanics.setMapCellDetailsFromMapObjectData(mapJPanelArray[i][j], currentMap.mapData[i][j]);
       }                  
    }            
              
    /**
     * This class contains all the player moment mechanics classes and methods 
     * 
     * @author saiteja prasadm
     * @since 3/11/2017
     * @version 1.0.0
     */
    public class PlayerMomentMechanics{
        
        int playerMomentCount = 0;
        
        /**
         * This class actionPerformed is triggered when up or w is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        public class UP_PRESSED extends AbstractAction {

          /**
          * This method performs the action when key is pressed
          * @param e ActionEvent
          */	 
          @Override
          public void actionPerformed(ActionEvent e) {
                          
               int[] position = GameMechanics.getPlayerPosition(currentMap);
               int rowNumber = position[0];
               int colNumber = position[1];
               if(rowNumber != 0){
                   String message = "   => " + character.getName() + " moving up";
                   movePlayer(message, rowNumber, colNumber, rowNumber - 1, colNumber);  
               }
                                       
          }
        }
    
        /**
         * This class actionPerformed is triggered when down or s is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        public class DOWN_PRESSED extends AbstractAction {

        	/**
        	 * This method performs the action when key is pressed
        	 * @param e ActionEvent
        	 */
          @Override
          public void actionPerformed(ActionEvent e) {
              
              int[] position = GameMechanics.getPlayerPosition(currentMap);
              int rowNumber = position[0];
              int colNumber = position[1];
              
              if(rowNumber < currentMap.mapHeight - 1){
                  String message = "   => " + character.getName() + " moving down";
                  movePlayer(message, rowNumber, colNumber, rowNumber + 1, colNumber);                  
              }                         
          }
          
        }
        
        /**
         * This class actionPerformed is triggered when left or a is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        public class LEFT_PRESSED extends AbstractAction {

        	/**
        	 * This method performs the action when key is pressed
        	 * @param e ActionEvent
        	 */
          @Override
          public void actionPerformed(ActionEvent e) {
            
              int[] position = GameMechanics.getPlayerPosition(currentMap);
              int rowNumber = position[0];
              int colNumber = position[1];
              
              if(colNumber != 0){
                  String message = "   => " + character.getName() + " moving left";
                  movePlayer(message, rowNumber, colNumber, rowNumber, colNumber - 1);
              }
                    
          }
          
        }
        
        /**
         * This class actionPerformed is triggered when right or d is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        public class RIGHT_PRESSED extends AbstractAction {

        	/**
        	 * This method performs the action when key is pressed
        	 * @param e ActionEvent
        	 */
          @Override
          public void actionPerformed(ActionEvent e) {
            
              int[] position = GameMechanics.getPlayerPosition(currentMap);
              int rowNumber = position[0];
              int colNumber = position[1];
              
              if(colNumber < currentMap.mapWidth - 1){
                  String message = "   => " + character.getName() + " moving right";
                  movePlayer(message, rowNumber, colNumber, rowNumber, colNumber + 1);       
              }
                
          }
        
        }
        
        
        /**
         * This method set's up key binding for player moments
         * @param jpanel parent jpanel 
         */
        public void setKeyListeners(JPanel jpanel){
          
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UP_PRESSED");
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "UP_PRESSED");
            
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DOWN_PRESSED");
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "DOWN_PRESSED");
            
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "LEFT_PRESSED");
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "LEFT_PRESSED");
            
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT_PRESSED");
            jpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "RIGHT_PRESSED");                       
            
            jpanel.getActionMap().put("UP_PRESSED", playerMomentMechanics.new UP_PRESSED());
            jpanel.getActionMap().put("DOWN_PRESSED", playerMomentMechanics.new DOWN_PRESSED());
            jpanel.getActionMap().put("LEFT_PRESSED", playerMomentMechanics.new LEFT_PRESSED());
            jpanel.getActionMap().put("RIGHT_PRESSED", playerMomentMechanics.new RIGHT_PRESSED());        
        }
        
        /**
         * This method removes key binding to the parent jpanel
         * @param jpanel parent jpanel
         */
        public void removeKeyListeners(JPanel jpanel){
            
            jpanel.getActionMap().put("UP_PRESSED", null);
            jpanel.getActionMap().put("DOWN_PRESSED", null);
            jpanel.getActionMap().put("LEFT_PRESSED", null);
            jpanel.getActionMap().put("RIGHT_PRESSED", null);   
            
        }
        
        /**
         * This method moves the player position
         * @param message This contains message to display on console what moment the player is taking
         * 
         * @param fromRowNumber from row position of player
         * @param fromColNumber from col position of player
         * @param toRowNumber to row position of player
         * @param toColNumber to col position of player
         */
        public void movePlayer(String message, int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber){
                      
            if(currentMap.mapData[toRowNumber][toColNumber] instanceof Character && ((Character) currentMap.mapData[toRowNumber][toColNumber]).getHitScore() < 1){
                                               
                Object tempPreviousMapCellObject = previousMapCellObject;
                previousMapCellObject = currentMap.mapData[toRowNumber][toColNumber];             
                currentMap.mapData[toRowNumber][toColNumber] = character;
                currentMap.mapData[fromRowNumber][fromColNumber] = tempPreviousMapCellObject;
                Console.printInConsole(message);
                repaintMap(); 
                
                if(previousMapCellObject instanceof Character){
                                        
                    if(ExtensionMethods.fetchAllItemNames(((Character) previousMapCellObject)).size() < 1)
                        DialogHelper.showBasicDialog("No items found");
                                        
                    else if(JOptionPane.showConfirmDialog(null, "Would you like to pick items from this dead monster", "You approched a dead monster", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        
                        if(character.backpack.backpackItems.size() >= 10)
                            DialogHelper.showBasicDialog("Your backpack is full");
                        
                        else{
                            Set<String> characterItems = new HashSet<>();
                            
                            characterItems.addAll(ExtensionMethods.fetchAllItemNames(((Character) previousMapCellObject)));
                            
                            JComboBox<String> itemsList = new JComboBox<String>();
                            for (String string : characterItems)
                                itemsList.addItem(string);
                            
                            JOptionPane.showMessageDialog(null, itemsList, "Select a item to pick from dead monster", JOptionPane.QUESTION_MESSAGE);
                            Entry<String, String> entry = ExtensionMethods.getByValue(((Character) previousMapCellObject).backpack.backpackItems, itemsList.getSelectedItem().toString());                        
                            
                            if(entry == null)
                                entry = ExtensionMethods.getByValue(((Character) previousMapCellObject).items, itemsList.getSelectedItem().toString());
                            
                            if(entry != null){
                                
                                if(((Character) previousMapCellObject).backpack.backpackItems.remove(entry.getKey(), entry.getValue()) == false)
                                    ((Character) previousMapCellObject).items.remove(entry.getKey(), entry.getValue());
                                  
                                if(character.items.containsKey(entry.getKey()))                                
                                    character.backpack.backpackItems.put(entry.getKey(), entry.getValue());
                                else
                                    character.items.put(entry.getKey(), entry.getValue());
                                
                                character.draw();
                                ((Character) previousMapCellObject).draw();
                                DialogHelper.showBasicDialog("You have picked up a " + entry.getKey() + " (" + entry.getValue() + ") from a dead monster"); 
                            }
                                                                           
                        }
                    }
                        
                }                               
            }
            
            else if(!currentMap.mapData[toRowNumber][toColNumber].equals(SharedVariables.WALL_STRING) && !(currentMap.mapData[toRowNumber][toColNumber] instanceof Character)){
              
                Object tempPreviousMapCellObject = previousMapCellObject;
                previousMapCellObject = currentMap.mapData[toRowNumber][toColNumber];             
                currentMap.mapData[toRowNumber][toColNumber] = character;
                currentMap.mapData[fromRowNumber][fromColNumber] = tempPreviousMapCellObject;              
                repaintMap(); 
                
                if(previousMapCellObject instanceof Item){
                    if(JOptionPane.showConfirmDialog(null, "This chest contains a " + ((Item) previousMapCellObject).getItemType() + " (" + ((Item) previousMapCellObject).getItemName() + "), would you like to pick it?", "You approched a chest", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        if(character.backpack.backpackItems.size() >= 10)
                            DialogHelper.showBasicDialog("Your backpack is full");
                        else{
                            Item item = (Item) previousMapCellObject;                            
                            
                            if(character.items.containsKey(item.itemType) && character.items.get(item.itemType) != null)
                                character.backpack.backpackItems.put(item.itemType, item.itemName);                            
                            else    
                                character.items.put(item.itemType, item.itemName);
                                       
                            character.draw();
                            previousMapCellObject = new String(SharedVariables.DEFAULT_CELL_STRING);
                            DialogHelper.showBasicDialog("Awesome, you have picked up a " + item.itemType + " (" + item.itemName + ") from a abandoned chest");
                        }
                    }
                    
                }
                
                else if(previousMapCellObject instanceof String && ((String) previousMapCellObject).equals(SharedVariables.KEY_STRING)){
                    character.setKeyCollectedFlag(true);
                    previousMapCellObject = SharedVariables.DEFAULT_CELL_STRING;;
                }
                
                else if(previousMapCellObject instanceof String && ((String) previousMapCellObject).equals(SharedVariables.EXIT_DOOR_STRING)){
                    if(checkIfTheObjectiveIsCompleted())
                        moveToNextMap();
                    
                    else
                        DialogHelper.showBasicDialog("You need to collect key (If map has one) or kill all the hostile enemies to clear this map");                        
                }
                    
            }
            
            else if(currentMap.mapData[toRowNumber][toColNumber] instanceof Character){
                
                if(((Character) currentMap.mapData[toRowNumber][toColNumber]).getIsFriendlyMonster() == false)
                    attackHostileMonster(toRowNumber, toColNumber);
                else
                    exchangeItemsFromFriendlyMonsters(toRowNumber, toColNumber);
            }

            playerMomentCount++;
            if(playerMomentCount >= 3){
                playerMomentCount = 0;
                GamePlayScreen.this.isTurnFinished = true;
            }
                
        }

        /**
         * This method changes the map if the player completes the current map
         */
        public void moveToNextMap() {
            
            previousMapCellObject = new String(SharedVariables.DEFAULT_CELL_STRING);
            character.setKeyCollectedFlag(false);            
            
            if(currentMapNumber + 1 == campaign.getMapNames().size()){
                JOptionPane.showConfirmDialog(null, "Congrats, you have completed the campaign, you will now go back to main screen", "Map cleared", JOptionPane.PLAIN_MESSAGE);
                GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());
            }
            
            else{
                JOptionPane.showConfirmDialog(null, "Congrats, you have cleared this map, you will now go to next map", "Map cleared", JOptionPane.PLAIN_MESSAGE);
                currentMapNumber++;                
                currentMap = campaign.getMapList().get(currentMapNumber);             
                currentMap.initalizeMapData(character.getName());      
                character.setLevel(character.getLevel() + 1);                
                setMapLevel();
                initComponents();                
            }
            
        }
       
        /**
         * This method lets player to exchange items from friendly monster
         * 
         * @param toRowNumber row number which user is trying to goto
         * @param toColNumber col number which user is trying to goto
         */
        private void exchangeItemsFromFriendlyMonsters(int toRowNumber, int toColNumber) {
           
            Character friendlyMonster = (Character) currentMap.mapData[toRowNumber][toColNumber];
            
            if(JOptionPane.showConfirmDialog(null, "Do you want to exchange items from this friendly monster (" + friendlyMonster.getName() + ") ?","You approched a friendly monster", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                
                if(ExtensionMethods.fetchAllItemNames(character).size() == 0)
                    DialogHelper.showBasicDialog("You don't have any item to exchange.");
                
                else if(character.backpack.backpackItems.size() + character.getAllItems().size() == 0)
                    DialogHelper.showBasicDialog("Enemy doesn't have any items to exchange");
                
                else if(character.backpack.backpackItems.size() >= 10)
                    DialogHelper.showBasicDialog("Your backpack is full");
                
                else{
                    Set<String> characterItems = new HashSet<>();
                    
                    characterItems.addAll(ExtensionMethods.fetchAllItemNames(character));
                    
                    JComboBox<String> itemsList = new JComboBox<String>();
                    for (String string : characterItems)
                        itemsList.addItem(string);
                    
                    JOptionPane.showMessageDialog(null, itemsList, "Select a item to exchange", JOptionPane.QUESTION_MESSAGE);
                    Entry<String, String> entry = ExtensionMethods.getByValue(character.backpack.backpackItems, itemsList.getSelectedItem().toString());
                    
                    if(entry == null)
                        entry = ExtensionMethods.getByValue(character.getAllItems(), itemsList.getSelectedItem().toString());                                       
                    
                    if(character.backpack.backpackItems.remove(entry.getKey(), entry.getValue()) == false)
                        character.items.remove(entry.getKey(), entry.getValue());
                    
                    if(((Character) currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.size() > 0){
                        
                        Random random = new Random();
                        List<String> keys = new ArrayList<String>(((Character) currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.keySet());                        
                        String randomKey = keys.get(random.nextInt(keys.size()));
                        Collection<String> values = ((Character) currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.get(randomKey);
                        
                        String value = (String) values.toArray()[new Random().nextInt(values.size())];
                        ((Character) currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.remove(randomKey, value);
                        
                        if(character.items.containsKey(randomKey))
                            character.backpack.backpackItems.put(randomKey, value);
                        else
                            character.items.put(randomKey, value);
                        
                        character.draw();
                        ((Character) currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.put(entry.getKey(), entry.getValue());
                        ((Character) currentMap.mapData[toRowNumber][toColNumber]).draw();
                        DialogHelper.showBasicDialog("You have received a " + randomKey + " (" + value + ") by the exchange");                                                                                              
                    }
                    
                    else{                        
                        Random random = new Random();
                        List<String> keys = new ArrayList<String>(((Character) currentMap.mapData[toRowNumber][toColNumber]).items.keySet());
                        String randomKey = keys.get(random.nextInt(keys.size()));
                        String value = ((Character) currentMap.mapData[toRowNumber][toColNumber]).items.get(randomKey);
                        
                        ((Character) currentMap.mapData[toRowNumber][toColNumber]).items.remove(randomKey, value);                        
                        
                        if(character.items.containsKey(randomKey))                            
                            character.backpack.backpackItems.put(randomKey, value);                                                
                        else
                            character.items.put(randomKey, value);
                        
                        character.draw();
                        ((Character) currentMap.mapData[toRowNumber][toColNumber]).backpack.backpackItems.put(entry.getKey(), entry.getValue());
                        ((Character) currentMap.mapData[toRowNumber][toColNumber]).draw();
                        DialogHelper.showBasicDialog("You have received a " + randomKey + " (" + value + ") by the exchange");  
                    }           
                    
                    
                }
            }
        }

        /**
         * This method lets player to attach Hostile monster
         * 
         * @param toRowNumber row number which user is trying to goto
         * @param toColNumber col number which user is trying to goto
         */
        private void attackHostileMonster(int toRowNumber, int toColNumber) {
            Character hostileMonster = (Character) currentMap.mapData[toRowNumber][toColNumber];
            hostileMonster.hit(hostileMonster.getHitScore());
        }
    
        /**
         * This method return true or false to state whether the object is completed or not
         * @return true if objective is completed else false
         */
        private boolean checkIfTheObjectiveIsCompleted(){
            
            if(character.isKeyCollected() == true)
                return true;
            
            ArrayList<Character> characters = GameMechanics.getAllCharacterObjects(currentMap);
            for(Character character : characters)
                if(!character.isPlayer() && !character.getIsFriendlyMonster() && character.getHitScore() > 0)
                    return false;
            
            int count = 0;
            for(Character character : characters)
                if(!character.getIsFriendlyMonster())
                    count++;
            
            if(count == 0)
                return false;
            
            return true;
        }
    }
    
    /**
     * This method is called when the selected character object gets updated
     * 
     * @param arg0 Observable
     * @param arg1 Object
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        if(((Character) arg0).isPlayer())
            this.showPlayerDetails(character);
        //else
          this.showPlayerDetails((Character) arg0);
    }
        
    /**
     * This method removes all the previous observable attached to the character object
     */
    public void removePreviousObservables() {
      
          ArrayList<Character> characters = GameMechanics.getAllCharacterObjects(currentMap);        
          for(Character characterObject : characters)
            characterObject.deleteObservers();      
    }
    
    /**
     * This method initializes the loaded game
     */
    public void initLoadGame(){
        this.playerMomentMechanics.setKeyListeners(this);
        this.initComponents();
    }
    
}