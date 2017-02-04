package JFrames;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import JPanels.LaunchScreen;

/**
 * This class create main JFrame in which JPanels are placed.
 * 
 * @author saiteja prasadam
 * @since 1/26/2017
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    /**
     * Initializes the JFrame. sets icon and sets size to maximized.
     */
	public MainFrame() {

	    setIconImage(Toolkit.getDefaultToolkit().getImage("app_icon.png"));
		setUndecorated(true);
		getContentPane().add(new LaunchScreen());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}

	/**
	 * This method replaces the current JPanel with passed JPanel.
	 * @param jpanel   This JPanel will be replaced by the current JPanel.
	 */
	public void replaceJPanel(JPanel jpanel){
	  
	    getContentPane().removeAll();
	    getContentPane().repaint();
	    getContentPane().add(jpanel);
	    setVisible(true);
	}

}