package tests.game.model;


import static org.junit.Assert.*;
import game.model.Campaign;
import game.model.jaxb.CampaignJaxb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestDeleteFileOperations
{
	Campaign campaignObj;
	ArrayList<String> Maps = new ArrayList<String>();
	File f;
	String campaignName;
	String fName;
	
	@Before
	public void initiateVars() throws IOException
	{	
		campaignName="Campaign1Test";
		campaignObj=new Campaign("Campaign1Test",Maps);
	
	}
	
	@Test
	public void testSaveXmlFile() throws FileNotFoundException
	{	
		CampaignJaxb.deleteCampaignXml(campaignName);
		f=new File("../Campaigns/Campaign1Test.xml");
		if(!f.exists())
		{
			assertTrue("File not Found",true);
		}
	}
}
