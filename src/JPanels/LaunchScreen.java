package JPanels;

import javax.swing.JPanel;

import JDilaogs.CreateStuffDialog;
import JDilaogs.NewCampaigneDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

/**
 * This class creates JPanel for the launch screen.
 * 
 * @author saiteja prasadam
 * @since 1/26/2017
 * @version 1.0
 */
@SuppressWarnings("serial")
public class LaunchScreen extends JPanel {

    /**
     * This class creates JPanel for the launch screen.
     */
	public LaunchScreen() {
		
		JButton btnNewCampaign = new JButton("New campaign");
		JButton btnCreateStuff = new JButton("Create stuff");
		JButton btnExit = new JButton("Exit game");

		initComponents(btnNewCampaign, btnCreateStuff, btnExit);
		buttonsOnclickListeners(btnNewCampaign, btnCreateStuff, btnExit);
	}

	/**
	 * This method initialize the UI components.
	 * 
     * @param btnNewCampaign   This contains the reference to new campaign button.
     * @param btnCreateStuff   This contains the reference to create stuff button.
     * @param exitButton       This contains the reference to exit button.
	 */
	private void initComponents(JButton btnNewCampaign, JButton btnCreateStuff, JButton btnExit) {
	
	    GridBagLayout gridBagLayout = new GridBagLayout();
	  
		{	//Grid bag layout
			gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);
		}
		
		Dimension btnDim = new Dimension(120, 120);
		Font btnFont = new Font("Tahoma", Font.BOLD, 11);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		
		{	//New game button			
			btnNewCampaign.setPreferredSize(btnDim);
			btnNewCampaign.setFont(btnFont);
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(btnNewCampaign, gbc);
		}
		
		{	//Load game button			
			btnCreateStuff.setPreferredSize(btnDim);
			btnCreateStuff.setFont(btnFont);
			gbc.gridx = 5;
			gbc.gridy = 2;
			add(btnCreateStuff, gbc);
		}
		
		{	//Exit game button			
			btnExit.setPreferredSize(btnDim);
			btnExit.setFont(btnFont);
			gbc.gridx = 5;
			gbc.gridy = 1;
			add(btnExit, gbc);
		}
	}
	
	/**
	 * This method sets onClick listeners for three buttons
	 * @param btnNewCampaign   This contains the reference to new campaign button.
	 * @param btnCreateStuff   This contains the reference to create stuff button.
	 * @param exitButton       This contains the reference to exit button.
	 */
	private void buttonsOnclickListeners(JButton btnNewCampaign, JButton btnCreateStuff, JButton exitButton) {
		
		btnNewCampaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NewCampaigneDialog();
			}
		});
		
	     btnCreateStuff.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	           new CreateStuffDialog();
	         }
	     });
		
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * This method paints the background to black color.
	 */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);     
        g2d.fillRect(0, 0, getSize().width, getSize().height);
    }

}