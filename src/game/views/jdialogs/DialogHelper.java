package game.views.jdialogs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Dialog helper contains static methods which are helpful for JDialogs.
 *
 * @author Saiteja prasdam
 * @version 1.0
 * @since 1/29/2017
 */
public class DialogHelper
{

    /**
     * This method moves JDialog to the center of the screen. 
     * @param dialog is reference to JDialog.
     */
    private static void centerDialogToCenter(JDialog dialog)
    {

        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
    }

    /**
     * This method set some set of properties to JDilaog.
     * 
     * @param dialog this is reference to JDialog.
     * @param title this specifies the title to be set to JDialog.
     * @param rectangle this specifies size of the JDialog to be set.
     */
    public static void setDialogProperties(JDialog dialog, String title, Rectangle rectangle)
    {
        dialog.setTitle(title);
        dialog.setBounds(rectangle);
        DialogHelper.centerDialogToCenter(dialog);
        dialog.setVisible(true);
        dialog.setResizable(false);
        dialog.setIconImage(Toolkit.getDefaultToolkit()
                .getImage("C:\\Users\\saite\\Documents\\Eclipse\\Projects\\DungeonsAndDragons\\img\\app_icon.png"));
    }

    /**
     * This method shows a basic message with ok button.
     * 
     * @param message Message to be displayed to the user.
     */
    public static void showBasicDialog(String message)
    {
        JOptionPane.showMessageDialog(null, message);
    }
}
