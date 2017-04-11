package tests.game.model.character.character;

import static org.junit.Assert.*;
import game.components.GameMechanics;
import game.model.Campaign;
import game.model.character.CharactersList;
import game.model.character.Character;
import game.model.character.strategyPattern.strategies.HumanPlayer;
import game.views.jpanels.GamePlayScreen;

import org.junit.Before;
import org.junit.Test;

public class CombatAttackRollTest {

	private GamePlayScreen gps;
	
	private boolean attackRuleSatisfied ;
	
	@Before
	public void init(){			
		gps = new GamePlayScreen("TestingCamp", "saitej", true, true);
	}
	
	@Test
	public void testSuccessfulAttack() {			
	
	}
}
