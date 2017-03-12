package game.views.jdialogs;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import game.GameLauncher;
import game.components.ExtensionMethods;
import game.views.jpanels.GamePlayScreen;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

/**
 * This class creates JDilog which helps user to select campaign and character to start game play
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 3/8/2017
 */
@SuppressWarnings("serial")
public class StartCampaign extends JDialog{
  
    /**
     * This is a class constructor which creates dialog which helps start of game play
     */
    public StartCampaign(){
        DialogHelper.setDialogProperties(this, "Start gameplay", new Rectangle(100, 100, 265, 150));        
        initComponents();
    }
    
    /**
     * This method initializes UI components
     */
    private void initComponents(){
      
        getContentPane().setLayout(null);               
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 11, 239, 65);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblCharacter = new JLabel("Character");
        lblCharacter.setBounds(10, 11, 64, 14);
        panel.add(lblCharacter);
        
        JLabel lblCampaign = new JLabel("Campaign");
        lblCampaign.setBounds(10, 37, 64, 14);
        panel.add(lblCampaign);
        
        JComboBox<String> characterComboBox = new JComboBox<>(ExtensionMethods.getCharacterList());
        characterComboBox.setBounds(84, 8, 145, 20);
        panel.add(characterComboBox);
        
        JComboBox<String> campaignComboBox = new JComboBox<>(ExtensionMethods.getCampaignsList());
        campaignComboBox.setBounds(84, 34, 145, 20);
        panel.add(campaignComboBox);     
        
        JButton btnStart = new JButton("Start");
        btnStart.setBounds(160, 87, 89, 23);
        btnStart.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
              dispose();
              GameLauncher.mainFrameObject.replaceJPanel(new GamePlayScreen(campaignComboBox.getSelectedItem().toString(), characterComboBox.getSelectedItem().toString()));
          }
        });
        getContentPane().add(btnStart);
    }
}