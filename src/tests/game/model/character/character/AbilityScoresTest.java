package tests.game.model.character.character;

import static org.junit.Assert.*;
import game.model.character.builderPattern.Fighter;
import game.model.character.builderPattern.FighterDirector;
import game.model.character.builderPattern.FighterDriverClass;

import org.junit.Before;
import org.junit.Test;

public class AbilityScoresTest {

	 int strength, constitution, dexterity;
	 FighterDriverClass fighter;
	 Fighter f;
	 FighterDirector fd;
	 boolean comparision1,comparision2 ;

	@Before 
	public void before(){
		fighter = new FighterDriverClass("Bully");
		fd = fighter.getFighterDirector();
		f = fighter.getFighter();
		strength = f.getConstitution();
		constitution = f.getConstitution();
		dexterity = f.getDexterity();
	}
	
	@Test
	public void testAbilityScores() {
		if(strength >= constitution)
			comparision1 = true;
		
		if(constitution >= dexterity)
			comparision2 = true;
		
		assertTrue(comparision1);
		assertTrue(comparision2);
	}

}
