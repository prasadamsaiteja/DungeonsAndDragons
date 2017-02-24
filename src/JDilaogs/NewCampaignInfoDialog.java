package JDilaogs;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GameComponents.ExtensionMethods;

import java.awt.SystemColor;

/**
 * 
 * @author RahulReddy
 *
 */
public class NewCampaignInfoDialog extends JDialog {
	JList savedMapsList;
	private String campaignName;
	DefaultListModel<String> addMaps_listModel;
	
	public NewCampaignInfoDialog(String nameValue) {
		DialogHelper.setDialogProperties(NewCampaignInfoDialog.this,"Campaign Zone", new Rectangle(554, 448));
		this.campaignName=nameValue;
		initComponents();
	}

	/**
	 * This Method Initializes the Components in the Current Dialog
	 */
	public void initComponents() {

		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);

		// This Panel displays saved Maps in the MAP directory
		{
			JPanel savedMapsPanel = new JPanel();
			savedMapsPanel.setLayout(new BorderLayout());
			savedMapsPanel.setBounds(36, 56, 197, 320);
				
			//Model
			DefaultListModel<String> savedMaps_listModel = new DefaultListModel<String>();
			String[] mapsList = ExtensionMethods.getMapsList();
			
			for (String mapName : mapsList)
				savedMaps_listModel.addElement(mapName);

			JList savedMapsList = new JList(savedMaps_listModel);			
			
			//Adding Scroll Bar
			JScrollPane listScroller = new JScrollPane();
            listScroller.setViewportView(savedMapsList);
            savedMapsPanel.add(listScroller, BorderLayout.CENTER);
            
            setListProperties(savedMapsList);
			savedMapsList.addListSelectionListener(new ListSelectionListener() {
	
				@Override
				public void valueChanged(ListSelectionEvent e) {
					int i=savedMapsList.getSelectedIndex();
					String clicked_Map_Jlist=(String)savedMapsList.getSelectedValue();
					System.out.println("The Value of Clicked Map :"+ clicked_Map_Jlist);
					System.out.println("Selected Index is : " +i);
					
				}
			});
			getContentPane().add(savedMapsPanel);

		}
		// This Panel displays Added Maps into the particular CAMPAIGN directory
		{
			JPanel addedMapsPanel = new JPanel();
			addedMapsPanel.setLayout(new BorderLayout());
			addedMapsPanel.setBackground(SystemColor.menu);
			addedMapsPanel.setBounds(317, 56, 197, 320);

			addMaps_listModel=new DefaultListModel<String>();
			JList<String> addedMapsList = new JList<String>(addMaps_listModel);
			
			//Adding Scroll Bar	
			JScrollPane listScroller2 = new JScrollPane();
			listScroller2.setViewportView(addedMapsList);
			addedMapsPanel.add(listScroller2, BorderLayout.CENTER);
			
			setListProperties(addedMapsList);
			getContentPane().add(addedMapsPanel);
		}
		
		//Button for Adding Maps to Campaign
		JButton btnAdd = new JButton("+");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//Listener Here
		btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

				}
		});
		btnAdd.setBounds(243, 145, 64, 23);
		getContentPane().add(btnAdd);

		//Button for Removing Maps from Campaign
		JButton btnRemove = new JButton("-");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRemove.setBounds(242, 205, 65, 23);
		
		//Listener Here
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		getContentPane().add(btnRemove);

		JLabel lblCampaignname = new JLabel(campaignName);
		lblCampaignname.setBounds(236, 24, 143, 14);
		getContentPane().add(lblCampaignname);

		JButton btnDone = new JButton("Done");
		btnDone.setBackground(Color.GREEN);
		btnDone.setBounds(425, 403, 89, 23);
		getContentPane().add(btnDone);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(Color.RED);
		btnCancel.setBounds(36, 403, 89, 23);
		getContentPane().add(btnCancel);
	}

	public void setListProperties(JList List) {
		// TODO Auto-generated method stub
		List.setLayoutOrientation(JList.VERTICAL);
		List.setBackground(SystemColor.menu);
		List.setFont(new Font("Tahoma", Font.BOLD, 12));
		List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}



	
}
