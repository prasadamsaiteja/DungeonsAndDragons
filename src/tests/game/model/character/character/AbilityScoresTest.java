package tests.game.model.character.character;

import static org.junit.Assert.*;
import game.model.character.builderPattern.Fighter;
import game.model.character.builderPattern.FighterDriverClass;

import org.junit.Test;
/**
 * This class is for testing the ablility scores
 * @author RahulReddy
 * @version 1.0.0
 */
public class AbilityScoresTest {
        
	/**
	 * This method is for testing the bully ability scores
	 */
    @Test
    public void testBullyAbilityScores() {
            
        Fighter fighter = new FighterDriverClass("Bully").getFighter();
        int strength = fighter.getStrength();
        int constitution = fighter.getConstitution();
        int dexterity = fighter.getDexterity();
        
        assertTrue(strength >= constitution);           
        assertTrue(constitution >= dexterity);          
    }
    
    /**
	 * This method is for testing the nimble ability scores
	 */
    @Test
    public void testNimbleAbilityScores() {
            
        Fighter fighter = new FighterDriverClass("Nimble").getFighter();
        int strength = fighter.getStrength();
        int constitution = fighter.getConstitution();
        int dexterity = fighter.getDexterity();
        
        assertTrue(dexterity >= constitution);          
        assertTrue(constitution >= strength);           
    }
    
    /**
	 * This method is for testing the tank ability scores
	 */
    @Test
    public void testTankAbilityScores() {
            
        Fighter fighter = new FighterDriverClass("Tank").getFighter();
        
        int strength = fighter.getStrength();
        int constitution = fighter.getConstitution();
        int dexterity = fighter.getDexterity();
        
        assertTrue(constitution >= dexterity);          
        assertTrue(dexterity >= strength);
    }

}
