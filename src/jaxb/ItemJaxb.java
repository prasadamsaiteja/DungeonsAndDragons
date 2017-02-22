package jaxb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import GameComponents.SharedVariables;
import ModelClasses.Item;
import ModelClasses.Map;

/**
 * This class helps to handle item object to xml operations
 * @author saiteja prasdam
 * @version 1.0.0
 * @since 2/21/2017
 */
public class ItemJaxb {

    /**
     * This method converts map object to xml
     * @param item  Item object
     */
    public static void convertItemObjectToXml(Item item) {
    
    	try {
            Marshaller marshaller = JAXBContext.newInstance(Item.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); //This lets format type to XML
                                  
            File file = new File(SharedVariables.ItemsDirectory + File.separator + item.itemName + ".xml");
            if(!file.exists()){
              Path pathToFile = Paths.get(SharedVariables.ItemsDirectory + File.separator + item.itemName + ".xml");
              Files.createDirectories(pathToFile.getParent());
              Files.createFile(pathToFile);
            }
                    
            marshaller.marshal(item, file);
        } 
        
        catch (JAXBException jaxbException) {
          jaxbException.printStackTrace();
        } catch (IOException e) {        
          e.printStackTrace();
        } 
    
    }

}
