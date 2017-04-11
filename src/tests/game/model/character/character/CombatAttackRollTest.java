package tests.game.model.character.character;

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
