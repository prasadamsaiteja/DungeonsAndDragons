 package JDilaogs;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.MatteBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import GameComponents.Console;
import javax.swing.JList;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class NewCampaigneDialog extends JDialog{
	JTextField campaign_name_tf;
	CreateStuffDialog csd;
	public NewCampaigneDialog(){
		
		DialogHelper.setDialogProperties(this, "Create new campaigne", new Rectangle(100, 100, 650, 400));
		getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBackground(new Color(0, 250, 154));
		btnStart.setBounds(535, 328, 89, 23);
		getContentPane().add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				/**
				 * 	csd.campaign_listModel.addElement(user_campaign_name);
					Console.printInConsole("I am in Action Listener after adding string to list");
					for(int i = 0; i < csd.campaign_listModel.getSize(); i++)
					{
			    	Console.printInConsole("I am in Loop" + count + "" +csd.campaign_listModel.getSize());
					Console.printInConsole("here" + csd.campaign_listModel.getElementAt(i));
					count++;
					}
				 */
				int count=0;
				String user_campaign_name=getCampaignName();
				if(user_campaign_name.equals(""))
				{
					Console.printInConsole("I have no Text");
				}
				else{
					Console.printInConsole("Yeah !!! I have  Text");
					System.out.println(user_campaign_name);
					
				}
				new NewMapDialog(NewCampaigneDialog.this);
			}
		});
		
		
		JPanel informationPanel = new JPanel();
		informationPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		informationPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		informationPanel.setBackground(Color.WHITE);
		informationPanel.setBounds(10, 11, 614, 308);
		getContentPane().add(informationPanel);
		
		campaign_name_tf = new JTextField("");
		JLabel lblPlayer = new JLabel("Campaign Name : ");
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		//JLabel lblnotSelected = new JLabel("(not selected)");
		
		JPanel addedMaps = new JPanel();
		addedMaps.setBackground(SystemColor.menu);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnNewButton_1 = new JButton("REMOVE");
		
		JList list = new JList();
		
		JPanel savedMaps = new JPanel();
		GroupLayout gl_informationPanel = new GroupLayout(informationPanel);
		gl_informationPanel.setHorizontalGroup(
			gl_informationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_informationPanel.createSequentialGroup()
					.addGroup(gl_informationPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_informationPanel.createSequentialGroup()
							.addComponent(lblPlayer)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(campaign_name_tf, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE))
						.addGroup(gl_informationPanel.createSequentialGroup()
							.addComponent(addedMaps, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
							.addGap(54)
							.addGroup(gl_informationPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
								.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
							.addGap(33)
							.addComponent(savedMaps, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_informationPanel.setVerticalGroup(
			gl_informationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_informationPanel.createSequentialGroup()
					.addGroup(gl_informationPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPlayer, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(campaign_name_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_informationPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_informationPanel.createSequentialGroup()
							.addGap(26)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
							.addGap(76)
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton_1))
						.addGroup(gl_informationPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_informationPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(savedMaps, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(addedMaps, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JList savedMapsList = new JList();
		savedMaps.add(savedMapsList);
		
		JList addedMapsList = new JList();
		addedMaps.add(addedMapsList);
		informationPanel.setLayout(gl_informationPanel);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(255, 0, 0));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(10, 328, 89, 23);
		getContentPane().add(btnCancel);
	}

	public String getCampaignName(){
		return campaign_name_tf.getText();

	}
}