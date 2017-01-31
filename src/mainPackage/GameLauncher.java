package mainPackage;

import javax.swing.UIManager;

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

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	
		}
		
		catch(Exception ex){}
		
		mainFrameObject = new MainFrame();
	}

}