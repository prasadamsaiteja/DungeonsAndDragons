package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.game.components.dice.RollTest;
import tests.game.model.map.MapTest;
import tests.game.model.campaign.TestCampaignName;
import tests.game.model.campaign.TestDeleteFileOperations;
import tests.game.model.campaign.TestSaveFileOperations;
import tests.game.model.character.character.ArmorClassTest;
import tests.game.model.character.character.DrawTest;
import tests.game.model.item.DeleteFileTest;
import tests.game.model.item.FileExistsTest;
import tests.game.model.item.ClassIntegrityTest;

/**
 * This class runs all the test cases classes.
 * @author supreet (s_supree)
 */
@RunWith(Suite.class)

@SuiteClasses(
{ DrawTest.class, ArmorClassTest.class, RollTest.class, MapTest.class, TestCampaignName.class,
  TestSaveFileOperations.class, TestDeleteFileOperations.class, DeleteFileTest.class, FileExistsTest.class,
  ClassIntegrityTest.class })

public class GameTestRunner
{

}
