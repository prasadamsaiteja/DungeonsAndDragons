
package game.views.jdialogs.campaignDialog;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import views.jdialogs.DialogHelper;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author RahulReddy
 *
 */
@SuppressWarnings("serial")
public class CampaignNameDialog extends JDialog {	
	
	public CampaignNameDialog() {
		DialogHelper.setDialogProperties(this,"Campaign Name", new Rectangle(440,150));
		getContentPane().setLayout(null);
	
		initComponents();
	}

	private void initComponents() {
		
		//Jpanel
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setBounds(0, 0, 434, 261);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		//Campaign name Jlabel
		JLabel lblCampaignName = new JLabel("Enter Campaign Name :");
		lblCampaignName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCampaignName.setBounds(10, 25, 173, 21);
		contentPanel.add(lblCampaignName);
		
		//Campaign Name JTextField
		JTextField campaignNameValue = new JTextField();
		campaignNameValue.setBackground(SystemColor.menu);
		campaignNameValue.setBounds(193, 21, 218, 31);
		contentPanel.add(campaignNameValue);
		campaignNameValue.setColumns(10);
		
		//Next JButton
		JButton btn_Next = new JButton("Next");
		btn_Next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Opens a Dialog with a list of loaded Maps
				if(campaignNameValue.getText().length() >= 4)
				{
					new NewCampaignInfoDialog(campaignNameValue.getText());
					dispose();					
				}
				else
					DialogHelper.showBasicDialog("Campaign name should be atleast 4 characters");				
			}
			
		});
		btn_Next.setBounds(158, 81, 89, 23);
		contentPanel.add(btn_Next);
	}

}
