package tests.game.model.character;

import static org.junit.Assert.*;
import game.model.character.Character;
import game.model.character.CharactersList;

import org.junit.Test;

/**
 * This class is for testing the Attack modifiers
 */
public class AttackModifiersTest {
	
    /**
     * This method is for testing the Attack modifiers involved during combat
     */
    @Test
    public void testAttackModifiers(){
        
        Character character = CharactersList.getByName("saitej");
        
        if(character.getWeaponObject().itemClass.equalsIgnoreCase("Melee"))           
            assertEquals(character.getStrengthModifier() + (int) Math.ceil((double) character.getLevel() / (double) 4), character.getAttackBonus());
                
        else
            assertEquals(character.getDexterityModifier() + (int) Math.ceil((double) character.getLevel() / (double) 4), character.getAttackBonus());
            
    }

}
