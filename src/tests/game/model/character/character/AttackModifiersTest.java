package tests.game.model.character.character;

import static org.junit.Assert.*;
import game.model.character.Character;
import game.model.character.CharactersList;

import org.junit.Test;

public class AttackModifiersTest {
	
    @Test
    public void testAttackModifiers(){
        
        Character character = CharactersList.getByName("saitej");
        
        if(character.getWeaponObject().itemClass.equalsIgnoreCase("Melee"))           
            assertEquals(character.getStrengthModifier() + (int) Math.ceil((double) character.getLevel() / (double) 4), character.getAttackBonus());
                
        else
            assertEquals(character.getDexterityModifier() + (int) Math.ceil((double) character.getLevel() / (double) 4), character.getAttackBonus());
            
    }

}
