package game.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import game.model.jaxb.MapJaxb;

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
    private ArrayList<String> mapNames;
    private ArrayList<Map> mapList;
    
    /**
     * Constructor that sets the name and maps of campaign to local variables.
     * 
     * @param campaign_Name name of campaign
     * @param addedMaps list of added maps in the campaign
     */
    public Campaign(String campaign_Name, ArrayList<String> addedMaps)
    {
        this.campaignName = campaign_Name;
        this.mapNames = addedMaps;
    }

    /**
     * Default constructor
     */
    public Campaign()
    {
    }

    /**
     * Method returns the name of campaign
     * 
     * @return campaignName
     */
    public String getCampaignName()
    {
        return campaignName;
    }

    /**
     * This method returns all the map names saved
     * @return arraylist of map names
     */
    public ArrayList<String> getMapNames(){
        return mapNames;
    }

    /**
     * This method fetches all the map object and store them to mapList
     */
    public void fetchMaps(){
      
        mapList = new ArrayList<>();
        for (String mapName : mapNames) {
            Map map = MapJaxb.getMapFromXml(mapName);
            if(map != null)
              mapList.add(map);
        }
    }

    /**
     * This method returns map object list
     * @return Arraylist of map objects
     */
    public ArrayList<Map> getMapList(){
        return mapList;
    }
}