package mainPackage;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import JFrames.MainFrame;

/**
 * This class contains main method which starts the JFrame. 
 * 
 * @author saiteja prasadam
 * @version 1.0
 */
public class GameLauncher {
  
    public static MainFrame mainFrameObject; 

    /**
     * This is the main method, application starts with this method.
     * @param args Command line arguments.
     */
	public static void main(String[] args) {

		try {
			MetalLookAndFeel.setCurrentTheme(new OceanTheme());
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}	
		
		mainFrameObject = new MainFrame();
	}

}