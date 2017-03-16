package game.views.jpanels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import game.GameLauncher;
import game.components.GameMechanics;
import game.components.SharedVariables;
import game.model.Campaign;
import game.model.Item;
import game.model.Map;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.jaxb.CampaignJaxb;
import game.model.onclickListeners.MapClickListener;
import game.views.jdialogs.DialogHelper;

/**
 * This JPanel is the game play screen 
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 3/8/2017
 */
@SuppressWarnings("serial")
public class GamePlayScreen extends JPanel implements Observer{
  
    private JPanel mapJPanelArray[][];
    private Character character;
    private Campaign campaign;    
    private Map currentMap;
    private int currentMapNumber = 0;
    private Object previousMapCellObject = SharedVariables.DEFAULT_CELL_STRING;
    private JPanel characterDetailsPanel;
  
    /**
     * This is constructor method for this class
     * @param camapaignName This is the campaign to be loaded
     * @param characterName This is the character to be loaded
     */
    public GamePlayScreen(String camapaignName, String characterName){
      
         this.campaign = CampaignJaxb.getCampaignFromXml(camapaignName);
         this.character = CharactersList.getByName(characterName).clone();
         this.character.setPlayerFlag(true);
         
         if(campaign == null || character == null)
             DialogHelper.showBasicDialog("Error reading saved files");
         
         else{
             this.setKeyListeners();
             this.campaign.fetchMaps();
             this.currentMap = campaign.getMapList().get(currentMapNumber);             
             this.currentMap.initalizeMapData(this.character.getName());             
             this.setMapLevel();
             initComponents();
         }
           
    }
    
    /**
     * This method set the level of the enemy and item using by players and enemies, matching to the player
     */
    private void setMapLevel(){
        
        for (int i = 0; i < currentMap.mapWidth; i++)      
            for (int j = 0; j < currentMap.mapHeight; j++)
                if(currentMap.mapData[i][j] instanceof Character && (!((Character) currentMap.mapData[i][j]).isPlayer()))
                        ((Character) currentMap.mapData[i][j]).setLevel(character.getLevel());
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
     * @param RadioButtonGroup Radio button group for selection of place marker
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
      
       showPlayerDetails(character);
   }
    
    /**
     * This method displays the character details on the screen
     * @param character Character object that needs to be displayed on the screen
     */
    public void showPlayerDetails(Character character) {
                                
          this.removePreviousObservables();
          character.addObserver(this);
          characterDetailsPanel.removeAll();
          
          for(int index = 0; index < 5; index++){
              
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
          characterDetailsPanel.add(openInventory);  
          characterDetailsPanel.revalidate();
    
    }                  
       
    /**
     * This method repaints the map after the action is completed
     */
    private void repaintMap() {
     
     for (int i = 0; i < currentMap.mapWidth; i++)      
       for (int j = 0; j < currentMap.mapHeight; j++) {
         
         for(MouseListener listener : mapJPanelArray[i][j].getMouseListeners())  
             mapJPanelArray[i][j].removeMouseListener(listener);
           
           mapJPanelArray[i][j].addMouseListener(new MapClickListener(this, currentMap.mapData[i][j]));
           mapJPanelArray[i][j] = GameMechanics.setMapCellDetailsFromMapObjectData(mapJPanelArray[i][j], currentMap.mapData[i][j]);           
       }                  
    }
        
    /**
     * This method set's up key binding for player moments
     */
    private void setKeyListeners(){
      
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "UP_PRESSED");
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "UP_PRESSED");
        
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "DOWN_PRESSED");
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "DOWN_PRESSED");
        
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "LEFT_PRESSED");
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "LEFT_PRESSED");
        
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT_PRESSED");
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "RIGHT_PRESSED");

        PlayerMomentMechanics playerMomentMechanics = new PlayerMomentMechanics();
        
        this.getActionMap().put("UP_PRESSED", playerMomentMechanics.new UP_PRESSED());
        this.getActionMap().put("DOWN_PRESSED", playerMomentMechanics.new DOWN_PRESSED());
        this.getActionMap().put("LEFT_PRESSED", playerMomentMechanics.new LEFT_PRESSED());
        this.getActionMap().put("RIGHT_PRESSED", playerMomentMechanics.new RIGHT_PRESSED());        
    }
              
    /**
     * This class contains all the player moment mechanics classes and methods 
     * @author saiteja prasadm
     * @since 3/11/2017
     * @version 1.0.0
     */
    class PlayerMomentMechanics{
        
        /**
         * This class actionPerformed is triggered when up or w is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        class UP_PRESSED extends AbstractAction {

          @Override
          public void actionPerformed(ActionEvent e) {
                          
               int[] position = GameMechanics.getPlayerPosition(currentMap);
               int rowNumber = position[0];
               int colNumber = position[1];
               if(rowNumber != 0)
                 movePlayer(rowNumber, colNumber, rowNumber - 1, colNumber);                      
          }
        }
    
        /**
         * This class actionPerformed is triggered when down or s is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        class DOWN_PRESSED extends AbstractAction {

          @Override
          public void actionPerformed(ActionEvent e) {
              
              int[] position = GameMechanics.getPlayerPosition(currentMap);
              int rowNumber = position[0];
              int colNumber = position[1];
              
              if(rowNumber < currentMap.mapHeight - 1)
                movePlayer(rowNumber, colNumber, rowNumber + 1, colNumber);               
          }
        }
        
        /**
         * This class actionPerformed is triggered when left or a is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        class LEFT_PRESSED extends AbstractAction {

          @Override
          public void actionPerformed(ActionEvent e) {
            
              int[] position = GameMechanics.getPlayerPosition(currentMap);
              int rowNumber = position[0];
              int colNumber = position[1];
              
              if(colNumber != 0)
                movePlayer(rowNumber, colNumber, rowNumber, colNumber - 1);    
          }
          
        }
        
        /**
         * This class actionPerformed is triggered when right or d is pressed by the user.
         * @author saiteja prasadam
         * @version 1.0.0
         * @since 3/9/2017
         *
         */
        class RIGHT_PRESSED extends AbstractAction {

          @Override
          public void actionPerformed(ActionEvent e) {
            
              int[] position = GameMechanics.getPlayerPosition(currentMap);
              int rowNumber = position[0];
              int colNumber = position[1];
              
              if(colNumber < currentMap.mapWidth - 1)
                movePlayer(rowNumber, colNumber, rowNumber, colNumber + 1);       
          }
        }
        
        
        /**
         * This method moves the player position
         * @param fromRowNumber from row position of player
         * @param fromColNumber from col position of player
         * @param toRowNumber to row position of player
         * @param toColNumber to col position of player
         */
        private void movePlayer(int fromRowNumber, int fromColNumber, int toRowNumber, int toColNumber){
          
            if(!currentMap.mapData[toRowNumber][toColNumber].equals(SharedVariables.WALL_STRING) && ! (currentMap.mapData[toRowNumber][toColNumber] instanceof Character)){
              
                Object tempPreviousMapCellObject = previousMapCellObject;
                previousMapCellObject = currentMap.mapData[toRowNumber][toColNumber];             
                currentMap.mapData[toRowNumber][toColNumber] = currentMap.mapData[fromRowNumber][fromColNumber];
                currentMap.mapData[fromRowNumber][fromColNumber] = tempPreviousMapCellObject;              
                repaintMap(); 
                
                if(previousMapCellObject instanceof Item){              
                    JOptionPane.showConfirmDialog(null, "This chest contains a " + ((Item) previousMapCellObject).getItemType() + " (" + ((Item) previousMapCellObject).getItemName() + "), would you like to pick it?", "You approched a chest", JOptionPane.YES_NO_OPTION);
                }
                
                else if(previousMapCellObject instanceof String && ((String) previousMapCellObject).equals(SharedVariables.KEY_STRING)){
                    character.setKeyCollectedFlag(true);
                    previousMapCellObject = SharedVariables.DEFAULT_CELL_STRING;;
                }
                
                else if(previousMapCellObject instanceof String && ((String) previousMapCellObject).equals(SharedVariables.EXIT_DOOR_STRING)){
                    if(checkIfTheObjectiveIsCompleted())
                        moveToNextMap();
                    
                    else
                        DialogHelper.showBasicDialog("You need to collecte key (If map has one) or kill all the hostile enemies to clear this map");                        
                }
                    
            }
            
            else if(currentMap.mapData[toRowNumber][toColNumber] instanceof Character){
                
                if(((Character) currentMap.mapData[toRowNumber][toColNumber]).getIsFriendlyMonster() == false)
                    attackHostileMonster(toRowNumber, toColNumber);
                else
                    exchangeItemsFromFriendlyMonsters(toRowNumber, toColNumber);
            }
                   
        }

        /**
         * This method changes the map if the player completes the current map
         */
        private void moveToNextMap() {
            
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
         * @param toRowNumber row number which user is tryiing to goto
         * @param toColNumber col number which user is trying to goto
         */
        private void exchangeItemsFromFriendlyMonsters(int toRowNumber, int toColNumber) {
            Character friendlyMonster = (Character) currentMap.mapData[toRowNumber][toColNumber];
            JOptionPane.showConfirmDialog(null, "Do you want to exchange items from this friendly monster (" + friendlyMonster.getName() + ") ?","You approched a friendly monster", JOptionPane.YES_NO_OPTION);
        }

        /**
         * This method lets player to attach Hostile monster
         * @param toRowNumber row number which user is tryiing to goto
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
            
            return true;            
        }
    }
               
    /**
     * This method is called when the selected character object gets updated
     */
    @Override
    public void update(Observable arg0, Object arg1) {
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
    
}