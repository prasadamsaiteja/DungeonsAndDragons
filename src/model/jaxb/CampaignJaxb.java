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

/**
 * Class that displays the information of Campaign that shows the list of maps in the Data folder and 
 * User selected maps for a specific campaign
 * @author RahulReddy
 * @version 1.0
 * @since   2/20/2017
 */
public class CampaignJaxb {
	 
	/**
	 * This Method converts the Campaign Object information like Maps into XML
	 * @param campaign It has properties to be saved in XML file
	 */
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
	 /**
	  * Removes Campaign from the List and delete the User selected Campaign
	  * @param campaignName Name of the user selected campaign
	  * @return boolean whether Campaign is deleted or not
	  */
    public static final boolean deleteCampaignXml(String campaignName){
      
        try{
          File file = new File(SharedVariables.CampaignsDirectory + File.separator + campaignName + ".xml");
          return file.delete();  
        }
        
        catch(Exception ignored){
          return false;
        }
      
    }
    
	  /**
	   * This method loads the XML file of the selected Campaign from the Campaigns folder
	   * @param campaignName Name of the user selected campaign
	   * @return Campaign  The campaign object
	   */
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
