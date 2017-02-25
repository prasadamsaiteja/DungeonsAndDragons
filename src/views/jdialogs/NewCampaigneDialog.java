 package views.jdialogs;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.MatteBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
/**
 * The class that sets the name for the campaign
 * @author RahulReddy
 *
 */
public class NewCampaigneDialog extends JDialog{

	public NewCampaigneDialog(){
		
		DialogHelper.setDialogProperties(this, "Create new campaigne", new Rectangle(100, 100, 650, 400));
		getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBackground(new Color(0, 250, 154));
		btnStart.setBounds(535, 328, 89, 23);
		getContentPane().add(btnStart);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) null));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 614, 308);
		getContentPane().add(panel);
		
		JButton btnSelectPlayer = new JButton("Select player");
		
		JLabel lblPlayer = new JLabel("Player : ");
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblnotSelected = new JLabel("(not selected)");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblPlayer)
							.addGap(18)
							.addComponent(lblnotSelected, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnSelectPlayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 587, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPlayer, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblnotSelected)
						.addComponent(btnSelectPlayer))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(255, 0, 0));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(10, 328, 89, 23);
		getContentPane().add(btnCancel);
	}

}