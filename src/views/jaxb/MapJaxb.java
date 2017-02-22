package views.jaxb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import components.SharedVariables;
import model.Map;

public class MapJaxb {

  public static final void convertMapObjectToXml(Map map){
         
      try {
          Marshaller marshaller = JAXBContext.newInstance(Map.class).createMarshaller();
          marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); //This lets format type to XML
                                
          File file = new File(SharedVariables.MapsDirectory + File.separator + map.getMapName() + ".xml");
          if(!file.exists()){
            Path pathToFile = Paths.get(SharedVariables.MapsDirectory + File.separator + map.getMapName() + ".xml");
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
          }
                  
          marshaller.marshal(map, file);
      } 
      
      catch (JAXBException jaxbException) {
        jaxbException.printStackTrace();
      } catch (IOException e) {        
        e.printStackTrace();
      }  
  }
  
  public static final boolean deleteMapXml(String mapName){
    
    try{
      File file = new File(SharedVariables.MapsDirectory + File.separator + mapName + ".xml");
      return file.delete();  
    }
    
    catch(Exception ignored){
      return false;
    }
    
  }

  public static Map getMapFromXml(String mapName) {
      
    try {      
        File mapFile = new File(SharedVariables.MapsDirectory + File.separator + mapName + ".xml");
        
        if(!mapFile.exists())
          return null;
             
        Unmarshaller unmarshaller = JAXBContext.newInstance(Map.class).createUnmarshaller();
        return (Map) unmarshaller.unmarshal(mapFile);
        
      } 
      
      catch (JAXBException e) {
        return null;
      }

  }
}
