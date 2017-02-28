package game;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import game.views.jframes.MainFrame;

/**
 * This class contains main method which starts the JFrame.
 * 
 * @author saiteja prasadam
 * @version 1.0
 */
public class GameLauncher
{

    public static MainFrame mainFrameObject;

    /**
     * This is the main method, application starts with this method.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {

        try
        {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.startsWith("mac os x"))
            {
                MetalLookAndFeel.setCurrentTheme(new OceanTheme());
                UIManager.setLookAndFeel(new MetalLookAndFeel());
            }

            else
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
               | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        mainFrameObject = new MainFrame();
    }

}
