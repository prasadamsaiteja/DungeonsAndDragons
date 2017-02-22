package JDilaogs;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import JDialogs.actionlisteners.CharacterDialogBtnAddActionListener;
import JDialogs.viewmodels.CharactersListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GameComponents.ExtensionMethods;
import GameComponents.SharedVariables;
import JPanels.MapDesigner;
import ModelClasses.Map;
import jaxb.MapJaxb;
import mainPackage.GameLauncher;

import java.awt.Font;
import java.awt.GridLayout;

/**
 * This class is a JDialog which helps user to create new campaign, map or character.
 * 
 * @author saiteja prasdam
 * @version 1.0
 * @since 1/29/2017
 */

@SuppressWarnings("serial")
public class CreateStuffDialog extends JDialog{
  
  private SpringLayout sl_mapsPanel;
  private SpringLayout sl_characterPanel;
  private int defaultTab = 0;
  private String lastCreateMapName;
 
  /**
   * This class is a JDialog which helps user to create new campaign, map or character.
   * @param mapName 
   * @param i 
   */
  public CreateStuffDialog() {
    DialogHelper.setDialogProperties(this, "Create stuff", new Rectangle(100, 100, 640, 390));
    getContentPane().setLayout(null);
    initComponents();
  }

  /**
   * This class is a JDialog which helps user to create new campaign, map or character.
   */
  public CreateStuffDialog(int defaultTabIndex, String mapName) {
    DialogHelper.setDialogProperties(this, "Create stuff", new Rectangle(100, 100, 640, 390));
    getContentPane().setLayout(null); 
    defaultTab = defaultTabIndex;
    lastCreateMapName = mapName;
    initComponents();
  }

  /**
   * Initializes the UI components.
   */
  private void initComponents() {
    
    JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    tabbedPane.setBounds(10, 11, 615, 312);
    getContentPane().add(tabbedPane);
    
    //Campaign panel
    tabbedPane.addTab("Campaigns", null, initCampaignPanel(), null); 
    
    //Maps panel
    tabbedPane.addTab("Maps", null, initMapsPanel(), null);
    
    //Character panel
    tabbedPane.addTab("Character", null, initCharacterPanel(), null);
    
    //Items panel 
    tabbedPane.addTab("Items", null, initItemsPanel(), null);
    
    if(defaultTab != 0)
      tabbedPane.setSelectedIndex(defaultTab - 1);

    {   //Done button
        JButton btnDone = new JButton("Done");
        btnDone.addActionListener(new ActionListener() {
          @Override
		public void actionPerformed(ActionEvent arg0) {
            dispose();
          }
        });
        btnDone.setBounds(536, 327, 89, 23);
        getContentPane().add(btnDone);
    }
  }

  /**
   * Initializes the Character panel.
   * @param characterPanel This contains reference character panel tab.
   * @return 
   */
  private JPanel initCharacterPanel() {
    
      JPanel characterPanel = new JPanel();
      characterPanel.setBackground(Color.WHITE);     
    
      sl_characterPanel = new SpringLayout();
      characterPanel.setLayout(sl_characterPanel);

      CharactersListModel characterList = new CharactersListModel();
      JList<String> characterJlist = new JList<String>(characterList);

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setViewportView(characterJlist);
      
      sl_characterPanel.putConstraint(SpringLayout.NORTH, characterJlist, 10, SpringLayout.NORTH, characterPanel);
      sl_characterPanel.putConstraint(SpringLayout.WEST, characterJlist, 10, SpringLayout.WEST, characterPanel);
      sl_characterPanel.putConstraint(SpringLayout.EAST, characterJlist, 600, SpringLayout.WEST, characterPanel);
      characterPanel.add(characterJlist);      
      
      JButton btnAdd = new JButton("Create");
      ActionListener btnAddActionListener = new CharacterDialogBtnAddActionListener(CreateStuffDialog.this, characterList);
      btnAdd.addActionListener(btnAddActionListener);
      sl_characterPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, characterPanel);
      sl_characterPanel.putConstraint(SpringLayout.SOUTH, characterJlist, -6, SpringLayout.NORTH, btnAdd);
      sl_characterPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, characterPanel);
      sl_characterPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, characterPanel);
      characterPanel.add(btnAdd);
      
      JButton btnEdit = new JButton("Edit");
      btnEdit.setEnabled(false);
      sl_characterPanel.putConstraint(SpringLayout.EAST, btnEdit, 92, SpringLayout.WEST, characterJlist);        
      sl_characterPanel.putConstraint(SpringLayout.NORTH, btnEdit, 6, SpringLayout.SOUTH, characterJlist);
      sl_characterPanel.putConstraint(SpringLayout.WEST, btnEdit, 0, SpringLayout.WEST, characterJlist);
      characterPanel.add(btnEdit);  
      
      JButton btnRemove = new JButton("Remove");
      btnRemove.setEnabled(false);
      sl_characterPanel.putConstraint(SpringLayout.NORTH, btnRemove, 6, SpringLayout.SOUTH, characterJlist);
      sl_characterPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
      sl_characterPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);
      characterPanel.add(btnRemove);      
      characterJlist.addListSelectionListener(new ListSelectionListener() {          
        @SuppressWarnings("rawtypes")
        @Override
        public void valueChanged(ListSelectionEvent e) {
              
             if(((JList) e.getSource()).getSelectedValue() == null){
                 btnRemove.setEnabled(false);
                 btnEdit.setEnabled(false);
             }
             
             else{
               btnRemove.setEnabled(true);
               btnEdit.setEnabled(true);
               }
                   
           }
       });
      
      return characterPanel;
  }

  /**
   * Initializes the Map panel.
   * @param mapsPanel   This contains reference to Map panel tab.
   * @return 
   */
  private JPanel initMapsPanel() {
    
        JPanel mapsPanel = new JPanel();
        mapsPanel.setBackground(Color.WHITE);
    
        sl_mapsPanel = new SpringLayout();
        mapsPanel.setLayout(sl_mapsPanel);
                
        DefaultListModel<String> mapsJlistModel = new DefaultListModel<>();
        JList<String> mapsJlist = new JList<String>(mapsJlistModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(mapsJlist);
        
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, mapsJlist, 10, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, mapsJlist, 10, SpringLayout.WEST, mapsPanel);
        
        String[] mapsList = ExtensionMethods.getMapsList();
        for(String mapName : mapsList)
            mapsJlistModel.addElement(mapName);
        
        mapsJlist.setFont(new Font("Tahoma", Font.BOLD, 12));
        mapsJlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        mapsPanel.add(mapsJlist);
        
        JButton btnAdd = new JButton("Create");        
        sl_mapsPanel.putConstraint(SpringLayout.EAST, mapsJlist, 0, SpringLayout.EAST, btnAdd);
        btnAdd.addActionListener(new ActionListener() {
          @Override
		public void actionPerformed(ActionEvent arg0) {              
              new NewMapDialog(CreateStuffDialog.this);
          }
        });
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, mapsPanel);
        mapsPanel.add(btnAdd);
        
        JButton btnEdit = new JButton("Edit");
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, btnEdit, 251, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.SOUTH, mapsJlist, -6, SpringLayout.NORTH, btnEdit);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, btnEdit, 10, SpringLayout.WEST, mapsPanel);
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
              if(mapsJlist.getSelectedValue() != null){
                  GameLauncher.mainFrameObject.replaceJPanel(new MapDesigner(mapsJlist.getSelectedValue())); 
                  dispose();
              }
          }

        });
        mapsPanel.add(btnEdit);
        
        JButton btnRemove = new JButton("Remove");
        btnRemove.setEnabled(false);
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, btnRemove, 251, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, btnEdit, -282, SpringLayout.WEST, btnRemove);        
        sl_mapsPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);        
        btnRemove.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
              if(mapsJlist.getSelectedValue() != null){
                    MapJaxb.deleteMapXml((String) mapsJlist.getSelectedValue());         
                    mapsJlistModel.clear();
                    
                    String[] mapsList = ExtensionMethods.getMapsList();
                    for(String mapName : mapsList)
                      mapsJlistModel.addElement(mapName);
              }
          }

        });
        mapsPanel.add(btnRemove);   
        
        JPanel mapPreviewPanel = new JPanel(); 
        mapPreviewPanel.setBackground(Color.WHITE);
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, mapPreviewPanel, 10, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, mapPreviewPanel, 6, SpringLayout.EAST, mapsJlist);
        sl_mapsPanel.putConstraint(SpringLayout.SOUTH, mapPreviewPanel, -6, SpringLayout.NORTH, btnAdd);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, mapPreviewPanel, -10, SpringLayout.EAST, mapsPanel);
        mapsPanel.add(mapPreviewPanel);
        
        mapsJlist.addListSelectionListener(new ListSelectionListener() {
          
          @SuppressWarnings("rawtypes")
          @Override
          public void valueChanged(ListSelectionEvent evt) {
                
              if (!evt.getValueIsAdjusting())
              {
                  
                    if(((JList) evt.getSource()).getSelectedValue() == null){
                        btnRemove.setEnabled(false);
                        btnEdit.setEnabled(false);
                        sl_mapsPanel.putConstraint(SpringLayout.EAST, mapsJlist, 0, SpringLayout.EAST, btnAdd);
                    }               
                    
                    else{
                      btnRemove.setEnabled(true);
                      btnEdit.setEnabled(true);                
                      updateMapPreview(mapPreviewPanel, (String)((JList) evt.getSource()).getSelectedValue());
                      sl_mapsPanel.putConstraint(SpringLayout.EAST, mapsJlist, -228, SpringLayout.EAST, btnAdd);
                    }
                }
                                  
             }

          private void updateMapPreview(JPanel mapPreviewPanel, String mapName) {
                        
              Map mapObject = MapJaxb.getMapFromXml(mapName);
             
              mapPreviewPanel.removeAll();              
              mapPreviewPanel.setLayout(new GridLayout(mapObject.mapWidth, mapObject.mapHeight));
              JPanel[][] mapJPanelArray = new JPanel[mapObject.mapWidth][mapObject.mapHeight];
              
              for(int i = 0; i < mapObject.mapWidth; i++)            
                  for(int j = 0; j < mapObject.mapHeight; j++)
                  {
                      mapJPanelArray[i][j] = new JPanel();
                      mapJPanelArray[i][j].setBackground(SharedVariables.mapCellHashMap.get(mapObject.mapCellValues[i][j]));                    
                      mapJPanelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                      mapPreviewPanel.add(mapJPanelArray[i][j]);                      
                  }                
              
              mapPreviewPanel.revalidate();
              mapPreviewPanel.repaint();
          }

        });
        
        mapsJlist.setSelectedValue(lastCreateMapName, true);
        return mapsPanel;
  }
  
  /**
   * Initializes the Items panel.
   * @param itemsPanel   This contains reference to Items panel tab.
   * @return 
   */  
  private JPanel initItemsPanel() {
    
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.WHITE);
    
        SpringLayout sl_itemsPanel = new SpringLayout();
        itemsPanel.setLayout(sl_itemsPanel);
        
        DefaultListModel<String> itemJlistModel = new DefaultListModel<>();
        JList<String> itemJlist = new JList<String>(itemJlistModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(itemJlist);
        
        itemJlist.setFont(new Font("Tahoma", Font.BOLD, 12));
        itemJlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, itemJlist, 10, SpringLayout.NORTH, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, itemJlist, 10, SpringLayout.WEST, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, itemJlist, 600, SpringLayout.WEST, itemsPanel);
        itemsPanel.add(itemJlist);
        
        JButton btnAdd = new JButton("Create");
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.SOUTH, itemJlist, -6, SpringLayout.NORTH, btnAdd);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, itemsPanel);
        btnAdd.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
              new NewItemDialog();
          }
        });
        itemsPanel.add(btnAdd);
        
        JButton btnEdit = new JButton("Edit");
        btnEdit.setEnabled(false);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, btnEdit, 92, SpringLayout.WEST, itemJlist);        
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, btnEdit, 6, SpringLayout.SOUTH, itemJlist);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, btnEdit, 0, SpringLayout.WEST, itemJlist);        
        itemsPanel.add(btnEdit);         
        
        JButton btnRemove = new JButton("Remove");
        btnRemove.setEnabled(false);
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, btnRemove, 6, SpringLayout.SOUTH, itemJlist);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);
        itemsPanel.add(btnRemove);
        
        itemJlist.addListSelectionListener(new ListSelectionListener() {
          @SuppressWarnings("rawtypes")
          @Override
          public void valueChanged(ListSelectionEvent e) {
                
               if(((JList) e.getSource()).getSelectedValue() == null){
                   btnRemove.setEnabled(false);
                   btnEdit.setEnabled(false);
               }
               
               else{
                 btnRemove.setEnabled(true);
                 btnEdit.setEnabled(true);
                 }
                     
             }
         });
        
        return itemsPanel;
  }
  
  /**
   * Initializes the Campaign panel.
   * @param campaignPanel   This contains reference to Campaign panel tab.
   * @return 
   */
  private JPanel initCampaignPanel() {
      
      JPanel campaignPanel = new JPanel();
      campaignPanel.setBackground(Color.WHITE);      
    
      SpringLayout sl_campaignPanel = new SpringLayout();
      campaignPanel.setLayout(sl_campaignPanel);
      
      DefaultListModel<String> campaignJlistModel = new DefaultListModel<>();
      JList<String> campaignJlist = new JList<String>(campaignJlistModel);
      
      campaignJlist.setFont(new Font("Tahoma", Font.BOLD, 12));
      campaignJlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
      sl_campaignPanel.putConstraint(SpringLayout.NORTH, campaignJlist, 10, SpringLayout.NORTH, campaignPanel);
      sl_campaignPanel.putConstraint(SpringLayout.WEST, campaignJlist, 10, SpringLayout.WEST, campaignPanel);
      sl_campaignPanel.putConstraint(SpringLayout.EAST, campaignJlist, 600, SpringLayout.WEST, campaignPanel);
      campaignPanel.add(campaignJlist);
      
      JButton btnAdd = new JButton("Create");
      sl_campaignPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, campaignPanel);
      sl_campaignPanel.putConstraint(SpringLayout.SOUTH, campaignJlist, -6, SpringLayout.NORTH, btnAdd);
      sl_campaignPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, campaignPanel);
      sl_campaignPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, campaignPanel);
      campaignPanel.add(btnAdd);
      
      JButton btnEdit = new JButton("Edit");
      btnEdit.setEnabled(false);
      sl_campaignPanel.putConstraint(SpringLayout.EAST, btnEdit, 92, SpringLayout.WEST, campaignJlist);
      sl_campaignPanel.putConstraint(SpringLayout.NORTH, btnEdit, 6, SpringLayout.SOUTH, campaignJlist);
      sl_campaignPanel.putConstraint(SpringLayout.WEST, btnEdit, 0, SpringLayout.WEST, campaignJlist);
      campaignPanel.add(btnEdit);
      
      JButton btnRemove = new JButton("Remove");
      btnRemove.setEnabled(false);
      sl_campaignPanel.putConstraint(SpringLayout.NORTH, btnRemove, 6, SpringLayout.SOUTH, campaignJlist);
      sl_campaignPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
      sl_campaignPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);
      campaignPanel.add(btnRemove);
      
      campaignJlist.addListSelectionListener(new ListSelectionListener() {
        @SuppressWarnings("rawtypes")
        @Override
        public void valueChanged(ListSelectionEvent e) {
              
             if(((JList) e.getSource()).getSelectedValue() == null){
                 btnRemove.setEnabled(false);
                 btnEdit.setEnabled(false);
             }
             
             else{
               btnRemove.setEnabled(true);
               btnEdit.setEnabled(true);
               }
                   
           }
       });
            
      return campaignPanel;
  }
}