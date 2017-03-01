package tests.game.model.campaign;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import game.model.Campaign;
import game.model.jaxb.CampaignJaxb;

import org.junit.Before;
import org.junit.Test;

public class TestSaveFileOperations
{
    Campaign campaignObj;
    ArrayList<String> Maps = new ArrayList<String>();
    File f;
    String campaignName;
    String fName;

    @Before
    public void initiateVars() throws IOException
    {
        Maps.add("Map 1");
        Maps.add("Map 2");
        Maps.add("Map 3");
        Maps.add("Map 4");
        Maps.add("Map 5");
        campaignName = "Campaign1Test";
        campaignObj = new Campaign("Campaign1Test", Maps);
    }

    @Test
    public void testSaveXmlFile()
    {
        CampaignJaxb.convertCampaignInfoToXml(campaignObj);
        f = new File("../Campaigns/Campaign1Test.xml");
        fName = f.getName();
        assertEquals(fName, campaignName + ".xml");

    }
}
