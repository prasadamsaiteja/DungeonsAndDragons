package game.views.jdialogs;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import game.model.character.Character;
import game.views.jdialogs.viewmodels.UpdateCharacterPanelInfo;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class CharacterInventoryDialog extends JDialog
{
    private Character c;
    
    public CharacterInventoryDialog(Character c) {
        setAlwaysOnTop(true);
        this.c = c;
        DialogHelper.setDialogProperties(this, "Character Inventory", new Rectangle(0, 0, 400, 400));
        initComponents();
    }
    
    public void initComponents(){
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1};
        gridBagLayout.rowHeights = new int[]{1};
        gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
                        
     // Create a new panel for previewing selected character
        JPanel charPreviewPanel = new JPanel();
        charPreviewPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc_charPreviewPanel = new GridBagConstraints();
        gbc_charPreviewPanel.weighty = 400.0;
        gbc_charPreviewPanel.weightx = 400.0;
        gbc_charPreviewPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_charPreviewPanel.gridx = 0;
        gbc_charPreviewPanel.gridy = 0;
        getContentPane().add(charPreviewPanel, gbc_charPreviewPanel);
       
        GridBagLayout gbl_charPreviewPanel = new GridBagLayout();
        gbl_charPreviewPanel.columnWidths = new int[]{0, 400};
        gbl_charPreviewPanel.rowHeights = new int[]{0, 400};
        gbl_charPreviewPanel.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_charPreviewPanel.rowWeights = new double[]{Double.MIN_VALUE};
        charPreviewPanel.setLayout(gbl_charPreviewPanel);
        
        // Create a text pane
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        
        try
        {
            UpdateCharacterPanelInfo charPanelInfo = new UpdateCharacterPanelInfo(doc);
            charPanelInfo.setCharacter(c);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        textPane.setEditable(false);
        
        JScrollPane areaScrollPane = new JScrollPane(textPane);
        areaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        GridBagConstraints gbc_areaScrollPane = new GridBagConstraints();
        gbc_areaScrollPane.gridwidth = 10;
        gbc_areaScrollPane.gridheight = 10;
        gbc_areaScrollPane.fill = GridBagConstraints.BOTH;
        gbc_areaScrollPane.gridx = 0;
        gbc_areaScrollPane.gridy = 1;
        charPreviewPanel.add(areaScrollPane, gbc_areaScrollPane);
    }
}
