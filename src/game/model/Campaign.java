package game.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Campaign")

/**
 * Model Class for Campaign
 * 
 * @author RahulReddy
 * @version 1.0
 * @since 2/20/2017
 */
public class Campaign
{

    @XmlElement(name = "Campaign_Name")
    private String campaignName;

    @XmlElement(name = "Loaded_Maps")
    public ArrayList<String> maps;

    /**
     * Constructor that sets the name and maps of campaign to local variables.
     * @param campaign_Name name of campaign
     * @param addedMaps list of added maps in the campaign
     */
    public Campaign(String campaign_Name, ArrayList<String> addedMaps)
    {
        this.campaignName = campaign_Name;
        this.maps = addedMaps;
    }

    /**
     * Default constructor
     */
    public Campaign()
    {
    }
    /**
     * Method returns the name of campaign
     * @return campaignName 
     */
    public String getCampaignName()
    {
        return campaignName;
    }

}
