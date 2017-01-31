package JPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import GameComponents.SharedVariables;
import mainPackage.GameLauncher;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

/**
 * Map Designer helps user to create custom maps.
 * 
 * @author saiteja prasadam
 * @since 30/1/2017
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MapDesigner extends JPanel {

  /**
   * Map Designer helps user to create custom maps.
   * @param mapName Name of the map.
   * @param width   Width of the map.
   * @param height  Height of the map.
   */
  public MapDesigner(String mapName, int width, int height) {
      initComponents(mapName, width, height);     
  }

  /**
   * Initializes all the UI components.
   * @param mapName Name of the map.
   * @param width   Width of the map.
   * @param height  Height of the map.
   */
  private void initComponents(String mapName, int width, int height) {
    
      GridBagLayout gridBagLayout = new GridBagLayout();
      gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
      gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
      gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.5};
      gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
      setLayout(gridBagLayout);
      
      {     //Map panel
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
            
            mapPanel.setLayout(new GridLayout(width, height));
            mapPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            JPanel panel[][] = new JPanel[width][height];
            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    panel[i][j] = new JPanel();
                    panel[i][j].setBackground(SharedVariables.mapDefaultCellColor);                  
                    panel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panel[i][j].setPreferredSize(new Dimension(35, 35));
                    panel[i][j].addMouseListener(new MouseListener() {
                      
                      @Override
                      public void mouseReleased(MouseEvent e) {
                          
                      }
                      
                      @Override
                      public void mousePressed(MouseEvent e) {
                        
                      }
                      
                      @Override
                      public void mouseExited(MouseEvent e) {
                        ((JPanel) e.getSource()).setBackground(SharedVariables.mapDefaultCellColor);
                      }
                      
                      @Override
                      public void mouseEntered(MouseEvent e) {
                        ((JPanel) e.getSource()).setBackground(SharedVariables.mapMouseHoverColor);
                      }
                      
                      @Override
                      public void mouseClicked(MouseEvent e) {
                                                
                      }
                    });
                    mapPanel.add(panel[i][j]);
                }
            }             
      }
      
      {     //Design panel
            JPanel designPanel = new JPanel();
            designPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.GRAY, null));
            designPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc_designPanel = new GridBagConstraints();
            gbc_designPanel.gridheight = 5;
            gbc_designPanel.fill = GridBagConstraints.BOTH;
            gbc_designPanel.gridx = 4;
            gbc_designPanel.gridy = 0;
            add(designPanel, gbc_designPanel);
            GridBagLayout gbl_designPanel = new GridBagLayout();
            gbl_designPanel.columnWidths = new int[]{0};
            gbl_designPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            gbl_designPanel.columnWeights = new double[]{1.0};
            gbl_designPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
            designPanel.setLayout(gbl_designPanel);
            
            JPanel mapNamePanel = new JPanel();
            mapNamePanel.setBackground(Color.WHITE);
            mapNamePanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
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
            
            componentsPanel.add(new JButton("test"));         
            JLabel label = new JLabel("Wall");
            label.setFont(new Font("Tahoma", Font.BOLD, 12));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(label);
            
            componentsPanel.add(new JButton("test"));
            JLabel label_1 = new JLabel("Player");
            label_1.setHorizontalAlignment(SwingConstants.CENTER);
            label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
            componentsPanel.add(label_1);
            
            componentsPanel.add(new JButton("test"));
            JLabel label_2 = new JLabel("Monster");
            label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
            label_2.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(label_2);
            
            componentsPanel.add(new JButton("test"));
            JLabel label_3 = new JLabel("Entry door");
            label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
            label_3.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(label_3);
            
            componentsPanel.add(new JButton("test"));
            JLabel label_4 = new JLabel("Exit door");
            label_4.setFont(new Font("Tahoma", Font.BOLD, 12));
            label_4.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(label_4);
            
            componentsPanel.add(new JButton("test"));
            JLabel label_5 = new JLabel("Chest");
            label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
            label_5.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(label_5);
            
            componentsPanel.add(new JButton("test"));
            JLabel label_6 = new JLabel("Key");
            label_6.setFont(new Font("Tahoma", Font.BOLD, 12));
            label_6.setHorizontalAlignment(SwingConstants.CENTER);
            componentsPanel.add(label_6);
            
            JButton btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  GameLauncher.mainFrameObject.replaceJPanel(new LaunchScreen());
              }
            });
            btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
            GridBagConstraints gbc_btnCancel = new GridBagConstraints();
            gbc_btnCancel.fill = GridBagConstraints.BOTH;
            gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
            gbc_btnCancel.gridx = 0;
            gbc_btnCancel.gridy = 8;
            designPanel.add(btnCancel, gbc_btnCancel);
            
            
            JButton btnSaveButton = new JButton("Save");
            btnSaveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
            GridBagConstraints gbc_btnSaveButton = new GridBagConstraints();
            btnSaveButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            gbc_btnSaveButton.fill = GridBagConstraints.BOTH;
            gbc_btnSaveButton.gridx = 0;
            gbc_btnSaveButton.gridy = 9;
            designPanel.add(btnSaveButton, gbc_btnSaveButton);
      }
  }

}