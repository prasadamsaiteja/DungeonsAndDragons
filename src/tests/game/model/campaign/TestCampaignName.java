package tests.game.model.campaign;

import java.util.ArrayList;

import game.model.Campaign;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Tests the campaign Name
 * @author RahulReddy
 * @version 1.0.0
 */
public class TestCampaignName extends TestCase
{
    String name = "Demo.xml";
    ArrayList<String> maps;
    Campaign campapignName = new Campaign("Demo.xml", maps);

    /**
     * Method that Tests the campaign Name
     */
    @Test
    public void testCampaignName()
    {
        assertEquals(campapignName.getCampaignName(), name);
    }

}
