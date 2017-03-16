package game.views.jdialogs.characterDialog;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import game.components.ExtensionMethods;
import game.model.Campaign;
import game.model.jaxb.CampaignJaxb;
import game.views.jdialogs.DialogHelper;

/**
 * This class is a jdialog that sets the name and maps to a particular campaign
 * 
 * @author RahulReddy
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class NewCharacterInfoDialog extends JDialog
{

    private String characterName;
    private ArrayList<String> backpackItems;
    private Character characterObject;

    public NewCharacterInfoDialog(Character character)
    {
        DialogHelper.setDialogProperties(NewCharacterInfoDialog.this, "Edit backpack", new Rectangle(554, 448));
        characterObject = character;
       
    }
    
    /**
     * Sets the List(SAVED,Added) properties present in the dialog
     * 
     * @param List List Object
     */
    public void setListProperties(JList<String> List)
    {
        List.setLayoutOrientation(JList.VERTICAL);
        List.setBackground(SystemColor.menu);
        List.setFont(new Font("Tahoma", Font.BOLD, 12));
        List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
