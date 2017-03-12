package game.views.jdialogs.campaignDialog;

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
import game.components.SharedVariables;
import game.model.Campaign;
import game.model.jaxb.CampaignJaxb;
import game.views.jdialogs.DialogHelper;

/**
 * This class is a Jdialog that sets the name and maps to a particular campaign
 * 
 * @author RahulReddy
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class NewCampaignInfoDialog extends JDialog
{

    private String campaignName;
    private ArrayList<String> addedMaps;
    private Campaign campaignObject;

    /**
     * Constructor that sets the name of the Campaign in the dialog
     * 
     * @param nameValue Name of the campaign
     * @wbp.parser.constructor
     */
    public NewCampaignInfoDialog(String nameValue)
    {
        DialogHelper.setDialogProperties(NewCampaignInfoDialog.this, "New Campaign", new Rectangle(554, 448));
        this.campaignName = nameValue;
        initComponents();
    }

    /**
     * Constructor that takes the campaign object and performs operations on
     * Added Maps List
     * 
     * @param campaignFromXml Campaign Object
     */
    public NewCampaignInfoDialog(Campaign campaignFromXml)
    {
        DialogHelper.setDialogProperties(NewCampaignInfoDialog.this, "Edit Campaign", new Rectangle(554, 448));
        campaignObject = campaignFromXml;
        initComponents();
    }

    /**
     * This Method Initializes the Components in the Current Dialog
     */
    private void initComponents()
    {

        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(null);

        // This Panel displays saved Maps in the MLIGHT_AP directory
        JPanel savedMapsPanel = new JPanel();
        savedMapsPanel.setLayout(new BorderLayout());
        savedMapsPanel.setBounds(36, 56, 197, 320);

        // Maps list
        DefaultListModel<String> savedMapsListModel = new DefaultListModel<String>();
        JList<String> savedMapsList = new JList<>(savedMapsListModel);
        String[] mapsList = ExtensionMethods.getMapsList();

        for (String mapName : mapsList)
            savedMapsListModel.addElement(mapName);

        // Adding Scroll Bar to Maps List
        JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(savedMapsList);
        savedMapsPanel.add(listScroller, BorderLayout.CENTER);

        setListProperties(savedMapsList);
        getContentPane().add(savedMapsPanel);

        // Added maps to campaign
        JPanel addedMapsPanel = new JPanel();
        addedMapsPanel.setLayout(new BorderLayout());
        addedMapsPanel.setBackground(SystemColor.menu);
        addedMapsPanel.setBounds(317, 56, 197, 320);

        DefaultListModel<String> addedMapsListModel = new DefaultListModel<String>();
        JList<String> addedMapsList = new JList<String>(addedMapsListModel);

        if (campaignObject != null)
        {
            for (String mapName : campaignObject.getMapNames())
                addedMapsListModel.addElement(mapName);
        }

        // Adding Scroll Bar to maps list 2
        JScrollPane listScroller2 = new JScrollPane();
        listScroller2.setViewportView(addedMapsList);
        addedMapsPanel.add(listScroller2, BorderLayout.CENTER);

        setListProperties(addedMapsList);
        getContentPane().add(addedMapsPanel);

        // Button for Adding Maps to Campaign
        JButton btnAdd = new JButton("+");
        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnAdd.setBounds(243, 145, 64, 23);
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (savedMapsList.getSelectedValue() != null)
                {
                	 if(addedMapsListModel.getSize() > SharedVariables.CAMPAIGN_TOTAL_MAPS)
                     {
                     	btnAdd.setEnabled(false);
                     	DialogHelper.showBasicDialog("The Total Number of Maps you can have is 10");
                     }
                	 else
                	 {
                     	addedMapsListModel.addElement(savedMapsList.getSelectedValue());
                	 }
                }
            }
        });
        btnAdd.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(btnAdd);

        // Button for Removing Maps from Campaign
        JButton btnRemove = new JButton("-");
        btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnRemove.setEnabled(false);
        btnRemove.setBounds(242, 205, 65, 23);
        btnRemove.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = addedMapsList.getSelectedIndex();
                addedMapsListModel.remove(index);
                
                if(addedMapsListModel.getSize() <= SharedVariables.CAMPAIGN_TOTAL_MAPS)
                {
                	btnAdd.setEnabled(true);
                }
                
                if (index > 0)
                    addedMapsList.setSelectedIndex(index - 1);
            }
        });
        btnRemove.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(btnRemove);

        // Campaign Name
        JLabel lblCampaignname = new JLabel(campaignName);
        if (campaignObject != null)
            lblCampaignname.setText(campaignObject.getCampaignName());

        lblCampaignname.setBounds(243, 25, 167, 14);
        getContentPane().add(lblCampaignname);

        // Save current setting to camapaign
        JButton btnDone = new JButton("Done");
        btnDone.setBackground(Color.LIGHT_GRAY);
        btnDone.setBounds(455, 387, 89, 23);
        getContentPane().add(btnDone);

        btnDone.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addedMaps = new ArrayList<String>();

                ListModel<String> model = addedMapsList.getModel();
                String addedMapsString;

                if (model.getSize() != 0)
                {
                    for (int i = 0; i < model.getSize(); i++)
                    {
                        Object o = model.getElementAt(i);
                        addedMapsString = o.toString();
                        addedMaps.add(i, addedMapsString);
                    }
                    CampaignJaxb.convertCampaignInfoToXml(new Campaign(campaignName, addedMaps));
                    dispose();
                }
                else
                {
                    DialogHelper.showBasicDialog("Please Select 1 Map to PLAY CAMPAIGN");
                }
            }
        });

        // Cancel campaign creation
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(Color.LIGHT_GRAY);
        btnCancel.setBounds(10, 385, 89, 23);
        getContentPane().add(btnCancel);

        btnCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                dispose();
            }
        });

        savedMapsList.addListSelectionListener(new ListSelectionListener()
        {

            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (((JList) e.getSource()).getSelectedValue() == null)
                    btnAdd.setEnabled(false);

                else
                    btnAdd.setEnabled(true);
            }
        });

        addedMapsList.addListSelectionListener(new ListSelectionListener()
        {
            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (((JList) e.getSource()).getSelectedValue() == null)
                    btnRemove.setEnabled(false);

                else
                    btnRemove.setEnabled(true);
            }
        });

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
