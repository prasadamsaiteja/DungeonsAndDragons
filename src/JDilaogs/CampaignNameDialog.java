package JDilaogs;

import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JPanel;

import java.awt.SystemColor;

import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.JButton;

import GameComponents.ExtensionMethods;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * 
 * @author RahulReddy
 *
 */
public class CampaignNameDialog extends JDialog {
	private JTextField campaignName_TextField;
	private JTextField campaignNameValue;
	private DefaultListModel<String> campaignName_ListModel;
	
	public CampaignNameDialog() {
		// TODO Auto-generated constructor stub
		DialogHelper.setDialogProperties(this,"Campaign Name", new Rectangle(440,150));
		getContentPane().setLayout(null);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBounds(0, 0, 434, 261);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblCampaignName = new JLabel("Enter Campaign Name :");
		lblCampaignName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCampaignName.setBounds(10, 25, 173, 21);
		contentPanel.add(lblCampaignName);
		
		campaignNameValue = new JTextField();
		campaignNameValue.setBackground(SystemColor.menu);
		campaignNameValue.setBounds(193, 21, 218, 31);
		contentPanel.add(campaignNameValue);
		campaignNameValue.setColumns(10);
		
		JButton btn_addName = new JButton("OK");
		btn_addName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Opens a Dialog with a list of loaded Maps
				if(campaignNameValue!=null)
				{
					NewCampaignInfoDialog ncid=new NewCampaignInfoDialog(campaignNameValue.getText());
					System.out.println("Map Name typed is " + campaignNameValue.getText());
					(CampaignNameDialog.this).dispose();
					//addToCampaigns();
				}
				else{
					
				}	
			}
			
		});
		btn_addName.setBounds(158, 81, 89, 23);
		contentPanel.add(btn_addName);
	
	}
	/*public DefaultListModel<String> addToCampaigns() {
		// TODO Auto-generated method stub
		
		String[] campaignsList = ExtensionMethods.getCampaignsList();
        for(String campaignName : campaignsList)
        	campaignName_ListModel.addElement(campaignName);
        //System.out.println("The Campaign List has files " + campaignsList);
		return campaignName_ListModel;
	}
	*/
}
