package model.character.wearables.weapons;

/**
 * Longsword is a Melee Weapon which has a score of
 *
 * @author Supreet Singh (s_supree)
 * @since 1.0.0
 */

public class Longsword extends WeaponAbstract{
	public Longsword(){
		this.weaponLevel = 1;
		this.setType("melee");
		this.diceSides = 6;
		this.numberOfRolls = 4;
	}
}