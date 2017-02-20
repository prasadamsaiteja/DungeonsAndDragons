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

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;

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
	public NewCampaignInfoDialog(String nameValue) {
		DialogHelper.setDialogProperties(NewCampaignInfoDialog.this,"Campaign Zone", new Rectangle(554, 448));
		this.campaignName=nameValue;
		initComponents();
	}

	

	/**
	 * This Method Initializes the Components in the Current Dialog
	 */
	public void initComponents() {
		// TODO Auto-generated method stub
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);

		// This Panel displays saved Maps in the MAP directory
		{
			JPanel addMapsPanel = new JPanel();
			addMapsPanel.setLayout(new BorderLayout());
			addMapsPanel.setBounds(36, 56, 197, 320);
			getContentPane().add(addMapsPanel);

			DefaultListModel<String> listModel = new DefaultListModel<String>();
			String[] mapsList = ExtensionMethods.getMapsList();

			for (String mapName : mapsList)
				listModel.addElement(mapName);

			JList addedMapsList = new JList(listModel);
			addedMapsList.setBackground(SystemColor.menu);
			addMapsPanel.add(addedMapsList);
			addedMapsList.setFont(new Font("Tahoma", Font.BOLD, 12));
			addMapsPanel.add(addedMapsList, BorderLayout.CENTER);

		}
		// This Panel displays Added Maps into the particular CAMPAIGN directory
		{
			JPanel savedMapsPanel = new JPanel();
			savedMapsPanel.setBackground(SystemColor.menu);
			savedMapsPanel.setBounds(317, 56, 197, 320);
			getContentPane().add(savedMapsPanel);

			JList savedMapsList = new JList();
			savedMapsPanel.add(savedMapsList);
			getContentPane().add(savedMapsPanel);
		}

		JButton btnAdd = new JButton("+");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//Listener Here
		btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
		});
		btnAdd.setBounds(243, 145, 64, 23);
		getContentPane().add(btnAdd);

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
}
