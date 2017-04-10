package tests.game.model.character.character;

import static org.junit.Assert.*;
import game.model.item.Item;
import game.model.jaxb.ItemJaxb;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.character.classes.CharacterClassStructure;
import game.model.character.classes.CharacterHelper;

import org.junit.Before;
import org.junit.Test;

public class AttackModifiersTest {

	int strength, constitution, dexterity;
	private Item item;
	int expectedAttackRoll;
	int expectedAttackPoints;
	int generatedAttackPoints;
	Character character;
	int itemLevel ;
	
	
	@Before
	public void init() {
		
		character = CharactersList.getByName("saitej");
		character.getConstitution();
		character.getStrength();
		character.getDexterity();
		itemLevel = character.getLevel();

	}
	
	@Test
	public void testAttackModifiers(){
		
		if(character.getWeaponObject().itemClass.equalsIgnoreCase("Melee"))
		{
			expectedAttackPoints = character.getStrengthModifier() + (int) Math.ceil((double) itemLevel / (double) 4);
			generatedAttackPoints = character.getAttackBonus();
		}
		else
		{
			expectedAttackPoints = character.getDexterityModifier() + (int) Math.ceil((double) itemLevel / (double) 4);
			generatedAttackPoints = character.getAttackBonus();
		}
		
		System.out.println("Ex + Gen" + expectedAttackPoints + " .. " + generatedAttackPoints);
		assertEquals(expectedAttackPoints, generatedAttackPoints);
		
	}

}
