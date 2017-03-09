package game.views.jpanels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import game.components.SharedVariables;
import game.model.Campaign;
import game.model.Map;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.jaxb.CampaignJaxb;
import game.views.jdialogs.DialogHelper;

/**
 * This JPanel is the game play screen 
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 3/8/2017
 */
@SuppressWarnings("serial")
public class GamePlayScreen extends JPanel{
  
    private JPanel mapJPanelArray[][]; // Map grid
    private Character character;
    private Campaign campaign;    
    private Map currentMap;
  
    /**
     * This is constructor method for this class
     */
    public GamePlayScreen(String camapaignName, String characterName){
      
         this.campaign = CampaignJaxb.getCampaignFromXml(camapaignName);
         this.character = CharactersList.getByName(characterName);
         
         if(campaign == null || character == null)
             DialogHelper.showBasicDialog("Error reading saved files");
         
         else{
             campaign.fetchMaps();
             this.currentMap = campaign.getMapList().get(0);  
             setKeyListeners();
             initComponents();
         }
           
    }
    
    /**
     * This method initializes UI components
     */
    private void initComponents(){
      
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
              mapJPanelArray[i][j].setBackground(SharedVariables.mapCellHashMap.get(currentMap.mapCellValues[i][j]));
              
              if(mapJPanelArray[i][j].getBackground() == SharedVariables.mapCellHashMap.get(SharedVariables.ENTRY_DOOR_STRING))
                mapJPanelArray[i][j].setBackground(SharedVariables.mapCellHashMap.get(SharedVariables.PLAYER_STRING));
                
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
           
           for(int index = 0; index < 3; index++){
             
               String key = "", value = "";
               if(index == 0){
                   key = "Campaign Name : ";
                   value = campaign.getCampaignName();
               }
               
               else if(index == 1){
                   key = "Character Name : ";
                   value = character.getName();                   
               }
               
               else{
                   key = "Current Map Name : ";
                   value = currentMap.getMapName();
               }
             
               JPanel campaignNameJpanel = new JPanel();
               campaignDetailsPanel.add(campaignNameJpanel);
               campaignNameJpanel.setBackground(Color.WHITE);
               campaignNameJpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
               
               JLabel campaginLabel = new JLabel(key);
               campaginLabel.setHorizontalAlignment(SwingConstants.CENTER);
               campaginLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
               campaignNameJpanel.add(campaginLabel);
               
               JLabel campaignNameValueLabel = new JLabel(value);
               campaignNameValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
               campaignNameValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
               campaignNameJpanel.add(campaignNameValueLabel);
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
        
        this.getActionMap().put("UP_PRESSED", new UP_PRESSED());
        this.getActionMap().put("DOWN_PRESSED", new DOWN_PRESSED());
        this.getActionMap().put("LEFT_PRESSED", new LEFT_PRESSED());
        this.getActionMap().put("RIGHT_PRESSED", new RIGHT_PRESSED());
        
    }
    
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
          System.out.println("Fired up");
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
          System.out.println("Fired down");
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
          System.out.println("Fired left");
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
          System.out.println("Fired right");
      }
    }
    
}