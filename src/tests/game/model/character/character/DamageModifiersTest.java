package tests.game.model.character.character;

import static org.junit.Assert.*;
import game.components.Dice;
import game.model.character.CharactersList;
import game.model.character.Character;
import game.model.item.decoratorPattern.MeleeWeapon;
import game.model.item.decoratorPattern.RangedWeapon;
import game.model.item.decoratorPattern.Weapon;
import game.model.item.decoratorPattern.WeaponDecorator;

import org.junit.Before;
import org.junit.Test;

public class DamageModifiersTest {

	private Character character;
	Weapon ranged_Weapon, melee_Weapon;
	private int expectedDamagePoints;
	private int generatedDamagePoints_Range, generatedDamagePoints_Melee;
	private int diceRoll;
	
	@Before
	public void init(){
		
		character = CharactersList.getByName("saitej");
		ranged_Weapon = new WeaponDecorator(new RangedWeapon());
		diceRoll = ranged_Weapon.damagePoints(character);
		melee_Weapon = new WeaponDecorator(new MeleeWeapon());
		
	}
	
	@Test
	public void test() {
		
		if(character.getWeaponObject().itemClass.equalsIgnoreCase("Melee"))
		{
			expectedDamagePoints = diceRoll + character.getStrengthModifier();
			System.out.println("melee expected damage points" + expectedDamagePoints +" .. " +diceRoll);
		}
		else
		{
			expectedDamagePoints = diceRoll;
			System.out.println("Ranged expected damage points" +" .. "+ expectedDamagePoints );
		}
		
		generatedDamagePoints_Range = diceRoll ;
		
		System.out.println("gen points.." + generatedDamagePoints_Range );

		generatedDamagePoints_Melee = melee_Weapon.damagePoints(character);


		assertEquals(expectedDamagePoints, generatedDamagePoints_Range);
		
		assertNotEquals(expectedDamagePoints,generatedDamagePoints_Melee);
	}

}
