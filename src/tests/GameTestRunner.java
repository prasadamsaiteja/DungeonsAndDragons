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
import tests.game.model.item.ItemTestCase1;
import tests.game.model.item.ItemTestCase2;
import tests.game.model.item.ItemTestCase3;

@RunWith(Suite.class)

@SuiteClasses({
    DrawTest.class,
    ArmorClassTest.class,
    RollTest.class,
    MapTest.class,
    TestCampaignName.class,
    TestSaveFileOperations.class,
    TestDeleteFileOperations.class,
    ItemTestCase1.class,
    ItemTestCase2.class,
    ItemTestCase3.class
})

/**
 * @author supreet (s_supree)
 */
public class GameTestRunner
{

}
