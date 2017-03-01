package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.game.components.dice.RollTest;
import tests.game.model.character.character.ArmorClassTest;
import tests.game.model.character.character.DrawTest;

@RunWith(Suite.class)
@SuiteClasses(
{ DrawTest.class, ArmorClassTest.class, RollTest.class })

/**
 * @author supreet (s_supree)
 */
public class GameTestRunner
{

}
