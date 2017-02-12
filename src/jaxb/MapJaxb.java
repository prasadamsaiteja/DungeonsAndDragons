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
import ModelClasses.Map;

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
  
}
