package tests.game.model.character.character;

import static org.junit.Assert.*;
import game.model.character.builderPattern.Fighter;
import game.model.character.builderPattern.FighterDirector;
import game.model.character.builderPattern.FighterDriverClass;

import org.junit.Before;
import org.junit.Test;

public class AbilityScoresTest {
	
	@Test
	public void testBullyAbilityScores() {
		
		Fighter fighter = new FighterDriverClass("Bully").getFighter();
		int strength = fighter.getConstitution();
		int constitution = fighter.getConstitution();
		int dexterity = fighter.getDexterity();
		
		assertTrue(strength >= constitution);		
		assertTrue(constitution >= dexterity);		
	}
	
	@Test
	public void testNimbleAbilityScores() {
		
		Fighter fighter = new FighterDriverClass("Nimble").getFighter();
		int strength = fighter.getConstitution();
		int constitution = fighter.getConstitution();
		int dexterity = fighter.getDexterity();
		
		assertTrue(dexterity >= constitution);		
		assertTrue(constitution >= strength);		
	}
	
	@Test
	public void testTankAbilityScores() {
		
		Fighter fighter = new FighterDriverClass("Tank").getFighter();
		int strength = fighter.getConstitution();
		int constitution = fighter.getConstitution();
		int dexterity = fighter.getDexterity();
		
		System.out.println(constitution + " " + dexterity + " " + strength);
		assertTrue(constitution >= dexterity);		
		assertTrue(dexterity >= strength);
	}

}
