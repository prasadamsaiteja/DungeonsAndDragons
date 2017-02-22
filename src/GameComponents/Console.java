package GameComponents;

/**
 * This class contains common methods used for displaying data to console and keeping log enteries.
 * 
 * @author  saiteja prasadam
 * @version 1.0
 * @since 2/2/2017
 */
public class Console {
  
    /**
     * Print the message to the console.
     * @param message to be displayed.
     */
    public static void printInConsole(String message){
        System.out.println(message);
    }
    
    /**
     * Print the message to the console and adds to log if boolean is true.
     * @param message to be displayed.
     * @param enterIntoLog Boolean value stating whether to add to the log file.
     */
    public static void printInConsole(String message, Boolean enterIntoLog){
        printInConsole(message);
        //Enter log
    }

}