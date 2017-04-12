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
     * This method is for testing the melee weapon Attack modifiers involved during combat
     */
    @Test
    public void testMeleeAttackModifiers(){
        
        Character character = CharactersList.getByName("saitej");
        character.setWeaponName("plain_melee_weapon");
        assertEquals(character.getStrengthModifier() + (int) Math.ceil((double) character.getLevel() / (double) 4), character.getAttackBonus());                    
    }
    
    /**
     * This method is for testing the ranged weapon Attack modifiers involved during combat
     */
    @Test
    public void testRangedAttackModifiers(){
        Character character = CharactersList.getByName("saitej");
        character.setWeaponName("plain_ranged_weapon");
        assertEquals(character.getDexterityModifier() + (int) Math.ceil((double) character.getLevel() / (double) 4), character.getAttackBonus());
    }

}
