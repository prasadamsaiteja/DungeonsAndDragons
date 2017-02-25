package model.jaxb;

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
import model.Campaign;
import model.Map;
/**
 * 
 * @author RahulReddy
 * @version 1.0
 * @since   2/20/2017
 */
public class CampaignJaxb {
	 public static final void convertCampaignInfoToXml(Campaign campaign){
         
	      try {
	          Marshaller marshaller = JAXBContext.newInstance(Campaign.class).createMarshaller();
	          marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); //This lets format type to XML
	                                
	          File file = new File(SharedVariables.CampaignsDirectory + File.separator + campaign.getCampaignName() + ".xml");
	          if(!file.exists()){
	            Path pathToFile = Paths.get(SharedVariables.CampaignsDirectory + File.separator + campaign.getCampaignName() + ".xml");
	            Files.createDirectories(pathToFile.getParent());
	            Files.createFile(pathToFile);
	          }
	                  
	          marshaller.marshal(campaign, file);
	      } 
	      
	      catch (JAXBException jaxbException) {
	        jaxbException.printStackTrace();
	      } catch (IOException e) {        
	        e.printStackTrace();
	      }  
	  }
	  public static final boolean deleteCampaignXml(String campaignName){
		    
		    try{
		      File file = new File(SharedVariables.CampaignsDirectory + File.separator + campaignName + ".xml");
		      return file.delete();  
		    }
		    
		    catch(Exception ignored){
		      return false;
		    }
		    
		  }
	  public static Campaign getCampaignFromXml(String campaignName) {
	      
		    try {      
		        File campaignFile = new File(SharedVariables.CampaignsDirectory + File.separator + campaignName + ".xml");
		        
		        if(!campaignFile.exists())
		          return null;
		             
		        Unmarshaller unmarshaller = JAXBContext.newInstance(Campaign.class).createUnmarshaller();
		        return (Campaign) unmarshaller.unmarshal(campaignFile);
		        
		      } 
		      
		      catch (JAXBException e) {
		        return null;
		      }

		  }

	
}
