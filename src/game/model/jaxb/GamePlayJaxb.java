package game.model.jaxb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import game.components.SharedVariables;
import game.views.jpanels.GamePlayScreen;

/**
 * This class helps to save and load game
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 3/30/2017
 *
 */
public class GamePlayJaxb {

    /**
     * This method converts saved game object to xml and save it to a file
     * @param savedGameTitle This is the title of saved game
     * @param gameplayObject This contains game play object to be saved
     * @return true if file saved successfully
     */
    public static final boolean convertGameObjectToXml(String savedGameTitle, GamePlayScreen gameplayObject)
    {

        try
        {
                                                                     
            File file = new File(SharedVariables.SavedGamesDirectory + File.separator + savedGameTitle + ".xml");
            if (!file.exists())
            {
                Path pathToFile = Paths.get(SharedVariables.SavedGamesDirectory + File.separator + savedGameTitle + ".xml");
                Files.createDirectories(pathToFile.getParent());
                Files.createFile(pathToFile);                               
            }
            
            XStream xstream = new XStream(new StaxDriver());
            String xml = xstream.toXML(gameplayObject);
            FileWriter out = new FileWriter(file.getAbsolutePath());
            out.write(xml);
            out.close();
            
            return true;
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
       
    }
    
    /**
     * This method get saved game object from xml
     * @param savedGameTitle title of the savedGameTitle
     * @return saved game object
     */
    public static GamePlayScreen getGamePlayObjectFromXml(String savedGameTitle)
    {
        File savedGameFile = new File(SharedVariables.SavedGamesDirectory + File.separator + savedGameTitle + ".xml");

        if (!savedGameFile.exists())
            return null;

        XStream xstream = new XStream(new StaxDriver());
        return (GamePlayScreen) xstream.fromXML(savedGameFile);
    }
}
