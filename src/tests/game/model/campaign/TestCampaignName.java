package tests.game.model.campaign;

import java.util.ArrayList;

import game.model.Campaign;
import junit.framework.TestCase;

import org.junit.Test;

public class TestCampaignName extends TestCase
{
	String name="Demo.xml";
	ArrayList<String> maps;
	Campaign campapignName=new Campaign("Demo.xml",maps);
	
	@Test
	public void testCampaignName()
	{
		assertEquals(campapignName.getCampaignName(),name);
	}

}
