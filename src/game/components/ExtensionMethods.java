package game.components;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import game.model.character.CharactersList;

/**
 * This class contains essential methods required by game components
 * 
 * @author saiteja prasadam
 * @since 2/5/2017
 *
 */
public class ExtensionMethods
{

    /**
     * This method returns all the maps saved
     * 
     * @return List of maps names saved
     */
    public static String[] getMapsList()
    {

        if (!new File(SharedVariables.MapsDirectory).exists())
            return new String[0];

        File[] fileList = new File(SharedVariables.MapsDirectory).listFiles();
        String[] fileName = new String[fileList.length];

        for (int i = 0; i < fileList.length; i++)
            if (fileList[i].getName().endsWith(".xml"))
                fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");

        return fileName;
    }

    /**
     * This method returns all the items saved
     * 
     * @return List of items names saved
     */
    public static String[] getItemsList()
    {

        if (!new File(SharedVariables.ItemsDirectory).exists())
            return new String[0];

        File[] fileList = new File(SharedVariables.ItemsDirectory).listFiles();
        String[] fileName = new String[fileList.length];

        for (int i = 0; i < fileList.length; i++)
            if (fileList[i].getName().endsWith(".xml"))
                fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");

        return fileName;
    }

    /**
     * This method returns all the campaign saved
     * 
     * @return List of campaigns names saved
     */
    public static String[] getCampaignsList()
    {
        File f = new File(SharedVariables.CampaignsDirectory);
        if (!f.exists())
        {
            return new String[0];
        }
        File[] fileList = f.listFiles();
        String[] fileName = new String[fileList.length];
        for (int i = 0; i < fileList.length; i++)
        {
            if (fileList[i].getName().endsWith(".xml"))
                fileName[i] = fileList[i].getName().replaceFirst("[.][^.]+$", "");

        }
        return fileName;
    }

    /**
     * This method returns all the characters saved
     * @return List of characters names saved
     */
    public static String[] getCharacterList(){
        ArrayList<String> chars = CharactersList.getNames();
        return chars.toArray(new String[chars.size()]);
    }
    
    /**
     * This method plays a error sound, to let the user know something is wrong
     */
    public static void playErrorSound(){
      
        try{
          new Thread(new Runnable() {
            
            @Override
            public void run() {
              final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
              if (runnable != null) runnable.run();
            }
          }).start();
          
        }
        
        catch(Exception ignored){}
    }
}