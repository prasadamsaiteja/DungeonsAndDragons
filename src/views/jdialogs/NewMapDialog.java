package views.jdialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.GameLauncher;
import views.jpanels.MapDesigner;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class NewMapDialog extends JDialog {

    private JDialog parent;
  
	public NewMapDialog(JDialog jdialog) {

	    this.parent = jdialog;
		DialogHelper.setDialogProperties(this, "New map", new Rectangle(100, 100, 250, 150));
		initComponents();
	}

	private void initComponents() {
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTextField map_name_text_field = new JTextField();
		JTextField map_dimesions_y = new JTextField();
		JTextField map_dimesions_x = new JTextField();
		
		{ 	//Map name label
			JLabel map_name_label = new JLabel("Map name");
			map_name_label.setBounds(5, 5, 66, 34);
			contentPanel.add(map_name_label);
		}

		{ 	//Map name text field			
			map_name_text_field.setBounds(71, 11, 153, 23);
			contentPanel.add(map_name_text_field);
			map_name_text_field.setColumns(10);
		}

		{ 	//Map dimension label
			JLabel lblNewLabel_1 = new JLabel("Map dimensions");
			lblNewLabel_1.setBounds(5, 39, 83, 34);
			contentPanel.add(lblNewLabel_1);
		}

		{ 	//Map dimension text field width			
			map_dimesions_x.setBounds(110, 45, 32, 23);
			map_dimesions_x.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(map_dimesions_x);
			map_dimesions_x.setColumns(10);
		}

		{ 	//Map dimension text field height			
			map_dimesions_y.setColumns(10);
			map_dimesions_y.setHorizontalAlignment(SwingConstants.CENTER);
			map_dimesions_y.setBounds(179, 45, 32, 23);
			contentPanel.add(map_dimesions_y);
		}

		{	//X label and next button
			JLabel x = new JLabel("x");
			x.setBounds(156, 45, 15, 20);
			contentPanel.add(x);
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("Next");
					okButton.addActionListener(new ActionListener() {
					  @Override
					public void actionPerformed(ActionEvent e) {
					      if(parent != null)
					          parent.dispose();
					      dispose();		
					      GameLauncher.mainFrameObject.replaceJPanel(new MapDesigner(map_name_text_field.getText(), Integer.parseInt(map_dimesions_x.getText()), Integer.parseInt(map_dimesions_y.getText())));
					  }
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
			}
		}
	}

}