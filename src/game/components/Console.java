package game.components;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game.views.jpanels.GamePlayScreen;

/**
 * This class contains common methods used for displaying data to console and
 * keeping log entries.
 * 
 * @author saiteja prasadam
 * @version 1.0
 * @since 2/2/2017
 */
public class Console {
    
    public static JTextArea console;
    public static JScrollPane consoleScrollPane;

    /**
     * Print the message to the console.
     * 
     * @param message to be displayed.
     */
    public static void printInConsole(String message)
    {
        System.out.println(message);
        if(GamePlayScreen.console != null && GamePlayScreen.consoleScrollPane != null){
            GamePlayScreen.console.append("\n");
            GamePlayScreen.console.append(message);  
        }
    }

    /**
     * Print the message to the console and adds to log if boolean is true.
     * 
     * @param message to be displayed.
     * @param enterIntoLog Boolean value stating whether to add to the log file.
     */
    public static void printInConsole(String message, Boolean enterIntoLog)
    {
        printInConsole(message);
    }

}
