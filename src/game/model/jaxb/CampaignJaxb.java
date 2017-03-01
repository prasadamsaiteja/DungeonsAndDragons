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
import game.model.Campaign;

/**
 * This Class converts the Campaign object to  XML
 * @author RahulReddy
 * @version 1.0
 * @since 2/20/2017
 */
public class CampaignJaxb
{
	
/**
 * This Method converts the campaign object to XML and stores in Campaign Folder
 * @param campaign Campaign Object
 */
    public static final void convertCampaignInfoToXml(Campaign campaign)
    {
        try
        {
            Marshaller marshaller = JAXBContext.newInstance(Campaign.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // This lets format type to XML

            File file = new File(SharedVariables.CampaignsDirectory + File.separator + campaign.getCampaignName() + ".xml");
            if (!file.exists())
            {
                Path pathToFile = Paths.get(SharedVariables.CampaignsDirectory + File.separator + campaign.getCampaignName() + ".xml");
                Files.createDirectories(pathToFile.getParent());
                Files.createFile(pathToFile);
            }

            marshaller.marshal(campaign, file);
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
     * This Method deletes the campaign from the Campaign list and the XML file
     * @param campaignName Name of the Campaign
     * @return boolean whether the campaign is deleted or not
     */ 
    public static final boolean deleteCampaignXml(String campaignName)
    {

        try
        {
            File file = new File(SharedVariables.CampaignsDirectory + File.separator + campaignName + ".xml");
            return file.delete();
        }

        catch (Exception ignored)
        {
            return false;
        }

    }
    
    /**
     * This method loads the campaign using XML file 
     * @param campaignName Name of the Campaign
     * @return campaign Object
     */
    public static Campaign getCampaignFromXml(String campaignName)
    {

        try
        {
            File campaignFile = new File(SharedVariables.CampaignsDirectory + File.separator + campaignName + ".xml");

            if (!campaignFile.exists())
                return null;

            Unmarshaller unmarshaller = JAXBContext.newInstance(Campaign.class).createUnmarshaller();
            return (Campaign) unmarshaller.unmarshal(campaignFile);

        }
        
        catch (JAXBException e)
        {
            return null;
        }
    }

}
