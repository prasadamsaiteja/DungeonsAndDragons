package game.model.jaxb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import game.components.SharedVariables;
import game.model.Item;

/**
 * This class helps to handle item object to xml operations
 * 
 * @author saiteja prasdam
 * @version 1.0.0
 * @since 2/21/2017
 */
public class ItemJaxb
{

    /**
     * This method converts map object to xml
     * 
     * @param item Item object
     */
    public static void convertItemObjectToXml(Item item)
    {

        try
        {
            Marshaller marshaller = JAXBContext.newInstance(Item.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // This
                                                                                    // lets
                                                                                    // format
                                                                                    // type
                                                                                    // to
                                                                                    // XML

            File file = new File(SharedVariables.ItemsDirectory + File.separator + item.itemName + ".xml");
            if (!file.exists())
            {
                Path pathToFile = Paths.get(SharedVariables.ItemsDirectory + File.separator + item.itemName + ".xml");
                Files.createDirectories(pathToFile.getParent());
                Files.createFile(pathToFile);
            }

            marshaller.marshal(item, file);
        }

        catch (JAXBException jaxbException)
        {
            jaxbException.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Delete item xml file
     * 
     * @param itemName name of the item
     * @return return if the file is deleted or not
     */
    public static final boolean deleteItemXml(String itemName)
    {

        try
        {
            File file = new File(SharedVariables.ItemsDirectory + File.separator + itemName + ".xml");
            file.delete();
            return true;
        }

        catch (Exception ignored)
        {
            return false;
        }

    }

    /**
     * Get Item object from xml file
     * 
     * @param itemName name of the item
     * @return Returns the Item object from xml
     */
    public static Item getItemFromXml(String itemName)
    {

        try
        {
            File itemFile = new File(SharedVariables.ItemsDirectory + File.separator + itemName + ".xml");

            if (!itemFile.exists())
                return null;

            Unmarshaller unmarshaller = JAXBContext.newInstance(Item.class).createUnmarshaller();
            return (Item) unmarshaller.unmarshal(itemFile);

        }

        catch (JAXBException e)
        {
            return null;
        }

    }
}
